package com.wafflestudio.snucse.minhall.model

data class Seat(
    val id: Int,
    val x: Int,
    val y: Int,
    val mode: Mode,
    val rotation: Float = 0f,
) {
    enum class Mode {
        AVAILABLE,
        SELECTED,
        TAKEN,
        DISABLED
    }

    fun toggleSelect() = when (mode) {
        Mode.AVAILABLE -> copy(mode = Mode.SELECTED)
        Mode.SELECTED -> copy(mode = Mode.AVAILABLE)
        else -> this
    }

    val isSelected: Boolean
        get() = mode == Mode.SELECTED

    companion object {
        val seats = listOf(
            // Table 1
            Seat(1, 183, 170, Mode.AVAILABLE, -30f),
            Seat(2, 224, 147, Mode.AVAILABLE, -30f),
            // Table 2
            Seat(3, 347, 30, Mode.AVAILABLE),
            Seat(4, 415, 30, Mode.AVAILABLE),
            Seat(5, 483, 30, Mode.AVAILABLE),
            Seat(6, 544, 100, Mode.AVAILABLE),
            Seat(7, 544, 269, Mode.AVAILABLE),
            Seat(8, 544, 348, Mode.AVAILABLE),
            Seat(9, 403, 348, Mode.AVAILABLE),
            Seat(10, 358, 348, Mode.AVAILABLE),
            Seat(11, 288, 248, Mode.AVAILABLE),
            Seat(12, 429, 216, Mode.AVAILABLE),
            Seat(13, 429, 171, Mode.AVAILABLE),
            Seat(14, 380, 147, Mode.AVAILABLE),
            Seat(15, 335, 147, Mode.AVAILABLE),
            // Table 3
            Seat(16, 808, 77, Mode.AVAILABLE),
            Seat(17, 808, 131, Mode.AVAILABLE),
            Seat(18, 808, 186, Mode.AVAILABLE),
            Seat(19, 808, 240, Mode.AVAILABLE),
            Seat(20, 808, 296, Mode.AVAILABLE),
            Seat(21, 808, 351, Mode.AVAILABLE),
            Seat(22, 808, 405, Mode.AVAILABLE),
            Seat(23, 808, 459, Mode.AVAILABLE),
            Seat(24, 682, 459, Mode.AVAILABLE),
            Seat(25, 682, 405, Mode.AVAILABLE),
            Seat(26, 682, 351, Mode.AVAILABLE),
            Seat(27, 682, 296, Mode.AVAILABLE),
            Seat(28, 682, 240, Mode.AVAILABLE),
            Seat(29, 682, 186, Mode.AVAILABLE),
            Seat(30, 682, 131, Mode.AVAILABLE),
            Seat(31, 682, 77, Mode.AVAILABLE),
            // Table 4
            Seat(32, 942, 30, Mode.AVAILABLE),
            Seat(33, 1003, 30, Mode.AVAILABLE),
            Seat(34, 997, 156, Mode.AVAILABLE),
            Seat(35, 997, 250, Mode.AVAILABLE),
            Seat(36, 1003, 374, Mode.AVAILABLE),
            Seat(37, 942, 374, Mode.AVAILABLE),
            Seat(38, 874, 156, Mode.AVAILABLE),
            Seat(39, 874, 250, Mode.AVAILABLE),
            // Table 5
            Seat(40, 1130, 30, Mode.AVAILABLE),
            Seat(41, 1222, 30, Mode.AVAILABLE),
            Seat(42, 1232, 156, Mode.AVAILABLE),
            Seat(43, 1232, 250, Mode.AVAILABLE),
            Seat(44, 1117, 250, Mode.AVAILABLE),
            Seat(45, 1117, 156, Mode.AVAILABLE),
            // Table 6
            Seat(46, 1304, 463, Mode.AVAILABLE),
            Seat(47, 1304, 403, Mode.AVAILABLE),
            // Table 7
            Seat(48, 1104, 444, Mode.AVAILABLE),
            Seat(49, 1164, 444, Mode.AVAILABLE),
            Seat(50, 1164, 554, Mode.AVAILABLE),
            Seat(51, 1104, 554, Mode.AVAILABLE),
            // Table 8
            Seat(52, 318, 444, Mode.AVAILABLE),
            Seat(53, 379, 444, Mode.AVAILABLE),
            Seat(54, 379, 554, Mode.AVAILABLE),
            Seat(55, 318, 554, Mode.AVAILABLE),
            // Table 9
            Seat(56, 160, 291, Mode.AVAILABLE),
            Seat(57, 160, 354, Mode.AVAILABLE),
            Seat(58, 160, 418, Mode.AVAILABLE),
            Seat(59, 160, 481, Mode.AVAILABLE),
            Seat(60, 30, 481, Mode.AVAILABLE),
            Seat(61, 30, 418, Mode.AVAILABLE),
            Seat(62, 30, 354, Mode.AVAILABLE),
            Seat(63, 30, 291, Mode.AVAILABLE),
        )
    }
}
