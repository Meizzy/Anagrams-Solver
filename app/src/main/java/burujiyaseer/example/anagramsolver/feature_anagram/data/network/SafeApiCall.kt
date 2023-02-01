package burujiyaseer.example.anagramsolver.feature_anagram.data.network

import android.util.Log
import burujiyaseer.example.anagramsolver.core.utils.Resource
import retrofit2.HttpException

private const val TAG = "SafeApiCall"

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                }
                else -> {
                    Log.e(TAG, "error is ${throwable.message}")
                    Resource.Failure(true, null, null)
                }
            }
        }
    }
}