package com.fruitstore

import org.assertj.core.api.BDDAssertions.then
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalStdlibApi
class CartTest {

    private val calendar = mock<Calendar> {  }
    private val cartRepository = InMemoryCartRepository()
    private val applePromotion = ApplePromotion(cartRepository, calendar)
    private val pearPromotion = PearPromotion(cartRepository)
    private val cart = Cart(cartRepository, listOf(applePromotion, pearPromotion))

    @Test
    fun `should add several items`() {
        val fruits = listOf(banana, apple, pear)

        whenAddingAnItem(*fruits.toTypedArray())

        thenCartContains(fruits)
    }

    @Test
    fun `should calculate total`() {
        val fruits = listOf(banana, apple, pear)

        whenAddingAnItem(*fruits.toTypedArray())

        thenTotalIs(45)
    }

    @Test
    fun `should calculate incomplete pear promotions`() {
        val fruits = listOf(pear, apple, pear, pear)

        whenAddingAnItem(*fruits.toTypedArray())

        thenTotalIs(48)
    }

    @Test
    fun `should calculate several pears promotion`() {
        val fruits = listOf(pear, pear, pear, pear, apple)

        whenAddingAnItem(*fruits.toTypedArray())

        thenTotalIs(56)
    }

    @Test
    fun `calculate apple promotion only for mondays`() {
        whenever(calendar.dayOfWeek()).thenReturn(1)
        val fruits = listOf(apple, apple, apple)

        whenAddingAnItem(*fruits.toTypedArray())

        thenTotalIs(50)
    }

    @Test
    fun `should not apply apple promotion when it is not monday`() {
        givenADayOfWeek(5)
        val fruits = listOf(apple, apple, apple)

        whenAddingAnItem(*fruits.toTypedArray())

        thenTotalIs(60)
    }

    private fun givenADayOfWeek(dayNumber: Int) {
        whenever(calendar.dayOfWeek()).thenReturn(dayNumber)
    }

    @Test
    fun `should calculate incomplete apple promotions`() {
        givenADayOfWeek(1)
        val fruits = listOf(apple, apple, apple, apple)

        whenAddingAnItem(*fruits.toTypedArray())

        thenTotalIs(70)
    }

    @Test
    fun `apple promotions on friday`() {
        givenADayOfWeek(5)
        val fruits = buildList { repeat(30) { add(apple) } }
        whenAddingAnItem(*fruits.toTypedArray())

        thenTotalIs(450)
    }

    private fun thenCartContains(fruits: List<Item>) {
        then(cartRepository.getItems()).containsExactlyElementsOf(fruits)
    }

    private fun whenAddingAnItem(vararg item: Item) {
        item.forEach {
            cart.addItem(it)
        }
    }

    private fun thenTotalIs(value: Int) {
        then(cart.calculateTotal()).isEqualTo(value)
    }

    companion object {
        val banana = createItem("banana", 15)
        val apple = createItem("apple", 20)
        val pear = createItem("pear", 10)
        private fun createItem(name: String, price: Int = 0) = Item(name, price)
    }
}