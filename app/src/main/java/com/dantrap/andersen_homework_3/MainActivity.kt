package com.dantrap.andersen_homework_3

import android.graphics.BitmapFactory
import android.os.Bundle
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.dantrap.andersen_homework_3.databinding.ActivityMainBinding
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.urlEditText.doOnTextChanged { text, _, _, _ ->
            if (URLUtil.isValidUrl(text.toString())) {
                binding.textField.error = null
                loadImage(text.toString())
            } else {
                binding.textField.error = "Invalid URL"
                binding.imageView.setImageResource(0)
            }
        }

        binding.urlEditText.doAfterTextChanged { text ->
            if (text.isNullOrEmpty()) {
                with(binding) {
                    textField.error = null
                }
            }
        }
    }

    private fun loadImage(imageUrl: String) {
        val thread = Thread {
            val url = URL(imageUrl)
            val urlConnection = url.openConnection() as HttpURLConnection
            try {
                val inputStream = BufferedInputStream(urlConnection.inputStream)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                runOnUiThread {
                    binding.imageView.setImageBitmap(bitmap)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    binding.imageView.setImageResource(R.drawable.ic_warning)
                }
            } finally {
                urlConnection.disconnect()
            }
        }
        thread.start()
    }
}


