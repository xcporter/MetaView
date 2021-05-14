package com.xcporter

import org.gradle.api.Project
import java.io.File

sealed class AnalysisType(val project: Project) {
    var target: File = project.projectDir
    var style: List<String>? = null
    var outputDir: File = File(project.buildDir.path + "/docs").apply { this.mkdirs() }
    var outputFile: String? = null

    class ClassTree(project: Project) : AnalysisType(project) {
        var ignoreDelegates: List<String> = listOf()
        var splitDelegates: List<String> = listOf()

        override fun toString(): String = "${style?.joinToString(" ")}"
    }

    class FunctionTree(project: Project) : AnalysisType(project) {
        var ignoreDelegates: List<String> = listOf()
        var memberFunctions : Boolean = false
        var freeFunctions : Boolean = false
        var receiverFunctions : Boolean = true
    }
}
