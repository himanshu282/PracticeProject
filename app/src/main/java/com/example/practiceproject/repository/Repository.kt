package com.example.practiceproject.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import androidx.paging.map
import com.example.practiceproject.ArticlePagingSource
import com.example.practiceproject.api.APIInterface
import com.example.practiceproject.api.RetrofitBuilder
import com.example.practiceproject.database.DatabaseBuilder
import com.example.practiceproject.database.entities.Images
import com.example.practiceproject.database.relations.ArticleWithImages
import com.example.practiceproject.model.DataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class Repository(context: Context, private val apiInterface: APIInterface) {
    private val articleDao = DatabaseBuilder.getInstance(context).articleDao()

//    companion object{
//        fun getTeslaArticles(): Flow<DataItem> = flow{
//            val response = RetrofitBuilder.api.getTeslaArticles()
//            emit(response)
//        }.flowOn(Dispatchers.IO)
//    }

    fun getArticles() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = {ArticlePagingSource(apiInterface)}
    ).liveData


    fun insertArticleInDatabase(article: com.example.practiceproject.database.entities.Article){
        CoroutineScope(Dispatchers.IO).launch {
            articleDao.insertArticle(article)
        }
    }

    fun insertImagesInDatabase(images: Images){
        CoroutineScope(Dispatchers.IO).launch {
            articleDao.insertImages(images)
        }
    }

    suspend fun getArticlesWithImages(title:String) : List<ArticleWithImages> {
            return articleDao.getArticleWithImages(title)
    }


}