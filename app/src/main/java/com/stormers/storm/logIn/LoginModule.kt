package com.stormers.storm.logIn

import com.stormers.storm.logIn.controller.LoginController
import com.stormers.storm.logIn.controller.LoginControllerImpl
import com.stormers.storm.logIn.service.LoginService
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