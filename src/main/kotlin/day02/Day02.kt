package day02

import println
import readInput

const val RED_COUNT = 12
const val GREEN_COUNT = 13
const val BLUE_COUNT = 14

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { readMatchinGames(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { readPowerOfCubes(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02/Day02_test")
    val gameIds = part1(testInput)
    check(gameIds== 8)

    val powerOfCubes = part2(testInput)
    check(powerOfCubes== 2286)

    val input = readInput("Day02/Day02")
    part1(input).println()
    part2(input).println()
}

// Part 1
fun readMatchinGames(gameString : String) : Int {
    val matchGameNumber = Regex("Game (\\d+).*").find(gameString)!!
    var gameNumber = matchGameNumber.groupValues[1].toInt()
    val sets = getGameSets(gameString)

    sets.forEach {
        val redCubes = getNumberByCubeColor(it, "red")
        val greenCubes = getNumberByCubeColor(it, "green")
        val blueCubes = getNumberByCubeColor(it, "blue")

        if (redCubes > RED_COUNT || greenCubes > GREEN_COUNT || blueCubes > BLUE_COUNT) {
            gameNumber = 0
        }
    }

    return gameNumber
}

fun getNumberByCubeColor(gameString : String, color : String) : Int {
    return Regex("(\\d+) $color").find(gameString)?.groupValues.let {
        it?.get(1)?.toInt()
    }?: 0
}

fun getGameSets(gameString: String) : List<String> {
    return gameString.split(":")[1].split(";")
}

// Part 2
fun readPowerOfCubes(gameString : String) : Int {
    val maxRed = getHighestValueByCubeColor(gameString, "red")
    val maxGreen = getHighestValueByCubeColor(gameString, "green")
    val maxBlue = getHighestValueByCubeColor(gameString, "blue")

    return maxRed.times(maxGreen).times(maxBlue)
}

fun getHighestValueByCubeColor(gameString: String, color: String) : Int {
    return Regex("(\\d+) $color").findAll(gameString)
        .map { it.groupValues[1] }
        .maxBy { it.toInt() }.toInt()
}