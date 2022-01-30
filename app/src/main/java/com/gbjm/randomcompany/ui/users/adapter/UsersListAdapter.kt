package com.gbjm.randomcompany.ui.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.paging.PagingDataAdapter
import com.gbjm.randomcompany.R
import com.gbjm.randomcompany.base.BaseViewHolder
import com.gbjm.randomcompany.ui.users.entity.UiUserRow
import com.squareup.picasso.Picasso

class UsersListAdapter(private val listener: UserListener) : PagingDataAdapter<UiUserRow, UsersListAdapter.ListViewHolder>(UserDiffCallBack()) {

    interface UserListener {
        fun onUserPhotoClicked(user: UiUserRow)

        fun onUserDeleteClicked(user: UiUserRow)

        fun onUserFavoriteClicked(user: UiUserRow, checked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_row, parent, false)
        return ListViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       with (getItem(position)) {
            holder.bind(this!!)
        }
    }

    class ListViewHolder(view: View, private val listener: UserListener?) : BaseViewHolder<UiUserRow>(view) {
        var image: ImageView = view.findViewById(R.id.ivUser)
        var name: TextView = view.findViewById(R.id.tvName)
        var surname: TextView = view.findViewById(R.id.tvSurname)
        var email: TextView = view.findViewById(R.id.tvEmail)
        var phone: TextView = view.findViewById(R.id.tvPhone)
        var deleteBtn: ImageButton = view.findViewById(R.id.btnDelete)
        var favoriteTggl: ToggleButton = view.findViewById(R.id.tgglFavorite)
        var parent: View = view.findViewById(R.id.parent)


        override fun bind(item: UiUserRow) {
            favoriteTggl.setOnCheckedChangeListener(null)
            favoriteTggl.isChecked = item.isFavorite

            Picasso.get().cancelRequest(image)
            if (item.image.isNotEmpty()) {

                Picasso.get().isLoggingEnabled = true
                Picasso.get()
                    .load(item.image)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.image_error)
                    .into(image)
            } else {
                image.setImageResource(R.drawable.image_error)
            }
            name.text = item.name
            surname.text = item.surname
            email.text = item.email
            phone.text = item.phone

            image.setOnClickListener {
                listener?.onUserPhotoClicked(item)
            }

            deleteBtn.setOnClickListener {
                listener?.onUserDeleteClicked(item)
            }

            favoriteTggl.setOnCheckedChangeListener { _, b ->
                //we have to change ui isFavorite manually to keep the state of the toggleButton
                //in recycleview cause the changes in the database favorite table
                //doesnt provoque a change in the model
                item.isFavorite = b
                listener?.onUserFavoriteClicked(item, b)
            }
        }
    }

}
