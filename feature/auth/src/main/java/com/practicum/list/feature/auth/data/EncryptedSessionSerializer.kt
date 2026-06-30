package com.practicum.list.feature.auth.data

import androidx.datastore.core.Serializer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.OutputStream

object EncryptedSessionSerializer : Serializer<UserSession> {

    private val gson = Gson()

    override val defaultValue: UserSession = UserSession()

    override suspend fun readFrom(input: InputStream): UserSession {
        return try {
            val encryptedBytes = input.readBytes()
            if (encryptedBytes.isEmpty()) return defaultValue

            val decryptedJson = CryptoHelper.decryptBytes(encryptedBytes)
            gson.fromJson(decryptedJson, UserSession::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserSession, output: OutputStream) {
        val jsonString = t.let { gson.fromJson(it, object : TypeToken<UserSession>() {}.type) }
            //Json.encodeToString(UserSession.serializer(), t)
        val encryptedBytes = CryptoHelper.encryptBytes(jsonString)
        output.write(encryptedBytes)
    }
}