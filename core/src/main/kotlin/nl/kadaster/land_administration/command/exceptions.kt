package nl.kadaster.land_administration.command

import nl.kadaster.land_administration.coreapi.Fraction

open class DomainException(override val message: String, override val cause: Throwable? = null) : RuntimeException(message, cause)

class SharesTotalNotValid(sum: Fraction) : DomainException("Shares don't count up to 1/1 faction in total (but was [$sum])")
