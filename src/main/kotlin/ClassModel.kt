data class ClassModel (val name: String?,
                       val delegation: List<String?> = listOf(),
                       val kind: ClassType = ClassType.CLASS,
                       val modifiers: List<String?> = listOf(),
                       val enumEntries: List<String?> = listOf())

enum class ClassType(val template: (String)->String) {
    ENUM({"enum $it"}),
    INTERFACE({"interface $it"}),
    ABSTRACT({"class $it"}),
    CLASS({"class $it"}),
    DATA({"class $it <<(D, #DFAB25)>>"}),
    SEALED({"class $it <<(S, #D9335B)>>"})
}