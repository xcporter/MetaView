data class ClassModel (val name: String?,
                       val delegation: List<String?> = listOf(),
                       val kind: ClassType = ClassType.CLASS,
                       val modifiers: List<String?> = listOf(),
                       val enumEntries: List<String?> = listOf())

enum class ClassType(val template: (ClassModel)->String) {
    ENUM({"enum \"${it.name}\" {\n\t${it.enumEntries.filterNotNull().joinToString("\n\t")}\n}"}),
    INTERFACE({"interface \"${it.name}\""}),
    ABSTRACT({"abstract \"${it.name}\""}),
    CLASS({"class \"${it.name}\""}),
    DATA({"class \"${it.name}\" <<(D, #DFAB25)>>"}),
    SEALED({"class \"${it.name}\" <<(S, #D9335B)>>"})
}