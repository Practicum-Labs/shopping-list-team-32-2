package com.practicum.list.core.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PrimaryOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FallbackOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PrimaryAuthApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FallbackAuthApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshAuthApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshResilientAuthApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshFallbackAuthApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshFallbackRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PrimaryRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FallbackRetrofit