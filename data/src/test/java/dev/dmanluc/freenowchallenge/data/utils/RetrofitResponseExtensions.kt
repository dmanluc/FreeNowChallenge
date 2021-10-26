package dev.dmanluc.freenowchallenge.data.utils

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

fun <T> createSuccessResponse(code: Int, bodyContent: T): Response<T> {
    return Response.success(
        400,
        bodyContent
    )
}

fun createErrorResponse(code: Int, bodyContent: String): Response<String> {
    return Response.error(
        code,
        bodyContent.toResponseBody()
    )
}