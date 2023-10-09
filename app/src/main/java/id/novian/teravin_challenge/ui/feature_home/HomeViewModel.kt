package id.novian.teravin_challenge.ui.feature_home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.teravin_challenge.domain.model.Movie
import id.novian.teravin_challenge.domain.repository.MovieRepository
import id.novian.teravin_challenge.util.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _uiState: MutableLiveData<UiState<List<Movie>>> = MutableLiveData()
    val uiState: LiveData<UiState<List<Movie>>> get() = _uiState

    fun getAllPopularMovies() {
        _uiState.postValue(UiState.Loading)
        viewModelScope.launch {
            try {
                val response = repository.getAllPopularMovies()

                Log.i("vm", "size of data is ${response.size}")

                if (response.isEmpty()) {
                    _uiState.postValue(UiState.Failure("No data is available"))
                } else {
                    _uiState.postValue(UiState.Success(response.take(10)))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.postValue(UiState.Failure("Error Occurred with message: ${e.message}"))
            }
        }
    }

}