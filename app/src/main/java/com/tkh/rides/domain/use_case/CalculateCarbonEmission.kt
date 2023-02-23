package com.tkh.rides.domain.use_case

import kotlin.math.roundToInt

class CalculateCarbonEmission {


    operator fun invoke(kilometrage: Int): Int {

        var c02Emitted: Int
        if (kilometrage > 5000) {
            val kmAfter5000 = kilometrage - 5000

            c02Emitted = (kmAfter5000 * 1.5).roundToInt() + 5000
        } else {
            c02Emitted = kilometrage
        }
        return c02Emitted
    }
}