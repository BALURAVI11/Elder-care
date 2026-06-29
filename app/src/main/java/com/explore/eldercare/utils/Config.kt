package com.explore.eldercare.utils

import android.app.AlertDialog
import android.content.Context
import com.explore.eldercare.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Config {
    private var dialog : androidx.appcompat.app.AlertDialog? =null

    fun showDialog(context: Context) {
        dialog = MaterialAlertDialogBuilder(context).setView(R.layout.loading_layout).setCancelable(false).create()

        dialog!!.show()

    }

    fun hideDialog(){
        dialog!!.dismiss()
    }
}