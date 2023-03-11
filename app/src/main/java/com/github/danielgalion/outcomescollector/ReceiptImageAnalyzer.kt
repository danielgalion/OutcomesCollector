package com.github.danielgalion.outcomescollector

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage

@ExperimentalGetImage class ReceiptImageAnalyzer: ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

            // Pass image to an ML Kit Vision API
            // ...
        }

        image.close()
    }
}