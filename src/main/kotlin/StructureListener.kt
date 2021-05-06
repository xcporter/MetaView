import com.xcporter.KotlinParser
import com.xcporter.KotlinParserBaseListener

class StructureListener : KotlinParserBaseListener() {
    override fun enterClassDeclaration(ctx: KotlinParser.ClassDeclarationContext?) {
        val mods = listOfNotNull(ctx?.modifiers()?.text)
        val type = when {
            ctx?.INTERFACE() != null -> ClassType.INTERFACE
            mods.any { it.contains("abstract") } -> ClassType.ABSTRACT
            mods.any { it.contains("enum") } -> ClassType.ENUM
            mods.any { it.contains("data") } -> ClassType.DATA
            else -> ClassType.CLASS
        }

        classes.add(ClassModel(ctx?.simpleIdentifier()?.text,
            ctx?.delegationSpecifiers()?.text?.split(",") ?: listOf(),
            type,
            mods
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