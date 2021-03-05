package com.khansafzh.newsapp2.service

import android.telecom.Call
import com.khansafzh.newsapp2.model.ResponseNews
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
   @GET("top-headlines")
   fun getNewsData(
       @Query("country") country: String?,
       @Query("apikey")  apikey : String?
  ): retrofit2.Call<ResponseNews>
}