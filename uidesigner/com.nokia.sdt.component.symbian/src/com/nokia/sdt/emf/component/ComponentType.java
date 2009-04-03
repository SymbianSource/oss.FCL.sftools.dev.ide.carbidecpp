/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getSymbian <em>Symbian</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getDesignerImages <em>Designer Images</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getExtensionProperties <em>Extension Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getPropertyOverrides <em>Property Overrides</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getEvents <em>Events</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getSourceGen <em>Source Gen</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getSourceMapping <em>Source Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getImplementations <em>Implementations</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getBaseComponent <em>Base Component</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getCategory <em>Category</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getFriendlyName <em>Friendly Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getInstanceNameRoot <em>Instance Name Root</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.ComponentType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType()
 * @model extendedMetaData="name='component_._type' kind='elementOnly'"
 * @generated
 */
public interface ComponentType extends EObject{
	/**
	 * Returns the value of the '<em><b>Documentation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The optional documentation element provides brief description information about the component and a link to more detailed documentation
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Documentation</em>' containment reference.
	 * @see #setDocumentation(DocumentationType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_Documentation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='documentation' namespace='##targetNamespace'"
	 * @generated
	 */
	DocumentationType getDocumentation();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getDocumentation <em>Documentation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Documentation</em>' containment reference.
	 * @see #getDocumentation()
	 * @generated
	 */
	void setDocumentation(DocumentationType value);

	/**
	 * Returns the value of the '<em><b>Symbian</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbian</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The optional Symbian element provides SDK compatibility information and information about the related 
	 * 	C++ classes and resource structures. When this element is omitted the component will be considered incompatible
	 * 	with all Symbian SDKs.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Symbian</em>' containment reference.
	 * @see #setSymbian(SymbianType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_Symbian()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='symbian' namespace='##targetNamespace'"
	 * @generated
	 */
	SymbianType getSymbian();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getSymbian <em>Symbian</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symbian</em>' containment reference.
	 * @see #getSymbian()
	 * @generated
	 */
	void setSymbian(SymbianType value);

