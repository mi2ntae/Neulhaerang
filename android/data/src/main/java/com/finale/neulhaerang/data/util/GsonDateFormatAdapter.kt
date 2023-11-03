package com.finale.neulhaerang.data.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val dateTimePattern = "yyyy-MM-dd: HH:mm"
const val timePattern = "HH:mm"

class GsonDateFormatAdapter : JsonSerializer<LocalDateTime?>, JsonDeserializer<LocalDateTime?> {
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
