/*
 * Copyright (c) 2020. Copyright 2020 Dave Galloway
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.dave99galloway.typesafemapmap.test

import com.github.dave99galloway.typesafemapmap.*
import com.github.dave99galloway.typesafemapmap.test.StoreByInterfaceTest.AnAbstractIdea
import com.github.dave99galloway.typesafemapmap.test.StoreByInterfaceTest.ConcreteReality
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ActLikeAMutableIterableTest {

    private lateinit var typeSafeMapMap: ITypeSafeMapMap

    @Before
    fun setUp() {
        typeSafeMapMap = TypeSafeMapMap()
    }

    @After
    fun tearDown() {
        typeSafeMapMap = TypeSafeMapMap()
    }

    @Test
    fun iterateOverInts() {
        1.rangeTo(10).forEach { typeSafeMapMap.put(key = it, value = it) }
        val ints = mutableListOf<Int>()
        typeSafeMapMap.forEach<Int, Unit> {
            ints.add(it)
        }
        assertThat(ints).isEqualTo((1..10).toList())
    }

    @Test
    fun mapWithInts() {
        1.rangeTo(10).forEach { typeSafeMapMap.put(key = it, value = it) }
        val ints = typeSafeMapMap.mapWith<Int, Int> {
            it.value
        }
        assertThat(ints).isEqualTo((1..10).toList())
    }

    @Test
    fun operateDirectlyOnMapWithInts() {
        1.rangeTo(10).forEach { typeSafeMapMap.put(key = it, value = it) }
        val ints = typeSafeMapMap.allEntriesOfV<Int>().map { it.value }
        assertThat(ints).isEqualTo((1..10).toList())
    }

    @Test
    fun operateDirectlyOnMapOfAbstractItems() {
        val abstract = "abstract"
        0.rangeTo(10).forEach {
            val abstraction: AnAbstractIdea = ConcreteReality(data = "$abstract$it")
            typeSafeMapMap.putAs(key = "$abstract$it", value = abstraction)
        }
        typeSafeMapMap.allEntriesOfV<AnAbstractIdea>().values.forEachIndexed { i: Int, idea: AnAbstractIdea ->
            assertThat(idea.data).isEqualTo("$abstract$i")
            assertThat(idea.doThing("$abstract$i")).isTrue()
        }
    }

    @Test
    fun removeEntriesOnMapOfAbstractItems() {
        val abstract = "abstract"
        0.rangeTo(10).forEach {
            val abstraction: AnAbstractIdea = ConcreteReality(data = "$abstract$it")
            typeSafeMapMap.putAs(key = "$abstract$it", value = abstraction)
        }
        typeSafeMapMap.allEntriesOfV<AnAbstractIdea>().values.forEachIndexed { i: Int, idea: AnAbstractIdea ->
            assertThat(idea.data).isEqualTo("$abstract$i")
            assertThat(idea.doThing("$abstract$i")).isTrue()
        }
        typeSafeMapMap.mutableEntriesOfV<AnAbstractIdea>().clear()
        assertThat(typeSafeMapMap.allEntriesOfV<AnAbstractIdea>()).isEmpty()
    }
}
