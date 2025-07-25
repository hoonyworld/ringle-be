package org.ringle.globalutils.auth;

public enum Role {
	USER("ROLE_USER"),
	COMPANY("ROLE_COMPANY"),
	ADMIN("ROLE_ADMIN");

	private final String key;

	Role(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
