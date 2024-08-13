package com.plcoding.core.presentation.ui.utils

import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.presentation.ui.R

/**
 * Converts a [DataError] to a [UiText] representation for displaying user-friendly error messages.
 *
 * @return A [UiText] instance corresponding to the specific [DataError].
 */
fun DataError.asUiText(): UiText = when (this) {
    DataError.Local.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
    DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.error_no_internet)
    DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(R.string.error_payload_too_large)
    DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.error_request_timeout)
    DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.error_server)
    DataError.Network.SERIALIZATION -> UiText.StringResource(R.string.error_serialization)
    DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.error_too_many_requests)
    else -> UiText.StringResource(R.string.error_unknown)
}