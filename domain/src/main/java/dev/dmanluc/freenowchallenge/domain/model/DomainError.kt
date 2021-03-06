package dev.dmanluc.freenowchallenge.domain.model

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Definition of a hierachy of domain errors to work with
 *
 */
sealed class DomainError {
    data class HttpError(val code: Int, val body: String) : DomainError()
    data class NetworkError(val throwable: Throwable) : DomainError()
    data class UnknownError(val throwable: Throwable) : DomainError()
}