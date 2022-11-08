package com.example.practiceproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceproject.R
import com.example.practiceproject.databinding.ChildItemBinding
import com.example.practiceproject.model.Article

class ChildAdapter(private var articles : List<Article>,var shape:String) : RecyclerView.Adapter<ChildAdapter.MyViewHolder>() {

    class MyViewHolder( var item: ChildItemBinding) : RecyclerView.ViewHolder(item.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        ChildItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = articles[position]
        if(shape == "rectangle")
            holder.item.authorName.setBackgroundResource(R.drawable.rectangle_shape)
        else if (shape == "square")
            holder.item.authorName.setBackgroundResource(R.drawable.square_shape)

        holder.item.authorName.text = currentItem.author
    }

    override fun getItemCount(): Int = articles.size
}