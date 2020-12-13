/**
 * 
 */
package com.cloud.business.alibaba.ocean.rawsdk.client;

import com.cloud.business.alibaba.ocean.rawsdk.client.entity.AuthorizationToken;
import com.cloud.business.alibaba.ocean.rawsdk.client.exception.OceanException;
import com.cloud.business.alibaba.ocean.rawsdk.client.policy.RequestPolicy;

/**
 * @author hongbang.hb
 *
 */
public interface APIClient {


	public <T> T send(Request request, Class<T> resultType, RequestPolicy policy) throws OceanException;


	public AuthorizationToken getToken(String code) throws OceanException;


	public AuthorizationToken refreshToken(String refreshToken) throws OceanException;
	
	

	public void start();

	public void shutdown();


}
