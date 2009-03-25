/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.component.property.IPropertyDescriptorExtensions;
import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.emf.dm.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IProperty Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IPropertyContainerImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated NOT
 */
public class IPropertyContainerImpl extends EObjectImpl implements IPropertyContainer, IInternalPropertyChangeListener {
	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap properties = null;
	
	private EObject owner;
	private IPropertyValueSource valueSource;
	private boolean validateNameProperty;
	private IInternalPropertyChangeListener propertyListener;
	private Object id; // id for this container, if it's a compound property.
	private String path; // full property path to this container

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IPropertyContainerImpl() {
		super();
	}

	public Object clone() {
		IPropertyContainerImpl result = null;
		try {
			result = (IPropertyContainerImpl) super.clone();
			result.id = null;
			result.owner = null;
			result.validateNameProperty = false;
			result.propertyListener = null;
			
		} catch (CloneNotSupportedException e) {
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.eINSTANCE.getIPropertyContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EMap getProperties() {
		if (properties == null) {
			PropertyMap pm = new PropertyMap(this,
					DmPackage.eINSTANCE.getEStringToIPropertyValueMapEntry(), 
					EStringToIPropertyValueMapEntryImpl.class, this, DmPackage.INODE__PROPERTIES);
			pm.setPropertyChangeListener(this);
			properties = pm;
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case DmPackage.IPROPERTY_CONTAINER__PROPERTIES:
					return ((InternalEList)getProperties()).basicRemove(otherEnd, msgs);
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
			case DmPackage.IPROPERTY_CONTAINER__PROPERTIES:
				return getProperties();
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
			case DmPackage.IPROPERTY_CONTAINER__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection)newValue);
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
			case DmPackage.IPROPERTY_CONTAINER__PROPERTIES:
				getProperties().clear();
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
			case DmPackage.IPROPERTY_CONTAINER__PROPERTIES:
				return properties != null && !properties.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	public Object get(String id) {
		Object result = null;
		IPropertyValue pv = (IPropertyValue) getProperties().get(id);
		if (pv != null) {
			result = pv.getValue();
		}
		return result;
	}
	
	ILocalizedStringBundle getBundle() {
		ILocalizedStringBundle result = null;
		IDesignerData dd = getDesignerData();
		if (dd != null)
			result = dd.getStringBundle();
		return result;
	}
	
	IMacroStringTable getMacros() {
		IMacroStringTable result = null;
		IDesignerData dd = getDesignerData();
		if (dd != null)
			result = dd.getMacroTable();
		return result;
	}
/*	
	IPropertyValue createPropertyValueForObject_Deleteme(Object obj, Object propertyID) {
		IPropertyValue result = null;
		if (obj instanceof IPropertyValue) {
			result = (IPropertyValue) obj;
		}
		else if (obj instanceof String) {
			result = DmFactory.eINSTANCE.createIPropertyValue();
			result.setStringValue(new StringValue(StringValue.LITERAL, (String)obj));
		}
		else if (obj instanceof List) {
			result = createSequenceForProperty();
			// There are cases with property sheets were it sets a compound
			// value to itself. To make this work straightforwardly we need to
			// copy the items to a separate list. This both avoids
			// self-referential problems and causes new IPropertyValue objects
			// to be constructed.
			ArrayList safeCopy = new ArrayList();
			safeCopy.addAll((List)obj);
			for (Iterator iter = safeCopy.iterator(); iter.hasNext();) {
				IPropertyValue pv = createPropertyValueForObject(iter.next(), null);
				Check.checkState(pv != null);
				result.getSequenceValue().add(pv);
			}
		}
		else if (obj instanceof ISequencePropertySource) {
			ISequencePropertySource sps = (ISequencePropertySource) obj;
			result = createPropertyValueForObject(sps.getSequence(), propertyID);
		}
		else if (obj instanceof IPropertySource) {
			result = createPropertyContainerForProperty(propertyID);
			result.getCompoundValue().setFromPropertySource((IPropertySource)obj);
		}
		Check.checkState(result != null);
		return result;
	}
*/
	// When there is an existing string value for a property
	// we preserve the value type if it is literal, localized, or macro.
	// This is for 2-way support, so we don't change the user's code style
	// out from under them.
	// Also, for localized strings we can't just delete the value from
	// the map if we're intending to update the value for the current
	// language, as deleting the property map entry has the side effect
	// of deleting all language values.
	// Return value == oldValue if the string is a localized or macro
	// value whose macro is retained
	// When oldValue is return, i.e. the value is conserved, then newValue is
	// released and should no longer be used.
	public StringValue conserveStringType(StringValue oldValue, StringValue newValue) {
		StringValue result = newValue;
		if (oldValue != null) {
	
			boolean transferFormat = !oldValue.isReference();
			
			if (transferFormat) {
				String newString = lookupString(newValue);
				if (oldValue.isLocalized()) {
					ILocalizedStringBundle bundle = getBundle();
					// no bundle, means this is probably designer data properties, should
					// not be a localized string here
					if (bundle != null) {
						bundle.updateLocalizedStringDefault(oldValue.getValue(), newString);
						result = oldValue;
						releaseStringValue(newValue);
					}
				}
				else if (oldValue.isMacro()) {
					IMacroStringTable macros = getMacros();
					macros.updateMacro(oldValue, newString);
					result = oldValue;
					releaseStringValue(newValue);
				}
				else { // literal
					// force new value to be a literal
					if  (!newValue.isLiteral())
						result = createLiteral(newString);
				}
			}
		}
		return result;
	}

	public IPropertyValue set(String id, StringValue value) {
		return set(id, value, false);
	}
	
	public IPropertyValue set(String id, StringValue value, boolean overrideCurrentState) {
		IPropertyValue result = null;
		if (value != null) {	    
			// We don't just blindly add/update with the new value.
			// Rather, we preserve the original string type, updating
			// with the new value.
			IPropertyValue pv = (IPropertyValue) getProperties().get(id);
			boolean doNewValue = false;
			String oldString = null;
			if (pv != null && pv.hasStringValue() && 
				((pv.getStringValue().getType() == value.getType()) || !overrideCurrentState)) {
				oldString = lookupString(pv.getStringValue());
				StringValue newStringValue = conserveStringType(pv.getStringValue(), value);
				if (newStringValue != pv.getStringValue()) {
					doNewValue = true;
					value = newStringValue;
				}
			}
			else 
				doNewValue = true;
						
			if (doNewValue) {
				pv = DmFactory.eINSTANCE.createIPropertyValue();
				pv.setStringValue(value);
				getProperties().put(id, pv);
				result = pv;
			}
			else {
				// make a "fake" old value for notification, since the name itself is not
				// changing. If needed we could make a new "localized property changed" event
				IPropertyValue oldPV = null;
				if (oldString != null) {
					oldPV = DmFactory.eINSTANCE.createIPropertyValue();
					oldPV.setStringValue(createLiteral(oldString));
				}
				propertyListener.propertyChanged(id, oldPV, pv);
				result = pv;
			}
		}
		else
			getProperties().remove(id);
		return result;
	}

	public IPropertyValue set(String id, IPropertyContainer value) {
		IPropertyValue result = null;
		if (value != null) {
			IPropertyValue pv = (IPropertyValue) getProperties().get(id);
			boolean doAdd = false;
			if (pv == null) {
				doAdd = true;
				pv = DmFactory.eINSTANCE.createIPropertyValue();
			}
			
			pv.getCompoundValue().setFromPropertyContainer(value);
			
			if (doAdd)
				getProperties().put(id, pv);
			result = pv;
		}
		else
			getProperties().remove(id);
		return result;
	}

	IPropertyValue createListPropertyValue(Object propertyID, List l) {
		IPropertyValue result = createSequenceForProperty(propertyID);
		// There are cases with property sheets were it sets a compound
		// value to itself. To make this work straightforwardly we need to
		// copy the items to a separate list. This both avoids
		// self-referential problems and causes new IPropertyValue objects
		// to be constructed.
		ArrayList safeCopy = new ArrayList();
		safeCopy.addAll(l);
		for (Iterator iter = safeCopy.iterator(); iter.hasNext();) {
			Object listItem = iter.next();
			Check.checkState(listItem instanceof IPropertyValue);
			IPropertyValue pv = (IPropertyValue) listItem;
			result.getSequenceValue().add(pv);
		}
		return result;
	}

	public IPropertyValue set(String id, List value) {
		IPropertyValue result = null;
		if (value != null) {
			IPropertyValue pv = createListPropertyValue(id, value);		
			getProperties().put(id, pv);
			result = pv;
		}
		else
			getProperties().remove(id);
		return result;
	}

	public void setFromPropertySource(IPropertySource source) {
		IPropertyDescriptor[] pdList = source.getPropertyDescriptors();
		if (pdList != null) {
			for (int i = 0; i < pdList.length; i++) {
				IPropertyDescriptor pd = pdList[i];
				
				String id = pd.getId().toString();
				Object value = source.getPropertyValue(id);
				if (value != null) {
					if (value instanceof IPropertySource) {
						IPropertyValue pv = createPropertyContainerForProperty(id);
						pv.getCompoundValue().setFromPropertySource((IPropertySource)value);
						getProperties().put(id, pv);
					}
					else if (value instanceof List) {
						IPropertyValue pv = createSequenceForProperty(id);
						pv.getSequenceValue().addAll((List)value);
						getProperties().put(id, pv);
					}
					else if (value != null) {
						boolean isLocalized = false;
						if (pd instanceof IPropertyDescriptorExtensions) {
							IPropertyDescriptorExtensions pde = (IPropertyDescriptorExtensions) pd;
							isLocalized = pde.isPropertyLocalizable();
						}
						StringValue sv;
						if (isLocalized) {
							sv = createLocalized(value.toString());
						} else {
							sv = createLiteral(value.toString());
						}
						set(id, sv, false);
					}
					else {
						// can't accept this kind of value
					}
				}
			}
		}
	}
	
	public void reset(String id) {
		// A localized property has a value per language.
		// Resetting for one language does not reset for other languages.
		// Therefore, we must check for and handle localized properties
		// separately.
		boolean doRemoveKey = true;
		IPropertyValue pv = (IPropertyValue) getProperties().get(id);
		if (pv != null) {
			ILocalizedStringBundle bundle = getBundle();
			if (pv.hasStringValue()) {
				StringValue sv = pv.getStringValue();
				if (sv.isLocalized()) {
					// if the bundle has only one language table its ok to just delete the entry
					if (bundle.getLocalizedStringTables().size() > 1) {
						bundle.updateLocalizedStringDefault(sv.getValue(), null);
						// the property changed, but since the string is elsewhere it can't be
						// reflected in the property value (without an API change).
						propertyChanged(id, pv, pv);
						doRemoveKey = false;
					}
				}
			}
		}
		
		if (doRemoveKey) {
			getProperties().removeKey(id);
		}
	}
	
	public StringValue createLiteral(String value) {
		StringValue result = new StringValue(StringValue.LITERAL, value);
		return result;
	}

	public StringValue createLocalized(String value) {
		StringValue result = null;
		ILocalizedStringBundle bundle = getBundle();
		if (bundle != null)
			result = bundle.addLocalizedStringDefault(value);
		return result;
	}

	public StringValue createMacro(String value) {
		StringValue result = null;
		IMacroStringTable macroTable = getMacros();
		if (macroTable != null)
			result = macroTable.addMacro(value);
		return result;
	}

	public StringValue createReference(String value) {
		return new StringValue(StringValue.REFERENCE, value);
	}

	public IPropertyValue createPropertyContainerForProperty(Object propertyId) {
		// assign an internal id, for debugging. this container won't be assigned as
		// a real value, but is being used for some temporary purpose.
		if (propertyId == null)
			propertyId = "<temporary>";
		IPropertyValueImpl result = (IPropertyValueImpl) DmFactory.eINSTANCE.createIPropertyValue();
		IPropertyContainerImpl container = (IPropertyContainerImpl) DmFactory.eINSTANCE.createIPropertyContainer();
		container.setPropertyChangeListener(propertyListener);
		container.setID(propertyId);
		String childPath;
		if (path != null) {
			childPath = path + "." + propertyId.toString();
		}
		else {
			childPath = propertyId.toString();
		}
		container.setPath(childPath);
		container.setOwner(getOwner());
		result.setCompoundValue(container);
		return result;
	}

	public IPropertyValue createSequenceForProperty(Object propertyId) {
		IPropertyValueImpl result = (IPropertyValueImpl) DmFactory.eINSTANCE.createIPropertyValue();
		if (propertyId == null)
			propertyId = "<temporary>";
		String childPath;
		if (path != null) {
			childPath = path + "." + propertyId.toString();
		}
		else {
			childPath = propertyId.toString();
		}
		result.initSequenceValue(this, childPath, propertyId);
		return result;
	}

	public IPropertyValueSource getPropertyValueSource() {
		if (valueSource == null) {
			valueSource = new PropertyValueSource(this);
		}
		return valueSource;
	}
	
	public EObject getOwner() {
		return owner;
	}
	
	public void setOwner(EObject owner) {
		this.owner = owner;
	}
	
	IDesignerData getDesignerData() {
		IDesignerData result = null;
		if (owner instanceof IDesignerData) {
			result = (IDesignerData)owner;
		}
		else if (owner instanceof INode) {
			result = ((INode)owner).getDesignerData();
		}
		return result;
	}

	public IDesignerDataModel getDesignerDataModel() {
		IDesignerData designerData = getDesignerData();
		if (designerData != null)
			return designerData.getDesignerDataModel();
		
		return null;
	}
	
	void setValidateNameProperty(boolean validate) {
		validateNameProperty = validate;
	}
	
	void setPropertyChangeListener(IInternalPropertyChangeListener listener) {
		this.propertyListener = listener;
	}
	
	void setID(Object id) {
		this.id = id;
	}
	
	void setPath(String path) {
		this.path = path;
	}
	
	public boolean queryPropertyChange(Object propertyId, Object newValueObj) {
		if (!(newValueObj instanceof IPropertyValue))
			return false;
		IPropertyValue newValue = (IPropertyValue) newValueObj;
		boolean okToChange = true;
		// Ensure no other node has this name already
		if (validateNameProperty && INode.NAME_PROPERTY.equals(propertyId)) {
			String proposedName = lookupString(newValue.getStringValue());
			if (proposedName != null) {
				IDesignerData dd = getDesignerData();
				if (dd != null) {
					INode otherNode = dd.findByNameProperty(proposedName);
					if (otherNode != null && otherNode != owner) {
						okToChange = false;
					}
				}
			}
			else
				okToChange = false;
		}
		return okToChange;
	}
	
	public String findPropertyIDForValue(IPropertyValue pv) {
		if (properties == null) return null;
		String result = null;
		for (Iterator iter = properties.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (entry.getValue() == pv) {
				result = (String) entry.getKey();
				break;
			}
		}
		return result;
	}

	public void propertyChanged(Object propertyId, IPropertyValue oldValue, IPropertyValue newValue) {
		if (propertyListener != null) {
			// When child properties change we modify the notification
			// to say this property changed, not the child.
			// Reporting an unqualified child name is meaningless, and
			// creating detailed information (i.e. qualified path) is
			// unneeded by any client code.
			propertyListener.propertyChanged(id != null? id : propertyId, oldValue, newValue);
		}
	}

	public void setFromPropertyContainer(IPropertyContainer container) {
		getProperties().clear();
		for (Iterator iter = container.getProperties().entrySet().iterator(); iter.hasNext();) {
			EStringToIPropertyValueMapEntryImpl element = (EStringToIPropertyValueMapEntryImpl) iter.next();
			String id = element.getTypedKey();
			IPropertyValue srcValue = element.getTypedValue();
			if (srcValue != null) {
				if (srcValue.hasStringValue())
					set(id, srcValue.getStringValue(), false);
				else if (srcValue.hasCompoundValue())
					set(id, srcValue.getCompoundValue());
				else if (srcValue.hasSequenceValue())
					set(id, srcValue.getSequenceValue());
			}
		}
	}

	public Object visitProperties(IPropertyVisitor visitor) {
		if (properties == null) return null;
		
		Object result = null;
		for (Iterator iter = properties.entrySet().iterator(); iter.hasNext();) {
			EStringToIPropertyValueMapEntryImpl element = (EStringToIPropertyValueMapEntryImpl) iter.next();
			if (element != null) {
				result = visitor.visit(this, element);
				// the visitor cannot itself reset a property, but
				// if it sets the value to null we will reset on its behalf
				boolean didReset = false;
				if (element.getValue() == null) {
					iter.remove();
					didReset = true;
				}
				if (result != null)
					break;
				
				if (!didReset && element.getTypedValue().hasCompoundValue()) {
					result = element.getTypedValue().getCompoundValue().visitProperties(visitor);
					if (result != null)
						break;
				}
			}
		}
		return result;
	}

	public String lookupString(StringValue stringValue) {
		String result = null;
		if (stringValue != null) {
			result = stringValue.getValue();
			IDesignerData dd = getDesignerData();
			if (dd != null) {
				switch (stringValue.getType()) {
				case StringValue.LOCALIZED:
					result = dd.getStringBundle().getLocalizedStringDefault(result);
					break;
				case StringValue.MACRO:
					result = (String) dd.getMacroTable().getStringMacros().get(result);
					break;
				}
			}
		}
		return result;
	}
	
	public void releaseStringValue(StringValue value) {
		// remove localized strings and string macros
		ILocalizedStringBundle bundle = getBundle();
		IMacroStringTable macros = getMacros();
		if (value.isLocalized() && bundle != null) {
			bundle.removeLocalizedStringAllLanguages(value.getValue());
		}
		else if (value.isMacro() && macros != null) {
			macros.getStringMacros().removeKey(value.getValue());
		}
	}

	public void releasePropertyValue(IPropertyValue value) {
		// remove localized strings and string macros
		if (value.hasStringValue()) {
			releaseStringValue(value.getStringValue());
		}
		else if (value.hasCompoundValue()) {
			IPropertyContainer compoundValue = value.getCompoundValue();
			compoundValue.getProperties().clear();
		}
		else if (value.hasSequenceValue()) {
			// A sequence property is being reset. All localized elements should be removed
			EList list = value.getSequenceValue();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				IPropertyValue element = (IPropertyValue) iter.next();
				releasePropertyValue(element);
			}				
		}
	}

	public String getPropertyPath() {
		return path;
	}
}
