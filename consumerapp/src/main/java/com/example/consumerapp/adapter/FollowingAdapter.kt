package com.example.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.consumerapp.R
import com.example.consumerapp.data.FollowingResponse
import kotlinx.android.synthetic.main.list_item.view.*

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    private val mData = ArrayList<FollowingResponse>()

    fun setFollowingData(item: ArrayList<FollowingResponse>) {
        mData.clear()
        mData.addAll(item)
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
        fun bind(followingItems: FollowingResponse) {
            with(itemView) {
                usernameUser.text = followingItems.username
                typeUser.text = followingItems.type
                Glide.with(itemView.context)
                    .load(followingItems.avatar)
                    .into(avatarUser)

                itemView.setOnClickListener {
                    Toast.makeText(
                        context,
                        followingItems.username,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                val anim = AnimationUtils.loadAnimation(context, R.anim.recyclerview_anim)
                itemView.animation = anim
            }
        }
    }
}