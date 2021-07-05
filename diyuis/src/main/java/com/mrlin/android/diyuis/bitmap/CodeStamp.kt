package com.mrlin.android.diyuis.bitmap

import android.graphics.*
import android.text.TextPaint
import androidx.core.graphics.withSave

object CodeStamp {
    /**
     * 生成条码标签
     * @param content   内容
     * @param codeWidth 条码宽
     * @param codeHeight    条码高
     * @param textSize  提示文本大小，为0则不显示
     * @param rotation  旋转的角度
     * @return  条码图
     */
    fun createBarcodeStamp(content: String, codeWidth: Float, codeHeight: Float,
                           textSize: Float = 0f, rotation: Float = 0f): Bitmap? {
        val canvasHeight = codeHeight + textSize
        val codeImg = CodeUtils.createBarcode(content, codeWidth.toInt(), codeHeight.toInt()) ?: return null
        val canvasRect = RectF(0f, 0f, codeWidth, canvasHeight)
        //获得应用旋转后整体图片占据的边缘区域
        Matrix().apply { postRotate(rotation) }.mapRect(canvasRect)

        val canvasCenterX = canvasRect.width() / 2
        val canvasCenterY = canvasRect.height() / 2
        //输出的图片
        val canvasOutputImg = Bitmap.createBitmap(canvasRect.width().toInt(), canvasRect.height().toInt(), Bitmap.Config.ARGB_8888)

        val canvas = Canvas(canvasOutputImg)
        canvas.drawColor(Color.WHITE)
        canvas.withSave {
            //计算能够居中的坐标起始点
            val startX = (canvas.width - codeWidth) / 2f
            val startY = (canvas.height - canvasHeight) / 2f
            setMatrix(Matrix().apply {
                postRotate(rotation, canvasCenterX, canvasCenterY)
            })
            val codePaint = Paint().apply { isAntiAlias = true }
            drawBitmap(codeImg, startX, startY, codePaint)
            if (textSize > 0) {
                val textPaint = TextPaint()
                textPaint.textAlign = Paint.Align.CENTER
                textPaint.color = Color.BLACK
                textPaint.textSize = textSize
                textPaint.isAntiAlias = true
                drawText(content, canvasCenterX, startY + canvasHeight, textPaint)
            }
        }
        return canvasOutputImg
    }
}