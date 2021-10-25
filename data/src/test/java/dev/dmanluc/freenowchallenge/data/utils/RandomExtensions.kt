package dev.dmanluc.freenowchallenge.data.utils

import kotlin.random.Random

private val CHAR_POOL = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun Random.nextString(length: Int = 16): String =
    (1..length)
        .map { Random.nextInt(0, CHAR_POOL.size) }
        .map(CHAR_POOL::get)
        .joinToString("")

fun <T> Random.anyOf(values: List<T>): T =
    values[Random.nextInt(values.size)]

fun <T> Random.anyOf(values: Array<T>): T =
    values[Random.nextInt(values.size)]

inline fun <reified T : Enum<*>> Random.next(): T =
    anyOf(T::class.java.enumConstants!!)
