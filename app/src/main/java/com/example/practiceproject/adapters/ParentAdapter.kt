package com.example.practiceproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceproject.ArticlePagingAdapter
import com.example.practiceproject.databinding.ParentItemBinding
import com.example.practiceproject.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParentAdapter(var data : PagingData<Article>) : RecyclerView.Adapter<ParentAdapter.MyViewHolder>() {
    private var shape = arrayListOf("circle" , "rectangle", "square")
//    private lateinit var article: List<Article>


    class MyViewHolder( var item: ParentItemBinding) : RecyclerView.ViewHolder(item.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        ParentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = shape[position]
        holder.item.typeName.text = currentItem

//        article = data
        val childAdapter = ArticlePagingAdapter(currentItem)
        holder.item.rvChild.layoutManager = LinearLayoutManager(holder.itemView.context,LinearLayoutManager.HORIZONTAL,false)
        holder.item.rvChild.adapter = childAdapter
        CoroutineScope(Dispatchers.IO).launch {
            childAdapter.submitData(data)
        }

    }

    override fun getItemCount(): Int = shape.size
}