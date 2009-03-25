/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.MappingResourceType;

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
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapping Resource Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapSimpleMember <em>Map Simple Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapInstanceMember <em>Map Instance Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapReferenceMember <em>Map Reference Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapFixedMember <em>Map Fixed Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapEnumMember <em>Map Enum Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapIdentifierMember <em>Map Identifier Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapArrayMember <em>Map Array Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapResourceMember <em>Map Resource Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapBitmaskMember <em>Map Bitmask Member</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapMemberFromType <em>Map Member From Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getMapIntoProperty <em>Map Into Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getSelect <em>Select</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getHeaders <em>Headers</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingResourceTypeImpl#getStruct <em>Struct</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MappingResourceTypeImpl extends TwoWayMappingTypeImpl implements MappingResourceType {
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
	 * The default value of the '{@link #getHeaders() <em>Headers</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getHeaders()
	 * @generated
	 * @ordered
	 */
    protected static final List HEADERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaders() <em>Headers</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getHeaders()
	 * @generated
	 * @ordered
	 */
    protected List headers = HEADERS_EDEFAULT;

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
	 * The default value of the '{@link #getStruct() <em>Struct</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getStruct()
	 * @generated
	 * @ordered
	 */
    protected static final String STRUCT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStruct() <em>Struct</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getStruct()
	 * @generated
	 * @ordered
	 */
    protected String struct = STRUCT_EDEFAULT;

    /** @generated NOT */
    String uniqueIdString;
    
    /** @generated NOT */
    static int uniqueId;
    
	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated NOT
	 */
    protected MappingResourceTypeImpl() {
		super();
		this.uniqueIdString = "" + (uniqueId++); 

	}
    
    /**
     * Get unique token for this resource type
     * @generated NOT
     */
    public String getUniqueId() {
        return uniqueIdString;
    }
    
	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.MAPPING_RESOURCE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ComponentPackage.MAPPING_RESOURCE_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapSimpleMember() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapInstanceMember() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapReferenceMember() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapFixedMember() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapEnumMember() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapIdentifierMember() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapArrayMember() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapResourceMember() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapBitmaskMember() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMapMemberFromType() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMapIntoProperty() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getSelect() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_RESOURCE_TYPE__SELECT);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public List getHeaders() {
		return headers;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setHeaders(List newHeaders) {
		List oldHeaders = headers;
		headers = newHeaders;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_RESOURCE_TYPE__HEADERS, oldHeaders, headers));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_RESOURCE_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getStruct() {
		return struct;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setStruct(String newStruct) {
		String oldStruct = struct;
		struct = newStruct;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_RESOURCE_TYPE__STRUCT, oldStruct, struct));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.MAPPING_RESOURCE_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER:
				return ((InternalEList)getMapSimpleMember()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER:
				return ((InternalEList)getMapInstanceMember()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER:
				return ((InternalEList)getMapReferenceMember()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER:
				return ((InternalEList)getMapFixedMember()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER:
				return ((InternalEList)getMapEnumMember()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER:
				return ((InternalEList)getMapIdentifierMember()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER:
				return ((InternalEList)getMapArrayMember()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER:
				return ((InternalEList)getMapResourceMember()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER:
				return ((InternalEList)getMapBitmaskMember()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE:
				return ((InternalEList)getMapMemberFromType()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY:
				return ((InternalEList)getMapIntoProperty()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__SELECT:
				return ((InternalEList)getSelect()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.MAPPING_RESOURCE_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER:
				return getMapSimpleMember();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER:
				return getMapInstanceMember();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER:
				return getMapReferenceMember();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER:
				return getMapFixedMember();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER:
				return getMapEnumMember();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER:
				return getMapIdentifierMember();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER:
				return getMapArrayMember();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER:
				return getMapResourceMember();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER:
				return getMapBitmaskMember();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE:
				return getMapMemberFromType();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY:
				return getMapIntoProperty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__SELECT:
				return getSelect();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__HEADERS:
				return getHeaders();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__ID:
				return getId();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__STRUCT:
				return getStruct();
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
			case ComponentPackage.MAPPING_RESOURCE_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER:
				getMapSimpleMember().clear();
				getMapSimpleMember().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER:
				getMapInstanceMember().clear();
				getMapInstanceMember().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER:
				getMapReferenceMember().clear();
				getMapReferenceMember().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER:
				getMapFixedMember().clear();
				getMapFixedMember().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER:
				getMapEnumMember().clear();
				getMapEnumMember().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER:
				getMapIdentifierMember().clear();
				getMapIdentifierMember().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER:
				getMapArrayMember().clear();
				getMapArrayMember().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER:
				getMapResourceMember().clear();
				getMapResourceMember().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER:
				getMapBitmaskMember().clear();
				getMapBitmaskMember().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE:
				getMapMemberFromType().clear();
				getMapMemberFromType().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY:
				getMapIntoProperty().clear();
				getMapIntoProperty().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__SELECT:
				getSelect().clear();
				getSelect().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__HEADERS:
				setHeaders((List)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__ID:
				setId((String)newValue);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__STRUCT:
				setStruct((String)newValue);
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
			case ComponentPackage.MAPPING_RESOURCE_TYPE__GROUP:
				getGroup().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER:
				getMapSimpleMember().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER:
				getMapInstanceMember().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER:
				getMapReferenceMember().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER:
				getMapFixedMember().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER:
				getMapEnumMember().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER:
				getMapIdentifierMember().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER:
				getMapArrayMember().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER:
				getMapResourceMember().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER:
				getMapBitmaskMember().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE:
				getMapMemberFromType().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY:
				getMapIntoProperty().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__SELECT:
				getSelect().clear();
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__HEADERS:
				setHeaders(HEADERS_EDEFAULT);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case ComponentPackage.MAPPING_RESOURCE_TYPE__STRUCT:
				setStruct(STRUCT_EDEFAULT);
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
			case ComponentPackage.MAPPING_RESOURCE_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_SIMPLE_MEMBER:
				return !getMapSimpleMember().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_INSTANCE_MEMBER:
				return !getMapInstanceMember().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_REFERENCE_MEMBER:
				return !getMapReferenceMember().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_FIXED_MEMBER:
				return !getMapFixedMember().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_ENUM_MEMBER:
				return !getMapEnumMember().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_IDENTIFIER_MEMBER:
				return !getMapIdentifierMember().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_ARRAY_MEMBER:
				return !getMapArrayMember().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_RESOURCE_MEMBER:
				return !getMapResourceMember().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_BITMASK_MEMBER:
				return !getMapBitmaskMember().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_MEMBER_FROM_TYPE:
				return !getMapMemberFromType().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__MAP_INTO_PROPERTY:
				return !getMapIntoProperty().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__SELECT:
				return !getSelect().isEmpty();
			case ComponentPackage.MAPPING_RESOURCE_TYPE__HEADERS:
				return HEADERS_EDEFAULT == null ? headers != null : !HEADERS_EDEFAULT.equals(headers);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ComponentPackage.MAPPING_RESOURCE_TYPE__STRUCT:
				return STRUCT_EDEFAULT == null ? struct != null : !STRUCT_EDEFAULT.equals(struct);
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
		result.append(", headers: ");
		result.append(headers);
		result.append(", id: ");
		result.append(id);
		result.append(", struct: ");
		result.append(struct);
		result.append(')');
		return result.toString();
	}

} //MappingResourceTypeImpl
