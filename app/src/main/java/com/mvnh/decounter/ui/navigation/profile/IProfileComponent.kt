package com.mvnh.decounter.ui.navigation.profile

import com.arkivanov.decompose.ComponentContext

class IProfileComponent(
    componentContext: ComponentContext
) : ProfileComponent, ComponentContext by componentContext
