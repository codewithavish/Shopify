package com.example.shopify.presentation.auth.register

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description


//Your ViewModel uses viewModelScope, which relies on the main coroutine dispatcher.
// Ordinary JVM unit tests don't have Android's real Main dispatcher, so we replace it with a test dispatcher.
//What did we achieve?
// Our tests can now control when coroutines execute, making them predictable rather than dependent on real timing.


@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}