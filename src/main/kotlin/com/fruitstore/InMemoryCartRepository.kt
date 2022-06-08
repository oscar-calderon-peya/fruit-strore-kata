package com.fruitstore

class InMemoryCartRepository: CartRepository {
    private val items = mutableListOf<Item>()

    override fun add(item: Item) {
        items.add(item)
    }

    override fun getItems(): List<Item> {
        return items
    }

}