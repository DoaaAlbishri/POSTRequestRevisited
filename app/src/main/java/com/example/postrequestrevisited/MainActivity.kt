package com.example.postrequestrevisited

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val words = arrayListOf<String>()
    lateinit var myRv : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // val textView = findViewById<TextView>(R.id.textView)
        val addNew = findViewById<Button>(R.id.button2)
        val userUD = findViewById<Button>(R.id.button3)
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        myRv = findViewById(R.id.recyclerView)
        //myRv.adapter = RecyclerViewAdapter(words)
        //myRv.layoutManager = LinearLayoutManager(this)
        //progress
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        val call: Call<List<Users.UserDetails>> = apiInterface!!.getUser()

        call?.enqueue(object : Callback<List<Users.UserDetails>> {
            override fun onResponse(
                    call: Call<List<Users.UserDetails>>,
                    response: Response<List<Users.UserDetails>>
            )
            {
                progressDialog.dismiss()
                val resource: List<Users.UserDetails>? = response.body()
                var userData:String? = "";
                for(User in resource!!){
                    userData = userData +User.name+ "\n"+User.location+ "\n"+"USER ID: "+User.pk + "\n" + "\n"
                }
            //    textView.text= userData
                words.add(userData.toString())
                rv()
            }
            override fun onFailure(call: Call<List<Users.UserDetails>>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, ""+t.message, Toast.LENGTH_SHORT).show();
            }
        })

        addNew.setOnClickListener {
                intent = Intent(applicationContext, MainActivity2::class.java)
                startActivity(intent)
        }
        userUD.setOnClickListener {
            intent = Intent(applicationContext, MainActivity3::class.java)
            startActivity(intent)
        }
        }
    fun rv(){
        myRv.adapter = RecyclerViewAdapter(words)
        myRv.layoutManager = LinearLayoutManager(this)
    }
  //  override fun onResume() {
    //    super.onResume()
     //   intent = Intent(applicationContext, MainActivity::class.java)
     //   startActivity(intent)
    //}
    }