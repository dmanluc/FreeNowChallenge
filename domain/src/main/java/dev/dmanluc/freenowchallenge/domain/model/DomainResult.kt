package dev.dmanluc.freenowchallenge.domain.model

import arrow.core.Either

typealias DomainResult<T> = Either<Throwable, T>