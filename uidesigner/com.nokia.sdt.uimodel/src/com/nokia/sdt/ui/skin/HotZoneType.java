/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hot Zone Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.ui.skin.HotZoneType#getEvent <em>Event</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.HotZoneType#getState <em>State</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.HotZoneType#getHeight <em>Height</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.HotZoneType#isSticky <em>Sticky</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.HotZoneType#getWidth <em>Width</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.HotZoneType#getX <em>X</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.HotZoneType#getY <em>Y</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.ui.skin.SkinPackage#getHotZoneType()
 * @model extendedMetaData="name='hotZone_._type' kind='elementOnly'"
 * @generated
 */
public interface HotZoneType extends EObject {
	/**
	 * Returns the value of the '<em><b>Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event</em>' containment reference.
	 * @see #setEvent(EventType)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getHotZoneType_Event()
	 * @model containment="true" resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='event' namespace='##targetNamespace'"
	 * @generated
	 */
	EventType getEvent();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getEvent <em>Event</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event</em>' containment reference.
	 * @see #getEvent()
	 * @generated
	 */
	void setEvent(EventType value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.ui.skin.StateType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' containment reference list.
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getHotZoneType_State()
	 * @model type="com.nokia.sdt.ui.skin.StateType" containment="true" resolveProxies="false"
	 *        extendedMetaData="kind='element' name='state' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getState();

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #isSetHeight()
	 * @see #unsetHeight()
	 * @see #setHeight(short)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getHotZoneType_Height()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='height'"
	 * @generated
	 */
	short getHeight();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #isSetHeight()
	 * @see #unsetHeight()
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(short value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHeight()
	 * @see #getHeight()
	 * @see #setHeight(short)
	 * @generated
	 */
	void unsetHeight();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getHeight <em>Height</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Height</em>' attribute is set.
	 * @see #unsetHeight()
	 * @see #getHeight()
	 * @see #setHeight(short)
	 * @generated
	 */
	boolean isSetHeight();

	/**
	 * Returns the value of the '<em><b>Sticky</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sticky</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sticky</em>' attribute.
	 * @see #isSetSticky()
	 * @see #unsetSticky()
	 * @see #setSticky(boolean)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getHotZoneType_Sticky()
	 * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='sticky'"
	 * @generated
	 */
	boolean isSticky();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#isSticky <em>Sticky</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sticky</em>' attribute.
	 * @see #isSetSticky()
	 * @see #unsetSticky()
	 * @see #isSticky()
	 * @generated
	 */
	void setSticky(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#isSticky <em>Sticky</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSticky()
	 * @see #isSticky()
	 * @see #setSticky(boolean)
	 * @generated
	 */
	void unsetSticky();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#isSticky <em>Sticky</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Sticky</em>' attribute is set.
	 * @see #unsetSticky()
	 * @see #isSticky()
	 * @see #setSticky(boolean)
	 * @generated
	 */
	boolean isSetSticky();

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #isSetWidth()
	 * @see #unsetWidth()
	 * @see #setWidth(short)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getHotZoneType_Width()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='width'"
	 * @generated
	 */
	short getWidth();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #isSetWidth()
	 * @see #unsetWidth()
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(short value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWidth()
	 * @see #getWidth()
	 * @see #setWidth(short)
	 * @generated
	 */
	void unsetWidth();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getWidth <em>Width</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Width</em>' attribute is set.
	 * @see #unsetWidth()
	 * @see #getWidth()
	 * @see #setWidth(short)
	 * @generated
	 */
	boolean isSetWidth();

	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #isSetX()
	 * @see #unsetX()
	 * @see #setX(short)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getHotZoneType_X()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='x'"
	 * @generated
	 */
	short getX();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #isSetX()
	 * @see #unsetX()
	 * @see #getX()
	 * @generated
	 */
	void setX(short value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetX()
	 * @see #getX()
	 * @see #setX(short)
	 * @generated
	 */
	void unsetX();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getX <em>X</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>X</em>' attribute is set.
	 * @see #unsetX()
	 * @see #getX()
	 * @see #setX(short)
	 * @generated
	 */
	boolean isSetX();

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #isSetY()
	 * @see #unsetY()
	 * @see #setY(short)
	 * @see com.nokia.sdt.ui.skin.SkinPackage#getHotZoneType_Y()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='y'"
	 * @generated
	 */
	short getY();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #isSetY()
	 * @see #unsetY()
	 * @see #getY()
	 * @generated
	 */
	void setY(short value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetY()
	 * @see #getY()
	 * @see #setY(short)
	 * @generated
	 */
	void unsetY();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.ui.skin.HotZoneType#getY <em>Y</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Y</em>' attribute is set.
	 * @see #unsetY()
	 * @see #getY()
	 * @see #setY(short)
	 * @generated
	 */
	boolean isSetY();

} // HotZoneType
