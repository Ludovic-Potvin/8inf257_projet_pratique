package com.project.mobile.presentation

import androidx.compose.ui.graphics.Color
import com.project.mobile.ui.theme.Purple
import com.project.mobile.ui.theme.WhitePurple

data class DayVM(
    val jour: String = "",
    val activatedOrNo: ActivatedOrNo

    )


sealed class ActivatedOrNo(
    val backgroundColor: Color
)

data object Activated:ActivatedOrNo(
    WhitePurple)

data object NoActivated:ActivatedOrNo(
    //deux versions possibles :
    //Color.Unspecified)
    Purple)
