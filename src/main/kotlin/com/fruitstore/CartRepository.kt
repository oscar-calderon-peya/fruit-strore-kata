package com.fruitstore

interface CartRepository {
    fun add(item: Item)
    fun getItems(): List<Item>
}