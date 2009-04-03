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
package com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl;

import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.EnvVarsType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType;

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
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl#getCarbideBuilderConfigInfo <em>Carbide Builder Config Info</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl#getEnvVars <em>Env Vars</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl#getSisBuilder <em>Sis Builder</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl#getVar <em>Var</em>}</li>
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
	protected FeatureMap mixed = null;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap xMLNSPrefixMap = null;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap xSISchemaLocation = null;

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
	protected EClass eStaticClass() {
		return CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, CarbideBuildConfigPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, CarbideBuildConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, CarbideBuildConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CarbideBuilderConfigInfoType getCarbideBuilderConfigInfo() {
		return (CarbideBuilderConfigInfoType)getMixed().get(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCarbideBuilderConfigInfo(CarbideBuilderConfigInfoType newCarbideBuilderConfigInfo, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO, newCarbideBuilderConfigInfo, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCarbideBuilderConfigInfo(CarbideBuilderConfigInfoType newCarbideBuilderConfigInfo) {
		((FeatureMap.Internal)getMixed()).set(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO, newCarbideBuilderConfigInfo);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationType getConfiguration() {
		return (ConfigurationType)getMixed().get(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__CONFIGURATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConfiguration(ConfigurationType newConfiguration, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__CONFIGURATION, newConfiguration, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfiguration(ConfigurationType newConfiguration) {
		((FeatureMap.Internal)getMixed()).set(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__CONFIGURATION, newConfiguration);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnvVarsType getEnvVars() {
		return (EnvVarsType)getMixed().get(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__ENV_VARS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnvVars(EnvVarsType newEnvVars, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__ENV_VARS, newEnvVars, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnvVars(EnvVarsType newEnvVars) {
		((FeatureMap.Internal)getMixed()).set(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__ENV_VARS, newEnvVars);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SisBuilderType getSisBuilder() {
		return (SisBuilderType)getMixed().get(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__SIS_BUILDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSisBuilder(SisBuilderType newSisBuilder, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__SIS_BUILDER, newSisBuilder, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSisBuilder(SisBuilderType newSisBuilder) {
		((FeatureMap.Internal)getMixed()).set(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__SIS_BUILDER, newSisBuilder);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VarType getVar() {
		return (VarType)getMixed().get(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__VAR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVar(VarType newVar, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__VAR, newVar, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVar(VarType newVar) {
		((FeatureMap.Internal)getMixed()).set(CarbideBuildConfigPackage.Literals.DOCUMENT_ROOT__VAR, newVar);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO:
				return basicSetCarbideBuilderConfigInfo(null, msgs);
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__CONFIGURATION:
				return basicSetConfiguration(null, msgs);
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__ENV_VARS:
				return basicSetEnvVars(null, msgs);
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__SIS_BUILDER:
				return basicSetSisBuilder(null, msgs);
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__VAR:
				return basicSetVar(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO:
				return getCarbideBuilderConfigInfo();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__CONFIGURATION:
				return getConfiguration();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__ENV_VARS:
				return getEnvVars();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__SIS_BUILDER:
				return getSisBuilder();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__VAR:
				return getVar();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO:
				setCarbideBuilderConfigInfo((CarbideBuilderConfigInfoType)newValue);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__CONFIGURATION:
				setConfiguration((ConfigurationType)newValue);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__ENV_VARS:
				setEnvVars((EnvVarsType)newValue);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__SIS_BUILDER:
				setSisBuilder((SisBuilderType)newValue);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__VAR:
				setVar((VarType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO:
				setCarbideBuilderConfigInfo((CarbideBuilderConfigInfoType)null);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__CONFIGURATION:
				setConfiguration((ConfigurationType)null);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__ENV_VARS:
				setEnvVars((EnvVarsType)null);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__SIS_BUILDER:
				setSisBuilder((SisBuilderType)null);
				return;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__VAR:
				setVar((VarType)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO:
				return getCarbideBuilderConfigInfo() != null;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__CONFIGURATION:
				return getConfiguration() != null;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__ENV_VARS:
				return getEnvVars() != null;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__SIS_BUILDER:
				return getSisBuilder() != null;
			case CarbideBuildConfigPackage.DOCUMENT_ROOT__VAR:
				return getVar() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl