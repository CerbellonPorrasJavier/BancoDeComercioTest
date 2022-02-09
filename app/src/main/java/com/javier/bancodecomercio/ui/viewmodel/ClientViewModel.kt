package com.javier.bancodecomercio.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javier.bancodecomercio.data.model.ClientModel
import com.javier.bancodecomercio.domain.GetClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val getClientUseCase: GetClientUseCase
) : ViewModel() {

    val clientModel = MutableLiveData<ClientModel>()
    val isLoading = MutableLiveData<Boolean>()

    fun getClient(id: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getClientUseCase(id)
            result?.let {
                clientModel.postValue(it)
                isLoading.postValue(false)
            }
        }
    }
}