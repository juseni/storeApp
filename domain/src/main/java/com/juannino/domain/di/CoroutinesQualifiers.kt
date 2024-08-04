package com.juannino.domain.di

import javax.inject.Qualifier

/**
 * @author Juan Sebastian Niño - 2023
 */

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher