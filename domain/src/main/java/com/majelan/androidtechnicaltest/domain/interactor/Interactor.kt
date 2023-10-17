package com.majelan.androidtechnicaltest.domain.interactor

interface Interactor<P : Any, T> {
    suspend operator fun invoke(params: P): T
}

interface NoParamsInteractor<T> {
    suspend operator fun invoke(): T
}