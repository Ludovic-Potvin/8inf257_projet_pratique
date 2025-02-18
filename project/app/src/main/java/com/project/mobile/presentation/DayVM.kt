package com.project.mobile.presentation

import Activated
import ActivatedOrNo
import NoActivated
import com.project.mobile.ui.theme.Purple
import com.project.mobile.ui.theme.WhitePurple


data class DayVM(
    val abreviation: String = "",
    val fullname: String = "",
    var state: ActivatedOrNo,
    ) {

    fun changeState() {
        this.state = if (state is Activated) {
            NoActivated
        } else {
            Activated
        }

    }
}

