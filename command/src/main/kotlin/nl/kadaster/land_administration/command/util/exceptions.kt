package nl.kadaster.land_administration.command.util

import nl.kadaster.land_administration.command.model.Fraction

open class CommandException(override val message: String, override val cause: Throwable? = null) : RuntimeException(message, cause)

class SharesTotalNotValid(sum: Fraction) : CommandException("Shares don't count up to 1/1 faction in total (but was [$sum])")
