package com.xcporter

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.File

object TreeParser {
    var verbose: Boolean = false
    var deeplyVerbose = false
    val classes = mutableListOf<ClassModel>()
    val functions = mutableListOf<FunctionModel>()
    private val walker = ParseTreeWalker()
    var currentChart: AnalysisType? = null
    fun parseTarget (target: File) {
        classes.clear()
        functions.clear()
        target
            .walkTopDown()
            .filter { it.extension == "kt" }
            .forEach {
                if (verbose) println(it)
                val lexer = KotlinLexer(CharStreams.fromPath(it.toPath()))
                val parser = KotlinParser(CommonTokenStream(lexer))
                walker.walk(StructureListener(), parser.kotlinFile())
            }
    }
}