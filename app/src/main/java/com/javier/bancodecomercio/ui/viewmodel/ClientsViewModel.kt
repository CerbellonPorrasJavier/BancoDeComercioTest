package com.javier.bancodecomercio.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javier.bancodecomercio.data.model.ClientModel
import com.javier.bancodecomercio.domain.GetClientsUseCase
import com.javier.bancodecomercio.domain.GetListClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getListClientUseCase: GetListClientUseCase,
    private val getClientsUseCase: GetClientsUseCase
): ViewModel() {

    val listClientModel = MutableLiveData<ArrayList<ClientModel>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getListClientUseCase()
            if (!result.isNullOrEmpty()) {
                listClientModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }

    fun getRefreshClients () {
        val clients = getClientsUseCase()
        clients?.let {
            listClientModel.postValue(it)
        }
    }
}