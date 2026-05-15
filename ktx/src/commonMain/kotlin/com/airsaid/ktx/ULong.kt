package com.airsaid.ktx

/**
 * Returns this [ULong] value if it's not null, or zero if it is null.
 */
fun ULong?.orZero(): ULong = this ?: 0UL
