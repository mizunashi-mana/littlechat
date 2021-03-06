package com.example.littlechat.support.security

import java.io.ByteArrayOutputStream
import java.lang.IllegalArgumentException
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import java.util.*

object KeyReader {
    private fun ByteArray.stripHeaderFooter(): ByteArray {
        return String(this)
            .replace(Regex("-----(BEGIN|END) .*-----"), "")
            .replace(Regex("\\s"), "")
            .toByteArray(Charsets.US_ASCII)
    }

    /**
     * @throws InvalidKeySpecException
     * @throws IllegalArgumentException
     */
    fun readPublicKey(content: ByteArray): PublicKey {
        val keyContent = Base64.getDecoder()
            .decode(content.stripHeaderFooter())
        val spec = X509EncodedKeySpec(keyContent)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePublic(spec)
    }
}
