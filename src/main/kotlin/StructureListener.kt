import com.xcporter.KotlinParser
import com.xcporter.KotlinParserBaseListener

class StructureListener : KotlinParserBaseListener() {
    override fun enterClassDeclaration(ctx: KotlinParser.ClassDeclarationContext?) {
        val mods = listOfNotNull(ctx?.modifiers()?.text)
        val delegation = ctx?.delegationSpecifiers()?.text?.split(",")
            //                    todo(react mode)
//                  todo(ignore rule, use list contains)
            ?.flatMap {
                it
                    .replace("\\(.*\\)".toRegex(), "")
                    .replace("RComponent<", "")
                    .replace(">", "")
                    .split(",")
            }
            ?.filter { it != "RProps" && it != "RState" && it != "View"}

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
        println(properties)


        classes.add(ClassModel(ctx?.simpleIdentifier()?.text,
            delegation ?: listOf(),
            type,
            mods,
            properties ?: mapOf(),
            enumEntries = enumValues ?: listOf()
        ))
    }
//
//    override fun enterDelegationSpecifier(ctx: KotlinParser.DelegationSpecifierContext?) {
//        println("delegation: ${ ctx?.text }")
//    }

//    override fun enterClassModifier(ctx: KotlinParser.ClassModifierContext?) {
//        println("modifier: ${ ctx?.text }")
//    }
//
//    override fun exitKotlinFile(ctx: KotlinParser.KotlinFileContext?) {
//        classes.forEach(::println)
//    }
}