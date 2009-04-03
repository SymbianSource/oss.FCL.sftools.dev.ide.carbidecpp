/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.emf.dm.*;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>INode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.INodeImpl#getComponentId <em>Component Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.INodeImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.INodeImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.INodeImpl#getEventBindings <em>Event Bindings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated NOT
 */
public class INodeImpl extends EObjectImpl implements INode, IInternalPropertyChangeListener {
	private final class ReferencePropertyVisitor implements IPropertyVisitor {
		private final String newName;
		private final String oldName;
		private INodeImpl node;

		private ReferencePropertyVisitor(String newName, String oldName) {
			this.newName = newName;
			this.oldName = oldName;
		}

		public void setNode(INodeImpl node) {
			this.node = node;
		}

		public Object visit(IPropertyContainer container, EStringToIPropertyValueMapEntryImpl entry) {
			IPropertyValue pv = entry.getTypedValue();
			// We need to check for strings referencing the old name,
			// both as simple values and sequences. Compound property 
			// values will be handled as the visitor recurses.
			if (pv.hasStringValue()) {
				StringValue currStringValue = pv.getStringValue();
				if (currStringValue.isReference() &&
					currStringValue.getValue().equals(oldName)) {
					if (newName != null) {
						IPropertyValue newPV = DmFactory.eINSTANCE.createIPropertyValue();
						newPV.setStringValue(new StringValue(StringValue.REFERENCE, newName));
						entry.setTypedValue(newPV);
						node.propertyChanged(entry.getKey(), pv, newPV);
					}
					else {
						// we set the value to null. the caller will reset the
						// property
						entry.setTypedValue(null);
						node.propertyChanged(entry.getKey(), pv, null);
					}
				}
			}
			else if (pv.hasSequenceValue()) {
				EList l = pv.getSequenceValue();
				boolean didChange = false;
				for (ListIterator iter = l.listIterator(); iter.hasNext();) {
					IPropertyValue seqPV = (IPropertyValue) iter.next();
					if (seqPV.hasStringValue()) {
						StringValue currStringValue = seqPV.getStringValue();
						if (currStringValue.isReference() &&
								currStringValue.getValue().equals(oldName)) {
							if (newName != null) {
								IPropertyValue newPV = DmFactory.eINSTANCE.createIPropertyValue();
								newPV.setStringValue(new StringValue(StringValue.REFERENCE, newName));
								iter.set(newPV);
							} else {
								iter.remove();
							}
							didChange = true;
						}
					}
				}
				if (didChange) {
					((PropertyValueSequence) l).didChange();
				}
			}
			return null;
		}
	}

	/**
	 * The default value of the '{@link #getComponentId() <em>Component Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentId()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPONENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComponentId() <em>Component Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentId()
	 * @generated
	 * @ordered
	 */
	protected String componentId = COMPONENT_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected IPropertyContainer properties = null;
	
	private PropertyValueSource valueSource;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList children = null;

	/**
	 * The cached value of the '{@link #getEventBindings() <em>Event Bindings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventBindings()
	 * @generated
	 * @ordered
	 */
	protected EList eventBindings = null;
	
