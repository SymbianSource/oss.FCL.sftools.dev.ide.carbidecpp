/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.TemplateGroupType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Template Group Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getDefineLocation <em>Define Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getInline <em>Inline</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getUseTemplate <em>Use Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getUseTemplateGroup <em>Use Template Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getExpandMacro <em>Expand Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getForm <em>Form</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.TemplateGroupTypeImpl#getPhase <em>Phase</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TemplateGroupTypeImpl extends ConditionalSourceGenImpl implements TemplateGroupType {
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
	 * The default value of the '{@link #getForm() <em>Form</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getForm()
	 * @generated
	 * @ordered
	 */
    protected static final String FORM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getForm() <em>Form</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getForm()
	 * @generated
	 * @ordered
	 */
    protected String form = FORM_EDEFAULT;

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
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
    protected static final String LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
    protected String location = LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
    protected static final String MODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
    protected String mode = MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPhase() <em>Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPhase()
	 * @generated
	 * @ordered
	 */
    protected static final String PHASE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPhase() <em>Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPhase()
	 * @generated
	 * @ordered
	 */
    protected String phase = PHASE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected TemplateGroupTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.TEMPLATE_GROUP_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ComponentPackage.TEMPLATE_GROUP_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDefineLocation() {
		return getGroup().list(ComponentPackage.Literals.TEMPLATE_GROUP_TYPE__DEFINE_LOCATION);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getTemplate() {
		return getGroup().list(ComponentPackage.Literals.TEMPLATE_GROUP_TYPE__TEMPLATE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInline() {
		return getGroup().list(ComponentPackage.Literals.TEMPLATE_GROUP_TYPE__INLINE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getUseTemplate() {
		return getGroup().list(ComponentPackage.Literals.TEMPLATE_GROUP_TYPE__USE_TEMPLATE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getUseTemplateGroup() {
		return getGroup().list(ComponentPackage.Literals.TEMPLATE_GROUP_TYPE__USE_TEMPLATE_GROUP);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getExpandMacro() {
		return getGroup().list(ComponentPackage.Literals.TEMPLATE_GROUP_TYPE__EXPAND_MACRO);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getForm() {
		return form;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setForm(String newForm) {
		String oldForm = form;
		form = newForm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.TEMPLATE_GROUP_TYPE__FORM, oldForm, form));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.TEMPLATE_GROUP_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setLocation(String newLocation) {
		String oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.TEMPLATE_GROUP_TYPE__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getMode() {
		return mode;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMode(String newMode) {
		String oldMode = mode;
		mode = newMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.TEMPLATE_GROUP_TYPE__MODE, oldMode, mode));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getPhase() {
		return phase;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPhase(String newPhase) {
		String oldPhase = phase;
		phase = newPhase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.TEMPLATE_GROUP_TYPE__PHASE, oldPhase, phase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.TEMPLATE_GROUP_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.TEMPLATE_GROUP_TYPE__DEFINE_LOCATION:
				return ((InternalEList)getDefineLocation()).basicRemove(otherEnd, msgs);
			case ComponentPackage.TEMPLATE_GROUP_TYPE__TEMPLATE:
				return ((InternalEList)getTemplate()).basicRemove(otherEnd, msgs);
			case ComponentPackage.TEMPLATE_GROUP_TYPE__INLINE:
				return ((InternalEList)getInline()).basicRemove(otherEnd, msgs);
			case ComponentPackage.TEMPLATE_GROUP_TYPE__USE_TEMPLATE:
				return ((InternalEList)getUseTemplate()).basicRemove(otherEnd, msgs);
			case ComponentPackage.TEMPLATE_GROUP_TYPE__USE_TEMPLATE_GROUP:
				return ((InternalEList)getUseTemplateGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.TEMPLATE_GROUP_TYPE__EXPAND_MACRO:
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
			case ComponentPackage.TEMPLATE_GROUP_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__DEFINE_LOCATION:
				return getDefineLocation();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__TEMPLATE:
				return getTemplate();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__INLINE:
				return getInline();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__USE_TEMPLATE:
				return getUseTemplate();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__USE_TEMPLATE_GROUP:
				return getUseTemplateGroup();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__EXPAND_MACRO:
				return getExpandMacro();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__FORM:
				return getForm();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__ID:
				return getId();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__LOCATION:
				return getLocation();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__MODE:
				return getMode();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__PHASE:
				return getPhase();
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
			case ComponentPackage.TEMPLATE_GROUP_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__DEFINE_LOCATION:
				getDefineLocation().clear();
				getDefineLocation().addAll((Collection)newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__TEMPLATE:
				getTemplate().clear();
				getTemplate().addAll((Collection)newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__INLINE:
				getInline().clear();
				getInline().addAll((Collection)newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__USE_TEMPLATE:
				getUseTemplate().clear();
				getUseTemplate().addAll((Collection)newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__USE_TEMPLATE_GROUP:
				getUseTemplateGroup().clear();
				getUseTemplateGroup().addAll((Collection)newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__EXPAND_MACRO:
				getExpandMacro().clear();
				getExpandMacro().addAll((Collection)newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__FORM:
				setForm((String)newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__ID:
				setId((String)newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__LOCATION:
				setLocation((String)newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__MODE:
				setMode((String)newValue);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__PHASE:
				setPhase((String)newValue);
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
			case ComponentPackage.TEMPLATE_GROUP_TYPE__GROUP:
				getGroup().clear();
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__DEFINE_LOCATION:
				getDefineLocation().clear();
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__TEMPLATE:
				getTemplate().clear();
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__INLINE:
				getInline().clear();
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__USE_TEMPLATE:
				getUseTemplate().clear();
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__USE_TEMPLATE_GROUP:
				getUseTemplateGroup().clear();
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__EXPAND_MACRO:
				getExpandMacro().clear();
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__FORM:
				setForm(FORM_EDEFAULT);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__MODE:
				setMode(MODE_EDEFAULT);
				return;
			case ComponentPackage.TEMPLATE_GROUP_TYPE__PHASE:
				setPhase(PHASE_EDEFAULT);
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
			case ComponentPackage.TEMPLATE_GROUP_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__DEFINE_LOCATION:
				return !getDefineLocation().isEmpty();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__TEMPLATE:
				return !getTemplate().isEmpty();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__INLINE:
				return !getInline().isEmpty();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__USE_TEMPLATE:
				return !getUseTemplate().isEmpty();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__USE_TEMPLATE_GROUP:
				return !getUseTemplateGroup().isEmpty();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__EXPAND_MACRO:
				return !getExpandMacro().isEmpty();
			case ComponentPackage.TEMPLATE_GROUP_TYPE__FORM:
				return FORM_EDEFAULT == null ? form != null : !FORM_EDEFAULT.equals(form);
			case ComponentPackage.TEMPLATE_GROUP_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ComponentPackage.TEMPLATE_GROUP_TYPE__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case ComponentPackage.TEMPLATE_GROUP_TYPE__MODE:
				return MODE_EDEFAULT == null ? mode != null : !MODE_EDEFAULT.equals(mode);
			case ComponentPackage.TEMPLATE_GROUP_TYPE__PHASE:
				return PHASE_EDEFAULT == null ? phase != null : !PHASE_EDEFAULT.equals(phase);
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
		result.append(", form: ");
		result.append(form);
		result.append(", id: ");
		result.append(id);
		result.append(", location: ");
		result.append(location);
		result.append(", mode: ");
		result.append(mode);
		result.append(", phase: ");
		result.append(phase);
		result.append(')');
		return result.toString();
	}

} //TemplateGroupTypeImpl
