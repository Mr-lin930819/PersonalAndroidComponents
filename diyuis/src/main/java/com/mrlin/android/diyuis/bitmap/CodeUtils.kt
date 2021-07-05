package com.mrlin.android.diyuis.bitmap

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix


object CodeUtils {
    /**
     *  生成条形码（不支持中文）
     *  @param content
     *  @return
     */
    fun createBarcode(content: String?, codeWidth: Int, codeHeight: Int): Bitmap? {
        try {
            val bitMatrix: BitMatrix =
                MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, codeWidth, codeHeight)
            val width: Int = bitMatrix.width
            val height: Int = bitMatrix.height
            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (bitMatrix.get(x, y)) -0x1000000 else -0x1
                }
            }
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }
}