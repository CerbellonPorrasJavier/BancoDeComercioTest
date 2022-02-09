package com.javier.bancodecomercio.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.javier.bancodecomercio.databinding.ActivityClientDetailsBinding
import com.javier.bancodecomercio.ui.viewmodel.ClientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        with(binding) {
            val id = intent.extras?.getInt(ID_CLIENT)
            clientViewModel.isLoading.observe(this@ClientDetailsActivity, {
                progressBar.isVisible = it
            })
            id?.let { clientViewModel.getClient(it) }
            clientViewModel.clientModel.observe(this@ClientDetailsActivity, { client ->
                textToolbarCompany.text = client.company.name
                textNameClient.text = client.name
                textUsernameClient.text = client.username
                textEmailClient.text = client.email
                textAddressClient.text = client.address.street.plus(" ").plus(client.address.suite)
                textCityClient.text = client.address.city
                textZipcodeClient.text = client.address.zipcode
                textPhoneClient.text = client.phone
                textWebsiteClient.text = client.website
                textCompanyClient.text = client.company.name
                textCatchphraseClient.text = client.company.catchPhrase
                textBsClient.text = client.company.bs
            })
            backArrow.setOnClickListener {
                onBackPressed()
            }
        }
    }
}