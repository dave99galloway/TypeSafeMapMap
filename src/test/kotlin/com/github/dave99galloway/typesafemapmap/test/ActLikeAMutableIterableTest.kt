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

import com.github.dave99galloway.typesafemapmap.ITypeSafeMapMap
import com.github.dave99galloway.typesafemapmap.TypeSafeMapMap
import com.github.dave99galloway.typesafemapmap.forEach
import com.github.dave99galloway.typesafemapmap.get
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
        for (x in 1..10) {
            typeSafeMapMap.put(key = x, value = x)
            val retrieved: Int = typeSafeMapMap.get(x)
            assertThat(retrieved).isEqualTo(x)
        }
        val ints = mutableListOf<Int>()
        typeSafeMapMap.forEach<Int, Unit> {
            ints.add(it)
        }
        assertThat(ints).isEqualTo((1..10).toList())
    }
}