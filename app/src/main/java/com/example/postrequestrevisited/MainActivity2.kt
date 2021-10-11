package com.example.postrequestrevisited

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val name = findViewById<View>(R.id.edName) as EditText
        val location = findViewById<View>(R.id.edLoc) as EditText
        val savebtn = findViewById<View>(R.id.btsave) as Button
        val view = findViewById<Button>(R.id.btview)
        view.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        savebtn.setOnClickListener {
            //val random = Random(nextInt)
            var user = Users.UserDetails(name.text.toString(), location.text.toString(),Random.nextInt(0, 200))

            addUserdata(user, onResult = {
                name.setText("")
                location.setText("")
            })
        }
    }

    private fun addUserdata(user: Users.UserDetails, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.addUser(user).enqueue(object : Callback<Users.UserDetails> {
                override fun onResponse(
                        call: Call<Users.UserDetails>,
                        response: Response<Users.UserDetails>
                ) {
                    onResult()
                    Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<Users.UserDetails>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }
}