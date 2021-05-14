package com.xcporter

data class ClassModel (val name: String?,
                       val delegation: List<String?> = listOf(),
                       val kind: ClassType = ClassType.CLASS,
                       val modifiers: List<String?> = listOf(),
                       val properties: Map<String?, String?> = mapOf(),
                       val enumEntries: List<String?> = listOf())

enum class ClassType(val template: (ClassModel)->String) {
    ENUM({
        """enum "${it.name}" {
            |    ${it.enumEntries.filterNotNull().joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true }) "\n__\n\t+ serializer()" else ""}
            |}
            """.trimMargin()
    }),
    INTERFACE({
        """interface "${it.name}" {
            |    ${it.properties.map { "${it.key}: ${it.value}"}.joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}
            |}
            """.trimMargin()
    }),
    ABSTRACT({
        """abstract "${it.name}" {
            |    ${it.properties.map { "${it.key}: ${it.value}"}.joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}
            |}
            """.trimMargin()
    }),
    CLASS({
        """class "${it.name}" {
            |    ${it.properties.map { "${it.key}: ${it.value}"}.joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}
            |}
            """.trimMargin()
    }),
    DATA({
        """class "${it.name}" <<(D, #EFBA68)>> {
            |    ${it.properties.map { "${it.key}: ${it.value}"}.joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}
            |}
            """.trimMargin()
    }),
    SEALED({
        """class "${it.name}" <<(S, #D9335B)>> {
            |    ${it.properties.map { "${it.key}: ${it.value}"}.joinToString("\n\t")}${if (it.modifiers.any { it?.contains("Serializable") == true } ) "\n__\n\t+ serializer()" else ""}
            |}
            """.trimMargin()
    }),
}