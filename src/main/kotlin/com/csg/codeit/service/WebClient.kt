package com.csg.codeit.service

import com.csg.codeit.config.objectMapper
import com.csg.codeit.model.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class WebClient(private val httpClient: OkHttpClient) {

    fun postJson(url: HttpUrl, request: RequestPayload, modifier: (Request.Builder) -> Request.Builder = { it }): Response? =
        Request.Builder().post(request.toJson.toRequestBody(MEDIA_TYPE_JSON))
            .let { httpClient.newCall(it.url(url).let(modifier).build()).execute() }
            .let { if (it.isSuccessful) it else it.close().run { null } }

    private val RequestPayload.toJson: String get() = objectMapper.writeValueAsString(this)

    companion object {
        private val MEDIA_TYPE_JSON = "application/json".toMediaType()
    }
}