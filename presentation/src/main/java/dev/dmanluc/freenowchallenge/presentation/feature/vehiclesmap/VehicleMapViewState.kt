package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import android.os.Parcelable
import dev.dmanluc.freenowchallenge.presentation.model.VehicleItem
import kotlinx.parcelize.Parcelize

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Definition of a hierachy of possible states to be shown in the vehicle list screen
 *
 */
sealed class VehicleMapViewState: Parcelable {
    @Parcelize object FirstLoading : VehicleMapViewState()
    @Parcelize object EmptyVehiclesLoaded : VehicleMapViewState()
    @Parcelize data class VehiclesLoaded(val vehicleItemList: List<VehicleItem>) : VehicleMapViewState()
    @Parcelize data class VehiclesLoadedError(val error: Throwable) : VehicleMapViewState()
}