package com.juannino.data.di

import javax.inject.Qualifier

/**
 * @author Juan Sebastian Niño - 2023
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: CabifyDispatcher) {
}

enum class CabifyDispatcher {
    Default,
    IO,
}