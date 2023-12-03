package day01

/**
 * https://adventofcode.com/2023/day/1
 */

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { readFirstAndLastDigit(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { readFirstAndLastWrittenDigit(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01/Day01_test")
    check(part1(testInput) == 142)

    val testInput2 = readInput("Day01/Day01_test_p2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01/Day01")
    part1(input).println()
    part2(input).println()
}

fun readFirstAndLastWrittenDigit(fileLine: String): Int {
    val writtenNumbers = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    )

    val firstWrittenDigit = fileLine.findAnyOf(writtenNumbers)?.second.toString()
    val lastWrittenDigit = fileLine.findLastAnyOf(writtenNumbers)?.second.toString()

    val firstDigit = NumberToDigit.from(firstWrittenDigit)
    val lastDigit = NumberToDigit.from(lastWrittenDigit)

    return "$firstDigit$lastDigit".toInt()
}

fun readFirstAndLastDigit(fileLine: String): Int {
    val first = fileLine.first { ch -> ch in '0'..'9' }
    val last = fileLine.last { ch -> ch in '0'..'9' }
    return "$first$last".toInt()
}

enum class NumberToDigit(val number: Int) {
    one(1),
    two(2),
    three(3),
    four(4),
    five(5),
    six(6),
    seven(7),
    eight(8),
    nine(9);

    companion object {
        infix fun from(value: String): String {
            NumberToDigit.values().onEach {
                if (it.toString() == value) {
                    return it.number.toString()
                }
            }
            return value
        }
    }
}
