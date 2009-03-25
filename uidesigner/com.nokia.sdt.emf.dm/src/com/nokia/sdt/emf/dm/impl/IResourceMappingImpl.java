/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.IResourceMapping;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IResource Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IResourceMappingImpl#getInstanceName <em>Instance Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IResourceMappingImpl#getRsrcFile <em>Rsrc File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IResourceMappingImpl#getRsrcId <em>Rsrc Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IResourceMappingImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IResourceMappingImpl extends EObjectImpl implements IResourceMapping {
	/**
	 * The default value of the '{@link #getInstanceName() <em>Instance Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceName()
	 * @generated
	 * @ordered
	 */
	protected static final String INSTANCE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInstanceName() <em>Instance Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceName()
	 * @generated
	 * @ordered
	 */
	protected String instanceName = INSTANCE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRsrcFile() <em>Rsrc File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRsrcFile()
	 * @generated
	 * @ordered
	 */
	protected static final String RSRC_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRsrcFile() <em>Rsrc File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRsrcFile()
	 * @generated
	 * @ordered
	 */
	protected String rsrcFile = RSRC_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRsrcId() <em>Rsrc Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRsrcId()
	 * @generated
	 * @ordered
	 */
	protected static final String RSRC_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRsrcId() <em>Rsrc Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRsrcId()
	 * @generated
	 * @ordered
	 */
	protected String rsrcId = RSRC_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IResourceMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.Literals.IRESOURCE_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstanceName(String newInstanceName) {
		String oldInstanceName = instanceName;
		instanceName = newInstanceName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IRESOURCE_MAPPING__INSTANCE_NAME, oldInstanceName, instanceName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRsrcFile() {
		return rsrcFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRsrcFile(String newRsrcFile) {
		String oldRsrcFile = rsrcFile;
		rsrcFile = newRsrcFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IRESOURCE_MAPPING__RSRC_FILE, oldRsrcFile, rsrcFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRsrcId() {
		return rsrcId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRsrcId(String newRsrcId) {
		String oldRsrcId = rsrcId;
		rsrcId = newRsrcId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IRESOURCE_MAPPING__RSRC_ID, oldRsrcId, rsrcId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IRESOURCE_MAPPING__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DmPackage.IRESOURCE_MAPPING__INSTANCE_NAME:
				return getInstanceName();
			case DmPackage.IRESOURCE_MAPPING__RSRC_FILE:
				return getRsrcFile();
			case DmPackage.IRESOURCE_MAPPING__RSRC_ID:
				return getRsrcId();
			case DmPackage.IRESOURCE_MAPPING__VALUE:
				return getValue();
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
			case DmPackage.IRESOURCE_MAPPING__INSTANCE_NAME:
				setInstanceName((String)newValue);
				return;
			case DmPackage.IRESOURCE_MAPPING__RSRC_FILE:
				setRsrcFile((String)newValue);
				return;
			case DmPackage.IRESOURCE_MAPPING__RSRC_ID:
				setRsrcId((String)newValue);
				return;
			case DmPackage.IRESOURCE_MAPPING__VALUE:
				setValue((String)newValue);
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
			case DmPackage.IRESOURCE_MAPPING__INSTANCE_NAME:
				setInstanceName(INSTANCE_NAME_EDEFAULT);
				return;
			case DmPackage.IRESOURCE_MAPPING__RSRC_FILE:
				setRsrcFile(RSRC_FILE_EDEFAULT);
				return;
			case DmPackage.IRESOURCE_MAPPING__RSRC_ID:
				setRsrcId(RSRC_ID_EDEFAULT);
				return;
			case DmPackage.IRESOURCE_MAPPING__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case DmPackage.IRESOURCE_MAPPING__INSTANCE_NAME:
				return INSTANCE_NAME_EDEFAULT == null ? instanceName != null : !INSTANCE_NAME_EDEFAULT.equals(instanceName);
			case DmPackage.IRESOURCE_MAPPING__RSRC_FILE:
				return RSRC_FILE_EDEFAULT == null ? rsrcFile != null : !RSRC_FILE_EDEFAULT.equals(rsrcFile);
			case DmPackage.IRESOURCE_MAPPING__RSRC_ID:
				return RSRC_ID_EDEFAULT == null ? rsrcId != null : !RSRC_ID_EDEFAULT.equals(rsrcId);
			case DmPackage.IRESOURCE_MAPPING__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		result.append(" (instanceName: ");
		result.append(instanceName);
		result.append(", rsrcFile: ");
		result.append(rsrcFile);
		result.append(", rsrcId: ");
		result.append(rsrcId);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //IResourceMappingImpl
