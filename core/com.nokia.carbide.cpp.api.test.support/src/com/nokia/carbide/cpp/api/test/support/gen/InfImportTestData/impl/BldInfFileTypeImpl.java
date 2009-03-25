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
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigurationsType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentsType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bld Inf File Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl#getRootDirectory <em>Root Directory</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl#getBuildConfigurations <em>Build Configurations</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl#getInfComponents <em>Inf Components</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl#getMakMakeRefs <em>Mak Make Refs</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl#getSdkId <em>Sdk Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BldInfFileTypeImpl extends EObjectImpl implements BldInfFileType {
	/**
	 * The cached value of the '{@link #getRootDirectory() <em>Root Directory</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootDirectory()
	 * @generated
	 * @ordered
	 */
	protected RootDirectoryType rootDirectory;

	/**
	 * The cached value of the '{@link #getBuildConfigurations() <em>Build Configurations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildConfigurations()
	 * @generated
	 * @ordered
	 */
	protected BuildConfigurationsType buildConfigurations;

	/**
	 * The cached value of the '{@link #getInfComponents() <em>Inf Components</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfComponents()
	 * @generated
	 * @ordered
	 */
	protected InfComponentsType infComponents;

	/**
	 * The cached value of the '{@link #getMakMakeRefs() <em>Mak Make Refs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMakMakeRefs()
	 * @generated
	 * @ordered
	 */
	protected MakMakeRefsType makMakeRefs;

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected String projectName = PROJECT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSdkId() <em>Sdk Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSdkId()
	 * @generated
	 * @ordered
	 */
	protected static final String SDK_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSdkId() <em>Sdk Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSdkId()
	 * @generated
	 * @ordered
	 */
	protected String sdkId = SDK_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BldInfFileTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfImportTestDataPackage.Literals.BLD_INF_FILE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootDirectoryType getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRootDirectory(RootDirectoryType newRootDirectory, NotificationChain msgs) {
		RootDirectoryType oldRootDirectory = rootDirectory;
		rootDirectory = newRootDirectory;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__ROOT_DIRECTORY, oldRootDirectory, newRootDirectory);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootDirectory(RootDirectoryType newRootDirectory) {
		if (newRootDirectory != rootDirectory) {
			NotificationChain msgs = null;
			if (rootDirectory != null)
				msgs = ((InternalEObject)rootDirectory).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InfImportTestDataPackage.BLD_INF_FILE_TYPE__ROOT_DIRECTORY, null, msgs);
			if (newRootDirectory != null)
				msgs = ((InternalEObject)newRootDirectory).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InfImportTestDataPackage.BLD_INF_FILE_TYPE__ROOT_DIRECTORY, null, msgs);
			msgs = basicSetRootDirectory(newRootDirectory, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__ROOT_DIRECTORY, newRootDirectory, newRootDirectory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildConfigurationsType getBuildConfigurations() {
		return buildConfigurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBuildConfigurations(BuildConfigurationsType newBuildConfigurations, NotificationChain msgs) {
		BuildConfigurationsType oldBuildConfigurations = buildConfigurations;
		buildConfigurations = newBuildConfigurations;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS, oldBuildConfigurations, newBuildConfigurations);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuildConfigurations(BuildConfigurationsType newBuildConfigurations) {
		if (newBuildConfigurations != buildConfigurations) {
			NotificationChain msgs = null;
			if (buildConfigurations != null)
				msgs = ((InternalEObject)buildConfigurations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InfImportTestDataPackage.BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS, null, msgs);
			if (newBuildConfigurations != null)
				msgs = ((InternalEObject)newBuildConfigurations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InfImportTestDataPackage.BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS, null, msgs);
			msgs = basicSetBuildConfigurations(newBuildConfigurations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS, newBuildConfigurations, newBuildConfigurations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfComponentsType getInfComponents() {
		return infComponents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInfComponents(InfComponentsType newInfComponents, NotificationChain msgs) {
		InfComponentsType oldInfComponents = infComponents;
		infComponents = newInfComponents;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__INF_COMPONENTS, oldInfComponents, newInfComponents);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInfComponents(InfComponentsType newInfComponents) {
		if (newInfComponents != infComponents) {
			NotificationChain msgs = null;
			if (infComponents != null)
				msgs = ((InternalEObject)infComponents).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InfImportTestDataPackage.BLD_INF_FILE_TYPE__INF_COMPONENTS, null, msgs);
			if (newInfComponents != null)
				msgs = ((InternalEObject)newInfComponents).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InfImportTestDataPackage.BLD_INF_FILE_TYPE__INF_COMPONENTS, null, msgs);
			msgs = basicSetInfComponents(newInfComponents, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__INF_COMPONENTS, newInfComponents, newInfComponents));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MakMakeRefsType getMakMakeRefs() {
		return makMakeRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMakMakeRefs(MakMakeRefsType newMakMakeRefs, NotificationChain msgs) {
		MakMakeRefsType oldMakMakeRefs = makMakeRefs;
		makMakeRefs = newMakMakeRefs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__MAK_MAKE_REFS, oldMakMakeRefs, newMakMakeRefs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMakMakeRefs(MakMakeRefsType newMakMakeRefs) {
		if (newMakMakeRefs != makMakeRefs) {
			NotificationChain msgs = null;
			if (makMakeRefs != null)
				msgs = ((InternalEObject)makMakeRefs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InfImportTestDataPackage.BLD_INF_FILE_TYPE__MAK_MAKE_REFS, null, msgs);
			if (newMakMakeRefs != null)
				msgs = ((InternalEObject)newMakMakeRefs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InfImportTestDataPackage.BLD_INF_FILE_TYPE__MAK_MAKE_REFS, null, msgs);
			msgs = basicSetMakMakeRefs(newMakMakeRefs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__MAK_MAKE_REFS, newMakMakeRefs, newMakMakeRefs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectName(String newProjectName) {
		String oldProjectName = projectName;
		projectName = newProjectName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__PROJECT_NAME, oldProjectName, projectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSdkId() {
		return sdkId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSdkId(String newSdkId) {
		String oldSdkId = sdkId;
		sdkId = newSdkId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_FILE_TYPE__SDK_ID, oldSdkId, sdkId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__ROOT_DIRECTORY:
				return basicSetRootDirectory(null, msgs);
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS:
				return basicSetBuildConfigurations(null, msgs);
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__INF_COMPONENTS:
				return basicSetInfComponents(null, msgs);
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__MAK_MAKE_REFS:
				return basicSetMakMakeRefs(null, msgs);
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
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__ROOT_DIRECTORY:
				return getRootDirectory();
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS:
				return getBuildConfigurations();
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__INF_COMPONENTS:
				return getInfComponents();
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__MAK_MAKE_REFS:
				return getMakMakeRefs();
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__PATH:
				return getPath();
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__PROJECT_NAME:
				return getProjectName();
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__SDK_ID:
				return getSdkId();
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
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__ROOT_DIRECTORY:
				setRootDirectory((RootDirectoryType)newValue);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS:
				setBuildConfigurations((BuildConfigurationsType)newValue);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__INF_COMPONENTS:
				setInfComponents((InfComponentsType)newValue);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__MAK_MAKE_REFS:
				setMakMakeRefs((MakMakeRefsType)newValue);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__PATH:
				setPath((String)newValue);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__PROJECT_NAME:
				setProjectName((String)newValue);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__SDK_ID:
				setSdkId((String)newValue);
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
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__ROOT_DIRECTORY:
				setRootDirectory((RootDirectoryType)null);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS:
				setBuildConfigurations((BuildConfigurationsType)null);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__INF_COMPONENTS:
				setInfComponents((InfComponentsType)null);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__MAK_MAKE_REFS:
				setMakMakeRefs((MakMakeRefsType)null);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__PROJECT_NAME:
				setProjectName(PROJECT_NAME_EDEFAULT);
				return;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__SDK_ID:
				setSdkId(SDK_ID_EDEFAULT);
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
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__ROOT_DIRECTORY:
				return rootDirectory != null;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS:
				return buildConfigurations != null;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__INF_COMPONENTS:
				return infComponents != null;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__MAK_MAKE_REFS:
				return makMakeRefs != null;
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__PROJECT_NAME:
				return PROJECT_NAME_EDEFAULT == null ? projectName != null : !PROJECT_NAME_EDEFAULT.equals(projectName);
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE__SDK_ID:
				return SDK_ID_EDEFAULT == null ? sdkId != null : !SDK_ID_EDEFAULT.equals(sdkId);
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
		result.append(" (path: ");
		result.append(path);
		result.append(", projectName: ");
		result.append(projectName);
		result.append(", sdkId: ");
		result.append(sdkId);
		result.append(')');
		return result.toString();
	}

} //BldInfFileTypeImpl
