package com.perinze.merge.ui.favorite

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.perinze.merge.ui.search.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class FavoriteViewModel(context: Context) : ViewModel() {

    private val _result = MutableLiveData<List<Favorite>>().apply {
        value = emptyList()
    }
    val result: LiveData<List<Favorite>> = _result

    private val db: FavoriteDao = AppDatabase.getInstance(context).favoriteDao()

    fun retrieve() {
        viewModelScope.launch(Dispatchers.IO) {
            _result.postValue(db.getAll())
        }
    }
}

class FavoriteViewModelFactory(val context: Context): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(Context::class.java)
            .newInstance(context)
}