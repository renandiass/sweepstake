# Create a cluster of 6 nodes, 3 in each dc
ccm create test -v 2.2.6 -n 3:3 -s

CREATE TABLE sweepstake.sw_st_client (
	des_email text,
	des_name text,
    des_password text,
	PRIMARY KEY (des_email)
) WITH bloom_filter_fp_chance = 0.01
AND comment = 'Table responsible to store client information'
AND dclocal_read_repair_chance = 0.1
AND default_time_to_live = 0
AND gc_grace_seconds = 864000
AND max_index_interval = 2048
AND memtable_flush_period_in_ms = 0
AND min_index_interval = 128
AND read_repair_chance = 0.0
AND speculative_retry = '99.0PERCENTILE'
AND caching = {
	'keys' : 'ALL',
	'rows_per_partition' : 'NONE'
}
AND compression = {
	'chunk_length_in_kb' : 64,
	'class' : 'LZ4Compressor'
}
AND compaction = {
	'bucket_high' : 1.5,
	'bucket_low' : 0.5,
	'class' : 'SizeTieredCompactionStrategy',
	'enabled' : true,
	'max_threshold' : 32,
	'min_sstable_size' : 50,
	'min_threshold' : 4,
	'tombstone_compaction_interval' : 86400,
	'tombstone_threshold' : 0.2,
	'unchecked_tombstone_compaction' : false
};



CREATE KEYSPACE sweepstake
WITH durable_writes = true
AND replication = {
	'class' : 'NetworkTopologyStrategy',
	'dc1' : 3,
	'dc2' : 3
};

