package com.project.mobile.common

/**
 * Enum class representing different category types.
 *
 * The available category types are:
 * - [TRAVAIL]: Represents work-related activities.
 * - [MEDICAL]: Represents medical-related activities.
 * - [MAISON]: Represents home-related activities.
 * - [SPORT]: Represents sports-related activities.
 * - [ECOLE]: Represents school-related activities.
 * - [LOISIR]: Represents leisure activities.
 * - [AUTRE]: Represents other activities.
 */
enum class CategoryType(val label: String) {
    TRAVAIL("Travail"),
    MEDICAL("Médical"),
    MAISON("Maison"),
    SPORT("Sport"),
    ECOLE("Ecole"),
    LOISIR("Loisir"),
    AUTRE("Autre");

    companion object {
        /**
         * Returns the corresponding [CategoryType] for the provided label.
         * If no match is found, returns [CategoryType.AUTRE] as the default.
         *
         * @param label The label to match against the category types.
         * @return The matching [CategoryType] or [CategoryType.AUTRE] if no match is found.
         */
        fun fromLabel(label: String): CategoryType {
            return CategoryType.entries.find { it.label == label } ?: AUTRE
        }
    }
}