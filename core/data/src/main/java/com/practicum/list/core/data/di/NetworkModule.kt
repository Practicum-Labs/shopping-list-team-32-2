package com.practicum.list.core.data.di

import android.content.Context
import com.practicum.list.core.common.utils.NetworkConnectionChecker
import com.practicum.list.core.common.utils.NetworkConnectionCheckerImpl
import com.practicum.list.core.data.network.AuthInterceptor
import com.practicum.list.core.data.network.NetworkClient
import com.practicum.list.core.data.network.TokenAuthenticator
import com.practicum.list.core.data.network.api.AuthApi
import com.practicum.list.core.data.network.client.RetrofitNetworkClient
import com.practicum.list.core.data.remote.api.ProductApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://practicumopbackend-production.up.railway.app/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(TokenAuthenticator(context, userSession))
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides @Singleton
    fun provideNetworkConnectionChecker(
        @ApplicationContext context: Context,
    ): NetworkConnectionChecker = NetworkConnectionCheckerImpl(context)

    @Provides @Singleton
    fun provideNetworkClient(
        authApi: AuthApi,
        networkConnectionChecker: NetworkConnectionChecker,
    ): NetworkClient = RetrofitNetworkClient(authApi, networkConnectionChecker)
}
