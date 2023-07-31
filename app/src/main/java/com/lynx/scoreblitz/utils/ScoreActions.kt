package com.lynx.scoreblitz.utils

sealed class NavActions {
    object OnDashboard : NavActions()
    object OnNews : NavActions()
    object OnWatch : NavActions()
}
