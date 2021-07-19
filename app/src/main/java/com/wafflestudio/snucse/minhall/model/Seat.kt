package com.wafflestudio.snucse.minhall.model

data class Seat(
    val id: Int,
    val x: Int,
    val y: Int,
    val mode: Mode,
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
            Seat(1, 0, 186, Seat.Mode.AVAILABLE),
            Seat(2, 66, 120, Seat.Mode.AVAILABLE),
            Seat(3, 176, 102, Seat.Mode.AVAILABLE),
            Seat(4, 214, 202, Seat.Mode.AVAILABLE),
            Seat(5, 166, 255, Seat.Mode.AVAILABLE),
            Seat(6, 214, 301, Seat.Mode.AVAILABLE),
            Seat(7, 176, 399, Seat.Mode.AVAILABLE),
            Seat(8, 66, 395, Seat.Mode.AVAILABLE),
            Seat(9, 0, 331, Seat.Mode.AVAILABLE),
            // Table 2
            Seat(10, 337, 290, Seat.Mode.AVAILABLE),
            Seat(11, 385, 242, Seat.Mode.AVAILABLE),
            Seat(12, 430, 193, Seat.Mode.AVAILABLE),
            Seat(13, 501, 99, Seat.Mode.AVAILABLE),
            Seat(14, 570, 99, Seat.Mode.AVAILABLE),
            Seat(15, 570, 168, Seat.Mode.AVAILABLE),
            Seat(16, 838, 113, Seat.Mode.AVAILABLE),
            Seat(17, 838, 218, Seat.Mode.AVAILABLE),
            Seat(18, 732, 305, Seat.Mode.AVAILABLE),
            Seat(19, 732, 374, Seat.Mode.AVAILABLE),
            Seat(20, 534, 374, Seat.Mode.AVAILABLE),
            Seat(21, 479, 374, Seat.Mode.AVAILABLE),
            // Table 3
            Seat(22, 942, 0, Seat.Mode.AVAILABLE),
            Seat(23, 942, 87, Seat.Mode.AVAILABLE),
            Seat(24, 942, 172, Seat.Mode.AVAILABLE),
            Seat(25, 942, 257, Seat.Mode.AVAILABLE),
            Seat(26, 942, 343, Seat.Mode.AVAILABLE),
            // Table 4
            Seat(27, 1249, 107, Seat.Mode.AVAILABLE),
            Seat(28, 1203, 147, Seat.Mode.AVAILABLE),
            Seat(29, 1203, 196, Seat.Mode.AVAILABLE),
            Seat(30, 1248, 242, Seat.Mode.AVAILABLE),
            Seat(31, 1215, 405, Seat.Mode.AVAILABLE),
            Seat(32, 1134, 405, Seat.Mode.AVAILABLE),
            // Table 5
            Seat(33, 1352, 343, Seat.Mode.AVAILABLE),
            Seat(34, 1352, 263, Seat.Mode.AVAILABLE),
            Seat(35, 1352, 181, Seat.Mode.AVAILABLE),
            Seat(36, 1352, 101, Seat.Mode.AVAILABLE),
            Seat(37, 1513, 101, Seat.Mode.AVAILABLE),
            Seat(38, 1513, 181, Seat.Mode.AVAILABLE),
            Seat(39, 1513, 263, Seat.Mode.AVAILABLE),
            Seat(40, 1513, 343, Seat.Mode.AVAILABLE),
            // Table 6
            Seat(41, 1649, 255, Seat.Mode.AVAILABLE),
            Seat(42, 1602, 202, Seat.Mode.AVAILABLE),
            Seat(43, 1640, 103, Seat.Mode.AVAILABLE),
            Seat(44, 1751, 120, Seat.Mode.AVAILABLE),
            Seat(45, 1816, 186, Seat.Mode.AVAILABLE),
            Seat(46, 1816, 331, Seat.Mode.AVAILABLE),
            Seat(47, 1751, 395, Seat.Mode.AVAILABLE),
            Seat(48, 1640, 399, Seat.Mode.AVAILABLE),
            Seat(49, 1602, 301, Seat.Mode.AVAILABLE),
            // Table 7
            Seat(50, 128, 499, Seat.Mode.AVAILABLE),
            Seat(51, 183, 499, Seat.Mode.AVAILABLE),
            Seat(52, 238, 499, Seat.Mode.AVAILABLE),
            Seat(53, 293, 499, Seat.Mode.AVAILABLE),
            Seat(54, 293, 643, Seat.Mode.AVAILABLE),
            Seat(55, 238, 643, Seat.Mode.AVAILABLE),
            Seat(56, 183, 643, Seat.Mode.AVAILABLE),
            Seat(57, 128, 643, Seat.Mode.AVAILABLE),
            // Table 8
            Seat(58, 69, 744, Seat.Mode.AVAILABLE),
            Seat(59, 125, 770, Seat.Mode.AVAILABLE),
            Seat(60, 182, 770, Seat.Mode.AVAILABLE),
            Seat(61, 239, 770, Seat.Mode.AVAILABLE),
            Seat(62, 296, 770, Seat.Mode.AVAILABLE),
            Seat(63, 296, 895, Seat.Mode.AVAILABLE),
            Seat(64, 239, 895, Seat.Mode.AVAILABLE),
            Seat(65, 182, 895, Seat.Mode.AVAILABLE),
            Seat(66, 125, 895, Seat.Mode.AVAILABLE),
            Seat(67, 69, 895, Seat.Mode.AVAILABLE),
            // Table 9
            Seat(68, 379, 612, Seat.Mode.AVAILABLE),
            Seat(69, 480, 612, Seat.Mode.AVAILABLE),
            Seat(70, 480, 706, Seat.Mode.AVAILABLE),
            Seat(71, 379, 706, Seat.Mode.AVAILABLE),
            // Table 10
            Seat(72, 697, 654, Seat.Mode.AVAILABLE),
            Seat(73, 697, 599, Seat.Mode.AVAILABLE),
            Seat(74, 697, 543, Seat.Mode.AVAILABLE),
            Seat(75, 697, 488, Seat.Mode.AVAILABLE),
            Seat(76, 841, 488, Seat.Mode.AVAILABLE),
            Seat(77, 841, 543, Seat.Mode.AVAILABLE),
            Seat(78, 841, 599, Seat.Mode.AVAILABLE),
            Seat(79, 841, 654, Seat.Mode.AVAILABLE),
            // Table 11
            Seat(80, 949, 654, Seat.Mode.AVAILABLE),
            Seat(81, 949, 599, Seat.Mode.AVAILABLE),
            Seat(82, 949, 543, Seat.Mode.AVAILABLE),
            Seat(83, 949, 488, Seat.Mode.AVAILABLE),
            Seat(84, 1094, 488, Seat.Mode.AVAILABLE),
            Seat(85, 1094, 543, Seat.Mode.AVAILABLE),
            Seat(86, 1094, 599, Seat.Mode.AVAILABLE),
            Seat(87, 1094, 654, Seat.Mode.AVAILABLE),
            // Table 12
            Seat(88, 1094, 744, Seat.Mode.AVAILABLE),
            Seat(89, 1150, 770, Seat.Mode.AVAILABLE),
            Seat(90, 1207, 770, Seat.Mode.AVAILABLE),
            Seat(91, 1264, 770, Seat.Mode.AVAILABLE),
            Seat(92, 1321, 770, Seat.Mode.AVAILABLE),
            Seat(93, 1321, 895, Seat.Mode.AVAILABLE),
            Seat(94, 1264, 895, Seat.Mode.AVAILABLE),
            Seat(95, 1207, 895, Seat.Mode.AVAILABLE),
            Seat(96, 1150, 895, Seat.Mode.AVAILABLE),
            Seat(97, 1095, 895, Seat.Mode.AVAILABLE),
            // Table 13
            Seat(98, 1361, 612, Seat.Mode.AVAILABLE),
            Seat(99, 1462, 612, Seat.Mode.AVAILABLE),
            Seat(100, 1462, 706, Seat.Mode.AVAILABLE),
            Seat(101, 1361, 706, Seat.Mode.AVAILABLE),
            // Table 14
            Seat(102, 1526, 499, Seat.Mode.AVAILABLE),
            Seat(103, 1581, 499, Seat.Mode.AVAILABLE),
            Seat(104, 1636, 499, Seat.Mode.AVAILABLE),
            Seat(105, 1692, 499, Seat.Mode.AVAILABLE),
            Seat(106, 1692, 643, Seat.Mode.AVAILABLE),
            Seat(107, 1636, 643, Seat.Mode.AVAILABLE),
            Seat(108, 1581, 643, Seat.Mode.AVAILABLE),
            Seat(109, 1526, 643, Seat.Mode.AVAILABLE),
            // Table 15
            Seat(110, 1526, 717, Seat.Mode.AVAILABLE),
            Seat(111, 1581, 717, Seat.Mode.AVAILABLE),
            Seat(112, 1636, 717, Seat.Mode.AVAILABLE),
            Seat(113, 1692, 717, Seat.Mode.AVAILABLE),
            Seat(114, 1692, 861, Seat.Mode.AVAILABLE),
            Seat(115, 1636, 861, Seat.Mode.AVAILABLE),
            Seat(116, 1581, 861, Seat.Mode.AVAILABLE),
            Seat(117, 1526, 861, Seat.Mode.AVAILABLE),
            // Table 16
            Seat(118, 1526, 935, Seat.Mode.AVAILABLE),
            Seat(119, 1581, 935, Seat.Mode.AVAILABLE),
            Seat(120, 1636, 935, Seat.Mode.AVAILABLE),
            Seat(121, 1692, 935, Seat.Mode.AVAILABLE),
            Seat(122, 1692, 1078, Seat.Mode.AVAILABLE),
            Seat(123, 1636, 1078, Seat.Mode.AVAILABLE),
            Seat(124, 1581, 1078, Seat.Mode.AVAILABLE),
            Seat(125, 1526, 1078, Seat.Mode.AVAILABLE),
        )
    }
}
