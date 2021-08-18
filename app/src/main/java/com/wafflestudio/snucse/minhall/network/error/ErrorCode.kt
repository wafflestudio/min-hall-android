package com.wafflestudio.snucse.minhall.network.error

import androidx.annotation.StringRes
import com.wafflestudio.snucse.minhall.R

enum class ErrorCode(
    val code: String,
    @StringRes val resId: Int = R.string.error_unknown,
) {
    UNKNOWN("0000"),
    MISSING_TOKEN("0001", R.string.error_missing_token),
    UNAUTHORIZED("0401", R.string.error_unauthorized),
    FORBIDDEN("0403", R.string.error_forbidden),
    NETWORK("0404", R.string.error_network),
    SERVER_ERROR("0500", R.string.error_server_error),
    INVALID_CODE("C001"),
    RESOURCE_NOT_FOUND("C002"),
    EXPIRED_CODE("C003"),
    PARSE_EXCEPTION("C004"),
    MESSAGE_LENGTH_EXCESS("C005"),
    USER_NAME_INVALID("C006"),
    TIME_INVALID("C007", R.string.error_time_invalid),
    // User
    USER_NOT_FOUND("U001"),
    STUDENT_UNAVAILABLE("U004", R.string.error_student_unavailable),
    STUDENT_UNAUTHORIZED("U005", R.string.error_student_unauthorized),
    // Reservation
    RESERVATION_NOT_FOUND("R001", R.string.error_reservation_not_found),
    TODAY_RESERVATION_NOT_FOUND("R002", R.string.error_today_reservation_not_found),
    RESERVATION_NUMBER_EXCEED("R003", R.string.error_reservation_number_exceed),
    RESERVATION_TIME_INVALID("R004", R.string.error_reservation_time_invalid),
    RESERVATION_TIME_TOO_EARLY("R005", R.string.error_time_too_early),
    // Seat
    SEAT_NOT_FOUND("S001", R.string.error_seat_not_found),
    SEAT_UNAVAILABLE("S003", R.string.error_seat_unavailable),
    // Login
    PASSWORD_NOT_VALID("L001", R.string.error_password_not_valid),
    TOKEN_ERROR("L002", R.string.error_token_error),
    LOGIN_FAILURE("L003", R.string.error_login_failure);
}
