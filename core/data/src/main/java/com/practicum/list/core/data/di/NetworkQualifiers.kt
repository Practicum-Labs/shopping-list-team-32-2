package com.practicum.list.core.data.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshAuthApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshRetrofit