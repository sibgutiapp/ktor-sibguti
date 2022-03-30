package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*

const val API_VERSION = "/v1"

fun main() {
    embeddedServer(Netty, port = 5000, host = "sibguti.herokuapp.com") {
        configureRouting()
        configureSerialization()
        configureTemplating()
        configureHTTP()
    }.start(wait = true)
}
