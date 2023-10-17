package com.majelan.androidtechnicaltest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestObserver<T>(
    scope: CoroutineScope,
    testScheduler: TestCoroutineScheduler,
    flow: Flow<T>
) {
    val values = mutableListOf<T>()
    private val job = scope.launch(UnconfinedTestDispatcher(testScheduler)) {
        flow.collect { values.add(it) }
    }

    fun finish() {
        job.cancel()
    }
}

fun <T> Flow<T>.observe(scope: TestScope): TestObserver<T> {
    return TestObserver(scope, scope.testScheduler, this)
}
