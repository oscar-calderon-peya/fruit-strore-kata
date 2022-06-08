package com.fruitstore

import org.assertj.core.api.BDDAssertions.then
import org.junit.Test

class CartTest {

    private val cartRepository = InMemoryCartRepository()
    private val cart = Cart(cartRepository)

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

        then(cart.calculateTotal()).isEqualTo(25)
    }


    private fun thenCartContains(fruits: List<Item>) {
        then(cartRepository.getItems()).containsExactlyElementsOf(fruits)
    }

    private fun whenAddingAnItem(vararg item: Item) {
        item.forEach {
            cart.addItem(it)
        }
    }

    companion object {
        val banana = createItem("banana")
        val apple = createItem("apple")
        val pear = createItem("pear")
        private fun createItem(name: String, price: Int = 0) = Item(name, price)
    }
}