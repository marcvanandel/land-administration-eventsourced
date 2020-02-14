package nl.kadaster.land_administration.core.commons

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
