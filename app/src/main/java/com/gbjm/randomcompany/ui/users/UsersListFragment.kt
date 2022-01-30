package com.gbjm.randomcompany.ui.users

import android.app.AlertDialog
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

    private var listAdapter: UsersListAdapter? = null
    private var recycler : RecyclerView? = null
    private var progress: ProgressBar? = null

    private var swipeToRefresh: SwipeRefreshLayout? = null

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
            progress?.visibility = View.GONE
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
        swipeToRefresh = binding.swipeRefreshLayout

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
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("Are you sure you want to Delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        // Delete selected note from database
                        viewModel.onDeleteItem(user.id)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }

            override fun onUserFavoriteClicked(user: UiUserRow, checked: Boolean) {
                if (checked) {
                    viewModel.onAddFavorite(user.id)
                } else {
                    viewModel.onDeleteFavorite(user.id)
                }
            }
        })

        recycler?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler?.adapter = listAdapter

        progress?.visibility = View.VISIBLE

        swipeToRefresh?.setOnRefreshListener {
            swipeToRefresh?.isRefreshing = false
            viewModel.onRefresh()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.onListNeeded().collectLatest { users ->
                    listAdapter?.submitData(users)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onListNeeded().collectLatest { users ->
                listAdapter?.submitData(users)
            }
        }
    }

    fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}