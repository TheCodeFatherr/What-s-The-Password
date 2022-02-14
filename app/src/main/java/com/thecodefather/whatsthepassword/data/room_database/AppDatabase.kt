package com.thecodefather.whatsthepassword.data.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thecodefather.whatsthepassword.data.room_database.daos.ConfigDao
import com.thecodefather.whatsthepassword.data.room_database.entities.ConfigEntity
import com.thecodefather.whatsthepassword.internal.Constants

@Database(
    entities = [
        ConfigEntity::class
    ], version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun configDao(): ConfigDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                Constants.FileConstants.DATABASE_NAME
            )
                .build()
    }
}