package com.nokia.carbide.cpp.internal.sdk.core.model;

import java.io.Serializable;

public class SDKManagerCacheEntry implements Serializable {

	private static final long serialVersionUID = 3694246766733614433L;

	private String id;
	private String epocRoot;
	private String osVersion;
	private boolean isEnabled;

	public SDKManagerCacheEntry() {
		this.id = "";
		this.epocRoot = "";
		this.osVersion = "";
		this.isEnabled = false;
	}

	public SDKManagerCacheEntry(String id, String epocRoot, String osVersion, boolean isEnabled) {
		this.id = id;
		this.epocRoot = epocRoot;
		this.osVersion = osVersion;
		this.isEnabled = isEnabled;
	}

	public String getEpocRoot() {
		return epocRoot;
	}

	public String getId() {
		return this.id;
	}

	public String getOsVersion() {
		return this.osVersion;
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setEpocRoot(String epocRoot) {
		this.epocRoot = epocRoot;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((epocRoot == null) ? 0 : epocRoot.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isEnabled ? 1231 : 1237);
		result = prime * result
				+ ((osVersion == null) ? 0 : osVersion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SDKManagerCacheEntry other = (SDKManagerCacheEntry) obj;
		if (epocRoot == null) {
			if (other.epocRoot != null)
				return false;
		} else if (!epocRoot.equals(other.epocRoot))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		if (osVersion == null) {
			if (other.osVersion != null)
				return false;
		} else if (!osVersion.equals(other.osVersion))
			return false;
		return true;
	}

}