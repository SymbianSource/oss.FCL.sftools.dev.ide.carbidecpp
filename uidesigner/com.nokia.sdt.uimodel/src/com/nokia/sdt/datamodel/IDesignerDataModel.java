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
package com.nokia.sdt.datamodel;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.sourcegen.ISourceGenSession;
import com.nokia.cpp.internal.api.utils.core.CacheMap;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.nokia.sdt.workspace.IProjectContext;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;

import java.util.Collection;
import java.util.List;

	/**
	 * Abstract interface for an EMF model usable with
	 * UI Designer. Both the UI designer and the display
	 * model interact with the model via this API
	 * and a standard set of adapter interfaces.
	 */
public interface IDesignerDataModel {
	
	/**
	 * Return the abstract specifier for this model
	 */
	IDesignerDataModelSpecifier getModelSpecifier();
	
	/**
	 * Return the abstract project context for this model
	 */
	IProjectContext getProjectContext();

		/**
		 * Releases all resources used by the model. Do not
		 * use this instance after calling dispose
		 */
	void dispose();
	
		/**
		 * Provides an EMF editing domain
		 * @see org.eclipse.emf.edit.domain.EditingDomain
		 */
	EditingDomain getEditingDomain();
	
	/**
	 * Returns the root object of the data model. This is not necessarily
	 * the root visual container
	 * @see #getRootContainers()
	 */
	EObject getRoot();
	
		/**
		 * Returns the model objects corresponding to the
		 * root visual containers. These objects are not
		 * necessarily roots in the model implementation, i.e.
		 * they may have parent objects.
		 * @see org.eclipse.emf.ecore.EObject
		 * @returns non-null array of containers
		 */
	EObject[] getRootContainers();
	
	/**
	 * Create a new root container instance. 
	 * The new object is added as a root container to the data model
	 * @param component the component to associate with the new object
	 */
	EObject createRootContainerInstance(IComponent component);
	
		/**
		 * Returns the component set associated with the
		 * data model. All components are found within
		 * the scope of this component set.
		 */
	IComponentSet getComponentSet();
	
		/**
		 * Establishes a new component set for the model.
		 * The model is not immediately validated
		 * against the new component set, the valdate method
		 * must be called to do so.
		 * @param cs may not be null
		 * @throws CoreException 
		 */
	void setComponentSet(IComponentSet cs) throws CoreException;
	
		/**
		 * Validate the structure of a data model and
		 * the current data against the current component
		 * set.
		 */
	Collection<IModelMessage> validate();
	
		/**
		 * Returns the display model for this data model,
		 * initializing one if necessary
		 */
	IDisplayModel getDisplayModelForRootContainer(EObject rootContainer) throws CoreException;
	
		/**
		 * Returns an existing display model, if there is one. 
		 * Does not create otherwise.
		 */
	IDisplayModel getExistingDisplayModelForRootContainer(EObject rootContainer);
	
		/**
		 * Notification that display model is disposed
		 */
	void displayModelDisposed(IDisplayModel displayModel);
	
		/**
		 * Install the given EMF adapter factory into the
		 * model. The factory will then be able to fulfill
		 * adapter requests.
		 */
	void addAdapterFactory(AdapterFactory adapterFactory);
	
	/**
	 * Create a new component instance. The new object is not
	 * part of the data model, and should be added via
	 * createAddNewComponentInstanceCommand
	 * @param tool the component to associate with the new object
	 */
	EObject createNewComponentInstance(IComponent tool);
	
	static final int IN_FRONT = 0;
	static final int AT_END = -1;
		/**
		 * Creates an undoable EMF command to insert a new instance of a component
		 * @param owner the parent object for the new object
		 * @param child EObject previously created from createNewComponentInstance. It
		 * must not have been previously added to the data model.
		 * @param insertionPosition position at which to insert the new object. Use
		 * the constants IN_FRONT, AT_END, or the index of an existing object under the given owner.
		 * @return an executable command
		 */
	Command createAddNewComponentInstanceCommand(EObject owner, EObject child, int insertionPosition);
	
		/**
		 * Creates an undoable EMF command to remove a collection of existing component instances
		 * @param objectsToRemove a list of objects to be removed
		 * @return an executable command
		 */
	Command createRemoveComponentInstancesCommand(List<EObject> objectsToRemove);
	
		/**
		 * Creates an undoable EMF command to move an existing component instance object.
		 * The object can be moved to a different owner or re-ordered within the same
		 * owner.
		 * @param targetObject the object to be moved
		 * @param newOwner the new owner for the object. Can be the existing owner
		 * @param insertionPosition the desired position for the object. Use
		 * the constants IN_FRONT, AT_END, or the index of an existing object under the given owner.
	     * @return an executable command
		 */
	Command createMoveComponentInstanceCommand(EObject targetObject, EObject newOwner, int insertionPosition);
	
		/**
		 * Creates an undoable EMF command to copy a collection of component instances
		 * to the EMF EditingDomain's clipboard.
		 * @param objectsToCopy a List of EObject
		 * @return an executable command
		 */
	Command createCopyComponentInstancesCommand(List objectsToCopy);
	
