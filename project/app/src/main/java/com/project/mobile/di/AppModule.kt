package com.project.mobile.di;

import com.project.mobile.language.LanguageViewModel
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.project.mobile.data.RoutinesDao
import com.project.mobile.data.RoutinesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRoutinesDatabase(context: Application): RoutinesDatabase {
        return Room.databaseBuilder(
            context,
            RoutinesDatabase::class.java,
            RoutinesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRoutinesDAO(db: RoutinesDatabase): RoutinesDao {
        return db.dao
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    fun provideLanguageViewModel(
        application: Application,
        sharedPrefs: SharedPreferences
    ): LanguageViewModel {
        return LanguageViewModel(application, sharedPrefs)
    }
}