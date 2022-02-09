package com.javier.bancodecomercio.data.network

import android.util.Log
import com.javier.bancodecomercio.data.model.ClientModel
import javax.inject.Inject

class ClientService @Inject constructor(
    private val api: ClientAPI
) {
    suspend fun getClients(): ArrayList<ClientModel>? {
        return try {
            val response = api.getListClient()
            response.body() ?: arrayListOf()
        } catch (ex: Exception) {
            Log.e("ClientService", "getClients: ${ex.message}")
            null
        }
    }

    suspend fun getClient(id: Int): ClientModel? {
        return try {
            val response = api.getClient(id)
            response.body()?.firstOrNull()
        } catch (ex: Exception) {
            Log.e("ClientService", "getClient: ${ex.message}")
            null
        }
    }
}