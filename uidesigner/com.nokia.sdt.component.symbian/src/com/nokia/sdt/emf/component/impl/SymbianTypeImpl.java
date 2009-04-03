/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.SymbianType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Symbian Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SymbianTypeImpl#getClassHelpTopic <em>Class Help Topic</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SymbianTypeImpl#getClassName <em>Class Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SymbianTypeImpl#getMaxSDKVersion <em>Max SDK Version</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SymbianTypeImpl#getMinSDKVersion <em>Min SDK Version</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SymbianTypeImpl#getResourceHelpTopic <em>Resource Help Topic</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SymbianTypeImpl#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SymbianTypeImpl#getSdkName <em>Sdk Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SymbianTypeImpl extends EObjectImpl implements SymbianType {
	/**
	 * The default value of the '{@link #getClassHelpTopic() <em>Class Help Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getClassHelpTopic()
	 * @generated
	 * @ordered
	 */
    protected static final String CLASS_HELP_TOPIC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassHelpTopic() <em>Class Help Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getClassHelpTopic()
	 * @generated
	 * @ordered
	 */
    protected String classHelpTopic = CLASS_HELP_TOPIC_EDEFAULT;

	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
    protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
    protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxSDKVersion() <em>Max SDK Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMaxSDKVersion()
	 * @generated
	 * @ordered
	 */
    protected static final String MAX_SDK_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMaxSDKVersion() <em>Max SDK Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMaxSDKVersion()
	 * @generated
	 * @ordered
	 */
    protected String maxSDKVersion = MAX_SDK_VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getMinSDKVersion() <em>Min SDK Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMinSDKVersion()
	 * @generated
	 * @ordered
	 */
    protected static final String MIN_SDK_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMinSDKVersion() <em>Min SDK Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMinSDKVersion()
	 * @generated
	 * @ordered
	 */
    protected String minSDKVersion = MIN_SDK_VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getResourceHelpTopic() <em>Resource Help Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getResourceHelpTopic()
	 * @generated
	 * @ordered
	 */
    protected static final String RESOURCE_HELP_TOPIC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResourceHelpTopic() <em>Resource Help Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getResourceHelpTopic()
	 * @generated
	 * @ordered
	 */
    protected String resourceHelpTopic = RESOURCE_HELP_TOPIC_EDEFAULT;

	/**
	 * The default value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getResourceType()
	 * @generated
	 * @ordered
	 */
    protected static final String RESOURCE_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getResourceType()
	 * @generated
	 * @ordered
	 */
    protected String resourceType = RESOURCE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSdkName() <em>Sdk Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSdkName()
	 * @generated
	 * @ordered
	 */
    protected static final Object SDK_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSdkName() <em>Sdk Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSdkName()
	 * @generated
	 * @ordered
	 */
    protected Object sdkName = SDK_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbianTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.SYMBIAN_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getClassHelpTopic() {
		return classHelpTopic;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setClassHelpTopic(String newClassHelpTopic) {
		String oldClassHelpTopic = classHelpTopic;
		classHelpTopic = newClassHelpTopic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYMBIAN_TYPE__CLASS_HELP_TOPIC, oldClassHelpTopic, classHelpTopic));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getClassName() {
		return className;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setClassName(String newClassName) {
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYMBIAN_TYPE__CLASS_NAME, oldClassName, className));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getMaxSDKVersion() {
		return maxSDKVersion;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMaxSDKVersion(String newMaxSDKVersion) {
		String oldMaxSDKVersion = maxSDKVersion;
		maxSDKVersion = newMaxSDKVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYMBIAN_TYPE__MAX_SDK_VERSION, oldMaxSDKVersion, maxSDKVersion));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getMinSDKVersion() {
		return minSDKVersion;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMinSDKVersion(String newMinSDKVersion) {
		String oldMinSDKVersion = minSDKVersion;
		minSDKVersion = newMinSDKVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYMBIAN_TYPE__MIN_SDK_VERSION, oldMinSDKVersion, minSDKVersion));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getResourceHelpTopic() {
		return resourceHelpTopic;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setResourceHelpTopic(String newResourceHelpTopic) {
		String oldResourceHelpTopic = resourceHelpTopic;
		resourceHelpTopic = newResourceHelpTopic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYMBIAN_TYPE__RESOURCE_HELP_TOPIC, oldResourceHelpTopic, resourceHelpTopic));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getResourceType() {
		return resourceType;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setResourceType(String newResourceType) {
		String oldResourceType = resourceType;
		resourceType = newResourceType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYMBIAN_TYPE__RESOURCE_TYPE, oldResourceType, resourceType));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Object getSdkName() {
		return sdkName;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSdkName(Object newSdkName) {
		Object oldSdkName = sdkName;
		sdkName = newSdkName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYMBIAN_TYPE__SDK_NAME, oldSdkName, sdkName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.SYMBIAN_TYPE__CLASS_HELP_TOPIC:
				return getClassHelpTopic();
			case ComponentPackage.SYMBIAN_TYPE__CLASS_NAME:
				return getClassName();
			case ComponentPackage.SYMBIAN_TYPE__MAX_SDK_VERSION:
				return getMaxSDKVersion();
			case ComponentPackage.SYMBIAN_TYPE__MIN_SDK_VERSION:
				return getMinSDKVersion();
			case ComponentPackage.SYMBIAN_TYPE__RESOURCE_HELP_TOPIC:
				return getResourceHelpTopic();
			case ComponentPackage.SYMBIAN_TYPE__RESOURCE_TYPE:
				return getResourceType();
			case ComponentPackage.SYMBIAN_TYPE__SDK_NAME:
				return getSdkName();
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
			case ComponentPackage.SYMBIAN_TYPE__CLASS_HELP_TOPIC:
				setClassHelpTopic((String)newValue);
				return;
			case ComponentPackage.SYMBIAN_TYPE__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case ComponentPackage.SYMBIAN_TYPE__MAX_SDK_VERSION:
				setMaxSDKVersion((String)newValue);
				return;
			case ComponentPackage.SYMBIAN_TYPE__MIN_SDK_VERSION:
				setMinSDKVersion((String)newValue);
				return;
			case ComponentPackage.SYMBIAN_TYPE__RESOURCE_HELP_TOPIC:
				setResourceHelpTopic((String)newValue);
				return;
			case ComponentPackage.SYMBIAN_TYPE__RESOURCE_TYPE:
				setResourceType((String)newValue);
				return;
			case ComponentPackage.SYMBIAN_TYPE__SDK_NAME:
				setSdkName(newValue);
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
			case ComponentPackage.SYMBIAN_TYPE__CLASS_HELP_TOPIC:
				setClassHelpTopic(CLASS_HELP_TOPIC_EDEFAULT);
				return;
			case ComponentPackage.SYMBIAN_TYPE__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case ComponentPackage.SYMBIAN_TYPE__MAX_SDK_VERSION:
				setMaxSDKVersion(MAX_SDK_VERSION_EDEFAULT);
				return;
			case ComponentPackage.SYMBIAN_TYPE__MIN_SDK_VERSION:
				setMinSDKVersion(MIN_SDK_VERSION_EDEFAULT);
				return;
			case ComponentPackage.SYMBIAN_TYPE__RESOURCE_HELP_TOPIC:
				setResourceHelpTopic(RESOURCE_HELP_TOPIC_EDEFAULT);
				return;
			case ComponentPackage.SYMBIAN_TYPE__RESOURCE_TYPE:
				setResourceType(RESOURCE_TYPE_EDEFAULT);
				return;
			case ComponentPackage.SYMBIAN_TYPE__SDK_NAME:
				setSdkName(SDK_NAME_EDEFAULT);
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
			case ComponentPackage.SYMBIAN_TYPE__CLASS_HELP_TOPIC:
				return CLASS_HELP_TOPIC_EDEFAULT == null ? classHelpTopic != null : !CLASS_HELP_TOPIC_EDEFAULT.equals(classHelpTopic);
			case ComponentPackage.SYMBIAN_TYPE__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case ComponentPackage.SYMBIAN_TYPE__MAX_SDK_VERSION:
				return MAX_SDK_VERSION_EDEFAULT == null ? maxSDKVersion != null : !MAX_SDK_VERSION_EDEFAULT.equals(maxSDKVersion);
			case ComponentPackage.SYMBIAN_TYPE__MIN_SDK_VERSION:
				return MIN_SDK_VERSION_EDEFAULT == null ? minSDKVersion != null : !MIN_SDK_VERSION_EDEFAULT.equals(minSDKVersion);
			case ComponentPackage.SYMBIAN_TYPE__RESOURCE_HELP_TOPIC:
				return RESOURCE_HELP_TOPIC_EDEFAULT == null ? resourceHelpTopic != null : !RESOURCE_HELP_TOPIC_EDEFAULT.equals(resourceHelpTopic);
			case ComponentPackage.SYMBIAN_TYPE__RESOURCE_TYPE:
				return RESOURCE_TYPE_EDEFAULT == null ? resourceType != null : !RESOURCE_TYPE_EDEFAULT.equals(resourceType);
			case ComponentPackage.SYMBIAN_TYPE__SDK_NAME:
				return SDK_NAME_EDEFAULT == null ? sdkName != null : !SDK_NAME_EDEFAULT.equals(sdkName);
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
		result.append(" (classHelpTopic: ");
		result.append(classHelpTopic);
		result.append(", className: ");
		result.append(className);
		result.append(", maxSDKVersion: ");
		result.append(maxSDKVersion);
		result.append(", minSDKVersion: ");
		result.append(minSDKVersion);
		result.append(", resourceHelpTopic: ");
		result.append(resourceHelpTopic);
		result.append(", resourceType: ");
		result.append(resourceType);
		result.append(", sdkName: ");
		result.append(sdkName);
		result.append(')');
		return result.toString();
	}

} //SymbianTypeImpl
