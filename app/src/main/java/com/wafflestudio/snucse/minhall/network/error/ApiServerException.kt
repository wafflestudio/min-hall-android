package com.wafflestudio.snucse.minhall.network.error

import java.io.IOException

class ApiServerException(
    val body: ErrorResponseBody,
) : IOException()
