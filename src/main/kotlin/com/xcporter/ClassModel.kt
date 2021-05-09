package com.xcporter

data class ClassModel (val name: String?,
                       val delegation: List<String?> = listOf(),
                       val kind: ClassType = ClassType.CLASS,
                       val modifiers: List<String?> = listOf(),
                       val properties: Map<String?, String?> = mapOf(),
                       val enumEntries: List<String?> = listOf())

enum class ClassType(val template: (ClassModel)->String) {
    ENUM({
        "enum \"${it.name}\" {\n\t${it.enumEntries.filterNotNull().joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}\n}"
    }),
    INTERFACE({
        "interface \"${it.name}\" {\n\t${it.properties.map { "${it.key} ${it.value}"}.joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}\n}"
    }),
    ABSTRACT({
        "abstract \"${it.name}\" {\n\t${it.properties.map { "${it.key} ${it.value}"}.joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}\n}"
    }),
    CLASS({
        "class \"${it.name}\" {\n\t${it.properties.map { "${it.key} ${it.value}"}.joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}\n}"
    }),
    DATA({
        "class \"${it.name}\" <<(D, #DFAB25)>> {\n\t${it.properties.map { "${it.key} ${it.value}"}.joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}\n}"
    }),
    SEALED({
        "class \"${it.name}\" <<(S, #D9335B)>> {\n\t${it.properties.map { "${it.key} ${it.value}"}.joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}\n}"
    }),
}