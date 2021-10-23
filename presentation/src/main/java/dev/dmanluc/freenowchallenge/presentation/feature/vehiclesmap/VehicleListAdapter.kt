package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import dev.dmanluc.freenowchallenge.presentation.databinding.ItemVehicleListBinding

class VehicleListAdapter(
    private val onClickVehicle: ((Vehicle) -> Unit) = {}
) : RecyclerView.Adapter<VehicleItemViewHolder>() {

    private val items: MutableList<Vehicle> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleItemViewHolder {
        return VehicleItemViewHolder(
            ItemVehicleListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VehicleItemViewHolder, position: Int) {
        holder.bindVehicle(items[position], onClickVehicle)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(vehicleList: List<Vehicle>) {
        items.clear()
        items.addAll(vehicleList)

        notifyDataSetChanged()
    }
}