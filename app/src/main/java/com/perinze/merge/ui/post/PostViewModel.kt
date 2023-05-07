package com.perinze.merge.ui.post

import android.content.Context
import androidx.lifecycle.*
import com.perinze.merge.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(context: Context) : ViewModel() {

    private val _result = MutableLiveData<List<Post>>().apply {
        value = emptyList()
    }
    val result: LiveData<List<Post>> = _result

    private val db: PostDao = AppDatabase.getInstance(context).postDao()

    fun retrieve() {
        viewModelScope.launch(Dispatchers.IO) {
            _result.postValue(db.getAll())
        }
    }
}

class PostViewModelFactory(val context: Context): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T =
        modelClass.getConstructor(Context::class.java)
            .newInstance(context)
}