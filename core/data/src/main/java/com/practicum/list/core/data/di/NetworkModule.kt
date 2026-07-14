package com.practicum.list.core.data.di

import android.content.Context
import com.practicum.list.core.common.utils.NetworkConnectionChecker
import com.practicum.list.core.common.utils.NetworkConnectionCheckerImpl
import com.practicum.list.core.data.network.ApiEndpoints
import com.practicum.list.core.data.network.ApiHostSelector
import com.practicum.list.core.data.network.AuthInterceptor
import com.practicum.list.core.data.network.NetworkClient
import com.practicum.list.core.data.network.TokenAuthenticator
import com.practicum.list.core.data.network.api.AuthApi
import com.practicum.list.core.data.network.client.ResilientAuthApi
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
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val PRIMARY_CONNECT_TIMEOUT_SECONDS = 3L
    private const val FALLBACK_CONNECT_TIMEOUT_SECONDS = 10L
    private const val READ_TIMEOUT_SECONDS = 30L

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    @PrimaryOkHttpClient
    fun providePrimaryOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient = createAuthOkHttpClient(
        authInterceptor = authInterceptor,
        tokenAuthenticator = tokenAuthenticator,
        connectTimeoutSeconds = PRIMARY_CONNECT_TIMEOUT_SECONDS,
    )

    @Provides
    @Singleton
    @FallbackOkHttpClient
    fun provideFallbackOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient = createAuthOkHttpClient(
        authInterceptor = authInterceptor,
        tokenAuthenticator = tokenAuthenticator,
        connectTimeoutSeconds = FALLBACK_CONNECT_TIMEOUT_SECONDS,
    )

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @PrimaryOkHttpClient okHttpClient: OkHttpClient,
    ): OkHttpClient = okHttpClient

    @Provides
    @Singleton
    @PrimaryRetrofit
    fun providePrimaryRetrofit(
        @PrimaryOkHttpClient okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit = createRetrofit(
        baseUrl = ApiEndpoints.PRIMARY_BASE_URL,
        okHttpClient = okHttpClient,
        moshi = moshi,
    )

    @Provides
    @Singleton
    @FallbackRetrofit
    fun provideFallbackRetrofit(
        @FallbackOkHttpClient okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit = createRetrofit(
        baseUrl = ApiEndpoints.FALLBACK_BASE_URL,
        okHttpClient = okHttpClient,
        moshi = moshi,
    )

    @Provides
    @Singleton
    fun provideRetrofit(
        @PrimaryRetrofit retrofit: Retrofit,
    ): Retrofit = retrofit

    @Provides
    @Singleton
    @PrimaryAuthApi
    fun providePrimaryAuthApi(
        @PrimaryRetrofit retrofit: Retrofit,
    ): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    @FallbackAuthApi
    fun provideFallbackAuthApi(
        @FallbackRetrofit retrofit: Retrofit,
    ): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideResilientAuthApi(
        @PrimaryAuthApi primaryAuthApi: AuthApi,
        @FallbackAuthApi fallbackAuthApi: AuthApi,
        hostSelector: ApiHostSelector,
    ): ResilientAuthApi = ResilientAuthApi(
        primary = primaryAuthApi,
        fallback = fallbackAuthApi,
        hostSelector = hostSelector,
    )

    @Provides
    @Singleton
    @RefreshRetrofit
    fun provideRefreshRetrofit(moshi: Moshi): Retrofit = createRetrofit(
        baseUrl = ApiEndpoints.PRIMARY_BASE_URL,
        okHttpClient = createRefreshOkHttpClient(PRIMARY_CONNECT_TIMEOUT_SECONDS),
        moshi = moshi,
    )

    @Provides
    @Singleton
    @RefreshFallbackRetrofit
    fun provideRefreshFallbackRetrofit(moshi: Moshi): Retrofit = createRetrofit(
        baseUrl = ApiEndpoints.FALLBACK_BASE_URL,
        okHttpClient = createRefreshOkHttpClient(FALLBACK_CONNECT_TIMEOUT_SECONDS),
        moshi = moshi,
    )

    @Provides
    @Singleton
    @RefreshAuthApi
    fun provideRefreshAuthApi(
        @RefreshRetrofit retrofit: Retrofit,
    ): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    @RefreshFallbackAuthApi
    fun provideRefreshFallbackAuthApi(
        @RefreshFallbackRetrofit retrofit: Retrofit,
    ): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    @RefreshResilientAuthApi
    fun provideRefreshResilientAuthApi(
        @RefreshAuthApi primaryAuthApi: AuthApi,
        @RefreshFallbackAuthApi fallbackAuthApi: AuthApi,
        hostSelector: ApiHostSelector,
    ): ResilientAuthApi = ResilientAuthApi(
        primary = primaryAuthApi,
        fallback = fallbackAuthApi,
        hostSelector = hostSelector,
    )

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides
    @Singleton
    fun provideNetworkConnectionChecker(
        @ApplicationContext context: Context,
    ): NetworkConnectionChecker = NetworkConnectionCheckerImpl(context)

    @Provides
    @Singleton
    fun provideNetworkClient(
        resilientAuthApi: ResilientAuthApi,
        networkConnectionChecker: NetworkConnectionChecker,
    ): NetworkClient = RetrofitNetworkClient(resilientAuthApi, networkConnectionChecker)

    private fun createAuthOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        connectTimeoutSeconds: Long,
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .connectTimeout(connectTimeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .addInterceptor(logging)
            .build()
    }

    private fun createRefreshOkHttpClient(connectTimeoutSeconds: Long): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(connectTimeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    private fun createRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}
