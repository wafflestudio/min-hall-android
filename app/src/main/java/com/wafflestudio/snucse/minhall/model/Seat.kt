package com.wafflestudio.snucse.minhall.model

data class Seat(
    val id: Int,
    val x: Int,
    val y: Int,
    val rotation: Float = 0f,
    val mode: Mode = Mode.AVAILABLE,
) {
    enum class Mode {
        AVAILABLE,
        SELECTED,
        TAKEN,
        DISABLED
    }

    override fun equals(other: Any?): Boolean = other is Seat &&
            other.id == id &&
            other.x == x &&
            other.y == y &&
            other.rotation == rotation

    override fun hashCode(): Int = id

    fun toggleSelect() = when (mode) {
        Mode.AVAILABLE -> copy(mode = Mode.SELECTED)
        Mode.SELECTED -> copy(mode = Mode.AVAILABLE)
        else -> this
    }

    val isSelected: Boolean
        get() = mode == Mode.SELECTED

    companion object {
        fun seats() = listOf(
            // Table 1
            Seat(1, 183, 170, -30f),
            Seat(2, 224, 147, -30f),
            // Table 2
            Seat(3, 347, 30),
            Seat(4, 415, 30),
            Seat(5, 483, 30),
            Seat(6, 544, 100),
            Seat(7, 544, 269),
            Seat(8, 544, 348),
            Seat(9, 403, 348),
            Seat(10, 358, 348),
            Seat(11, 288, 248),
            Seat(12, 429, 216),
            Seat(13, 429, 171),
            Seat(14, 380, 147),
            Seat(15, 335, 147),
            // Table 3
            Seat(16, 808, 77),
            Seat(17, 808, 131),
            Seat(18, 808, 186),
            Seat(19, 808, 240),
            Seat(20, 808, 296),
            Seat(21, 808, 351),
            Seat(22, 808, 405),
            Seat(23, 808, 459),
            Seat(24, 682, 459),
            Seat(25, 682, 405),
            Seat(26, 682, 351),
            Seat(27, 682, 296),
            Seat(28, 682, 240),
            Seat(29, 682, 186),
            Seat(30, 682, 131),
            Seat(31, 682, 77),
            // Table 4
            Seat(32, 942, 30),
            Seat(33, 1003, 30),
            Seat(34, 997, 156),
            Seat(35, 997, 250),
            Seat(36, 1003, 374),
            Seat(37, 942, 374),
            Seat(38, 874, 156),
            Seat(39, 874, 250),
            // Table 5
            Seat(40, 1130, 30),
            Seat(41, 1222, 30),
            Seat(42, 1232, 156),
            Seat(43, 1232, 250),
            Seat(44, 1117, 250),
            Seat(45, 1117, 156),
            // Table 6
            Seat(46, 1304, 463),
            Seat(47, 1304, 403),
            // Table 7
            Seat(48, 1104, 444),
            Seat(49, 1164, 444),
            Seat(50, 1164, 554),
            Seat(51, 1104, 554),
            // Table 8
            Seat(52, 318, 444),
            Seat(53, 379, 444),
            Seat(54, 379, 554),
            Seat(55, 318, 554),
            // Table 9
            Seat(56, 160, 291),
            Seat(57, 160, 354),
            Seat(58, 160, 418),
            Seat(59, 160, 481),
            Seat(60, 30, 481),
            Seat(61, 30, 418),
            Seat(62, 30, 354),
            Seat(63, 30, 291),
        )
    }
}
