package com.stormers.storm.preference

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferenceManager(@ApplicationContext context: Context): SharedPreferenceManager =
        SharedPreferenceManager(context)

    @Provides
    @Singleton
    fun provideUserPreferenceManager(sharedPreferenceManager: SharedPreferenceManager): UserPreferenceManager =
        UserPreferenceManager(sharedPreferenceManager)
}
