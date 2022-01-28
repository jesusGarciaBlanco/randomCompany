package com.gbjm.randomcompany.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Base class for ViewHolders
 */
abstract class BaseViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}
