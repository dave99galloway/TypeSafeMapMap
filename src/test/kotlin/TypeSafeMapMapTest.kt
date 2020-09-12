import com.github.dave99galloway.typesafemapmap.ITypeSafeMapMap
import com.github.dave99galloway.typesafemapmap.TypeSafeMapMap
import com.github.dave99galloway.typesafemapmap.get
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class TypeSafeMapMapTest {

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
    fun getMap() {
        assertThat(typeSafeMapMap.map).isNotNull
    }

    @Test
    fun setStringValue() {
        val valToSave = "Hello, World!"
        val keyToSave = "greeting"
        typeSafeMapMap.put(key = keyToSave, value = valToSave)
        val retrieved: String = typeSafeMapMap.get(keyToSave)
        assertThat(retrieved).isEqualTo(valToSave)

    }

    @Test
    fun setIntValue() {
        val valToSave = 1
        val keyToSave = "one"
        typeSafeMapMap.put(key = keyToSave, value = valToSave)
        val retrieved: Int = typeSafeMapMap.get(keyToSave)
        assertThat(retrieved).isEqualTo(valToSave)
    }

    @Test
    fun setIntValueWithIntKey() {
        val valToSave = 1
        val keyToSave = 1
        typeSafeMapMap.put(key = keyToSave, value = valToSave)
        val retrieved: Int = typeSafeMapMap.get(keyToSave)
        assertThat(retrieved).isEqualTo(valToSave)
    }

    @Test
    fun setIntValueWithIntKeySameInstance() {
        val kvToSave = 1
        typeSafeMapMap.put(key = kvToSave, value = kvToSave)
        val retrieved: Int = typeSafeMapMap.get(kvToSave)
        assertThat(retrieved).isEqualTo(kvToSave)
    }

    @Test
    fun setCustomClassValue() {
        val valToSave = this
        val keyToSave = "TypeSafeMapMapTest"
        typeSafeMapMap.put(key = keyToSave, value = valToSave)
        val retrieved: TypeSafeMapMapTest = typeSafeMapMap.get(keyToSave)
        assertThat(retrieved).isEqualTo(valToSave)
    }

    @Test
    fun retrieveLastValueByDefault() {
        for (x in 1..10) {
            typeSafeMapMap.put(key = x, value = x)
            val retrieved: Int = typeSafeMapMap.get(x)
            assertThat(retrieved).isEqualTo(x)
        }
        val lastInt: Int = typeSafeMapMap.get()
        assertThat(lastInt).isEqualTo(10)
    }
}