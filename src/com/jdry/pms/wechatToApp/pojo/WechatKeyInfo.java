package com.jdry.pms.wechatToApp.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_wechat_key_info")
public class WechatKeyInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appId;
	private String appSecret;
	private String grantType;
	private String uriGetToken;
	private String uriBatchgetMaterial;
	private long tokenGetTime;
	private long expiresIn;
	private String accessToken;
	
	@Id
	@Column(name = "app_id")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "app_secret")
	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	@Column(name = "grant_type")
	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	@Column(name = "uri_get_token")
	public String getUriGetToken() {
		return uriGetToken;
	}

	public void setUriGetToken(String uriGetToken) {
		this.uriGetToken = uriGetToken;
	}

	@Column(name = "uri_batchget_material")
	public String getUriBatchgetMaterial() {
		return uriBatchgetMaterial;
	}

	public void setUriBatchgetMaterial(String uriBatchgetMaterial) {
		this.uriBatchgetMaterial = uriBatchgetMaterial;
	}

	@Column(name = "token_get_time")
	public long getTokenGetTime() {
		return tokenGetTime;
	}

	public void setTokenGetTime(long tokenGetTime) {
		this.tokenGetTime = tokenGetTime;
	}

	@Column(name = "expires_in")
	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Column(name = "access_token")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}	
}
