package com.artemissoftware.data

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.artemissoftware.data.errors.GithubProfileApiNetworkException
import com.artemissoftware.data.remote.sources.GithubSource
import com.artemissoftware.data.repository.GitHubRepositoryImpl
import okhttp3.OkHttpClient

class ff {
    private val BASE_URL = "https://api.github.com/graphql"


     fun setupApollo(): ApolloClient {
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
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttp)
            .build()
    }


    suspend fun testGet() {

        val client = setupApollo()
        val gitsource = GithubSource(client)
        val repo = GitHubRepositoryImpl(gitsource)
        try {

            val response = repo.getUserProfile("JakeWharton")
            Log.d("LaunchList", "Success ${response.data}")

        } catch (ex: GithubProfileApiNetworkException) {
            //ApiNetworkResponse(error = ex.toApiNetworkError())
        }
    }

    suspend fun getlolo() {

        val client = setupApollo()

        //val response = client.query(FindQuery("JakeWharton", "butterknife")).execute()

        val response = client.query(UserLoloQuery("JakeWharton")).execute()

        Log.d("LaunchList", "Success ${response.data}")

        //--Log.d("LaunchList", "Success ${response.data}")


//        client.query(FindQuery
//            .builder()
//            .name(repo_name_edittext.text.toString())
//            .owner(owner_name_edittext.text.toString())
//            .build())
//            .enqueue(object : ApolloCall.Callback<FindQuery.Data>() {
//
//                override fun onFailure(e: ApolloException) {
//                    Log.info(e.message.toString())
//                }
//
//                override fun onResponse(response: Response<FindQuery.Data>) {
//                    Log.info(" " + response.data()?.repository())
//                    runOnUiThread({
//                        progress_bar.visibility = View.GONE
//                        name_text_view.text = String.format(getString(R.string.name_text),
//                            response.data()?.repository()?.name())
//                        description_text_view.text = String.format(getString(R.string.description_text),
//                            response.data()?.repository()?.description())
//                        forks_text_view.text = String.format(getString(R.string.fork_count_text),
//                            response.data()?.repository()?.forkCount().toString())
//                        url_text_view.text = String.format(getString(R.string.url_count_text),
//                            response.data()?.repository()?.url().toString())
//                    })
//                }
//
//            })
//    })
//
//    }
    }
}