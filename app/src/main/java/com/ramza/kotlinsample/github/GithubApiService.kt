package com.ramza.kotlinsample.github

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/users")
    fun search(@Query("q") query: String,
               @Query("page") page: Int = 1,
               @Query("per_page") perPage: Int = 10): Observable<Result>


    @GET("search/users")
    fun search2(@Query("q") query: String,
               @Query("page") page: Int = 1,
               @Query("per_page") perPage: Int = 10): Call<Result>

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): GithubApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()

            return retrofit.create(GithubApiService::class.java);
        }

        fun create2(): GithubApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()

            return retrofit.create(GithubApiService::class.java);
        }
    }
}