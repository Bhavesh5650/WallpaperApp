package com.example.wallpaperapplication.network

import com.example.wallpaperapplication.data.WallpaperModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperInterface {

    @GET("api/")
    fun getWallpaper(
        @Query("key") key:String = "45815377-02edcef58947d46eef2b580d8",
        @Query("orientation") orientation:String="vertical",
        @Query("q") q:String,
        @Query("page") page:Int
    ) : Call<WallpaperModel>
}