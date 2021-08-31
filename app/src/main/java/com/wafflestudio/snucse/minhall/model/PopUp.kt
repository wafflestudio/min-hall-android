package com.wafflestudio.snucse.minhall.model

sealed class PopUp

class NoticePopUp(
    val message: String,
    val show: Boolean,
    val title: String,
) : PopUp()

class WarningPopUp(
    val message: String,
    val show: Boolean,
    val title: String,
) : PopUp()

object EmptyPopUp : PopUp()
