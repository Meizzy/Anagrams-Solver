package burujiyaseer.example.anagramsolver.feature_dictionary.data.remote.dto

import burujiyaseer.example.anagramsolver.feature_dictionary.domain.model.Meaning

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
){
    fun toMeaning(): Meaning{
        return Meaning(
            definitions.map { it.toDefinition() },
            partOfSpeech
        )
    }
}