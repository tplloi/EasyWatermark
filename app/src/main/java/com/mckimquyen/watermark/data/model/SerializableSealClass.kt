package com.mckimquyen.watermark.data.model

import java.io.Serializable

sealed interface SerializableSealClass<T : Serializable> {
    /**
     * return serializable key for specify class
     * @author rosuh
     * @date 2021/6/27
     */
    fun serializeKey(): T
}
