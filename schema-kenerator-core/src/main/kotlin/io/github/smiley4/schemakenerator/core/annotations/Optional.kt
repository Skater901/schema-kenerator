package io.github.smiley4.schemakenerator.core.annotations

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialInfo


/**
 * Specifies that the annotated object is optional, i.e. not required.
 */
@OptIn(ExperimentalSerializationApi::class)
@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION
)
@SerialInfo
@Retention(AnnotationRetention.RUNTIME)
annotation class Optional
