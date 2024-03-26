package com.mckimquyen.watermark.ui.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mckimquyen.watermark.databinding.AOpenSourceBinding
import com.mckimquyen.watermark.utils.ktx.inflate
import com.mckimquyen.watermark.utils.ktx.openLink

class OpenSourceActivity : AppCompatActivity() {

    private val binding by inflate<AOpenSourceBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)
        setupViews()
    }

    private fun setupViews() {
        binding.myToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.cardColorPicker.setOnClickListener {
            openLink("https://github.com/skydoves/ColorPickerView")
        }

        binding.cardGlideLibrary.setOnClickListener {
            openLink("https://github.com/bumptech/glide")
        }

        binding.cardMaterialComponents.setOnClickListener {
            openLink("https://github.com/material-components/material-components-android")
        }

        binding.cardMaterialCompressor.setOnClickListener {
            openLink("https://github.com/zetbaitsu/Compressor/")
        }
    }
}
