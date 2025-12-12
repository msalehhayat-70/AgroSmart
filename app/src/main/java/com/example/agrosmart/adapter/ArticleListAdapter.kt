package com.project.farmingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.project.farmingapp.R
import com.project.farmingapp.utilities.CellClickListener

class ArticleListAdapter(
    private val articleListData: List<DocumentSnapshot>,
    private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<ArticleListAdapter.ArticleListViewholder>() {

    // Inner class for the ViewHolder to prevent potential memory leaks
    class ArticleListViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleName: TextView = itemView.findViewById(R.id.descTextxArticleListFrag)
        val articleImage: ImageView = itemView.findViewById(R.id.imageArticleListFrag)
        val articleCard: CardView = itemView.findViewById(R.id.articleListCardArtListFrag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewholder {
        // Inflate the layout using the parent's context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_list_single, parent, false)
        return ArticleListViewholder(view)
    }

    override fun getItemCount(): Int {
        return articleListData.size
    }

    override fun onBindViewHolder(holder: ArticleListViewholder, position: Int) {
        val singleArticle = articleListData[position]

        // Safely access Firestore data with a null-check
        val articleData = singleArticle.data
        if (articleData != null) {
            val title = articleData["title"] as? String ?: "No Title"
            holder.articleName.text = title

            holder.articleCard.setOnClickListener {
                cellClickListener.onCellClickListener(title)
            }

            // Safely cast the images list
            val images = articleData["images"] as? List<*>
            if (!images.isNullOrEmpty()) {
                val firstImage = images[0] as? String
                if (firstImage != null) {
                    Glide.with(holder.itemView.context)
                        .load(firstImage)
                        .into(holder.articleImage)
                }
            }
        }
    }
}
