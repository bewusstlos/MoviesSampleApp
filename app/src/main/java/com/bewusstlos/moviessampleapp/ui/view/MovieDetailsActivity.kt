package com.bewusstlos.moviessampleapp.ui.view

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bewusstlos.moviessampleapp.R
import com.bewusstlos.moviessampleapp.data.model.MovieDetails
import com.bewusstlos.moviessampleapp.ui.viewmodel.MovieDetailsViewModel
import com.bewusstlos.moviessampleapp.ui.viewmodel.viewModel
import com.bumptech.glide.Glide
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.instance
import java.text.NumberFormat
import java.util.*


class MovieDetailsActivity : AppCompatActivity(), DIAware {
    companion object{
        const val MOVIE_ID = "movie_id"
        const val MOVIE_TITLE = "movie_title"
    }
    override val di: DI by di()
    val viewModel: MovieDetailsViewModel by viewModel()
    private val posterPathPrefix: String by instance("moviePosterPrefix")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        title = intent.getStringExtra(MOVIE_TITLE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initObservers()
        viewModel.getMovieDetails(intent.getIntExtra(MOVIE_ID, 0))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initObservers() {
        viewModel.movieLiveData.observe(this) { populateData(it) }
    }

    private fun populateData(movie: MovieDetails) {
        Glide.with(this).load(posterPathPrefix + movie.posterPath).into(findViewById(R.id.iv_poster))

        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("USD")
        findViewById<TextView>(R.id.tv_budget).text = "Budget: " + format.format(movie.budget)
        findViewById<TextView>(R.id.tv_revenue).text = "Revenue: " + format.format(movie.revenue)
        findViewById<TextView>(R.id.tv_status).text = "Status: ${movie.status}"
        findViewById<TextView>(R.id.tv_popularity).text =  "Popularity: %.2f".format(movie.popularity)
        findViewById<TextView>(R.id.tv_genres).text = "Genres: " + movie.genres.joinToString { it.name }
        movie.runtime?.let {
            findViewById<TextView>(R.id.tv_duration).text = "Duration: ${movie.runtime / 60} h ${movie.runtime % 60} m"
        }
        findViewById<TextView>(R.id.tv_companies).text = "Companies: " + movie.productionCompanies.joinToString { "${it.name}(${it.originCountry})"  }
        findViewById<TextView>(R.id.tv_countries).text = "Countries: " + movie.productionCountries.joinToString { it.name }
        findViewById<TextView>(R.id.tv_vote).text = "Rating: ${movie.voteAverage}(${movie.voteCount} votes)"
        findViewById<TextView>(R.id.tv_spoken_languages).text = "Spoken languages: " + movie.spokenLanguages.joinToString { it.name }
        movie.homePage?.let {
            findViewById<TextView>(R.id.tv_homepage).apply {
                text = it
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }
}