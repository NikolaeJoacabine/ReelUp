package com.nikol.nav_impl.back_argument

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.nikol.nav_api.BackArgument
import com.nikol.nav_api.BackArgumentHolder

class SavedStateBackArgumentHolder(private val savedStateHandle: SavedStateHandle) :
    BackArgumentHolder {
    override fun <T> get(key: String): T? {
        return savedStateHandle[key]
    }

    override fun <T> set(key: String, value: T) {
        savedStateHandle[key] = value
    }
}

infix fun <T : BackArgument<*>> NavController.with(argument: T): NavController = apply {
    previousBackStackEntry?.savedStateHandle?.set(argument.key, argument.value)
}