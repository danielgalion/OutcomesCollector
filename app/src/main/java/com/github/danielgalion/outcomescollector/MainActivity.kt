package com.github.danielgalion.outcomescollector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.danielgalion.outcomescollector.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        requestPermissions(arrayOf(android.Manifest.permission.CAMERA),1)

        binding.previewBtn.setOnClickListener {

            Log.d("click", "clicked")

            this.startActivity(
                Intent(this, PreviewActivity::class.java)
            )
        }
    }
}