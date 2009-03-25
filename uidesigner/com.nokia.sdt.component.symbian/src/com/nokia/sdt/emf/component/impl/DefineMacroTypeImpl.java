/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.DefineMacroType;
import com.nokia.sdt.emf.component.ImportArgumentsType;
import com.nokia.sdt.emf.component.MacroArgumentType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Define Macro Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl#getImportArguments <em>Import Arguments</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl#getMacroArgument <em>Macro Argument</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl#getInline <em>Inline</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl#getDefineLocation <em>Define Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl#getExpandMacro <em>Expand Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl#getHelp <em>Help</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DefineMacroTypeImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DefineMacroTypeImpl extends EObjectImpl implements DefineMacroType {
	/**
	 * The cached value of the '{@link #getImportArguments() <em>Import Arguments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportArguments()
	 * @generated
	 * @ordered
	 */
	protected EList importArguments;

	/**
	 * The cached value of the '{@link #getMacroArgument() <em>Macro Argument</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMacroArgument()
	 * @generated
	 * @ordered
	 */
	protected EList macroArgument;

	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

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
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DefineMacroTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.DEFINE_MACRO_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getImportArguments() {
		if (importArguments == null) {
			importArguments = new EObjectContainmentEList(ImportArgumentsType.class, this, ComponentPackage.DEFINE_MACRO_TYPE__IMPORT_ARGUMENTS);
		}
		return importArguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMacroArgument() {
		if (macroArgument == null) {
			macroArgument = new EObjectContainmentEList(MacroArgumentType.class, this, ComponentPackage.DEFINE_MACRO_TYPE__MACRO_ARGUMENT);
		}
		return macroArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ComponentPackage.DEFINE_MACRO_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTemplate() {
		return getGroup().list(ComponentPackage.Literals.DEFINE_MACRO_TYPE__TEMPLATE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInline() {
		return getGroup().list(ComponentPackage.Literals.DEFINE_MACRO_TYPE__INLINE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDefineLocation() {
		return getGroup().list(ComponentPackage.Literals.DEFINE_MACRO_TYPE__DEFINE_LOCATION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getExpandMacro() {
		return getGroup().list(ComponentPackage.Literals.DEFINE_MACRO_TYPE__EXPAND_MACRO);
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_MACRO_TYPE__HELP, oldHelp, help));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DEFINE_MACRO_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.DEFINE_MACRO_TYPE__IMPORT_ARGUMENTS:
				return ((InternalEList)getImportArguments()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DEFINE_MACRO_TYPE__MACRO_ARGUMENT:
				return ((InternalEList)getMacroArgument()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DEFINE_MACRO_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DEFINE_MACRO_TYPE__TEMPLATE:
				return ((InternalEList)getTemplate()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DEFINE_MACRO_TYPE__INLINE:
				return ((InternalEList)getInline()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DEFINE_MACRO_TYPE__DEFINE_LOCATION:
				return ((InternalEList)getDefineLocation()).basicRemove(otherEnd, msgs);
			case ComponentPackage.DEFINE_MACRO_TYPE__EXPAND_MACRO:
				return ((InternalEList)getExpandMacro()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.DEFINE_MACRO_TYPE__IMPORT_ARGUMENTS:
				return getImportArguments();
			case ComponentPackage.DEFINE_MACRO_TYPE__MACRO_ARGUMENT:
				return getMacroArgument();
			case ComponentPackage.DEFINE_MACRO_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ComponentPackage.DEFINE_MACRO_TYPE__TEMPLATE:
				return getTemplate();
			case ComponentPackage.DEFINE_MACRO_TYPE__INLINE:
				return getInline();
			case ComponentPackage.DEFINE_MACRO_TYPE__DEFINE_LOCATION:
				return getDefineLocation();
			case ComponentPackage.DEFINE_MACRO_TYPE__EXPAND_MACRO:
				return getExpandMacro();
			case ComponentPackage.DEFINE_MACRO_TYPE__HELP:
				return getHelp();
			case ComponentPackage.DEFINE_MACRO_TYPE__ID:
				return getId();
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
			case ComponentPackage.DEFINE_MACRO_TYPE__IMPORT_ARGUMENTS:
				getImportArguments().clear();
				getImportArguments().addAll((Collection)newValue);
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__MACRO_ARGUMENT:
				getMacroArgument().clear();
				getMacroArgument().addAll((Collection)newValue);
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__TEMPLATE:
				getTemplate().clear();
				getTemplate().addAll((Collection)newValue);
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__INLINE:
				getInline().clear();
				getInline().addAll((Collection)newValue);
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__DEFINE_LOCATION:
				getDefineLocation().clear();
				getDefineLocation().addAll((Collection)newValue);
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__EXPAND_MACRO:
				getExpandMacro().clear();
				getExpandMacro().addAll((Collection)newValue);
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__HELP:
				setHelp((String)newValue);
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__ID:
				setId((String)newValue);
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
			case ComponentPackage.DEFINE_MACRO_TYPE__IMPORT_ARGUMENTS:
				getImportArguments().clear();
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__MACRO_ARGUMENT:
				getMacroArgument().clear();
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__GROUP:
				getGroup().clear();
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__TEMPLATE:
				getTemplate().clear();
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__INLINE:
				getInline().clear();
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__DEFINE_LOCATION:
				getDefineLocation().clear();
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__EXPAND_MACRO:
				getExpandMacro().clear();
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__HELP:
				setHelp(HELP_EDEFAULT);
				return;
			case ComponentPackage.DEFINE_MACRO_TYPE__ID:
				setId(ID_EDEFAULT);
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
			case ComponentPackage.DEFINE_MACRO_TYPE__IMPORT_ARGUMENTS:
				return importArguments != null && !importArguments.isEmpty();
			case ComponentPackage.DEFINE_MACRO_TYPE__MACRO_ARGUMENT:
				return macroArgument != null && !macroArgument.isEmpty();
			case ComponentPackage.DEFINE_MACRO_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ComponentPackage.DEFINE_MACRO_TYPE__TEMPLATE:
				return !getTemplate().isEmpty();
			case ComponentPackage.DEFINE_MACRO_TYPE__INLINE:
				return !getInline().isEmpty();
			case ComponentPackage.DEFINE_MACRO_TYPE__DEFINE_LOCATION:
				return !getDefineLocation().isEmpty();
			case ComponentPackage.DEFINE_MACRO_TYPE__EXPAND_MACRO:
				return !getExpandMacro().isEmpty();
			case ComponentPackage.DEFINE_MACRO_TYPE__HELP:
				return HELP_EDEFAULT == null ? help != null : !HELP_EDEFAULT.equals(help);
			case ComponentPackage.DEFINE_MACRO_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
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
		result.append(" (group: ");
		result.append(group);
		result.append(", help: ");
		result.append(help);
		result.append(", id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //DefineMacroTypeImpl
