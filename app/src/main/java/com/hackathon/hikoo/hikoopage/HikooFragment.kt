package com.hackathon.hikoo.hikoopage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

import com.hackathon.hikoo.R
import com.hackathon.hikoo.maincontainer.MainActivity
import com.hackathon.hikoo.model.domain.Shelter
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import com.hackathon.hikoo.view.ListDivider
import com.hackathon.hikoo.view.adpater.ShelterAdapter
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject

class HikooFragment : Fragment(), HikooView,
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMapClickListener,
    GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraIdleListener,
    GoogleMap.OnCameraMoveListener,
    ShelterAdapter.ShelterItemCallback {

    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private lateinit var sosButton: View
    private lateinit var shelterList: RecyclerView

    private val presenter: HikooPresenter by inject()

    companion object {
        fun newInstance() = HikooFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hikoo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        retrieveViews(view)
        setListener()
        (activity as? MainActivity)?.setActionBarTitle(R.string.hikoo)
    }

    private fun setListener() {
        sosButton.setOnClickListener {
            presenter.sendSOS()
        }
    }

    private fun retrieveViews(view: View) {
        mapView = view.findViewById(R.id.map_view)
        sosButton = view.findViewById(R.id.sos_linearlayout)
        shelterList = view.findViewById(R.id.shelter_list)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        mapView?.let { map ->
            map.onCreate(savedInstanceState)
            map.getMapAsync(this)
        }
    }

    override fun onStart() {
        super.onStart()
//        EventBus.getDefault().register(this)
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
        (activity as? MainActivity)?.startLocationListener()
    }

    override fun onPause() {
        mapView?.onPause()
        (activity as? MainActivity)?.stopLocationListener()
        super.onPause()
    }

    override fun onStop() {
        mapView?.onStop()
        super.onStop()
//        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        mapView = null
        googleMap?.isMyLocationEnabled = false
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_hikoo, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.hikoo_alert -> {
                (activity as? MainActivity)?.launchAlertPage()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        this.googleMap?.isIndoorEnabled = false
        this.googleMap?.setOnMarkerClickListener(this)
        this.googleMap?.setOnMapClickListener(this)
        activity?.let {
            if (ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }

            presenter.getShelter(it, googleMap)
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        return true
    }

    override fun onMapClick(position: LatLng?) {
    }

    override fun onCameraMoveStarted(position: Int) {
    }

    override fun onCameraIdle() {
    }

    override fun onCameraMove() {
    }

    //region HikooView
    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }

    override fun setupShelter(list: List<Shelter>, imageLoadTool: ImageLoadTool) {
        val shelterAdapter = ShelterAdapter(this, imageLoadTool)
        val divider = ListDivider(shelterList.context, ListDivider.VERTICAL_LIST, false, false)
        divider.setColor(ContextCompat.getColor(shelterList.context, R.color.iris_blue))
        shelterList.let {
            it.setHasFixedSize(true)
            it.adapter = shelterAdapter
            it.addItemDecoration(divider)
        }
        shelterAdapter.addShelterItem(list)
    }

    override fun onShelterItemClicked(shelter: Shelter) {
        try {
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(shelter.latpt, shelter.lngpt), 16f))
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    override fun setupMyLocation(shelter: Shelter) {
        try {
            val defaultLocation = LatLng(24.780033, 120.996217)
            val userLocation = LatLng(shelter.latpt, shelter.lngpt)
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }
    //endregion

}
