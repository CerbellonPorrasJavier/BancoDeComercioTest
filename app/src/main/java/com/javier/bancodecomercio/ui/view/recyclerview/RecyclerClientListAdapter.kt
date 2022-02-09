package com.javier.bancodecomercio.ui.view.recyclerview

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.bancodecomercio.R
import com.javier.bancodecomercio.data.model.ClientModel
import com.javier.bancodecomercio.databinding.ItemClientListBinding
import com.javier.bancodecomercio.ui.view.SplashActivity
import com.javier.bancodecomercio.ui.view.recyclerview.RecyclerClientListAdapter.CustomViewHolder

typealias ClientListener = ((ClientModel) -> Unit)?

class RecyclerClientListAdapter(
    private val items: ArrayList<ClientModel>,
    val clientListener: ClientListener = null
): RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(R.layout.item_client_list, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CustomViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = ItemClientListBinding.bind(itemView)

        fun bind(client: ClientModel) = with(binding) {
            with(client) {
                textAddressClient.text = address.street.plus(" ").plus(address.suite)
                textCompanyClient.text = company.name
                val myLocation = Location("My Location")
                myLocation.latitude = SplashActivity.myLatitude
                myLocation.longitude = SplashActivity.myLongitude
                val clientLocation = Location ("Client Location")
                val address = address
                val geo = address.geo
                clientLocation.latitude = geo.lat.toDouble()
                clientLocation.longitude = geo.lng.toDouble()
                val distance = (myLocation.distanceTo(clientLocation).toDouble()) / 1000.00
                textDistanceClient.text = String.format("%.2f km", distance)
                /*itemView.setOnClickListener {
                    val intentToDetail = Intent(context, ClientDetailsActivity::class.java)
                    intentToDetail.putExtra("IDClient", id)
                    ContextCompat.startActivity(context, intentToDetail, Bundle())
                }*/
                itemView.setOnClickListener { clientListener?.invoke(this) }
            }
        }
    }
}