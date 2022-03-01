package com.artemissoftware.githubprofile.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.artemissoftware.data.remote.sources.GithubSource
import com.artemissoftware.githubprofile.util.ApiConstants.BASE_URL
import com.artemissoftware.githubprofile.util.ApiConstants.GITHUB_PROFILE_KEY

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(
                    original.method,
                    original.body
                )
                builder.addHeader(
                    "Authorization", GITHUB_PROFILE_KEY
                )
                chain.proceed(builder.build())
            }
            .build()
        return ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttp)
            .build()
    }



    @Provides
    @Singleton
    fun provideGithubSource(apolloClient: ApolloClient): GithubSource {
        return GithubSource(apolloClient)
    }

}