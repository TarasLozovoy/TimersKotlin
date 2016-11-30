package com.levor.timerskotlin.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView

import java.io.IOException
import java.lang.ref.WeakReference

object BitmapUtils {

    @Throws(IOException::class)
    fun loadBitmap(bitmapFilePath: String, maxSideSize: Int, imageView: ImageView) {
        LoadBitmapAsyncTask().execute(imageView, bitmapFilePath, maxSideSize)
    }

    @Throws(IOException::class)
    private fun getScaledBitmap(filePath: String, maxSideSize: Int): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, maxSideSize)

        options.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeFile(filePath, options) ?: throw IOException()

        return bitmap
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, maxSideSize: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > maxSideSize || width > maxSideSize) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested maxSideSize.
            while (halfHeight / inSampleSize >= maxSideSize && halfWidth / inSampleSize >= maxSideSize) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private class LoadBitmapAsyncTask : AsyncTask<Any, Void, Bitmap>() {
        private var imageView: WeakReference<ImageView>? = null

        override fun doInBackground(vararg params: Any?): Bitmap? {
            val image = params[0]
            if (image is ImageView) {
                imageView = WeakReference(image)
            }

            val filePath = params[1] as String
            if (filePath.isEmpty()) return null
            val maxSideSize = params[2] as Int
            return getScaledBitmap(filePath, maxSideSize)
        }

        override fun onPostExecute(result: Bitmap?) {
            val imageViewToUpdate = imageView?.get()
            imageViewToUpdate?.setImageBitmap(result)
        }

    }
}