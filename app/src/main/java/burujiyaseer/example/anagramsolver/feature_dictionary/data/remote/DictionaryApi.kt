package burujiyaseer.example.anagramsolver.feature_dictionary.data.remote

import burujiyaseer.example.anagramsolver.feature_dictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    companion object{
         const val BASE_URL = "https://api.dictionaryapi.dev/"
    }

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWord(@Path("word") word: String) : List<WordInfoDto>
}