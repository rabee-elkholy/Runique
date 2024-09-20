package com.plcoding.core.data.di

import com.plcoding.core.data.local.SessionStorageImpl
import com.plcoding.core.data.networking.HttpClientFactory
import com.plcoding.core.domain.session.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }

    singleOf(::SessionStorageImpl).bind<SessionStorage>()
}