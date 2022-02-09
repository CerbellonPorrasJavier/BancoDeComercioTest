package com.javier.bancodecomercio.domain

import com.javier.bancodecomercio.data.ClientRepository
import com.javier.bancodecomercio.data.model.ClientModel
import javax.inject.Inject

class GetListClientUseCase @Inject constructor(
    private val repository: ClientRepository
){
    suspend operator fun invoke(): ArrayList<ClientModel> = repository.getAllClients()
}