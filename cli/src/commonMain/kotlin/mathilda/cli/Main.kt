package mathilda.cli

import mathilda.builder

object Main {

    fun run(args: Array<String>) {
        val json = """
            {
                "version":1,
                "rules": {
                    "google": {
                        "type": "remove",
                        "parameters": ["utm_","ga_"],
                    },
                    "google2": {
                        "type":"remove_regex",
                        "regex": "regex",
                    }
                }
            }
        """.trimIndent()

        val mathilda = builder(json).build()
        //mathilda.clean("")

        println("${mathilda.rules.size}")
    }
}
