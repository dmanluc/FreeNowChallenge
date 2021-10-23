package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import dev.dmanluc.freenowchallenge.presentation.databinding.ItemVehicleListBinding

class VehicleItemViewHolder(private val binding: ItemVehicleListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindVehicle(vehicle: Vehicle, onClickVehicle: ((Vehicle) -> Unit)) {
        binding.vehicleName.text = "Vehicle ${vehicle.id}"

        binding.root.setOnClickListener {
            onClickVehicle(vehicle)
        }
    }

}