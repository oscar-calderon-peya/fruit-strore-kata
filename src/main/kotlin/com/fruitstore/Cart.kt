package com.fruitstore

class Cart(private val cartRepository: CartRepository) {

    fun addItem(item: Item) {
        cartRepository.add(item)
    }

    fun calculateTotal(): Int {
        return cartRepository.getItems().sumBy { it.price }
    }
}