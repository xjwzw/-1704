package redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisTest {

//	@Test
//	public void test1(){
//		Jedis jedis=new Jedis("192.168.161.40",7000);
//		String name= jedis.get("name");
//		System.out.println(name);
//		jedis.close();
//	}
//	
//	@Test
//	public void shard(){
//		GenericObjectPoolConfig config=new GenericObjectPoolConfig();
//		config.setMaxTotal(50);
//		
//		List<JedisShardInfo> shardsList=new ArrayList<JedisShardInfo>();
//		JedisShardInfo info1=new JedisShardInfo("192.168.161.40",7000);
//		shardsList.add(info1);
//		JedisShardInfo info2=new JedisShardInfo("192.168.161.40",7001);
//		shardsList.add(info2);
//		JedisShardInfo info3=new JedisShardInfo("192.168.247.20",6381);
//		//shardsList.add(info3);
//		
//		ShardedJedisPool shardJedisPool=new ShardedJedisPool(config, shardsList);
//		ShardedJedis jedis=shardJedisPool.getResource();
//		for (int i = 0; i < 100; i++) {
//			jedis.set("n"+i, "tt"+"i");
//		}
//		jedis.close();
//	}
	
	
}
