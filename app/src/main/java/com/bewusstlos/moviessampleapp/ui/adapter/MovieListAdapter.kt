package com.bewusstlos.moviessampleapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bewusstlos.moviessampleapp.R
import com.bewusstlos.moviessampleapp.data.model.Movie
import com.bewusstlos.moviessampleapp.ui.view.MovieDetailsActivity
import com.bumptech.glide.Glide

class MovieListAdapter(private val context: Context, private val posterPathPrefix: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val ITEM_MOVIE = 0
        const val ITEM_LOADING = 1
        const val VISIBLE_THRESHOLD = 3

    }

    var isLoading = false
    private val data: MutableList<Movie> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == data.size)
            ITEM_LOADING
        else
            ITEM_MOVIE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_MOVIE -> MovieViewHolder(
                LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false)
            )
            ITEM_LOADING -> LoadingViewHolder(
                LayoutInflater.from(context).inflate(R.layout.loading_list_item, parent, false)
            )
            else -> throw IllegalArgumentException("No viewHolders matching viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            holder.itemView.apply {
                data[position].posterPath?.let { posterPath ->
                    Glide.with(context).load(posterPathPrefix + posterPath).into(findViewById(R.id.iv_poster))
                }
                findViewById<TextView>(R.id.tv_title).text = data[position].title
                findViewById<TextView>(R.id.tv_popularity).text = "Popularity: %.2f".format(data[position].popularity)

                setOnClickListener {
                    val intent = Intent(context, MovieDetailsActivity::class.java)
                    intent.putExtra(MovieDetailsActivity.MOVIE_ID, data[position].id)
                    intent.putExtra(MovieDetailsActivity.MOVIE_TITLE, data[position].title)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) data.size + 1 else data.size
    }

    fun addItems(items: List<Movie>) {
        val insertStartPos = data.size
        data.addAll(items)
        if(insertStartPos == 0)
            notifyDataSetChanged()
        else
            notifyItemRangeInserted(insertStartPos, insertStartPos + items.size)
        isLoading = false
    }

    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }

    fun isEmpty() = data.isEmpty()

    private class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)
    private class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}