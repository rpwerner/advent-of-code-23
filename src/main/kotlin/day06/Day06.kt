package day06

import multiplyOf
import println
import readInput

fun main() {
  //  check(Day06(readInput("Day06/Day06_test")).solvePart1() == 288)
   // Day06(readInput("Day06/Day06")).solvePart1().println()

    check(Day06(readInput("Day06/Day06_test")).solvePart2() == 71503L)
    Day06(readInput("Day06/Day06")).solvePart2().println()
}

class Day06(input: List<String>) {

    private val timeAndDistancePart1 : List<Pair<Long, Long>> = parsePart1Input(input)
    private val timeAndDistancePart2 : Pair<Long, Long> = parsePart2Input(input)

    private fun parsePart1Input(input: List<String>): List<Pair<Long, Long>> {
        return readLine(0, input).zip(readLine(1, input))
    }

    private fun parsePart2Input(input: List<String>): Pair<Long, Long> {
        val time = readLineAsSingleNumber(input[0])
        val distance = readLineAsSingleNumber(input[1])
        return Pair(time, distance)
    }

    private fun readLine(position: Int, input: List<String>) : List<Long> {
        return input[position].substringAfter(":")
            .trim().split(" ")
            .filter { it.isNotBlank() }
            .map { it.toLong() }
    }

    private fun readLineAsSingleNumber(input: String) : Long {
        return input.substringAfter(":")
            .split(" ")
            .filter { it.isNotBlank() }
            .joinToString("") { it }.toLong()
    }

    private fun getTotalOfRecordBreaker(pair: Pair<Long, Long>) : Long {
        val recordBreaker : Long = (1 .. pair.first).fold(0) { acc, holdTime ->
            val timeLeftToMove = pair.first - holdTime
            val distance = timeLeftToMove * holdTime
            if(distance > pair.second) {
                acc + 1
            } else {
                acc + 0
            }
        }
        return recordBreaker
    }

    fun solvePart1() : Long {
        return timeAndDistancePart1.multiplyOf { pair ->
            getTotalOfRecordBreaker(pair).toInt()
        }.toLong()
    }

    fun solvePart2() : Long {
        return getTotalOfRecordBreaker(timeAndDistancePart2)
    }
}