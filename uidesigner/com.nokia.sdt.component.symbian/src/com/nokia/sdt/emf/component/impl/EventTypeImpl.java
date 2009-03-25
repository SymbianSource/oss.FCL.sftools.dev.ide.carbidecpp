/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.EventType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EventTypeImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EventTypeImpl#getDescriptionKey <em>Description Key</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EventTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EventTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EventTypeImpl#getHandlerNameTemplate <em>Handler Name Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EventTypeImpl#getHelpKey <em>Help Key</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EventTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventTypeImpl extends EObjectImpl implements EventType {
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
	 * The default value of the '{@link #getGroup() <em>Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected static final String GROUP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected String group = GROUP_EDEFAULT;

	/**
	 * The default value of the '{@link #getHandlerNameTemplate() <em>Handler Name Template</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHandlerNameTemplate()
	 * @generated
	 * @ordered
	 */
	protected static final String HANDLER_NAME_TEMPLATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHandlerNameTemplate() <em>Handler Name Template</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHandlerNameTemplate()
	 * @generated
	 * @ordered
	 */
	protected String handlerNameTemplate = HANDLER_NAME_TEMPLATE_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.EVENT_TYPE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EVENT_TYPE__CATEGORY, oldCategory, category));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EVENT_TYPE__DESCRIPTION_KEY, oldDescriptionKey, descriptionKey));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EVENT_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroup(String newGroup) {
		String oldGroup = group;
		group = newGroup;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EVENT_TYPE__GROUP, oldGroup, group));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHandlerNameTemplate() {
		return handlerNameTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHandlerNameTemplate(String newHandlerNameTemplate) {
		String oldHandlerNameTemplate = handlerNameTemplate;
		handlerNameTemplate = newHandlerNameTemplate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EVENT_TYPE__HANDLER_NAME_TEMPLATE, oldHandlerNameTemplate, handlerNameTemplate));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EVENT_TYPE__HELP_KEY, oldHelpKey, helpKey));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.EVENT_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.EVENT_TYPE__CATEGORY:
				return getCategory();
			case ComponentPackage.EVENT_TYPE__DESCRIPTION_KEY:
				return getDescriptionKey();
			case ComponentPackage.EVENT_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case ComponentPackage.EVENT_TYPE__GROUP:
				return getGroup();
			case ComponentPackage.EVENT_TYPE__HANDLER_NAME_TEMPLATE:
				return getHandlerNameTemplate();
			case ComponentPackage.EVENT_TYPE__HELP_KEY:
				return getHelpKey();
			case ComponentPackage.EVENT_TYPE__NAME:
				return getName();
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
			case ComponentPackage.EVENT_TYPE__CATEGORY:
				setCategory((String)newValue);
				return;
			case ComponentPackage.EVENT_TYPE__DESCRIPTION_KEY:
				setDescriptionKey((String)newValue);
				return;
			case ComponentPackage.EVENT_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case ComponentPackage.EVENT_TYPE__GROUP:
				setGroup((String)newValue);
				return;
			case ComponentPackage.EVENT_TYPE__HANDLER_NAME_TEMPLATE:
				setHandlerNameTemplate((String)newValue);
				return;
			case ComponentPackage.EVENT_TYPE__HELP_KEY:
				setHelpKey((String)newValue);
				return;
			case ComponentPackage.EVENT_TYPE__NAME:
				setName((String)newValue);
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
			case ComponentPackage.EVENT_TYPE__CATEGORY:
				setCategory(CATEGORY_EDEFAULT);
				return;
			case ComponentPackage.EVENT_TYPE__DESCRIPTION_KEY:
				setDescriptionKey(DESCRIPTION_KEY_EDEFAULT);
				return;
			case ComponentPackage.EVENT_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case ComponentPackage.EVENT_TYPE__GROUP:
				setGroup(GROUP_EDEFAULT);
				return;
			case ComponentPackage.EVENT_TYPE__HANDLER_NAME_TEMPLATE:
				setHandlerNameTemplate(HANDLER_NAME_TEMPLATE_EDEFAULT);
				return;
			case ComponentPackage.EVENT_TYPE__HELP_KEY:
				setHelpKey(HELP_KEY_EDEFAULT);
				return;
			case ComponentPackage.EVENT_TYPE__NAME:
				setName(NAME_EDEFAULT);
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
			case ComponentPackage.EVENT_TYPE__CATEGORY:
				return CATEGORY_EDEFAULT == null ? category != null : !CATEGORY_EDEFAULT.equals(category);
			case ComponentPackage.EVENT_TYPE__DESCRIPTION_KEY:
				return DESCRIPTION_KEY_EDEFAULT == null ? descriptionKey != null : !DESCRIPTION_KEY_EDEFAULT.equals(descriptionKey);
			case ComponentPackage.EVENT_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case ComponentPackage.EVENT_TYPE__GROUP:
				return GROUP_EDEFAULT == null ? group != null : !GROUP_EDEFAULT.equals(group);
			case ComponentPackage.EVENT_TYPE__HANDLER_NAME_TEMPLATE:
				return HANDLER_NAME_TEMPLATE_EDEFAULT == null ? handlerNameTemplate != null : !HANDLER_NAME_TEMPLATE_EDEFAULT.equals(handlerNameTemplate);
			case ComponentPackage.EVENT_TYPE__HELP_KEY:
				return HELP_KEY_EDEFAULT == null ? helpKey != null : !HELP_KEY_EDEFAULT.equals(helpKey);
			case ComponentPackage.EVENT_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(", group: ");
		result.append(group);
		result.append(", handlerNameTemplate: ");
		result.append(handlerNameTemplate);
		result.append(", helpKey: ");
		result.append(helpKey);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //EventTypeImpl
