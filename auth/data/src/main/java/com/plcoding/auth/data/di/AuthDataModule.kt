package com.plcoding.auth.data.di

import com.plcoding.auth.data.EmailPatternValidator
import com.plcoding.auth.data.repository.RegisterRepositoryImpl
import com.plcoding.auth.domain.PatternValidator
import com.plcoding.auth.domain.UserDataValidator
import com.plcoding.auth.domain.repository.RegisterRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> { EmailPatternValidator }

    singleOf(::UserDataValidator)

    singleOf(::RegisterRepositoryImpl).bind<RegisterRepository>()
}