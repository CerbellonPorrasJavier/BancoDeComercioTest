package com.javier.bancodecomercio.data.network

import com.javier.bancodecomercio.data.model.ClientModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientAPI {
    @GET("/users")
    suspend fun getListClient (): Response<ArrayList<ClientModel>>

    @GET("/users")
    suspend fun getClient (@Query("id") id: Int) : Response<ClientModel>
}