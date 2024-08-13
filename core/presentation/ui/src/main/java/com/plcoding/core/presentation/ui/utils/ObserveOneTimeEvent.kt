package com.plcoding.core.presentation.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Observes a [Flow] of events and triggers the provided [onEvent] function for each event.
 * This observation is tied to the lifecycle of the current [LocalLifecycleOwner],
 * ensuring that events are only collected when the lifecycle is at least in the STARTED state.
 *
 * @param T The type of events emitted by the [Flow].
 * @param flow The [Flow] to observe.
 * @param key1 An optional key to control recomposition. Defaults to `null`.
 * @param key2 An optional key to control recomposition. Defaults to `null`.
 * @param onEvent A callback that gets invoked with each new event from the [Flow].
 */
@Composable
fun <T> ObserveOneTimeEvent(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(flow, lifecycleOwner, key1, key2) {
        // Observes the flow only when the lifecycle is in the STARTED state or above.
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // Collect events on the main thread.
            withContext(Dispatchers.Main.immediate) {
                flow.collect { event ->
                    onEvent(event)
                }
            }
        }
    }
}
