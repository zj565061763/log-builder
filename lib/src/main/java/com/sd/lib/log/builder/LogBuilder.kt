package com.sd.lib.log.builder

interface LogBuilder {
    /**
     * 设置格式化对象
     */
    fun setFormatter(formatter: Formatter?): LogBuilder

    /**
     * 添加[content]，[content]为null或者空则不添加，
     * [content]用[Object.toString]值显示
     */
    fun add(content: Any?): LogBuilder

    /**
     * 添加[content]，[content]为null或者空则不添加，
     * [content]用hash值表示
     */
    fun addHash(content: Any?): LogBuilder

    /**
     * 添加一对内容，[key]为null或者空则不添加，
     * [value]为[View]时用hash值表示，否则用[Object.toString]值表示
     */
    fun pair(key: String?, value: Any?): LogBuilder

    /**
     * 添加一对内容，[key]为null或者空则不添加，
     * [value]用hash值表示
     */
    fun pairHash(key: String?, value: Any?): LogBuilder

    /**
     * 添加一对内容，[key]为null或者空则不添加，
     * [value]用[Object.toString]值显示
     */
    fun pairStr(key: String?, value: Any?): LogBuilder

    /**
     * 等价与：pair("instance", instance)，
     * [instance]用hash值表示
     */
    fun instance(instance: Any?): LogBuilder

    /**
     * 等价与：pair("instanceStr", instance)，
     * [instance]用[Object.toString]值表示
     */
    fun instanceStr(instance: Any?): LogBuilder

    /**
     * 等价与：pair("uuid", uuid)
     */
    fun uuid(uuid: String?): LogBuilder

    /**
     * 换行
     */
    fun nextLine(): LogBuilder

    /**
     * 等价与：add(clazz.getSimpleName())
     */
    fun clazz(clazz: Class<*>): LogBuilder

    /**
     * 等价与：add(clazz.getName())
     */
    fun clazzFull(clazz: Class<*>): LogBuilder

    /**
     * 清空
     */
    fun clear(): LogBuilder

    /**
     * 构建字符串
     */
    fun build(): String

    interface Formatter {
        /** 返回key-value之间的分隔符，默认":" */
        val separatorForKeyValue: String

        /** 返回段与段之间的分隔符，默认"|" */
        val separatorForPart: String
    }
}