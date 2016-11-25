package com.levor.timerskotlin.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory

import java.io.IOException

object BitmapUtils {

    @Throws(IOException::class)
    fun getScaledBitmap(filePath: String, maxSideSize: Int): Bitmap {
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
}