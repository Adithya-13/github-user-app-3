package com.example.githubuserapp3.ui

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubuserapp3.R
import com.example.githubuserapp3.adapter.OnItemClickCallback
import com.example.githubuserapp3.adapter.SearchAdapter
import com.example.githubuserapp3.data.Items
import com.example.githubuserapp3.ui.DetailActivity.Companion.EXTRA_USERNAME
import com.example.githubuserapp3.viewModel.SearchViewModel
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener {

    companion object {
        const val STATE_TRUE = "state_true"
        const val STATE_FALSE = "state_false"
    }

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter
    private val tag = SearchActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backGroundColor()
        setContentView(R.layout.activity_search)
        showTextBase(true)

        searchViewConfig()
        configSearchViewModel(savedInstanceState)
        configRecyclerView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_TRUE, true)
        outState.putBoolean(STATE_FALSE, false)
    }

    private fun searchViewConfig() {
        searchView.setOnSearchActionListener(this)
    }

    override fun onButtonClicked(buttonCode: Int) {}

    override fun onSearchStateChanged(enabled: Boolean) {}

    override fun onSearchConfirmed(text: CharSequence?) {
        showLoading(true)
        showTextBase(false)

        searchViewModel.setSearchUser(text.toString())
        closeKeyboard()
    }

    private fun configSearchViewModel(savedInstanceState: Bundle?) {

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.getSearchUser().observe(this, Observer {

            if (it != null) {
                Log.d(tag, "setData")
                adapter.setData(it)
                if (savedInstanceState != null) {
                    showLoading(savedInstanceState.getBoolean(STATE_FALSE))
                    showTextBase(savedInstanceState.getBoolean(STATE_FALSE))
                } else {
                    showLoading(false)
                    showTextBase(false)
                }
            }
            if (it.isEmpty()) {
                if (savedInstanceState != null) {
                    showTextBase(savedInstanceState.getBoolean(STATE_TRUE))
                } else {
                    showTextBase(true)
                }
            }

        })

    }

    private fun configRecyclerView() {

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        val width = Resources.getSystem().displayMetrics.widthPixels

        searchRecyclerView.layoutManager = GridLayoutManager(applicationContext, width / 300)
        searchRecyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(items: Items) {
                showSelectedData(items)
            }
        })

    }

    private fun showSelectedData(items: Items) {

        val intentDetail = Intent(this, DetailActivity::class.java)
        intentDetail.putExtra(EXTRA_USERNAME, items.username)
        startActivity(intentDetail)
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
        Log.d(tag, "intent to Detail Activity")

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
            Log.d(tag, "show loading: true")
        } else {
            progressBar.visibility = View.GONE
            Log.d(tag, "show loading: false")
        }
    }

    private fun showTextBase(state: Boolean) {
        if (state) {
            tvSearchUser.visibility = View.VISIBLE
            lottieNotFound.visibility = View.VISIBLE
            Log.d(tag, "show text base: true")
        } else {
            tvSearchUser.visibility = View.GONE
            lottieNotFound.visibility = View.GONE
            Log.d(tag, "show text base: false")
        }
    }

    private fun closeKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        Log.d(tag, "close keyboard")
    }

    fun backButton(view: View) {
        onBackPressed()
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
        Log.d(tag, "backButton: onBackPressed")
    }

    private fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.splashscreen_background)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
    }
}