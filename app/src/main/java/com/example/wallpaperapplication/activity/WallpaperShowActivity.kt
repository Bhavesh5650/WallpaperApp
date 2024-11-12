package com.example.wallpaperapplication.activity

import android.R.attr.bitmap
import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.wallpaperapplication.MainActivity
import com.example.wallpaperapplication.MainActivity.Companion.wallList
import com.example.wallpaperapplication.R
import com.example.wallpaperapplication.adapter.ViewPagerAdapter
import com.example.wallpaperapplication.databinding.ActivityWallpaperShowBinding


class WallpaperShowActivity : AppCompatActivity() {

    private lateinit var binding:ActivityWallpaperShowBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWallpaperShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backBtnClick()

        val intentImage = intent.getStringExtra("wallpaper")
        val position = intent.getIntExtra("position",1)

        val viewPagerAdapter = ViewPagerAdapter(MainActivity.wallList)
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.setCurrentItem(position,false)

        binding.setWallpaperBtn.setOnClickListener {

            val wallpaperManager = WallpaperManager.getInstance(this)
            var currentPosition = binding.viewPager.currentItem

            Glide.with(this).asBitmap().load(wallList[currentPosition]!!.largeImageURL).listener(@SuppressLint(
                "CheckResult"
            )
            object :
                RequestListener<Bitmap> {
                @SuppressLint("CheckResult")
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    target: Target<Bitmap>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {

                    val metrics = DisplayMetrics()
                    val display = windowManager.defaultDisplay
                    display.getMetrics(metrics)
                    val screenWidth = metrics.widthPixels
                    val screenHeight = metrics.heightPixels


                    wallpaperManager.suggestDesiredDimensions(screenWidth, screenHeight)
                    val width = wallpaperManager.getDesiredMinimumWidth();
                    val height = wallpaperManager.getDesiredMinimumHeight();

                    if(resource.width - width / 2 > 500) {
                        val x = (resource.width - width) / 2
                        val y = (resource.height - height) / 2

                        val wallpaper = Bitmap.createBitmap(resource, x, y, width, height)
                        wallpaperManager.setBitmap(wallpaper)
                    }
                    else {
                        val wallpaper = Bitmap.createBitmap(resource, width, height, resource.width, resource.height)
                        wallpaperManager.setBitmap(wallpaper)
                    }
                    return false
                }
            }).submit()

            Toast.makeText(this@WallpaperShowActivity, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backBtnClick()
    {
        binding.wallShowBackBtn.setOnClickListener {
            finish()
        }
    }


}