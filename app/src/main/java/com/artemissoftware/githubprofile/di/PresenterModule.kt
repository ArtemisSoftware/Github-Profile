package com.artemissoftware.githubprofile.di

import android.app.Application
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.artemissoftware.data.database.GitHubDataBase
import com.artemissoftware.data.remote.sources.GithubSource
import com.artemissoftware.domain.usecase.GetUserProfileUseCase
import com.artemissoftware.domain.usecase.RefreshUserProfileUseCase
import com.artemissoftware.githubprofile.ui.GithubProfileContract
import com.artemissoftware.githubprofile.ui.GithubProfileFragment
import com.artemissoftware.githubprofile.ui.GithubProfilePresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.scopes.FragmentScoped




@Module
@InstallIn(SingletonComponent::class)
object PresenterModule {


    @Provides
    @Singleton
    fun provideGithubProfilePresenter(
        getUserProfileUseCase: GetUserProfileUseCase,
        refreshUserProfileUseCase: RefreshUserProfileUseCase
    ): GithubProfilePresenter {
        return GithubProfilePresenter(getUserProfileUseCase, refreshUserProfileUseCase)
    }

//    @Provides
//    @Singleton
//    fun provideGitHubDataBase(app: Application): GitHubDataBase {
//        return Room.databaseBuilder(
//            app,
//            GitHubDataBase::class.java, "github_db"
//        )
//            //.addTypeConverter(DateConverter)
//            .build()
//    }
}


//@InstallIn(FragmentComponent::class)
//@Module
//abstract class PresenterModule {
//
//    @Binds
//    abstract fun bindActivity(activity: GithubProfileFragment): GithubProfileContract.View
//
//
//
//}