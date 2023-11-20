package com.bewusstlos.moviessampleapp.ui.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import com.bewusstlos.moviessampleapp.ui.view.ui.theme.MoviesSampleAppTheme
import com.bewusstlos.moviessampleapp.ui.viewmodel.MovieDetailsViewModel
import com.bewusstlos.moviessampleapp.ui.viewmodel.viewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di
import org.kodein.di.instance
import coil.compose.AsyncImage

class MovieDetailsActivityCompose : ComponentActivity(), DIAware {
    companion object {
        const val MOVIE_ID = "movie_id"
        const val MOVIE_TITLE = "movie_title"
    }
    override val di: DI by di()
    private val viewModel: MovieDetailsViewModel by viewModel()
    private val posterPathPrefix: String by instance("moviePosterPrefix")

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesSampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxHeight(0.7f)) {

                        Image(bitmap = ImageBitmap(),
                            contentDescription = "movie_poster",
                            colorFilter = ColorFilter.tint(Color.Blue))

                    }
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesSampleAppTheme {
        Greeting("Android")
    }
}