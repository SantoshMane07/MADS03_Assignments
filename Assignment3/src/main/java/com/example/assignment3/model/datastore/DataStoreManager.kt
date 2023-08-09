package com.example.assignment3.model.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.datastore by preferencesDataStore(name = "my_datastore")

class DataStoreManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val authenticatedKey = booleanPreferencesKey("authenticated")

    val authenticated: Flow<Boolean> = context.datastore.data.map { preference ->
        preference[authenticatedKey] ?: false
    }

    suspend fun saveAuthenticated(authenticated: Boolean) {
        context.datastore.edit { preference ->
            preference[authenticatedKey] = authenticated
        }
    }
}
