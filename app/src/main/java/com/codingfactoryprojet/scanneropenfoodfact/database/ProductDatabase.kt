package com.codingfactoryprojet.scanneropenfoodfact.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import com.codingfactoryprojet.scanneropenfoodfact.service.product.ProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductRoomDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    /*private class ProductDatabaseCallBack(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            *//*INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.productDao())
                }
            }*//*
        }

        *//*suspend fun populateDatabase(productDao: ProductDao) {
            var p1 = Product(
                barcode = 737628064502,
                brand = "truc",
                categories = "dhfdfd",
                imageURL = "https://static.openfoodfacts.org/images/products/073/762/806/4502/ingredients_en.10.200.jpg",
                nutriscore = "c",
                caloriesPer100g = 230,
                labels = "dfdfd",
                name = "truc"
            )
            productDao.insert(p1)
        }*//*
    }*/

    companion object {
        @Volatile
        private var INSTANCE: ProductRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): ProductRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductRoomDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()/* .addCallback(ProductDatabaseCallBack(scope)) */.build()
                INSTANCE = instance
                return instance
            }
        }
    }
}