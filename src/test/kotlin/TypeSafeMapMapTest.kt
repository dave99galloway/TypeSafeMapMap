import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class TypeSafeMapMapTest {

    lateinit var typeSafeMapMap: TypeSafeMapMap

    @Before
    fun setUp() {
        typeSafeMapMap = TypeSafeMapMap()
    }

    @After
    fun tearDown() {
        typeSafeMapMap = TypeSafeMapMap()
    }

    @Test
    fun getMap() {
        assertThat(typeSafeMapMap.map).isNotNull

    }

    @Test
    fun setStringValue(){
        val valToSave = "Hello, World!"
        val keyToSave = "greeting"
        typeSafeMapMap.put(key = keyToSave,value = valToSave)
        val retrieved:String = typeSafeMapMap.get(keyToSave)
        assertThat(retrieved).isEqualTo(valToSave)

    }
}