package com.wafflestudio.snucse.minhall.network.error

data class ErrorResponseBody(
    val message: String?,
    val status: Int?,
    private val code: String?,
) {
    val errorCode: ErrorCode
        get() = ErrorCode.values().firstOrNull { it.code == code } ?: ErrorCode.UNKNOWN
}
