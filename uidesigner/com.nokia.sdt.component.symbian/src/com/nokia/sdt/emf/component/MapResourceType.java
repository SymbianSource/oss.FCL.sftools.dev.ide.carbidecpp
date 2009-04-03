/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Resource Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.MapResourceType#getBaseName <em>Base Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapResourceType#getRssFile <em>Rss File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapResourceType#getStandalone <em>Standalone</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.MapResourceType#isUnnamed <em>Unnamed</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapResourceType()
 * @model extendedMetaData="name='mapResource_._type' kind='elementOnly'"
 * @generated
 */
public interface MapResourceType extends MappingResourceType{
	/**
	 * Returns the value of the '<em><b>Base Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Force the base name of the resource.  If unspecified, resources are
	 * 						named after the instance and member or element being mapped.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Base Name</em>' attribute.
	 * @see #setBaseName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapResourceType_BaseName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='baseName'"
	 * @generated
	 */
	String getBaseName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapResourceType#getBaseName <em>Base Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Name</em>' attribute.
	 * @see #getBaseName()
	 * @generated
	 */
	void setBaseName(String value);

	/**
	 * Returns the value of the '<em><b>Rss File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rss File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Force the RSS filename (but not directory).  This is used to redirect
	 * 						resources to a top-level RSS file.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Rss File</em>' attribute.
	 * @see #setRssFile(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapResourceType_RssFile()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='rssFile'"
	 * @generated
	 */
	String getRssFile();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapResourceType#getRssFile <em>Rss File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rss File</em>' attribute.
	 * @see #getRssFile()
	 * @generated
	 */
	void setRssFile(String value);

	/**
	 * Returns the value of the '<em><b>Standalone</b></em>' attribute.
	 * The default value is <code>"default"</code>.
	 * The literals are from the enumeration {@link com.nokia.sdt.emf.component.StandaloneType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						This tells whether the resource must be emitted as a standalone resource statement (always),
	 * 						regardless of whether it is also generated as a resource expression, or whether it
	 * 						will never be emitted unless referenced by another resource (never).  The default behavior
	 * 						is to emit it if not generated as an expression.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Standalone</em>' attribute.
	 * @see com.nokia.sdt.emf.component.StandaloneType
	 * @see #isSetStandalone()
	 * @see #unsetStandalone()
	 * @see #setStandalone(StandaloneType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapResourceType_Standalone()
	 * @model default="default" unique="false" unsettable="true"
	 *        extendedMetaData="kind='attribute' name='standalone'"
	 * @generated
	 */
	StandaloneType getStandalone();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapResourceType#getStandalone <em>Standalone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Standalone</em>' attribute.
	 * @see com.nokia.sdt.emf.component.StandaloneType
	 * @see #isSetStandalone()
	 * @see #unsetStandalone()
	 * @see #getStandalone()
	 * @generated
	 */
	void setStandalone(StandaloneType value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.MapResourceType#getStandalone <em>Standalone</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetStandalone()
	 * @see #getStandalone()
	 * @see #setStandalone(StandaloneType)
	 * @generated
	 */
    void unsetStandalone();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.MapResourceType#getStandalone <em>Standalone</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Standalone</em>' attribute is set.
	 * @see #unsetStandalone()
	 * @see #getStandalone()
	 * @see #setStandalone(StandaloneType)
	 * @generated
	 */
    boolean isSetStandalone();

	/**
	 * Returns the value of the '<em><b>Unnamed</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Unnamed</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						This tells whether to emit the resource without a name
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Unnamed</em>' attribute.
	 * @see #isSetUnnamed()
	 * @see #unsetUnnamed()
	 * @see #setUnnamed(boolean)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getMapResourceType_Unnamed()
	 * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='unnamed'"
	 * @generated
	 */
    boolean isUnnamed();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.MapResourceType#isUnnamed <em>Unnamed</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unnamed</em>' attribute.
	 * @see #isSetUnnamed()
	 * @see #unsetUnnamed()
	 * @see #isUnnamed()
	 * @generated
	 */
    void setUnnamed(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.MapResourceType#isUnnamed <em>Unnamed</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetUnnamed()
	 * @see #isUnnamed()
	 * @see #setUnnamed(boolean)
	 * @generated
	 */
    void unsetUnnamed();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.MapResourceType#isUnnamed <em>Unnamed</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Unnamed</em>' attribute is set.
	 * @see #unsetUnnamed()
	 * @see #isUnnamed()
	 * @see #setUnnamed(boolean)
	 * @generated
	 */
    boolean isSetUnnamed();

} // MapResourceType