	/**
	 * See INode#isConfigured
	 */
	protected boolean configured;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected INodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.eINSTANCE.getINode();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComponentId() {
		return componentId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentId(String newComponentId) {
		String oldComponentId = componentId;
		componentId = newComponentId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.INODE__COMPONENT_ID, oldComponentId, componentId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public IPropertyContainer getProperties() {
		if (properties == null) {
			properties = DmFactory.eINSTANCE.createIPropertyContainer();
			IPropertyContainerImpl impl = (IPropertyContainerImpl) properties;
			impl.setOwner(this);
			impl.setPropertyChangeListener(this);
			impl.setValidateNameProperty(true);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(IPropertyContainer newProperties, NotificationChain msgs) {
		IPropertyContainer oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.INODE__PROPERTIES, oldProperties, newProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	public IComponent getComponent() {
		IComponent result = null;
		ComponentHelper helper = ComponentHelper.getComponentHelper(this);
		if (helper != null)
			result = helper.lookupComponent(componentId);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getChildren() {
		if (children == null) {
			children = new ValidatingEObjectContainmentEList(INode.class, this, DmPackage.INODE__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getEventBindings() {
		if (eventBindings == null) {
			eventBindings = new EObjectContainmentEList(IEventBinding.class, this, DmPackage.INODE__EVENT_BINDINGS);
		}
		return eventBindings;
	}
	
	public IEventBinding findBinding(String eventID) {
		IEventBinding result = null;
		if (eventBindings != null) {
			for (Iterator iter = eventBindings.iterator(); iter.hasNext();) {
				IEventBinding binding = (IEventBinding) iter.next();
				if (binding.getEventID().equals(eventID)) {
					result = binding;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case DmPackage.INODE__PROPERTIES:
					return basicSetProperties(null, msgs);
				case DmPackage.INODE__CHILDREN:
					return ((InternalEList)getChildren()).basicRemove(otherEnd, msgs);
				case DmPackage.INODE__EVENT_BINDINGS:
					return ((InternalEList)getEventBindings()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case DmPackage.INODE__COMPONENT_ID:
				return getComponentId();
			case DmPackage.INODE__PROPERTIES:
				return getProperties();
			case DmPackage.INODE__CHILDREN:
				return getChildren();
			case DmPackage.INODE__EVENT_BINDINGS:
				return getEventBindings();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case DmPackage.INODE__COMPONENT_ID:
				setComponentId((String)newValue);
				return;
			case DmPackage.INODE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
				return;
			case DmPackage.INODE__EVENT_BINDINGS:
				getEventBindings().clear();
				getEventBindings().addAll((Collection)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case DmPackage.INODE__COMPONENT_ID:
				setComponentId(COMPONENT_ID_EDEFAULT);
				return;
			case DmPackage.INODE__CHILDREN:
				getChildren().clear();
				return;
			case DmPackage.INODE__EVENT_BINDINGS:
				getEventBindings().clear();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case DmPackage.INODE__COMPONENT_ID:
				return COMPONENT_ID_EDEFAULT == null ? componentId != null : !COMPONENT_ID_EDEFAULT.equals(componentId);
			case DmPackage.INODE__PROPERTIES:
				return properties != null;
			case DmPackage.INODE__CHILDREN:
				return children != null && !children.isEmpty();
			case DmPackage.INODE__EVENT_BINDINGS:
				return eventBindings != null && !eventBindings.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (componentId: ");
		result.append(componentId);
		result.append(')');
		return result.toString();
	}

	public String getName() {
		String result = null;
		if (properties != null) {
			Object value = properties.get(NAME_PROPERTY);
			if (value instanceof StringValue) {
				result = properties.lookupString((StringValue)value);
			}
		}
		return result;
	}

	public INode findByNameProperty(final String name) {
		if (name == null) return null;
		INodeVisitor visitor = new INodeVisitor() {
			public Object visit(INode node) {
				if (name.equals(node.getName())) {
					return node;
				}
				return null;
			}
		};
		return (INode) visitPreorder(visitor);	
	}
	
	public Object visitPreorder(INodeVisitor visitor) {
		Object result = visitor.visit(this);
		if (result == null && children != null) {
			for (Iterator iter = children.iterator(); iter.hasNext() &&
			   	 result == null;) {
				INodeImpl curr = (INodeImpl) iter.next();
				result = curr.visitPreorder(visitor);
			}
		}
		return result;
	}
	
	public IPropertyValueSource getPropertyValueSource() {
		if (valueSource == null)
			valueSource = new PropertyValueSource(properties);
		return valueSource;
	}

	public INode getRootContainer() {
		INode result = null;
		EObject container = eContainer();
		if (container instanceof INode) {
			result = ((INode)container).getRootContainer();
		}
		else
			result = this;
		return result;
	}
	
	public IDesignerData getDesignerData() {
		INode rootContainer = getRootContainer();
		IDesignerData result = (IDesignerData) rootContainer.eContainer();
		return result;
	}

	public boolean queryPropertyChange(Object propertyId, Object newValue) {
		// the top-level IPropertyContainer handles name validation
		return true;
	}

	public void propertyChanged(final Object propertyId, IPropertyValue oldValue, IPropertyValue newValue) {
		if (eNotificationRequired()) {
			Notification msg = new PropertyNotification(this, Notification.SET, propertyId);
			eNotify(msg);
			
			// notify any referents
			INodeVisitor visitor = new INodeVisitor() {
				public Object visit(INode node) {
					if (node == INodeImpl.this)
						return null;
					IPropertySource ps = ModelUtils.getPropertySource(node);
					if (ps instanceof IPropertyInformation) {
						IPropertyInformation information = (IPropertyInformation) ps;
						EObject owner = information.getPropertyOwner(propertyId);
						if (owner == INodeImpl.this) {
							IComponentInstance instance = ModelUtils.getComponentInstance(node);
							if (instance != null) {
								instance.firePropertyChanged(propertyId);
							}
						}
					}
					return null;
				}
			};

			IDesignerData designerData = getDesignerData();
			// don't forward these notifications until the model is loaded,
			// otherwise the load of the model itself triggers all sorts of
			// 'missing component' messages
			if (designerData != null && ComponentHelper.getComponentHelper(designerData) != null) {
				designerData.visitNodes(visitor);
			}
		}
		
		if (INode.NAME_PROPERTY.equals(propertyId)) {
			updateNodeReferences(oldValue, newValue);
		}
	}
	
	private void releaseMacrosAndLocalizedStrings(IPropertyContainer properties,
					final ILocalizedStringBundle bundle, final IMacroStringTable macros) {
		properties.visitProperties(new IPropertyVisitor() {
			public Object visit(IPropertyContainer container, EStringToIPropertyValueMapEntryImpl entry) {
				IPropertyValue value = entry.getTypedValue();
				if (value.hasStringValue()) {
					StringValue stringValue = value.getStringValue();
					if (stringValue.isLocalized() && bundle != null) {
						bundle.removeLocalizedStringAllLanguages(stringValue.getValue());
					}
					else if (stringValue.isMacro() && macros != null) {
						macros.getStringMacros().removeKey(stringValue.getValue());
					}
				}
				
				return null;
			}
		});
	}
	
	private void releaseMacrosAndLocalizedStrings(INode node, INode notifierNode) {
		IPropertyContainerImpl notifierProperties = (IPropertyContainerImpl) notifierNode.getProperties();
		final ILocalizedStringBundle bundle = notifierProperties.getBundle();
		final IMacroStringTable macros = notifierProperties.getMacros();
		// no notification is given for children, so visit child nodes
		node.visitPreorder(new INodeVisitor() {
			public Object visit(INode node) {
				releaseMacrosAndLocalizedStrings(node.getProperties(), bundle, macros);
				return null;
			}
		});
	}
	
	private void updateNodeReferences(IPropertyValue oldValue, IPropertyValue newValue) {
		
		// No action needed if node had no name, there couldn't be references
		// to it.
		if (oldValue == null || oldValue.getStringValue() == null)
			return;
		
		final String oldName = oldValue.getStringValue().getValue();
		String newTmp = null;
		if (newValue != null && newValue.hasStringValue() && newValue.getStringValue().getValue().length() > 0)
			newTmp = newValue.getStringValue().getValue();
		final String newName = newTmp;
	
		final ReferencePropertyVisitor propertyVisitor = new ReferencePropertyVisitor(newName, oldName);
		
		INodeVisitor nodeVisitor = new INodeVisitor() {
			public Object visit(INode node) {
				propertyVisitor.setNode((INodeImpl) node);
				node.getProperties().visitProperties(propertyVisitor);
				return null;
			}
		};
		
		
		IDesignerData designerData = this.getDesignerData();
		if (designerData == null)
			return;
		designerData.visitNodes(nodeVisitor);
	}

	public String validateName(String newName) {
		String result = null;
		if (!NamePropertySupport.isLegalName(newName)) {
			result = NamePropertySupport.illegalNameMessage(newName);
		}
		else {
			INode foundNode = getRootContainer().findByNameProperty(newName);
			if (foundNode != null && foundNode != this) {
				result = NamePropertySupport.duplicateNameMessage(newName);				
			}
		}
		return result;
	}
	
	void ensureValidName() {
		if (validateName(getName()) != null) {
			IDesignerDataModel designerModel = getDesignerData().getDesignerDataModel();
			String name = NamePropertySupport.generateDefaultName(designerModel, getComponent());
			StringValue sv = new StringValue(StringValue.LITERAL, name);
			getProperties().set(NAME_PROPERTY, sv, true);
		}
	}
	
	static void ensureValidNames(INodeImpl node) {
		node.ensureValidName();
		for (Iterator iter = node.getChildren().iterator(); iter.hasNext();) {
			INodeImpl child = (INodeImpl) iter.next();
			child.ensureValidName();
		}
	}

	public void eNotify(Notification notification) {
		// Hook notification to detect when children are added or removed.
		// When added we ensure each child gets a unique name.
		// When removed we clear any reference properties
		int eventType = notification.getEventType();
		Object feature = notification.getFeature();
	
		if (feature instanceof EStructuralFeature) {
			EStructuralFeature sf = (EStructuralFeature) feature;
			if (sf.getFeatureID() == DmPackage.INODE__CHILDREN) {
				switch (eventType) {
				case Notification.ADD:
					ensureValidNames((INodeImpl) notification.getNewValue());
					break;
					
				case Notification.ADD_MANY:
				{
					EList items = (EList) notification.getNewValue();
					for (Iterator iter = items.iterator(); iter.hasNext();) {
						ensureValidNames((INodeImpl) iter.next());
					}
					break;
				}
				
				case Notification.REMOVE:
				{
					INode node = (INode) notification.getOldValue();
					IPropertyValue oldValue = (IPropertyValue) node.getProperties().getProperties().get(INode.NAME_PROPERTY); 
					updateNodeReferences(oldValue, null);
					releaseMacrosAndLocalizedStrings(node, (INode) notification.getNotifier());
					break;
				}
				
				case Notification.REMOVE_MANY:
				{
					List items = (List) notification.getOldValue();
					for (Iterator iter = items.iterator(); iter.hasNext();) {
						INode node = (INode) iter.next();
						IPropertyValue oldValue = (IPropertyValue) node.getProperties().getProperties().get(INode.NAME_PROPERTY); 
						updateNodeReferences(oldValue, null);
						releaseMacrosAndLocalizedStrings(node, (INode) notification.getNotifier());
					}
					break;
				}
				}
			}
		}
		super.eNotify(notification);
	}

	public boolean eNotificationRequired() {
		// We need notification to implement component reference
		// properties. By default notification is dependent on adapters.
		// In real scenarios we'd always have adapters, but test code does not.
		return true;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	public boolean isConfigured() {
		return configured;
	}


} //INodeImpl
