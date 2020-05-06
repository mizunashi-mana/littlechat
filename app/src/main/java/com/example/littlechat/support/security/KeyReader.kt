package com.example.littlechat.support.security

import java.io.ByteArrayOutputStream
import java.lang.IllegalArgumentException
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import java.util.*

object KeyReader {
    private fun isNewlineByte(b: Byte): Boolean {
        return when (b) {
            '\n'.toByte() ->
                true
            else ->
                false
        }
    }

    private fun isWhitespaceByte(b: Byte): Boolean {
        return when (b) {
            '\n'.toByte() ->
                true
            ' '.toByte() ->
                true
            '\t'.toByte() ->
                true
            else ->
                false
        }
    }

    private fun stripHeaderFooter(content: ByteArray): ByteArray {
        val out = ByteArrayOutputStream()

        var startOffset = 0
        while (
            startOffset < content.size &&
            isWhitespaceByte(content[startOffset])
        ) {
            startOffset += 1
        }
        while (startOffset < content.size) {
            if (isNewlineByte(content[startOffset])) {
                startOffset += 1
                break
            }

            startOffset += 1
        }

        var endOffset = content.size - 1
        while (
            0 <= endOffset &&
            isWhitespaceByte(content[endOffset])
        ) {
            endOffset -= 1
        }
        while (0 <= endOffset) {
            if (isNewlineByte(content[endOffset])) {
                endOffset -= 1
                break
            }

            endOffset -= 1
        }

        val input = content.inputStream(
            startOffset,
            endOffset - startOffset
        )
        do {
            val b = input.read()

            if (b == -1) break

            if (isWhitespaceByte(b.toByte())) continue

            out.write(b)
        } while (true)

        return out.toByteArray()
    }

    /**
     * @throws InvalidKeySpecException
     * @throws IllegalArgumentException
     */
    fun readPublicKey(content: ByteArray): PublicKey {
        val keyContent = Base64.getDecoder().decode(
            stripHeaderFooter(content)
        )
        val spec = X509EncodedKeySpec(keyContent)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePublic(spec)
    }
}
