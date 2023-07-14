package com.ksatria.hotel.repository

import com.ksatria.hotel.dao.HotelDao
import com.ksatria.hotel.model.Hotel
import kotlinx.coroutines.flow.Flow

// kelas HotelRepository untuk menyediakan akses ke data hotel dan melakukan operasi database
class HotelRepository(private val hotelDao: HotelDao) {
    // variabel flow yang menampung daftar hotel dari dao
    val allHotels: Flow<List<Hotel>> = hotelDao.getAllHotel();

    // untuk insert data hotel dari dao
    suspend fun insertHotel(hotel: Hotel){
        hotelDao.insertHotel(hotel)
    }

    // untuk  delete data hotel dari dao
    suspend fun deletetHotel(hotel: Hotel){
        hotelDao.deleteHotel(hotel)
    }

    // untuk update data hotel dari dao
    suspend fun updatetHotel(hotel: Hotel){
        hotelDao.updateHotel(hotel)
    }
}