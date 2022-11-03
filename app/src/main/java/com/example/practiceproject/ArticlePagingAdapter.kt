package com.example.practiceproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceproject.databinding.ChildItemBinding
import com.example.practiceproject.model.Article

class ArticlePagingAdapter(var shapeData:String) : PagingDataAdapter<Article,ArticlePagingAdapter.ArticleViewHolder>(
    COMPARATOR) {

    private var shape = arrayListOf("circle" , "rectangle", "square")
//    private lateinit var article: ArrayList<Article?>
    class ArticleViewHolder( var item: ChildItemBinding) : RecyclerView.ViewHolder(item.root) {

    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(shapeData == "rectangle")
            holder.item.authorName.setBackgroundResource(R.drawable.rectangle_shape)
        else if (shapeData == "square")
            holder.item.authorName.setBackgroundResource(R.drawable.square_shape)

        holder.item.authorName.text = currentItem?.author
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder = ArticleViewHolder(
        ChildItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.author == newItem.author
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    }
}