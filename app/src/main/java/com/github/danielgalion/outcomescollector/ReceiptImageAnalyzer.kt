package com.github.danielgalion.outcomescollector

import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

@ExperimentalGetImage class ReceiptImageAnalyzer: ImageAnalysis.Analyzer {

    private lateinit var objectDetector: ObjectDetector
    companion object {
        private const val TAG = "ReceiptImageAnalyzer"
    }

    init {
        initDetector()
    }
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image

        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

            // Pass image to an ML Kit Vision API
            // ...

            objectDetector.process(inputImage)
                .addOnSuccessListener {
                    Log.i(TAG, it.size.toString())
                    image.close()
                }
                .addOnFailureListener {
                    Log.e(TAG, it.message.toString())
                    Log.e(TAG, it.stackTraceToString())
                    image.close()
                }
        }
    }

    private fun initDetector() {
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .build()

        objectDetector = ObjectDetection.getClient(options)
    }
}