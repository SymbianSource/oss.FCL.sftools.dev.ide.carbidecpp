/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Define Location Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getInline <em>Inline</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getScript <em>Script</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getExpandMacro <em>Expand Macro</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getBaseLocation <em>Base Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getDir <em>Dir</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getDomain <em>Domain</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getFile <em>File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getFilter <em>Filter</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getIfEvents <em>If Events</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getIsEventHandler <em>Is Event Handler</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getLocation <em>Location</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getOwned <em>Owned</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DefineLocationType#getRealize <em>Realize</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType()
 * @model extendedMetaData="name='defineLocation_._type' kind='elementOnly'"
 * @generated
 */
public interface DefineLocationType extends EObject{
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
    FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Template</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.TemplateType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Template</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		This defines a contribution.  The text element is template-expanded 
	 * 		with ${ ... } expression escapes and may contain Javascript in &lt;% ... %&gt; sections.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Template</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Template()
	 * @model type="com.nokia.sdt.emf.component.TemplateType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='template' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getTemplate();

	/**
	 * Returns the value of the '<em><b>Inline</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.InlineType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inline</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Provide Javascript in-line with templates and other sourceGen elements.
	 * 		Be sure to supply a "forms" attribute, otherwise the code may execute multiple times
	 * 		during the source generation contribution-gathering phase.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Inline</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Inline()
	 * @model type="com.nokia.sdt.emf.component.InlineType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='inline' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getInline();

	/**
	 * Returns the value of the '<em><b>Script</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.ScriptType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Script</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		Define a reference to an interface implemented in Javascript.
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Script</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Script()
	 * @model type="com.nokia.sdt.emf.component.ScriptType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='script' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getScript();

	/**
	 * Returns the value of the '<em><b>Expand Macro</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.emf.component.ExpandMacroType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expand Macro</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 		<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 		Added post 1.1.
	 * 		Expands a given macro into the sourceGen of the caller.  
	 * 		This has the same effect as inserting the same templates and inlines 
	 * 		from the macro's definition at the point of call.  
	 * 		Variable references from those templates and inlines
	 * 		are substituted with the values provided in attributes 
	 * 		(e.g. variableName="value") or expandArgument child elements.  
	 * 		The latter may be preferred for cases where code is substituted,
	 * 		so the formatting may be retained.
	 * 		</p>
	 * 		<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 		As a special case, variable references may have modifiers to
	 * 		modify the formatting of a variable when it is expanded.  
	 * 		Modifiers are appended
	 * 		to the variable name as in: $(varName::modifier).  
	 * 		</p>
	 * 		<p xmlns="http://www.nokia.com/sdt/emf/component">
	 * 		Currently supported modifiers are all for tweaking canonical
	 * 		function argument lists, which match the format of 
	 * 		declaration, with spaces, argument names, default values, etc:
	 * 		</p>
	 * 		<ul xmlns="http://www.nokia.com/sdt/emf/component">
	 * 			<li>as-function-declaration-args: identity
	 * 			</li>
	 * 			<li>as-function-definition-args: removes default argument values
	 * 			</li>
	 * 			<li>as-function-location-args: removes argument names, default arguments, and spaces
	 * 			</li>
	 * 		</ul>
	 * 		
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expand Macro</em>' containment reference list.
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_ExpandMacro()
	 * @model type="com.nokia.sdt.emf.component.ExpandMacroType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='expandMacro' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getExpandMacro();

	/**
	 * Returns the value of the '<em><b>Base Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Base Location</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				If defined, the location id this location lives inside.  (E.g. a class inside a file,
	 * 				an enum declaration inside a class, etc.)
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Base Location</em>' attribute.
	 * @see #setBaseLocation(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_BaseLocation()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='baseLocation'"
	 * @generated
	 */
    String getBaseLocation();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getBaseLocation <em>Base Location</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Location</em>' attribute.
	 * @see #getBaseLocation()
	 * @generated
	 */
    void setBaseLocation(String value);

	/**
	 * Returns the value of the '<em><b>Dir</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Dir</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				For a top-level location, where baseLocation is null, this is a
	 * 				template-expanded reference to the project-relative directory to
	 * 				use.  Predefined variables ${src}, ${inc}, ${resource}, ${build}
	 * 				are provided.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dir</em>' attribute.
	 * @see #setDir(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Dir()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='dir'"
	 * @generated
	 */
    String getDir();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getDir <em>Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dir</em>' attribute.
	 * @see #getDir()
	 * @generated
	 */
    void setDir(String value);

