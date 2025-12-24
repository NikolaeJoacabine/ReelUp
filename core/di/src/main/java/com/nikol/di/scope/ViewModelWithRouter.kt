package com.nikol.di.scope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.nikol.viewmodel.Router
import com.nikol.viewmodel.BaseViewModel
import com.nikol.viewmodel.BindLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun <reified VM : BaseViewModel<*, *, *, R>, reified R : Router> viewModelWithRouter(
    crossinline routerFactory: () -> R
): VM {
    val vm: VM = koinViewModel()

    DisposableEffect(vm) {
        val router = routerFactory()
        vm.setRouter(router)
        onDispose { vm.setRouter(null) }
    }
    return vm
}
