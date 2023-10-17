package com.majelan.androidtechnicaltest.ui.architecture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<A : BaseAction, S : BaseState, E : BaseSideEffect> : ViewModel() {

    private val internalSideEffect = MutableSharedFlow<E>()

    abstract val state: StateFlow<S>
    val sideEffect = internalSideEffect.asSharedFlow()

    protected abstract suspend fun onHandle(action: A)

    fun handle(action: A) = viewModelScope.launch {
        onHandle(action)
    }

    protected fun dispatchSideEffect(sideEffect: E) {
        internalSideEffect.tryEmit(sideEffect)
    }
}

abstract class BaseViewModel2<A : BaseAction, S : BaseState, E : BaseSideEffect>(private val initialState: S) : BaseViewModel<A, S, E>() {

    private val internalState = MutableStateFlow(initialState)

    override val state = internalState.asStateFlow()

    protected fun updateState(reducer: (S) -> S) {
        internalState.value = reducer(state.value)
    }
}

interface BaseAction
interface BaseState
interface BaseSideEffect

sealed class UiState<out T> : BaseState {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val e: Exception) : UiState<Nothing>()
}

fun <T> UiState<T>.map(block: (data: T) -> T): UiState<T> {
    return when (this) {
        is UiState.Error, is UiState.Loading -> this
        is UiState.Success -> this.copy(data = block(this.data))
    }
}

fun <T> UiState<T>.getOrNull(): T? {
    return (this as? UiState.Success)?.data
}

fun <T> UiState<T>.isSuccessful(): Boolean {
    return (this is UiState.Success)
}

object NoSideEffect : BaseSideEffect

@Composable
fun <A : BaseAction, S : BaseState, E : BaseSideEffect> BaseViewModel<A, S, E>.watchAsState(): State<S> {
    return state.collectAsState()
}

@Composable
fun <A : BaseAction, S : BaseState, E : BaseSideEffect> OnSideEffect(viewModel: BaseViewModel<A, S, E>, block: suspend CoroutineScope.(sideEffect: E) -> Unit) {
    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect {
            block(it)
        }
    }
}
