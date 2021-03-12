package com.codingfactoryprojet.scanneropenfoodfact

import androidx.appcompat.app.AppCompatActivity
import com.codingfactoryprojet.scanneropenfoodfact.entity.product.Product
import com.codingfactoryprojet.scanneropenfoodfact.productlist.ProductListActivity
import com.codingfactoryprojet.scanneropenfoodfact.productlist.ProductListViewModel
import com.codingfactoryprojet.scanneropenfoodfact.service.product.ProductRepository
import org.junit.Assert
import org.junit.Test

abstract class ScanTest {


    lateinit var productRepository : ProductRepository



    @Test
    fun `should detect when wrong barcode` (){


        ProductListViewModel().allProducts

        productList.getDataForProduct(3061990141354)

        Assert.assertEquals(3061990141354,  productRepository.getProductByBarcode(3061990141354).barcode)

    }
}