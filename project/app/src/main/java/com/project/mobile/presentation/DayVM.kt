package com.project.mobile.presentation

import Activated
import ActivatedOrNo
import androidx.compose.ui.graphics.Color
import com.project.mobile.ui.theme.Purple
import com.project.mobile.ui.theme.WhitePurple


data class DayVM(
    val abreviation: String = "",
    val fullname: String = "",
    var state: ActivatedOrNo,
    var isModified: Boolean = false // Ajout d'un booléen pour savoir si l'état a été modifié
) {

    fun changeState() {
        this.state = if (state is Activated) {
            NoActivated
        } else {
            Activated
        }
        isModified = true  // Marque comme modifié après changement
    }
}

