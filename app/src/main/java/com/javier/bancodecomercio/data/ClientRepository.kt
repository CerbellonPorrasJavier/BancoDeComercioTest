package com.javier.bancodecomercio.data

import com.javier.bancodecomercio.data.model.ClientModel
import com.javier.bancodecomercio.data.model.ClientProvider
import com.javier.bancodecomercio.data.network.ClientService
import javax.inject.Inject

class ClientRepository @Inject constructor(
    private val api: ClientService,
    private val clientProvider: ClientProvider
){
    suspend fun getAllClients(): ArrayList<ClientModel>? {
        val response = api.getClients()
        if (!response.isNullOrEmpty()) {
            clientProvider.clients = response
        }
        return response
    }

    suspend fun getClient(id: Int): ClientModel? {
        val response = api.getClient(id)
        clientProvider.client = response
        return response
    }
}