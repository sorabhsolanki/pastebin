package com.pastebin.util;

/**
 */
public interface CacheConstants {

    String CACHE_IN_MEMORY = "inMemory";
    String CACHE_DISTRIBUTED_REDIS = "redisHandler";
    String CACHE_DISTRIBUTED_AEROSPIKE = "aerospikeHandler";

    String FILE_STORAGE = "simpleDirectory";
    String MONGO_DB_STORAGE = "mongoDBGridFS";

    String INITIAL_POOL_SIZE = "initial.pool.size";
    String MAXIMUM_POOL_SIZE = "maximum.pool.size";
    String KEEP_ALIVE_TIME_IN_SEC = "keep.alive.time.in.sec";
    String WORK_QUEUE_SIZE = "work.queue.size";

}
