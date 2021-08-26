package com.dantrap.andersen_homework_3

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dantrap.andersen_homework_3.databinding.ActivityMainBinding

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
                with (binding) {
                    textField.error = null
                }
            }
        }
    }

    private fun loadImage(url: String) {

        Glide
            .with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(this@MainActivity, "Loading failed", Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .error(R.drawable.ic_warning)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .placeholder(getCircularProgress(this))
            .into(binding.imageView)
    }



    companion object {

        private fun getCircularProgress(context: Context) : CircularProgressDrawable{
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            return circularProgressDrawable
        }
    }
}


