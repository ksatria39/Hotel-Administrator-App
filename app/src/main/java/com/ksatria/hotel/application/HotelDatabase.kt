package com.ksatria.hotel.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ksatria.hotel.dao.HotelDao
import com.ksatria.hotel.model.Hotel

// mendefinisikan database
@Database(entities = [Hotel::class], version = 2, exportSchema = false)
abstract class HotelDatabase: RoomDatabase() {
    abstract fun hoteDao(): HotelDao

    companion object{
        private var INSTANCE: HotelDatabase? = null

        // migrasi database untuk menambahkan kolom lat dan long
        private val migration1To2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE hotel_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE hotel_table ADD COLUMN longitude Double DEFAULT 0.0")
            }
        }

        fun getDatabase(context: Context): HotelDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HotelDatabase::class.java,
                    "hotel_database_1"
                )
                    .addMigrations(migration1To2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}