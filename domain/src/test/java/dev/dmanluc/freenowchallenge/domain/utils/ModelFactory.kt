package dev.dmanluc.freenowchallenge.domain.utils

import kotlin.random.Random

interface ModelFactory<T> {

    fun createOne(): T

    fun createMany(amount: Int? = null): List<T> =
        (1..(amount ?: Random.nextInt(2, 10))).map { createOne() }
}
