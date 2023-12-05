package day03

import isNumeric
import println
import readInput
import kotlin.math.sign

fun main() {

    fun hasSymbolHere(x: Int, y: Int, matrixInput: List<List<String>>) : Boolean {
        return true
    }

    fun hasAdjacentSymbol(x: Int, y: Int, matrixInput: List<List<String>>): Boolean {
        println("$x $y")
        val stringSize = x + y + 1



        hasSymbolHere(x, y, matrixInput)
        hasSymbolHere(x, y, matrixInput)
        hasSymbolHere(x, y, matrixInput)
        hasSymbolHere(x, y, matrixInput)
        hasSymbolHere(x, y, matrixInput)
        hasSymbolHere(x, y, matrixInput)
        hasSymbolHere(x, y, matrixInput)
        hasSymbolHere(x, y, matrixInput)

        return true
    }

    fun part1(matrixInput: List<List<String>>): Int {
        var totalSum = 0
        var lastY = 0
        matrixInput.forEachIndexed { x, lineList ->
            var currentNumberAsStr = ""

            lineList.forEachIndexed { y, string ->

                if(string.isNumeric()) {
                    currentNumberAsStr += string
                    lastY = y
                } else {
                    // not number at all or anymore
                    if(currentNumberAsStr.isNotEmpty() && hasAdjacentSymbol(x, lastY, matrixInput)) {
                        val currentNumberAsDigit = currentNumberAsStr.toInt()
                        totalSum += currentNumberAsDigit
                        currentNumberAsStr = ""
                    }
                }
            }
        }

        return totalSum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03/Day03_test")
    val testMatrixInput = transformToMatrix(testInput)
    //check(part1(testMatrixInput) == 4361)
    check(part1(testMatrixInput) == 4533)

    val input = readInput("Day03/Day03")
    val matrixInput = transformToMatrix(testInput)
    part1(matrixInput).println()
    //part2(input).println()
}

fun transformToMatrix(input : List<String>) : List<List<String>> {

    var inputMatrix = mutableListOf<List<String>>()

    input.forEach {line ->
        var lineList = mutableListOf<String>()
        line.forEach {
            lineList.add(it.toString())
        }
        inputMatrix.add(lineList)
    }

    runCatching {
        println(inputMatrix[0][10])
    }.onFailure {

    }

    return inputMatrix
}
