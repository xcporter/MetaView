package com.xcporter

import org.gradle.api.Project
import java.io.File

sealed class AnalysisType(val project: Project) {
    var style: List<String>? = null
    var outputDir: File = File(project.buildDir.path + "/docs").apply { this.mkdirs() }
    var outputFile: String? = null

    class ClassTree(project: Project) : AnalysisType(project) {
        var ignoreDelegates: List<String> = listOf()
        var splitDelegates: List<String> = listOf()

        override fun toString(): String = "${style?.joinToString(" ")}"
    }

    class FunctionReceiverTree(project: Project) : AnalysisType(project) {
        var ignoreReceivers: List<String> = listOf()
    }
}
