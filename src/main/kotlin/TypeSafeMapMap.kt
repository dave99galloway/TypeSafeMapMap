import java.lang.reflect.Type

interface ITypeSafeMapMap {
    /*TODO: - can we remove this map property and still make the get work? */
    val map: HashMap<Type, Any>
    fun <K: Any,V: Any>put(key: K, value: V)
    /* TODO: implement the rest of MutableMap<K,V> (Or even non generic version) interface methods where possible? */
}

inline fun <K:Any, reified V:Any> ITypeSafeMapMap.get(key: K): V {
    return (map[V::class.java] as HashMap<*, *>).get(key = key)as V
}

class TypeSafeMapMap : ITypeSafeMapMap {
    override val map: HashMap<Type, Any> = HashMap()

    override fun <K:Any,V:Any>put(key: K, value: V){
        if(!map.containsKey(key = value::class.java)){
            map[value::class.java] = HashMap<Any,V>()
        }
        /*TODO:- this is the big one, can we removed this unchecked cast and still make this class work?*/
        @Suppress("UNCHECKED_CAST")
        (map[value::class.java] as HashMap<Any,V>)[key] = value
    }
// inline reified methods can't be defined on an interface, but can be defined as extensions.
    // this isn't the same thing, but allows us to use an abstraction instead of having to use the concrete
    // class
//    inline fun <K:Any, reified V:Any>get(key: K) : V{
//        return (map[V::class.java] as HashMap<Any,V>).get(key = key)as V
//    }
}