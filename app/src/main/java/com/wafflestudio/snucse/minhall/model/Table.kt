package com.wafflestudio.snucse.minhall.model

data class Table(
    val index: Int,
    val seats: List<Seat>,
)

val tables = listOf(
    Table(
        1, listOf(
            Seat(1, 0, 186, Seat.Mode.AVAILABLE),
            Seat(2, 66, 120, Seat.Mode.AVAILABLE),
            Seat(3, 176, 102, Seat.Mode.AVAILABLE),
            Seat(4, 214, 202, Seat.Mode.AVAILABLE),
            Seat(5, 166, 255, Seat.Mode.AVAILABLE),
            Seat(6, 214, 301, Seat.Mode.AVAILABLE),
            Seat(7, 176, 399, Seat.Mode.AVAILABLE),
            Seat(8, 66, 395, Seat.Mode.AVAILABLE),
            Seat(9, 0, 331, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        2, listOf(
            Seat(1, 337, 290, Seat.Mode.AVAILABLE),
            Seat(2, 385, 242, Seat.Mode.AVAILABLE),
            Seat(3, 430, 193, Seat.Mode.AVAILABLE),
            Seat(4, 501, 99, Seat.Mode.AVAILABLE),
            Seat(5, 570, 99, Seat.Mode.AVAILABLE),
            Seat(6, 570, 168, Seat.Mode.AVAILABLE),
            Seat(7, 838, 113, Seat.Mode.AVAILABLE),
            Seat(8, 838, 218, Seat.Mode.AVAILABLE),
            Seat(9, 732, 305, Seat.Mode.AVAILABLE),
            Seat(10, 732, 374, Seat.Mode.AVAILABLE),
            Seat(11, 534, 374, Seat.Mode.AVAILABLE),
            Seat(12, 479, 374, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        3, listOf(
            Seat(1, 942, 0, Seat.Mode.AVAILABLE),
            Seat(2, 942, 87, Seat.Mode.AVAILABLE),
            Seat(3, 942, 172, Seat.Mode.AVAILABLE),
            Seat(4, 942, 257, Seat.Mode.AVAILABLE),
            Seat(5, 942, 343, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        4, listOf(
            Seat(1, 1249, 107, Seat.Mode.AVAILABLE),
            Seat(2, 1203, 147, Seat.Mode.AVAILABLE),
            Seat(3, 1203, 196, Seat.Mode.AVAILABLE),
            Seat(4, 1248, 242, Seat.Mode.AVAILABLE),
            Seat(5, 1215, 405, Seat.Mode.AVAILABLE),
            Seat(6, 1134, 405, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        5, listOf(
            Seat(1, 1352, 343, Seat.Mode.AVAILABLE),
            Seat(2, 1352, 263, Seat.Mode.AVAILABLE),
            Seat(3, 1352, 181, Seat.Mode.AVAILABLE),
            Seat(4, 1352, 101, Seat.Mode.AVAILABLE),
            Seat(5, 1513, 101, Seat.Mode.AVAILABLE),
            Seat(6, 1513, 181, Seat.Mode.AVAILABLE),
            Seat(7, 1513, 263, Seat.Mode.AVAILABLE),
            Seat(8, 1513, 343, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        6, listOf(
            Seat(1, 1649, 255, Seat.Mode.AVAILABLE),
            Seat(2, 1602, 202, Seat.Mode.AVAILABLE),
            Seat(3, 1640, 103, Seat.Mode.AVAILABLE),
            Seat(4, 1751, 120, Seat.Mode.AVAILABLE),
            Seat(5, 1816, 186, Seat.Mode.AVAILABLE),
            Seat(6, 1816, 331, Seat.Mode.AVAILABLE),
            Seat(7, 1751, 395, Seat.Mode.AVAILABLE),
            Seat(8, 1640, 399, Seat.Mode.AVAILABLE),
            Seat(9, 1602, 301, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        7, listOf(
            Seat(1, 128, 499, Seat.Mode.AVAILABLE),
            Seat(2, 183, 499, Seat.Mode.AVAILABLE),
            Seat(3, 238, 499, Seat.Mode.AVAILABLE),
            Seat(4, 293, 499, Seat.Mode.AVAILABLE),
            Seat(5, 293, 643, Seat.Mode.AVAILABLE),
            Seat(6, 238, 643, Seat.Mode.AVAILABLE),
            Seat(7, 183, 643, Seat.Mode.AVAILABLE),
            Seat(8, 128, 643, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        8, listOf(
            Seat(1, 69, 744, Seat.Mode.AVAILABLE),
            Seat(2, 125, 770, Seat.Mode.AVAILABLE),
            Seat(3, 182, 770, Seat.Mode.AVAILABLE),
            Seat(4, 239, 770, Seat.Mode.AVAILABLE),
            Seat(5, 296, 770, Seat.Mode.AVAILABLE),
            Seat(6, 296, 895, Seat.Mode.AVAILABLE),
            Seat(7, 239, 895, Seat.Mode.AVAILABLE),
            Seat(8, 182, 895, Seat.Mode.AVAILABLE),
            Seat(9, 125, 895, Seat.Mode.AVAILABLE),
            Seat(10, 69, 895, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        9, listOf(
            Seat(1, 379, 612, Seat.Mode.AVAILABLE),
            Seat(2, 480, 612, Seat.Mode.AVAILABLE),
            Seat(3, 480, 706, Seat.Mode.AVAILABLE),
            Seat(4, 379, 706, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        10, listOf(
            Seat(1, 697, 654, Seat.Mode.AVAILABLE),
            Seat(2, 697, 599, Seat.Mode.AVAILABLE),
            Seat(3, 697, 543, Seat.Mode.AVAILABLE),
            Seat(4, 697, 488, Seat.Mode.AVAILABLE),
            Seat(5, 841, 488, Seat.Mode.AVAILABLE),
            Seat(6, 841, 543, Seat.Mode.AVAILABLE),
            Seat(7, 841, 599, Seat.Mode.AVAILABLE),
            Seat(8, 841, 654, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        11, listOf(
            Seat(1, 949, 654, Seat.Mode.AVAILABLE),
            Seat(2, 949, 599, Seat.Mode.AVAILABLE),
            Seat(3, 949, 543, Seat.Mode.AVAILABLE),
            Seat(4, 949, 488, Seat.Mode.AVAILABLE),
            Seat(5, 1094, 488, Seat.Mode.AVAILABLE),
            Seat(6, 1094, 543, Seat.Mode.AVAILABLE),
            Seat(7, 1094, 599, Seat.Mode.AVAILABLE),
            Seat(8, 1094, 654, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        12, listOf(
            Seat(1, 1094, 744, Seat.Mode.AVAILABLE),
            Seat(2, 1150, 770, Seat.Mode.AVAILABLE),
            Seat(3, 1207, 770, Seat.Mode.AVAILABLE),
            Seat(4, 1264, 770, Seat.Mode.AVAILABLE),
            Seat(5, 1321, 770, Seat.Mode.AVAILABLE),
            Seat(6, 1321, 895, Seat.Mode.AVAILABLE),
            Seat(7, 1264, 895, Seat.Mode.AVAILABLE),
            Seat(8, 1207, 895, Seat.Mode.AVAILABLE),
            Seat(9, 1150, 895, Seat.Mode.AVAILABLE),
            Seat(10, 1095, 895, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        13, listOf(
            Seat(1, 1361, 612, Seat.Mode.AVAILABLE),
            Seat(2, 1462, 612, Seat.Mode.AVAILABLE),
            Seat(3, 1462, 706, Seat.Mode.AVAILABLE),
            Seat(4, 1361, 706, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        14, listOf(
            Seat(1, 1526, 499, Seat.Mode.AVAILABLE),
            Seat(2, 1581, 499, Seat.Mode.AVAILABLE),
            Seat(3, 1636, 499, Seat.Mode.AVAILABLE),
            Seat(4, 1692, 499, Seat.Mode.AVAILABLE),
            Seat(5, 1692, 643, Seat.Mode.AVAILABLE),
            Seat(6, 1636, 643, Seat.Mode.AVAILABLE),
            Seat(7, 1581, 643, Seat.Mode.AVAILABLE),
            Seat(8, 1526, 643, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        15, listOf(
            Seat(1, 1526, 717, Seat.Mode.AVAILABLE),
            Seat(2, 1581, 717, Seat.Mode.AVAILABLE),
            Seat(3, 1636, 717, Seat.Mode.AVAILABLE),
            Seat(4, 1692, 717, Seat.Mode.AVAILABLE),
            Seat(5, 1692, 861, Seat.Mode.AVAILABLE),
            Seat(6, 1636, 861, Seat.Mode.AVAILABLE),
            Seat(7, 1581, 861, Seat.Mode.AVAILABLE),
            Seat(8, 1526, 861, Seat.Mode.AVAILABLE),
        )
    ),
    Table(
        16, listOf(
            Seat(1, 1526, 935, Seat.Mode.AVAILABLE),
            Seat(2, 1581, 935, Seat.Mode.AVAILABLE),
            Seat(3, 1636, 935, Seat.Mode.AVAILABLE),
            Seat(4, 1692, 935, Seat.Mode.AVAILABLE),
            Seat(5, 1692, 1078, Seat.Mode.AVAILABLE),
            Seat(6, 1636, 1078, Seat.Mode.AVAILABLE),
            Seat(7, 1581, 1078, Seat.Mode.AVAILABLE),
            Seat(8, 1526, 1078, Seat.Mode.AVAILABLE),
        )
    ),
)
