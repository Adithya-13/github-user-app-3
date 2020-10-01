package com.example.githubuserapp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp3.R
import com.example.githubuserapp3.data.entity.Favorite
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var favorites = ArrayList<Favorite>()
        set(favorite) {
            this.favorites.clear()
            this.favorites.addAll(favorite)
            notifyDataSetChanged()
        }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount() = favorites.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite) {
            with(itemView) {
                usernameUser.text = favorite.username
                typeUser.text = favorite.type
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .into(avatarUser)

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(favorite) }

                val anim = AnimationUtils.loadAnimation(context, R.anim.recyclerview_anim)
                itemView.animation = anim
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(favorite: Favorite)
    }
}