package edu.asu.heal.promis.api.model;

/**
 * This model maps to the schema of the clientInfo table;
 * @author deepak
 *
 */
public class ClientRegistration {
	
	private String clientID;
	private String clientSecret;
	private String issuedAt;
	private String expiresAt;
	private String appType;
	private String appVersion;
	private String patientPIN;
	
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getIssuedAt() {
		return issuedAt;
	}
	public void setIssuedAt(String issuedAt) {
		this.issuedAt = issuedAt;
	}
	public String getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getPatientPIN() {
		return patientPIN;
	}
	public void setPatientPIN(String patientPIN) {
		this.patientPIN = patientPIN;
	}

}
