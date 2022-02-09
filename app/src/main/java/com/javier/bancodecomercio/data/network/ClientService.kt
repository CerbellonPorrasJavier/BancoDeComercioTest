package com.javier.bancodecomercio.data.network

import com.javier.bancodecomercio.data.model.ClientModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClientService @Inject constructor(
    private val api: ClientAPI
) {
    suspend fun getClients(): ArrayList<ClientModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getListClient()
            response.body() ?: arrayListOf()
        }
    }

    suspend fun getClient(id: Int): ClientModel? {
        return withContext(Dispatchers.IO) {
            val response = api.getClient(id)
            response.body()
        }
    }
}