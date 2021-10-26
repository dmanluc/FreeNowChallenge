package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import android.os.Parcelable
import androidx.annotation.StringRes
import dev.dmanluc.freenowchallenge.presentation.model.VehicleItem
import kotlinx.parcelize.Parcelize

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Definition of a hierachy of possible states to be shown in the vehicle list screen
 *
 */
sealed class VehicleMapViewState : Parcelable {
    @Parcelize
    object FirstLoading : VehicleMapViewState()
    @Parcelize
    data class EmptyVehiclesLoaded(@StringRes val messageResId: Int) : VehicleMapViewState()
    @Parcelize
    data class VehiclesLoaded(val vehicleItemList: List<VehicleItem>) : VehicleMapViewState()
    @Parcelize
    data class VehiclesLoadedError(val errorMessage: String) : VehicleMapViewState()
    @Parcelize
    data class VehiclesLoadedConnectivityError(@StringRes val errorMessageResId: Int) :
        VehicleMapViewState()
}