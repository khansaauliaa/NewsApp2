package com.khansafzh.newsapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.khansafzh.newsapp2.model.ArticlesItem
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.hide()
        fb_arrowback.setOnClickListener { startActivity(Intent(MainActivity.getLaunchService(this))) }
        val news = intent.getParcelableExtra<ArticlesItem>("EXTRA_NEWS") as ArticlesItem
        Glide.with(this).load(news.urlToImage).into(iv_detail)
        tv_title1.text = news.title.toString()
        tv_name_detail.text = news.author.toString()
        tv_date_detail.text = news.publishedAt.toString()
        tv_description_detail.text = news.description.toString()
        tv_content_detail.text = news.content.toString()


    }
}