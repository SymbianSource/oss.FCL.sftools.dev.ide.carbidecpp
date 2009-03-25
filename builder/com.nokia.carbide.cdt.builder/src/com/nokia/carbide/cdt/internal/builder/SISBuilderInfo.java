/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/
package com.nokia.carbide.cdt.internal.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cdt.internal.api.builder.SISBuilderInfo2;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigFactory;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

/**
 * @deprecated use {@link SISBuilderInfo2} instead
 */
public class SISBuilderInfo implements ISISBuilderInfo {

	private SisBuilderType sisBuilderType;
	private IProject project;
	private ISymbianSDK sdk;
	
	
	public  SisBuilderType getSisBuilderType(){
		return sisBuilderType;
	}
	
	public IProject getProject() {
		return project;
	}
	
	/**
	 * Creates a new object to hold the SisBuilderInfo for one configuration.
	 * @param project - A valid project.
	 * @param sisBuilderType - The DOM interface. If null, a default one will be created init'ed to default values
	 */
	public SISBuilderInfo(IProject project, SisBuilderType sisBuilderType, ISymbianSDK sdk) {
		this.project = project;
		this.sdk = sdk;
		if (sisBuilderType == null){
			this.sisBuilderType = CarbideBuildConfigFactory.eINSTANCE.createSisBuilderType();
		} else {
			this.sisBuilderType = sisBuilderType;
		}
		initNullData();
	}
	
	public IPath getFinalSISFullPath(){
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			ISymbianSDK sdk = cpi.getDefaultConfiguration().getSDK();
			if (sdk.getOSVersion().getMajor() < 9){
				return getUnsignedSISFullPath();
			}
		}
		return getSignedSISFullPath();
	}

	
	public void setPKGFile(String pkgFileString){
		sisBuilderType.setPkgFile(pkgFileString);
	}
	
	public String getPKGFileString(){
		return sisBuilderType.getPkgFile();
	}
	
	public void setContentSearchLocation(String searchLocale){
		sisBuilderType.setContentSearchLocation(searchLocale);
	}
	
	public String getContentSearchLocation(){
		return sisBuilderType.getContentSearchLocation();
	}
	
	public IPath getPKGFullPath(){
		IPath fullPath;
		if (getPKGFileString().indexOf(":") > 0){
			fullPath = new Path(getPKGFileString());
		} else {		
			fullPath = CarbideBuilderPlugin.getProjectRoot(project);
			if (fullPath != null)
				fullPath = fullPath.append(getPKGFileString());
		}
		return fullPath;
	}
	
	public String getCertificate() {
		return sisBuilderType.getCert();
	}
	
	public IPath getCertificateFullPath(){
		IPath fullPath;
		if (getCertificate().indexOf(":") > 0){
			fullPath = new Path(getCertificate());
		} else {		
			fullPath = CarbideBuilderPlugin.getProjectRoot(project);
			fullPath = fullPath.append(getCertificate());
		}
		return fullPath;
	}

	public String getKey() {
		return sisBuilderType.getKey();
	}
	
	public IPath getKeyFullPath(){
		IPath fullPath;
		if (getKey().indexOf(":") > 0){
			fullPath = new Path(getKey());
		} else {		
			fullPath = CarbideBuilderPlugin.getProjectRoot(project);
			fullPath = fullPath.append(getKey());
		}
		return fullPath;
	}

	public String getUnsignedSISFileName() {
		return sisBuilderType.getOutputFileName();
	}
	
	public IPath getUnsignedSISFullPath(){
		IPath fullPath;
		if (getUnsignedSISFileName().length() == 0){
			// Use default PKG file name
			fullPath = getPKGFullPath();
			fullPath = fullPath.removeFileExtension();
			fullPath = fullPath.addFileExtension("sis");
		} else if (getUnsignedSISFileName().indexOf(":") > 0){
			// SIS already a full path
			fullPath = new Path(getUnsignedSISFileName());
		} else {
			// probably a relative path, make relative to PKG file
			fullPath = getPKGFullPath();
			fullPath = fullPath.removeLastSegments(1);
			fullPath = fullPath.append(getUnsignedSISFileName());
		}
		return fullPath;
	}

	public String getPassword() {
		return sisBuilderType.getPassword();
	}

	public String getSignedSISFileName() {
		return sisBuilderType.getSignedFileName();
	}
	
	public IPath getSignedSISFullPath(){
		IPath fullPath;
		if (getSignedSISFileName().length() == 0){
			// Use default PKG file name
			fullPath = getPKGFullPath();
			fullPath = fullPath.removeFileExtension();
			fullPath = fullPath.addFileExtension("sisx");
		} else if (getSignedSISFileName().indexOf(":") > 0){
			// SIS already a full path
			fullPath = new Path(getSignedSISFileName());
		} else {
			// probably a relative path, make relative to PKG file
			fullPath = getPKGFullPath();
			fullPath = fullPath.removeLastSegments(1);
			fullPath = fullPath.append(getSignedSISFileName());
		}
		return fullPath;
	}

	public void setCertificate(String certString) {
		sisBuilderType.setCert(certString);
	}

	public void setKey(String keyString) {
		sisBuilderType.setKey(keyString);
	}

	public void setOutputSISFileName(String outputFileName) {
		sisBuilderType.setOutputFileName(outputFileName);
	}

	public void setPassword(String password) {
		sisBuilderType.setPassword(password);
	}

	public void setSignedSISFileName(String signedFileName) {
		sisBuilderType.setSignedFileName(signedFileName);
	}

	public String getAdditionalOptions() {
		return sisBuilderType.getAdditionalOptions();
	}

	public boolean isCreateStubFormat() {
		return sisBuilderType.isSetCreateStubFormat();
	}

	public void setAdditionalOptions(String options) {
		sisBuilderType.setAdditionalOptions(options);		
	}

	public void setCreateStubFormat(boolean flag) {
		sisBuilderType.setCreateStubFormat(flag);
	}
	
	protected void initNullData(){
		if (sisBuilderType.getAdditionalOptions() == null){
			sisBuilderType.setAdditionalOptions("");
		}
		if (sisBuilderType.getCert() == null){
			sisBuilderType.setCert("");
		}
		if (sisBuilderType.getKey() == null){
			sisBuilderType.setKey("");
		}
		if (sisBuilderType.getOutputFileName() == null){
			sisBuilderType.setOutputFileName("");
		}
		if (sisBuilderType.getPassword() == null){
			sisBuilderType.setPassword("");
		}
		if (sisBuilderType.getPkgFile() == null){
			sisBuilderType.setPkgFile("");
		}
		if (sisBuilderType.getSignedFileName() == null){
			sisBuilderType.setSignedFileName("");
		}
		if (sisBuilderType.getContentSearchLocation() == null){
			sisBuilderType.setContentSearchLocation("");
		}
	}
	
	public int getSigningType() {
		// mimic the behavior from 1.2.x
		if (getCertificate().length() > 0) {
			return KEY_CERT_SIGN;
		}
		if (sdk.isEKA2()) {
			return SELF_SIGN;
		}
		return DONT_SIGN;
	}

	public boolean isEnabled() {
		return true;
	}

	public void setEnabled(boolean enable) {
		// noop
	}

	public void setSigningType(int type) {
		// noop
	}
}
