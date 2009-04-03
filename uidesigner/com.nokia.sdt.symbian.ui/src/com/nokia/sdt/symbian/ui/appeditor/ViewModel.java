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
package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.dm.S60ModelUtils;
import com.nokia.sdt.symbian.dm.S60ViewDesignInfo;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.sdt.symbian.dm.UIQViewDesignInfo;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.ViewDesignInfo;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.nokia.sdt.workspace.IProjectContext;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

	/**
	 * Provides the model need for the application editor's
	 * Views page. It is used to reconcile:
	 *   - UI designs which are referenced from the application model.
	 *     They may or may not be present in the project, i.e. missing
	 *   - UI designs which are in the project. They may or may not
	 *     be currently referenced in the application model
	 *
	 */
public class ViewModel {
	
	private IDesignerDataModel rootDataModel;
	private List<ViewModelEntry> entries;
	private ViewModelEntry initialView;
	private IComponentInstancePropertyListener viewRefPropertyListener;
	private IComponentInstancePropertyListener appUiPropertyListener;
	private IComponentInstanceChildListener appUiChildListener;
	private IComponentInstanceChildListener statusPaneChildListener;
	private ListenerList<IListener> modelListeners = new ListenerList<IListener>();
	private boolean isViewAppUi;
	private boolean isNaviTabbingEnabled;
	
	public interface IListener {
		void entryAdded(ViewModelEntry entry);
		void entryChanged(ViewModelEntry entry);
		void entryRemoved(ViewModelEntry entry);
		void entriesReordered();
		void initialViewChanged(ViewModelEntry oldInitialView, ViewModelEntry newInitialView);
		void naviPaneTabbingChanged(boolean enabled);
	}
	
	public ViewModel(IDesignerDataModel dataModel) {
		this.rootDataModel = dataModel;
		
		// use a single listener to handle refreshing when
		// a property changes on an entry
		viewRefPropertyListener = new IComponentInstancePropertyListener() {
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				viewReferenceChanged(componentInstance);
			}
		};

