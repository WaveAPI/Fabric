package org.waveapi.api.kotlin.entities.entity

import org.waveapi.api.entities.EntityCastingType
import org.waveapi.api.entities.entity.EntityBase

fun <T> EntityBase?.toType(type: EntityCastingType<T>?): T? {
    return this?.to(type)
}