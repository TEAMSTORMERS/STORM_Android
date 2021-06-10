package com.stormers.storm.login

import com.stormers.storm.login.controller.LoginController
import com.stormers.storm.login.controller.LoginControllerImpl
import com.stormers.storm.login.service.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LoginModule {

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideLoginController(loginService: LoginService): LoginController =
        LoginControllerImpl(loginService)
}