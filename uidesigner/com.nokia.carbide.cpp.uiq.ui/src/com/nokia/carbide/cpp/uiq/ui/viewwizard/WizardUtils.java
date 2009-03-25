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
/* START_USECASES: CU1, CU2 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.viewwizard;

import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.emf.dm.INodeVisitor;
import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Map;

public class WizardUtils {

	public static final String NON_VIEW_APPUI_ID = "com.nokia.carbide.uiq.ApplicationUI"; //$NON-NLS-1$
	public static final String APPLICATION_ID = "com.nokia.carbide.uiq.Application"; //$NON-NLS-1$
	public static final String CLASS_NAME_PROPERTY_ID = "className"; //$NON-NLS-1$

	public static IPropertySource getProperties(EObject instance) {
		return (IPropertySource) EcoreUtil.getRegisteredAdapter(instance, IPropertySource.class);
	}
	
	public static IComponentCustomizerUI getCustomizerUI(EObject instance) {
		return (IComponentCustomizerUI) EcoreUtil.getRegisteredAdapter(instance, IComponentCustomizerUI.class);
	}
	
	public static ILayoutObject getLayoutObject(EObject instance) {
		return (ILayoutObject) EcoreUtil.getRegisteredAdapter(instance, ILayoutObject.class);
	}

	public static IComponentInstance getComponentInstance(EObject instance) {
		return (IComponentInstance) EcoreUtil.getRegisteredAdapter(instance, IComponentInstance.class);
	}

	public static EObject addChildObject(IDesignerDataModel dataModel, IComponent component, EObject parent, 
														String name, String className, int insertionPosition) {
		EObject child = dataModel.createNewComponentInstance(component);
		Command command = 
			dataModel.createAddNewComponentInstanceCommand(parent, child, insertionPosition);
		if (command.canExecute()) {
			command.execute();
			
			IPropertySource propertySource = WizardUtils.getProperties(child);
            if (propertySource != null) {
    			if (name != null)
    				propertySource.setPropertyValue("name", name); //$NON-NLS-1$
    			if (className != null)
                    propertySource.setPropertyValue(CLASS_NAME_PROPERTY_ID, className); //$NON-NLS-1$
            }

            return child;
		}
		
		return null;
	}

	public static void addDefaultBoundsToInstance(EObject instance, IDisplayModel displayModel, int width, int height) {
		ILayoutObject layoutObject = WizardUtils.getLayoutObject(instance);
		Check.checkContract(layoutObject != null);
		LayoutAreaConfiguration config = EditorUtils.getSavedConfiguration(displayModel, null);
		if (config != null) {
			try {
				displayModel.setCurrentConfiguration(config);
				return;
			} catch (CoreException e) {
				// fall through if there's an exception
			}
		}
		
		Rectangle newBounds = new Rectangle(0, 0, width, height);
		layoutObject.setBounds(newBounds);
		
		ILayoutContainer layoutContainer = (ILayoutContainer) EcoreUtil.getRegisteredAdapter(
				instance, ILayoutContainer.class);
		if (layoutContainer != null)
			layoutContainer.layoutChildren();
	}

	public static EObject addRootInstance(IDesignerDataModel dataModel, IComponent component, String name, String className) {
		EObject rootInstance = dataModel.createRootContainerInstance(component);
	
		if (name != null) {
			IPropertySource propertySource = WizardUtils.getProperties(rootInstance);
			propertySource.setPropertyValue("name", name); //$NON-NLS-1$
            propertySource.setPropertyValue(CLASS_NAME_PROPERTY_ID, className);
		}
		
		return rootInstance;
	}

	public static void addSourceGenPropertiesFromTemplate(IDesignerDataModel dataModel, ITemplate template, boolean isLegacy) {
		Check.checkState(template != null);
		if (template != null) {
			Map<String, Object> valueStore = template.getTemplateValues();
			String string = (String) valueStore.get("sourceDir"); //$NON-NLS-1$
			Check.checkState(string != null);
	        dataModel.setProperty(DesignerDataModel.SOURCE_DIRECTORY_ID, string);
	        string = (String) valueStore.get("incDir"); //$NON-NLS-1$
	        Check.checkState(string != null);
	        dataModel.setProperty(DesignerDataModel.INCLUDE_DIRECTORY_ID, string);
	        string = (String) valueStore.get("resDir"); //$NON-NLS-1$
	        Check.checkState(string != null);
	        dataModel.setProperty(DesignerDataModel.RESOURCE_DIRECTORY_ID, string);
	        if (!isLegacy) {
		        string = (String) valueStore.get("regDir"); //$NON-NLS-1$
		        Check.checkState(string != null);
		        dataModel.setProperty(DesignerDataModel.REGISTRY_DIRECTORY_ID, string);
	        }
	        string = (String) valueStore.get("groupDir"); //$NON-NLS-1$
	        Check.checkState(string != null);
	        dataModel.setProperty(DesignerDataModel.BUILD_DIRECTORY_ID, "group"); //$NON-NLS-1$
		}
		if (isLegacy)
			dataModel.setProperty(SymbianModelUtils.LEGACY_APPLICATION_PROPERTY, "true"); //$NON-NLS-1$
		else
			dataModel.removeProperty(SymbianModelUtils.LEGACY_APPLICATION_PROPERTY);
	}

	public static void addSourceGenPropertiesFromModel(IDesignerDataModel dataModel, IDesignerDataModel otherModel) {
		Check.checkState(otherModel != null);
		if (otherModel != null) {
			String string = otherModel.getProperty(DesignerDataModel.SOURCE_DIRECTORY_ID);
			Check.checkState(string != null);
	        dataModel.setProperty(DesignerDataModel.SOURCE_DIRECTORY_ID, string);
	        string = otherModel.getProperty(DesignerDataModel.INCLUDE_DIRECTORY_ID);
	        Check.checkState(string != null);
	        dataModel.setProperty(DesignerDataModel.INCLUDE_DIRECTORY_ID, string);
	        string = otherModel.getProperty(DesignerDataModel.RESOURCE_DIRECTORY_ID);
	        Check.checkState(string != null);
	        dataModel.setProperty(DesignerDataModel.RESOURCE_DIRECTORY_ID, string);
	        string = otherModel.getProperty(DesignerDataModel.BUILD_DIRECTORY_ID);
	        Check.checkState(string != null);
	        dataModel.setProperty(DesignerDataModel.BUILD_DIRECTORY_ID, "group"); //$NON-NLS-1$
		}
        dataModel.setProperty(DesignerDataModel.SOURCEGEN_PROVIDER_PROPERTY, SymbianModelUtils.SOURCEGEN_PROVIDER_ID);
	}

	public static IDesignerDataModel getExtantRootModel(IProject project) {
		if (!hasRootDataModelFile(project))
			return null;
		
		IDesignerDataModel dataModel = null;
		WorkspaceContext wc = WorkspaceContext.getContext();
		IProjectContext pc = wc.getContextForProject(project);
		if (pc != null) {
			ISymbianProjectContext spc = (ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);
			if (spc != null) {
				IDesignerDataModelSpecifier rootModelSpecifier = spc.getRootModel();
				if (rootModelSpecifier != null) {
					LoadResult lr = rootModelSpecifier.load();
					if (lr != null) {
						dataModel = lr.getModel();
					}
				}
			}			
		}		
		return dataModel;
	}
	
	public static boolean hasRootDataModelFile(IProject project) {
		if (project == null)
			return false;
		IPath projectPath = project.getLocation(); // use absolute path
		IPath rootModelPath = projectPath.append(DesignerDataModel.ROOT_DATA_MODEL_FILENAME);
	    IWorkspace workspace = ResourcesPlugin.getWorkspace();
	    Check.checkState(workspace != null);
	    IWorkspaceRoot root = workspace.getRoot();
	    IFile[] files = root.findFilesForLocation(rootModelPath);
	    if (files.length > 0)
	    	return files[0].exists();
	    
	    return false;
	}
	
	public static boolean rootModelIsNotViewSwitching(IDesignerDataModel dataModel) {
		INodeVisitor appUiFinder = new INodeVisitor() {
			public Object visit(INode node) {
				if (node.getComponentId().equals(NON_VIEW_APPUI_ID))
					return node;
				return null;
			}
		};
		return ((DesignerDataModel) dataModel).getDesignerData().visitNodes(appUiFinder) != null;
	}

	/**
	 */
	public static EObject getApplicationObject(IDesignerDataModel dataModel) {
		INodeVisitor appUiFinder = new INodeVisitor() {
			public Object visit(INode node) {
				if (node.getComponentId().equals(APPLICATION_ID))
					return node;
				return null;
			}
		};
		return (EObject) ((DesignerDataModel) dataModel).getDesignerData().visitNodes(appUiFinder);
	}
	
	public static EObject getSpecificObject(final String componentId, IDesignerDataModel dataModel) {
		INodeVisitor containerFinder = new INodeVisitor() {
			public Object visit(INode node) {
				if (node.getComponentId().equals(componentId))
					return node;
				return null;
			}
		};
		return (EObject) ((DesignerDataModel) dataModel).getDesignerData().visitNodes(containerFinder);
	}
	
	public static EObject getObjectWithAttribute(final String attributeName, 
			final String expectedAttributeValue, IDesignerDataModel dataModel) {
		INodeVisitor containerFinder = new INodeVisitor() {
			public Object visit(INode node) {
				IAttributes attributes = (IAttributes) node.getComponent().getAdapter(IAttributes.class);
				if (attributes != null && attributes.getAttribute(attributeName) != null) {
					if (attributes.getAttribute(attributeName).equals(expectedAttributeValue))
						return node;
				}
				return null;
			}
		};
		return (EObject) ((DesignerDataModel) dataModel).getDesignerData().visitNodes(containerFinder);
	}

	/**
	 * Enable or disable sourcegen for the model
	 * @param dataModel
	 */
	public static boolean toggleSourceGeneration(IDesignerDataModel dataModel, boolean enable) {
		boolean oldSetting = dataModel.getProperty(DesignerDataModel.SOURCEGEN_PROVIDER_PROPERTY) != null;
		if (enable)
			dataModel.setProperty(DesignerDataModel.SOURCEGEN_PROVIDER_PROPERTY, SymbianModelUtils.SOURCEGEN_PROVIDER_ID);
		else {
			dataModel.removeProperty(DesignerDataModel.SOURCEGEN_PROVIDER_PROPERTY);
			dataModel.setSourceGenSession(null);
		}
		return oldSetting;
	}

	/**
	 * Enable sourcegen for the model and set it up for generation.
	 * @param sourceGenProvider
	 * @param dataModel
	 * @param modelSpecifier
	 */
	public static void setupSourceGeneration(ISourceGenProvider sourceGenProvider, IDesignerDataModel dataModel, IDesignerDataModelSpecifier modelSpecifier) {
		toggleSourceGeneration(dataModel, true);
		dataModel.setSourceGenSession(SymbianModelUtils.createSourceGenSession(sourceGenProvider, dataModel, modelSpecifier));
	}

}