	/**
	 * Creates an undoable EMF command which copy a collection of component instances
	 * to the EMF EditingDomain's clipboard and removes them from the model.
	 * @param objectsToCut a List of EObject
	 * @return an executable command
	 */
	Command createCutComponentInstancesCommand(List objectsToCut);

		/**
		 * Creates an undoable EMF command to paste the component instances on the 
		 * EMF EditingDomain's clipboard to the model as children of <code>owner</code>.
		 * @param owner the instance to copy to
		 * @return an executable command
		 */
	Command createPasteComponentInstancesCommand(EObject owner);
	
		/**
		 * Creates an undoable command to add an event binding
		 * @param targetObject object to receive the new binding
		 * @param event descriptor for the event
		 * @param userSpecifiedHandlerName the desired event handler method name. Use null to allow the default, recommended handler name.
		 * @return an executable command
		 */
	Command createAddEventBindingCommand(EObject targetObject, IEventDescriptor event, String userSpecifiedHandlerName);
	
		/**
		 * Creates an undoable command to remove an existing 
		 * event binding.
		 * @param eventBinding the binding to remove. The target object
		 * is implied by the binding
		 * @return an executable command
		 */
	Command createRemoveEventBindingCommand(IEventBinding eventBinding);
	
		/**
		 * Returns a list of the resources for this data model.
		 * The list contains only the primary resources involved with saving
		 * the model data and not other resources generated from it, i.e.
		 * generated source code.
		 */
	Resource[] getResources();
	
		/**
		 * Returns a list of the read-only resources for this data model.
		 * A null return value implies the model can be saved, non-null
		 * that it cannot.
		 */
	Resource[] getReadOnlyResources();
	
		/**
		 * Saves the data model to its storage. Throws an exception 
		 * upon failure.
		 * @param monitor 
		 */
	void	saveModel(IProgressMonitor monitor) throws Exception;
	
		/**
		 * Save the data model to a new file. Future calls
		 * to saveModel will save to the new file
		 */
	void	saveModelAs(IDesignerDataModelSpecifier modelSpecifier, IProgressMonitor monitor) throws Exception;
	
		/**
		 * Return the id of the name property.
		 */
	Object getNamePropertyId();
	
		/**
		 * Searches the model for a component instance with the given 
		 * name.
		 */
	EObject findByNameProperty(String name);
	
		/**
		 * Provide a base name for a new component instance.
		 * The name may subsequently have a suffix added to
		 * ensure uniqueness within the model.
		 */
	String getInstanceNameRoot(IComponent component);

		/**
		 * Returns the IDesignerDataModelProvider that
		 * created this model. May be used to
		 * create temporary models compatible
		 * with this model
		 */
	IDesignerDataModelProvider getProvider();
    
        /**
         * Return a string property from the data model.
         * @param propertyId the property name
         * @return the property value, or null if property does not exist
         */
    String getProperty(String propertyId);

        /**
         * Set a string property in the data model.
         * @param propertyId the property name
         * @param value the property value (must not be null)
         */
    void setProperty(String propertyId, String value);
    
        /**
         * Remove a string property from the data model.
         * @param propertyId the property name
         */
    void removeProperty(String propertyId);
    
    	/**
    	 * Get the root component instances
    	 */
    IComponentInstance[] getRootComponentInstances();
    
		/**
    	 * Set the source gen session.  Setting the session
    	 * does not guarantee source will be generated
    	 * if the model does not have a source gen provider
    	 * property set.
    	 */
    void setSourceGenSession(ISourceGenSession session);

		/**
		 * Get the source gen session.  May be null.
		 */
	ISourceGenSession getSourceGenSession();
	
		/**
		 * Get the data model's local cache map.  This is created
		 * on demand and disposed when the data model is disposed.
		 */
	CacheMap getCache();
	
	
		/**
		 * @return true if the data model version is up to date and all
		 * referenced components are up to date with respect to the currently
		 * installed components.
		 */
	boolean isModelUpToDate();
	
		/**
		 * Call this routine prior to initiating changes that might modify files
		 * on disk. This would include UI changes or batch changes that might write files.
		 * @return IStatus indicating if editing operations are OK. The IStatus details
		 * depend on the underlying version control, but IStatus.isOK will return true if 
		 * it's ok to proceed.
		 * @param context as with {@link IWorkspace#validateEdit(org.eclipse.core.resources.IFile[], Object)},
		 * this is either null or a Shell. If a Shell, this signals UI interaction is allowed and the shell
		 * is used as the context for that UI. If null, no UI interaction will happen.
		 * @see IWorkspace#validateEdit(org.eclipse.core.resources.IFile[], Object)
		 */
	IStatus validateEdit(Object context);
	
	/**
	 * A listener for notifying that a data model was disposed
	 */
	interface IDisposeListener {
		void dataModelDisposed(IDesignerDataModel model);
	}
	
	/**
	 * Add a dispose listener
	 * @param listener IDisposeListener
	 */
	void addDisposeListener(IDisposeListener listener);
	
	/**
	 * Remove a dispose listener
	 * @param listener IDisposeListener
	 */
	void removeDisposeListener(IDisposeListener listener);
	
	/**
	 * @param object EObject
	 * @return the component id String
	 */
	String getComponentId(EObject object);
}
