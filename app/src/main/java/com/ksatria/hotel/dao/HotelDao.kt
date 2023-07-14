package com.ksatria.hotel.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ksatria.hotel.model.Hotel
import kotlinx.coroutines.flow.Flow

// DAO (Data Access Object) untuk mengakses dan memanipulasi data hotel dalam database menggunakan Room Persistence Library.
@Dao
interface HotelDao {

    // anotasi yang digunakan untuk menjalankan query SQL dalam metode getAllHotel()
    @Query("SELECT * FROM hotel_table ORDER BY name ASC")
    fun getAllHotel(): Flow<List<Hotel>>

    // menambah data dalam database
    @Insert
    suspend fun insertHotel(hotel: Hotel)

    // menghapus data dalam database
    @Delete
    suspend fun deleteHotel(hotel: Hotel)

    // memperbarui data dalam database
    @Update fun updateHotel(hotel: Hotel)
}