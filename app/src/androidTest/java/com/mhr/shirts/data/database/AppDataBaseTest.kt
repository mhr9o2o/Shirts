package com.mhr.shirts.data.database


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mhr.shirts.data.data_models.Basket
import com.mhr.shirts.data.data_models.Shirt
import com.mhr.shirts.data.database.dao.BasketDao
import com.mhr.shirts.data.database.dao.ShirtDao
import junit.framework.Assert.assertNull
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {

    private lateinit var database: AppDataBase
    private lateinit var shirtDao: ShirtDao
    private lateinit var basketDao: BasketDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        shirtDao = database.shirtDao()
        basketDao = database.basketDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    //region Shirts
    @Test
    @Throws(Exception::class)
    fun writeAShirtAndRead()
    {
        val shirt = Shirt(0, "name", 20, "brown", null, "l", "")

        shirtDao.insertShirt(shirt)
        val shirts = shirtDao.getShirts()
        assertThat(shirts.size, equalTo(1))
        assertThat(shirts[0].id, equalTo(0))
        assertThat(shirts[0].name, equalTo("name"))
        assertThat(shirts[0].price, equalTo(20))
        assertThat(shirts[0].colour, equalTo("brown"))
        assertThat(shirts[0].quantity?:0, equalTo(0))
        assertThat(shirts[0].size, equalTo("l"))
        assertThat(shirts[0].picture, equalTo(""))

    }

    @Test
    @Throws(Exception::class)
    fun findShirtsByColour()
    {
        val shirt1 = Shirt(0, "name", 20, "green", null, "l", "")
        val shirt2 = Shirt(1, "name", 20, "green", null, "l", "")
        val shirt3 = Shirt(2, "name", 20, "blue", null, "l", "")

        shirtDao.insertShirt(shirt1)
        shirtDao.insertShirt(shirt2)
        shirtDao.insertShirt(shirt3)

        val blueShirts = shirtDao.filterShirtsByColour("blue")
        val greenShirts = shirtDao.filterShirtsByColour("green")

        assertThat(blueShirts.size, equalTo(1))
        assertThat(blueShirts[0].colour, equalTo("blue"))
        assertThat(greenShirts.size, equalTo(2))
        assertThat(greenShirts[0].colour, equalTo("green"))
        assertThat(greenShirts[1].colour, equalTo("green"))

    }

    @Test
    @Throws(Exception::class)
    fun findShirtsBySize()
    {
        val shirt1 = Shirt(0, "name", 20, "green", null, "s", "")
        val shirt2 = Shirt(1, "name", 20, "green", null, "m", "")
        val shirt3 = Shirt(2, "name", 20, "blue", null, "m", "")

        shirtDao.insertShirt(shirt1)
        shirtDao.insertShirt(shirt2)
        shirtDao.insertShirt(shirt3)

        val smallShirts = shirtDao.filterShirtsBySize("s")
        val mediumShirts = shirtDao.filterShirtsBySize("m")

        assertThat(smallShirts.size, equalTo(1))
        assertThat(smallShirts[0].size, equalTo("s"))
        assertThat(mediumShirts.size, equalTo(2))
        assertThat(mediumShirts[0].size, equalTo("m"))
        assertThat(mediumShirts[1].size, equalTo("m"))

    }

    @Test
    @Throws(Exception::class)
    fun findShirtsByColourAndSize()
    {
        val shirt1 = Shirt(0, "name", 20, "green", null, "s", "")
        val shirt2 = Shirt(1, "name", 20, "green", null, "m", "")
        val shirt3 = Shirt(2, "name", 20, "blue", null, "m", "")

        shirtDao.insertShirt(shirt1)
        shirtDao.insertShirt(shirt2)
        shirtDao.insertShirt(shirt3)

        val smallGreenShirts = shirtDao.filterShirtsByColourAndSize("green", "s")
        val mediumGreenShirts = shirtDao.filterShirtsByColourAndSize("green", "m")
        val mediumBlueShirts = shirtDao.filterShirtsByColourAndSize("blue", "m")
        val smallBlueShirts = shirtDao.filterShirtsByColourAndSize("blue", "s")

        assertThat(smallGreenShirts.size, equalTo(1))
        assertThat(smallGreenShirts[0].size, equalTo("s"))
        assertThat(smallGreenShirts[0].colour, equalTo("green"))

        assertThat(mediumGreenShirts.size, equalTo(1))
        assertThat(mediumGreenShirts[0].size, equalTo("m"))
        assertThat(mediumGreenShirts[0].colour, equalTo("green"))

        assertThat(mediumBlueShirts.size, equalTo(1))
        assertThat(mediumBlueShirts[0].size, equalTo("m"))
        assertThat(mediumBlueShirts[0].colour, equalTo("blue"))

        assertThat(smallBlueShirts.size, equalTo(0))

    }
    //endregion

    //region Basket
    @Test
    @Throws(Exception::class)
    fun savingBasket()
    {
        val shirt = Shirt(0, "name", 20, "green", null, "s", "")
        val basket = Basket(0, mutableListOf(shirt))

        basketDao.insertBasket(basket)

        val retrievedBasket = basketDao.getBasket()

        assertThat(retrievedBasket, equalTo(basket))
        assertThat(retrievedBasket.shirts.size, equalTo(1))
        assertThat(retrievedBasket.shirts[0], equalTo(shirt))

    }

    @Test
    @Throws(Exception::class)
    fun deletingBasket()
    {
        val shirt = Shirt(0, "name", 20, "green", null, "s", "")
        val basket = Basket(0, mutableListOf(shirt))

        basketDao.insertBasket(basket)
        basketDao.deleteBasket(basket)

        val retrievedBasket = basketDao.getBasket()

        assertNull(retrievedBasket)

    }
    //endregion

}