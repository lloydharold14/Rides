package com.tkh.rides.domain.use_case

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class CalculateCarbonEmissionTest {
    private lateinit var calculateCarbonEmission: CalculateCarbonEmission

    @Before
    fun setUp() {

        calculateCarbonEmission = CalculateCarbonEmission()
    }

    @Test
    fun `Kilometrage less or equal to 5000km, c02 is less or equal to 5000`() {

        val result = calculateCarbonEmission(4500)

        assertEquals(result.equals(4500), true)
    }

    @Test
    fun `Kilometrage is greater than 5000km, c02 is higher than 5000`() {

        val result = calculateCarbonEmission(10000)

        assertEquals(result.equals(12500), true)
    }
}