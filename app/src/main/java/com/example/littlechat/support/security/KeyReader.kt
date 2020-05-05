package com.example.littlechat.support.security

import java.io.ByteArrayOutputStream
import java.lang.IllegalArgumentException
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import java.util.*

object KeyReader {
    private fun stripHeaderFooter(content: ByteArray): ByteArray {
        val out = ByteArrayOutputStream()

        var startOffset = 0
        while (
            startOffset < content.size &&
            content[startOffset] == '\n'.toByte()
        ) {
            startOffset += 1
        }
        while (startOffset < content.size) {
            if (content[startOffset] == '\n'.toByte()) {
                startOffset += 1
                break
            }

            startOffset += 1
        }

        var endOffset = content.size - 1
        while (
            0 <= endOffset &&
            content[endOffset] == '\n'.toByte()
        ) {
            endOffset -= 1
        }
        while (0 <= endOffset) {
            if (content[endOffset] == '\n'.toByte()) {
                endOffset -= 1
                break
            }

            endOffset -= 1
        }

        val input = content.inputStream(
            startOffset,
            endOffset - startOffset
        )
        while (true) {
            val b = input.read()

            if (b == -1) {
                break
            }

            if (b == '\n'.toInt()) {
                continue
            }

            out.write(b)
        }

        return out.toByteArray()
    }

    /**
     * @throws InvalidKeySpecException
     * @throws IllegalArgumentException
     */
    fun readPublicKeyWithException(content: ByteArray): PublicKey {
        val keyContent = Base64.getDecoder().decode(
            stripHeaderFooter(content)
        )
        val spec = X509EncodedKeySpec(keyContent)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePublic(spec)
    }

    fun readPublicKey(content: ByteArray): PublicKey? {
        return try {
            readPublicKeyWithException(content)
        } catch (e: IllegalArgumentException) {
            null
        } catch (e: InvalidKeySpecException) {
            null
        }
    }
}
