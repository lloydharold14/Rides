package com.tkh.rides.domain.use_case

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CheckSizeRangeTest {
    private lateinit var checkSizeRange: CheckSizeRange

    @Before
    fun setup() {
        checkSizeRange = CheckSizeRange()
    }

    @Test
    fun `Input out of range, return false`() {

        val result = checkSizeRange.invoke(102)

        assertEquals(result, false)
    }
}