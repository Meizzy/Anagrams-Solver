package burujiyaseer.example.anagramsolver.feature_anagram.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface WordApi {

    @GET("/all/:{word}")
    suspend fun getAnagramWords(@Path("word") word: String): AnagramWords
}