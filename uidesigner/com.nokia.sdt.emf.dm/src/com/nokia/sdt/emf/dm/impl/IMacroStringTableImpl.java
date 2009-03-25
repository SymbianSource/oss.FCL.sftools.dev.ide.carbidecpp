/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.IMacroStringTable;

import java.util.Collection;

import com.nokia.sdt.emf.dm.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IMacro String Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IMacroStringTableImpl#getStringMacros <em>String Macros</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IMacroStringTableImpl extends EObjectImpl implements IMacroStringTable {
	/**
	 * The cached value of the '{@link #getStringMacros() <em>String Macros</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringMacros()
	 * @generated
	 * @ordered
	 */
	protected EMap stringMacros = null;
	
	private IStringKeyProvider keyProvider;

	private Set<String> userGeneratedKeys;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IMacroStringTableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.Literals.IMACRO_STRING_TABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap getStringMacros() {
		if (stringMacros == null) {
			stringMacros = new EcoreEMap(DmPackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, DmPackage.IMACRO_STRING_TABLE__STRING_MACROS);
		}
		return stringMacros;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DmPackage.IMACRO_STRING_TABLE__STRING_MACROS:
				return ((InternalEList)getStringMacros()).basicRemove(otherEnd, msgs);
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
			case DmPackage.IMACRO_STRING_TABLE__STRING_MACROS:
				if (coreType) return getStringMacros();
				else return getStringMacros().map();
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
			case DmPackage.IMACRO_STRING_TABLE__STRING_MACROS:
				((EStructuralFeature.Setting)getStringMacros()).set(newValue);
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
			case DmPackage.IMACRO_STRING_TABLE__STRING_MACROS:
				getStringMacros().clear();
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
			case DmPackage.IMACRO_STRING_TABLE__STRING_MACROS:
				return stringMacros != null && !stringMacros.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	public StringValue addMacro(String value) {
		String key = keyProvider.assignMacroStringKey();
		EMap map = getStringMacros();
		Check.checkContract(!map.containsKey(key));
		map.put(key, value);
		return new StringValue(StringValue.MACRO, key);
	}
	
	public StringValue addMacroWithKey(String key, String value) {
		StringValue result = null;
		EMap map = getStringMacros();
		String currValue = (String) map.get(key);
		if (currValue == null) {
			map.put(key, value);
			result = new StringValue(StringValue.MACRO, key);
		}
		else if (currValue.equals(value)) {
			result = new StringValue(StringValue.MACRO, key);
		}
		return result;
	}

	public void updateMacro(StringValue keyValue, String newValue) {
		EMap map = getStringMacros();
		Check.checkState(keyValue.isMacro());
		Check.checkState(map.containsKey(keyValue.getValue()));
		map.put(keyValue.getValue(), newValue);
	}

	public void setKeyProvider(IStringKeyProvider provider) {
		Check.checkArg(provider);
		this.keyProvider = provider;
	}
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.emf.dm.IMacroStringTable#getKeyProvider()
     */
    public IStringKeyProvider getKeyProvider() {
        return keyProvider;
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.emf.dm.IMacroStringTable#getUserGeneratedStringKeys()
	 */
	public Set<String> getUserGeneratedStringKeys() {
		if (userGeneratedKeys == null)
			userGeneratedKeys = new HashSet<String>();
		return userGeneratedKeys;
	}

} //IMacroStringTableImpl
