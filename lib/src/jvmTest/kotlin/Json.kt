val Any.jsonStream
    get() = javaClass.getResourceAsStream("/test.json")
        ?: throw IllegalArgumentException("File test.json not found")
