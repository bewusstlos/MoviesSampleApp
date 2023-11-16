package com.bewusstlos.moviessampleapp.ui.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bewusstlos.moviessampleapp.R
import com.bewusstlos.moviessampleapp.ui.adapter.MovieListAdapter
import com.bewusstlos.moviessampleapp.ui.viewmodel.ListViewModel
import com.bewusstlos.moviessampleapp.ui.viewmodel.viewModel
import kotlinx.coroutines.*
import org.kodein.di.*
import org.kodein.di.android.di

class ListActivity : AppCompatActivity(), DIAware {
    override val di: DI by di()
    private val viewModel: ListViewModel by viewModel()
    private val posterPathPrefix: String by instance("moviePosterPrefix")
    lateinit var moviesAdapter: MovieListAdapter

    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchJob: Job? = null

    private lateinit var rvMovies: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var tvTotalFound: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initUI()
        initObservers()
        viewModel.loadMovies(null)
    }

    private fun initUI() {
        rvMovies = findViewById(R.id.rv_movies)
        etSearch = findViewById(R.id.et_search)
        swipeRefresh = findViewById(R.id.swipe_refresh)
        tvTotalFound = findViewById(R.id.tv_total_found)
        rvMovies.layoutManager = object : LinearLayoutManager(this) {
            //hotfix
            override fun onLayoutChildren(recycler: Recycler?, state: RecyclerView.State?) {
                try {
                    super.onLayoutChildren(recycler, state)
                } catch (e: IndexOutOfBoundsException) {
                    Log.e("TAG", "IOOBE in RecyclerView")
                }
            }
        }

        moviesAdapter = MovieListAdapter(this, posterPathPrefix)
        rvMovies.adapter = moviesAdapter
        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = (recyclerView.layoutManager as LinearLayoutManager).itemCount;
                val lastVisibleItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!moviesAdapter.isLoading && (totalItemCount <= (lastVisibleItem + MovieListAdapter.VISIBLE_THRESHOLD)) && !moviesAdapter.isEmpty()) {
                    viewModel.loadMovies(etSearch.text.toString())
                }
            }
        })

        swipeRefresh.setOnRefreshListener {
            hideKeyboard()
            clearData()
            etSearch.setText("")
            viewModel.loadMovies(if (etSearch.text.isNullOrEmpty()) null else etSearch.text.toString())
        }


        etSearch.doOnTextChanged { text, _, _, _ ->
            searchHandler.postDelayed({
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    clearData()
                    viewModel.loadMovies(if (text.isNullOrBlank()) null else text.toString())
                }
            }, 300)
        }

        rvMovies.requestFocus()
    }

    private fun initObservers() {
        viewModel.moviesLiveData.observe(this) {
            swipeRefresh.isRefreshing = false
            moviesAdapter.addItems(it)
        }

        viewModel.isLoadingLiveData.observe(this) {
            moviesAdapter.isLoading = it
        }

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.searchResultsCount.observe(this) {
            tvTotalFound.visibility = if (it != -1) View.VISIBLE else View.GONE
            tvTotalFound.text = "Total movies found: $it"
        }
    }

    private fun clearData() {
        moviesAdapter.clear()
        viewModel.clearData()
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}