package com.airsaid.ktx

/**
 * Returns this [Float] value if it's not null, or 0F if it is null.
 */
fun Float?.orZero(): Float = this ?: 0F
