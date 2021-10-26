package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.updateLayoutParams
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import dagger.hilt.android.AndroidEntryPoint
import dev.dmanluc.freenowchallenge.presentation.R
import dev.dmanluc.freenowchallenge.presentation.custom.ErrorDataItem
import dev.dmanluc.freenowchallenge.presentation.databinding.ActivityMapsBinding
import dev.dmanluc.freenowchallenge.presentation.extensions.*
import dev.dmanluc.freenowchallenge.presentation.model.VehicleItem
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

        configureSplashAnimation(savedInstanceState)
    }

    private fun configureSplashAnimation(savedInstanceState: Bundle?) {
        val splashWasDisplayed = savedInstanceState != null
        if (!splashWasDisplayed) {
            val splashScreen = installSplashScreen()
            splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
                // Get logo and start a fade out animation
                splashScreenViewProvider.view
                    .animate()
                    .setDuration(300.toLong())
                    .alpha(0f)
                    .withEndAction {
                        // After the fade out, remove the
                        // splash and set content view
                        splashScreenViewProvider.remove()
                        setContentView(binding.root)
                        setupUi()
                    }.start()
            }
        } else {
            setTheme(R.style.Theme_FreeNowChallenge)
            setContentView(binding.root)
            setupUi()
        }
    }

    private fun setupUi() {
        configureRecyclerView()

        //makeStatusBarTransparent()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fetchVehiclePois()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureRecyclerView() {
        binding.bottomSheetView.vehiclesList.adapter = vehiclesAdapter
    }

    private fun fetchVehiclePois() {
        viewModel.getVehiclesFromMapBounds(VehiclesMapViewModel.mapBounds)
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

        configureBottomSheet()

        setupVehiclesObserver()
    }

    private fun setupVehiclesObserver() {
        viewModel.vehiclesStateLiveData.observe(this) { vehiclesMapState ->
            when (vehiclesMapState) {
                VehicleMapViewState.FirstLoading -> handleLoadingStatus()
                is VehicleMapViewState.EmptyVehiclesLoaded -> handleErrorState(errorMessageResId = vehiclesMapState.messageResId)
                is VehicleMapViewState.VehiclesLoaded -> handleVehiclesLoaded(vehiclesMapState)
                is VehicleMapViewState.VehiclesLoadedError -> handleErrorState(errorMessage = vehiclesMapState.errorMessage)
                is VehicleMapViewState.VehiclesLoadedConnectivityError -> handleErrorState(
                    errorMessageResId = vehiclesMapState.errorMessageResId
                )
            }
        }
    }

    private fun handleVehiclesLoaded(vehiclesMapState: VehicleMapViewState.VehiclesLoaded) {
        binding.bottomSheetView.loading.gone()
        binding.bottomSheetView.errorView.gone()

        vehiclesAdapter.setItems(vehiclesMapState.vehicleItemList)
        vehiclesMapState.vehicleItemList.forEach { item ->
            addVehicleMapMarker(item)
        }

        binding.bottomSheetView.dragIcon.visible()
        bottomSheetBehavior.isDraggable = true
    }

    private fun handleLoadingStatus() {
        binding.bottomSheetView.loading.visible()
        binding.bottomSheetView.errorView.gone()
        bottomSheetBehavior.isDraggable = false
    }

    private fun handleErrorState(
        errorMessage: String? = null,
        @StringRes errorMessageResId: Int? = null
    ) {
        val errorData = ErrorDataItem(
            errorMessageResId,
            errorMessage,
            onErrorAction = ::fetchVehiclePois
        )
        binding.bottomSheetView.loading.gone()
        binding.bottomSheetView.dragIcon.gone()
        binding.bottomSheetView.errorView.visible()
        binding.bottomSheetView.errorView.setData(errorData)
        bottomSheetBehavior.isDraggable = false
    }

    private fun addVehicleMapMarker(item: VehicleItem) {
        val mapMarkerOptions = MarkerOptions()
            .title(item.fleetType.description)
            .snippet(item.id.toString())
            .position(item.mapLatLng)
            .also {
                if (item.iconResource != null) {
                    it.icon(
                        bitmapDescriptorFromVector(
                            item.iconResource,
                            sizeInPx = 48.dpToPx
                        )
                    )
                    it.rotation(item.mapBearing.toFloat())
                }
            }

        map.addMarker(mapMarkerOptions)
    }

    private fun configureBottomSheet() {
        bottomSheetBehavior = from(binding.bottomSheetView.bottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
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

    }

    private fun onVehicleClicked(vehicle: VehicleItem) {
        centerZoomAt(map, vehicle.mapLatLng)
        bottomSheetBehavior.state = STATE_COLLAPSED
    }

    private fun centerZoomAt(googleMap: GoogleMap, coordinate: LatLng, zoomLevel: Int = 17) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, zoomLevel.toFloat()))
    }

    private fun resizeMapByBottomSheetOffset(offset: Float) {
        val maxHeightMap = binding.root.height - bottomSheetBehavior.peekHeight + 8.dpToPx
        binding.map.updateLayoutParams {
            height = ((1 - offset) * maxHeightMap).roundToInt()
        }
    }

}