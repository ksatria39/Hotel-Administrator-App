package com.ksatria.hotel.application

import android.app.Application
import com.ksatria.hotel.repository.HotelRepository

// untuk menginisialisasi komponen utama aplikasi, database dan repository.
class HotelApp: Application() {
    val  database by lazy { HotelDatabase.getDatabase(this) }
    val repository by lazy { HotelRepository(database.hoteDao()) }
}