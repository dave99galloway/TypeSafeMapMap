package com.github.dave99galloway.typesafemapmap

import org.apache.commons.collections4.map.LinkedMap
import java.lang.reflect.Type
import java.util.Collections.synchronizedMap

interface ITypeSafeMapMap {
    /*TODO: - can we remove this map property and still make the get work? */
    val map: LinkedHashMap<Type, Any>
    fun <K : Any, V : Any> put(key: K, value: V)

    /* TODO: implement the rest of MutableMap<K,V> (Or even non generic version) interface methods where possible? */
}

inline fun <K : Any, reified V : Any> ITypeSafeMapMap.get(key: K): V {
    val syncMap = synchronizedMap(getMapOfType<V>())
    return synchronized(syncMap) {
        syncMap.get(key = key) as V
    }
}

inline fun <reified V : Any> ITypeSafeMapMap.get(): V {
    val linkedMap = getMapOfType<V>()
    val syncMap = synchronizedMap(linkedMap)
    return synchronized(syncMap) {
        syncMap[linkedMap.lastKey()] as V
    }
}

inline fun <reified V : Any> ITypeSafeMapMap.getMapOfType(): LinkedMap<*, *> {
    return try {
        map[V::class.java] as LinkedMap<*, *>
    } catch (e: NullPointerException) {
        throw  NoneOfThisTypeStoredException(V::class.java, e)
    }
}

class TypeSafeMapMap : ITypeSafeMapMap {
    /*TODO: parameterise the underlying map implementation  */
    override val map: LinkedHashMap<Type, Any> = LinkedHashMap()

    override fun <K : Any, V : Any> put(key: K, value: V) {
        val masterMap = synchronizedMap(map)
        synchronized(masterMap) {
            /*TODO:- this is the big one, can we remove this unchecked cast and still make this class work?*/
            @Suppress("UNCHECKED_CAST")
            val linkedMap = masterMap.computeIfAbsent(value::class.java) {
                LinkedMap<Any, V>()
            } as LinkedMap<Any, V>
            val syncMap = synchronizedMap(linkedMap)
            synchronized(syncMap) {
                syncMap[key] = value
            }
        }
    }
}

inline fun <K : Any, reified V : Any> ITypeSafeMapMap.putAs(key: K, value: V) {
    val masterMap = synchronizedMap(map)
    synchronized(masterMap) {
        /*TODO:- this is the big one, can we remove this unchecked cast and still make this class work?*/
        @Suppress("UNCHECKED_CAST")
        val linkedMap = masterMap.computeIfAbsent(V::class.java) {
            LinkedMap<Any, V>()
        } as LinkedMap<Any, V>
        val syncMap = synchronizedMap(linkedMap)
        synchronized(syncMap) {
            syncMap[key] = value
        }
    }
}


class NoneOfThisTypeStoredException(valueType: Any, cause: Throwable) :
    Exception("No instances of $valueType are stored here", cause)