package com.gbjm.randomcompany

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.gbjm.randomcompany.navigation.NavigationFlow
import com.gbjm.randomcompany.navigation.ToFlowNavigable

class StartFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.initiateAppBtn)
        button.setOnClickListener {
            (requireActivity() as ToFlowNavigable).navigateToFlow(NavigationFlow.UsersFlow)
        }
    }
}