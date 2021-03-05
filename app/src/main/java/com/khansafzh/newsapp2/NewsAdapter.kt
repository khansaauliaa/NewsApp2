package com.khansafzh.newsapp2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khansafzh.newsapp2.model.ArticlesItem
import kotlinx.android.synthetic.main.activity_news_item.view.*
import org.jetbrains.anko.intentFor

class NewsAdapter (var context: Context, var listNews : List<ArticlesItem?>?) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind (news: ArticlesItem){
            with(itemView){
                tv_title_items.text = news.title
                tv_date_item.text = news.publishedAt
                Glide.with(context).load(news.urlToImage).centerCrop().into(iv_item_news)
                itemView.setOnClickListener{
                    itemView.context.startActivity(
                        itemView.context.intentFor<DetailActivity>(
                            "EXTRA_NEWS" to news
                        )
                    )
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_news_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listNews!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNews?.get(position)!!)
    }

}
