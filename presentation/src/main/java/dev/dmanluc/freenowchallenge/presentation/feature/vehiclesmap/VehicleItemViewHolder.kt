package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.freenowchallenge.presentation.databinding.ItemVehicleListBinding
import dev.dmanluc.freenowchallenge.presentation.model.VehicleItem

class VehicleItemViewHolder(private val binding: ItemVehicleListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindVehicle(vehicle: VehicleItem, onClickVehicle: ((VehicleItem) -> Unit)) {
        binding.vehicleName.text = vehicle.fleetType.description
        binding.vehicleId.text = vehicle.id.toString()
        vehicle.iconResource?.let { binding.vehicleIcon.setImageResource(it) }

        binding.root.setOnClickListener {
            onClickVehicle(vehicle)
        }
    }

}