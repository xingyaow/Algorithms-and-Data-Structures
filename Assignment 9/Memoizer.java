import java.util.HashMap;
public class Memoizer
{
    private HashMap<Object, Object> hash;
    private Functor func;
    
    public Memoizer(Functor f){
        func = f;
        hash = new HashMap<Object,Object>();
    }
    
    public Object call(Object x){
        if(hash.containsKey(x)) ;
        else hash.put(x, func.fn(x));
        return hash.get(x);
    }
}