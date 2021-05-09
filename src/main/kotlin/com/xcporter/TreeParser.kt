package com.xcporter

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.File

object TreeParser {
    val classes = mutableListOf<ClassModel>()
    private val walker = ParseTreeWalker()
    var currentChart: AnalysisType? = null
    fun parseTarget (target: File) {
        classes.clear()
        target
            .walkTopDown()
            .filter { it.extension == "kt" }
            .forEach {
                println(it)
                val lexer = KotlinLexer(CharStreams.fromPath(it.toPath()))
                val parser = KotlinParser(CommonTokenStream(lexer))
                walker.walk(StructureListener(), parser.kotlinFile())
            }
    }
}