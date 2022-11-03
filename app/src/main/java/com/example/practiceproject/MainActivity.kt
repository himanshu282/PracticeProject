package com.example.practiceproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.practiceproject.adapters.ParentAdapter
import com.example.practiceproject.database.entities.Images
import com.example.practiceproject.model.Article
import com.example.practiceproject.viewmodel.ArticleViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var parentAdapter: ParentAdapter
    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        recyclerView = findViewById(R.id.rvParent)
        mShimmerViewContainer?.startShimmer()
        setupFirebase()
        articleViewModel= ViewModelProvider(this)[ArticleViewModel::class.java]
//        articleViewModel.getTeslaArticle()

        setData()



        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        pullToRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                recyclerView.visibility = View.GONE
                mShimmerViewContainer?.visibility = View.VISIBLE
                mShimmerViewContainer?.startShimmer()
                delay(2000)
                setData()
            }

            pullToRefresh.isRefreshing = false
        }

    }

//fun setInDb()
//{
//    articleViewModel.article?.observe(this){
//        val article = com.example.practiceproject.database.entities.Article(it.title)
//        val images = Images(0,it.url,it.urlToImage,it.title)
//        articleViewModel.insertArticle(article,this)
//        articleViewModel.insertImages(images,this)
//        articleViewModel.getArticleImages(it.title,this)
//    }
//}

    private fun setData(){
        articleViewModel.getData(this).observe(this) {
            Log.d("TAG", "onCreate: $it")
            mShimmerViewContainer?.stopShimmer()
            mShimmerViewContainer?.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            setupRecyclerView(it)


//            it.forEach { new ->
//                val images = Images(0,new.url,new.urlToImage,new.title)
//                val article = com.example.practiceproject.database.entities.Article(new.title)
//                articleViewModel.insertArticle(article,this)
//                articleViewModel.insertImages(images,this)
//                articleViewModel.getArticleImages(new.title,this)
//
//                Log.d("TAG", "onCreate: ${new.author}")
//            }

        }


//        articleViewModel.responseArticles.observe(this) {
//            Log.d("TAG", "onCreate: $it")
//            mShimmerViewContainer?.stopShimmer()
//            mShimmerViewContainer?.visibility = View.GONE
//            recyclerView.visibility = View.VISIBLE
//            setupRecyclerView(it)
//
//            it.forEach { new ->
//                val images = Images(0,new.url,new.urlToImage,new.title)
//                val article = com.example.practiceproject.database.entities.Article(new.title)
//                articleViewModel.insertArticle(article,this)
//                articleViewModel.insertImages(images,this)
//                articleViewModel.getArticleImages(new.title,this)
//
//                Log.d("TAG", "onCreate: ${new.author}")
//            }
//        }
    }

    private fun setupRecyclerView(data : PagingData<Article>){
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        parentAdapter = ParentAdapter(data)
        recyclerView.adapter = parentAdapter
//        parentAdapter.submitData(lifecycle,data)

    }

    private fun setupFirebase(){

        val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_data)

        mFirebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("TAG", "\"Config params updated: ${mFirebaseRemoteConfig.getString("json_test")}\" ")
                    val jsonTest = mFirebaseRemoteConfig.getString("json_test")
                    val obj = JSONObject(jsonTest)
                    val arr = obj.getJSONArray("type")


                    for (i in 0 until arr.length()){
                        val ob = arr.getJSONObject(i)

                        val iter: Iterator<String> = ob.keys()
                        while (iter.hasNext()) {
                            val key = iter.next()
                            try {
                                val value: Any = ob.get(key)
                                Log.d("TAG", "setupFisdfsrebase: $key")
                            } catch (e: JSONException) {
                                // Something went wrong!
                            }
                        }

                        if (ob.has("circle") ) {
                            Log.d("TAG", "setupFirebase: ${ob.getString("circle")}")
                            val internal = ob.getJSONObject("circle").getString("endpoint")
                            Log.d("TAG", "setupFirebase: $internal")
                        }
                        else
                            Log.d("TAG", "setupFirebase: nothing")

                    }
                    Toast.makeText(
                        this@MainActivity, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity, "Fetch failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
//                displayWelcomeMessage()
            }
    }
}