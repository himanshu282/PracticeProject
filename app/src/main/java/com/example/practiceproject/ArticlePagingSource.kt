package com.example.practiceproject

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practiceproject.api.APIInterface
import com.example.practiceproject.model.Article
import com.example.practiceproject.model.DataItem
import java.lang.Exception

class ArticlePagingSource(private val apiInterface: APIInterface): PagingSource<Int,Article>() {
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
            LoadResult.Page(
                data = response.articles,
                prevKey = if(currentPage==1) null else currentPage+1,
                nextKey = currentPage+1
            )
        } catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}