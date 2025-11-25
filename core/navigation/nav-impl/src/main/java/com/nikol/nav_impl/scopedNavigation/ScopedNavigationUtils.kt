package com.nikol.nav_impl.scopedNavigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.toRoute
import org.koin.compose.ComposeContextWrapper
import org.koin.compose.LocalKoinApplication
import org.koin.compose.LocalKoinScope
import org.koin.compose.currentKoinScope
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.viewmodel.defaultExtras
import org.koin.viewmodel.factory.KoinViewModelFactory
import androidx.navigation.compose.composable as nativeComposable
import androidx.navigation.navigation as nativeNavigation
import kotlin.reflect.KClass
import kotlin.reflect.KType

inline fun <reified C : Component> Module.component(
    crossinline constructor: Scope.() -> C,
) = viewModel { this.constructor() }

class KoinNavigationScope<PC : Component>(
    val clazz: KClass<PC>,
    val getParentEntry: () -> NavBackStackEntry,
) {

    inline fun <reified T : Any, reified C : Component> NavGraphBuilder.composable(
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
        crossinline parseParameters: (T) -> ParametersHolder = { parametersOf() },
        crossinline parseParentParameters: (T) -> ParametersHolder = { parametersOf() },
        crossinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
    ) {
        nativeComposable<T>(typeMap = typeMap) { navBackStackEntry ->
            ParentScopedContent<C>(
                navBackStackEntry,
                parseParameters(navBackStackEntry.toRoute()),
                parseParentParameters(navBackStackEntry.toRoute()),
                content,
            )
        }
    }


    @OptIn(KoinInternalApi::class)
    @Composable
    inline fun <reified C : Component> AnimatedContentScope.ParentScopedContent(
        navBackStackEntry: NavBackStackEntry,
        parameters: ParametersHolder,
        parentParameters: ParametersHolder,
        crossinline content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
    ) {
        val parentEntry = remember { getParentEntry() }
        val parentComponent = parentEntry.getScopeComponent(clazz) { parentParameters }
        val component = navBackStackEntry.getScopeComponent(C::class) { parameters }
        component.scope.linkTo(parentComponent.scope)

        CompositionLocalProvider(
            LocalKoinApplication provides ComposeContextWrapper(component.scope.getKoin()),
            LocalKoinScope provides ComposeContextWrapper(component.scope),
        ) {
            content(navBackStackEntry)
        }
    }

    @Composable
    fun <SC : Component> NavBackStackEntry.getScopeComponent(
        clazz: KClass<SC>,
        parameters: ParametersDefinition? = null,
    ): SC {
        val factory = KoinViewModelFactory(clazz, currentKoinScope(), null, parameters)
        val provider = ViewModelProvider.create(viewModelStore, factory, defaultExtras(this))
        return provider[clazz]
    }
}

inline fun <reified T : Any, reified C : Component> NavGraphBuilder.navigation(
    controller: NavController,
    startDestination: Any,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    crossinline builder: NavGraphBuilder.(KoinNavigationScope<C>) -> Unit,
) {
    nativeNavigation<T>(startDestination, typeMap) {
        builder(KoinNavigationScope(C::class) { controller.getBackStackEntry<T>() })
    }
}

inline fun <reified T : Any, reified C : Component> NavGraphBuilder.navigation(
    controller: NavController,
    startDestination: KClass<*>,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    crossinline builder: NavGraphBuilder.(KoinNavigationScope<C>) -> Unit,
) {
    nativeNavigation<T>(startDestination, typeMap) {
        builder(KoinNavigationScope(C::class) { controller.getBackStackEntry<T>() })
    }
}

inline fun <reified C : Component> NavGraphBuilder.navigation(
    controller: NavController,
    startDestination: String,
    route: String,
    builder: NavGraphBuilder.(KoinNavigationScope<C>) -> Unit,
) {
    nativeNavigation(
        startDestination = startDestination,
        route = route,
    ) {
        builder(KoinNavigationScope(C::class) { controller.getBackStackEntry(route) })
    }
}
