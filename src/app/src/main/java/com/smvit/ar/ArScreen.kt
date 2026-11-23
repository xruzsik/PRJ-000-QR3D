package com.smvit.ar

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Session
import com.google.ar.core.TrackingFailureReason
import com.google.ar.core.TrackingState
import com.smvit.ar.companion.Companion
import com.smvit.ar.companion.Floor
import com.smvit.ar.qr.Frames
import com.smvit.ar.qr.PoseSmoother
import com.smvit.ar.qr.QrPoseFilter
import com.smvit.ar.qr.QrScanner
import com.smvit.ar.qr.Quat
import com.smvit.ar.qr.RigidPose
import com.smvit.ar.qr.Vec3
import com.smvit.ar.qr.toArPose
import com.smvit.ar.qr.toRigidPose
import dev.romainguy.kotlin.math.Float3
import dev.romainguy.kotlin.math.Quaternion
import io.github.sceneview.ar.ARSceneView
import io.github.sceneview.node.ModelNode
import io.github.sceneview.node.Node
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelInstance
import io.github.sceneview.rememberModelLoader
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.exp

private const val TAG = "SmvitAR"

/** Continuous tracking needed before QR sightings are trusted. */
/** A sighting this far from where its code was means the world frame shifted. */
private const val WORLD_JUMP_METERS = 0.15

private fun TrackingFailureReason.toHintFailure(): SearchHint.Failure? = when (this) {
    TrackingFailureReason.NONE -> null
    TrackingFailureReason.INSUFFICIENT_LIGHT -> SearchHint.Failure.TOO_DARK
    TrackingFailureReason.EXCESSIVE_MOTION -> SearchHint.Failure.TOO_FAST
    TrackingFailureReason.INSUFFICIENT_FEATURES -> SearchHint.Failure.NOT_ENOUGH_DETAIL
    else -> SearchHint.Failure.OTHER
}

private const val TRACKING_WARMUP_NANOS = 1_500_000_000L

/** QR scans per second are capped by this interval; each one is ML Kit plus pose solving. */
private const val SCAN_INTERVAL_NANOS = 125_000_000L

/** A code not seen for this long is held where it was last shown, on an ARCore anchor. */
private const val LOST_NANOS = 1_000_000_000L

/** Sightings fitting their corners worse than this are ignored once a code is tracked. */
private const val MAX_REPROJECTION_PX = 3.0

/** A roaming character leaves its code once it's been out of view this long... */
private const val RELEASE_UNSEEN_NANOS = 2_000_000_000L

/** ...or once the player is this far (horizontally) from the code. */
private const val RELEASE_DISTANCE_METERS = 1.5

/** How often the floor height is re-estimated from ARCore's feature points. */
private const val FLOOR_CHECK_NANOS = 500_000_000L

/** Wander-angle offsets (radians) for the 1st, 2nd, ... companion, so they spread out. */
private val SIDE_BIASES = listOf(0.0, 0.6, -0.6, 1.1, -1.1)

/**
 * A code being tracked. While it's in view its model follows every QR
 * measurement: each is exact relative to the camera that took it, so the model
 * stays on the code even when ARCore's own position estimate drifts (by up to
 * ~20 cm on the A53). Out of view, the model is held on an ARCore anchor.
 */
private class TrackedCode(val entry: CatalogEntry, var target: RigidPose, var lastSeenNanos: Long) {
    val smoother = PoseSmoother()
    var displayed: RigidPose = target
    var held: Anchor? = null
    var node: Node? = null
    var offsetNode: Node? = null
    var model: ModelNode? = null
    var playing: String? = null

    /** Set once the character has left its code to roam with the player. */
    var companion: Companion? = null
    var lastStepNanos = 0L

    /** Model scale relative to its on-code size; grows to roaming size after release. */
    var scale = 1.0
}

/**
 * Scans QR codes and keeps each one's model from [Catalog] on it. A code's
 * anchor frame has +Y out of the code, +Z towards its bottom edge and +X towards
 * its right edge (the axis test cube shows them as green, blue and red).
 */
