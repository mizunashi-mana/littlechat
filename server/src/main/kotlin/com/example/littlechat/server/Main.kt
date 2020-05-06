package com.example.littlechat.server

import com.example.littlechat.server.service.AuthService
import com.example.littlechat.server.support.security.KeyReader
import io.grpc.BindableService
import io.grpc.Server
import io.grpc.ServerBuilder
import java.io.File
import java.security.KeyStore
import java.security.cert.Certificate
import java.util.*

class MainServer(
    private val port: Int,
    val services: List<BindableService>
) {
    val server: Server

    init {
        val serverBuilder = ServerBuilder
            .forPort(port)

        for (service in services) {
            serverBuilder.addService(service)
        }

        server = serverBuilder.build()
    }

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@MainServer.stop()
                println("*** server shut down")
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}

fun main() {
    val port = 5000

    val jwtSignPrivateKey = KeyReader
        .readPrivateKey(File("../asset/server.key").readBytes())

    val server = MainServer(
        port,
        listOf(
            AuthService { jwtSignPrivateKey }
        )
    )
    server.start()
    server.blockUntilShutdown()
}
