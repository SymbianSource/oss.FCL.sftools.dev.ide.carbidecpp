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
 * A representation of the model object '<em><b>Symbian Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.SymbianType#getClassHelpTopic <em>Class Help Topic</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SymbianType#getClassName <em>Class Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SymbianType#getMaxSDKVersion <em>Max SDK Version</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SymbianType#getMinSDKVersion <em>Min SDK Version</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SymbianType#getResourceHelpTopic <em>Resource Help Topic</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SymbianType#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.SymbianType#getSdkName <em>Sdk Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getSymbianType()
 * @model extendedMetaData="name='symbian_._type' kind='empty'"
 * @generated
 */
public interface SymbianType extends EObject{
	/**
	 * Returns the value of the '<em><b>Class Help Topic</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Class Help Topic</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A link to help information for this C++ class
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Class Help Topic</em>' attribute.
	 * @see #setClassHelpTopic(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSymbianType_ClassHelpTopic()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='classHelpTopic'"
	 * @generated
	 */
    String getClassHelpTopic();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SymbianType#getClassHelpTopic <em>Class Help Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Help Topic</em>' attribute.
	 * @see #getClassHelpTopic()
	 * @generated
	 */
    void setClassHelpTopic(String value);

	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Class Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The C++ class name corresponding to this component, if any. Not used for source code generation,
	 * 				but is displayed in the component palette.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSymbianType_ClassName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='className'"
	 * @generated
	 */
    String getClassName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SymbianType#getClassName <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
    void setClassName(String value);

	/**
	 * Returns the value of the '<em><b>Max SDK Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Max SDK Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A version number indicating the maximum SDK version with which the component is
	 * 				compatible. For example, if the value is 2.8 the component will be selected for use with a 2.8 SDK but not 3.0.
	 * 				Uses OSGI style version strings, composed of up to 3 integers and a text qualifier, of the form &lt;major version&gt;.&lt;minor version&gt;.&lt;micro version&gt;.&lt;qualifier&gt;.
	 * 			    See http://help.eclipse.org/help31/topic/org.eclipse.platform.doc.isv/reference/osgi/org/osgi/framework/Version.html for further information.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max SDK Version</em>' attribute.
	 * @see #setMaxSDKVersion(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSymbianType_MaxSDKVersion()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='maxSDKVersion'"
	 * @generated
	 */
    String getMaxSDKVersion();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SymbianType#getMaxSDKVersion <em>Max SDK Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max SDK Version</em>' attribute.
	 * @see #getMaxSDKVersion()
	 * @generated
	 */
    void setMaxSDKVersion(String value);

	/**
	 * Returns the value of the '<em><b>Min SDK Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Min SDK Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A version number indicating the lowest SDK version with which the component is
	 * 				compatible. For example, if the value is 2.8 the component will be selected for use with a 2.8 SDK but not 2.6.
	 * 				Uses OSGI style version strings, composed of up to 3 integers and a text qualifier, of the form &lt;major version&gt;.&lt;minor version&gt;.&lt;micro version&gt;.&lt;qualifier&gt;.
	 * 			    See http://help.eclipse.org/help31/topic/org.eclipse.platform.doc.isv/reference/osgi/org/osgi/framework/Version.html for further information.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min SDK Version</em>' attribute.
	 * @see #setMinSDKVersion(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSymbianType_MinSDKVersion()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='minSDKVersion'"
	 * @generated
	 */
    String getMinSDKVersion();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SymbianType#getMinSDKVersion <em>Min SDK Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min SDK Version</em>' attribute.
	 * @see #getMinSDKVersion()
	 * @generated
	 */
    void setMinSDKVersion(String value);

	/**
	 * Returns the value of the '<em><b>Resource Help Topic</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Help Topic</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A link to help information for the resource
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Resource Help Topic</em>' attribute.
	 * @see #setResourceHelpTopic(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSymbianType_ResourceHelpTopic()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='resourceHelpTopic'"
	 * @generated
	 */
    String getResourceHelpTopic();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SymbianType#getResourceHelpTopic <em>Resource Help Topic</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Help Topic</em>' attribute.
	 * @see #getResourceHelpTopic()
	 * @generated
	 */
    void setResourceHelpTopic(String value);

	/**
	 * Returns the value of the '<em><b>Resource Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The Symbian RSS resource type for this component, if any
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Resource Type</em>' attribute.
	 * @see #setResourceType(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSymbianType_ResourceType()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='resourceType'"
	 * @generated
	 */
    String getResourceType();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SymbianType#getResourceType <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Type</em>' attribute.
	 * @see #getResourceType()
	 * @generated
	 */
    void setResourceType(String value);

	/**
	 * Returns the value of the '<em><b>Sdk Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sdk Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This value identifies the Symbian OS variant with which the component is compatible.
	 * 				Currently the only supported value is "com.nokia.serie60".
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Sdk Name</em>' attribute.
	 * @see #setSdkName(Object)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getSymbianType_SdkName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnySimpleType" required="true"
	 *        extendedMetaData="kind='attribute' name='sdkName'"
	 * @generated
	 */
    Object getSdkName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.SymbianType#getSdkName <em>Sdk Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sdk Name</em>' attribute.
	 * @see #getSdkName()
	 * @generated
	 */
    void setSdkName(Object value);

} // SymbianType
