import java.lang.reflect.Type

interface ITypeSafeMapMap {
    /*TODO: - can we remove this map property and still make the get work? */
    val map: LinkedHashMap<Type, Any>
    fun <K: Any,V: Any>put(key: K, value: V)
    /* TODO: implement the rest of MutableMap<K,V> (Or even non generic version) interface methods where possible? */
}

inline fun <K:Any, reified V:Any> ITypeSafeMapMap.get(key: K): V {
    return (map[V::class.java] as LinkedHashMap<*, *>).get(key = key)as V
}

class TypeSafeMapMap : ITypeSafeMapMap {
    /*TODO: parameterise the underlying map implementation  */
    override val map: LinkedHashMap<Type, Any> = LinkedHashMap()

    override fun <K:Any,V:Any>put(key: K, value: V){
        if(!map.containsKey(key = value::class.java)){
            map[value::class.java] = LinkedHashMap<Any,V>()
        }
        /*TODO:- this is the big one, can we removed this unchecked cast and still make this class work?*/
        @Suppress("UNCHECKED_CAST")
        (map[value::class.java] as LinkedHashMap<Any,V>)[key] = value
    }
// inline reified methods can't be defined on an interface, but can be defined as extensions.
    // this isn't the same thing, but allows us to use an abstraction instead of having to use the concrete
    // class
//    inline fun <K:Any, reified V:Any>get(key: K) : V{
//        return (map[V::class.java] as HashMap<Any,V>).get(key = key)as V
//    }
}