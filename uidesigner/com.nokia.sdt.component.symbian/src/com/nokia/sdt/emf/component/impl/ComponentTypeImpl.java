/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.AttributesType;
import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.ComponentType;
import com.nokia.sdt.emf.component.DesignerImagesType;
import com.nokia.sdt.emf.component.DocumentationType;
import com.nokia.sdt.emf.component.EventsType;
import com.nokia.sdt.emf.component.ExtensionPropertiesType;
import com.nokia.sdt.emf.component.ImplementationsType;
import com.nokia.sdt.emf.component.PropertiesType;
import com.nokia.sdt.emf.component.PropertyOverridesType;
import com.nokia.sdt.emf.component.SourceGenType;
import com.nokia.sdt.emf.component.SourceMappingType;
import com.nokia.sdt.emf.component.SymbianType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getSymbian <em>Symbian</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getDesignerImages <em>Designer Images</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getExtensionProperties <em>Extension Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getPropertyOverrides <em>Property Overrides</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getEvents <em>Events</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getSourceGen <em>Source Gen</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getSourceMapping <em>Source Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getImplementations <em>Implementations</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getBaseComponent <em>Base Component</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getFriendlyName <em>Friendly Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getInstanceNameRoot <em>Instance Name Root</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentTypeImpl extends EObjectImpl implements ComponentType {
	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected DocumentationType documentation;

	/**
	 * The cached value of the '{@link #getSymbian() <em>Symbian</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbian()
	 * @generated
	 * @ordered
	 */
	protected SymbianType symbian;

	/**
	 * The cached value of the '{@link #getDesignerImages() <em>Designer Images</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesignerImages()
	 * @generated
	 * @ordered
	 */
	protected DesignerImagesType designerImages;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected AttributesType attributes;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected PropertiesType properties;

	/**
	 * The cached value of the '{@link #getExtensionProperties() <em>Extension Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensionProperties()
	 * @generated
	 * @ordered
	 */
	protected EList extensionProperties;

	/**
	 * The cached value of the '{@link #getPropertyOverrides() <em>Property Overrides</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyOverrides()
	 * @generated
	 * @ordered
	 */
	protected PropertyOverridesType propertyOverrides;

	/**
	 * The cached value of the '{@link #getEvents() <em>Events</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvents()
	 * @generated
	 * @ordered
	 */
	protected EventsType events;

	/**
	 * The cached value of the '{@link #getSourceGen() <em>Source Gen</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSourceGen()
	 * @generated
	 * @ordered
	 */
    protected SourceGenType sourceGen;

	/**
	 * The cached value of the '{@link #getSourceMapping() <em>Source Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSourceMapping()
	 * @generated
	 * @ordered
	 */
    protected SourceMappingType sourceMapping;

	/**
	 * The cached value of the '{@link #getImplementations() <em>Implementations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementations()
	 * @generated
	 * @ordered
	 */
	protected ImplementationsType implementations;

	/**
	 * The default value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean abstract_ = ABSTRACT_EDEFAULT;

	/**
	 * This is true if the Abstract attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean abstractESet;

	/**
	 * The default value of the '{@link #getBaseComponent() <em>Base Component</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBaseComponent()
	 * @generated
	 * @ordered
	 */
    protected static final String BASE_COMPONENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBaseComponent() <em>Base Component</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBaseComponent()
	 * @generated
	 * @ordered
	 */
    protected String baseComponent = BASE_COMPONENT_EDEFAULT;

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
	 * The default value of the '{@link #getFriendlyName() <em>Friendly Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFriendlyName()
	 * @generated
	 * @ordered
	 */
    protected static final String FRIENDLY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFriendlyName() <em>Friendly Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFriendlyName()
	 * @generated
	 * @ordered
	 */
    protected String friendlyName = FRIENDLY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getInstanceNameRoot() <em>Instance Name Root</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getInstanceNameRoot()
	 * @generated
	 * @ordered
	 */
    protected static final String INSTANCE_NAME_ROOT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInstanceNameRoot() <em>Instance Name Root</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getInstanceNameRoot()
	 * @generated
	 * @ordered
	 */
    protected String instanceNameRoot = INSTANCE_NAME_ROOT_EDEFAULT;

	/**
	 * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
    protected static final String QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
    protected String qualifiedName = QUALIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
    protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
    protected String version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.COMPONENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentationType getDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentation(DocumentationType newDocumentation, NotificationChain msgs) {
		DocumentationType oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__DOCUMENTATION, oldDocumentation, newDocumentation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentation(DocumentationType newDocumentation) {
		if (newDocumentation != documentation) {
			NotificationChain msgs = null;
			if (documentation != null)
				msgs = ((InternalEObject)documentation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__DOCUMENTATION, null, msgs);
			if (newDocumentation != null)
				msgs = ((InternalEObject)newDocumentation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__DOCUMENTATION, null, msgs);
			msgs = basicSetDocumentation(newDocumentation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__DOCUMENTATION, newDocumentation, newDocumentation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianType getSymbian() {
		return symbian;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSymbian(SymbianType newSymbian, NotificationChain msgs) {
		SymbianType oldSymbian = symbian;
		symbian = newSymbian;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__SYMBIAN, oldSymbian, newSymbian);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymbian(SymbianType newSymbian) {
		if (newSymbian != symbian) {
			NotificationChain msgs = null;
			if (symbian != null)
				msgs = ((InternalEObject)symbian).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__SYMBIAN, null, msgs);
			if (newSymbian != null)
				msgs = ((InternalEObject)newSymbian).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__SYMBIAN, null, msgs);
			msgs = basicSetSymbian(newSymbian, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__SYMBIAN, newSymbian, newSymbian));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesignerImagesType getDesignerImages() {
		return designerImages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDesignerImages(DesignerImagesType newDesignerImages, NotificationChain msgs) {
		DesignerImagesType oldDesignerImages = designerImages;
		designerImages = newDesignerImages;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__DESIGNER_IMAGES, oldDesignerImages, newDesignerImages);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDesignerImages(DesignerImagesType newDesignerImages) {
		if (newDesignerImages != designerImages) {
			NotificationChain msgs = null;
			if (designerImages != null)
				msgs = ((InternalEObject)designerImages).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__DESIGNER_IMAGES, null, msgs);
			if (newDesignerImages != null)
				msgs = ((InternalEObject)newDesignerImages).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__DESIGNER_IMAGES, null, msgs);
			msgs = basicSetDesignerImages(newDesignerImages, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__DESIGNER_IMAGES, newDesignerImages, newDesignerImages));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributesType getAttributes() {
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributes(AttributesType newAttributes, NotificationChain msgs) {
		AttributesType oldAttributes = attributes;
		attributes = newAttributes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__ATTRIBUTES, oldAttributes, newAttributes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributes(AttributesType newAttributes) {
		if (newAttributes != attributes) {
			NotificationChain msgs = null;
			if (attributes != null)
				msgs = ((InternalEObject)attributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__ATTRIBUTES, null, msgs);
			if (newAttributes != null)
				msgs = ((InternalEObject)newAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__ATTRIBUTES, null, msgs);
			msgs = basicSetAttributes(newAttributes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__ATTRIBUTES, newAttributes, newAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesType getProperties() {
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(PropertiesType newProperties, NotificationChain msgs) {
		PropertiesType oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__PROPERTIES, oldProperties, newProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(PropertiesType newProperties) {
		if (newProperties != properties) {
			NotificationChain msgs = null;
			if (properties != null)
				msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__PROPERTIES, null, msgs);
			if (newProperties != null)
				msgs = ((InternalEObject)newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__PROPERTIES, null, msgs);
			msgs = basicSetProperties(newProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__PROPERTIES, newProperties, newProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getExtensionProperties() {
		if (extensionProperties == null) {
			extensionProperties = new EObjectContainmentEList(ExtensionPropertiesType.class, this, ComponentPackage.COMPONENT_TYPE__EXTENSION_PROPERTIES);
		}
		return extensionProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyOverridesType getPropertyOverrides() {
		return propertyOverrides;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropertyOverrides(PropertyOverridesType newPropertyOverrides, NotificationChain msgs) {
		PropertyOverridesType oldPropertyOverrides = propertyOverrides;
		propertyOverrides = newPropertyOverrides;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__PROPERTY_OVERRIDES, oldPropertyOverrides, newPropertyOverrides);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyOverrides(PropertyOverridesType newPropertyOverrides) {
		if (newPropertyOverrides != propertyOverrides) {
			NotificationChain msgs = null;
			if (propertyOverrides != null)
				msgs = ((InternalEObject)propertyOverrides).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__PROPERTY_OVERRIDES, null, msgs);
			if (newPropertyOverrides != null)
				msgs = ((InternalEObject)newPropertyOverrides).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__PROPERTY_OVERRIDES, null, msgs);
			msgs = basicSetPropertyOverrides(newPropertyOverrides, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__PROPERTY_OVERRIDES, newPropertyOverrides, newPropertyOverrides));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventsType getEvents() {
		return events;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvents(EventsType newEvents, NotificationChain msgs) {
		EventsType oldEvents = events;
		events = newEvents;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__EVENTS, oldEvents, newEvents);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvents(EventsType newEvents) {
		if (newEvents != events) {
			NotificationChain msgs = null;
			if (events != null)
				msgs = ((InternalEObject)events).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__EVENTS, null, msgs);
			if (newEvents != null)
				msgs = ((InternalEObject)newEvents).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__EVENTS, null, msgs);
			msgs = basicSetEvents(newEvents, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__EVENTS, newEvents, newEvents));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public SourceGenType getSourceGen() {
		return sourceGen;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetSourceGen(SourceGenType newSourceGen, NotificationChain msgs) {
		SourceGenType oldSourceGen = sourceGen;
		sourceGen = newSourceGen;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__SOURCE_GEN, oldSourceGen, newSourceGen);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSourceGen(SourceGenType newSourceGen) {
		if (newSourceGen != sourceGen) {
			NotificationChain msgs = null;
			if (sourceGen != null)
				msgs = ((InternalEObject)sourceGen).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__SOURCE_GEN, null, msgs);
			if (newSourceGen != null)
				msgs = ((InternalEObject)newSourceGen).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__SOURCE_GEN, null, msgs);
			msgs = basicSetSourceGen(newSourceGen, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__SOURCE_GEN, newSourceGen, newSourceGen));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public SourceMappingType getSourceMapping() {
		return sourceMapping;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetSourceMapping(SourceMappingType newSourceMapping, NotificationChain msgs) {
		SourceMappingType oldSourceMapping = sourceMapping;
		sourceMapping = newSourceMapping;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__SOURCE_MAPPING, oldSourceMapping, newSourceMapping);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSourceMapping(SourceMappingType newSourceMapping) {
		if (newSourceMapping != sourceMapping) {
			NotificationChain msgs = null;
			if (sourceMapping != null)
				msgs = ((InternalEObject)sourceMapping).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__SOURCE_MAPPING, null, msgs);
			if (newSourceMapping != null)
				msgs = ((InternalEObject)newSourceMapping).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__SOURCE_MAPPING, null, msgs);
			msgs = basicSetSourceMapping(newSourceMapping, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__SOURCE_MAPPING, newSourceMapping, newSourceMapping));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImplementationsType getImplementations() {
		return implementations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImplementations(ImplementationsType newImplementations, NotificationChain msgs) {
		ImplementationsType oldImplementations = implementations;
		implementations = newImplementations;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__IMPLEMENTATIONS, oldImplementations, newImplementations);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementations(ImplementationsType newImplementations) {
		if (newImplementations != implementations) {
			NotificationChain msgs = null;
			if (implementations != null)
				msgs = ((InternalEObject)implementations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__IMPLEMENTATIONS, null, msgs);
			if (newImplementations != null)
				msgs = ((InternalEObject)newImplementations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_TYPE__IMPLEMENTATIONS, null, msgs);
			msgs = basicSetImplementations(newImplementations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__IMPLEMENTATIONS, newImplementations, newImplementations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAbstract() {
		return abstract_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbstract(boolean newAbstract) {
		boolean oldAbstract = abstract_;
		abstract_ = newAbstract;
		boolean oldAbstractESet = abstractESet;
		abstractESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__ABSTRACT, oldAbstract, abstract_, !oldAbstractESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAbstract() {
		boolean oldAbstract = abstract_;
		boolean oldAbstractESet = abstractESet;
		abstract_ = ABSTRACT_EDEFAULT;
		abstractESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.COMPONENT_TYPE__ABSTRACT, oldAbstract, ABSTRACT_EDEFAULT, oldAbstractESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAbstract() {
		return abstractESet;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getBaseComponent() {
		return baseComponent;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setBaseComponent(String newBaseComponent) {
		String oldBaseComponent = baseComponent;
		baseComponent = newBaseComponent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__BASE_COMPONENT, oldBaseComponent, baseComponent));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__CATEGORY, oldCategory, category));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getFriendlyName() {
		return friendlyName;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setFriendlyName(String newFriendlyName) {
		String oldFriendlyName = friendlyName;
		friendlyName = newFriendlyName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__FRIENDLY_NAME, oldFriendlyName, friendlyName));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getInstanceNameRoot() {
		return instanceNameRoot;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setInstanceNameRoot(String newInstanceNameRoot) {
		String oldInstanceNameRoot = instanceNameRoot;
		instanceNameRoot = newInstanceNameRoot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__INSTANCE_NAME_ROOT, oldInstanceNameRoot, instanceNameRoot));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getQualifiedName() {
		return qualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setQualifiedName(String newQualifiedName) {
		String oldQualifiedName = qualifiedName;
		qualifiedName = newQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__QUALIFIED_NAME, oldQualifiedName, qualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_TYPE__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.COMPONENT_TYPE__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case ComponentPackage.COMPONENT_TYPE__SYMBIAN:
				return basicSetSymbian(null, msgs);
			case ComponentPackage.COMPONENT_TYPE__DESIGNER_IMAGES:
				return basicSetDesignerImages(null, msgs);
			case ComponentPackage.COMPONENT_TYPE__ATTRIBUTES:
				return basicSetAttributes(null, msgs);
			case ComponentPackage.COMPONENT_TYPE__PROPERTIES:
				return basicSetProperties(null, msgs);
			case ComponentPackage.COMPONENT_TYPE__EXTENSION_PROPERTIES:
				return ((InternalEList)getExtensionProperties()).basicRemove(otherEnd, msgs);
			case ComponentPackage.COMPONENT_TYPE__PROPERTY_OVERRIDES:
				return basicSetPropertyOverrides(null, msgs);
			case ComponentPackage.COMPONENT_TYPE__EVENTS:
				return basicSetEvents(null, msgs);
			case ComponentPackage.COMPONENT_TYPE__SOURCE_GEN:
				return basicSetSourceGen(null, msgs);
			case ComponentPackage.COMPONENT_TYPE__SOURCE_MAPPING:
				return basicSetSourceMapping(null, msgs);
			case ComponentPackage.COMPONENT_TYPE__IMPLEMENTATIONS:
				return basicSetImplementations(null, msgs);
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
			case ComponentPackage.COMPONENT_TYPE__DOCUMENTATION:
				return getDocumentation();
			case ComponentPackage.COMPONENT_TYPE__SYMBIAN:
				return getSymbian();
			case ComponentPackage.COMPONENT_TYPE__DESIGNER_IMAGES:
				return getDesignerImages();
			case ComponentPackage.COMPONENT_TYPE__ATTRIBUTES:
				return getAttributes();
			case ComponentPackage.COMPONENT_TYPE__PROPERTIES:
				return getProperties();
			case ComponentPackage.COMPONENT_TYPE__EXTENSION_PROPERTIES:
				return getExtensionProperties();
			case ComponentPackage.COMPONENT_TYPE__PROPERTY_OVERRIDES:
				return getPropertyOverrides();
			case ComponentPackage.COMPONENT_TYPE__EVENTS:
				return getEvents();
			case ComponentPackage.COMPONENT_TYPE__SOURCE_GEN:
				return getSourceGen();
			case ComponentPackage.COMPONENT_TYPE__SOURCE_MAPPING:
				return getSourceMapping();
			case ComponentPackage.COMPONENT_TYPE__IMPLEMENTATIONS:
				return getImplementations();
			case ComponentPackage.COMPONENT_TYPE__ABSTRACT:
				return isAbstract() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.COMPONENT_TYPE__BASE_COMPONENT:
				return getBaseComponent();
			case ComponentPackage.COMPONENT_TYPE__CATEGORY:
				return getCategory();
			case ComponentPackage.COMPONENT_TYPE__FRIENDLY_NAME:
				return getFriendlyName();
			case ComponentPackage.COMPONENT_TYPE__INSTANCE_NAME_ROOT:
				return getInstanceNameRoot();
			case ComponentPackage.COMPONENT_TYPE__QUALIFIED_NAME:
				return getQualifiedName();
			case ComponentPackage.COMPONENT_TYPE__VERSION:
				return getVersion();
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
			case ComponentPackage.COMPONENT_TYPE__DOCUMENTATION:
				setDocumentation((DocumentationType)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__SYMBIAN:
				setSymbian((SymbianType)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__DESIGNER_IMAGES:
				setDesignerImages((DesignerImagesType)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__ATTRIBUTES:
				setAttributes((AttributesType)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__PROPERTIES:
				setProperties((PropertiesType)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__EXTENSION_PROPERTIES:
				getExtensionProperties().clear();
				getExtensionProperties().addAll((Collection)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__PROPERTY_OVERRIDES:
				setPropertyOverrides((PropertyOverridesType)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__EVENTS:
				setEvents((EventsType)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__SOURCE_GEN:
				setSourceGen((SourceGenType)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__SOURCE_MAPPING:
				setSourceMapping((SourceMappingType)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__IMPLEMENTATIONS:
				setImplementations((ImplementationsType)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__ABSTRACT:
				setAbstract(((Boolean)newValue).booleanValue());
				return;
			case ComponentPackage.COMPONENT_TYPE__BASE_COMPONENT:
				setBaseComponent((String)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__CATEGORY:
				setCategory((String)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__FRIENDLY_NAME:
				setFriendlyName((String)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__INSTANCE_NAME_ROOT:
				setInstanceNameRoot((String)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__QUALIFIED_NAME:
				setQualifiedName((String)newValue);
				return;
			case ComponentPackage.COMPONENT_TYPE__VERSION:
				setVersion((String)newValue);
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
			case ComponentPackage.COMPONENT_TYPE__DOCUMENTATION:
				setDocumentation((DocumentationType)null);
				return;
			case ComponentPackage.COMPONENT_TYPE__SYMBIAN:
				setSymbian((SymbianType)null);
				return;
			case ComponentPackage.COMPONENT_TYPE__DESIGNER_IMAGES:
				setDesignerImages((DesignerImagesType)null);
				return;
			case ComponentPackage.COMPONENT_TYPE__ATTRIBUTES:
				setAttributes((AttributesType)null);
				return;
			case ComponentPackage.COMPONENT_TYPE__PROPERTIES:
				setProperties((PropertiesType)null);
				return;
			case ComponentPackage.COMPONENT_TYPE__EXTENSION_PROPERTIES:
				getExtensionProperties().clear();
				return;
			case ComponentPackage.COMPONENT_TYPE__PROPERTY_OVERRIDES:
				setPropertyOverrides((PropertyOverridesType)null);
				return;
			case ComponentPackage.COMPONENT_TYPE__EVENTS:
				setEvents((EventsType)null);
				return;
			case ComponentPackage.COMPONENT_TYPE__SOURCE_GEN:
				setSourceGen((SourceGenType)null);
				return;
			case ComponentPackage.COMPONENT_TYPE__SOURCE_MAPPING:
				setSourceMapping((SourceMappingType)null);
				return;
			case ComponentPackage.COMPONENT_TYPE__IMPLEMENTATIONS:
				setImplementations((ImplementationsType)null);
				return;
			case ComponentPackage.COMPONENT_TYPE__ABSTRACT:
				unsetAbstract();
				return;
			case ComponentPackage.COMPONENT_TYPE__BASE_COMPONENT:
				setBaseComponent(BASE_COMPONENT_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT_TYPE__CATEGORY:
				setCategory(CATEGORY_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT_TYPE__FRIENDLY_NAME:
				setFriendlyName(FRIENDLY_NAME_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT_TYPE__INSTANCE_NAME_ROOT:
				setInstanceNameRoot(INSTANCE_NAME_ROOT_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT_TYPE__QUALIFIED_NAME:
				setQualifiedName(QUALIFIED_NAME_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT_TYPE__VERSION:
				setVersion(VERSION_EDEFAULT);
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
			case ComponentPackage.COMPONENT_TYPE__DOCUMENTATION:
				return documentation != null;
			case ComponentPackage.COMPONENT_TYPE__SYMBIAN:
				return symbian != null;
			case ComponentPackage.COMPONENT_TYPE__DESIGNER_IMAGES:
				return designerImages != null;
			case ComponentPackage.COMPONENT_TYPE__ATTRIBUTES:
				return attributes != null;
			case ComponentPackage.COMPONENT_TYPE__PROPERTIES:
				return properties != null;
			case ComponentPackage.COMPONENT_TYPE__EXTENSION_PROPERTIES:
				return extensionProperties != null && !extensionProperties.isEmpty();
			case ComponentPackage.COMPONENT_TYPE__PROPERTY_OVERRIDES:
				return propertyOverrides != null;
			case ComponentPackage.COMPONENT_TYPE__EVENTS:
				return events != null;
			case ComponentPackage.COMPONENT_TYPE__SOURCE_GEN:
				return sourceGen != null;
			case ComponentPackage.COMPONENT_TYPE__SOURCE_MAPPING:
				return sourceMapping != null;
			case ComponentPackage.COMPONENT_TYPE__IMPLEMENTATIONS:
				return implementations != null;
			case ComponentPackage.COMPONENT_TYPE__ABSTRACT:
				return isSetAbstract();
			case ComponentPackage.COMPONENT_TYPE__BASE_COMPONENT:
				return BASE_COMPONENT_EDEFAULT == null ? baseComponent != null : !BASE_COMPONENT_EDEFAULT.equals(baseComponent);
			case ComponentPackage.COMPONENT_TYPE__CATEGORY:
				return CATEGORY_EDEFAULT == null ? category != null : !CATEGORY_EDEFAULT.equals(category);
			case ComponentPackage.COMPONENT_TYPE__FRIENDLY_NAME:
				return FRIENDLY_NAME_EDEFAULT == null ? friendlyName != null : !FRIENDLY_NAME_EDEFAULT.equals(friendlyName);
			case ComponentPackage.COMPONENT_TYPE__INSTANCE_NAME_ROOT:
				return INSTANCE_NAME_ROOT_EDEFAULT == null ? instanceNameRoot != null : !INSTANCE_NAME_ROOT_EDEFAULT.equals(instanceNameRoot);
			case ComponentPackage.COMPONENT_TYPE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? qualifiedName != null : !QUALIFIED_NAME_EDEFAULT.equals(qualifiedName);
			case ComponentPackage.COMPONENT_TYPE__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
		result.append(" (abstract: ");
		if (abstractESet) result.append(abstract_); else result.append("<unset>");
		result.append(", baseComponent: ");
		result.append(baseComponent);
		result.append(", category: ");
		result.append(category);
		result.append(", friendlyName: ");
		result.append(friendlyName);
		result.append(", instanceNameRoot: ");
		result.append(instanceNameRoot);
		result.append(", qualifiedName: ");
		result.append(qualifiedName);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //ComponentTypeImpl
