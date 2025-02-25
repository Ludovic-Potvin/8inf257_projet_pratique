package com.project.mobile.common

import com.project.mobile.common.CategoryType.AUTRE
import com.project.mobile.common.CategoryType.ECOLE
import com.project.mobile.common.CategoryType.LOISIR
import com.project.mobile.common.CategoryType.MAISON
import com.project.mobile.common.CategoryType.MEDICAL
import com.project.mobile.common.CategoryType.SPORT
import com.project.mobile.common.CategoryType.TRAVAIL

/**
 * Enum class representing different levels of priority.
 *
 * This enum defines three levels of priority:
 * - `HighPriority`: The highest priority level.
 * - `StandardPriority`: The standard priority level.
 * - `LowPriority`: The lowest priority level.
 *
 * Each priority level has an associated integer value, where:
 * - `HighPriority` is represented by 3.
 * - `StandardPriority` is represented by 2.
 * - `LowPriority` is represented by 1.
 *
 * This enum provides methods to convert between `PriorityType` and its corresponding integer value.
 */
enum class PriorityType {
    HighPriority,
    StandardPriority,
    LowPriority;

    fun toInt(): Int {
        return when (this) {
            LowPriority -> 1
            StandardPriority -> 2
            HighPriority -> 3
        }
    }

    companion object {
        /**
         * Converts an integer value to its corresponding `PriorityType`.
         *
         * - 3 will be converted to `HighPriority`.
         * - 2 will be converted to `StandardPriority`.
         * - 1 will be converted to `LowPriority`.
         *
         * @param value The integer value to be converted.
         * @return The corresponding `PriorityType`.
         * @throws IllegalArgumentException If the integer value is invalid (not 1, 2, or 3).
         */
        fun fromInt(value: Int): PriorityType {
            return when (value) {
                3 -> HighPriority
                2 -> StandardPriority
                1 -> LowPriority
                else -> throw IllegalArgumentException("Invalid priority value")
            }
        }
    }
}