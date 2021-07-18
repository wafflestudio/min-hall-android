package com.wafflestudio.snucse.minhall.model

data class Seat(
    val index: Int,
    val x: Int,
    val y: Int,
    val mode: Mode,
) {
    enum class Mode {
        AVAILABLE,
        TAKEN,
        DISABLED
    }
}