@Composable
fun ArScreen(catalog: Catalog, onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)

    val codes = remember { mutableStateMapOf<String, TrackedCode>() }
    var status by remember { mutableStateOf(Texts.SCAN_PROMPT) }
    val searchHint = remember { SearchHint() }
    var hint by remember { mutableStateOf<SearchHint.Hint?>(null) }
    // Last logged ARCore tracking state, as text (logged only when it changes).
    val lastTrackingLog = remember { AtomicReference("") }

    val context = LocalContext.current
    val store = remember { CollectionStore(context) }
    var progress by remember { mutableStateOf(TrailProgress(catalog.trail, store.load())) }
    // The story card on screen: the character and whether it was just collected.
    var story by remember { mutableStateOf<Pair<CatalogEntry, Boolean>?>(null) }
    var showCollection by remember { mutableStateOf(false) }
    var showBadge by remember { mutableStateOf(false) }

    val filter = remember {
        QrPoseFilter(requiredSamples = 3, onInconsistent = { payload, meters, radians ->
            Log.d(TAG, "\"$payload\": sightings disagree by %.1f mm / %.1f deg".format(meters * 1000, Math.toDegrees(radians)))
        })
    }
    // Frame timestamp since which ARCore has been tracking without a break (0: not tracking).
    val trackingSinceNanos = remember { AtomicLong(0) }
    // Floor height in ARCore's world frame (null: not estimated yet).
    val floorY = remember { AtomicReference<Double?>(null) }
    val lastFloorCheckNanos = remember { AtomicLong(0) }

    val scanner = remember {
        QrScanner(
            sizeFor = { payload -> catalog.forPayload(payload)?.qrSizeMeters },
            intervalNanos = SCAN_INTERVAL_NANOS,
            onUnknown = { payload -> if (codes.isEmpty()) status = Texts.unknownCode(payload) },
            expectedRotation = { payload -> codes[payload]?.target?.rotation },
        ) { sighting ->
            val payload = sighting.payload
            val code = codes[payload]
            if (code != null) {
                if (sighting.reprojectionErrorPx > MAX_REPROJECTION_PX) return@QrScanner
                // A code doesn't move, so a big step means ARCore's world frame
                // shifted (a tracking reset); logged to tell that apart from pose errors.
                val step = (sighting.worldFromAnchor.translation - code.target.translation).norm()
                if (step > WORLD_JUMP_METERS) Log.d(TAG, "\"$payload\": world jumped %.0f mm".format(step * 1000))
                code.target = sighting.worldFromAnchor
                code.lastSeenNanos = sighting.timestampNanos
                code.held?.let {
                    it.detach()
                    code.held = null
                }
                status = code.entry.name
                return@QrScanner
            }
            // A new code: wait for a few agreeing sightings before showing anything.
            val entry = catalog.forPayload(payload) ?: return@QrScanner
            when (val result = filter.add(payload, sighting.worldFromAnchor, sighting.reprojectionErrorPx, sighting.timestampNanos)) {
                QrPoseFilter.Result.Rejected ->
                    Log.d(TAG, "rejected \"$payload\": reprojection ${sighting.reprojectionErrorPx} px")
                is QrPoseFilter.Result.Collecting -> {
                    status = Texts.holdSteady(entry.name, result.count, result.required)
                    searchHint.reset()
                }
                is QrPoseFilter.Result.Stable -> {
                    codes[payload] = TrackedCode(entry, result.worldFromAnchor, sighting.timestampNanos)
                    status = entry.name
                    val find = progress.find(entry.id)
                    if (find.isNew) {
                        progress = find.progress
                        store.save(progress.found)
                    }
                    if (entry.story != null) story = entry to find.isNew
                    if (find.earnedBadge) showBadge = true
                    Log.d(TAG, "locked on \"$payload\" at ${result.worldFromAnchor.toArPose()}")
                }
            }
        }
    }
    DisposableEffect(scanner) { onDispose { scanner.close() } }

    Box(Modifier.fillMaxSize()) {
        ARSceneView(
            modifier = Modifier.fillMaxSize(),
            engine = engine,
            modelLoader = modelLoader,
            // Plane finding and depth both made tracking pause several times a
            // second on the A53, and QR tracking needs neither.
            planeRenderer = false,
            planeFindingMode = Config.PlaneFindingMode.DISABLED,
            depthMode = Config.DepthMode.DISABLED,
            sessionConfiguration = { _, config ->
                // Cheaper than ENVIRONMENTAL_HDR; the A53 overheats in long sessions.
                config.lightEstimationMode = Config.LightEstimationMode.AMBIENT_INTENSITY
            },
            onSessionUpdated = { session, frame ->
                // ARCore's world coordinates jump by up to a metre while tracking
                // settles (seen on the A53), so only use sightings after a warm-up,
                // and drop collected ones whenever tracking breaks.
                val tracking = frame.camera.trackingState == TrackingState.TRACKING
                val failureReason = frame.camera.trackingFailureReason
                val trackingLog = if (tracking) "TRACKING" else "${frame.camera.trackingState} ($failureReason)"
                if (lastTrackingLog.getAndSet(trackingLog) != trackingLog) Log.d(TAG, "ARCore: $trackingLog")
                if (!tracking) {
                    if (trackingSinceNanos.getAndSet(0) != 0L) filter.reset()
                } else if (trackingSinceNanos.get() == 0L) {
                    trackingSinceNanos.set(frame.timestamp)
                }
                val warmedUp = tracking && frame.timestamp - trackingSinceNanos.get() >= TRACKING_WARMUP_NANOS

                if (warmedUp) {
                    scanner.onFrame(frame)
                    if (status == Texts.TRACKING_PROMPT) status = Texts.SCAN_PROMPT
                } else if (codes.isEmpty()) {
                    status = Texts.TRACKING_PROMPT
                }
                val newHint = searchHint.update(
                    frame.timestamp,
                    searching = codes.isEmpty(),
                    readyToScan = warmedUp,
                    failure = if (tracking) null else failureReason.toHintFailure(),
                )
                if (newHint != hint) {
                    Log.d(TAG, "tip: ${newHint ?: "none"}")
                    hint = newHint
                }
                val camera = frame.camera.pose.toRigidPose()
                if (tracking && codes.values.any { it.entry.roamSizeMeters != null } &&
                    frame.timestamp - lastFloorCheckNanos.get() > FLOOR_CHECK_NANOS
                ) {
                    lastFloorCheckNanos.set(frame.timestamp)
                    updateFloor(frame, camera.translation.y, floorY)
                }
                codes.forEach { (payload, code) ->
                    val roaming = codes.values.count { it.companion != null }
                    if (updateDisplay(session, frame, tracking, camera, floorY, roaming, payload, code)) {
                        status = Texts.companionJoined(code.entry.name)
                    }
                }
            },
        ) {
            codes.forEach { (payload, code) ->
                // One node per code for its whole life: it is moved every frame,
                // never rebuilt (that would reload the model).
                key(payload) {
                    val entry = code.entry
                    // Parent: the code's upright frame, moved every frame. Child: the
                    // catalog offset. Grandchild: the model, scaled and with its
                    // bottom-centre at the origin (centerOrigin sets the model node's
                    // own position, which is why it can't be the node we move).
                    // Once roaming, the parent carries the companion's own pose and the
                    // offset node only its growing scale.
                    Node(apply = {
                        code.node = this
                        showAt(this, code.companion?.pose ?: Frames.upright(code.displayed))
                    }) {
                        Node(apply = {
                            code.offsetNode = this
                            if (code.companion == null) setLocalPose(this, entry.offset)
                        }) {
                            val model = rememberModelInstance(modelLoader, entry.model)
                            model?.let {
                                ModelNode(
                                    modelInstance = it,
                                    scaleToUnits = entry.modelSizeMeters,
                                    centerOrigin = Float3(0f, -1f, 0f),
                                    // Clips are switched by hand (idle/walk/run), never
                                    // auto-played: that would play all of them at once.
                                    autoAnimate = false,
                                    apply = {
                                        code.model = this
                                        if (code.playing == null) play(code, entry.animation)
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .safeDrawingPadding()
                .padding(16.dp),
        ) {
            story?.let { (entry, isNew) ->
                StoryCard(
                    entry = entry,
                    collectedLine = if (isNew) Texts.newlyCollected(progress) else Texts.ALREADY_COLLECTED,
                    onClose = { story = null },
                )
            }
            StatusText(status)
            hint?.let { StatusText(Texts.hint(it)) }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { showCollection = true }) { Text(Texts.collectionButton(progress)) }
                if (codes.isNotEmpty()) {
                    OutlinedButton(onClick = {
                        codes.values.forEach { it.held?.detach() }
                        codes.clear()
                        filter.reset()
                        story = null
                        status = Texts.SCAN_PROMPT
                        searchHint.reset()
                    }) {
                        Text(Texts.RESCAN)
                    }
                }
            }
        }

        if (showCollection) {
            CollectionScreen(
                catalog = catalog,
                progress = progress,
                onClose = { showCollection = false },
                onClear = {
                    progress = progress.copy(found = emptySet())
                    store.save(emptySet())
                },
            )
        }
        if (showBadge) BadgeDialog(onClose = { showBadge = false })
    }
}

/**
 * Moves [code]'s model one frame: roaming with the player once released;
 * before that, towards the latest QR measurement while the code is in view,
 * else the anchor it's held on. Returns true on the frame it's released.
 */
private fun updateDisplay(
    session: Session,
    frame: Frame,
    tracking: Boolean,
    camera: RigidPose,
    floorY: AtomicReference<Double?>,
    roaming: Int,
    payload: String,
    code: TrackedCode,
): Boolean {
    val now = frame.timestamp
    val companion = code.companion
    if (companion != null) {
        val dt = (now - code.lastStepNanos).coerceIn(0, 100_000_000) / 1e9
        code.lastStepNanos = now
        val ground = floorY.get() ?: Floor.assumed(camera.translation.y).also { floorY.compareAndSet(null, it) }
        // ARCore's camera looks along its -Z.
        companion.step(dt, camera.translation, camera.rotation.column(2) * -1.0, ground)
        code.node?.let { showAt(it, companion.pose) }
        growToRoamingSize(code, dt)
        play(
            code,
            when (companion.gait) {
                Companion.Gait.IDLE -> code.entry.animation
                Companion.Gait.WALK -> code.entry.walkAnimation ?: code.entry.animation
                Companion.Gait.RUN -> code.entry.runAnimation ?: code.entry.walkAnimation ?: code.entry.animation
            },
        )
        return false
    }

    val roamSize = code.entry.roamSizeMeters
    if (roamSize != null && tracking) {
        val codePosition = code.displayed.translation
        val awayFromCode = Math.hypot(codePosition.x - camera.translation.x, codePosition.z - camera.translation.z)
        if (now - code.lastSeenNanos > RELEASE_UNSEEN_NANOS || awayFromCode > RELEASE_DISTANCE_METERS) {
            val start = Frames.upright(code.displayed) compose code.entry.offset
            code.companion = Companion(start, roamSize, SIDE_BIASES[roaming % SIDE_BIASES.size])
            code.lastStepNanos = now
            code.held?.detach()
            code.held = null
            code.offsetNode?.let { setLocalPose(it, RigidPose.IDENTITY) }
            Log.d(TAG, "\"$payload\": released to roam (%.1f m from the code)".format(awayFromCode))
            return true
        }
    }

    if (tracking && code.held == null && now - code.lastSeenNanos > LOST_NANOS) {
        code.held = session.createAnchor(code.displayed.toArPose())
        Log.d(TAG, "\"$payload\": out of view, holding it on an anchor")
    }
    val held = code.held
    val target = if (held != null && held.trackingState == TrackingState.TRACKING) held.pose.toRigidPose() else code.target
    code.displayed = code.smoother.step(target, now)
    code.node?.let { showAt(it, Frames.upright(code.displayed)) }
    return false
}

/** Eases a released character from its on-code size up to its roaming size. */
private fun growToRoamingSize(code: TrackedCode, dt: Double) {
    val roamSize = code.entry.roamSizeMeters ?: return
    val target = roamSize / code.entry.modelSizeMeters
    code.scale += (target - code.scale) * (1 - exp(-dt / 0.4))
    code.offsetNode?.scale = Float3(code.scale.toFloat())
}

/** Switches the looping animation to [name] (no-op if it's already playing or null). */
private fun play(code: TrackedCode, name: String?) {
    if (name == null || name == code.playing) return
    val model = code.model ?: return
    code.playing?.let { model.stopAnimation(it) }
    model.playAnimation(name, 1f, true)
    code.playing = name
}

/** Re-estimates the floor from ARCore's feature points, smoothing out jumps. */
private fun updateFloor(frame: Frame, cameraY: Double, floorY: AtomicReference<Double?>) {
    val points = frame.acquirePointCloud().use { cloud ->
        val buffer = cloud.points // x, y, z, confidence per point
        (0 until buffer.remaining() / 4).mapNotNull { i ->
            if (buffer.get(i * 4 + 3) < 0.3f) return@mapNotNull null
            Vec3(buffer.get(i * 4).toDouble(), buffer.get(i * 4 + 1).toDouble(), buffer.get(i * 4 + 2).toDouble())
        }
    }
    val estimate = Floor.fromPoints(points, cameraY) ?: return
    val previous = floorY.get()
    floorY.set(if (previous == null) estimate else previous + (estimate - previous) * 0.3)
}

private fun setLocalPose(node: Node, pose: RigidPose) {
    val q = Quat.fromMatrix(pose.rotation)
    node.position = Float3(pose.translation.x.toFloat(), pose.translation.y.toFloat(), pose.translation.z.toFloat())
    node.quaternion = Quaternion(q.x.toFloat(), q.y.toFloat(), q.z.toFloat(), q.w.toFloat())
}

private fun showAt(node: Node, pose: RigidPose) {
    val q = Quat.fromMatrix(pose.rotation)
    node.worldPosition = Float3(pose.translation.x.toFloat(), pose.translation.y.toFloat(), pose.translation.z.toFloat())
    node.worldQuaternion = Quaternion(q.x.toFloat(), q.y.toFloat(), q.z.toFloat(), q.w.toFloat())
}
