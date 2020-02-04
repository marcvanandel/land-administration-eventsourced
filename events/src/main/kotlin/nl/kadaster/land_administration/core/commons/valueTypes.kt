package nl.kadaster.land_administration.core.commons

enum class RightType {
    OWNERSHIP
}

open class ObjectIdentifier(val namespace: String, open val localId: String) {
    final override fun toString(): String {
        return "$namespace.$localId"
    }

    fun asString(): String = toString()
}

data class ObjectId(override val localId: String) : ObjectIdentifier(namespace, localId) {
    companion object {
        const val namespace: String = "OBJECT"
    }
}

data class RightId(override val localId: String) : ObjectIdentifier(namespace, localId) {
    companion object {
        const val namespace: String = "RIGHT"
    }
}

data class SubjectId(override val localId: String) : ObjectIdentifier(namespace, localId) {
    companion object {
        const val namespace: String = "SUBJECT"
    }
}

data class Share(val subjectId: SubjectId, val fraction: Fraction)

data class Fraction(val numerator: Int, val denominator: Int) : Comparable<Fraction> {

    fun decimal(): Double = numerator.toDouble() / denominator

    override fun compareTo(other: Fraction) = decimal().compareTo(other.decimal())

    override fun toString() = "$numerator/$denominator"

    //unary operators
    operator fun unaryMinus() = Fraction(-this.numerator, this.denominator)

    //binary operators
    operator fun plus(add: Fraction) =
            if (this.denominator == add.denominator) Fraction(this.numerator + add.numerator, denominator)
            else {
                val a = this * add.denominator
                val b = add * this.denominator
                Fraction(a.numerator + b.numerator, a.denominator)
            }

    operator fun times(num: Int) = Fraction(numerator * num, denominator * num)

    //increments
    operator fun dec() = Fraction(this.numerator - 1, this.denominator)


    //invoke convention
    operator fun invoke(prefix: String = "") = println(prefix + toString())
}


operator fun Fraction.get(ind: Int) =
        when (ind) {
            0 -> numerator
            1 -> denominator
            else -> IllegalArgumentException("Index must be 0 or 1")
        }

operator fun Fraction.inc() = Fraction(this.numerator + 1, this.denominator)

operator fun ClosedRange<Fraction>.iterator() =
        object : Iterator<Fraction> {
            var curr: Fraction = start
            override fun hasNext() = curr <= endInclusive
            override fun next() = curr++

        }
