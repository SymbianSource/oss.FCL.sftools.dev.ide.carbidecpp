/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.UseTemplateGroupType;
import com.nokia.sdt.emf.component.UseTemplateType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Use Template Group Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.UseTemplateGroupTypeImpl#getUseTemplate <em>Use Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.UseTemplateGroupTypeImpl#getIds <em>Ids</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UseTemplateGroupTypeImpl extends EObjectImpl implements UseTemplateGroupType {
	/**
	 * The cached value of the '{@link #getUseTemplate() <em>Use Template</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getUseTemplate()
	 * @generated
	 * @ordered
	 */
    protected EList useTemplate;

	/**
	 * The default value of the '{@link #getIds() <em>Ids</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getIds()
	 * @generated
	 * @ordered
	 */
    protected static final String IDS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIds() <em>Ids</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getIds()
	 * @generated
	 * @ordered
	 */
    protected String ids = IDS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected UseTemplateGroupTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.USE_TEMPLATE_GROUP_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getUseTemplate() {
		if (useTemplate == null) {
			useTemplate = new EObjectContainmentEList(UseTemplateType.class, this, ComponentPackage.USE_TEMPLATE_GROUP_TYPE__USE_TEMPLATE);
		}
		return useTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getIds() {
		return ids;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setIds(String newIds) {
		String oldIds = ids;
		ids = newIds;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.USE_TEMPLATE_GROUP_TYPE__IDS, oldIds, ids));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE__USE_TEMPLATE:
				return ((InternalEList)getUseTemplate()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE__USE_TEMPLATE:
				return getUseTemplate();
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE__IDS:
				return getIds();
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
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE__USE_TEMPLATE:
				getUseTemplate().clear();
				getUseTemplate().addAll((Collection)newValue);
				return;
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE__IDS:
				setIds((String)newValue);
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
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE__USE_TEMPLATE:
				getUseTemplate().clear();
				return;
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE__IDS:
				setIds(IDS_EDEFAULT);
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
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE__USE_TEMPLATE:
				return useTemplate != null && !useTemplate.isEmpty();
			case ComponentPackage.USE_TEMPLATE_GROUP_TYPE__IDS:
				return IDS_EDEFAULT == null ? ids != null : !IDS_EDEFAULT.equals(ids);
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
		result.append(" (ids: ");
		result.append(ids);
		result.append(')');
		return result.toString();
	}

} //UseTemplateGroupTypeImpl
