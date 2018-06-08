package com.ramza.kotlinsample.github

import io.reactivex.Observable
import retrofit2.Call

class SearchRepository(val apiService: GithubApiService) {
    fun searchUsers(location: String, language: String): Observable<Result> {
        return apiService.search(query = "location:$location+language:$language")
    }

    fun searchUser2(location: String, language: String): Call<Result> {
        return apiService.search2(query = "location:$location+language:$language")
    }
}