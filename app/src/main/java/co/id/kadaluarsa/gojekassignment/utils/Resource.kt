package co.id.kadaluarsa.gojekassignment.utils

import androidx.annotation.NonNull
import androidx.annotation.Nullable


/**
 * a generic class to holds a value with its loading status.
 * @param T
 */
sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Failure<out T>(val message: Throwable?) : Resource<T>()
}