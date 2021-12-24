package cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CustomCache {
    public static void setCache(String data){
        CacheManager cm = CacheManager.getInstance();
        if (cm.cacheExists("data")){
            Cache cache = cm.getCache("data");
            cache.put(new Element(cache.getSize(), data));
            System.out.println(cache.get(cache.getSize()-1));
        }
        else {
            cm.addCache("data");
            Cache cache = cm.getCache("data");
            cache.put(new Element(cache.getSize(), data));
            System.out.println(cache.get(cache.getSize()-1));
        }


    }
}
