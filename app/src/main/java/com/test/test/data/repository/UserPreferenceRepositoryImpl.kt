package com.test.test.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.test.test.common.Constants.USER_PREFERENCES
import com.test.test.domain.models.UserPref
import com.test.test.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

class UserPreferenceRepositoryImpl @Inject constructor(
    private val context: Context
) : UserPreferenceRepository {

    override suspend fun getUserPreference(): UserPref {
        val preferences = context.dataStore.data.first()
        return UserPref(
            name = preferences[NAME] ?: "",
            role = preferences[ROLE] ?: "",
            urlToImage = preferences[IMAGE_URL] ?: "",
            accessToken = preferences[ACCESS_TOKEN] ?: "",
        )
    }

    override suspend fun saveUserPreference(
        name: String,
        role: String,
        urlToImage: String,
        accessToken: String
    ) {
        context.dataStore.edit {
            it[NAME] = name
            it[ROLE] = role
            it[IMAGE_URL] = urlToImage
            it[ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun removeUserPreference() {
        context.dataStore.edit {
            it.remove(NAME)
            it.remove(ROLE)
            it.remove(IMAGE_URL)
            it.remove(ACCESS_TOKEN)
        }
    }

    companion object {
        private val NAME = stringPreferencesKey("name")
        private val ROLE = stringPreferencesKey("role")
        private val IMAGE_URL = stringPreferencesKey("image_url")
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    }
}