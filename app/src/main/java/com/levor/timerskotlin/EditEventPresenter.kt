package com.levor.timerskotlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.levor.timerskotlin.utils.FileUtils


class EditEventPresenter(var activity: EditEventActivity){

    fun showFileChooserDialog() {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST)
            return
        }
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            activity.startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    SELECT_FILE_IN_FILESYSTEM_REQUEST)
        } catch (ignored: android.content.ActivityNotFoundException) {
        }
    }

    fun onResultReceived(requestCode: Int, data: Intent?) {
        when (requestCode) {
            SELECT_FILE_IN_FILESYSTEM_REQUEST -> {
                val uri = data?.data
                if (uri != null) {
                    val path = FileUtils.getPathFromUri(activity, uri)
                    activity.updateImage(path)
                }
            }
        }
    }

    fun onWritePermissionGranted(requestCode: Int) {
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST -> showFileChooserDialog()
        }
    }

    companion object {
        val SELECT_FILE_IN_FILESYSTEM_REQUEST = 1001
        val WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 1002
    }
}
