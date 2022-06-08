package com.fruitstore

import org.assertj.core.api.BDDAssertions.then
import org.junit.Test

class CartTest {
    private val cartRepository = InMemoryCartRepository()
    private val cart = Cart(cartRepository)

    @Test
    fun `should add several items`() {
        cart.addItem(banana)
        cart.addItem(apple)
        cart.addItem(pear)

        then(cartRepository.getItems()).containsExactlyElementsOf(listOf(banana, apple, pear))
    }

    @Test
    fun `should calculate total`() {
        cart.addItem(banana.copy(price = 5))
        cart.addItem(apple.copy(price = 10))
        cart.addItem(pear.copy(price = 10))

        then(cart.calculateTotal()).isEqualTo(25)
    }

    companion object {
        val banana = createItem("banana")
        val apple = createItem("apple")
        val pear = createItem("pear")
        private fun createItem(name: String, price: Int = 0) = Item(name, price)
    }
}