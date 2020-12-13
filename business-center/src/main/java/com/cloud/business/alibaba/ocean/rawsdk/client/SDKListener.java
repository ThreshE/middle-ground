/**
 * 
 */
package com.cloud.business.alibaba.ocean.rawsdk.client;

import com.cloud.business.alibaba.ocean.rawsdk.client.serialize.DeSerializerListener;
import com.cloud.business.alibaba.ocean.rawsdk.client.serialize.SerializerListener;

/**
 * @author hongbang.hb
 *
 */
public interface SDKListener {
	
	public void register(SerializerListener serializerListener);

	public void register(DeSerializerListener deSerializerListener);
	
}
