package com.mvnh.decounter.ui.navigation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.mvnh.decounter.ui.navigation.auth.AuthComponent
import com.mvnh.decounter.ui.navigation.counter.CounterComponent
import com.mvnh.decounter.ui.navigation.profile.ProfileComponent
import com.mvnh.decounter.ui.navigation.settings.SettingsComponent
import kotlinx.serialization.Serializable

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Auth(val component: AuthComponent) : Child()
        data class Counter(val component: CounterComponent) : Child()
        data class Settings(val component: SettingsComponent) : Child()
        data class Profile(val component: ProfileComponent) : Child()
    }

    @Serializable
    sealed class Configuration {

        @Serializable
        data object AuthConfiguration : Configuration()

        @Serializable
        data object CounterConfiguration : Configuration()

        @Serializable
        data object SettingsConfiguration : Configuration()

        @Serializable
        data object ProfileConfiguration : Configuration()
    }

    fun navigateTo(configuration: Configuration)
}
