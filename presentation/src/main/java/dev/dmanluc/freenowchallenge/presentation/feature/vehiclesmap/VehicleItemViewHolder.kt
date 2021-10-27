package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.freenowchallenge.presentation.databinding.ItemVehicleListBinding
import dev.dmanluc.freenowchallenge.presentation.model.VehicleItem

class VehicleItemViewHolder(private val binding: ItemVehicleListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindVehicle(vehicle: VehicleItem, onClickVehicle: ((VehicleItem) -> Unit)) {
        with(binding) {
            vehicleName.text = vehicle.fleetType.description
            vehicleId.text = vehicle.id
            vehicle.iconResource?.let { vehicleIcon.setImageResource(it) }

            root.setOnClickListener {
                onClickVehicle(vehicle)
            }
        }
    }

}