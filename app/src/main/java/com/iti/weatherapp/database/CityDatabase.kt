package com.iti.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.iti.weatherapp.helper.MyApplication
import com.iti.weatherapp.model.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The Room Database that contains the Task table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = arrayOf(City::class), version = 1, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    private class CityDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var cityDao = database.cityDao()

                    // Delete all content here.
                    // Add sample words.
                    var city = City(country = "Egypt", city = "Egypt", region = "Egypt")
                    cityDao.insertCity(city)

                    var city2 = City(country = "America", city = "America", region = "America")
                    cityDao.insertCity(city2)
                }
            }
        }
    }



    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CityDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): CityDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityDatabase::class.java,
                    "cities"
                )
                    .allowMainThreadQueries()
                    .addCallback(CityDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
