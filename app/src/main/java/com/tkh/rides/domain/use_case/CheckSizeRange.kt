package com.tkh.rides.domain.use_case

class CheckSizeRange {
    operator fun invoke(size: Int): Boolean {

        if (size in 1..100) return true
        return false
    }
}