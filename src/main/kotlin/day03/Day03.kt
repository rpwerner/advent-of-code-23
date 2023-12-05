package day03

import isNumeric
import println
import readInput

val MATCH_SYMBOL_REGEX = "[^0-9.]".toRegex()

fun main() {
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
                    if(currentNumberAsStr.isNotEmpty() && hasAdjacentSymbolInRange(x, lastY, matrixInput)) {
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
fun hasAdjacentSymbolInRange(x: Int, y: Int, matrixInput: List<List<String>>): Boolean {

    for(i in x .. y) {
        println("Base position $x,$i")
        val foundSymbol = isSymbolAtPosition(x-1, i-1, matrixInput) || // above left
                isSymbolAtPosition(x-1, i, matrixInput) || // above
                isSymbolAtPosition(x-1, i+1, matrixInput) || // above right

                isSymbolAtPosition(x, i+1, matrixInput) || // right
                isSymbolAtPosition(x, i-1, matrixInput)  || // left

                isSymbolAtPosition(x+1, i-1, matrixInput) ||  // bellow left
                isSymbolAtPosition(x+1, i, matrixInput) || // bellow
                isSymbolAtPosition(x+1, i+1, matrixInput) // bellow right

        if(foundSymbol) return foundSymbol
    }

    return false
}

fun isSymbolAtPosition(x: Int, y: Int, matrixInput: List<List<String>>) : Boolean {
    println("Checking position: $x,$y")
    if(doesPositionExist(x,y,matrixInput.size)) {
        return MATCH_SYMBOL_REGEX.matches(matrixInput[x][y])
    }

    return false
}

fun doesPositionExist(x: Int, y: Int, matrixSize: Int) : Boolean {
    if(x < 0 || y < 0) {
        return false
    } else if(x > matrixSize || y > matrixSize) {
        return false
    }

    return true
}

fun transformToMatrix(input : List<String>) : List<List<String>> {

    var inputMatrix = mutableListOf<List<String>>()
    var i = 0
    input.forEach {line ->
        var lineList = mutableListOf<String>()
        var j = 0
        line.forEach {
            print("| $i,$j |")
            lineList.add(it.toString())
            j++
        }
        println()
        i++
        inputMatrix.add(lineList)
    }

    runCatching {
        println(inputMatrix[0][10])
    }.onFailure {

    }

    return inputMatrix
}
