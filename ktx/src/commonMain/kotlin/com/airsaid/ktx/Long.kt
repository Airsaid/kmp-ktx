package com.airsaid.ktx

/**
 * Returns this [Long] value if it's not null, or zero if it is null.
 */
fun Long?.orZero(): Long = this ?: 0L
