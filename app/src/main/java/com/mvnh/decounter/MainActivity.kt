package com.mvnh.decounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.retainedComponent
import com.mvnh.decounter.ui.navigation.root.IRootComponent
import com.mvnh.decounter.ui.navigation.root.RootComponent
import com.mvnh.decounter.ui.navigation.root.RootContent
import com.mvnh.decounter.ui.theme.DecounterTheme

class MainActivity : ComponentActivity() {

    private lateinit var rootComponent: RootComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        rootComponent =
            retainedComponent { componentContext ->
                IRootComponent(
                    componentContext = componentContext
                )
            }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DecounterTheme {
                RootContent(
                    component = rootComponent
                )
            }
        }
    }
}
