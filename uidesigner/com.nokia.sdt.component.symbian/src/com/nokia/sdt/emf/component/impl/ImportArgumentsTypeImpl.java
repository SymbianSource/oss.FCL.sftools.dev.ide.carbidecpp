/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.ImportArgumentsType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Import Arguments Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ImportArgumentsTypeImpl#getArguments <em>Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ImportArgumentsTypeImpl#getExceptArguments <em>Except Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ImportArgumentsTypeImpl#getHelp <em>Help</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ImportArgumentsTypeImpl#getMacroName <em>Macro Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ImportArgumentsTypeImpl extends EObjectImpl implements ImportArgumentsType {
	/**
	 * The default value of the '{@link #getArguments() <em>Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected static final List ARGUMENTS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArguments() <em>Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected List arguments = ARGUMENTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getExceptArguments() <em>Except Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExceptArguments()
	 * @generated
	 * @ordered
	 */
	protected static final List EXCEPT_ARGUMENTS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExceptArguments() <em>Except Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExceptArguments()
	 * @generated
	 * @ordered
	 */
	protected List exceptArguments = EXCEPT_ARGUMENTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getHelp() <em>Help</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelp()
	 * @generated
	 * @ordered
	 */
	protected static final String HELP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHelp() <em>Help</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelp()
	 * @generated
	 * @ordered
	 */
	protected String help = HELP_EDEFAULT;

	/**
	 * The default value of the '{@link #getMacroName() <em>Macro Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMacroName()
	 * @generated
	 * @ordered
	 */
	protected static final String MACRO_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMacroName() <em>Macro Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMacroName()
	 * @generated
	 * @ordered
	 */
	protected String macroName = MACRO_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportArgumentsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.IMPORT_ARGUMENTS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getArguments() {
		return arguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArguments(List newArguments) {
		List oldArguments = arguments;
		arguments = newArguments;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.IMPORT_ARGUMENTS_TYPE__ARGUMENTS, oldArguments, arguments));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getExceptArguments() {
		return exceptArguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExceptArguments(List newExceptArguments) {
		List oldExceptArguments = exceptArguments;
		exceptArguments = newExceptArguments;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.IMPORT_ARGUMENTS_TYPE__EXCEPT_ARGUMENTS, oldExceptArguments, exceptArguments));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHelp() {
		return help;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHelp(String newHelp) {
		String oldHelp = help;
		help = newHelp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.IMPORT_ARGUMENTS_TYPE__HELP, oldHelp, help));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMacroName() {
		return macroName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMacroName(String newMacroName) {
		String oldMacroName = macroName;
		macroName = newMacroName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.IMPORT_ARGUMENTS_TYPE__MACRO_NAME, oldMacroName, macroName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__ARGUMENTS:
				return getArguments();
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__EXCEPT_ARGUMENTS:
				return getExceptArguments();
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__HELP:
				return getHelp();
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__MACRO_NAME:
				return getMacroName();
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
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__ARGUMENTS:
				setArguments((List)newValue);
				return;
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__EXCEPT_ARGUMENTS:
				setExceptArguments((List)newValue);
				return;
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__HELP:
				setHelp((String)newValue);
				return;
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__MACRO_NAME:
				setMacroName((String)newValue);
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
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__ARGUMENTS:
				setArguments(ARGUMENTS_EDEFAULT);
				return;
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__EXCEPT_ARGUMENTS:
				setExceptArguments(EXCEPT_ARGUMENTS_EDEFAULT);
				return;
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__HELP:
				setHelp(HELP_EDEFAULT);
				return;
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__MACRO_NAME:
				setMacroName(MACRO_NAME_EDEFAULT);
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
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__ARGUMENTS:
				return ARGUMENTS_EDEFAULT == null ? arguments != null : !ARGUMENTS_EDEFAULT.equals(arguments);
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__EXCEPT_ARGUMENTS:
				return EXCEPT_ARGUMENTS_EDEFAULT == null ? exceptArguments != null : !EXCEPT_ARGUMENTS_EDEFAULT.equals(exceptArguments);
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__HELP:
				return HELP_EDEFAULT == null ? help != null : !HELP_EDEFAULT.equals(help);
			case ComponentPackage.IMPORT_ARGUMENTS_TYPE__MACRO_NAME:
				return MACRO_NAME_EDEFAULT == null ? macroName != null : !MACRO_NAME_EDEFAULT.equals(macroName);
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
		result.append(" (arguments: ");
		result.append(arguments);
		result.append(", exceptArguments: ");
		result.append(exceptArguments);
		result.append(", help: ");
		result.append(help);
		result.append(", macroName: ");
		result.append(macroName);
		result.append(')');
		return result.toString();
	}

} //ImportArgumentsTypeImpl
