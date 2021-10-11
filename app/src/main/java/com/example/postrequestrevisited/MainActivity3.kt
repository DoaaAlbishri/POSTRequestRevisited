package com.example.postrequestrevisited

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val id = findViewById<View>(R.id.edId) as EditText
        val name = findViewById<View>(R.id.edName) as EditText
        val location = findViewById<View>(R.id.edLoc) as EditText
        val update = findViewById<View>(R.id.btupdate) as Button
        val delete = findViewById<View>(R.id.btdelete) as Button

        update.setOnClickListener {
            var user = Users.UserDetails(
                name.text.toString(), location.text.toString(), id.text.toString().toInt())

            updateUserdata(user, onResult = {
                id.setText("")
                name.setText("")
                location.setText("")
            })
        }

        delete.setOnClickListener {
            var user = Users.UserDetails(name.text.toString(), location.text.toString(), id.text.toString().toInt())

            deleteUserdata(user, onResult = {
                id.setText("")
                name.setText("")
                location.setText("")
            })
        }
    }

    private fun updateUserdata(user: Users.UserDetails, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.updateUser(user.pk!!,user).enqueue(object : Callback<Users.UserDetails> {
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

    private fun deleteUserdata(user: Users.UserDetails, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.deleteUser(user.pk!!).enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    onResult()
                    Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}