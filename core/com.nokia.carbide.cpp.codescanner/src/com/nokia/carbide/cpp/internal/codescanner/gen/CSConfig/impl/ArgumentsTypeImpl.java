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

package com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl;

import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Arguments Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ArgumentsTypeImpl#getInput <em>Input</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ArgumentsTypeImpl#getLxr <em>Lxr</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ArgumentsTypeImpl#getLxrversion <em>Lxrversion</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ArgumentsTypeImpl#getOutputformat <em>Outputformat</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ArgumentsTypeImpl#getTimestampedoutput <em>Timestampedoutput</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArgumentsTypeImpl extends EObjectImpl implements ArgumentsType {
	/**
	 * The cached value of the '{@link #getInput() <em>Input</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInput()
	 * @generated
	 * @ordered
	 */
	protected EList<String> input;

	/**
	 * The default value of the '{@link #getLxr() <em>Lxr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLxr()
	 * @generated
	 * @ordered
	 */
	protected static final String LXR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLxr() <em>Lxr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLxr()
	 * @generated
	 * @ordered
	 */
	protected String lxr = LXR_EDEFAULT;

	/**
	 * The default value of the '{@link #getLxrversion() <em>Lxrversion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLxrversion()
	 * @generated
	 * @ordered
	 */
	protected static final String LXRVERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLxrversion() <em>Lxrversion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLxrversion()
	 * @generated
	 * @ordered
	 */
	protected String lxrversion = LXRVERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getOutputformat() <em>Outputformat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputformat()
	 * @generated
	 * @ordered
	 */
	protected static final String OUTPUTFORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOutputformat() <em>Outputformat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputformat()
	 * @generated
	 * @ordered
	 */
	protected String outputformat = OUTPUTFORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimestampedoutput() <em>Timestampedoutput</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestampedoutput()
	 * @generated
	 * @ordered
	 */
	protected static final String TIMESTAMPEDOUTPUT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimestampedoutput() <em>Timestampedoutput</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestampedoutput()
	 * @generated
	 * @ordered
	 */
	protected String timestampedoutput = TIMESTAMPEDOUTPUT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArgumentsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CSConfigPackage.eINSTANCE.getArgumentsType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getInput() {
		if (input == null) {
			input = new EDataTypeEList<String>(String.class, this, CSConfigPackage.ARGUMENTS_TYPE__INPUT);
		}
		return input;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLxr() {
		return lxr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLxr(String newLxr) {
		String oldLxr = lxr;
		lxr = newLxr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.ARGUMENTS_TYPE__LXR, oldLxr, lxr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLxrversion() {
		return lxrversion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLxrversion(String newLxrversion) {
		String oldLxrversion = lxrversion;
		lxrversion = newLxrversion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.ARGUMENTS_TYPE__LXRVERSION, oldLxrversion, lxrversion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOutputformat() {
		return outputformat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputformat(String newOutputformat) {
		String oldOutputformat = outputformat;
		outputformat = newOutputformat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.ARGUMENTS_TYPE__OUTPUTFORMAT, oldOutputformat, outputformat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTimestampedoutput() {
		return timestampedoutput;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimestampedoutput(String newTimestampedoutput) {
		String oldTimestampedoutput = timestampedoutput;
		timestampedoutput = newTimestampedoutput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.ARGUMENTS_TYPE__TIMESTAMPEDOUTPUT, oldTimestampedoutput, timestampedoutput));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CSConfigPackage.ARGUMENTS_TYPE__INPUT:
				return getInput();
			case CSConfigPackage.ARGUMENTS_TYPE__LXR:
				return getLxr();
			case CSConfigPackage.ARGUMENTS_TYPE__LXRVERSION:
				return getLxrversion();
			case CSConfigPackage.ARGUMENTS_TYPE__OUTPUTFORMAT:
				return getOutputformat();
			case CSConfigPackage.ARGUMENTS_TYPE__TIMESTAMPEDOUTPUT:
				return getTimestampedoutput();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
		@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CSConfigPackage.ARGUMENTS_TYPE__INPUT:
				getInput().clear();
				getInput().addAll((Collection<? extends String>)newValue);
				return;
			case CSConfigPackage.ARGUMENTS_TYPE__LXR:
				setLxr((String)newValue);
				return;
			case CSConfigPackage.ARGUMENTS_TYPE__LXRVERSION:
				setLxrversion((String)newValue);
				return;
			case CSConfigPackage.ARGUMENTS_TYPE__OUTPUTFORMAT:
				setOutputformat((String)newValue);
				return;
			case CSConfigPackage.ARGUMENTS_TYPE__TIMESTAMPEDOUTPUT:
				setTimestampedoutput((String)newValue);
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
			case CSConfigPackage.ARGUMENTS_TYPE__INPUT:
				getInput().clear();
				return;
			case CSConfigPackage.ARGUMENTS_TYPE__LXR:
				setLxr(LXR_EDEFAULT);
				return;
			case CSConfigPackage.ARGUMENTS_TYPE__LXRVERSION:
				setLxrversion(LXRVERSION_EDEFAULT);
				return;
			case CSConfigPackage.ARGUMENTS_TYPE__OUTPUTFORMAT:
				setOutputformat(OUTPUTFORMAT_EDEFAULT);
				return;
			case CSConfigPackage.ARGUMENTS_TYPE__TIMESTAMPEDOUTPUT:
				setTimestampedoutput(TIMESTAMPEDOUTPUT_EDEFAULT);
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
			case CSConfigPackage.ARGUMENTS_TYPE__INPUT:
				return input != null && !input.isEmpty();
			case CSConfigPackage.ARGUMENTS_TYPE__LXR:
				return LXR_EDEFAULT == null ? lxr != null : !LXR_EDEFAULT.equals(lxr);
			case CSConfigPackage.ARGUMENTS_TYPE__LXRVERSION:
				return LXRVERSION_EDEFAULT == null ? lxrversion != null : !LXRVERSION_EDEFAULT.equals(lxrversion);
			case CSConfigPackage.ARGUMENTS_TYPE__OUTPUTFORMAT:
				return OUTPUTFORMAT_EDEFAULT == null ? outputformat != null : !OUTPUTFORMAT_EDEFAULT.equals(outputformat);
			case CSConfigPackage.ARGUMENTS_TYPE__TIMESTAMPEDOUTPUT:
				return TIMESTAMPEDOUTPUT_EDEFAULT == null ? timestampedoutput != null : !TIMESTAMPEDOUTPUT_EDEFAULT.equals(timestampedoutput);
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
		result.append(" (input: ");
		result.append(input);
		result.append(", lxr: ");
		result.append(lxr);
		result.append(", lxrversion: ");
		result.append(lxrversion);
		result.append(", outputformat: ");
		result.append(outputformat);
		result.append(", timestampedoutput: ");
		result.append(timestampedoutput);
		result.append(')');
		return result.toString();
	}

} //ArgumentsTypeImpl
