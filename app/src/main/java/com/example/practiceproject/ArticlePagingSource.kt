package com.example.practiceproject

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practiceproject.api.APIInterface
import com.example.practiceproject.database.DatabaseBuilder
import com.example.practiceproject.database.entities.Images
import com.example.practiceproject.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ArticlePagingSource(private val apiInterface: APIInterface,var context: Context): PagingSource<Int,Article>() {
    private val articleDao = DatabaseBuilder.getInstance(context).articleDao()

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiInterface.getTeslaArticles(currentPage)
            CoroutineScope(Dispatchers.IO).launch {
                response.articles.forEach {
                            articleDao.insertArticle(com.example.practiceproject.database.entities.Article(title = it.title))
                            articleDao.insertImages(Images(0,it.url,it.urlToImage,it.title))
                            Log.d("TAG", "getArticleWithImages: ${articleDao.getArticleWithImages(it.title)}")
                }
            }

            Log.d("TAG", "load: ${response.articles}")
            LoadResult.Page(
                data = response.articles,
                prevKey = if(currentPage==1) null else currentPage-1,
                nextKey = currentPage+1
            )
        } catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}