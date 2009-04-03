/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.ExpandArgumentType;
import com.nokia.sdt.emf.component.ExpandMacroType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expand Macro Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ExpandMacroTypeImpl#getExpandArgument <em>Expand Argument</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ExpandMacroTypeImpl#getDontPassArguments <em>Dont Pass Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ExpandMacroTypeImpl#getHelp <em>Help</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ExpandMacroTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ExpandMacroTypeImpl#getPassArguments <em>Pass Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ExpandMacroTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExpandMacroTypeImpl extends ConditionalSourceGenImpl implements ExpandMacroType {
	/**
	 * The cached value of the '{@link #getExpandArgument() <em>Expand Argument</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpandArgument()
	 * @generated
	 * @ordered
	 */
	protected EList expandArgument;

	/**
	 * The default value of the '{@link #getDontPassArguments() <em>Dont Pass Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDontPassArguments()
	 * @generated
	 * @ordered
	 */
	protected static final List DONT_PASS_ARGUMENTS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDontPassArguments() <em>Dont Pass Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDontPassArguments()
	 * @generated
	 * @ordered
	 */
	protected List dontPassArguments = DONT_PASS_ARGUMENTS_EDEFAULT;

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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPassArguments() <em>Pass Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassArguments()
	 * @generated
	 * @ordered
	 */
	protected static final List PASS_ARGUMENTS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassArguments() <em>Pass Arguments</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassArguments()
	 * @generated
	 * @ordered
	 */
	protected List passArguments = PASS_ARGUMENTS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnyAttribute()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap anyAttribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExpandMacroTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.EXPAND_MACRO_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getExpandArgument() {
		if (expandArgument == null) {
			expandArgument = new EObjectContainmentEList(ExpandArgumentType.class, this, ComponentPackage.EXPAND_MACRO_TYPE__EXPAND_ARGUMENT);
		}
		return expandArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getDontPassArguments() {
		return dontPassArguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDontPassArguments(List newDontPassArguments) {
		List oldDontPassArguments = dontPassArguments;
		dontPassArguments = newDontPassArguments;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EXPAND_MACRO_TYPE__DONT_PASS_ARGUMENTS, oldDontPassArguments, dontPassArguments));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EXPAND_MACRO_TYPE__HELP, oldHelp, help));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EXPAND_MACRO_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getPassArguments() {
		return passArguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassArguments(List newPassArguments) {
		List oldPassArguments = passArguments;
		passArguments = newPassArguments;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EXPAND_MACRO_TYPE__PASS_ARGUMENTS, oldPassArguments, passArguments));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, ComponentPackage.EXPAND_MACRO_TYPE__ANY_ATTRIBUTE);
		}
		return anyAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.EXPAND_MACRO_TYPE__EXPAND_ARGUMENT:
				return ((InternalEList)getExpandArgument()).basicRemove(otherEnd, msgs);
			case ComponentPackage.EXPAND_MACRO_TYPE__ANY_ATTRIBUTE:
				return ((InternalEList)getAnyAttribute()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.EXPAND_MACRO_TYPE__EXPAND_ARGUMENT:
				return getExpandArgument();
			case ComponentPackage.EXPAND_MACRO_TYPE__DONT_PASS_ARGUMENTS:
				return getDontPassArguments();
			case ComponentPackage.EXPAND_MACRO_TYPE__HELP:
				return getHelp();
			case ComponentPackage.EXPAND_MACRO_TYPE__NAME:
				return getName();
			case ComponentPackage.EXPAND_MACRO_TYPE__PASS_ARGUMENTS:
				return getPassArguments();
			case ComponentPackage.EXPAND_MACRO_TYPE__ANY_ATTRIBUTE:
				if (coreType) return getAnyAttribute();
				return ((FeatureMap.Internal)getAnyAttribute()).getWrapper();
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
			case ComponentPackage.EXPAND_MACRO_TYPE__EXPAND_ARGUMENT:
				getExpandArgument().clear();
				getExpandArgument().addAll((Collection)newValue);
				return;
			case ComponentPackage.EXPAND_MACRO_TYPE__DONT_PASS_ARGUMENTS:
				setDontPassArguments((List)newValue);
				return;
			case ComponentPackage.EXPAND_MACRO_TYPE__HELP:
				setHelp((String)newValue);
				return;
			case ComponentPackage.EXPAND_MACRO_TYPE__NAME:
				setName((String)newValue);
				return;
			case ComponentPackage.EXPAND_MACRO_TYPE__PASS_ARGUMENTS:
				setPassArguments((List)newValue);
				return;
			case ComponentPackage.EXPAND_MACRO_TYPE__ANY_ATTRIBUTE:
				((FeatureMap.Internal)getAnyAttribute()).set(newValue);
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
			case ComponentPackage.EXPAND_MACRO_TYPE__EXPAND_ARGUMENT:
				getExpandArgument().clear();
				return;
			case ComponentPackage.EXPAND_MACRO_TYPE__DONT_PASS_ARGUMENTS:
				setDontPassArguments(DONT_PASS_ARGUMENTS_EDEFAULT);
				return;
			case ComponentPackage.EXPAND_MACRO_TYPE__HELP:
				setHelp(HELP_EDEFAULT);
				return;
			case ComponentPackage.EXPAND_MACRO_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ComponentPackage.EXPAND_MACRO_TYPE__PASS_ARGUMENTS:
				setPassArguments(PASS_ARGUMENTS_EDEFAULT);
				return;
			case ComponentPackage.EXPAND_MACRO_TYPE__ANY_ATTRIBUTE:
				getAnyAttribute().clear();
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
			case ComponentPackage.EXPAND_MACRO_TYPE__EXPAND_ARGUMENT:
				return expandArgument != null && !expandArgument.isEmpty();
			case ComponentPackage.EXPAND_MACRO_TYPE__DONT_PASS_ARGUMENTS:
				return DONT_PASS_ARGUMENTS_EDEFAULT == null ? dontPassArguments != null : !DONT_PASS_ARGUMENTS_EDEFAULT.equals(dontPassArguments);
			case ComponentPackage.EXPAND_MACRO_TYPE__HELP:
				return HELP_EDEFAULT == null ? help != null : !HELP_EDEFAULT.equals(help);
			case ComponentPackage.EXPAND_MACRO_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ComponentPackage.EXPAND_MACRO_TYPE__PASS_ARGUMENTS:
				return PASS_ARGUMENTS_EDEFAULT == null ? passArguments != null : !PASS_ARGUMENTS_EDEFAULT.equals(passArguments);
			case ComponentPackage.EXPAND_MACRO_TYPE__ANY_ATTRIBUTE:
				return anyAttribute != null && !anyAttribute.isEmpty();
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
		result.append(" (dontPassArguments: ");
		result.append(dontPassArguments);
		result.append(", help: ");
		result.append(help);
		result.append(", name: ");
		result.append(name);
		result.append(", passArguments: ");
		result.append(passArguments);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //ExpandMacroTypeImpl
