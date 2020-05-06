package com.example.littlechat.repository

import android.app.Application
import android.content.res.XmlResourceParser
import com.example.littlechat.R
import com.example.littlechat.support.security.KeyReader
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.net.MalformedURLException
import java.net.URL
import java.security.Key
import java.security.spec.InvalidKeySpecException
import javax.inject.Inject

interface SettingsRepository {
    val apiEndpoint: URL
    val jwtSigningKey: Key
}

class SettingsRepositoryImpl @Inject constructor(
    private val application: Application,
    loggerRepository: LoggerRepository
): SettingsRepository {
    private val logger =
        loggerRepository.getLogger(SettingsRepositoryImpl::class)

    override val apiEndpoint: URL
    override val jwtSigningKey: Key

    init {
        val loader = SettingsResourceLoader(logger)
        loader.load(application.resources.getXml(R.xml.settings))

        apiEndpoint = try {
            URL(loader.apiEndpoint)
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        }
        jwtSigningKey = try {
            val key = loader.key
                ?: throw RuntimeException("Public key setting should be specified")
            KeyReader.readPublicKey(key.toByteArray())
        } catch (e: InvalidKeySpecException) {
            throw RuntimeException(e)
        } catch (e: IllegalArgumentException) {
            throw RuntimeException(e.message)
        }
    }
}

class SettingsResourceLoader(
    private val logger: Logger
) {
    private val tagSettings: String = "settings"

    var apiEndpoint: String? = null
    private val tagApiEndpoint: String = "apiEndpoint"

    var key: String? = null
    private val tagKey: String = "key"

    fun load(parser: XmlResourceParser): SettingsResourceLoader {
        do {
            val isEnd = when (parser.next()) {
                XmlResourceParser.START_TAG -> {
                    when (parser.name) {
                        tagSettings ->
                            false
                        tagApiEndpoint ->
                            loadApiEndpoint(parser)
                        tagKey ->
                            loadKey(parser)
                        else -> {
                            logger.info("Unknown tag: ${parser.name}")
                            false
                        }
                    }
                }
                XmlResourceParser.END_DOCUMENT ->
                    true
                else ->
                    false
            }
        } while (isEnd)

        return this
    }

    private fun loadApiEndpoint(parser: XmlResourceParser): Boolean {
        do {
            val eventType = parser.next()
            when (eventType) {
                XmlResourceParser.END_TAG -> {
                    when (parser.name) {
                        tagApiEndpoint ->
                            return false
                        else ->
                            logger.info("Unknown tag in $tagApiEndpoint: ${parser.name}")
                    }
                }
                XmlResourceParser.TEXT ->
                    apiEndpoint = parser.text
                else -> {}
            }
        } while (eventType != XmlResourceParser.END_DOCUMENT)

        return true
    }

    private fun loadKey(parser: XmlResourceParser): Boolean {
        do {
            val eventType = parser.next()
            when (eventType) {
                XmlResourceParser.END_TAG -> {
                    when (parser.name) {
                        tagKey ->
                            return false
                        else ->
                            logger.info("Unknown tag in $tagKey: ${parser.name}")
                    }
                }
                XmlResourceParser.TEXT ->
                    key = parser.text
                else -> {}
            }
        } while (eventType != XmlResourceParser.END_DOCUMENT)

        return true
    }
}
