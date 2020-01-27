package nl.kadaster.land_administration.coreapi

enum class RightType {
    OWNERSHIP
}

//data class ObjectIdentifier(val namespace: String, val localId: String)
open class ObjectIdentifier(val namespace: String, open val localId: Long) {
    fun asString(): String {
        return "${namespace}.${localId.toString()}"
    }
}

data class ObjectId(override val localId: Long) : ObjectIdentifier("OBJECT", localId)
data class RightId(override val localId: Long) : ObjectIdentifier("RIGHT", localId)
data class SubjectId(override val localId: Long) : ObjectIdentifier("SUBJECT", localId)

data class Share(val subjectId: SubjectId, val fraction: Fraction)

data class Fraction(val numerator: Int, val denominator: Int) : Comparable<Fraction> {

    val decimal by lazy { numerator.toDouble() / denominator }

    override fun compareTo(other: Fraction) = decimal.compareTo(other.decimal)

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

