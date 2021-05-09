package com.xcporter

fun List<ClassModel>.umlGen(style: List<String> = listOf()) : String {
    val acc = mutableListOf<String>("```plantuml", "@startuml")

    acc.addAll(style)
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