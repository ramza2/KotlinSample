package com.ramza.kotlinsample.github

object SearchRepositoryProvider {
    fun provideSearchRepository(): SearchRepository {
        return SearchRepository(GithubApiService.create())
    }

    fun provideSearchRepository2(): SearchRepository{
        return SearchRepository(GithubApiService.create2())
    }
}
