package com.gbjm.randomcompany.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gbjm.core.architecture.extensions.viewModelProvider
import com.gbjm.randomcompany.R
import com.gbjm.randomcompany.base.BaseFragment
import com.gbjm.randomcompany.databinding.FragmentListBinding
import javax.inject.Inject
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.gbjm.randomcompany.navigation.NavigationFlow
import com.gbjm.randomcompany.navigation.ToFlowNavigable
import com.gbjm.randomcompany.ui.users.adapter.UsersListAdapter
import com.gbjm.randomcompany.ui.users.entity.UiUserRow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class UsersListFragment : BaseFragment<UsersListViewModel, FragmentListBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UsersListViewModel

    private lateinit var listAdapter: UsersListAdapter
    private lateinit var recycler : RecyclerView
    private lateinit var progress: ProgressBar

    /**
     * get the layout id
     * @return the layout Id
     */
    override fun getLayoutRes(): Int {
        return R.layout.fragment_list
    }

    /**
     * get the view model
     * @return the view model
     */
    override fun getViewModel(): UsersListViewModel {
        viewModel = viewModelProvider(viewModelFactory)
        return viewModel
    }

    /**
     * Called to do initial creation of a fragment
     * @param savedInstanceState - If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()
        setupObservation()


    }

    /**
     * Observe the observables and perform the actions
     */
    private fun setupObservation() {
        viewModel.uiError.observe(this, Observer {
            progress.visibility = View.GONE
            if (it!=null && !it.isEmpty()) {
                showError(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this@UsersListFragment
        recycler = binding.recycler
        progress = binding.progressNetwork

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recycler)
        listAdapter = UsersListAdapter(object : UsersListAdapter.UserListener {
            override fun onUserPhotoClicked(user: UiUserRow) {
                //we should navigate from viewModel
                (requireActivity() as ToFlowNavigable).navigateToFlow(NavigationFlow.DetailsFlow(user.id))
            }

            override fun onUserDeleteClicked(user: UiUserRow) {
                TODO("Not yet implemented")
                Timber.d("delete button was clicked")
            }

            override fun onUserFavoriteClicked(user: UiUserRow, checked: Boolean) {
                TODO("Not yet implemented")
                Timber.d("favorite button was clicked")
            }
        })

        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler.adapter = listAdapter

        progress.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onListNeeded().collectLatest { users ->
//                render(users)
                listAdapter?.submitData(users)
            }
        }
    }

//    fun render(list: List<UiUserRow>) {
//        listAdapter.set(list)
//        listAdapter.listener(object : UsersListAdapter.UserListener {
//            override fun onUserPhotoClicked(user: UiUserRow) {
//                //we should navigate from viewModel
//                (requireActivity() as ToFlowNavigable).navigateToFlow(NavigationFlow.DetailsFlow(user.id))
//            }
//
//            override fun onUserDeleteClicked(user: UiUserRow) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onUserFavoriteClicked(user: UiUserRow, checked: Boolean) {
//                TODO("Not yet implemented")
//            }
//        })
//        val layoutManager = recycler.layoutManager
//        if (layoutManager is LinearLayoutManager) {
//            val linearLayoutManager = layoutManager as LinearLayoutManager?
//            linearLayoutManager?.scrollToPositionWithOffset(0, 0)
//        }
//    }

    fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}