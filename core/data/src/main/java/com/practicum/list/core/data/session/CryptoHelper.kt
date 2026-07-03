package com.practicum.list.core.data.session

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoHelper @Inject constructor() {

    private fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(androidKeyStore).apply { load(null) }
        if (keyStore.containsAlias(keyAlias)) {
            return (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey
        }
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, androidKeyStore)
        val spec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .setKeySize(keySizeBits)
            .build()
        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    fun encryptBytes(plainText: String): ByteArray {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey())
        val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        val iv = cipher.iv
        return byteArrayOf(iv.size.toByte()) + iv + encryptedBytes
    }

    fun decryptBytes(encryptedBytes: ByteArray?): String? {
        if (encryptedBytes == null) return null
        val ivSize = encryptedBytes[0].toInt()
        val iv = encryptedBytes.copyOfRange(1, 1 + ivSize)
        val ciphertext = encryptedBytes.copyOfRange(1 + ivSize, encryptedBytes.size)

        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateSecretKey(), IvParameterSpec(iv))
        return String(cipher.doFinal(ciphertext), Charsets.UTF_8)
    }

    private companion object {
        const val transformation = "AES/CBC/PKCS7Padding"
        const val keyAlias = "my_datastore_key"
        const val androidKeyStore = "AndroidKeyStore"
        const val keySizeBits = 256
    }
}
