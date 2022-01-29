package com.gbjm.randomcompany.ui.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.gbjm.randomcompany.R
import com.gbjm.randomcompany.databinding.ItemUsersLoadStateFooterBinding

class UserLoadStateViewHolder (
    private val binding: ItemUsersLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnUsersRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvUsersErrorDescription.text = loadState.error.localizedMessage
        }
        binding.progressUsersLoadMore.isVisible = loadState is LoadState.Loading
        binding.btnUsersRetry.isVisible = loadState is LoadState.Error
        binding.tvUsersErrorDescription.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): UserLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_users_load_state_footer, parent, false)
            val binding = ItemUsersLoadStateFooterBinding.bind(view)
            return UserLoadStateViewHolder(binding, retry)
        }
    }
}