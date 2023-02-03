package burujiyaseer.example.anagramsolver.feature_dictionary.domain.model

data class Definition(
    val antonyms: List<Any>,
    val definition: String,
    val example: String?,
    val synonyms: List<Any>
)