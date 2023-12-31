
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/main/resources/$name.txt").readLines()

/**
 * Converts string to kotlin.md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)


fun String.isNumeric(): Boolean {
    return this.all { char -> char.isDigit() }
}

inline fun <T> Iterable<T>.multiplyOf(selector: (T) -> Int): Int {
    var multiply = 1
    for (element in this) {
        multiply *= selector(element)
    }
    return multiply
}

fun Map<Long, Long>.getOrSameValue(v: Long): Long {
    if(!this.containsKey(v)) {
        return v
    }

    return this[v]!!
}