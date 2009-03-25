/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.AbstractPropertyType;
import com.nokia.sdt.emf.component.ComponentPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Property Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl#getDescriptionKey <em>Description Key</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl#getEditorClass <em>Editor Class</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl#getHelpKey <em>Help Key</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.AbstractPropertyTypeImpl#isResettable <em>Resettable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AbstractPropertyTypeImpl extends EObjectImpl implements AbstractPropertyType {
	/**
	 * The default value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
    protected static final String CATEGORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
    protected String category = CATEGORY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescriptionKey() <em>Description Key</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescriptionKey()
	 * @generated
	 * @ordered
	 */
    protected static final String DESCRIPTION_KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescriptionKey() <em>Description Key</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescriptionKey()
	 * @generated
	 * @ordered
	 */
    protected String descriptionKey = DESCRIPTION_KEY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
    protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
    protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEditorClass() <em>Editor Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorClass()
	 * @generated
	 * @ordered
	 */
	protected static final String EDITOR_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditorClass() <em>Editor Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorClass()
	 * @generated
	 * @ordered
	 */
	protected String editorClass = EDITOR_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getHelpKey() <em>Help Key</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getHelpKey()
	 * @generated
	 * @ordered
	 */
    protected static final String HELP_KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHelpKey() <em>Help Key</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getHelpKey()
	 * @generated
	 * @ordered
	 */
    protected String helpKey = HELP_KEY_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
    protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
    protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
    protected static final boolean READ_ONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
    protected boolean readOnly = READ_ONLY_EDEFAULT;

	/**
	 * This is true if the Read Only attribute has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean readOnlyESet;

	/**
	 * The default value of the '{@link #isResettable() <em>Resettable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isResettable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RESETTABLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isResettable() <em>Resettable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isResettable()
	 * @generated
	 * @ordered
	 */
	protected boolean resettable = RESETTABLE_EDEFAULT;

	/**
	 * This is true if the Resettable attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean resettableESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractPropertyTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.ABSTRACT_PROPERTY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getCategory() {
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setCategory(String newCategory) {
		String oldCategory = category;
		category = newCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_PROPERTY_TYPE__CATEGORY, oldCategory, category));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDescriptionKey() {
		return descriptionKey;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDescriptionKey(String newDescriptionKey) {
		String oldDescriptionKey = descriptionKey;
		descriptionKey = newDescriptionKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY, oldDescriptionKey, descriptionKey));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEditorClass() {
		return editorClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditorClass(String newEditorClass) {
		String oldEditorClass = editorClass;
		editorClass = newEditorClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS, oldEditorClass, editorClass));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getHelpKey() {
		return helpKey;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setHelpKey(String newHelpKey) {
		String oldHelpKey = helpKey;
		helpKey = newHelpKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_PROPERTY_TYPE__HELP_KEY, oldHelpKey, helpKey));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_PROPERTY_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setReadOnly(boolean newReadOnly) {
		boolean oldReadOnly = readOnly;
		readOnly = newReadOnly;
		boolean oldReadOnlyESet = readOnlyESet;
		readOnlyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_PROPERTY_TYPE__READ_ONLY, oldReadOnly, readOnly, !oldReadOnlyESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetReadOnly() {
		boolean oldReadOnly = readOnly;
		boolean oldReadOnlyESet = readOnlyESet;
		readOnly = READ_ONLY_EDEFAULT;
		readOnlyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.ABSTRACT_PROPERTY_TYPE__READ_ONLY, oldReadOnly, READ_ONLY_EDEFAULT, oldReadOnlyESet));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetReadOnly() {
		return readOnlyESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResettable() {
		return resettable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResettable(boolean newResettable) {
		boolean oldResettable = resettable;
		resettable = newResettable;
		boolean oldResettableESet = resettableESet;
		resettableESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_PROPERTY_TYPE__RESETTABLE, oldResettable, resettable, !oldResettableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetResettable() {
		boolean oldResettable = resettable;
		boolean oldResettableESet = resettableESet;
		resettable = RESETTABLE_EDEFAULT;
		resettableESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.ABSTRACT_PROPERTY_TYPE__RESETTABLE, oldResettable, RESETTABLE_EDEFAULT, oldResettableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetResettable() {
		return resettableESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__CATEGORY:
				return getCategory();
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY:
				return getDescriptionKey();
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS:
				return getEditorClass();
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__HELP_KEY:
				return getHelpKey();
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__NAME:
				return getName();
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__READ_ONLY:
				return isReadOnly() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__RESETTABLE:
				return isResettable() ? Boolean.TRUE : Boolean.FALSE;
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
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__CATEGORY:
				setCategory((String)newValue);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY:
				setDescriptionKey((String)newValue);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS:
				setEditorClass((String)newValue);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__HELP_KEY:
				setHelpKey((String)newValue);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__NAME:
				setName((String)newValue);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__READ_ONLY:
				setReadOnly(((Boolean)newValue).booleanValue());
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__RESETTABLE:
				setResettable(((Boolean)newValue).booleanValue());
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
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__CATEGORY:
				setCategory(CATEGORY_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY:
				setDescriptionKey(DESCRIPTION_KEY_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS:
				setEditorClass(EDITOR_CLASS_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__HELP_KEY:
				setHelpKey(HELP_KEY_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__READ_ONLY:
				unsetReadOnly();
				return;
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__RESETTABLE:
				unsetResettable();
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
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__CATEGORY:
				return CATEGORY_EDEFAULT == null ? category != null : !CATEGORY_EDEFAULT.equals(category);
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__DESCRIPTION_KEY:
				return DESCRIPTION_KEY_EDEFAULT == null ? descriptionKey != null : !DESCRIPTION_KEY_EDEFAULT.equals(descriptionKey);
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__EDITOR_CLASS:
				return EDITOR_CLASS_EDEFAULT == null ? editorClass != null : !EDITOR_CLASS_EDEFAULT.equals(editorClass);
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__HELP_KEY:
				return HELP_KEY_EDEFAULT == null ? helpKey != null : !HELP_KEY_EDEFAULT.equals(helpKey);
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__READ_ONLY:
				return isSetReadOnly();
			case ComponentPackage.ABSTRACT_PROPERTY_TYPE__RESETTABLE:
				return isSetResettable();
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
		result.append(" (category: ");
		result.append(category);
		result.append(", descriptionKey: ");
		result.append(descriptionKey);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", editorClass: ");
		result.append(editorClass);
		result.append(", helpKey: ");
		result.append(helpKey);
		result.append(", name: ");
		result.append(name);
		result.append(", readOnly: ");
		if (readOnlyESet) result.append(readOnly); else result.append("<unset>");
		result.append(", resettable: ");
		if (resettableESet) result.append(resettable); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //AbstractPropertyTypeImpl
