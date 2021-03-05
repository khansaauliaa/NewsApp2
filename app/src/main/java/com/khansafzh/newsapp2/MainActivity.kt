package com.khansafzh.newsapp2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.khansafzh.newsapp2.model.ResponseNews
import com.khansafzh.newsapp2.service.RetrofitConfig
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var refUser : DatabaseReference? = null
    var firebaseUser : FirebaseUser? = null

    companion object {
        fun getLaunchService (from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        ib_profile_main.setOnClickListener(this)
        getNews()

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUser = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser!!.uid)
        refUser!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (p0 in snapshot.children) {
                    val photo = snapshot.child("photo").value.toString()
                    Glide.with(this@MainActivity).load(photo).into(iv_profile_main)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun getNews() {
        val country ="id"
        val apiKey = "71313ee2db6847bcac94a4b5f419a042"

        val loading = ProgressDialog.show(this, "Request Data", "Loading...")
        RetrofitConfig.getInstace().getNewsData(country, apiKey).enqueue(
            object : retrofit2.Callback<ResponseNews>{
                override fun onResponse(
                    call: retrofit2.Call<ResponseNews>,
                    response: Response<ResponseNews>
                ) {
                    Log.d("Response", "Success" + response.body()?.articles)
                    loading.dismiss()
                    if (response.isSuccessful){
                        val status = response.body()?.status
                        if (status.equals("ok")){
                            Toast.makeText(this@MainActivity, "Data Success !", Toast.LENGTH_SHORT).show()
                            val newsData = response.body()?.articles
                            val newsAdapter = NewsAdapter(this@MainActivity, newsData)
                            rv_main.adapter = newsAdapter
                            rv_main.layoutManager = LinearLayoutManager(this@MainActivity)

                            val dataHighlight = response.body()
                            Glide.with(this@MainActivity).load(dataHighlight?.articles?.component5()?.urlToImage).centerCrop().into(iv_highlight)
                            tv_highlight.text = dataHighlight?.articles?.component5()?.title
                            tv_name_author.text = dataHighlight?.articles?.component5()?.author
                            tv_date_highlight.text = dataHighlight?.articles?.component5()?.publishedAt
                        }else{
                            Toast.makeText(this@MainActivity, "Data Failed !", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponseNews>, t: Throwable) {
                    Log.d("Response", "Failed : " + t.localizedMessage)
                    loading.dismiss()
                }

            }
        )
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.ib_profile_main -> startActivity(Intent(ProfileActivity.getLaunchService(this)))
        }


    }
}
