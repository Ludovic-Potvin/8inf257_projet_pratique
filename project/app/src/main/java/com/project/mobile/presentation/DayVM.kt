package com.project.mobile.presentation

import androidx.compose.ui.graphics.Color
import com.project.mobile.ui.theme.Purple
import com.project.mobile.ui.theme.WhitePurple

data class DayVM(
    //val jour: String = "",
    val value: String ="",
    var state: ActivatedOrNo
    ){
    fun changeState(){
        state = if(state is Activated){
            NoActivated
        } else{
            Activated
        }
    }

}

//rajouter un booléen ?
sealed class ActivatedOrNo(
    val backgroundColor: Color,
    val activated: Boolean
)

data object Activated:ActivatedOrNo(
    WhitePurple, true)

data object NoActivated:ActivatedOrNo(
    //deux versions possibles :
    //Color.Unspecified)
    Purple, false)
