package day03

import isNumeric
import println
import readInput

val MATCH_SYMBOL_REGEX = "[^0-9.\\n]".toRegex()

var starPositions = mutableListOf<Pair<Int, Int>>()

fun main() {

    fun part1(matrixInput: List<List<String>>): Int {

        var totalSum = 0
        var lastY = 0

        matrixInput.forEachIndexed { x, lineList ->
            var currentNumberAsStr = ""
            val lineSize = lineList.size-1
            lineList.forEachIndexed { y, string ->

                if(string.isNumeric()) {
                    currentNumberAsStr += string
                    lastY = y

                    if(lineSize == y) {
                        // we have a number and we are in the end of line. Sum it up!
                        totalSum += getNumberForTotalSumIfValid(currentNumberAsStr, x, lastY, matrixInput)
                    }
                } else {
                    totalSum += getNumberForTotalSumIfValid(currentNumberAsStr, x, lastY, matrixInput)
                    currentNumberAsStr = ""
                    lastY = 0
                }
            }
        }

        return totalSum
    }

    fun part2(matrixInput: List<List<String>>): Int {
        var totalSum = 0

        starPositions.forEach {
            val x = it.first
            val y = it.second

            totalSum += multiplyAdjacentNumbersWhenPossible(x, y, matrixInput)
        }

        return totalSum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03/Day03_test")
    val testMatrixInput = transformToMatrix(testInput)
    check(part1(testMatrixInput) == 4361)
    check(part2(testMatrixInput) == 467835)

    val input = readInput("Day03/Day03")
    val matrixInput = transformToMatrix(input)
    part1(matrixInput).println()
    part2(matrixInput).println()
}

// PART 2
fun multiplyAdjacentNumbersWhenPossible(x: Int, y: Int, matrixInput: List<List<String>>): Int {

    val aboveLeft = readNumberFromPositionIfExists(x-1, y-1, matrixInput) // above left
    val above = readNumberFromPositionIfExists(x-1, y, matrixInput) // above
    val aboveRight = readNumberFromPositionIfExists(x-1, y+1, matrixInput) // above right

    val right = readNumberFromPositionIfExists(x, y+1, matrixInput) // right
    val left = readNumberFromPositionIfExists(x, y-1, matrixInput) // left

    val bellowLeft = readNumberFromPositionIfExists(x+1, y-1, matrixInput) // bellow left
    val bellow = readNumberFromPositionIfExists(x+1, y, matrixInput) // bellow
    val bellowRight = readNumberFromPositionIfExists(x+1, y+1, matrixInput) // bellow right

    val foundAdjacentNumbers = mutableListOf(aboveLeft, above, aboveRight, right, left, bellowLeft, bellow, bellowRight)
        .filter { it.isNotEmpty() }
        .distinct()
        .toCollection(mutableListOf())

    if(foundAdjacentNumbers.size == 2) {
        val first = foundAdjacentNumbers[0].toInt()
        val second = foundAdjacentNumbers[1].toInt()
        return first * second
    }

    return 0
}

fun readNumberFromPositionIfExists(x: Int, y: Int, matrixInput: List<List<String>>) : String {
    var foundNumber = ""

    // if not a number, return
    if(doesPositionExist(x,y, matrixInput) && !matrixInput[x][y].isNumeric()) {
        return ""
    }

    if(doesPositionExist(x,y, matrixInput) && matrixInput[x][y].isNumeric()) {
        // check right till no digit anymore
        foundNumber = lookRight(x, y, matrixInput)
    }

    if(doesPositionExist(x, y-1, matrixInput) && matrixInput[x][y-1].isNumeric()) {
        // check left till no digit anymore
        foundNumber = lookLeft(x, y-1, matrixInput) + foundNumber
    }

    return foundNumber
}

fun lookRight(x: Int, y: Int, matrixInput: List<List<String>>) : String {

    if(doesPositionExist(x,y, matrixInput) && matrixInput[x][y].isNumeric()) {
        // check right till no digit anymore
        return matrixInput[x][y] + lookRight(x, y+1, matrixInput)
    }

    return ""
}

fun lookLeft(x: Int, y: Int, matrixInput: List<List<String>>) : String {

    if(doesPositionExist(x,y, matrixInput) && matrixInput[x][y].isNumeric()) {
        // check right till no digit anymore
        return lookLeft(x, y-1, matrixInput) + matrixInput[x][y]
    }

    return ""
}



// PART 1
fun getNumberForTotalSumIfValid(currentNumberAsStr : String, x : Int, lastY : Int, matrixInput: List<List<String>>) : Int {

    if(currentNumberAsStr.isNotEmpty() && hasAdjacentSymbolInRange(x, lastY, currentNumberAsStr.length, matrixInput)) {
        val currentNumberAsDigit = currentNumberAsStr.toInt()
        //println("Summing: $currentNumberAsDigit")
        return currentNumberAsDigit
    }

    return 0;
}

fun hasAdjacentSymbolInRange(x: Int, y: Int, digitSize: Int, matrixInput: List<List<String>>): Boolean {
    val begin = y - digitSize +1 // posicao final - tamanho da string + 1 = y de inicio

    for(i in begin .. y) {

        val foundSymbol = isSymbolAtPosition(x-1, i-1, matrixInput) || // above left
                isSymbolAtPosition(x-1, i, matrixInput) || // above
                isSymbolAtPosition(x-1, i+1, matrixInput) || // above right

                isSymbolAtPosition(x, i+1, matrixInput) || // right
                isSymbolAtPosition(x, i-1, matrixInput)  || // left

                isSymbolAtPosition(x+1, i-1, matrixInput) ||  // bellow left
                isSymbolAtPosition(x+1, i, matrixInput) || // bellow
                isSymbolAtPosition(x+1, i+1, matrixInput) // bellow right

        if(foundSymbol) return true
    }
    return false
}

fun isSymbolAtPosition(x: Int, y: Int, matrixInput: List<List<String>>) : Boolean {
    if(doesPositionExist(x,y,matrixInput)) {
        return MATCH_SYMBOL_REGEX.matches(matrixInput[x][y])
    }
    return false
}

fun doesPositionExist(x: Int, y: Int, matrixInput: List<List<String>>) : Boolean {
    val matrixRows = matrixInput.size
    val matrixColumns = matrixInput[0].size

    if(x < 0 || y < 0 || x >= matrixRows || y >= matrixColumns) {
        return false
    }

    return true
}

fun transformToMatrix(input : List<String>) : List<List<String>> {
    val inputMatrix = mutableListOf<List<String>>()
    input.forEachIndexed {x, line ->
        val lineList = mutableListOf<String>()
        line.forEachIndexed { y, it ->
            if(it == '*') {
                starPositions.add(Pair(x, y))
            }
            lineList.add(it.toString())
        }
        inputMatrix.add(lineList)
    }

    return inputMatrix
}
