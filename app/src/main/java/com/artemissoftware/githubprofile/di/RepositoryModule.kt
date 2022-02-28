package com.artemissoftware.githubprofile.di

import com.artemissoftware.data.database.GitHubDataBase
import com.artemissoftware.data.remote.sources.GithubSource
import com.artemissoftware.data.repository.GitHubRepositoryImpl
import com.artemissoftware.domain.repository.GitHubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideGitHubRepository(
        gitHubSource: GithubSource,
        db: GitHubDataBase,
    ): GitHubRepository {
        return GitHubRepositoryImpl(gitHubSource, db.userDao)
    }

}