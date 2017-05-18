package com.yandong.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by yandong on 2017/5/16.
 */
public class TokenCache {
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PREFIX = "token_";
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                //默认的加载实现，当调用get取值的时候，如果key没有对应的值，就调用这个方法加载
                @Override
                public String load(String s) throws Exception {
                    return "null";//避免空指针
                }
            });//1000为cache的初始化容量,maxSize最大值，超过最大值则调用LRU算法
     public static void setKey(String key,String value){
         localCache.put(key,value);
     }
     public static String getKey(String key){
         String value = null;
         try {
            value = localCache.get(key);
            if("null".equals(value)){
                return null;
            }
            return value;
         }catch (Exception e){
            logger.error("local cache get error!",e);
         }
         return null;
     }
}
