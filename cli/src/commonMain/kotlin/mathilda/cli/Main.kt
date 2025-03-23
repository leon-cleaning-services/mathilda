package mathilda.cli

import mathilda.Library

object Main {

    fun run(args: Array<String>) {
        val library = Library()
        println("Hello world ${library.someLibraryMethod()}")
    }
}