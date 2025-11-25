package com.nikol.nav_impl.scopedNavigation

import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.getOrCreateScope
import org.koin.core.scope.Scope
import org.koin.viewmodel.scope.ScopeViewModel

@OptIn(KoinExperimentalAPI::class)
abstract class Component : ScopeViewModel() {
    override val scope: Scope by getOrCreateScope()
}