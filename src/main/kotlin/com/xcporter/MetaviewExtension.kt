package com.xcporter

import groovy.lang.Closure
import groovy.lang.Closure.DELEGATE_FIRST
import org.gradle.api.Project

open class MetaviewExtension(val proj: Project) {
    var analysisSequence = mutableListOf<AnalysisType>()

    var verbose : Boolean = false
    var deeplyVerbose: Boolean = false

    fun classTree(op: Closure<Unit>) {
        val obj = AnalysisType.ClassTree(proj)
        op.apply {
            resolveStrategy = DELEGATE_FIRST
            delegate = obj
            call()
        }
        analysisSequence.add (obj)
    }

    fun classTree(op: AnalysisType.ClassTree.()->Unit) {
        val obj = AnalysisType.ClassTree(proj)
        analysisSequence.add(obj.apply(op))
    }

    fun functionTree(op: Closure<Unit>) {
        val obj = AnalysisType.FunctionTree(proj)
        op.apply {
            resolveStrategy = DELEGATE_FIRST
            delegate = obj
            call()
        }
        analysisSequence.add (obj)
    }

    fun functionTree(op: AnalysisType.FunctionTree.() -> Unit) {
        val obj = AnalysisType.FunctionTree(proj)
        analysisSequence.add(obj.apply(op))
    }
}
