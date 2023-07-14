package com.ksatria.hotel.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ksatria.hotel.model.Hotel
import com.ksatria.hotel.repository.HotelRepository
import kotlinx.coroutines.launch

class HotelViewModel(private val repository: HotelRepository): ViewModel() {
    // Variabel allHotels adalah LiveData yang menampung daftar hotel dari repository.
    val allHotels: LiveData<List<Hotel>> = repository.allHotels.asLiveData()

    // fungsi untuk menambahkan data hotel ke repository
    fun insert(hotel: Hotel) = viewModelScope.launch {
        repository.insertHotel(hotel)
    }

    // fungsi untuk menghapus data hotel di repository
    fun delete(hotel: Hotel) = viewModelScope.launch {
        repository.deletetHotel(hotel)
    }

    // fungsi untuk mengupdate data hotel di repository
    fun update(hotel: Hotel) = viewModelScope.launch {
        repository.updatetHotel(hotel)
    }
}

// untuk memberikan instance ViewModel kepada kelas HotelViewModel.
class HotelViewModelFactory(private val repository: HotelRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom((HotelViewModel::class.java))){
            return HotelViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}