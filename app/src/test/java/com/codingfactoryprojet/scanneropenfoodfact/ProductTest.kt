package com.codingfactoryprojet.scanneropenfoodfact


import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import com.codingfactoryprojet.scanneropenfoodfact.service.product.ProductRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


class ProductTest {

    @Test
    fun `should detect when wrong barcode` (){

        val product = Product(barcode = 65556666568)

        Assert.assertEquals("Unknown", product.name)
        Assert.assertEquals(0, product.caloriesPer100g)
        Assert.assertEquals(false, product.isFavorite)
    }

}