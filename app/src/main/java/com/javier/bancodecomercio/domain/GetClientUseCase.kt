package com.javier.bancodecomercio.domain

import com.javier.bancodecomercio.data.ClientRepository
import com.javier.bancodecomercio.data.model.ClientModel
import javax.inject.Inject

class GetClientUseCase @Inject constructor(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(id: Int): ClientModel? = repository.getClient(id)
}