package com.mvnh.decounter.ui.navigation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.items
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.mvnh.decounter.ui.navigation.auth.IAuthComponent
import com.mvnh.decounter.ui.navigation.counter.ICounterComponent
import com.mvnh.decounter.ui.navigation.profile.IProfileComponent
import com.mvnh.decounter.ui.navigation.root.RootComponent.Configuration
import com.mvnh.decounter.ui.navigation.settings.ISettingsComponent

class IRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val stack = StackNavigation<Configuration>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = stack,
            serializer = Configuration.serializer(),
            initialConfiguration = Configuration.AuthConfiguration,
            handleBackButton = true,
            childFactory = ::createChild
        )

    @OptIn(DelicateDecomposeApi::class)
    override fun navigateTo(configuration: Configuration) =
        if (childStack.items.any { it.configuration == configuration }) {
            stack.bringToFront(configuration)
        } else {
            stack.push(configuration)
        }

//    private val componentFactories: Map<Configuration, ComponentFactory> = mapOf(
//        Configuration.AuthConfiguration to { RootComponent.Child.Auth(IAuthComponent(it)) },
//        Configuration.CounterConfiguration to { RootComponent.Child.Counter(ICounterComponent(it)) },
//        Configuration.SettingsConfiguration to { RootComponent.Child.Settings(ISettingsComponent(it)) },
//        Configuration.ProfileConfiguration to { RootComponent.Child.Profile(IProfileComponent(it)) }
//    )

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (configuration) {
            is Configuration.AuthConfiguration -> {
                RootComponent.Child.Auth(
                    component = IAuthComponent(
                        onNavigateTo = { navigateTo(it) },
                        componentContext = componentContext
                    )
                )
            }
            is Configuration.CounterConfiguration -> {
                RootComponent.Child.Counter(
                    component = ICounterComponent(
                        componentContext = componentContext
                    )
                )
            }
            is Configuration.SettingsConfiguration -> {
                RootComponent.Child.Settings(
                    component = ISettingsComponent(
                        componentContext = componentContext
                    )
                )
            }
            is Configuration.ProfileConfiguration -> {
                RootComponent.Child.Profile(
                    component = IProfileComponent(
                        componentContext = componentContext
                    )
                )
            }
        }
}

//private typealias ComponentFactory = (ComponentContext) -> RootComponent.Child
