package com.ksatria.hotel.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "hotel_table")
data class Hotel(
    @PrimaryKey(autoGenerate = true)
    // antribut-atribut data dalam tabel/entitas hotel_table
    val id: Int = 0,
    val name: String,
    val address: String,
    val numberOfRooms: String,
    val latitude: Double?,
    val longitude: Double?
) : Parcelable
