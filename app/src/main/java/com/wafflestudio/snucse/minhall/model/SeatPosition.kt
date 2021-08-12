package com.wafflestudio.snucse.minhall.model

data class SeatPosition(
    val x: Int,
    val y: Int,
    val rotation: Float = 0f,
) {
    companion object {
        val seatIdToPosition = mapOf(
            "A1" to SeatPosition(30, 291),
            "A2" to SeatPosition(30, 354),
            "A3" to SeatPosition(30, 418),
            "A4" to SeatPosition(30, 481),
            "A5" to SeatPosition(160, 291),
            "A6" to SeatPosition(160, 354),
            "A7" to SeatPosition(160, 418),
            "A8" to SeatPosition(160, 481),

            "B1" to SeatPosition(183, 170, -30f),
            "B2" to SeatPosition(224, 147, -30f),

            "C1" to SeatPosition(318, 444),
            "C2" to SeatPosition(379, 444),
            "C3" to SeatPosition(318, 554),
            "C4" to SeatPosition(379, 554),

            "D1" to SeatPosition(347, 30),
            "D2" to SeatPosition(415, 30),
            "D3" to SeatPosition(483, 30),
            "D4" to SeatPosition(544, 100),
            "D5" to SeatPosition(544, 269),
            "D6" to SeatPosition(544, 348),
            "D7" to SeatPosition(335, 147),
            "D8" to SeatPosition(380, 147),
            "D9" to SeatPosition(279, 256),
            "D10" to SeatPosition(321, 214),
            "D11" to SeatPosition(429, 171),
            "D12" to SeatPosition(429, 216),
            "D13" to SeatPosition(358, 348),
            "D14" to SeatPosition(403, 348),

            "E1" to SeatPosition(682, 77),
            "E2" to SeatPosition(682, 131),
            "E3" to SeatPosition(682, 186),
            "E4" to SeatPosition(682, 240),
            "E5" to SeatPosition(682, 296),
            "E6" to SeatPosition(682, 351),
            "E7" to SeatPosition(682, 405),
            "E8" to SeatPosition(682, 459),
            "E9" to SeatPosition(808, 77),
            "E10" to SeatPosition( 808, 131),
            "E11" to SeatPosition( 808, 186),
            "E12" to SeatPosition( 808, 240),
            "E13" to SeatPosition( 808, 296),
            "E14" to SeatPosition( 808, 351),
            "E15" to SeatPosition(808, 405),
            "E16" to SeatPosition(808, 459),

            "F1" to SeatPosition( 942, 30),
            "F2" to SeatPosition( 1003, 30),
            "F3" to SeatPosition( 874, 156),
            "F4" to SeatPosition( 997, 156),
            "F5" to SeatPosition( 874, 250),
            "F6" to SeatPosition( 997, 250),
            "F7" to SeatPosition( 942, 374),
            "F8" to SeatPosition( 1003, 374),

            "G1" to SeatPosition( 1130, 30),
            "G2" to SeatPosition( 1222, 30),
            "G3" to SeatPosition( 1117, 156),
            "G4" to SeatPosition( 1232, 156),
            "G5" to SeatPosition( 1117, 250),
            "G6" to SeatPosition( 1232, 250),

            "I1" to SeatPosition(1304, 463),
            "I2" to SeatPosition(1304, 403),

            "H1" to SeatPosition( 1104, 444),
            "H2" to SeatPosition( 1164, 444),
            "H3" to SeatPosition( 1104, 554),
            "H4" to SeatPosition( 1164, 554),
        )
    }
}
