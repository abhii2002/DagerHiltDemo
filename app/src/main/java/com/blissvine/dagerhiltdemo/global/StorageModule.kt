package com.blissvine.dagerhiltdemo.global

import com.blissvine.dagerhiltdemo.preferences.PreferenceImpl
import com.blissvine.dagerhiltdemo.preferences.PreferenceStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Binds
    abstract fun bindsPreferenceStorage(preferenceStorageImpl: PreferenceImpl): PreferenceStorage
}