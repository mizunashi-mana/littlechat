#!/usr/bin/env kscript

import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Key
import java.util.*
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.Writer

val generator = KeyPairGenerator.getInstance("RSA")
generator.initialize(4096)

val encoder = Base64.getEncoder()
val keyPair = generator.generateKeyPair()

fun Key.outputKey(file: File, header: ByteArray, footer: ByteArray) {
    FileOutputStream(file).use { out ->
        out.write(header)
        out.write('\n'.toInt())

        val input = encoder.encode(encoded).inputStream()
        var index = 0
        while (true) {
            val b = input.read()

            if (b == -1) {
                break
            }

            out.write(b)
            index += 1

            if (index == 64) {
                out.write('\n'.toInt())
                index = 0
            }
        }

        out.write('\n'.toInt())
        out.write(footer)
    }
}

val privateKey: PrivateKey = keyPair.private
privateKey.outputKey(
    File("asset/server.key"),
    "-----BEGIN ${privateKey.format} PRIVATE KEY-----".toByteArray(),
    "-----END ${privateKey.format} PRIVATE KEY-----".toByteArray()
)

val publicKey: PublicKey = keyPair.public
privateKey.outputKey(
    File("asset/server.pub.key"),
    "-----BEGIN ${publicKey.format} PUBLIC KEY-----".toByteArray(),
    "-----END ${publicKey.format} PUBLIC KEY-----".toByteArray()
)
