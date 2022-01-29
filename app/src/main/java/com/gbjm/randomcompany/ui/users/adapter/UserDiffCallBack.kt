package com.gbjm.randomcompany.ui.users.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gbjm.randomcompany.ui.users.entity.UiUserRow

class UserDiffCallBack : DiffUtil.ItemCallback<UiUserRow>() {
    override fun areItemsTheSame(oldItem: UiUserRow, newItem: UiUserRow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UiUserRow, newItem: UiUserRow): Boolean {
        return oldItem == newItem
    }
}