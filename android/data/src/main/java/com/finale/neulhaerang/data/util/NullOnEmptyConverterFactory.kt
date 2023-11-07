package com.finale.neulhaerang.data.util

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/** 비어있는(length=0)인 Response를 받았을 경우 처리 */
class NullOnEmptyConverterFactory : Converter.Factory() {
    companion object {
        fun create() = NullOnEmptyConverterFactory()
    }

    private fun converterFactory() = this

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ) = object : Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter =
            retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

        override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) {
            try {
                nextResponseBodyConverter.convert(value)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }
}