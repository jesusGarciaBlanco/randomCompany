package com.gbjm.randomcompany

import android.os.Bundle
import androidx.navigation.findNavController
import com.gbjm.randomcompany.navigation.NavigationFlow
import com.gbjm.randomcompany.navigation.Navigator
import com.gbjm.randomcompany.navigation.ToFlowNavigable
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity(), ToFlowNavigable {
    private val navigator: Navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.navController = findNavController(R.id.mainHostFragment)
    }

    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }
}