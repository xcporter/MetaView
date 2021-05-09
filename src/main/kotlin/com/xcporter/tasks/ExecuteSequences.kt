package com.xcporter.tasks

import com.xcporter.AnalysisType
import com.xcporter.TreeParser
import com.xcporter.metaviewExtension
import com.xcporter.umlGen
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ExecuteSequences : DefaultTask() {
    init {
        group = "documentation"
    }
    @Internal
    val ext = project.metaviewExtension()

    @TaskAction
    fun execute () {
        ext.analysisSequence.forEach {
            when(it) {
                is AnalysisType.ClassTree -> {
                    TreeParser.currentChart = it
                    TreeParser.parseTarget(it.target)
                    File(it.outputDir.path + "/" + (it.outputFile ?: "chart${ext.analysisSequence.indexOf(it).takeIf{ it != 0 } ?: ""}.md"))
                        .writeText(TreeParser.classes.umlGen(it.style ?: listOf()))
                }
                is AnalysisType.FunctionReceiverTree -> {
                    TODO("FunctionReceiverTree: This sequence is not yet implemented")
                }
            }
        }
    }

}