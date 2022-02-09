package com.javier.bancodecomercio.data.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientProvider @Inject constructor() {
    var clients: ArrayList<ClientModel> = arrayListOf()
    var client: ClientModel? = null
}