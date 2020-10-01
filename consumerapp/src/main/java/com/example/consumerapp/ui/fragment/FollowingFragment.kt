package com.example.consumerapp.ui.fragment

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.consumerapp.R
import com.example.consumerapp.adapter.FollowingAdapter
import com.example.consumerapp.ui.DetailActivity
import com.example.consumerapp.viewModel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var followingAdapter: FollowingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE

        val getUsername = activity?.intent?.getStringExtra(DetailActivity.EXTRA_USERNAME)
        if (getUsername != null) {
            configFollowingViewModel(getUsername)
        }
        configRecyclerView()

    }

    private fun configFollowingViewModel(getUsername: String) {
        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        followingViewModel.setFollowingUser(getUsername)
        followingViewModel.getFollowingUser().observe(activity!!, Observer {
            if (it != null) {
                followingAdapter.setFollowingData(it)
                progressBar.visibility = View.GONE
                lottieNotFound.visibility = View.GONE
            }
            if (it.isEmpty()) {
                progressBar.visibility = View.GONE
                lottieNotFound.visibility = View.VISIBLE
            }
        })
    }

    private fun configRecyclerView() {
        followingAdapter = FollowingAdapter()
        followingAdapter.notifyDataSetChanged()

        val width = Resources.getSystem().displayMetrics.widthPixels

        followingRecyclerView.layoutManager = GridLayoutManager(activity, width / 300)
        followingRecyclerView.adapter = followingAdapter
    }
}