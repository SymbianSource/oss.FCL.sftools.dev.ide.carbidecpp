/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateType#getForm <em>Form</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateType#getLocation <em>Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateType#getMode <em>Mode</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.TemplateType#getPhase <em>Phase</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateType()
 * @model extendedMetaData="name='template_._type' kind='simple'"
 * @generated
 */
public interface TemplateType extends ConditionalSourceGenString{
	/**
	 * Returns the value of the '<em><b>Form</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Form</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						A token used to select a particular kind of source for a parent.
	 * 						The namespace is determined implicitly by what a parent requests.
	 * 						
	 * 						For instance, a parent may have special sourcegen, and pass
	 * 						the form "SpecialCase" to its children.  Templates specifying
	 * 						form="SpecialCase" will be selected.  Other parents will not see
	 * 						such templates if they do not specify that form.
	 * 						
	 * 						If the form does not match, the template is not instantiated.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Form</em>' attribute.
	 * @see #setForm(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateType_Form()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='form'"
	 * @generated
	 */
    String getForm();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.TemplateType#getForm <em>Form</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Form</em>' attribute.
	 * @see #getForm()
	 * @generated
	 */
    void setForm(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Unique id for the template within a component or
	 * 						within a templateGroup.  Ids may be shared for different
	 * 						forms.  Derived components may redefine ids or inherit
	 * 						templates by id.
	 * 						Added post 1.1: if the id is unspecified in a templateGroup, 
	 * 						a default id is assigned.  To avoid inheriting, supply an empty id.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateType_Id()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
    String getId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.TemplateType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
    void setId(String value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						The location id the contribution goes to.  May not be specified when "phase" is set.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateType_Location()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='location'"
	 * @generated
	 */
    String getLocation();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.TemplateType#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
    void setLocation(String value);

	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Mode</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Unused currently.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see #setMode(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateType_Mode()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='mode'"
	 * @generated
	 */
    String getMode();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.TemplateType#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see #getMode()
	 * @generated
	 */
    void setMode(String value);

	/**
	 * Returns the value of the '<em><b>Phase</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Phase</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						The phase the contribution goes to.  A parent component must realize the
	 * 						phase into an actual location id.
	 * 						
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Phase</em>' attribute.
	 * @see #setPhase(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getTemplateType_Phase()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='phase'"
	 * @generated
	 */
    String getPhase();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.TemplateType#getPhase <em>Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Phase</em>' attribute.
	 * @see #getPhase()
	 * @generated
	 */
    void setPhase(String value);

} // TemplateType
