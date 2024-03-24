package com.mckimquyen.cmonet

interface IStorage {

    fun <T> save(key: String, value: T)

    fun <T> getValue(key: String, defaultValue: T): T
}
