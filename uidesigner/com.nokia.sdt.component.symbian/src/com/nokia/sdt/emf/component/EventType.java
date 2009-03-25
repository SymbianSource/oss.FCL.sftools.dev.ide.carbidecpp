/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.EventType#getCategory <em>Category</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EventType#getDescriptionKey <em>Description Key</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EventType#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EventType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EventType#getHandlerNameTemplate <em>Handler Name Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EventType#getHelpKey <em>Help Key</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.EventType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventType()
 * @model extendedMetaData="name='event_._type' kind='empty'"
 * @generated
 */
public interface EventType extends EObject{
	/**
	 * Returns the value of the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Categories are used for grouping events in the Events view. As with property categories,
	 * 				this can be a key to a category localized by the component provider, or it can be a pre-localized or literal string.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Category</em>' attribute.
	 * @see #setCategory(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventType_Category()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='category'"
	 * @generated
	 */
	String getCategory();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EventType#getCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' attribute.
	 * @see #getCategory()
	 * @generated
	 */
	void setCategory(String value);

	/**
	 * Returns the value of the '<em><b>Description Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A key for descriptive information about the event.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Description Key</em>' attribute.
	 * @see #setDescriptionKey(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventType_DescriptionKey()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='descriptionKey'"
	 * @generated
	 */
	String getDescriptionKey();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EventType#getDescriptionKey <em>Description Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description Key</em>' attribute.
	 * @see #getDescriptionKey()
	 * @generated
	 */
	void setDescriptionKey(String value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This displayable name for the event. This value should be localized.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventType_DisplayName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='displayName'"
	 * @generated
	 */
    String getDisplayName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EventType#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
    void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This is a grouping, separate from categories, used in event filtering. Events
	 * 				may only make sense for specific containers. The interface com.nokia.sdt.datamodel.adapter.IComponentEventInfo is
	 * 				used to filter applicable events from the complete set of declared events. When deriving from an existing base
	 * 				component it is not generally necessary to implement this interface. Instead, just use the groups defined by
	 * 				the base component in your own events. For example to make events visible in a CCoeControl container, The CCoeControlBase
	 * 				component uses the filter group "Control". 
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Group</em>' attribute.
	 * @see #setGroup(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='group'"
	 * @generated
	 */
	String getGroup();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EventType#getGroup <em>Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group</em>' attribute.
	 * @see #getGroup()
	 * @generated
	 */
	void setGroup(String value);

	/**
	 * Returns the value of the '<em><b>Handler Name Template</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Handler Name Template</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A template which is expanded to the proposed default name for the
	 * 				event handler function. An example of such a template is "Handle{title(name)}StateChangedL".
	 * 				The {} marks template variables. A variable may contain a simple property value, but generally only the 'name' property
	 * 				should be used. The pseudo-functions "lower", "upper", and "title" may be used to transform the property value.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Handler Name Template</em>' attribute.
	 * @see #setHandlerNameTemplate(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventType_HandlerNameTemplate()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='handlerNameTemplate'"
	 * @generated
	 */
	String getHandlerNameTemplate();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EventType#getHandlerNameTemplate <em>Handler Name Template</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Handler Name Template</em>' attribute.
	 * @see #getHandlerNameTemplate()
	 * @generated
	 */
	void setHandlerNameTemplate(String value);

	/**
	 * Returns the value of the '<em><b>Help Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Help Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A help topic for online help for the event.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Help Key</em>' attribute.
	 * @see #setHelpKey(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventType_HelpKey()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='helpKey'"
	 * @generated
	 */
	String getHelpKey();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EventType#getHelpKey <em>Help Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help Key</em>' attribute.
	 * @see #getHelpKey()
	 * @generated
	 */
	void setHelpKey(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The internal name for the event. This does not need to be a dotted name, but it must
	 * 				be unique within the component and its base components.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getEventType_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
    String getName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.EventType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
    void setName(String value);

} // EventType
