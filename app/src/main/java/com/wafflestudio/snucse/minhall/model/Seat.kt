package com.wafflestudio.snucse.minhall.model

data class Seat(
    val id: String,
    val mode: Mode = Mode.AVAILABLE,
) {
    enum class Mode {
        AVAILABLE,
        SELECTED,
        TAKEN,
        DISABLED
    }

    override fun equals(other: Any?): Boolean = other is Seat &&
            other.id == id

    override fun hashCode(): Int = id.hashCode()

    fun toggleSelect() = when (mode) {
        Mode.AVAILABLE -> copy(mode = Mode.SELECTED)
        Mode.SELECTED -> copy(mode = Mode.AVAILABLE)
        else -> this
    }

    val isSelected: Boolean
        get() = mode == Mode.SELECTED
}
