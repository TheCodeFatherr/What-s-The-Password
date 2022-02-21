/*
 *
 *  * Copyright (c) 2020 Razeware LLC
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 *  * distribute, sublicense, create a derivative work, and/or sell copies of the
 *  * Software in any work that is designed, intended, or marketed for pedagogical or
 *  * instructional purposes related to programming, coding, application development,
 *  * or information technology.  Permission for such use, copying, modification,
 *  * merger, publication, distribution, sublicensing, creation of derivative works,
 *  * or sale is expressly withheld.
 *  *
 *  * This project and source code may use libraries or frameworks that are
 *  * released under various Open-Source licenses. Use of those libraries and
 *  * frameworks are governed by their own individual licenses.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 *
 */

package com.thecodefather.whatsthepassword.internal.authentication

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import com.thecodefather.whatsthepassword.internal.extensions.warn
import java.nio.charset.Charset
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * Helper class to create and manage cryptography related functions
 */
@RequiresApi(Build.VERSION_CODES.M)
object CryptographyUtil {

  private const val KEY_SIZE = 128
  private const val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
  private const val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
  private const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7

  fun encrypt(dataToEncrypt: ByteArray,
                      passkey: CharArray): HashMap<String, ByteArray> {
    val map = HashMap<String, ByteArray>()

    try {
      // 1
      //Random salt for next step
      val random = SecureRandom()
      val salt = ByteArray(256)
      random.nextBytes(salt)

      // 2
      //PBKDF2 - derive the key from the password, don't use passwords directly
      val pbKeySpec = PBEKeySpec(passkey, salt, 1324, 256)
      val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
      val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
      val keySpec = SecretKeySpec(keyBytes, "AES")

      // 3
      //Create initialization vector for AES
      val ivRandom = SecureRandom() //not caching previous seeded instance of SecureRandom
      val iv = ByteArray(16)
      ivRandom.nextBytes(iv)
      val ivSpec = IvParameterSpec(iv)

      // 4
      //Encrypt
      val cipher = getCipher()
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
      val encrypted = cipher.doFinal(dataToEncrypt)

      // 5
      map["salt"] = salt
      map["iv"] = iv
      map["encrypted"] = encrypted
    } catch (e: Exception) {
      warn("encryption exception = ${e.message}")
    }

    return map

  }

  fun decrypt(map: HashMap<String, ByteArray>, passkey: CharArray): ByteArray? {
    var decrypted: ByteArray? = null
    try {

      val salt = map["salt"]
      val iv = map["iv"]
      val encrypted = map["encrypted"]

      // 2
      //regenerate key from password
      val pbKeySpec = PBEKeySpec(passkey, salt, 1324, 256)
      val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
      val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
      val keySpec = SecretKeySpec(keyBytes, "AES")

      // 3
      //Decrypt
      val cipher = getCipher()
      val ivSpec = IvParameterSpec(iv)
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
      decrypted = cipher.doFinal(encrypted)
    } catch (e: Exception) {
      warn("decryption exception = ${e.message}")
    }

    return decrypted
  }

  /**
   * Returns Cipher instance that uses the SecretKey to encrypt / decrypt data
   */
  fun getCipher(): Cipher {
    val transformation = "$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCRYPTION_PADDING"

    return Cipher.getInstance(transformation)
  }

  /**
   * Encrypts text with a Cipher and converts to EncryptedMessage
   */
  fun encrypt(plainText: String): HashMap<String, ByteArray> {
    val passwordByteArray = plainText.toByteArray(Charset.forName("UTF-8"))
    return encrypt(passwordByteArray, plainText.toCharArray())
  }
}

