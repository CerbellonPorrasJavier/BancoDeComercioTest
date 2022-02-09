package com.javier.bancodecomercio.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.javier.bancodecomercio.databinding.ActivityClientDetailsBinding
import com.javier.bancodecomercio.ui.viewmodel.ClientViewModel

class ClientDetailsActivity : AppCompatActivity() {

    companion object {
        const val ID_CLIENT = "ID_CLIENT"
    }

    private lateinit var binding: ActivityClientDetailsBinding

    private val clientViewModel : ClientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clientViewModel.getClient(intent?.extras?.getInt(ID_CLIENT) ?: 0)
        with(binding) {

        }
    }
}