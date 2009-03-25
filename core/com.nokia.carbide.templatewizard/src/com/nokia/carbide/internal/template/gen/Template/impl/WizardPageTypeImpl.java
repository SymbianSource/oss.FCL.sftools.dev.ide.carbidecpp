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

package com.nokia.carbide.internal.template.gen.Template.impl;

import com.nokia.carbide.internal.template.gen.Template.TemplatePackage;
import com.nokia.carbide.internal.template.gen.Template.WizardPageType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.*;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Wizard Page Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl#getUidField <em>Uid Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl#getTextField <em>Text Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl#getFilenameField <em>Filename Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl#getHelp <em>Help</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WizardPageTypeImpl extends EObjectImpl implements WizardPageType {
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
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

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
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WizardPageTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return TemplatePackage.Literals.WIZARD_PAGE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, TemplatePackage.WIZARD_PAGE_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getUidField() {
		return getGroup().list(TemplatePackage.Literals.WIZARD_PAGE_TYPE__UID_FIELD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTextField() {
		return getGroup().list(TemplatePackage.Literals.WIZARD_PAGE_TYPE__TEXT_FIELD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getFilenameField() {
		return getGroup().list(TemplatePackage.Literals.WIZARD_PAGE_TYPE__FILENAME_FIELD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatePackage.WIZARD_PAGE_TYPE__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatePackage.WIZARD_PAGE_TYPE__HELP, oldHelp, help));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatePackage.WIZARD_PAGE_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatePackage.WIZARD_PAGE_TYPE__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TemplatePackage.WIZARD_PAGE_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case TemplatePackage.WIZARD_PAGE_TYPE__UID_FIELD:
				return ((InternalEList)getUidField()).basicRemove(otherEnd, msgs);
			case TemplatePackage.WIZARD_PAGE_TYPE__TEXT_FIELD:
				return ((InternalEList)getTextField()).basicRemove(otherEnd, msgs);
			case TemplatePackage.WIZARD_PAGE_TYPE__FILENAME_FIELD:
				return ((InternalEList)getFilenameField()).basicRemove(otherEnd, msgs);
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
			case TemplatePackage.WIZARD_PAGE_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case TemplatePackage.WIZARD_PAGE_TYPE__UID_FIELD:
				return getUidField();
			case TemplatePackage.WIZARD_PAGE_TYPE__TEXT_FIELD:
				return getTextField();
			case TemplatePackage.WIZARD_PAGE_TYPE__FILENAME_FIELD:
				return getFilenameField();
			case TemplatePackage.WIZARD_PAGE_TYPE__DESCRIPTION:
				return getDescription();
			case TemplatePackage.WIZARD_PAGE_TYPE__HELP:
				return getHelp();
			case TemplatePackage.WIZARD_PAGE_TYPE__ID:
				return getId();
			case TemplatePackage.WIZARD_PAGE_TYPE__LABEL:
				return getLabel();
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
			case TemplatePackage.WIZARD_PAGE_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__UID_FIELD:
				getUidField().clear();
				getUidField().addAll((Collection)newValue);
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__TEXT_FIELD:
				getTextField().clear();
				getTextField().addAll((Collection)newValue);
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__FILENAME_FIELD:
				getFilenameField().clear();
				getFilenameField().addAll((Collection)newValue);
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__HELP:
				setHelp((String)newValue);
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__ID:
				setId((String)newValue);
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__LABEL:
				setLabel((String)newValue);
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
			case TemplatePackage.WIZARD_PAGE_TYPE__GROUP:
				getGroup().clear();
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__UID_FIELD:
				getUidField().clear();
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__TEXT_FIELD:
				getTextField().clear();
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__FILENAME_FIELD:
				getFilenameField().clear();
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__HELP:
				setHelp(HELP_EDEFAULT);
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case TemplatePackage.WIZARD_PAGE_TYPE__LABEL:
				setLabel(LABEL_EDEFAULT);
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
			case TemplatePackage.WIZARD_PAGE_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case TemplatePackage.WIZARD_PAGE_TYPE__UID_FIELD:
				return !getUidField().isEmpty();
			case TemplatePackage.WIZARD_PAGE_TYPE__TEXT_FIELD:
				return !getTextField().isEmpty();
			case TemplatePackage.WIZARD_PAGE_TYPE__FILENAME_FIELD:
				return !getFilenameField().isEmpty();
			case TemplatePackage.WIZARD_PAGE_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TemplatePackage.WIZARD_PAGE_TYPE__HELP:
				return HELP_EDEFAULT == null ? help != null : !HELP_EDEFAULT.equals(help);
			case TemplatePackage.WIZARD_PAGE_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case TemplatePackage.WIZARD_PAGE_TYPE__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
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
		result.append(", description: ");
		result.append(description);
		result.append(", help: ");
		result.append(help);
		result.append(", id: ");
		result.append(id);
		result.append(", label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} //WizardPageTypeImpl