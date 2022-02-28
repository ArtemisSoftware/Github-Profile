package com.artemissoftware.githubprofile.di

import android.app.Application
import androidx.room.Room
import com.artemissoftware.data.database.GitHubDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideGitHubDataBase(app: Application): GitHubDataBase {
        return Room.databaseBuilder(
            app,
            GitHubDataBase::class.java, "github_db"
        )
            //.addTypeConverter(Converters(GsonParser(Gson())))
        .build()
    }
}