	/**
	 * Returns the value of the '<em><b>Designer Images</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Designer Images</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This element is specifies images for use by the UI designer. Only those images that apply need to be defined, e.g.
	 * 		if a component never appears in the UI design wizard or palette then it doesn't need those images.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Designer Images</em>' containment reference.
	 * @see #setDesignerImages(DesignerImagesType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_DesignerImages()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='designerImages' namespace='##targetNamespace'"
	 * @generated
	 */
	DesignerImagesType getDesignerImages();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getDesignerImages <em>Designer Images</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Designer Images</em>' containment reference.
	 * @see #getDesignerImages()
	 * @generated
	 */
	void setDesignerImages(DesignerImagesType value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * Attribute elements can hold any static character data that is common to all instances of a component. Attributes are inherited from their base components.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference.
	 * @see #setAttributes(AttributesType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_Attributes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='attributes' namespace='##targetNamespace'"
	 * @generated
	 */
	AttributesType getAttributes();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getAttributes <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attributes</em>' containment reference.
	 * @see #getAttributes()
	 * @generated
	 */
	void setAttributes(AttributesType value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of properties defined for this component. Components also inherit the properties of their base
	 * 		components, and can have properties added dynamically, see extensionProperties.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference.
	 * @see #setProperties(PropertiesType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_Properties()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='properties' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertiesType getProperties();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getProperties <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' containment reference.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(PropertiesType value);

	/**
	 * Returns the value of the '<em><b>Extension Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.ExtensionPropertiesType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension Properties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Extension properties may be dynamically added, based on the state of an instance of this
	 * 		component, its container, or other objects. There may be more than one set of extension properties defined in a component.
	 * 		See the com.nokia.sdt.datamodel.adapter.IPropertyExtenders interface.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extension Properties</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_ExtensionProperties()
	 * @model type="com.nokia.sdt.emf.component.ExtensionPropertiesType" containment="true"
	 *        extendedMetaData="kind='element' name='extensionProperties' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getExtensionProperties();

	/**
	 * Returns the value of the '<em><b>Property Overrides</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Overrides</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of property overrides defined for this component. Components also
	 * 		inherit the property overrides of their base components, with changes applied by the most derived component taking
	 * 		precedence. 
	 * 		
	 * 		Property overrides allow certain characteristics of a property to be modified, such as changing a modifiable property
	 * 		to read-only.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property Overrides</em>' containment reference.
	 * @see #setPropertyOverrides(PropertyOverridesType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_PropertyOverrides()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='propertyOverrides' namespace='##targetNamespace'"
	 * @generated
	 */
	PropertyOverridesType getPropertyOverrides();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getPropertyOverrides <em>Property Overrides</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Overrides</em>' containment reference.
	 * @see #getPropertyOverrides()
	 * @generated
	 */
	void setPropertyOverrides(PropertyOverridesType value);

	/**
	 * Returns the value of the '<em><b>Events</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Events</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of events defined for this component. Components also inherit the events of
	 * 			their base components. Events are named notifications or hooks to which code can be attached. The data here is
	 * 			used to drive the user interface. The details of source code generation are part of the source code generation templates.
	 * 			
	 * 			The set of available events, and default event, may be overriden via code or script. See the 
	 * 			com.nokia.sdt.datamodel.adapter.IComponentEventInfo interface for more information.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Events</em>' containment reference.
	 * @see #setEvents(EventsType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_Events()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='events' namespace='##targetNamespace'"
	 * @generated
	 */
	EventsType getEvents();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getEvents <em>Events</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Events</em>' containment reference.
	 * @see #getEvents()
	 * @generated
	 */
	void setEvents(EventsType value);

	/**
	 * Returns the value of the '<em><b>Source Gen</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Gen</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This section controls C/C++ source generation (with the potential to support 
	 * 		other one-way languages).  In a sourceGen element, all the elements in linear 
	 * 		order comprise the component's contributions.  
	 * 		
	 * 		Conditional sourcegen is implemented by using the "form" attribute on 
	 * 		templates or templateGroups, or explicitly checking the regex "form" in inline code.  
	 * 		
	 * 		Another form of conditional sourcegen is event handling code.  The "ifEvents" attribute completely filters out defineLocation/template elements if a given event is not bound to an instance.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Gen</em>' containment reference.
	 * @see #setSourceGen(SourceGenType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_SourceGen()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='sourceGen' namespace='##targetNamespace'"
	 * @generated
	 */
    SourceGenType getSourceGen();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getSourceGen <em>Source Gen</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Gen</em>' containment reference.
	 * @see #getSourceGen()
	 * @generated
	 */
    void setSourceGen(SourceGenType value);

	/**
	 * Returns the value of the '<em><b>Source Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Mapping</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * The sourceMapping element defines a particular style of source generation
	 * which consists of automatic generation of source by structurally
	 * mapping a component instance to a source definition.  
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Mapping</em>' containment reference.
	 * @see #setSourceMapping(SourceMappingType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_SourceMapping()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='sourceMapping' namespace='##targetNamespace'"
	 * @generated
	 */
    SourceMappingType getSourceMapping();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getSourceMapping <em>Source Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Mapping</em>' containment reference.
	 * @see #getSourceMapping()
	 * @generated
	 */
    void setSourceMapping(SourceMappingType value);

	/**
	 * Returns the value of the '<em><b>Implementations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementations</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Implementation elements declare code or script implementations of interfaces by name.
	 * 			
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Implementations</em>' containment reference.
	 * @see #setImplementations(ImplementationsType)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_Implementations()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='implementations' namespace='##targetNamespace'"
	 * @generated
	 */
	ImplementationsType getImplementations();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getImplementations <em>Implementations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementations</em>' containment reference.
	 * @see #getImplementations()
	 * @generated
	 */
	void setImplementations(ImplementationsType value);

	/**
	 * Returns the value of the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specify true to define a component intended to be used as a basis for deriving other components.
	 * 				Abstract components are filtered out of the component palette.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Abstract</em>' attribute.
	 * @see #isSetAbstract()
	 * @see #unsetAbstract()
	 * @see #setAbstract(boolean)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_Abstract()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='abstract'"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract</em>' attribute.
	 * @see #isSetAbstract()
	 * @see #unsetAbstract()
	 * @see #isAbstract()
	 * @generated
	 */
	void setAbstract(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAbstract()
	 * @see #isAbstract()
	 * @see #setAbstract(boolean)
	 * @generated
	 */
	void unsetAbstract();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.ComponentType#isAbstract <em>Abstract</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Abstract</em>' attribute is set.
	 * @see #unsetAbstract()
	 * @see #isAbstract()
	 * @see #setAbstract(boolean)
	 * @generated
	 */
	boolean isSetAbstract();

	/**
	 * Returns the value of the '<em><b>Base Component</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Base Component</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The fully qualifed name of the base component. The version of the base component
	 * 			cannot be specified, it will be whichever is selected via SDK matching.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Base Component</em>' attribute.
	 * @see #setBaseComponent(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_BaseComponent()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='baseComponent'"
	 * @generated
	 */
    String getBaseComponent();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getBaseComponent <em>Base Component</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Component</em>' attribute.
	 * @see #getBaseComponent()
	 * @generated
	 */
    void setBaseComponent(String value);

	/**
	 * Returns the value of the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Category</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies the grouping for the editor's component palette. If this string matches the key
	 * 				of a known group then the localized name is looked up within the Symbian component provider. Otherwise it is treated like a
	 * 				potentially localized string, i.e. if it is %-prefixed a string is looked up in the properties file.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Category</em>' attribute.
	 * @see #setCategory(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_Category()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='category'"
	 * @generated
	 */
    String getCategory();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' attribute.
	 * @see #getCategory()
	 * @generated
	 */
    void setCategory(String value);

	/**
	 * Returns the value of the '<em><b>Friendly Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Friendly Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A short name displayed in the user interface. These values should be localized.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Friendly Name</em>' attribute.
	 * @see #setFriendlyName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_FriendlyName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='friendlyName'"
	 * @generated
	 */
    String getFriendlyName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getFriendlyName <em>Friendly Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Friendly Name</em>' attribute.
	 * @see #getFriendlyName()
	 * @generated
	 */
    void setFriendlyName(String value);

	/**
	 * Returns the value of the '<em><b>Instance Name Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Instance Name Root</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The root part of the name used in assigned unique names to component instances. For example,
	 * 			if the root is "label" then names will be assigned in sequence "label1", "label2", and so forth. These values should not be localized.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Instance Name Root</em>' attribute.
	 * @see #setInstanceNameRoot(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_InstanceNameRoot()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='instanceNameRoot'"
	 * @generated
	 */
    String getInstanceNameRoot();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getInstanceNameRoot <em>Instance Name Root</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Name Root</em>' attribute.
	 * @see #getInstanceNameRoot()
	 * @generated
	 */
    void setInstanceNameRoot(String value);

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Qualified Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A globally unique name for a component. By convention these use dotted names, such as
	 * 				com.example.MyComponent. Different versions of a component, in different files, can share the same qualified name. These values
	 * 				should not be localized.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see #setQualifiedName(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_QualifiedName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='qualifiedName'"
	 * @generated
	 */
    String getQualifiedName();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getQualifiedName <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
    void setQualifiedName(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Identifies the version of the component specified by this document. Uses OSGI style version strings, composed
	 * 			of up to 3 integers and a text qualifier, of the form &lt;major version&gt;.&lt;minor version&gt;.&lt;micro version&gt;.&lt;qualifier&gt;.
	 * 			See http://help.eclipse.org/help31/topic/org.eclipse.platform.doc.isv/reference/osgi/org/osgi/framework/Version.html for further information.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getComponentType_Version()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='version'"
	 * @generated
	 */
    String getVersion();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.ComponentType#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
    void setVersion(String value);

} // ComponentType
