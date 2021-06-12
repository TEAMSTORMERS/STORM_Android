package com.stormers.storm.mypage

import com.stormers.storm.mypage.datasource.MyPageDataSource
import com.stormers.storm.mypage.datasource.MyPageDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyPageModule {
    @Provides
    @Singleton
    fun provideMyPageService(retrofit: Retrofit): MyPageService = retrofit.create(MyPageService::class.java)

    @Provides
    @Singleton
    fun provideMyPageDataSource(myPageService: MyPageService): MyPageDataSource = MyPageDataSourceImpl(myPageService)
}