package com.nighty.testbed.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ApplicationApi(private val client: HttpClient) {
    suspend fun loremIpsum(count: Int): String = client
        .get("https://loripsum.net/api/$count/long/plaintext")
        .body()
}