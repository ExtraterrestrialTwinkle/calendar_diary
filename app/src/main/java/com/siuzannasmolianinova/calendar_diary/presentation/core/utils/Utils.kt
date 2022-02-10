package com.siuzannasmolianinova.calendar_diary.presentation.core.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(view: View, anchor: View?, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        .apply { anchorView = anchor }
        .show()
}

