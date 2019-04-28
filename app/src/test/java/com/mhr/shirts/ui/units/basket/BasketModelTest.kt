package com.mhr.shirts.ui.units.basket

import com.mhr.shirts.MyApplication
import com.mhr.shirts.data.DataAccessLayer
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.di.DaggerDataComponent
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class BasketModelTest {

    lateinit var myApplication: MyApplication
    lateinit var dataAccessLayer: DataAccessLayer
    lateinit var basketModel: BasketModel

    @Before
    fun setup()
    {
        myApplication = Mockito.mock(MyApplication::class.java)
        basketModel = Mockito.mock(BasketModel::class.java)
        dataAccessLayer = Mockito.mock(DataAccessLayer::class.java)
        basketModel.dataAccessLayer = dataAccessLayer
    }

    @Test
    fun addingToBasketIfNotExists() {
        val basket = Basket()
        val shirt = Shirt(0, "", 0, "", null, "", "")

        val basketModelSpy = Mockito.spy(basketModel)
        basketModelSpy.checkExistenceThenAdd(shirt, basket)

        Assert.assertEquals(1, basket.shirts.size)
        Assert.assertEquals(1, basket.shirts[0].quantity)

    }

    @Test
    fun addingToBasketIfExists()
    {
        val basket = Basket()
        val shirt = Shirt(0, "", 0, "", null, "", "")
        val basketModelSpy = Mockito.spy(basketModel)
        basketModelSpy.checkExistenceThenAdd(shirt, basket)
        //Now it should add the quantity:
        basketModelSpy.checkExistenceThenAdd(shirt, basket)

        Assert.assertEquals(1, basket.shirts.size)
        Assert.assertEquals(2, basket.shirts[0].quantity)
    }

    @Test
    fun removingNonExistingShirt()
    {
        val basket = Basket()
        val existingShirt = Shirt(0, "", 0, "", null, "", "")
        basket.shirts.add(existingShirt)
        val shirt = Shirt(1, "", 0, "", null, "", "")
        val basketModelSpy = Mockito.spy(basketModel)

        basketModelSpy.removeIfPossibleOrDelete(shirt, basket)

        Assert.assertEquals(1, basket.shirts.size)
    }

    @Test
    fun checkingDeleteOnRemove()
    {
        val basket = Basket()
        val shirt = Shirt(0, "", 0, "", null, "", "")
        val basketModelSpy = Mockito.spy(basketModel)

        //first add the shirt
        basketModelSpy.checkExistenceThenAdd(shirt, basket)
        //now it should be deleted on remove
        basketModelSpy.removeIfPossibleOrDelete(shirt, basket)

        Assert.assertEquals(0, basket.shirts.size)

    }

    @Test
    fun checkingRemove() {
        val basket = Basket()
        val shirt = Shirt(0, "", 0, "", null, "", "")
        val basketModelSpy = Mockito.spy(basketModel)

        //first add the shirt two times
        basketModelSpy.checkExistenceThenAdd(shirt, basket)
        basketModelSpy.checkExistenceThenAdd(shirt, basket)
        //now it should be not deleted on remove
        basketModelSpy.removeIfPossibleOrDelete(shirt, basket)

        Assert.assertEquals(1, basket.shirts.size)
        Assert.assertEquals(1, basket.shirts[0].quantity)
    }

    @Test
    fun delete() {
        val basket = Basket()
        val shirt = Shirt(0, "", 0, "", 10, "", "")
        basket.shirts.add(shirt)
        val basketModelSpy = Mockito.spy(basketModel)

        basketModelSpy.delete(shirt, basket)

        Assert.assertEquals(0, basket.shirts.size)

    }

    @Test
    fun updateTotalCost() {
        val basket = Basket()
        val shirt = Shirt(0, "", 20, "", 2, "", "")
        basket.shirts.add(shirt)
        val basketModelSpy = Mockito.spy(basketModel)

        Assert.assertEquals(40, basketModelSpy.updateTotalCost(basket))

    }
}