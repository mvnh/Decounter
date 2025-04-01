package com.mvnh.decounter.ui.navigation.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mvnh.decounter.R
import com.mvnh.decounter.ui.navigation.NavItem
import com.mvnh.decounter.ui.navigation.OnNavigateTo
import com.mvnh.decounter.ui.navigation.auth.AuthContent
import com.mvnh.decounter.ui.navigation.counter.CounterContent
import com.mvnh.decounter.ui.navigation.profile.ProfileContent
import com.mvnh.decounter.ui.navigation.root.RootComponent.Child.Auth
import com.mvnh.decounter.ui.navigation.root.RootComponent.Child.Counter
import com.mvnh.decounter.ui.navigation.root.RootComponent.Child.Profile
import com.mvnh.decounter.ui.navigation.root.RootComponent.Child.Settings
import com.mvnh.decounter.ui.navigation.root.RootComponent.Configuration.CounterConfiguration
import com.mvnh.decounter.ui.navigation.root.RootComponent.Configuration.ProfileConfiguration
import com.mvnh.decounter.ui.navigation.root.RootComponent.Configuration.SettingsConfiguration
import com.mvnh.decounter.ui.navigation.settings.SettingsContent

@Composable
fun RootContent(component: RootComponent) {
    val stack by component.childStack.subscribeAsState()
    val currentChild = stack.items.last().instance
    val currentConfiguration = stack.items.last().configuration

    Scaffold(
        bottomBar = {
            when (currentChild) {
                is Counter, is Profile, is Settings -> {
                    RootNavBar(
                        currentConfiguration = currentConfiguration as RootComponent.Configuration,
                        onNavigateTo = component::navigateTo
                    )
                }
                else -> { /* No bottom bar */ }
            }
        },
        contentWindowInsets = WindowInsets(0.dp),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Children(
                    stack = stack,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    animation = stackAnimation(fade()),
                    content = { child ->
                        when (val instance = child.instance) {
                            is Auth -> AuthContent(instance.component)
                            is Counter -> CounterContent(instance.component)
                            is Settings -> SettingsContent(instance.component)
                            is Profile -> ProfileContent(instance.component)
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun RootNavBar(
    currentConfiguration: RootComponent.Configuration,
    onNavigateTo: OnNavigateTo
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        navBarItems.forEach { item ->
            val isSelected = item.configuration == currentConfiguration

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        onNavigateTo(item.configuration)
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.label)
                    )
                }
            )
        }
    }
}

private val navBarItems = listOf(
    NavItem(
        configuration = CounterConfiguration,
        label = R.string.counter,
        icon = Icons.Default.Add
    ),
    NavItem(
        configuration = SettingsConfiguration,
        label = R.string.settings,
        icon = Icons.Default.Settings
    ),
    NavItem(
        configuration = ProfileConfiguration,
        label = R.string.profile,
        icon = Icons.Default.AccountCircle
    )
)
