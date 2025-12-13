package com.example.agrosmart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.model.SMPost
import com.example.agrosmart.viewmodel.SocialMediaViewModel

class SMPostListAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val postListData: List<SMPost>,
    private val viewModel: SocialMediaViewModel
) : RecyclerView.Adapter<SMPostListAdapter.SMPostListViewModel>() {

    class SMPostListViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNamePostSM: TextView = itemView.findViewById(R.id.userNamePostSM)
        val userPostTitleValue: TextView = itemView.findViewById(R.id.userPostTitleValue)
        val userPostDescValue: TextView = itemView.findViewById(R.id.userPostDescValue)
        val userPostUploadTime: TextView = itemView.findViewById(R.id.userPostUploadTime)
        val postImageSM: ImageView = itemView.findViewById(R.id.postImageSM)
        val postVideoSM: WebView = itemView.findViewById(R.id.postVideoSM)
        val userProfileImageCard: CardView = itemView.findViewById(R.id.userProfileImageCard)
        val postContainer: ConstraintLayout = itemView.findViewById(R.id.post_container)
        val userProfileImagePost: ImageView = itemView.findViewById(R.id.userProfileImagePost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMPostListViewModel {
        val view = LayoutInflater.from(context).inflate(R.layout.post_with_image_sm, parent, false)
        return SMPostListViewModel(view)
    }

    override fun getItemCount(): Int {
        return postListData.size
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: SMPostListViewModel, position: Int) {
        val currentPost = postListData[position]

        holder.userNamePostSM.text = currentPost.name
        holder.userPostTitleValue.text = currentPost.title
        holder.userPostDescValue.text = currentPost.description
        holder.userPostUploadTime.text = DateUtils.getRelativeTimeSpanString(currentPost.timeStamp)

        when (currentPost.uploadType) {
            "video" -> {
                val webSet: WebSettings = holder.postVideoSM.settings
                webSet.javaScriptEnabled = true
                webSet.loadWithOverviewMode = true
                webSet.useWideViewPort = true

                holder.postVideoSM.webViewClient = object : WebViewClient() {}

                holder.postVideoSM.loadUrl(currentPost.imageUrl ?: "")
                holder.postImageSM.visibility = View.GONE
                holder.postVideoSM.visibility = View.VISIBLE
            }
            "image" -> {
                Glide.with(context).load(currentPost.imageUrl).into(holder.postImageSM)
                holder.postVideoSM.visibility = View.GONE
                holder.postImageSM.visibility = View.VISIBLE
            }
            else -> {
                holder.postImageSM.visibility = View.GONE
                holder.postVideoSM.visibility = View.GONE
            }
        }

        holder.userProfileImageCard.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition)
        holder.postContainer.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition)

        holder.userPostDescValue.setOnClickListener {
            holder.userPostDescValue.maxLines = Int.MAX_VALUE
        }

        viewModel.getUserProfileImage(currentPost.userID).observe(lifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Glide.with(context).load(it).into(holder.userProfileImagePost)
            }
        }
    }
}