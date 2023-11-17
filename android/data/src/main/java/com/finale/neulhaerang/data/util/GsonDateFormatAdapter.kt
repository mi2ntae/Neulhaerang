package com.finale.neulhaerang.data.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val datePattern = "yyyy-MM-dd"
const val timePattern = "HH:mm"
const val dateTimePattern = "$datePattern $timePattern" // "yyyy-MM-dd HH:mm"

/**
 * LocalDateTime Serializer & Deserializer
 */
class GsonDateTimeFormatAdapter : JsonSerializer<LocalDateTime?>, JsonDeserializer<LocalDateTime?> {
    @Synchronized
    override fun serialize(
        localDateTime: LocalDateTime?,
        type: Type?,
        jsonSerializationContext: JsonSerializationContext?,
    ): JsonElement {
//        return JsonPrimitive(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(localDateTime))
        return JsonPrimitive(DateTimeFormatter.ofPattern(dateTimePattern).format(localDateTime))
    }

    @Synchronized
    override fun deserialize(
        jsonElement: JsonElement,
        type: Type?,
        jsonDeserializationContext: JsonDeserializationContext?,
    ): LocalDateTime {
//        return LocalDateTime.parse(jsonElement.asString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        return LocalDateTime.parse(
            jsonElement.asString, DateTimeFormatter.ofPattern(dateTimePattern)
        )
    }
}


/**
 * LocalDateTime Serializer & Deserializer
 */
class GsonDateFormatAdapter : JsonSerializer<LocalDate?>, JsonDeserializer<LocalDate?> {
    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return JsonPrimitive(DateTimeFormatter.ofPattern(datePattern).format(src))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): LocalDate? {
        return LocalDate.parse(json!!.asString, DateTimeFormatter.ofPattern(datePattern))
    }
}


/**
 * LocalDateTime Serializer & Deserializer
 */
class GsonTimeFormatAdapter : JsonSerializer<LocalTime?>, JsonDeserializer<LocalTime?> {
    override fun serialize(
        src: LocalTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return JsonPrimitive(DateTimeFormatter.ofPattern(timePattern).format(src))
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): LocalTime? {
        return LocalTime.parse(json!!.asString, DateTimeFormatter.ofPattern(timePattern))
    }
}
