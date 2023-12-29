package com.sd.lib.log.builder

import android.util.Log
import android.view.View

class FLogBuilder : LogBuilder {
    private val _list = mutableListOf<Pair<String?, String>>()
    private val _separatorForPart = "|"
    private val _separatorForKeyValue = ":"

    override fun add(content: Any?) = apply {
        if (content == null) return@apply
        if (content is String && content.isEmpty()) return@apply
        _list.add(Pair(null, content.toString()))
    }

    override fun addHash(content: Any?) = apply {
        if (content == null) return@apply
        if (content is String && content.isEmpty()) return@apply
        _list.add(Pair(null, content.hashName()))
    }

    override fun pair(key: String?, value: Any?) = apply {
        if (key.isNullOrEmpty()) return@apply
        val finalValue = if (value is View) value.hashName() else value.toString()
        _list.add(Pair(key, finalValue))
    }

    override fun pairHash(key: String?, value: Any?) = apply {
        pair(key, value.hashName())
    }

    override fun pairStr(key: String?, value: Any?) = apply {
        pair(key, value.toString())
    }

    override fun instance(instance: Any?) = apply {
        pair("instance", instance.hashSimpleName())
    }

    override fun instanceHash(instance: Any?) = apply {
        pair("instance", instance.hashName())
    }

    override fun instanceStr(instance: Any?) = apply {
        pair("instanceStr", instance.toString())
    }

    override fun uuid(uuid: String?) = apply {
        pair("uuid", uuid)
    }

    override fun nextLine() = apply {
        add("\n")
    }

    override fun clazz(clazz: Class<*>) = apply {
        add(clazz.simpleName)
    }

    override fun clazzFull(clazz: Class<*>) = apply {
        add(clazz.name)
    }

    override fun throwable(t: Throwable?) = apply {
        if (t != null) {
            nextLine()
            add(Log.getStackTraceString(t))
        }
    }

    override fun clear() = apply {
        _list.clear()
    }

    override fun build(): String {
        val buffer = StringBuffer()

        _list.forEachIndexed { index, item ->
            val key = item.first
            val value = item.second

            // format
            val content = if (key.isNullOrEmpty()) {
                value
            } else {
                "${key}${_separatorForKeyValue}${value}"
            }

            // content
            buffer.append(content)

            if (index < _list.lastIndex) {
                if (item.isNextLine() || _list.getOrNull(index + 1).isNextLine()) {
                    // ignore
                } else {
                    buffer.append(_separatorForPart)
                }
            }
        }

        return buffer.toString()
    }

    override fun toString(): String {
        return build()
    }
}

private fun Any?.hashName(): String {
    if (this == null) return "null"
    return this.javaClass.name + "@" + this.hashString()
}

private fun Any?.hashSimpleName(): String {
    if (this == null) return "null"
    return this.javaClass.simpleName + "@" + this.hashString()
}

private fun Any?.hashString(): String {
    if (this == null) return "null"
    return Integer.toHexString(this.hashCode())
}

private fun Pair<String?, String>?.isNextLine(): Boolean {
    if (this == null) return false
    return first == null && second == "\n"
}