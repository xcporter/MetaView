package com.xcporter

import com.xcporter.TreeParser.classes
import com.xcporter.TreeParser.currentChart
import com.xcporter.TreeParser.deeplyVerbose
import com.xcporter.TreeParser.functions

class StructureListener() : KotlinParserBaseListener() {
    override fun enterClassDeclaration(ctx: KotlinParser.ClassDeclarationContext?) {
        val mods = listOfNotNull(ctx?.modifiers()?.text)
        val delegation = ctx?.delegationSpecifiers()?.children?.map { it.text }
//                apply split delegate
            ?.flatMap {
                if((currentChart as? AnalysisType.ClassTree)?.splitDelegates?.contains(it.substringBefore("<")) == true) {
                    it
                        .replace("\\(.*\\)".toRegex(), "")
                        .split("<")
                        .drop(1)
                        .first().replace(">", "")
                        .split(",")
                } else {
                    listOf(it.replace("\\(.*\\)".toRegex(), ""))
                }
            }
//                apply ignore delegates
            ?.mapNotNull {
                if ((currentChart as? AnalysisType.ClassTree)?.ignoreDelegates?.contains(it) == true) null
                else it
            }

        val type = when {
            ctx?.INTERFACE() != null -> ClassType.INTERFACE
            mods.any { it.contains("abstract") } -> ClassType.ABSTRACT
            mods.any { it.contains("enum") } -> ClassType.ENUM
            mods.any { it.contains("data") } -> ClassType.DATA
            mods.any { it.contains("sealed") } -> ClassType.SEALED
            else -> ClassType.CLASS
        }
        val enumValues = ctx?.enumClassBody()?.enumEntries()?.enumEntry()?.map {
            it.simpleIdentifier().text
        }
//        Get properties from primary constructor
        val properties = ctx?.primaryConstructor()?.classParameters()?.classParameter()?.map {
            it?.simpleIdentifier()?.text to it?.type()?.text
        }?.toMap()?.filter { it.key?.contains("class")?.not() ?: false }
//            otherwise check members
            ?: ctx?.classBody()?.classMemberDeclarations()?.classMemberDeclaration()
                ?.mapNotNull {
                    it?.declaration()?.propertyDeclaration()?.variableDeclaration()
                    ?.let {
                        it.simpleIdentifier()?.text to it.type()?.text
                    }
                }?.toMap()
        if (deeplyVerbose) println(properties)


        TreeParser.classes.add(
            ClassModel(ctx?.simpleIdentifier()?.text,
                delegation ?: listOf(),
                type,
                mods,
                properties ?: mapOf(),
                enumEntries = enumValues ?: listOf()
            )
        )
    }

    override fun enterFunctionDeclaration(ctx: KotlinParser.FunctionDeclarationContext?) {
        val mods = listOfNotNull(ctx?.modifiers()?.text)
        val receivers = ctx?.receiverType()?.typeReference()?.userType()?.simpleUserType()?.mapNotNull { it.simpleIdentifier()?.text } ?: listOf()
        val returnType = ctx?.type()?.text
        val typeParameters = ctx?.typeParameters()?.typeParameter()?.mapNotNull { it.simpleIdentifier()?.text + ": " + it.type()?.typeReference()?.userType()?.simpleUserType()?.map { it.simpleIdentifier()?.text }?.firstOrNull()  } ?: listOf()
        val params = ctx?.functionValueParameters()?.functionValueParameter()?.map { it.parameter()?.simpleIdentifier()?.text to it.parameter()?.type()?.text }?.toMap() ?: mapOf()

        TreeParser.functions.add(
            FunctionModel(
                ctx?.simpleIdentifier()?.text,
                receivers,
                typeParameters,
                returnType,
                mods,
                params
            )
        )
    }

    override fun exitKotlinFile(ctx: KotlinParser.KotlinFileContext?) {
        if (deeplyVerbose) classes.forEach(::println)
        if (deeplyVerbose) functions.forEach(::println)
    }
}