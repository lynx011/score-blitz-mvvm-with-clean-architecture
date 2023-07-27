package com.lynx.scoreblitz.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectLatest(
    lifecycleOwner: LifecycleOwner, crossinline onCollect: suspend (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        collectLatest {
            onCollect(it)
        }
    }
}


inline fun <T> MutableSharedFlow<T>.emit(
    coroutineScope: CoroutineScope, crossinline onEmit: suspend () -> T
) {
    coroutineScope.launch {
        emit(onEmit())
    }
}