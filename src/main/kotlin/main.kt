import com.xcporter.KotlinLexer
import com.xcporter.KotlinParser
import com.xcporter.KotlinParserBaseListener
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.File

val classes = mutableListOf<ClassModel>()

val target = File("/Users/poralexc/Documents/Kotlin/efasParallel/admin")
    .walkTopDown()
    .filter { it.extension == "kt" }
val walker = ParseTreeWalker()

fun main() {
    target.forEach { file ->
        val lexer = KotlinLexer(CharStreams.fromPath(file.toPath()))
        val parser = KotlinParser(CommonTokenStream(lexer))
        walker.walk(StructureListener(), parser.kotlinFile())
    }
    File("/Users/poralexc/Documents/Kotlin/metaView/test.md").writeText(classes.umlGen())
    classes.forEach(::println)
}