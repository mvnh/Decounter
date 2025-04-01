package com.mvnh.decounter.ui.navigation.counter

import com.arkivanov.decompose.ComponentContext

class ICounterComponent(
    componentContext: ComponentContext
) : CounterComponent, ComponentContext by componentContext
