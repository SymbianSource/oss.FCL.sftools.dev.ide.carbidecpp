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
package com.nokia.sdt.uidesigner.events;

import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.Messages;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.*;

/**
 * Data model to support event editing. It provides
 * a unified view of event descriptors and bindings.
 * It also supports multiple selection, building
 * a model than reflects whether the multiple items
 * have common events and bindings. 
 *
 */
public class EventModel {

	private IComponentInstance[] instances;
	// map of event ID string -> EventItem
	private Map<String, EventItem> eventItems;
	// map of category string -> CategoryItem
	private Map<String, CategoryItem> categoryItems;
	// category string for events that had no category
	private String miscCategory = Messages.getString("EventModel.0"); //$NON-NLS-1$
		
	public EventModel(IComponentInstance[] instances) {
		Check.checkArg(instances != null && instances.length > 0);
		this.instances = instances;
		initializeEventItems();
		initializeCategories();		
	}
	
	public IComponentInstance[] getInstances() {
		return instances;
	}
	
	public class EventItem {
		public IEventDescriptor descriptor;
		public String category;
		// this will be non-null for single selection where
		// a binding exists, or for multiple selection where
		// all selected items have the same binding. In the
		// multiple selection case there is still a distinct
		// binding object for each selected object, but all
		// the display and symbol values are all the same.
		public IEventBinding binding;
		
		public String toString() {
			StringBuffer result = new StringBuffer(descriptor.getId());
			if (binding != null) {
				result.append("[");
				result.append(binding.getHandlerName());
				result.append("]");
			}
			return result.toString();
		}
	}
	
	public class CategoryItem {
		public String category;
		public EventItem[] events;
	}
	
	public EventItem[] getEventItems() {
		EventItem[] result = null;
		if (eventItems != null) {
			result = (EventItem[]) eventItems.values().toArray(new EventItem[eventItems.size()]);
		}
		return result;
	}
	
	public CategoryItem[] getCategoryItems() {
		CategoryItem[] result = null;
		if (categoryItems != null) {
			result = (CategoryItem[]) categoryItems.values().toArray(new CategoryItem[categoryItems.size()]);
		}
		return result;
	}
	
	public EventItem findEventItem(String eventID) {
		EventItem result = null;
		if (eventItems != null) {
			result = eventItems.get(eventID);
		}
		return result;
	}
	
	/**
	 * Given an event get its parent category item. Events
	 * that did not have a category are placed under the "Misc" category
	 * @param eventItem
	 * @return
	 */
	public CategoryItem findCategoryItem(EventItem eventItem) {
		CategoryItem result = null;
		if (categoryItems != null) {
			result = categoryItems.get(eventItem.category);
		}
		return result;
	}
	
	private void initializeEventItems() {
		// Build a list of all the event descriptors
		// For multiple selection we include events common across all
		// instances. Our idea of the "same event" relies on the uniqueness
		// of event IDs. For example, two different components may have
		// a "selected" event. They will only be considered the same if
		// the internal ID is the same. This allows the user visible name
		// to be the same even if the internal details, e.g. method signature
		// is different. The component author would need to consciously assign
		// the same ID to two different event definitions, and should only
		// do so if they are really equivalent.
		eventItems = instanceEventItems(instances[0]);
		if (eventItems != null) {
			// if multiple selection, remove descriptors not common to all
			for (int i = 1; i < instances.length; i++) {
				Map currItems = instanceEventItems(instances[i]);
				if (currItems != null) {
					for (Iterator iter = eventItems.entrySet().iterator(); iter.hasNext();) {
						Map.Entry entry = (Map.Entry) iter.next();
						EventItem currItem = (EventItem) currItems.get(entry.getKey());
						if (currItem != null) {
							// check if existing binding should be maintained
							// or cleared
							EventItem mainItem = (EventItem) entry.getValue();
							if (mainItem.binding != null &&
								(currItem.binding == null ||
								 !mainItem.binding.isSameHandler(currItem.binding))) {
								mainItem.binding = null;
							}
						}
						else {
							iter.remove();
						}
					}
				}
				else {
					eventItems.clear();
					break;
				}
			}
		}
	}
	
	private void initializeCategories() {
		if (eventItems != null) {
			// first make a list of all the event items per category
			HashMap listMap = new HashMap();
			for (Iterator iter = eventItems.values().iterator(); iter.hasNext();) {
				EventItem eventItem = (EventItem) iter.next();
				ArrayList list = (ArrayList) listMap.get(eventItem.category);
				if (list == null) {
					list = new ArrayList();
					listMap.put(eventItem.category, list);
				}
				list.add(eventItem);
			}
			// Now make CategoryItems from temporary list
			categoryItems = new HashMap();
			for (Iterator iter = listMap.entrySet().iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				CategoryItem item = new CategoryItem();
				item.category = (String) entry.getKey();
				ArrayList list = (ArrayList) entry.getValue();
				item.events = (EventItem[]) list.toArray(new EventItem[list.size()]);
				categoryItems.put(item.category, item);
			}
		}
	}
	
	private Map<String, EventItem> instanceEventItems(IComponentInstance instance) {
		// temporary component editor instances should not expose any events to the UI
		IComponentEditor componentEditor = Adapters.getComponentEditor(instance.getEObject());
		if ((componentEditor != null) && componentEditor.isTemporaryObject())
			return null;

		Map<String, EventItem> result = null;
		IComponentEventDescriptorProvider edp = ModelUtils.getComponentEventDescriptorProvider(instance.getEObject());
		IEventDescriptor[] descriptors = edp.getEventDescriptors();
		if (descriptors != null && descriptors.length > 0) {
			result = new HashMap<String, EventItem>();
			for (int i = 0; i < descriptors.length; i++) {
				EventItem item = new EventItem();
				item.descriptor = descriptors[i];
				item.category = descriptors[i].getCategory();
				if (TextUtils.strlen(item.category)== 0) {
					item.category = miscCategory;
				}
				item.binding = instance.findEventBinding(descriptors[i].getId());
				result.put(descriptors[i].getId(), item);
			}
		}
		return result;
	}	
}
