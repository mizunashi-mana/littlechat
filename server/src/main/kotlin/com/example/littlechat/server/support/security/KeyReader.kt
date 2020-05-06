package com.example.littlechat.server.support.security

import java.io.ByteArrayOutputStream
import java.lang.IllegalArgumentException
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
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
    fun readPrivateKey(content: ByteArray): PrivateKey {
        val keyContent = Base64.getDecoder()
            .decode(content.stripHeaderFooter())
        val spec = PKCS8EncodedKeySpec(keyContent)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePrivate(spec)
    }
}
