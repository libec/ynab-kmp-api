package infrastructure.networking

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

internal fun makeJsonNegotiation(): Json = Json {
    ignoreUnknownKeys = true
    namingStrategy = JsonNamingStrategy.SnakeCase
    explicitNulls = false
}