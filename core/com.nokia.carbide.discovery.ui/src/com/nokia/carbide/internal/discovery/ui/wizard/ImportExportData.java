package com.nokia.carbide.internal.discovery.ui.wizard;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;

class ImportExportData {
	private boolean wantsVersions;
	private Collection<URI> uris;
	private Collection<FeatureInfo> featureInfos;
	
	public ImportExportData() {
		uris = new HashSet<URI>();
		featureInfos = new HashSet<FeatureInfo>();
	}
	
	public ImportExportData(boolean wantsVersions, Collection<URI> uris, Collection<FeatureInfo> featureInfos) {
		this.wantsVersions = wantsVersions;
		this.uris = uris;
		this.featureInfos = featureInfos;
	}

	public void addURI(URI uri) {
		uris.add(uri);
	}
	
	public Collection<URI> getURIs() {
		return uris;
	}
	
	public void addFeatureInfo(FeatureInfo info) {
		featureInfos.add(info);
	}
	
	public Collection<FeatureInfo> getFeatureInfos() {
		return featureInfos;
	}
	
	public void setWantsVersions(boolean wantsVersions) {
		this.wantsVersions = wantsVersions;
	}
	
	public boolean getWantsVersions() {
		return wantsVersions;
	}
}