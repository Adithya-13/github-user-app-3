package com.example.githubuserapp3.ui

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp3.R
import com.example.githubuserapp3.adapter.ViewPagerAdapter
import com.example.githubuserapp3.data.entity.Favorite
import com.example.githubuserapp3.db.DatabaseContract.NoteColumns.Companion.AVATAR
import com.example.githubuserapp3.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.example.githubuserapp3.db.DatabaseContract.NoteColumns.Companion.TYPE
import com.example.githubuserapp3.db.DatabaseContract.NoteColumns.Companion.USERNAME
import com.example.githubuserapp3.db.DatabaseContract.NoteColumns.Companion.USER_ID
import com.example.githubuserapp3.viewModel.DetailViewModel
import com.example.githubuserapp3.viewModel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_search.progressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private val tag = DetailActivity::class.simpleName
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favorite: Favorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backGroundColor()
        setContentView(R.layout.activity_detail)
        showLoading(true)

        val getUsername = intent.getStringExtra(EXTRA_USERNAME)

        configDetailViewModel(getUsername)
        configViewPager()

    }

    private fun checkFavButton(favorite: Favorite) {

        val uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + favorite.userId)
        favoriteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FavoriteViewModel::class.java)

        favoriteViewModel.getQueryById(this, uriWithId).observe(this, Observer {

            if (it.isNullOrEmpty()) {
                Log.d(tag, "favButton")
                favButton.setBackgroundResource(R.drawable.ripple_fav)
                favButton()
            } else {
                Log.d(tag, "unFavButton")
                favButton.setBackgroundResource(R.drawable.ripple_unfav)
                unFavButton(uriWithId)
            }

        })
    }

    private fun favButton() {

        favButton.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                val values = ContentValues()
                values.put(USER_ID, favorite.userId)
                values.put(USERNAME, favorite.username)
                values.put(TYPE, favorite.type)
                values.put(AVATAR, favorite.avatar)
                contentResolver?.insert(CONTENT_URI, values)

            }

            favButton.setBackgroundResource(R.drawable.ripple_unfav)
            val toast = Toast.makeText(
                this,
                getString(R.string.add_user_fav, favorite.username),
                Toast.LENGTH_SHORT
            )
            toast.show()
            toast.setGravity(Gravity.CENTER, 0, 0)
            Log.d(tag, "favored")
            checkFavButton(favorite)

        }

    }

    private fun unFavButton(uriWithId: Uri) {

        favButton.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                contentResolver?.delete(uriWithId, null, null)
            }
            favButton.setBackgroundResource(R.drawable.ripple_fav)
            val toast = Toast.makeText(
                this,
                getString(R.string.remove_user_fav, favorite.username),
                Toast.LENGTH_SHORT
            )
            toast.show()
            toast.setGravity(Gravity.CENTER, 0, 0)
            checkFavButton(favorite)

        }

    }

    private fun configDetailViewModel(getUsername: String?) {

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(getUsername!!)
        detailViewModel.getDetailUser().observe(this, Observer {

            Log.d(tag, "binding")
            usernameDetail.text = getUsername
            nameDetail.text = it?.name ?: "-"
            companyDetail.text = it?.company ?: "-"
            locationDetail.text = it?.location ?: "-"
            repositoryDetail.text = getString(R.string.repository, it?.repository)
            followersDetail.text = getString(R.string.followers_d, it?.followers)
            followingDetail.text = getString(R.string.following_d, it?.following)
            Glide.with(this)
                .load(it?.avatar)
                .into(avatarDetail)
            Glide.with(this)
                .load(it?.avatar)
                .into(avatarDetailBackground)
            if (it != null) {
                favorite = Favorite(
                    it.userId,
                    it.username,
                    it.type,
                    it.avatar
                )
                checkFavButton(favorite)
            }
            showLoading(false)
        })
    }

    private fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.splashscreen_background)
    }

    private fun showLoading(state: Boolean) {

        if (state) {
            progressBar.visibility = View.VISIBLE
            avatarDetail.visibility = View.INVISIBLE
            nameDetail.visibility = View.INVISIBLE
            companyDetail.visibility = View.INVISIBLE
            locationDetail.visibility = View.INVISIBLE
            repositoryDetail.visibility = View.INVISIBLE
            followersDetail.visibility = View.INVISIBLE
            followingDetail.visibility = View.INVISIBLE
            tabs.visibility = View.INVISIBLE
            viewPager.visibility = View.INVISIBLE
            Log.d(tag, "show loading: true")
        } else {
            progressBar.visibility = View.GONE
            avatarDetail.visibility = View.VISIBLE
            nameDetail.visibility = View.VISIBLE
            companyDetail.visibility = View.VISIBLE
            locationDetail.visibility = View.VISIBLE
            repositoryDetail.visibility = View.VISIBLE
            followersDetail.visibility = View.VISIBLE
            followingDetail.visibility = View.VISIBLE
            tabs.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
            Log.d(tag, "show loading: false")
        }

    }

    private fun configViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    fun backButton(view: View) {
        onBackPressed()
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
        Log.d(tag, "backButton: onBackPressed")
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
    }
}