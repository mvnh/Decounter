package com.mvnh.decounter.ui.navigation.auth

import com.arkivanov.decompose.ComponentContext
import com.mvnh.decounter.ui.navigation.OnNavigateTo
import com.mvnh.decounter.ui.navigation.root.RootComponent.Configuration.CounterConfiguration

class IAuthComponent(
    val onNavigateTo: OnNavigateTo,
    componentContext: ComponentContext
) : AuthComponent, ComponentContext by componentContext {

    override fun login() {
        onNavigateTo(CounterConfiguration)
    }
}
