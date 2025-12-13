package com.example.agrosmart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.model.Article
import com.example.agrosmart.utilities.CellClickListener

class ArticleListAdapter(
    private val articleListData: List<Article>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<ArticleListAdapter.ArticleListViewHolder>() {

    class ArticleListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleName: TextView = itemView.findViewById(R.id.descTextxArticleListFrag)
        val articleImage: ImageView = itemView.findViewById(R.id.imageArticleListFrag)
        val articleCard: CardView = itemView.findViewById(R.id.articleListCardArtListFrag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_list_single, parent, false)
        return ArticleListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleListData.size
    }

    override fun onBindViewHolder(holder: ArticleListViewHolder, position: Int) {
        val singleArticle = articleListData[position]

        holder.articleName.text = singleArticle.title

        holder.articleCard.setOnClickListener {
            cellClickListener.onCellClickListener(singleArticle.title)
        }

        if (singleArticle.images.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(singleArticle.images[0])
                .into(holder.articleImage)
        }
    }
}
