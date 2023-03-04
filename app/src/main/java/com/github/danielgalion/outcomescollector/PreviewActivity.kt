package com.github.danielgalion.outcomescollector

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.github.danielgalion.outcomescollector.databinding.ActivityPreviewBinding
import com.google.common.util.concurrent.ListenableFuture

class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        Log.i("preview", "oncreate further")
        cameraProviderFuture.addListener({
            Log.i("preview", "runnable")
            bindPreview(cameraProviderFuture.get())
        }, ContextCompat.getMainExecutor(this))


    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder().build()

        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview.setSurfaceProvider(binding.preview.surfaceProvider)

        val camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
    }
}