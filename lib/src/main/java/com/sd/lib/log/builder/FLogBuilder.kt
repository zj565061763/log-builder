package com.sd.lib.log.builder

import android.util.Log
import android.view.View

class FLogBuilder : LogBuilder {
    private val _list = mutableListOf<Pair<String?, String>>()
    private var _formatter: LogBuilder.Formatter? = null
    private val formatter get() = _formatter ?: DefaultLogFormatter

    override fun setFormatter(formatter: LogBuilder.Formatter?) = apply {
        _formatter = formatter
    }

    override fun add(content: Any?) = apply {
        if (content == null) return@apply
        if (content is String && content.isEmpty()) return@apply
        _list.add(Pair(null, content.toString()))
    }

    override fun addHash(content: Any?) = apply {
        if (content == null) return@apply
        if (content is String && content.isEmpty()) return@apply
        _list.add(Pair(null, content.hashString()))
    }

    override fun pair(key: String?, value: Any?) = apply {
        if (key.isNullOrEmpty()) return@apply
        val finalValue = if (value is View) value.hashString() else value.toString()
        _list.add(Pair(key, finalValue))
    }

    override fun pairHash(key: String?, value: Any?) = apply {
        pair(key, value.hashString())
    }

    override fun pairStr(key: String?, value: Any?) = apply {
        pair(key, value.toString())
    }

    override fun instance(instance: Any?) = apply {
        pair("instance", instance.hashString())
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
        return buildString {
            _list.forEach { item ->
                append(formatter.separatorForPart)
                val key = item.first
                val value = item.second
                if (key.isNullOrEmpty()) {
                    append(value)
                } else {
                    append(key).append(formatter.separatorForKeyValue).append(value)
                }
            }
        }
    }

    override fun toString(): String {
        return build()
    }
}

private object DefaultLogFormatter : LogBuilder.Formatter {
    override val separatorForKeyValue get() = ":"
    override val separatorForPart get() = "|"
}

private fun Any?.hashString(): String {
    if (this == null) return "null"
    return this.javaClass.name + "@" + Integer.toHexString(this.hashCode())
}