package com.github.dave99galloway.typesafemapmap.test

import com.github.dave99galloway.typesafemapmap.TypeSafeMapMap
import com.github.dave99galloway.typesafemapmap.get
import com.github.dave99galloway.typesafemapmap.putAs
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class StoreByInterfaceTest {

    private lateinit var typeSafeMapMap: TypeSafeMapMap

    @Before
    fun setUp() {
        typeSafeMapMap = TypeSafeMapMap()
    }

    @After
    fun tearDown() {
        typeSafeMapMap = TypeSafeMapMap()
    }

    interface AnAbstractIdea {
        val data: String
        fun doThing(thingName: String): Boolean
    }

    class ConcreteReality(override val data: String) : AnAbstractIdea {
        override fun doThing(thingName: String): Boolean {
            return thingName.isNotEmpty()
        }
    }


    @Test
    fun storeConcrete() {
        val concrete = "Concrete"
        typeSafeMapMap.put(concrete, ConcreteReality(concrete))
        val retrieved: ConcreteReality = typeSafeMapMap.get(concrete)
        assertThat(retrieved.data).isEqualTo(concrete)
    }

    @Test
    fun storeAbstract() {
        val abstract = "abstract"
        //val abstraction: AnAbstractIdea = ConcreteReality(abstract)
        typeSafeMapMap.putAs<String, AnAbstractIdea, AnAbstractIdea>(key = abstract, value = ConcreteReality(abstract))
        val retrieved: AnAbstractIdea = typeSafeMapMap.get(abstract)
        assertThat(retrieved.data).isEqualTo(abstract)
    }


}