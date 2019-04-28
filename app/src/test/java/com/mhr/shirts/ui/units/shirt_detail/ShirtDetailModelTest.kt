package com.mhr.shirts.ui.units.shirt_detail

import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

class ShirtDetailModelTest {

    @Test
    fun addingToBasketIfNotExists() {

        val shirtDetailModel = Mockito.mock(ShirtDetailModel::class.java)
        val basket = Basket()
        val shirt = Shirt(0, "", 0, "", null, "", "")

        val shirtDetailModelSpy = Mockito.spy(shirtDetailModel)
        shirtDetailModelSpy.checkShirtExistenceAndUpdateTheBasket(shirt, basket)
        assertEquals(1, basket.shirts.size)
        assertEquals(1, basket.shirts[0].quantity)
    }

    @Test
    fun addingToBasketOfExists() {

        val shirtDetailModel = Mockito.mock(ShirtDetailModel::class.java)
        val basket = Basket()
        val shirt = Shirt(0, "", 0, "", null, "", "")

        val shirtDetailModelSpy = Mockito.spy(shirtDetailModel)
        shirtDetailModelSpy.checkShirtExistenceAndUpdateTheBasket(shirt, basket)
        //now it should add the quantity
        shirtDetailModelSpy.checkShirtExistenceAndUpdateTheBasket(shirt, basket)

        Assert.assertEquals(1, basket.shirts.size)
        Assert.assertEquals(2, basket.shirts[0].quantity)

    }
}