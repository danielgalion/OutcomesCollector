package com.github.danielgalion.outcomescollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.github.danielgalion.outcomescollector.databinding.ActivityPreviewBinding
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

@ExperimentalGetImage class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        Log.d("preview", "oncreate further")
        cameraProviderFuture.addListener({
            Log.d("preview", "runnable")
            bindPreview(cameraProviderFuture.get())
        }, ContextCompat.getMainExecutor(this))


    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder().build()

        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview.setSurfaceProvider(binding.preview.surfaceProvider)

        val camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, getImageAnalysis(), preview)
    }

    private fun getImageAnalysis(): ImageAnalysis {
        val imageAnalysis = ImageAnalysis.Builder()
        // enable the following line if RGBA output is needed.
        // .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
            .setTargetResolution(Size(1280,720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(mainExecutor, ReceiptImageAnalyzer()) // check the Executor - what should it be
        // shown here https://developer.android.com/training/camerax/analyze

        return imageAnalysis
    }

    private fun initDetector() {
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .build()

        val objectDetector = ObjectDetection.getClient(options)

    }
}