package com.xcporter

fun List<ClassModel>.umlGen(style: List<String> = listOf()) : String {
    val acc = mutableListOf<String>("```plantuml", "@startuml")

    acc.addAll(style)

//    add all class elements
    acc.addAll(
        this.map { it.kind.template(it) }
    )

//    add delegation
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

@JvmName("umlGenFunctionModel")
fun List<FunctionModel>.umlGen(style: List<String> = listOf()) : String {
    val acc = mutableListOf<String>("```plantuml", "@startuml")

    acc.addAll(style)

//    Add all function elements
    acc.addAll(
        this.map { it.toUML() }
    )


//  add delegation
    acc.addAll(
        this.filter { it.receivers.isNotEmpty() }
            .map {
                "${it.receivers.first()} <|--- \"${if(it.typeParameters.isNotEmpty()) "<${it.typeParameters.joinToString(", ")}>" else ""}${it.name}\""
            }
    )

    acc.addAll(listOf("@enduml", "```"))

    return acc.joinToString("\n")
}