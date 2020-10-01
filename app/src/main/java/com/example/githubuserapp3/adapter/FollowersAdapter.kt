package com.example.githubuserapp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp3.R
import com.example.githubuserapp3.data.FollowersResponse
import kotlinx.android.synthetic.main.list_item.view.*

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    private val mData = ArrayList<FollowersResponse>()

    fun setFollowersData(items: ArrayList<FollowersResponse>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followersItems: FollowersResponse) {

            with(itemView) {
                usernameUser.text = followersItems.username
                typeUser.text = followersItems.type
                Glide.with(itemView.context)
                    .load(followersItems.avatar)
                    .into(avatarUser)
                itemView.setOnClickListener {
                    Toast.makeText(
                        context,
                        followersItems.username,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                val anim = AnimationUtils.loadAnimation(context, R.anim.recyclerview_anim)
                itemView.animation = anim
            }
        }
    }
}