package com.mvnh.decounter.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.mvnh.decounter.ui.navigation.root.RootComponent

data class NavItem(
    val configuration: RootComponent.Configuration,
    @StringRes val label: Int,
    val icon: ImageVector,
)
