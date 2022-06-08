package com.fruitstore

class Cart(private val cartRepository: CartRepository, private val promotions: List<Promotions>)  {

    fun addItem(item: Item) {
        cartRepository.add(item)
    }

    fun calculateTotal(): Int {
        return calculateSubTotal() - promotions.calculateDiscounts()
    }


    private fun List<Promotions>.calculateDiscounts(): Int {
        return this.sumBy { it.calculateDiscount() }
    }

    private fun calculateSubTotal(): Int {
        val items = cartRepository.getItems()
        return items.sumBy { it.price }
    }
}

abstract class Promotions(private val cartRepository: CartRepository) {

    protected fun calculateDiscountsByQuantity(itemName: String, itemsQuantity: Int, discount: Int): Int {
        val appleAmount = countBy(itemName)
        val appleDiscountAmount: Int = appleAmount / itemsQuantity
        return appleDiscountAmount * discount
    }

    private fun countBy(itemName: String): Int {
        val items = cartRepository.getItems()
        return items.count { it.s == itemName }
    }

    abstract fun calculateDiscount(): Int

}

class PearPromotion(cartRepository: CartRepository): Promotions(cartRepository) {
    override fun calculateDiscount(): Int {
        return calculateDiscountsByQuantity("pear", 2, 2)
    }
}

class ApplePromotion(cartRepository: CartRepository, private val calendar: Calendar): Promotions(cartRepository) {
    override fun calculateDiscount(): Int {
        return when (calendar.dayOfWeek()) {
            5 -> {
                calculateDiscountsByQuantity("apple", 30, 150)
            }
            1 -> {
                calculateDiscountsByQuantity("apple", 3, 10)
            }
            else -> {
                0
            }
        }
    }

}
