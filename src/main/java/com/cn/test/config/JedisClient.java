package com.cn.test.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;


import java.util.List;
import java.util.Map;
import java.util.Set;

@Component(value = "jedis")
public class JedisClient {
    private final Logger logger= LoggerFactory.getLogger(JedisClient.class);

    @Autowired
    @Qualifier("jedisPool")
    private JedisPool pool;

    private final int EXPIRE=20*60;

    private Jedis getJedis(){
        return pool.getResource();
    }

    private void closeJedis(Jedis jedis){
        jedis.close();
    }

    public JedisPool getJedisPool(){
        return pool;
    }

    public String get(String key){

        Jedis jedis=getJedis();
        String value = null;
        try{
            value=jedis.get(key);
        }catch (Exception e){
            logger.error("get err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }

        return value;
    }

    public <T> T  get(String key,Class<T> tClass){
        Jedis jedis=getJedis();
        String value = null;
        try{
            value=jedis.get(key);
        }catch (Exception e){
            logger.error("get err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        T res=null;
        if (value!=null){
            try {
                res= JSON.parseObject(value,tClass);
            }catch (Exception e){
                logger.error("RedisUtils获取值转换对象错误！错误信息："+e.getMessage());
                return null;
            }
        }
        return res;
    }

    public Set<String> keys(String param){
        Jedis jedis = getJedis();
        Set<String> res = null;
        try{
            res = jedis.keys(param);
        }catch (Exception e){
            logger.error("keys err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public String setWithExpire(String key,String value){
        Jedis jedis=getJedis();
        String res = null;
        try{
            res = jedis.set(key,value);
            expire(key);
        }catch (Exception e){
            logger.error("setWithExpire err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public String setWithExpire(String key,String value,int seconds){
        Jedis jedis=getJedis();
        String res = null;
        try{
            res=jedis.set(key,value);
            expire(key,seconds);
        }catch (Exception e){
            logger.error("setWithExpire err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Boolean exists(String key){
        Jedis jedis=getJedis();
        Boolean res = null;
        try{
            res=jedis.exists(key);
        }catch (Exception e){
            logger.error("exists err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Long del(String key){
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.del(key);
        }catch (Exception e){
            logger.error("del err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public long expire(String key,int seconds){
        if (seconds<=0){
            return -1L;
        }
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.expire(key,seconds);
        }catch (Exception e){
            logger.error("expire err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public long expire(String key){
        return expire(key,EXPIRE);
    }

    public long ttl(String key){
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.ttl(key);
        }catch (Exception e){
            logger.error("set err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public String set(String key,String value){
        Jedis jedis=getJedis();
        String res = null;
        try{
            res=jedis.set(key, value);
        }catch (Exception e){
            logger.error("set err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }

        return res;
    }

    public String setex(String key,String value, int expire){
        Jedis jedis=getJedis();
        String res = null;
        try{
            res=jedis.setex(key, expire, value);
        }catch (Exception e){
            logger.error("setex err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Long hset(String key, String field, String value){
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.hset(key, field, value);
        }catch (Exception e){
            logger.error("hset err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public String hmset(String key, Map<String, String> map){
        Jedis jedis=getJedis();
        String res = null;
        try{
            res=jedis.hmset(key, map);
        }catch (Exception e){
            logger.error("hmset err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Long hincrBy(String key, String field, long l){
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.hincrBy(key, field, l);
        }catch (Exception e){
            logger.error("hincrBy err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public String hget(String key, String field){
        Jedis jedis=getJedis();
        String res = null;
        try{
            res=jedis.hget(key, field);
        }catch (Exception e){
            logger.error("hget err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Map<String, String> hgetAll(String key){
        Jedis jedis=getJedis();
        Map<String, String> res = null;
        try{
            res=jedis.hgetAll(key);
        }catch (Exception e){
            logger.error("hgetAll err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Boolean hexists(String key, String field){
        Jedis jedis=getJedis();
        Boolean res = null;
        try{
            res=jedis.hexists(key, field);
        }catch (Exception e){
            logger.error("hexists err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Long hlen(String key){
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.hlen(key);
        }catch (Exception e){
            logger.error("hlen err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }
    public Long hdel(String key, String ...fields){
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.hdel(key, fields);
        }catch (Exception e){
            logger.error("hdel err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public String getSet(String key,String value){
        Jedis jedis=getJedis();
        String res = null;
        try{
            res=jedis.getSet(key,value);
        }catch (Exception e){
            logger.error("getSet err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Long setnx(String key,String value){
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.setnx(key,value);
        }catch (Exception e){
            logger.error("setnx err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Long incr(String key){
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.incr(key);
        }catch (Exception e){
            logger.error("incr err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    /**
     *
     * @param jedisPubSub
     * @param patterns  例如：　__keyevent@0__:expired　参考网址：https://blog.csdn.net/zhu_tianwei/article/details/80169900
     */
    public void psubscribe(JedisPubSub jedisPubSub, String patterns){
        Jedis jedis=getJedis();
        jedis.psubscribe(jedisPubSub,patterns);
    }

    public void config(String parameter,String configValue){
        Jedis jedis=getJedis();
        try{
            List<String> configs=jedis.configGet(parameter);
            if ("".equals(configs.get(1))){
                String reply=jedis.configSet(parameter,configValue);
                logger.info(reply);
            }
        }catch (Exception e){
            logger.error("config err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
    }

//    public void main(String[] args) {
//        String param="notify-keyspace-events";
//        String value="Ex";
//        RedisUtils.config(param,value);
//    }



    public Long sadd(String key,String value){
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("sadd err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Long scard(String key){
        Jedis jedis=getJedis();
        Long res = null;
        try{
            res=jedis.scard(key);
        }catch (Exception e){
            logger.error("scard err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Long srem(String key){
        Jedis jedis = getJedis();
        Long res = null;
        try{
            res=jedis.srem(key);
        }catch (Exception e){
            logger.error("srem err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;
    }

    public Set<String> SMEMBERS(String key){
        Jedis jedis = getJedis();
        Set<String> res = null;
        try{
            res=jedis.smembers(key);
        }catch (Exception e){
            logger.error("srem err:"+e.getMessage());
        }finally {
            closeJedis(jedis);
        }
        return res;

    }

}


