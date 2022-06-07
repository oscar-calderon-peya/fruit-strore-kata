package com.fruitstore

import org.assertj.core.api.BDDAssertions.then
import org.junit.Test

class FruitStoreTest {

    @Test
    fun `failed test`() {
        then(true).isEqualTo(true)
    }
}