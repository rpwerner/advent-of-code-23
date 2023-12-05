package day03

import isNumeric
import println
import readInput

fun main() {
    var digitsMap = mutableMapOf<Int,MutableList<Item>>()
    var symbolsMap = mutableMapOf<Int,MutableList<Item>>()

    fun readAdjacentDigits(listOfIndexes: MutableList<Int>, line: Int): Int {
        var sum = 0
        if(digitsMap.containsKey(line)) {
            val digitsList = digitsMap[line]
            digitsList!!.forEach {
                if(it.listOfIndexes.any { it in listOfIndexes }) {
                    sum +=  it.value.toInt()
                }
            }
        }
        return sum
    }

    fun getNumbersFromAdjacentIndexes(symbolsList: List<Item>, line: Int) : Int {
        return symbolsList.sumOf {
            readAdjacentDigits(it.listOfIndexes, line-1) + 
            readAdjacentDigits(it.listOfIndexes, line) +
            readAdjacentDigits(it.listOfIndexes, line+1)
        }
    }

    /**
     * find number and mark where it starts and end
     *
     * find symbol and mark its index, +1 and -1
     *
     * find matches
     */
    fun part1(input: List<String>): Int {

        var digitsList = mutableListOf<Item>()
        var symbolsList = mutableListOf<Item>()

        // read all lines
        var lineNumber = 1
        input.forEach { line ->

            // find all numbers
            Regex("(\\d+)").findAll(line)
                .map { it.groupValues[1] }
                .forEach {
                    digitsList.add(Item(it, line.indexOf(it)))
                }

            // find all symbols
            Regex("([^0-9.|^\\n])").findAll(line)
                .map { it.groupValues[1] }
                .forEach {
                    symbolsList.add(Item(it, line.indexOf(it)))
                }

            digitsMap[lineNumber] = digitsList
            symbolsMap[lineNumber] = symbolsList
            lineNumber ++

            digitsList = mutableListOf()
            symbolsList = mutableListOf()
        }

        // find matching numbers
        var total = 0
        symbolsMap.forEach { entry ->

            if(entry.value.isNotEmpty()) {
                total += getNumbersFromAdjacentIndexes(entry.value, entry.key)
            }

        }

        return total
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03/Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03/Day03")
    part1(input).println()
    //part2(input).println()
}

class Item(
        val value: String,
        val index: Int
        ) {

    var listOfIndexes = mutableListOf<Int>()

    init {
        if(this.value.isNumeric()) {
            for (i in value.indices) {
                listOfIndexes.add(this.index + i)
            }
        } else {
            listOfIndexes.add(this.index - 1)
            listOfIndexes.add(this.index)
            listOfIndexes.add(this.index + 1)
        }
    }

}