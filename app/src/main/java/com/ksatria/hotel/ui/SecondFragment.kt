package com.ksatria.hotel.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ksatria.hotel.R
import com.ksatria.hotel.application.HotelApp
import com.ksatria.hotel.databinding.FragmentSecondBinding
import com.ksatria.hotel.model.Hotel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val hotelViewModel: HotelViewModel by viewModels {
        HotelViewModelFactory((applicationContext as HotelApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var hotel: Hotel? = null
    private lateinit var gMap: GoogleMap
    private var currentLatLng: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hotel = args.hotel

        // jika nilai hotel tidak null maka tombol delete akan muncul dan tombol simpan akan berubah menjadi tombol ubah
        if(hotel != null){
            binding.btnDelete.visibility = View.VISIBLE
            binding.btnSave.text = "Ubah"
            binding.etName.setText(hotel?.name)
            binding.etAddress.setText(hotel?.address)
            binding.etNumberOfRooms.setText(hotel?.numberOfRooms)
        }

        // Binding Google MAp
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

        val name = binding.etName.text
        val address = binding.etAddress.text
        val numberOfRooms = binding.etNumberOfRooms.text
        binding.btnSave.setOnClickListener {
            // akan menampilkan peringatan jika ada yg tidak diisi
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama Jangan Kosong", Toast.LENGTH_SHORT).show()
            }else if(address.isEmpty()){
                Toast.makeText(context, "Alamat Jangan Kosong", Toast.LENGTH_SHORT).show()
            }else if(numberOfRooms.isEmpty()){
                Toast.makeText(context, "Jumlah Kamar Jangan Kosong", Toast.LENGTH_SHORT).show()
            }else{
                // terisi semua dan data = null, tidak terdafatar makan akan ditambahkan
                // jika sudah terdaftar makan data yg sudah ada akan diupdate
                if (hotel == null){
                    val hotel = Hotel(0,name.toString(),address.toString(),numberOfRooms.toString(), currentLatLng?.latitude, currentLatLng?.longitude)
                    hotelViewModel.insert(hotel)
                }else{
                    val hotel = Hotel(hotel?.id!!,name.toString(),address.toString(),numberOfRooms.toString(), currentLatLng?.latitude, currentLatLng?.longitude)
                    hotelViewModel.update(hotel)
                }
                findNavController().popBackStack()
            }
        }

        // aksi yg akan dilakukan tombol delete ketika di klik
        binding.btnDelete.setOnClickListener {
            hotel?.let { it -> hotelViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap

        // mengaktifkan fitur zoom map
        val uiSettings = gMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        uiSettings.isZoomGesturesEnabled = true

        // Implementasi drag marker
        gMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    // fungsi ketika marker di drag
    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLng = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLng.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {}

    // cek apakah izin lokasi sudah diberikan
    private fun checkPermission() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            Toast.makeText(applicationContext,"Akses lokasi ditolak",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        // jika permission tidak disetujui akan berhenti di kondisi if
        // ika izin diberikan, maka koordinat lokasi terakhir pengguna diambil menggunakan fusedLocationClient.lastLocation
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    var latLong = LatLng(location.latitude, location.longitude)
                    currentLatLng = latLong
                    var title = "Marker"

                    // jika hotel tidak null maka mengambil data lat dan long dari hotel
                    if (hotel != null) {
                        title = hotel?.name.toString()
                        val newCurrentLocation = LatLng(hotel?.latitude!!, hotel?.longitude!!)
                        latLong = newCurrentLocation
                    }
                    // pengaturan marker
                    val markerOption = MarkerOptions()
                        .position(latLong)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hotel_marker))
                    gMap.addMarker(markerOption)
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong,12f))
                }
            }
    }
}