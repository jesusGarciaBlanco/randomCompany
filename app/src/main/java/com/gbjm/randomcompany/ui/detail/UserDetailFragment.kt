package com.gbjm.randomcompany.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.gbjm.core.architecture.extensions.viewModelProvider
import com.gbjm.randomcompany.R
import com.gbjm.randomcompany.base.BaseFragment
import com.gbjm.randomcompany.databinding.FragmentUserDetailBinding
import com.gbjm.randomcompany.ui.detail.entity.UiUserDetail
import com.squareup.picasso.Picasso
import javax.inject.Inject

class UserDetailFragment : BaseFragment<UserDetailViewModel, FragmentUserDetailBinding>() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserDetailViewModel

    private lateinit var image: ImageView
    private lateinit var name: TextView
    private lateinit var surname: TextView
    private lateinit var gender: TextView
    private lateinit var location: TextView
    private lateinit var registeredDate: TextView
    private lateinit var email: TextView

    /**
     * get the layout id
     * @return the layout Id
     */
    override fun getLayoutRes(): Int {
        return R.layout.fragment_user_detail
    }

    /**
     * get the view model
     * @return the view model
     */
    override fun getViewModel(): UserDetailViewModel {
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
        viewModel.uiUserDetail.observe(this, { detail ->
            renderHeader(detail)
        })

        viewModel.uiError.observe(this, {
            if (it!=null && it.isNotEmpty()) {
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
        binding.lifecycleOwner = this@UserDetailFragment

        image = binding.ivUserDetail
        name = binding.tvNameDetail
        surname = binding.tvSurnameDetail
        gender = binding.tvGenderDetail
        location = binding.tvLocationDetail
        registeredDate = binding.tvRegisteredDateDetail
        email = binding.tvEmailDetail


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("userId")?.let {
            viewModel.onUserDetailNeeded(it)
        }
    }

    fun renderHeader(detailHeader: UiUserDetail) {
        Picasso.get().isLoggingEnabled = true
        Picasso.get()
            .load(detailHeader.image)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.image_error)
            .into(image)

        name.text = detailHeader.name
        surname.text = detailHeader.surname
        gender.text = detailHeader.gender
        location.text = detailHeader.location
        registeredDate.text = detailHeader.registeredDate
        email.text = detailHeader.email

    }

    fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}