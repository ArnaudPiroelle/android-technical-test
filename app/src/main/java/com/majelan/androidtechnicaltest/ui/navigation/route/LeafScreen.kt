package com.majelan.androidtechnicaltest.ui.navigation.route

import com.majelan.androidtechnicaltest.ui.navigation.TopLevelDestination

abstract class LeafScreen(private val route: String) {
    fun createRoute(root: TopLevelDestination) = "${root.route}/$route"
}