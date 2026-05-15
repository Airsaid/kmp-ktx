package com.airsaid.ktx

/**
 * Returns this [Double] value if not null, otherwise 0.0
 */
fun Double?.orZero(): Double = this ?: 0.0
