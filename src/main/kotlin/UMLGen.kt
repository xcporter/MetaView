
fun List<ClassModel>.umlGen() : String {
    val acc = mutableListOf<String>("```plantuml", "@startuml")

    acc.addAll(
        this.map { it.kind.template(it) }
    )

    acc.addAll(
        this.flatMap { clazz ->
            clazz.delegation
                .filterNotNull()
                .map { "\"${it}\" <|--- ${clazz.name}" }
        }
    )

    acc.addAll(listOf("@enduml", "```"))

    return acc.joinToString("\n")
}