package day04

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { calculateScratchCardValue(it) }
    }

    fun part2(input: List<String>): Int {
        // map input to map
        var mapOfCards = mutableMapOf<Int, String>()
        input.forEach {line ->
            val number = readCardNumber(line)
            mapOfCards[number] = line
        }

        var fullCardList = input.toMutableList()

        var i = 0
        var fullSize = fullCardList.size
        while (i < fullSize) {
            val card = fullCardList[i]
            val cardNumber = readCardNumber(card)
            val myWinnerNumbers = getMyWinnerNumbers(card)
            val totalWinners = myWinnerNumbers.size

            if(totalWinners > 0) {
                for(j in 1 .. totalWinners) {
                    val copyTobeAdded = mapOfCards.get(j+cardNumber)
                    copyTobeAdded?.let {
                        fullCardList.add(copyTobeAdded)
                        fullSize = fullCardList.size
                    }
                }
            }
            i ++
        }

        return fullSize
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04/Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04/Day04")
    part1(input).println()
    part2(input).println()
}
// part 2
fun readCardNumber(line : String) : Int {
    return line.substringAfter("Card").substringBefore(":").trim().toInt()
}
fun getMyWinnerNumbers(card : String) : Set<Int> {
    val cardNumbers = card.substringAfter(":")
    val winningNumbers = getWinningNumbers(cardNumbers)
    val myNumbers = getMyNumbers(cardNumbers)
    return myNumbers.intersect(winningNumbers.toSet())
}

// part 1
fun calculateScratchCardValue(card : String) : Int {
    val intersectedList = getMyWinnerNumbers(card)

    var cardValue = 0

    for (i in 1 .. intersectedList.size) {
        if(cardValue == 0) {
            cardValue = 1
        } else {
            cardValue *= 2
        }
    }

    return cardValue
}

fun getWinningNumbers(cardNumbers : String) : List<Int> {
    return cardNumbers
        .substringBefore("|")
        .split(" ")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toCollection(mutableListOf())
}

fun getMyNumbers(cardNumbers : String) : List<Int> {
    return cardNumbers
        .substringAfter("|")
        .split(" ")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toCollection(mutableListOf())
}