package com.javier.bancodecomercio.domain

import com.javier.bancodecomercio.data.model.ClientModel
import com.javier.bancodecomercio.data.model.ClientProvider
import javax.inject.Inject

class GetClientsUseCase @Inject constructor(
    private val clientProvider: ClientProvider
){

    operator fun invoke(): ArrayList<ClientModel>? {
        val clients = clientProvider.clients
        return if (!clients.isNullOrEmpty()) {
            clients
        } else {
            null
        }
    }
}