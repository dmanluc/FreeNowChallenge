package dev.dmanluc.freenowchallenge.domain.model

import arrow.core.Either

typealias DomainResult<T> = Either<DomainError, T>