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
package com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl;

import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigurationsType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentsType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getBldInfFile <em>Bld Inf File</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getBldInfFiles <em>Bld Inf Files</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getBldInfImportData <em>Bld Inf Import Data</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getBuildConfig <em>Build Config</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getBuildConfigurations <em>Build Configurations</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getInfComponent <em>Inf Component</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getInfComponents <em>Inf Components</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getMakMakeRef <em>Mak Make Ref</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getMakMakeRefs <em>Mak Make Refs</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl#getRootDirectory <em>Root Directory</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfImportTestDataPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, InfImportTestDataPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, InfImportTestDataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, InfImportTestDataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BldInfFileType getBldInfFile() {
		return (BldInfFileType)getMixed().get(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BLD_INF_FILE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBldInfFile(BldInfFileType newBldInfFile, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BLD_INF_FILE, newBldInfFile, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBldInfFile(BldInfFileType newBldInfFile) {
		((FeatureMap.Internal)getMixed()).set(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BLD_INF_FILE, newBldInfFile);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BldInfFilesType getBldInfFiles() {
		return (BldInfFilesType)getMixed().get(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BLD_INF_FILES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBldInfFiles(BldInfFilesType newBldInfFiles, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BLD_INF_FILES, newBldInfFiles, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBldInfFiles(BldInfFilesType newBldInfFiles) {
		((FeatureMap.Internal)getMixed()).set(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BLD_INF_FILES, newBldInfFiles);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BldInfImportDataType getBldInfImportData() {
		return (BldInfImportDataType)getMixed().get(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BLD_INF_IMPORT_DATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBldInfImportData(BldInfImportDataType newBldInfImportData, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BLD_INF_IMPORT_DATA, newBldInfImportData, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBldInfImportData(BldInfImportDataType newBldInfImportData) {
		((FeatureMap.Internal)getMixed()).set(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BLD_INF_IMPORT_DATA, newBldInfImportData);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildConfigType getBuildConfig() {
		return (BuildConfigType)getMixed().get(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BUILD_CONFIG, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBuildConfig(BuildConfigType newBuildConfig, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BUILD_CONFIG, newBuildConfig, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuildConfig(BuildConfigType newBuildConfig) {
		((FeatureMap.Internal)getMixed()).set(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BUILD_CONFIG, newBuildConfig);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildConfigurationsType getBuildConfigurations() {
		return (BuildConfigurationsType)getMixed().get(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BUILD_CONFIGURATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBuildConfigurations(BuildConfigurationsType newBuildConfigurations, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BUILD_CONFIGURATIONS, newBuildConfigurations, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuildConfigurations(BuildConfigurationsType newBuildConfigurations) {
		((FeatureMap.Internal)getMixed()).set(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__BUILD_CONFIGURATIONS, newBuildConfigurations);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfComponentType getInfComponent() {
		return (InfComponentType)getMixed().get(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__INF_COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInfComponent(InfComponentType newInfComponent, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__INF_COMPONENT, newInfComponent, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInfComponent(InfComponentType newInfComponent) {
		((FeatureMap.Internal)getMixed()).set(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__INF_COMPONENT, newInfComponent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfComponentsType getInfComponents() {
		return (InfComponentsType)getMixed().get(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__INF_COMPONENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInfComponents(InfComponentsType newInfComponents, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__INF_COMPONENTS, newInfComponents, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInfComponents(InfComponentsType newInfComponents) {
		((FeatureMap.Internal)getMixed()).set(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__INF_COMPONENTS, newInfComponents);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MakMakeRefType getMakMakeRef() {
		return (MakMakeRefType)getMixed().get(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__MAK_MAKE_REF, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMakMakeRef(MakMakeRefType newMakMakeRef, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__MAK_MAKE_REF, newMakMakeRef, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMakMakeRef(MakMakeRefType newMakMakeRef) {
		((FeatureMap.Internal)getMixed()).set(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__MAK_MAKE_REF, newMakMakeRef);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MakMakeRefsType getMakMakeRefs() {
		return (MakMakeRefsType)getMixed().get(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__MAK_MAKE_REFS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMakMakeRefs(MakMakeRefsType newMakMakeRefs, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__MAK_MAKE_REFS, newMakMakeRefs, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMakMakeRefs(MakMakeRefsType newMakMakeRefs) {
		((FeatureMap.Internal)getMixed()).set(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__MAK_MAKE_REFS, newMakMakeRefs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootDirectoryType getRootDirectory() {
		return (RootDirectoryType)getMixed().get(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__ROOT_DIRECTORY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRootDirectory(RootDirectoryType newRootDirectory, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__ROOT_DIRECTORY, newRootDirectory, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootDirectory(RootDirectoryType newRootDirectory) {
		((FeatureMap.Internal)getMixed()).set(InfImportTestDataPackage.Literals.DOCUMENT_ROOT__ROOT_DIRECTORY, newRootDirectory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfImportTestDataPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_FILE:
				return basicSetBldInfFile(null, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_FILES:
				return basicSetBldInfFiles(null, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_IMPORT_DATA:
				return basicSetBldInfImportData(null, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__BUILD_CONFIG:
				return basicSetBuildConfig(null, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__BUILD_CONFIGURATIONS:
				return basicSetBuildConfigurations(null, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__INF_COMPONENT:
				return basicSetInfComponent(null, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__INF_COMPONENTS:
				return basicSetInfComponents(null, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__MAK_MAKE_REF:
				return basicSetMakMakeRef(null, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__MAK_MAKE_REFS:
				return basicSetMakMakeRefs(null, msgs);
			case InfImportTestDataPackage.DOCUMENT_ROOT__ROOT_DIRECTORY:
				return basicSetRootDirectory(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfImportTestDataPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case InfImportTestDataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case InfImportTestDataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_FILE:
				return getBldInfFile();
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_FILES:
				return getBldInfFiles();
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_IMPORT_DATA:
				return getBldInfImportData();
			case InfImportTestDataPackage.DOCUMENT_ROOT__BUILD_CONFIG:
				return getBuildConfig();
			case InfImportTestDataPackage.DOCUMENT_ROOT__BUILD_CONFIGURATIONS:
				return getBuildConfigurations();
			case InfImportTestDataPackage.DOCUMENT_ROOT__INF_COMPONENT:
				return getInfComponent();
			case InfImportTestDataPackage.DOCUMENT_ROOT__INF_COMPONENTS:
				return getInfComponents();
			case InfImportTestDataPackage.DOCUMENT_ROOT__MAK_MAKE_REF:
				return getMakMakeRef();
			case InfImportTestDataPackage.DOCUMENT_ROOT__MAK_MAKE_REFS:
				return getMakMakeRefs();
			case InfImportTestDataPackage.DOCUMENT_ROOT__ROOT_DIRECTORY:
				return getRootDirectory();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InfImportTestDataPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_FILE:
				setBldInfFile((BldInfFileType)newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_FILES:
				setBldInfFiles((BldInfFilesType)newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_IMPORT_DATA:
				setBldInfImportData((BldInfImportDataType)newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BUILD_CONFIG:
				setBuildConfig((BuildConfigType)newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BUILD_CONFIGURATIONS:
				setBuildConfigurations((BuildConfigurationsType)newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__INF_COMPONENT:
				setInfComponent((InfComponentType)newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__INF_COMPONENTS:
				setInfComponents((InfComponentsType)newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__MAK_MAKE_REF:
				setMakMakeRef((MakMakeRefType)newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__MAK_MAKE_REFS:
				setMakMakeRefs((MakMakeRefsType)newValue);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__ROOT_DIRECTORY:
				setRootDirectory((RootDirectoryType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case InfImportTestDataPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_FILE:
				setBldInfFile((BldInfFileType)null);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_FILES:
				setBldInfFiles((BldInfFilesType)null);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_IMPORT_DATA:
				setBldInfImportData((BldInfImportDataType)null);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BUILD_CONFIG:
				setBuildConfig((BuildConfigType)null);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BUILD_CONFIGURATIONS:
				setBuildConfigurations((BuildConfigurationsType)null);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__INF_COMPONENT:
				setInfComponent((InfComponentType)null);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__INF_COMPONENTS:
				setInfComponents((InfComponentsType)null);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__MAK_MAKE_REF:
				setMakMakeRef((MakMakeRefType)null);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__MAK_MAKE_REFS:
				setMakMakeRefs((MakMakeRefsType)null);
				return;
			case InfImportTestDataPackage.DOCUMENT_ROOT__ROOT_DIRECTORY:
				setRootDirectory((RootDirectoryType)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case InfImportTestDataPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case InfImportTestDataPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case InfImportTestDataPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_FILE:
				return getBldInfFile() != null;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_FILES:
				return getBldInfFiles() != null;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BLD_INF_IMPORT_DATA:
				return getBldInfImportData() != null;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BUILD_CONFIG:
				return getBuildConfig() != null;
			case InfImportTestDataPackage.DOCUMENT_ROOT__BUILD_CONFIGURATIONS:
				return getBuildConfigurations() != null;
			case InfImportTestDataPackage.DOCUMENT_ROOT__INF_COMPONENT:
				return getInfComponent() != null;
			case InfImportTestDataPackage.DOCUMENT_ROOT__INF_COMPONENTS:
				return getInfComponents() != null;
			case InfImportTestDataPackage.DOCUMENT_ROOT__MAK_MAKE_REF:
				return getMakMakeRef() != null;
			case InfImportTestDataPackage.DOCUMENT_ROOT__MAK_MAKE_REFS:
				return getMakMakeRefs() != null;
			case InfImportTestDataPackage.DOCUMENT_ROOT__ROOT_DIRECTORY:
				return getRootDirectory() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
