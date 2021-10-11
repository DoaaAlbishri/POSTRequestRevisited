package com.example.postrequestrevisited

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("/test/")
    fun getUser(): Call<List<Users.UserDetails>>

    @POST("/test/")
    fun addUser(@Body userData: Users.UserDetails): Call<Users.UserDetails>

    @PUT("/test/{id}")
    fun updateUser(@Path("id") id : Int , @Body userData: Users.UserDetails): Call<Users.UserDetails>

    @DELETE("/test/{id}")
    fun deleteUser(@Path("id") id : Int ): Call<Void>
}