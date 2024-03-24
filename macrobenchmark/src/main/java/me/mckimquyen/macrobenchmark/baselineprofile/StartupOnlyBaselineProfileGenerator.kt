package com.mckimquyen.macrobenchmark.baselineprofile

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import com.mckimquyen.benchmark.macro.base.util.TARGET_PACKAGE
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalBaselineProfilesApi::class)
class StartupOnlyBaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun appStartupOnly() {
        baselineProfileRule.collectBaselineProfile(packageName = TARGET_PACKAGE) {
            startActivityAndWait()
        }
    }
}
