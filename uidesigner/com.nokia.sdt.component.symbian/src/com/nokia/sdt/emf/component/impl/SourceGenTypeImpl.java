/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.SourceGenType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source Gen Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getDefineLocation <em>Define Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getTemplateGroup <em>Template Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getUseTemplate <em>Use Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getUseTemplateGroup <em>Use Template Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getInline <em>Inline</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getScript <em>Script</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getDefineMacro <em>Define Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getExpandMacro <em>Expand Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#isDebug <em>Debug</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceGenTypeImpl#getForms <em>Forms</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SourceGenTypeImpl extends EObjectImpl implements SourceGenType {
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
	 * The default value of the '{@link #isDebug() <em>Debug</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isDebug()
	 * @generated
	 * @ordered
	 */
    protected static final boolean DEBUG_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDebug() <em>Debug</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isDebug()
	 * @generated
	 * @ordered
	 */
    protected boolean debug = DEBUG_EDEFAULT;

	/**
	 * This is true if the Debug attribute has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean debugESet;

	/**
	 * The default value of the '{@link #getForms() <em>Forms</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getForms()
	 * @generated
	 * @ordered
	 */
    protected static final String FORMS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getForms() <em>Forms</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getForms()
	 * @generated
	 * @ordered
	 */
    protected String forms = FORMS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected SourceGenTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.SOURCE_GEN_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getDefineLocation() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_GEN_TYPE__DEFINE_LOCATION);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ComponentPackage.SOURCE_GEN_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getTemplate() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_GEN_TYPE__TEMPLATE);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getTemplateGroup() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_GEN_TYPE__TEMPLATE_GROUP);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getUseTemplate() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_GEN_TYPE__USE_TEMPLATE);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getUseTemplateGroup() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_GEN_TYPE__USE_TEMPLATE_GROUP);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getInline() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_GEN_TYPE__INLINE);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getScript() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_GEN_TYPE__SCRIPT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDefineMacro() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_GEN_TYPE__DEFINE_MACRO);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getExpandMacro() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_GEN_TYPE__EXPAND_MACRO);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isDebug() {
		return debug;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDebug(boolean newDebug) {
		boolean oldDebug = debug;
		debug = newDebug;
		boolean oldDebugESet = debugESet;
		debugESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SOURCE_GEN_TYPE__DEBUG, oldDebug, debug, !oldDebugESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetDebug() {
		boolean oldDebug = debug;
		boolean oldDebugESet = debugESet;
		debug = DEBUG_EDEFAULT;
		debugESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.SOURCE_GEN_TYPE__DEBUG, oldDebug, DEBUG_EDEFAULT, oldDebugESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetDebug() {
		return debugESet;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getForms() {
		return forms;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setForms(String newForms) {
		String oldForms = forms;
		forms = newForms;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SOURCE_GEN_TYPE__FORMS, oldForms, forms));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.SOURCE_GEN_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_GEN_TYPE__DEFINE_LOCATION:
				return ((InternalEList)getDefineLocation()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_GEN_TYPE__TEMPLATE:
				return ((InternalEList)getTemplate()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_GEN_TYPE__TEMPLATE_GROUP:
				return ((InternalEList)getTemplateGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_GEN_TYPE__USE_TEMPLATE:
				return ((InternalEList)getUseTemplate()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_GEN_TYPE__USE_TEMPLATE_GROUP:
				return ((InternalEList)getUseTemplateGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_GEN_TYPE__INLINE:
				return ((InternalEList)getInline()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_GEN_TYPE__SCRIPT:
				return ((InternalEList)getScript()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_GEN_TYPE__DEFINE_MACRO:
				return ((InternalEList)getDefineMacro()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_GEN_TYPE__EXPAND_MACRO:
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
			case ComponentPackage.SOURCE_GEN_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ComponentPackage.SOURCE_GEN_TYPE__DEFINE_LOCATION:
				return getDefineLocation();
			case ComponentPackage.SOURCE_GEN_TYPE__TEMPLATE:
				return getTemplate();
			case ComponentPackage.SOURCE_GEN_TYPE__TEMPLATE_GROUP:
				return getTemplateGroup();
			case ComponentPackage.SOURCE_GEN_TYPE__USE_TEMPLATE:
				return getUseTemplate();
			case ComponentPackage.SOURCE_GEN_TYPE__USE_TEMPLATE_GROUP:
				return getUseTemplateGroup();
			case ComponentPackage.SOURCE_GEN_TYPE__INLINE:
				return getInline();
			case ComponentPackage.SOURCE_GEN_TYPE__SCRIPT:
				return getScript();
			case ComponentPackage.SOURCE_GEN_TYPE__DEFINE_MACRO:
				return getDefineMacro();
			case ComponentPackage.SOURCE_GEN_TYPE__EXPAND_MACRO:
				return getExpandMacro();
			case ComponentPackage.SOURCE_GEN_TYPE__DEBUG:
				return isDebug() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.SOURCE_GEN_TYPE__FORMS:
				return getForms();
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
			case ComponentPackage.SOURCE_GEN_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__DEFINE_LOCATION:
				getDefineLocation().clear();
				getDefineLocation().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__TEMPLATE:
				getTemplate().clear();
				getTemplate().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__TEMPLATE_GROUP:
				getTemplateGroup().clear();
				getTemplateGroup().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__USE_TEMPLATE:
				getUseTemplate().clear();
				getUseTemplate().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__USE_TEMPLATE_GROUP:
				getUseTemplateGroup().clear();
				getUseTemplateGroup().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__INLINE:
				getInline().clear();
				getInline().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__SCRIPT:
				getScript().clear();
				getScript().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__DEFINE_MACRO:
				getDefineMacro().clear();
				getDefineMacro().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__EXPAND_MACRO:
				getExpandMacro().clear();
				getExpandMacro().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__DEBUG:
				setDebug(((Boolean)newValue).booleanValue());
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__FORMS:
				setForms((String)newValue);
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
			case ComponentPackage.SOURCE_GEN_TYPE__GROUP:
				getGroup().clear();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__DEFINE_LOCATION:
				getDefineLocation().clear();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__TEMPLATE:
				getTemplate().clear();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__TEMPLATE_GROUP:
				getTemplateGroup().clear();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__USE_TEMPLATE:
				getUseTemplate().clear();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__USE_TEMPLATE_GROUP:
				getUseTemplateGroup().clear();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__INLINE:
				getInline().clear();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__SCRIPT:
				getScript().clear();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__DEFINE_MACRO:
				getDefineMacro().clear();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__EXPAND_MACRO:
				getExpandMacro().clear();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__DEBUG:
				unsetDebug();
				return;
			case ComponentPackage.SOURCE_GEN_TYPE__FORMS:
				setForms(FORMS_EDEFAULT);
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
			case ComponentPackage.SOURCE_GEN_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ComponentPackage.SOURCE_GEN_TYPE__DEFINE_LOCATION:
				return !getDefineLocation().isEmpty();
			case ComponentPackage.SOURCE_GEN_TYPE__TEMPLATE:
				return !getTemplate().isEmpty();
			case ComponentPackage.SOURCE_GEN_TYPE__TEMPLATE_GROUP:
				return !getTemplateGroup().isEmpty();
			case ComponentPackage.SOURCE_GEN_TYPE__USE_TEMPLATE:
				return !getUseTemplate().isEmpty();
			case ComponentPackage.SOURCE_GEN_TYPE__USE_TEMPLATE_GROUP:
				return !getUseTemplateGroup().isEmpty();
			case ComponentPackage.SOURCE_GEN_TYPE__INLINE:
				return !getInline().isEmpty();
			case ComponentPackage.SOURCE_GEN_TYPE__SCRIPT:
				return !getScript().isEmpty();
			case ComponentPackage.SOURCE_GEN_TYPE__DEFINE_MACRO:
				return !getDefineMacro().isEmpty();
			case ComponentPackage.SOURCE_GEN_TYPE__EXPAND_MACRO:
				return !getExpandMacro().isEmpty();
			case ComponentPackage.SOURCE_GEN_TYPE__DEBUG:
				return isSetDebug();
			case ComponentPackage.SOURCE_GEN_TYPE__FORMS:
				return FORMS_EDEFAULT == null ? forms != null : !FORMS_EDEFAULT.equals(forms);
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
		result.append(", debug: ");
		if (debugESet) result.append(debug); else result.append("<unset>");
		result.append(", forms: ");
		result.append(forms);
		result.append(')');
		return result.toString();
	}

} //SourceGenTypeImpl
