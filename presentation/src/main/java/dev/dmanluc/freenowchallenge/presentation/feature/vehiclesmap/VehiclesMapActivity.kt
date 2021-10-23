package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_DRAGGING
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_SETTLING
import dagger.hilt.android.AndroidEntryPoint
import dev.dmanluc.freenowchallenge.domain.model.Vehicle
import dev.dmanluc.freenowchallenge.presentation.R
import dev.dmanluc.freenowchallenge.presentation.databinding.ActivityMapsBinding
import dev.dmanluc.freenowchallenge.presentation.extensions.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class VehiclesMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private val binding by viewBinding(ActivityMapsBinding::inflate)
    private val viewModel by viewModels<VehiclesMapViewModel>()

    private val vehiclesAdapter by lazy {
        VehicleListAdapter(::onVehicleClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        configureRecyclerView()

        //makeStatusBarTransparent()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getVehiclesFromMapBounds(VehiclesMapViewModel.mapBounds)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureRecyclerView() {
        binding.bottomSheetView.vehiclesList.adapter = vehiclesAdapter
    }

    private fun resizeMapByBottomSheetOffset(offset: Float) {
        val maxHeightMap = binding.root.height - bottomSheetBehavior.peekHeight + 8.dpToPx
        binding.map.updateLayoutParams {
            height = ((1 - offset) * maxHeightMap).roundToInt()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.apply {
            setLatLngBoundsForCameraTarget(VehiclesMapViewModel.mapBounds)
            val centerMapPosition = CameraPosition.Builder()
                .target(VehiclesMapViewModel.mapBounds.center)
                .zoom(11f)
                .tilt(30f)
                .build()
            animateCamera(CameraUpdateFactory.newCameraPosition(centerMapPosition))
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetView.bottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                when (bottomSheetBehavior.state) {
                    STATE_DRAGGING, STATE_SETTLING -> {
                        resizeMapByBottomSheetOffset(slideOffset)
                    }
                    else -> return
                }
            }
        })

        binding.interceptorLayout.apply {
            this.bottomSheetBehavior = this@VehiclesMapActivity.bottomSheetBehavior
            viewToIntercept = binding.bottomSheetView.vehiclesList
        }

        viewModel.vehiclesLiveData.observe(this) { vehicleList ->
            vehiclesAdapter.setItems(vehicleList)
            vehicleList.forEach {
                map.addMarker(MarkerOptions()
                    .title(it.fleetType.name)
                    .snippet(it.id.toString())
                    .position(it.getMapLocation()))
            }
        }
    }

    private fun onVehicleClicked(vehicle: Vehicle) {
        setBottomSheetVisibility(false)
        centerZoomAt(map, vehicle.getMapLocation())
    }

    private fun centerZoomAt(googleMap: GoogleMap, coordinate: LatLng, zoomLevel: Int = 17) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, zoomLevel.toFloat()))
    }

    private fun setBottomSheetVisibility(isVisible: Boolean) {
        val updatedState = if (isVisible) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_HALF_EXPANDED
        bottomSheetBehavior.state = updatedState
    }

}