		// listener for property changes on the AppUi so we know when
		// the initial view changes
		appUiPropertyListener = new IComponentInstancePropertyListener() {
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				if (SymbianModelUtils.APPUI_INITIALVIEW.equals(propertyId)) {
					IPropertySource ps = ModelUtils.getPropertySource(componentInstance);
					String newInitialViewName = (String) ps.getPropertyValue(propertyId);
					ViewModelEntry newInitialView = null;
					if (newInitialViewName != null) {
						for (ViewModelEntry currEntry : entries) {
							if (currEntry.viewReference != null &&
								currEntry.viewReference.getName().equals(newInitialViewName)) {
								newInitialView = currEntry;
								break;
							}
						}
					}
					ViewModelEntry oldInitialView = initialView;
					initialView = newInitialView;
					notifyInitialViewChanged(oldInitialView, initialView);
				}
			}
		};

		// put a child listener on the AppUi to handle view reference
		// adds and removes
		appUiChildListener = new IComponentInstanceChildListener() {
			public void childAdded(EObject parent, EObject child) {
				handleAppUiChildAdded(child);
			}

			public void childRemoved(EObject parent, EObject child) {
				handleAppUiChildRemoved(child);
			}

			public void childrenReordered(EObject parent) {
				// a view reference or some other child was reordered, but
				// we just assumed view references are affected
				viewReferencesReordered();
			}
		};
		
		statusPaneChildListener = new IComponentInstanceChildListener() {
			public void childAdded(EObject parent, EObject child) {
				statusPaneChildAdded(child);
			}

			public void childRemoved(EObject parent, EObject child) {
				statusPaneChildRemoved(child);
			}

			public void childrenReordered(EObject parent) {
			}			
		};
		
		refresh();
	}
	
	public void addModelListener(IListener modelListener) {
		modelListeners.add(modelListener);
	}
	
	private void initStatusPaneListener() {
		EObject appUi = SymbianModelUtils.findAppUi(rootDataModel);
		if (appUi != null) {
			EObject statusPane = ModelUtils.findFirstComponentInstance(
					appUi, S60ModelUtils.S60_STATUSPANE, true);
			if (statusPane != null) {
				IComponentInstance ci = ModelUtils.getComponentInstance(statusPane);
				ci.addChildListener(statusPaneChildListener);
			}
		}
	}
	
	public ViewModelEntry[] getEntries() {
		ViewModelEntry[] result = null;
		if (entries != null) {
			result = entries.toArray(new ViewModelEntry[entries.size()]);
		}
		return result;
	}
	
	public int getEntryCount() {
		int result = 0;
		if (entries != null) {
			result = entries.size();
		}
		return result;
	}
	
	public int indexOfEntry(ViewModelEntry entry) {
		int result = -1;
		if (entries != null) {
			result = entries.indexOf(entry);
		}
		return result;
	}
	
	public EObject getParentOfEntry(ViewModelEntry entry) {
		EObject result = null;
		if (entry.viewReference != null) {
			result = entry.viewReference.getParent();
		}
		return result;
	}
	
	public int indexOfEntryInParent(ViewModelEntry entry) {
		int result = -1;
		EObject parentObj = getParentOfEntry(entry);
		IComponentInstance parentCI = ModelUtils.getComponentInstance(parentObj);
		EObject children[] = parentCI.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (entry.viewReference.getEObject() == children[i]) {
				result = i;
				break;
			}
		}
		return result;
	}
	
	public ViewModelEntry getInitialView() {
		return initialView;
	}
	
	public boolean isViewAppUi() {
		return isViewAppUi;
	}
	
	public boolean isNaviTabbingEnabled() {
		return isNaviTabbingEnabled;
	}
		
	public IDesignerDataModelSpecifier[] getImportableModels() {
		IProjectContext projectContext = rootDataModel.getProjectContext();
		IDesignerDataModelSpecifier[] modelSpecifiers = projectContext.getModelSpecifiers();
		ArrayList<IDesignerDataModelSpecifier> unreferenced = new ArrayList<IDesignerDataModelSpecifier>();
		for (int i = 0; i < modelSpecifiers.length; i++) {
			if (modelSpecifiers[i].isUIDesign() && findEntryBySpecifier(modelSpecifiers[i]) == null) {
				unreferenced.add(modelSpecifiers[i]);
			}
		}
		return unreferenced.toArray(new IDesignerDataModelSpecifier[unreferenced.size()]);
	}

	private void clearListeners() {
		if (entries != null) {
			for (ViewModelEntry curr : entries) {
				if (curr.viewReference != null) {
					curr.viewReference.removePropertyListener(viewRefPropertyListener);
				}
			}
		}
		
		EObject appUiObj = SymbianModelUtils.findAppUi(rootDataModel);
		IComponentInstance appUi = ModelUtils.getComponentInstance(appUiObj);
		if (appUi != null) {
			appUi.removePropertyListener(appUiPropertyListener);
			appUi.removeChildListener(appUiChildListener);
		}
	}
		
	public void refresh() {
		clearListeners();
		entries = new ArrayList<ViewModelEntry>();
	
		HashMap<String, IDesignerDataModelSpecifier> existingModels = new HashMap<String, IDesignerDataModelSpecifier>();
		IProjectContext projectContext = rootDataModel.getProjectContext();
		IDesignerDataModelSpecifier[] modelSpecifiers = projectContext.getModelSpecifiers();
		for (int i = 0; i < modelSpecifiers.length; i++) {
			IPath path = modelSpecifiers[i].getPrimaryResource().getProjectRelativePath();
			existingModels.put(path.toString(), modelSpecifiers[i]);
		}
	
		EObject appUiObj = SymbianModelUtils.findAppUi(rootDataModel);
		IComponentInstance appUi = ModelUtils.getComponentInstance(appUiObj);
		if (appUi != null) {
			isViewAppUi = ModelUtils.isInstanceOf(appUiObj, S60ModelUtils.S60_VIEW_APPUI) && SymbianModelUtils.getModelSDK(rootDataModel) == SymbianModelUtils.SDKType.S60
			|| ModelUtils.isInstanceOf(appUiObj, UIQModelUtils.UIQ_APPLICATION_UI) && SymbianModelUtils.getModelSDK(rootDataModel) == SymbianModelUtils.SDKType.UIQ;
			appUi.addPropertyListener(appUiPropertyListener);
			appUi.addChildListener(appUiChildListener);
		}
		
		if (SymbianModelUtils.getModelSDK(rootDataModel) == SymbianModelUtils.SDKType.S60) {
			EObject[] children = appUi.getChildren();
			// Add all the contained view references, matching them up to the
			// data model specifier, if any.
			for (int i = 0; i < children.length; i++) {
				if (ModelUtils.isInstanceOf(children[i], S60ModelUtils.S60_DESIGN_REFERENCE)) {
					IComponentInstance ci = ModelUtils.getComponentInstance(children[i]);
					IPropertySource viewRefPS = ModelUtils.getPropertySource(children[i]);
					String filePath = (String) viewRefPS.getPropertyValue(SymbianModelUtils.DESIGNREF_DESIGNPATH);
					IDesignerDataModelSpecifier specifier = existingModels.remove(filePath);
					
					S60ViewDesignInfo designInfo = (S60ViewDesignInfo) SymbianModelUtils.getViewDesignInfo(specifier);
					ViewModelEntry entry = new ViewModelEntry(
							ModelUtils.isInstanceOf(children[i], S60ModelUtils.S60_AVKON_VIEW_REFERENCE),
							ci, filePath, designInfo.baseName);
					entry.specifier = specifier;
					updateEntryFromDesignInfo(entry, designInfo);
					entries.add(entry);
					ci.addPropertyListener(viewRefPropertyListener);
				}
			}
			
			IPropertySource appUiPS = ModelUtils.getPropertySource(appUiObj);
			String initialViewRefName = (String) appUiPS.getPropertyValue(SymbianModelUtils.APPUI_INITIALVIEW);
			if (!TextUtils.isEmpty(initialViewRefName)) {
				EObject viewRefObj = rootDataModel.findByNameProperty(initialViewRefName);
				initialView = findEntryByViewRef(viewRefObj);
			}
			
			isNaviTabbingEnabled = S60ModelUtils.isNaviPaneTabbingEnabled(rootDataModel);
						
			initStatusPaneListener();
		}
		else if (SymbianModelUtils.getModelSDK(rootDataModel) == SymbianModelUtils.SDKType.UIQ) {
			EObject[] children = appUi.getChildren();
			// Add all the contained view references, matching them up to the
			// data model specifier, if any.
			for (int i = 0; i < children.length; i++) {
				if (ModelUtils.isInstanceOf(children[i], UIQModelUtils.UIQ_DESIGN_REFERENCE)) {
					IComponentInstance ci = ModelUtils.getComponentInstance(children[i]);
					IPropertySource viewRefPS = ModelUtils.getPropertySource(children[i]);
					String filePath = (String) viewRefPS.getPropertyValue(SymbianModelUtils.DESIGNREF_DESIGNPATH);
					IDesignerDataModelSpecifier specifier = existingModels.remove(filePath);
					
					UIQViewDesignInfo designInfo = (UIQViewDesignInfo) SymbianModelUtils.getViewDesignInfo(specifier);
					ViewModelEntry entry = new ViewModelEntry(
							ModelUtils.isInstanceOf(children[i], UIQModelUtils.UIQ_DESIGN_REFERENCE),
							ci, filePath, designInfo.baseName);
					entry.specifier = specifier;
					updateEntryFromDesignInfo(entry, designInfo);
					entries.add(entry);
					ci.addPropertyListener(viewRefPropertyListener);
				}
			}
			
			IPropertySource appUiPS = ModelUtils.getPropertySource(appUiObj);
			String initialViewRefName = (String) appUiPS.getPropertyValue(SymbianModelUtils.APPUI_INITIALVIEW);
			if (!TextUtils.isEmpty(initialViewRefName)) {
				EObject viewRefObj = rootDataModel.findByNameProperty(initialViewRefName);
				initialView = findEntryByViewRef(viewRefObj);
			}
		} else {
			throw new IllegalStateException();
		}
	}
	
	private void notifyEntryAdded(ViewModelEntry entry) {
		for (IListener l : modelListeners) {
			l.entryAdded(entry);
		}
	}

	private void notifyEntryChanged(ViewModelEntry entry) {
		for (IListener l : modelListeners) {
			l.entryChanged(entry);
		}
	}
	
	private void notifyEntryRemoved(ViewModelEntry entry) {
		for (IListener l : modelListeners) {
			l.entryRemoved(entry);
		}
	}
	
	private void notifyEntriesReordered() {
		for (IListener l : modelListeners) {
			l.entriesReordered();
		}
	}
	
	private void notifyInitialViewChanged(ViewModelEntry oldInitialView, ViewModelEntry newInitialView) {
		for (IListener l : modelListeners) {
			l.initialViewChanged(oldInitialView, newInitialView);
		}
	}
	
	private void notifyNaviPaneTabbingChanged(boolean enabled) {
		for (IListener l : modelListeners) {
			l.naviPaneTabbingChanged(enabled);
		}
	}
	
	private String getPathProperty(EObject viewRefObj) {
		IPropertySource ps = ModelUtils.getPropertySource(viewRefObj);
		String path = (String) ps.getPropertyValue(SymbianModelUtils.DESIGNREF_DESIGNPATH);
		return TextUtils.strlen(path) > 0? path : null;
	}
	
	private ViewModelEntry findEntryByViewRef(EObject viewRefObj) {
		if (viewRefObj == null) return null;
		ViewModelEntry result = null;
		IComponentInstance viewRef = ModelUtils.getComponentInstance(viewRefObj);
		for (ViewModelEntry entry : entries) {
			if (entry.viewReference == viewRef) {
				result = entry;
				break;
			}
		}
		return result;
	}
	
	private ViewModelEntry findEntryByPath(String relativePath) {
		ViewModelEntry result = null;
		for (ViewModelEntry entry : entries) {
			if (entry.relativePath.equals(relativePath)) {
				result = entry;
				break;
			}
		}
		return result;
	}
	
	private ViewModelEntry findEntryBySpecifier(IDesignerDataModelSpecifier specifier) {
		if (specifier == null) return null;
		ViewModelEntry result = null;
		for (ViewModelEntry entry : entries) {
			if (specifier.equals(entry.specifier)) {
				result = entry;
				break;
			}
		}
		return result;
	}
	
	private IDesignerDataModelSpecifier findSpecifierByPath(String relativePath) {
		if (relativePath == null) return null;
		IPath path = new Path(relativePath);
		IDesignerDataModelSpecifier result = rootDataModel.getProjectContext().findSpecifierForPath(path);
		return result;
	}
	
	private void viewReferenceAdded(EObject viewRefObj) {	
		String relativePath = getPathProperty(viewRefObj);
		Check.checkState(findEntryByViewRef(viewRefObj)== null);
		Check.checkState(findEntryByPath(relativePath) == null);
		
		IComponentInstance viewRef = ModelUtils.getComponentInstance(viewRefObj);
		viewRef.addPropertyListener(viewRefPropertyListener);
	
		IDesignerDataModelSpecifier specifier = findSpecifierByPath(relativePath);
		
		// note: we don't know what kind of model it is yet (S60 vs UIQ) since 
		// this reference add notification happens before the instance is fully initialized --
		// so we can't assume any specific derived type for this ViewDesignInfo here
		ViewDesignInfo designInfo = SymbianModelUtils.getViewDesignInfo(specifier);
		
		ViewModelEntry entry = new ViewModelEntry(
				ModelUtils.isInstanceOf(viewRefObj, S60ModelUtils.S60_AVKON_VIEW_REFERENCE),
				viewRef, relativePath, designInfo.baseName);
		entry.specifier = specifier;
		updateEntryFromDesignInfo(entry, designInfo);
		entries.add(getIndexForAddedEntry(entry), entry);
		notifyEntryAdded(entry);
	}
	
	private IComponentInstance findPredecessorInDataModel(ViewModelEntry entry) {
		IComponentInstance predecessor = null;

		EObject parent = entry.viewReference.getParent();
		IComponentInstance containerInstance = ModelUtils.getComponentInstance(parent);
		EObject[] children = containerInstance.getChildren();
		Check.checkState(children != null);
		for (int i = 0; i < children.length; i++) {
			IComponentInstance childInstance = ModelUtils.getComponentInstance(children[i]);
			if (entry.viewReference.equals(childInstance))
				break;
			else if (SymbianModelUtils.isDesignReference(children[i]))
				predecessor = childInstance;
		}
		
		return predecessor;
	}
	
	private int getIndexForAddedEntry(ViewModelEntry entry) {
		if (!entries.isEmpty()) {
			IComponentInstance predecessor = findPredecessorInDataModel(entry);
			if (predecessor == null)
				return 0;
			else {
				int index = 0;
				for (Iterator<ViewModelEntry> iter = entries.iterator(); iter.hasNext();) {
					if (iter.next().viewReference.equals(predecessor))
						return index + 1;
					index++;
				}
			}
		}
		return 0;
	}

	private void updateEntryFromDesignInfo(ViewModelEntry entry, ViewDesignInfo designInfo) {
		if (designInfo instanceof S60ViewDesignInfo && ((S60ViewDesignInfo) designInfo).isAvkonView) {
			entry.mainComponentFriendlyName = ((S60ViewDesignInfo) designInfo).avkonViewFriendlyName;
		}
		else {
			entry.mainComponentFriendlyName = designInfo.userContainerFriendlyName;
		}
	}
	
	private void viewReferenceChanged(EObject viewRefObj) {
		ViewModelEntry entry = findEntryByViewRef(viewRefObj);
		Check.checkState(entry != null);
		
		// The only mutable state on entries is the model specifier
		// and relative path.
		// when views get imported the add happens before the path
		// property is set, so when need to hook up the specifier
		// upon the change event
	
		String relativePath = getPathProperty(viewRefObj);
		if (!ObjectUtils.equals(relativePath, entry.relativePath)) {
			entry.relativePath = relativePath;
			entry.specifier = findSpecifierByPath(relativePath);
			ViewDesignInfo designInfo = SymbianModelUtils.getViewDesignInfo(entry.specifier);
			updateEntryFromDesignInfo(entry, designInfo);	
		}
		
		notifyEntryChanged(entry);
	}
	
	private void viewReferenceRemoved(EObject viewRefObj) {
		ViewModelEntry entry = findEntryByViewRef(viewRefObj);
		if (entry != null) {
			entry.viewReference.removePropertyListener(viewRefPropertyListener);
			entries.remove(entry);
			notifyEntryRemoved(entry);
		}
	}
	
	private void viewReferencesReordered() {
		
		ArrayList<ViewModelEntry> newEntryList = new ArrayList<ViewModelEntry>();
		
		EObject appUiObj = SymbianModelUtils.findAppUi(rootDataModel);
		IComponentInstance appUi = ModelUtils.getComponentInstance(appUiObj);
		EObject[] children = appUi.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (SymbianModelUtils.isDesignReference(children[i])) {
				
				ViewModelEntry currEntry = findEntryByViewRef(children[i]);
				Check.checkState(currEntry != null);
				newEntryList.add(currEntry);
			}
		}
		
		entries = newEntryList;
		notifyEntriesReordered();
	}

	private void handleAppUiChildAdded(EObject child) {
		if (SymbianModelUtils.isDesignReference(child)) {
			viewReferenceAdded(child);
		}
		else if (ModelUtils.isInstanceOf(child, S60ModelUtils.S60_STATUSPANE)) {
			initStatusPaneListener();
		}
	}
	
	private void handleAppUiChildRemoved(EObject child) {
		if (SymbianModelUtils.isDesignReference(child)) {
			viewReferenceRemoved(child);
		}
		else if (ModelUtils.isInstanceOf(child, S60ModelUtils.S60_STATUSPANE)) {
			IComponentInstance ci = ModelUtils.getComponentInstance(child);
			ci.removeChildListener(statusPaneChildListener);
		}
	}
		
	private void statusPaneChildAdded(EObject child) {
		if (ModelUtils.isInstanceOf(child, S60ModelUtils.S60_NAVITAB)) {
			isNaviTabbingEnabled = true;
			notifyNaviPaneTabbingChanged(true);
		}
	}
	
	private void statusPaneChildRemoved(EObject child) {
		if (ModelUtils.isInstanceOf(child, S60ModelUtils.S60_NAVITAB)) {
			isNaviTabbingEnabled = false;
			notifyNaviPaneTabbingChanged(false);
		}
	}

	public void dispose() {
		clearListeners();
	}
}
