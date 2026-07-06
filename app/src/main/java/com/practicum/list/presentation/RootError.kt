package com.practicum.list.presentation

import androidx.annotation.StringRes
import com.practicum.list.R

enum class RootError(
    @StringRes val title: Int? = null,
    @StringRes val message: Int,
    @StringRes val buttonText: Int? = null
) {
    NO_INTERNET(
        title = R.string.no_internet,
        message = R.string.check_settings_and_wi_fi,
        buttonText = R.string.check_again
    ),
    SERVER_ERROR(
        title = R.string.server_error,
        message = R.string.server_error_description,
        buttonText = R.string.check_again
    ),
    UNDEFINED_ERROR(
        title = R.string.something_wrong,
        message = R.string.cannot_find_profile,
        buttonText = R.string.renew_session
    )
}