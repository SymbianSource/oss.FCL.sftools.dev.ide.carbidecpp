/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/
package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.component.property.ICompoundPropertyValueConverter;
import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.impl.PropertyNotification;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.*;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.*;

import java.text.MessageFormat;
import java.util.Iterator;

/**
 * Adapter for INode instances. Implements the adapters: IComponentInstance
 * IPropertySource IPropertyInformation IComponentEventDescriptorProvider
 * 
 */
public class ComponentInstanceAdapter extends AdapterImpl implements
		IComponentInstance, IPropertySource2, IPropertyInformation, 
		IComponentEventDescriptorProvider {

	static final EObject[] NO_CHILDREN = new EObject[0];
	
	IComponent component; // see getComponent()
	boolean invalidComponent; // true if component is flagged invalid. We'll return the ID, but not the IComponent
	boolean hasLoggedMissingComponent; // so we don't emit the same message to the log redundantly
	private boolean reentrantPropertySource; 
	
		// both propertySource and propertyInfoSource are
		// lazily initializeded in initPropertySource
	IPropertySource propertySource;
	IPropertyInformation propertyInfoSource;

		// lazily provided
	IComponentEventDescriptorProvider eventDescriptorProvider;
	ComponentEventDescriptorProviderFactory eventDescriptorProviderFactory;
	
	ListenerList<IComponentInstanceChildListener> childListeners;
	ListenerList<IComponentInstancePropertyListener> propertyListeners;
	ListenerList<IEventBindingListener> eventBindingListeners;
	
	public ComponentInstanceAdapter() {		
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "<ComponentInstanceAdapter: " + getName() + ": " + getComponentId() + ">"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    } 
    
	INode getNode() {
		return (INode) target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	public boolean isAdapterForType(Object type) {
		return type.equals(IComponentInstance.class)
				|| type.equals(IPropertySource.class)
				|| type.equals(IPropertySource2.class)
				|| type.equals(IPropertyInformation.class)
				|| type.equals(IComponentEventDescriptorProvider.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#setTarget(org.eclipse.emf.common.notify.Notifier)
	 */
	public void setTarget(Notifier newTarget) {
		if (newTarget != null) {
			if (newTarget instanceof INode) {
				super.setTarget(newTarget);
			} else
				throw new IllegalArgumentException();
		} else {
			super.setTarget(null);
		}
		resetPropertySource();
		
		if (target != null) {
			// Check if the component has its own IComponentInstancePropertyListener
			// implementation. If so hook it up now.
			Adapter adapter = EcoreUtil.getRegisteredAdapter((EObject)target, IComponentInstancePropertyListener.class);
			if (adapter != null) {
		    	 addPropertyListener((IComponentInstancePropertyListener)adapter);
		    }
			adapter = EcoreUtil.getRegisteredAdapter((EObject) target, IComponentInstanceChildListener.class);
			if (adapter != null) {
				addChildListener((IComponentInstanceChildListener) adapter);
			}
		}
	}
	
	void resetPropertySource() {
		propertySource = null;
		propertyInfoSource = null;
	}

	void initPropertySource() {
		if (propertySource != null)
			return;
		
		// detect recursion on same thread, thread-safety not the issue here
		// e.g. extension property script that tries to access a property value
		if (reentrantPropertySource)
			throw new IllegalStateException();
	
		try {
			reentrantPropertySource = true;
			INode node = getNode();
			if (node != null) {
				IComponent component = node.getComponent();
				if (component != null) {
					IPropertySourceProvider psp;
					psp = (IPropertySourceProvider) component
					.getAdapter(IPropertySourceProvider.class);
					if (psp != null) {
						propertySource = psp.getPropertySource(node.getProperties()
								.getPropertyValueSource());
						if (propertySource instanceof IPropertyInformation) {
							propertyInfoSource = (IPropertyInformation) propertySource;
						}
					}
				} 
				else { 
					String name = getName();
					if (name != null && 
							!name.equals(GEFClipboardEditingDomain.CLIPBOARD_ROOT) && 
							!hasLoggedMissingComponent) {
						Logging.log(SymbianPlugin.getDefault(),
								Logging.newStatus(
										SymbianPlugin.getDefault(),
										IStatus.ERROR,
										MessageFormat.format(
												Messages.getString("ComponentInstanceAdapter.MissingComponent"), //$NON-NLS-1$
												new Object[] {name, getComponentId() })));
						hasLoggedMissingComponent = true;
					}
				}
			}
		}
		finally {
			reentrantPropertySource = false;
		}
	}

	public String getComponentId() {
		return getNode().getComponentId();
	}

	public void setComponentId(String componentID) {
		getNode().setComponentId(componentID);
	}

	public IComponent getComponent() {
		// once an instance has been removed the component won't be accessible. Cache it so
		// it will still be accessible
		if (component == null && !invalidComponent) {
			component = getNode().getComponent();
		}
		return component;
	}

	/**
	 * Use to mark the component as invalid, without clearing out
	 * the component ID. For example, if a base component is missing we
	 * don't want to use the component, but we destroy our knowledge of 
	 * what component is needed.
	 */
	void markInvalidComponent() {
		invalidComponent = true;
		component = null;
	}

	public String getName() {
		return getNode().getName();
	}

	public String validateProposedName(String newName) {
		return getNode().validateName(newName);
	}

	public EObject getParent() {
		return getNode().eContainer();
	}

	public EObject[] getChildren() {
		EObject[] result = NO_CHILDREN;
		EList children = getNode().getChildren();
		if (children.size() > 0) {
			result = (EObject[]) children.toArray(new EObject[children.size()]);
		}
		return result;
	}

	public Object getEditableValue() {
		initPropertySource();
		return propertySource != null ? propertySource.getEditableValue()
				: null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		initPropertySource();
		return propertySource != null ? propertySource.getPropertyDescriptors()
				: new IPropertyDescriptor[0];
	}

	public Object getPropertyValue(Object id) {
		initPropertySource();
		return propertySource != null ? propertySource.getPropertyValue(id)
				: "???"; //$NON-NLS-1$
	}

	public boolean isPropertySet(Object id) {
		initPropertySource();
		return propertySource != null ? propertySource.isPropertySet(id)
				: false;
	}

	public void resetPropertyValue(Object id) {
		initPropertySource();
		if (propertySource != null)
			propertySource.resetPropertyValue(id);
	}

	public void setPropertyValue(Object id, Object value) {
		initPropertySource();
		if (propertySource != null)
			propertySource.setPropertyValue(id, value);
	}

	public void addChildListener(IComponentInstanceChildListener listener) {
		if (childListeners == null) {
			childListeners = new ListenerList<IComponentInstanceChildListener>();
		}
		childListeners.add(listener);
	}

	public void addPropertyListener(IComponentInstancePropertyListener listener) {
		if (propertyListeners == null) {
			propertyListeners = new ListenerList<IComponentInstancePropertyListener>();
		}
		propertyListeners.add(listener);
	}
	
	public void removeChildListener(IComponentInstanceChildListener listener) {
		if (childListeners != null) {
			childListeners.remove(listener);
		}
	}

	public void removePropertyListener(IComponentInstancePropertyListener listener) {
		if (propertyListeners != null) {
			propertyListeners.remove(listener);
		}
	}
	
	public void fireChildAdded(EObject child) {
		if (childListeners != null) {
			EObject parent = getNode();
			for (IComponentInstanceChildListener l : childListeners) {
				l.childAdded(parent, child);
			}
		}
	}

	void fireChildRemoved(EObject child) {
		if (childListeners != null) {
			EObject parent = getNode();
			for (IComponentInstanceChildListener l : childListeners) {
				l.childRemoved(parent, child);
			}
		}
	}

	void fireChildrenReordered(EObject child) {
		if (childListeners != null) {
			EObject parent = getNode();
			for (IComponentInstanceChildListener l : childListeners) {
				l.childrenReordered(parent);
			}
		}
	}

	public void firePropertyChanged(Object propertyId) {
		if (propertyListeners != null) {
			EObject instance = getNode();
			for (IComponentInstancePropertyListener l : propertyListeners) {
				l.propertyChanged(instance, propertyId);
			}
		}
	}

	public void notifyChanged(Notification msg) {

		// see comment for notifyChildAdapters
		notifyChildAdapters();
		
		// We only care about child and property events. if no
		// listeners then we can ignore all messages.
		if (propertyListeners == null && childListeners == null)
			return;

		int eventType = msg.getEventType();
		Object feature = msg.getFeature();

		// handle property change notification
		if (msg instanceof PropertyNotification) {
			PropertyNotification pn = (PropertyNotification) msg;
			firePropertyChanged(pn.getPropertyId());
		} else if (feature instanceof EStructuralFeature) {
			EStructuralFeature sf = (EStructuralFeature) feature;
			if (sf.getFeatureID() == DmPackage.INODE__CHILDREN) {
				// child was added or removed
				switch (eventType) {
				case Notification.ADD:
					fireChildAdded((EObject) msg.getNewValue());
					break;
				case Notification.ADD_MANY: {
					EList items = (EList) msg.getNewValue();
					for (Iterator iter = items.iterator(); iter.hasNext();) {
						fireChildAdded((EObject) iter.next());
					}
					break;
				}

				case Notification.REMOVE:
					fireChildRemoved((EObject) msg.getOldValue());
					break;

				case Notification.REMOVE_MANY: {
					EList items = (EList) msg.getNewValue();
					for (Iterator iter = items.iterator(); iter.hasNext();) {
						fireChildRemoved((EObject) iter.next());
					}
					break;
				}
				case Notification.MOVE:
					fireChildrenReordered((EObject) msg.getNewValue());
					break;

				default:
					// TODO remove this by enumerating all other cases
					throw new IllegalStateException(
							"unhandled event type of child node"); //$NON-NLS-1$
				}
			}
			else if (sf.getFeatureID() == DmPackage.INODE__EVENT_BINDINGS) {
				// event binding was added or removed
				switch (eventType) {
				case Notification.ADD:
					fireEventBindingAdded((com.nokia.sdt.emf.dm.IEventBinding) msg.getNewValue());
					break;
				case Notification.ADD_MANY: {
					EList items = (EList) msg.getNewValue();
					for (Iterator iter = items.iterator(); iter.hasNext();) {
						fireEventBindingAdded((com.nokia.sdt.emf.dm.IEventBinding) iter.next());
					}
					break;
				}
				
				case Notification.REMOVE:
					fireEventBindingRemoved((com.nokia.sdt.emf.dm.IEventBinding) msg.getOldValue());
					break;
					
				case Notification.REMOVE_MANY: {
					EList items = (EList) msg.getNewValue();
					for (Iterator iter = items.iterator(); iter.hasNext();) {
						fireEventBindingRemoved((com.nokia.sdt.emf.dm.IEventBinding) iter.next());
					}
					break;
				}
				case Notification.MOVE:
				default:
					// not supported
					break;
				}
			}
		}
	}
	
	/** Notify ComponentInstanceAdapters of children whenever
	 * an EMF notification is delivered on the parent. Because
	 * the set of properties can be dynamic we use this to flush
	 * the cached property source
	 */
	public void notifyChildAdapters() {
		EList children = getNode().getChildren();
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			EObject curr = (EObject) iter.next();
			IComponentInstance ci = (IComponentInstance) EcoreUtil.getExistingAdapter(curr, IComponentInstance.class);
			if (ci instanceof ComponentInstanceAdapter) {
				((ComponentInstanceAdapter)ci).parentChanged();
			}
		}
	}
	
	void parentChanged() {
		resetPropertySource();
		resetEventProvider();
	}

	public EObject getEObject() {
		return getNode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.datamodel.adapter.IComponentInstance#getDesignerDataModel()
	 */
	public IDesignerDataModel getDesignerDataModel() {
		IDesignerData designerData = getNode().getDesignerData();
		return designerData != null? designerData.getDesignerDataModel() : null;
	}

	public EObject getRootContainer() {
		INode lastContainer = null;
		EObject curr = getEObject();
		while (curr instanceof INode) {
			INode currNode = (INode) curr;
			IComponent component = currNode.getComponent();
            if (component != null) {
    			IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
    			if (attr != null) {
    				boolean isContainer = attr.getBooleanAttribute(CommonAttributes.IS_LAYOUT_CONTAINER, false);
    				if (isContainer) {
    					lastContainer = currNode;
    				}
    			}
            }
			curr = curr.eContainer();
		}
		return lastContainer;
	}

	public boolean isPropertyResettable(Object id) {
		boolean result = false;
		initPropertySource();
		if (propertySource instanceof IPropertySource2)
			result = ((IPropertySource2)propertySource).isPropertyResettable(id);
		return result;
	}

	public String getPropertyValueSymbol(Object propertyId) {
		String result = null;
		initPropertySource();
		if (propertyInfoSource != null) {
			result = propertyInfoSource.getPropertyValueSymbol(propertyId);
		}
		return result;
	}

	public String getPropertyTypeName(Object propertyId) {
		String result = null;
		initPropertySource();
		if (propertyInfoSource != null) {
			result = propertyInfoSource.getPropertyTypeName(propertyId);
		}
		return result;
	}
	
	public String getPropertyPath() {
		String result = null;
		initPropertySource();
		if (propertyInfoSource != null) {
			result = propertyInfoSource.getPropertyPath();
		}
		return result;
	}
	
	public String getPropertyPath(Object propertyId) {
		String result = null;
		initPropertySource();
		if (propertyInfoSource != null) {
			result = propertyInfoSource.getPropertyPath(propertyId);
		}
		return result;
	}
	
	public EObject getPropertyOwner(Object propertyId) {
		EObject result = null;
		initPropertySource();
		if (propertyInfoSource != null) {
			result = propertyInfoSource.getPropertyOwner(propertyId);
		}
		return result;
	}
	
	public IEventBinding[] getEventBindings() {
		IEventBinding[] result = null;
		EList eventBindings = getNode().getEventBindings();
		if (eventBindings != null && eventBindings.size() > 0) {
			result = new IEventBinding[eventBindings.size()];
			int index = 0;
			for (Iterator iter = eventBindings.iterator(); iter.hasNext();) {
				com.nokia.sdt.emf.dm.IEventBinding binding = (com.nokia.sdt.emf.dm.IEventBinding) iter.next();
				result[index++] = new EventBinding(getNode(), binding);
			}
		}
		return result;
	}
	
	public IEventBinding findEventBinding(String eventID) {
		IEventBinding result = null;
		com.nokia.sdt.emf.dm.IEventBinding binding = getNode().findBinding(eventID);
		if (binding != null) {
			result = new EventBinding(getNode(), binding);
			// don't return bindings that don't have a corresponding
			// event descriptor, e.g. due to component version differences.
			if (result.getEventDescriptor() == null) {
				result = null;
			}
		}
		return result;
	}

	public void addEventBindingListener(IEventBindingListener listener) {
		if (eventBindingListeners == null) {
			eventBindingListeners = new ListenerList<IEventBindingListener>();
		}
		eventBindingListeners.add(listener);
	}

	public void removeEventBindingListener(IEventBindingListener listener) {
		if (eventBindingListeners != null) {
			eventBindingListeners.remove(listener);
		}
	}
	
	private void fireEventBindingAdded(com.nokia.sdt.emf.dm.IEventBinding emfBinding) {
		if (eventBindingListeners != null) {
			INode node = getNode();
			EventBinding binding = new EventBinding(node, emfBinding);
			for (IEventBindingListener l : eventBindingListeners) {
				l.bindingAdded(node, binding);
			}
		}
	}
	
	private void fireEventBindingRemoved(com.nokia.sdt.emf.dm.IEventBinding emfBinding) {
		if (eventBindingListeners != null) {
			INode node = getNode();
			EventBinding binding = new EventBinding(node, emfBinding);
			for (IEventBindingListener l : eventBindingListeners) {
				l.bindingRemoved(node, binding);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IComponentEventDescriptorProvider#getEventDescriptors()
	 */
	public IEventDescriptor[] getEventDescriptors() {
		IEventDescriptor[] result = null;
		getComponentEventDescriptorProvider();
		if (eventDescriptorProvider != null) {
			result = eventDescriptorProvider.getEventDescriptors();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IComponentEventDescriptorProvider#findEventDescriptor(java.lang.String)
	 */
	public IEventDescriptor findEventDescriptor(String id) {
		IEventDescriptor result = null;
		getComponentEventDescriptorProvider();
		if (eventDescriptorProvider != null) {
			result = eventDescriptorProvider.findEventDescriptor(id);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IComponentEventDescriptorProvider#getDefaultEventIndex()
	 */
	public int getDefaultEventIndex() {
		int result = -1;
		getComponentEventDescriptorProvider();
		if (eventDescriptorProvider != null) {
			result = eventDescriptorProvider.getDefaultEventIndex();
		}
		return result;
	}

	private void getComponentEventDescriptorProvider() {
		// Don't bother initializing any event support if there's no component
		if (component != null && eventDescriptorProvider == null) {
			if (eventDescriptorProviderFactory == null)
				eventDescriptorProviderFactory = new ComponentEventDescriptorProviderFactory(SymbianPlugin.getDefault());
			eventDescriptorProvider = (IComponentEventDescriptorProvider) eventDescriptorProviderFactory.getAdapter(
					getTarget(), IComponentEventDescriptorProvider.class);
		}
	}
	
	private void resetEventProvider() {
		eventDescriptorProvider = null;
		// TODO: also remove bindings referencing obsolete events
	}

	public void updatePropertySource() {
		resetPropertySource();
	}

	public boolean isConfigured() {
		return getNode().isConfigured();
	}
	
	public void refresh() {
		resetPropertySource();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.property.IPropertyInformation#getCompoundPropertyValueConverter()
	 */
	public ICompoundPropertyValueConverter getCompoundPropertyValueConverter() {
		return null;
	}
}
