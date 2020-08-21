import java.lang.reflect.Type

class TypeSafeMapMap {
    val map: HashMap<Type, Any> = HashMap()

    fun <K:Any,V:Any>put(key: K, value: V){
        //var mapToAssign:
        if(!map.containsKey(key = value::class.java)){
            map[value::class.java] = HashMap<Any,V>()
        }
        (map[value::class.java] as HashMap<Any,V>).put(key = key, value = value)
    }

    inline fun <K:Any, reified V:Any>get(key: K) : V{
        //var mapToAssign:

        return (map[V::class.java] as HashMap<Any,V>).get(key = key)as V
    }
}