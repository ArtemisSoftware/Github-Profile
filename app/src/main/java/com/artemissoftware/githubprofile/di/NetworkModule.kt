package com.artemissoftware.githubprofile.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.artemissoftware.data.remote.sources.GithubSource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val BASE_URL = "https://api.github.com/graphql"

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
                    "Authorization", "Bearer " + "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"
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