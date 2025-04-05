package com.project.mobile.di;

import android.app.Application
import androidx.room.Room
import com.project.mobile.data.StoriesDao
import com.project.mobile.data.StoriesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideStoriesDatabase(context: Application): StoriesDatabase {
        return Room.databaseBuilder(
            context,
            StoriesDatabase::class.java,
            StoriesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideStoriesDAO(db: StoriesDatabase): StoriesDao {
        return db.dao
    }
}