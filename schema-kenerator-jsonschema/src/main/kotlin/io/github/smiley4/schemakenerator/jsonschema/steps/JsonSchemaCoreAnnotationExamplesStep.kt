package io.github.smiley4.schemakenerator.jsonschema.steps

import io.github.smiley4.schemakenerator.core.annotations.SchemaExample
import io.github.smiley4.schemakenerator.core.data.BaseTypeData
import io.github.smiley4.schemakenerator.core.data.PropertyData
import io.github.smiley4.schemakenerator.jsonschema.data.JsonSchema
import io.github.smiley4.schemakenerator.jsonschema.jsonDsl.JsonArray
import io.github.smiley4.schemakenerator.jsonschema.jsonDsl.JsonObject
import io.github.smiley4.schemakenerator.jsonschema.jsonDsl.JsonTextValue

/**
 * Adds additional metadata from core annotation [SchemaExample]
 * - input: [JsonSchema]
 * - output: [JsonSchema] with added information from annotations
 */
class JsonSchemaCoreAnnotationExamplesStep {

    fun process(schemas: Collection<JsonSchema>): Collection<JsonSchema> {
        return schemas.onEach { process(it) }
    }

    private fun process(schema: JsonSchema) {
        if (schema.json is JsonObject && schema.json.properties["examples"] == null) {
            determineExamples(schema.typeData)?.also { examples ->
                schema.json.properties["examples"] = JsonArray().also { arr -> arr.items.addAll(examples.map { JsonTextValue(it) }) }
            }
        }
        iterateProperties(schema) { prop, data ->
            determineExamples(data)?.also { examples ->
                prop.properties["examples"] = JsonArray().also { arr -> arr.items.addAll(examples.map { JsonTextValue(it) }) }
            }
        }
    }

    private fun determineExamples(typeData: PropertyData): List<String>? {
        return typeData.annotations
            .filter { it.name == SchemaExample::class.qualifiedName }
            .map { it.values["example"] as String }
            .let {
                it.ifEmpty {
                    null
                }
            }
    }

    private fun determineExamples(typeData: BaseTypeData): List<String>? {
        return typeData.annotations
            .filter { it.name == SchemaExample::class.qualifiedName }
            .map { it.values["example"] as String }
            .let {
                it.ifEmpty {
                    null
                }
            }
    }

}