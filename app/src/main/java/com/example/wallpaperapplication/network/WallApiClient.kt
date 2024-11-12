package com.example.wallpaperapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WallApiClient {

    companion object{

        val WALLURL = "https://pixabay.com/"
        private var retrofit:Retrofit?=null

        fun wallpaperAPI() : Retrofit
        {
            if(retrofit==null)
            {
                retrofit = Retrofit.Builder().baseUrl(WALLURL).addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit!!
        }
    }
}