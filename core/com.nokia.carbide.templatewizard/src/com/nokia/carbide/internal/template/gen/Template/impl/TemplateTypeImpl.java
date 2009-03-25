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

import com.nokia.carbide.internal.template.gen.Template.*;

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
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl#getWizardPage <em>Wizard Page</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl#getProcess <em>Process</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl#getCopyright <em>Copyright</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl#getHelp <em>Help</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TemplateTypeImpl extends EObjectImpl implements TemplateType {
	/**
	 * The cached value of the '{@link #getWizardPage() <em>Wizard Page</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWizardPage()
	 * @generated
	 * @ordered
	 */
	protected EList wizardPage;

	/**
	 * The cached value of the '{@link #getProcess() <em>Process</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcess()
	 * @generated
	 * @ordered
	 */
	protected EList process;

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
	 * The cached value of the '{@link #getMetadata() <em>Metadata</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetadata()
	 * @generated
	 * @ordered
	 */
	protected EList metadata;

	/**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getCopyright() <em>Copyright</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCopyright()
	 * @generated
	 * @ordered
	 */
	protected static final String COPYRIGHT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCopyright() <em>Copyright</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCopyright()
	 * @generated
	 * @ordered
	 */
	protected String copyright = COPYRIGHT_EDEFAULT;

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
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TemplateTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return TemplatePackage.Literals.TEMPLATE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getWizardPage() {
		if (wizardPage == null) {
			wizardPage = new EObjectContainmentEList(WizardPageType.class, this, TemplatePackage.TEMPLATE_TYPE__WIZARD_PAGE);
		}
		return wizardPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getProcess() {
		if (process == null) {
			process = new EObjectContainmentEList(ProcessType.class, this, TemplatePackage.TEMPLATE_TYPE__PROCESS);
		}
		return process;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatePackage.TEMPLATE_TYPE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMetadata() {
		if (metadata == null) {
			metadata = new EObjectContainmentEList(MetadataType.class, this, TemplatePackage.TEMPLATE_TYPE__METADATA);
		}
		return metadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(String newAuthor) {
		String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatePackage.TEMPLATE_TYPE__AUTHOR, oldAuthor, author));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCopyright(String newCopyright) {
		String oldCopyright = copyright;
		copyright = newCopyright;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatePackage.TEMPLATE_TYPE__COPYRIGHT, oldCopyright, copyright));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatePackage.TEMPLATE_TYPE__HELP, oldHelp, help));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatePackage.TEMPLATE_TYPE__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TemplatePackage.TEMPLATE_TYPE__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TemplatePackage.TEMPLATE_TYPE__WIZARD_PAGE:
				return ((InternalEList)getWizardPage()).basicRemove(otherEnd, msgs);
			case TemplatePackage.TEMPLATE_TYPE__PROCESS:
				return ((InternalEList)getProcess()).basicRemove(otherEnd, msgs);
			case TemplatePackage.TEMPLATE_TYPE__METADATA:
				return ((InternalEList)getMetadata()).basicRemove(otherEnd, msgs);
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
			case TemplatePackage.TEMPLATE_TYPE__WIZARD_PAGE:
				return getWizardPage();
			case TemplatePackage.TEMPLATE_TYPE__PROCESS:
				return getProcess();
			case TemplatePackage.TEMPLATE_TYPE__DESCRIPTION:
				return getDescription();
			case TemplatePackage.TEMPLATE_TYPE__METADATA:
				return getMetadata();
			case TemplatePackage.TEMPLATE_TYPE__AUTHOR:
				return getAuthor();
			case TemplatePackage.TEMPLATE_TYPE__COPYRIGHT:
				return getCopyright();
			case TemplatePackage.TEMPLATE_TYPE__HELP:
				return getHelp();
			case TemplatePackage.TEMPLATE_TYPE__LABEL:
				return getLabel();
			case TemplatePackage.TEMPLATE_TYPE__VERSION:
				return getVersion();
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
			case TemplatePackage.TEMPLATE_TYPE__WIZARD_PAGE:
				getWizardPage().clear();
				getWizardPage().addAll((Collection)newValue);
				return;
			case TemplatePackage.TEMPLATE_TYPE__PROCESS:
				getProcess().clear();
				getProcess().addAll((Collection)newValue);
				return;
			case TemplatePackage.TEMPLATE_TYPE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TemplatePackage.TEMPLATE_TYPE__METADATA:
				getMetadata().clear();
				getMetadata().addAll((Collection)newValue);
				return;
			case TemplatePackage.TEMPLATE_TYPE__AUTHOR:
				setAuthor((String)newValue);
				return;
			case TemplatePackage.TEMPLATE_TYPE__COPYRIGHT:
				setCopyright((String)newValue);
				return;
			case TemplatePackage.TEMPLATE_TYPE__HELP:
				setHelp((String)newValue);
				return;
			case TemplatePackage.TEMPLATE_TYPE__LABEL:
				setLabel((String)newValue);
				return;
			case TemplatePackage.TEMPLATE_TYPE__VERSION:
				setVersion((String)newValue);
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
			case TemplatePackage.TEMPLATE_TYPE__WIZARD_PAGE:
				getWizardPage().clear();
				return;
			case TemplatePackage.TEMPLATE_TYPE__PROCESS:
				getProcess().clear();
				return;
			case TemplatePackage.TEMPLATE_TYPE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TemplatePackage.TEMPLATE_TYPE__METADATA:
				getMetadata().clear();
				return;
			case TemplatePackage.TEMPLATE_TYPE__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case TemplatePackage.TEMPLATE_TYPE__COPYRIGHT:
				setCopyright(COPYRIGHT_EDEFAULT);
				return;
			case TemplatePackage.TEMPLATE_TYPE__HELP:
				setHelp(HELP_EDEFAULT);
				return;
			case TemplatePackage.TEMPLATE_TYPE__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case TemplatePackage.TEMPLATE_TYPE__VERSION:
				setVersion(VERSION_EDEFAULT);
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
			case TemplatePackage.TEMPLATE_TYPE__WIZARD_PAGE:
				return wizardPage != null && !wizardPage.isEmpty();
			case TemplatePackage.TEMPLATE_TYPE__PROCESS:
				return process != null && !process.isEmpty();
			case TemplatePackage.TEMPLATE_TYPE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case TemplatePackage.TEMPLATE_TYPE__METADATA:
				return metadata != null && !metadata.isEmpty();
			case TemplatePackage.TEMPLATE_TYPE__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
			case TemplatePackage.TEMPLATE_TYPE__COPYRIGHT:
				return COPYRIGHT_EDEFAULT == null ? copyright != null : !COPYRIGHT_EDEFAULT.equals(copyright);
			case TemplatePackage.TEMPLATE_TYPE__HELP:
				return HELP_EDEFAULT == null ? help != null : !HELP_EDEFAULT.equals(help);
			case TemplatePackage.TEMPLATE_TYPE__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case TemplatePackage.TEMPLATE_TYPE__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
		result.append(" (description: ");
		result.append(description);
		result.append(", author: ");
		result.append(author);
		result.append(", copyright: ");
		result.append(copyright);
		result.append(", help: ");
		result.append(help);
		result.append(", label: ");
		result.append(label);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //TemplateTypeImpl