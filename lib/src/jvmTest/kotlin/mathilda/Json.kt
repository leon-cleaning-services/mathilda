package mathilda

val Any.jsonStream
    get() = javaClass.getResourceAsStream("/rules.json")
        ?: throw IllegalArgumentException("File rules.json not found")
