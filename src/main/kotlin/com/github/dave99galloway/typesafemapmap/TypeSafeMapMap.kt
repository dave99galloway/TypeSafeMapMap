/*
 * Copyright (c) 2020. Copyright 2020 Dave Galloway
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dave99galloway.typesafemapmap

import org.apache.commons.collections4.map.LinkedMap
import java.lang.reflect.Type
import java.util.Collections.synchronizedMap

/**
 * The ITypeSafeMapMap (TSMM) interface. Currently requires just a map and a put method.
 * most of the work in this implementation is done via extension methods on ITypeSafeMapMap
 * @property map LinkedHashMap<Type, Any> - the top level map that TSMM implementations need to create to hold child maps
 */
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

inline fun <reified V> ITypeSafeMapMap.mutableEntriesOfV(): MutableMap<Any, V> {
    @Suppress("UNCHECKED_CAST")
    return this.map[V::class.java] as LinkedMap<Any, V>
}

inline fun <reified V> ITypeSafeMapMap.allEntriesOfV(): Map<Any, V> {
    @Suppress("UNCHECKED_CAST")
    return this.map[V::class.java] as LinkedMap<Any, V>
}

inline fun <reified V, R> ITypeSafeMapMap.forEach(action: (entry: V) -> R) {
    allEntriesOfV<V>().forEach {
        action(it.value!!)
    }
}

inline fun <reified V, R> ITypeSafeMapMap.mapWith(action: (entry: Map.Entry<Any, V>) -> R): List<R> {
    return allEntriesOfV<V>().map {
        action(it)
    }
}

class NoneOfThisTypeStoredException(valueType: Any, cause: Throwable) :
    Exception("No instances of $valueType are stored here", cause)