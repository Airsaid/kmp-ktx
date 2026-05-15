package com.airsaid.ktx

/**
 * Returns this [Int] value if it's not null, or zero if it is null.
 */
fun Int?.orZero(): Int = this ?: 0
