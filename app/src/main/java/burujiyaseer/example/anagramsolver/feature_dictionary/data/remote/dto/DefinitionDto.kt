package burujiyaseer.example.anagramsolver.feature_dictionary.data.remote.dto

import burujiyaseer.example.anagramsolver.feature_dictionary.domain.model.Definition

data class DefinitionDto(
    val antonyms: List<Any>,
    val definition: String,
    val example: String?,
    val synonyms: List<Any>
) {
    fun toDefinition(): Definition {
        return Definition(
            antonyms,
            definition,
            example,
            synonyms
        )
    }
}