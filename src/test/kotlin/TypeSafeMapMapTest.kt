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

    @Test
    fun setIntValue(){
        val valToSave = 1
        val keyToSave = "one"
        typeSafeMapMap.put(key = keyToSave,value = valToSave)
        val retrieved:Int = typeSafeMapMap.get(keyToSave)
        assertThat(retrieved).isEqualTo(valToSave)
    }

    @Test
    fun setCustomClassValue(){
        val valToSave = this
        val keyToSave = "TypeSafeMapMapTest"
        typeSafeMapMap.put(key = keyToSave,value = valToSave)
        val retrieved:TypeSafeMapMapTest = typeSafeMapMap.get(keyToSave)
        assertThat(retrieved).isEqualTo(valToSave)
    }
}