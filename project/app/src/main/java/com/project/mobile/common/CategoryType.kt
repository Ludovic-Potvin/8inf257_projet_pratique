package com.project.mobile.common

import android.content.Context
import com.project.mobile.R
import androidx.annotation.StringRes

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
enum class CategoryType(@StringRes val labelResId: Int) {
    TRAVAIL(R.string.category_work),
    MEDICAL(R.string.category_medical),
    MAISON(R.string.category_home),
    SPORT(R.string.category_sport),
    ECOLE(R.string.category_school),
    LOISIR(R.string.category_leisure),
    AUTRE(R.string.category_other);

    companion object {
        /**
         * Returns the corresponding [CategoryType] for the provided localized label.
         * If no match is found, returns [CategoryType.AUTRE] as the default.
         */
        fun fromLabel(label: String): CategoryType {
            return entries.firstOrNull { it.name == label } ?: AUTRE
        }
    }
}