package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.freenowchallenge.presentation.databinding.ItemVehicleListBinding
import dev.dmanluc.freenowchallenge.presentation.model.VehicleItem

class VehicleListAdapter(
    private val onClickVehicle: ((VehicleItem) -> Unit) = {}
) : RecyclerView.Adapter<VehicleItemViewHolder>() {

    private val items: MutableList<VehicleItem> = mutableListOf()

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

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(vehicleList: List<VehicleItem>) {
        items.clear()
        items.addAll(vehicleList)

        notifyDataSetChanged()
    }
}