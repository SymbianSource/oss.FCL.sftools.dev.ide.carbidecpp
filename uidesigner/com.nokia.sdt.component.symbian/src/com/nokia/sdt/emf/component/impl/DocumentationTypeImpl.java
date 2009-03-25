/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.DocumentationType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Documentation Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentationTypeImpl#getInformation <em>Information</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentationTypeImpl#getHelpTopic <em>Help Topic</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.DocumentationTypeImpl#getWizardDescription <em>Wizard Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentationTypeImpl extends EObjectImpl implements DocumentationType {
	/**
	 * The default value of the '{@link #getInformation() <em>Information</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInformation()
	 * @generated
	 * @ordered
	 */
	protected static final String INFORMATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInformation() <em>Information</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInformation()
	 * @generated
	 * @ordered
	 */
	protected String information = INFORMATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getHelpTopic() <em>Help Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpTopic()
	 * @generated
	 * @ordered
	 */
	protected static final String HELP_TOPIC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHelpTopic() <em>Help Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelpTopic()
	 * @generated
	 * @ordered
	 */
	protected String helpTopic = HELP_TOPIC_EDEFAULT;

	/**
	 * The default value of the '{@link #getWizardDescription() <em>Wizard Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWizardDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String WIZARD_DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWizardDescription() <em>Wizard Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWizardDescription()
	 * @generated
	 * @ordered
	 */
	protected String wizardDescription = WIZARD_DESCRIPTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.DOCUMENTATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInformation() {
		return information;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInformation(String newInformation) {
		String oldInformation = information;
		information = newInformation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DOCUMENTATION_TYPE__INFORMATION, oldInformation, information));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHelpTopic() {
		return helpTopic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHelpTopic(String newHelpTopic) {
		String oldHelpTopic = helpTopic;
		helpTopic = newHelpTopic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DOCUMENTATION_TYPE__HELP_TOPIC, oldHelpTopic, helpTopic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWizardDescription() {
		return wizardDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWizardDescription(String newWizardDescription) {
		String oldWizardDescription = wizardDescription;
		wizardDescription = newWizardDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.DOCUMENTATION_TYPE__WIZARD_DESCRIPTION, oldWizardDescription, wizardDescription));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.DOCUMENTATION_TYPE__INFORMATION:
				return getInformation();
			case ComponentPackage.DOCUMENTATION_TYPE__HELP_TOPIC:
				return getHelpTopic();
			case ComponentPackage.DOCUMENTATION_TYPE__WIZARD_DESCRIPTION:
				return getWizardDescription();
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
			case ComponentPackage.DOCUMENTATION_TYPE__INFORMATION:
				setInformation((String)newValue);
				return;
			case ComponentPackage.DOCUMENTATION_TYPE__HELP_TOPIC:
				setHelpTopic((String)newValue);
				return;
			case ComponentPackage.DOCUMENTATION_TYPE__WIZARD_DESCRIPTION:
				setWizardDescription((String)newValue);
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
			case ComponentPackage.DOCUMENTATION_TYPE__INFORMATION:
				setInformation(INFORMATION_EDEFAULT);
				return;
			case ComponentPackage.DOCUMENTATION_TYPE__HELP_TOPIC:
				setHelpTopic(HELP_TOPIC_EDEFAULT);
				return;
			case ComponentPackage.DOCUMENTATION_TYPE__WIZARD_DESCRIPTION:
				setWizardDescription(WIZARD_DESCRIPTION_EDEFAULT);
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
			case ComponentPackage.DOCUMENTATION_TYPE__INFORMATION:
				return INFORMATION_EDEFAULT == null ? information != null : !INFORMATION_EDEFAULT.equals(information);
			case ComponentPackage.DOCUMENTATION_TYPE__HELP_TOPIC:
				return HELP_TOPIC_EDEFAULT == null ? helpTopic != null : !HELP_TOPIC_EDEFAULT.equals(helpTopic);
			case ComponentPackage.DOCUMENTATION_TYPE__WIZARD_DESCRIPTION:
				return WIZARD_DESCRIPTION_EDEFAULT == null ? wizardDescription != null : !WIZARD_DESCRIPTION_EDEFAULT.equals(wizardDescription);
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
		result.append(" (information: ");
		result.append(information);
		result.append(", helpTopic: ");
		result.append(helpTopic);
		result.append(", wizardDescription: ");
		result.append(wizardDescription);
		result.append(')');
		return result.toString();
	}

} //DocumentationTypeImpl
