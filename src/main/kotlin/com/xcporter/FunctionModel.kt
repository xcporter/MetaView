package com.xcporter

data class FunctionModel(val name: String?,
                         val receivers: List<String> = listOf(),
                         val typeParameters: List<String> = listOf(),
                         val returnType: String? = null,
                         val modifiers: List<String?> = listOf(),
                         val parameters: Map<String?, String?> = mapOf())

fun FunctionModel.toUML() : String = """
    |class "${if(typeParameters.isNotEmpty()) "<${typeParameters.joinToString(", ")}>" else ""}$name" <<(f, #1CDF58)>> {
    |    ${parameters.map {"${it.key}: ${it.value}"}.joinToString("\n\t")}
    |__${returnType?.let { "\n$it" } ?: ""}
    |}""".trimMargin()