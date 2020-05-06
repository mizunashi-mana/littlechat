package com.example.littlechat.repository

import android.util.Log
import javax.inject.Inject
import kotlin.reflect.KClass

interface LoggerRepository {
    fun getLogger(): Logger
    fun getLogger(tag: String?): Logger
    fun getLogger(clazz: KClass<*>): Logger
}

interface Logger {
    fun debug(message: String)
    fun info(message: String)
    fun warning(message: String)
    fun error(e: Throwable)
}

class LoggerRepositoryImpl(
    private val tag: String?
): LoggerRepository, Logger {
    @Inject constructor(): this("app")

    constructor(clazz: KClass<*>): this(clazz.qualifiedName)

    override fun getLogger(): Logger {
        return this
    }

    override fun getLogger(tag: String?): Logger {
        return LoggerRepositoryImpl(tag)
    }

    override fun getLogger(clazz: KClass<*>): Logger {
        return LoggerRepositoryImpl(clazz)
    }

    override fun debug(message: String) {
        Log.d(tag, message)
    }

    override fun info(message: String) {
        Log.i(tag, message)
    }

    override fun warning(message: String) {
        Log.w(tag, message)
    }

    override fun error(e: Throwable) {
        Log.e(tag, "error", e)
    }
}
