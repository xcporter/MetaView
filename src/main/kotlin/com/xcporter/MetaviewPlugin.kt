package com.xcporter

import com.xcporter.tasks.ExecuteSequences
import org.gradle.api.Plugin
import org.gradle.api.Project

const val menuGroup = "MetaView"

class MetaviewPlugin : Plugin<Project> {
    override fun apply(proj: Project) {
        proj.extensions.create("generateUml", MetaviewExtension::class.java, proj)


        proj.afterEvaluate {
            val ext = it.metaviewExtension()
            TreeParser.verbose = ext.verbose
            TreeParser.deeplyVerbose = ext.deeplyVerbose
            if (ext.analysisSequence.isNotEmpty()) proj.tasks.register("generateUmlDiagrams", ExecuteSequences::class.java)
        }

    }

}

internal fun Project.metaviewExtension() : MetaviewExtension = this.extensions.getByType(MetaviewExtension::class.java)