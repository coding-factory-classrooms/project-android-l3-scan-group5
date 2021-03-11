package com.codingfactoryprojet.scanneropenfoodfact

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.codingfactoryprojet.scanneropenfoodfact.fragments.HistoryFragment
import com.codingfactoryprojet.scanneropenfoodfact.fragments.HomeFragment
import com.codingfactoryprojet.scanneropenfoodfact.fragments.ScanFragment

enum class MainScreen(@IdRes val menuItemId: Int,
                      @DrawableRes val menuItemIconId: Int,
                      @StringRes val titleStringId: Int,
                      val fragment: Fragment
) {
    HOME(R.id.action_home, R.drawable.baseline_document_scanner_black_18dp, R.string.bottom_navigation_menu_home, HomeFragment()),
    SCAN(R.id.action_scan, R.drawable.baseline_document_scanner_black_18dp, R.string.bottom_navigation_menu_scan, ScanFragment()),
    HISTORY(R.id.action_history, R.drawable.baseline_document_scanner_black_18dp, R.string.bottom_navigation_menu_history, HistoryFragment())
}

fun getMainScreenForMenuItem(menuItemId: Int): MainScreen? {
    for (mainScreen in MainScreen.values()) {
        if (mainScreen.menuItemId == menuItemId) {
            return mainScreen
        }
    }
    return null
}