	/**
	 * Returns the value of the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Domain</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				The source domain which controls how locations are interpreted and instantiated.  
	 * 				Only "cpp" is supported currently.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Domain</em>' attribute.
	 * @see #setDomain(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Domain()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='domain'"
	 * @generated
	 */
    String getDomain();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getDomain <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' attribute.
	 * @see #getDomain()
	 * @generated
	 */
    void setDomain(String value);

	/**
	 * Returns the value of the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>File</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				For a top-level location, where baseLocation is null, this is a
	 * 				template-expanded filename relative to the directory specified by "dir".
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>File</em>' attribute.
	 * @see #setFile(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_File()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='file'"
	 * @generated
	 */
    String getFile();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getFile <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated
	 */
    void setFile(String value);

	/**
	 * Returns the value of the '<em><b>Filter</b></em>' attribute.
	 * The default value is <code>"default"</code>.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Filter</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Defines a filter on contributions added to the location.
	 * 				Available filters:
	 * 				* unique-includes (can be applied anywhere, but scans files)
	 * 				* unique-prototypes (can be applied in "class()" or children, but scans the class)
	 * 				* unique-bases (can be applied to "bases()" or children, but scans the base class list)
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Filter</em>' attribute.
	 * @see #isSetFilter()
	 * @see #unsetFilter()
	 * @see #setFilter(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Filter()
	 * @model default="default" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='filter'"
	 * @generated
	 */
    String getFilter();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getFilter <em>Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filter</em>' attribute.
	 * @see #isSetFilter()
	 * @see #unsetFilter()
	 * @see #getFilter()
	 * @generated
	 */
    void setFilter(String value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getFilter <em>Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetFilter()
	 * @see #getFilter()
	 * @see #setFilter(String)
	 * @generated
	 */
    void unsetFilter();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getFilter <em>Filter</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Filter</em>' attribute is set.
	 * @see #unsetFilter()
	 * @see #getFilter()
	 * @see #setFilter(String)
	 * @generated
	 */
    boolean isSetFilter();

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
	 * 				The location identifier.  This must be unique in a component.  A derived component
	 * 				may redefine a location.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Id()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
    String getId();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
    void setId(String value);

	/**
	 * Returns the value of the '<em><b>If Events</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>If Events</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				If defined, a list of events, any of which must be bound for the
	 * 				location to be declared.  Otherwise, its id is not available and 
	 * 				the location is undefined.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>If Events</em>' attribute.
	 * @see #setIfEvents(List)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_IfEvents()
	 * @model unique="false" dataType="com.nokia.sdt.emf.component.ListOfStrings" many="false"
	 *        extendedMetaData="kind='attribute' name='ifEvents'"
	 * @generated
	 */
	List getIfEvents();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getIfEvents <em>If Events</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>If Events</em>' attribute.
	 * @see #getIfEvents()
	 * @generated
	 */
	void setIfEvents(List value);

	/**
	 * Returns the value of the '<em><b>Is Event Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Note: a string for macro use only; must resolve to "true" or "false".
	 * 				When "ifEvents" is non-empty, this flag tells the designer which particular
	 * 				function is the user-editable event handler.  So this usually is applied
	 * 				to a function() location.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Is Event Handler</em>' attribute.
	 * @see #setIsEventHandler(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_IsEventHandler()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='isEventHandler'"
	 * @generated
	 */
	String getIsEventHandler();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getIsEventHandler <em>Is Event Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Event Handler</em>' attribute.
	 * @see #getIsEventHandler()
	 * @generated
	 */
	void setIsEventHandler(String value);

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
	 * 				This describes the location relative to its base (use "" for a
	 * 				top-level file location).  
	 * 				
	 * A location segment in the cpp domain is a string
	 * representing a node in a C/C++ parse tree.  Each takes the syntax “&lt;name&gt;
	 * ‘(‘ &lt;arguments…&gt; ‘)’??.  Certain nodes may only appear within certain
	 * others.  This list defines top-level nodes:
	 * 
	 * class(&lt;name&gt;): 
	 * reference the class declaration for the class ‘name’, which may include namespaces (e.g.
	 * “class(MyClass)?? or “class(${className})??)
	 * 
	 * function(&lt;name&gt;(&lt;arguments…)):
	 * reference a function with the given signature.  ‘name’ may include namespaces
	 * or represent a destructor.  ‘arguments’ is a comma-separated list of types. 
	 * This list of arguments is currently not parsed and only the number of
	 * arguments matters.  The arguments list may end in ellipsis (“…??) to indicate
	 * that any zero or more arguments are matched.  Again, the &lt;template&gt;
	 * inside a &lt;defineLocation&gt; really defines the function.  (E.g.
	 * “function(main(int,char**))?? or “function(${className}::method(void*)??.)
	 * 
	 * region(&lt;name&gt;): 
	 * a region defines a commented block of text with no other syntactical clues.  The
	 * block comments and the name are used to identify the block.  Thus, names should
	 * be unique across a component.  (E.g. “region(Generated Includes)??.)
	 * 
	 * enum(&lt;name&gt;): 
	 * a enum declaration with the given name.  Name must be non-empty (locations are
	 * used to look up source, so anonymous enums cannot be unambiguously
	 * identified).  (E.g. “enum(T${className}Ids)??.)
	 * 
	 * namespace(&lt;name&gt;):
	 * a namespace declaration with the given name, which may contain colons.  (E.g.
	 * “namespace(std::tr1)??)
	 * 
	 * to-file(): 
	 * resolve to the current file of a location, i.e. get back to the root location. 
	 * 
	 * Inside a class(), namespace() is not allowed, and this
	 * additional segment is allowed:
	 * 
	 * bases(): 
	 * references the base-class-list within a class declaration.  If the class’ defining text
	 * already includes a base, then the defining text for the bases() location may be
	 * omitted.  If a class’ defining text does not include a base, the bases()
	 * location must include the leading colon in its defining text.  Otherwise,
	 * contributions are individual class references with leading commas.
	 * 
	 * In a function(), only class(), region(), enum(), and to-file() are allowed.
	 * 
	 * In an enum(), bases() or region(), only region() and to-file() are allowed.
	 * 
	 * 				
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Location()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='location'"
	 * @generated
	 */
    String getLocation();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
    void setLocation(String value);

	/**
	 * Returns the value of the '<em><b>Owned</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Note: a string for macro use only; must resolve to "true" or "false".
	 * 				If true, the location is marked generated in source and will be
	 * 				regenerated from scratch on every save.  Otherwise, the location
	 * 				is generated only when missing.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned</em>' attribute.
	 * @see #isSetOwned()
	 * @see #unsetOwned()
	 * @see #setOwned(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Owned()
	 * @model default="true" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='owned'"
	 * @generated
	 */
	String getOwned();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getOwned <em>Owned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owned</em>' attribute.
	 * @see #isSetOwned()
	 * @see #unsetOwned()
	 * @see #getOwned()
	 * @generated
	 */
	void setOwned(String value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getOwned <em>Owned</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetOwned()
	 * @see #getOwned()
	 * @see #setOwned(String)
	 * @generated
	 */
    void unsetOwned();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getOwned <em>Owned</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Owned</em>' attribute is set.
	 * @see #unsetOwned()
	 * @see #getOwned()
	 * @see #setOwned(String)
	 * @generated
	 */
    boolean isSetOwned();

	/**
	 * Returns the value of the '<em><b>Realize</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				Added post 1.1.  
	 * 				Note: a string for macro use only; must resolve to "true" or "false".
	 * 				If true, a template for this location is automatically added when this location is defined.
	 * 				Otherwise, the location is a placeholder and is not "realized" in actual source unless a template references it.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Realize</em>' attribute.
	 * @see #isSetRealize()
	 * @see #unsetRealize()
	 * @see #setRealize(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDefineLocationType_Realize()
	 * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='realize'"
	 * @generated
	 */
	String getRealize();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getRealize <em>Realize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Realize</em>' attribute.
	 * @see #isSetRealize()
	 * @see #unsetRealize()
	 * @see #getRealize()
	 * @generated
	 */
	void setRealize(String value);

	/**
	 * Unsets the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getRealize <em>Realize</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRealize()
	 * @see #getRealize()
	 * @see #setRealize(String)
	 * @generated
	 */
	void unsetRealize();

	/**
	 * Returns whether the value of the '{@link com.nokia.sdt.emf.component.DefineLocationType#getRealize <em>Realize</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Realize</em>' attribute is set.
	 * @see #unsetRealize()
	 * @see #getRealize()
	 * @see #setRealize(String)
	 * @generated
	 */
	boolean isSetRealize();

} // DefineLocationType
