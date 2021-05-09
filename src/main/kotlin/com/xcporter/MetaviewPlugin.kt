package com.xcporter

import com.xcporter.tasks.ExecuteSequences
import org.gradle.api.Plugin
import org.gradle.api.Project

const val menuGroup = "MetaView"

class MetaviewPlugin : Plugin<Project> {
    override fun apply(proj: Project) {
        proj.extensions.create("generateUml", MetaviewExtension::class.java, proj)
        proj.tasks.register("generateUmlDiagrams", ExecuteSequences::class.java)
    }

}

internal fun Project.metaviewExtension() : MetaviewExtension = this.extensions.getByType(MetaviewExtension::class.java)