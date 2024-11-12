package com.example.wallpaperapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapplication.adapter.WallpaperAdapter
import com.example.wallpaperapplication.data.HitsItem
import com.example.wallpaperapplication.data.WallpaperModel
import com.example.wallpaperapplication.databinding.ActivityMainBinding
import com.example.wallpaperapplication.network.WallApiClient.Companion.wallpaperAPI
import com.example.wallpaperapplication.network.WallpaperInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var pageNo = 1
    lateinit var wallpaperAdapter: WallpaperAdapter
    companion object{
        var wallList = mutableListOf<HitsItem?>()
    }
    var search:String="nature"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search = query!!
                wallpaperAdapter.clearData()
                callWallpaperApi(search = query, pageNo)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        Log.d("TAG", "onCreate ========= $search")
        callWallpaperApi(search, pageNo)


        wallpaperAdapter = WallpaperAdapter(wallList)
        binding.wallpaperRV.adapter = wallpaperAdapter

        binding.wallpaperRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val childCount = layoutManager.childCount
                val totalCount = layoutManager.itemCount
                val firstFind = layoutManager.findFirstVisibleItemPosition()

                if (childCount + firstFind >= totalCount) {
                    callWallpaperApi(search, pageNo++)
                }
            }
        })

    }

    private fun callWallpaperApi(search: String, pageNo: Int) {
        val wallpaperInterface = wallpaperAPI().create(WallpaperInterface::class.java)
        wallpaperInterface.getWallpaper(q = search, page = pageNo)
            .enqueue(object : Callback<WallpaperModel> {
                override fun onResponse(
                    call: Call<WallpaperModel>,
                    response: Response<WallpaperModel>
                ) {
                    Log.i("Success", "onResponse ======= ${response.body()} ")
                    wallpaperAdapter.setData(response.body()!!.hits as MutableList)

                }

                override fun onFailure(call: Call<WallpaperModel>, t: Throwable) {

                    Log.e("Error", "onFailure ====== ${t.message} ")
                }
            })
    }

}