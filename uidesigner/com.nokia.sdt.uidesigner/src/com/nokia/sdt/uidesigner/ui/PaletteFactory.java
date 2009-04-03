/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.*;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor.Visitor;
import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.editor.ICreationTool;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.derived.IExtendedFlyoutPreferences;
import com.nokia.sdt.uidesigner.ui.command.CreationFactory;
import com.nokia.sdt.uidesigner.ui.utils.*;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.*;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;

import java.util.*;

public class PaletteFactory {
	public static class CreationTool extends org.eclipse.gef.tools.CreationTool {
		@Override
		protected boolean handleMove() {
			// This is overridden to reorder when the command is requested.
			// In order to support target feedback listeners, getting the command must follow
			// showing target feedback. This works correctly for moving and resizing, but not creating.
			// TODO: GEF people have been asked to reorder the calls in the CreationTool as below.
			// If they do so, we can remove this subclass as well as the call to setToolClass in the
			// constructor below.
			updateTargetRequest();
			updateTargetUnderMouse();
			showTargetFeedback();
			setCurrentCommand(getCommand());
			return true;
		}
	}
	
	public static class CreationToolEntry extends CombinedTemplateCreationEntry {
		
		private CreationToolEntry(String label, String shortDesc,
				Object template, CreationFactory factory,
				ImageDescriptor iconSmall, ImageDescriptor iconLarge) {
			super(label, shortDesc, template, factory, iconSmall, iconLarge);
			setToolClass(CreationTool.class);
		}
		
		private static IComponent getComponentFromEntry(CombinedTemplateCreationEntry entry) {
			Object template = entry.getTemplate();
			if (template instanceof IAdaptable)
				return (IComponent) ((IAdaptable) template).getAdapter(IComponent.class);
			
			return null;
		}

		public boolean equals(Object obj) {
			if (obj instanceof CombinedTemplateCreationEntry) {
				IComponent component = getComponentFromEntry((CombinedTemplateCreationEntry) obj);
				IComponent componentSelf = getComponentFromEntry(this);
				if (component == componentSelf)
					return true;
				if (component == null || componentSelf == null || 
						component.getId() == null || componentSelf.getId() == null)
					return getTemplate().equals(obj);
				
				return component.getId().equals(componentSelf.getId());
			}
			
			return false;
		}
	}

	/** Default palette size. */
	private static final int DEFAULT_PALETTE_SIZE = 125;

	/** Preference ID used to persist the palette location. */
	private static final String PALETTE_DOCK_LOCATION = "PaletteFactory.Location"; //$NON-NLS-1$

	/** Preference ID used to persist the palette size. */
	private static final String PALETTE_SIZE = "PaletteFactory.Size"; //$NON-NLS-1$

	/** Preference ID used to persist the flyout palette's state. */
	private static final String PALETTE_STATE = "PaletteFactory.State"; //$NON-NLS-1$
	
	/** Preference ID used to persist the flyout palette's info expanded state. */
	private static final String PALETTE_INFO_EXPANDED = "PaletteFactory.InfoExpanded"; //$NON-NLS-1$
	
	private static final String UNCATEGORIZED = Strings.getString("PaletteFactory.NoCategory"); //$NON-NLS-1$

	/**
	 * Return a FlyoutPreferences instance used to save/load the preferences of
	 * a flyout palette.
	 */
	static FlyoutPreferences getCreatePalettePreferences() {
		// set default flyout palette preference values, in case the preference
		// store
		// does not hold stored values for the given preferences
		getPreferenceStore().setDefault(PALETTE_DOCK_LOCATION, -1);
		getPreferenceStore().setDefault(PALETTE_STATE, FlyoutPaletteComposite.STATE_PINNED_OPEN);
		getPreferenceStore().setDefault(PALETTE_SIZE, DEFAULT_PALETTE_SIZE);
		getPreferenceStore().setDefault(PALETTE_INFO_EXPANDED, true);

		return new IExtendedFlyoutPreferences() {
			public int getDockLocation() {
				return getPreferenceStore().getInt(PALETTE_DOCK_LOCATION);
			}

			public int getPaletteState() {
				return getPreferenceStore().getInt(PALETTE_STATE);
			}

			public int getPaletteWidth() {
				return getPreferenceStore().getInt(PALETTE_SIZE);
			}

			public boolean isInfoWidgetExpanded() {
				return getPreferenceStore().getBoolean(PALETTE_INFO_EXPANDED);
			}

			public void setDockLocation(int location) {
				getPreferenceStore().setValue(PALETTE_DOCK_LOCATION, location);
			}

			public void setPaletteState(int state) {
				getPreferenceStore().setValue(PALETTE_STATE, state);
			}

			public void setPaletteWidth(int width) {
				getPreferenceStore().setValue(PALETTE_SIZE, width);
			}

			public void setInfoWidgetExpanded(boolean state) {
				getPreferenceStore().setValue(PALETTE_INFO_EXPANDED, state);
			}
		};
	}
	
	static class ComponentCreationTool implements ICreationTool {
		private IComponent component;
		
		public ComponentCreationTool(IComponent component) {
			this.component = component;
		}

		public EObject createNewObject(IDesignerDataModel dataModel) {
			return dataModel.createNewComponentInstance(component);
		}

		public String getCategory() {
			return component.getCategory();
		}

		public String getDescription() {
			Object obj = component.getAdapter(IDocumentation.class);
			if (obj != null) {
	            String text = ((IDocumentation) obj).getInformation();
	            return TextUtils.cleanUpXMLText(text);
	        }
			return null;
		}

		public ImageDescriptor getIcon16() {
			Object obj = component.getAdapter(IDesignerImages.class);
			if (obj != null) {
				return ((IDesignerImages) obj).getSmallIconDescriptor();
			}
			
	        return null;
		}

		public ImageDescriptor getIcon24() {
			Object obj = component.getAdapter(IDesignerImages.class);
			if (obj != null) {
				return ((IDesignerImages) obj).getLargeIconDescriptor();
			}
			
			return null;
		}

		public String getLabel() {
			return component.getFriendlyName();
		}

		public Object getAdapter(Class adapter) {
			return component.getAdapter(adapter);
		}

		public void addNotify(EObject parent) {
		}
	}

	/**
	 * Returns the preference store for the plugin.
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#getPreferenceStore()
	 */
	private static IPreferenceStore getPreferenceStore() {
		return UIModelPlugin.getDefault().getPreferenceStore();
	}
	
	private static String getShortDescription(ICreationTool tool) {
		return tool.getDescription();
	}
	
	private static ImageDescriptor getLargeIconDescriptor(ICreationTool tool) {
		ImageDescriptor imageDescriptor = tool.getIcon24();
		if (imageDescriptor != null)
			return imageDescriptor;
		
		return ImageDescriptor.createFromImage(EditorUtils.getUnknownLargeIcon());
	}

	private static ImageDescriptor getSmallIconDescriptor(ICreationTool tool) {
		ImageDescriptor imageDescriptor = tool.getIcon16();
		if (imageDescriptor != null)
			return imageDescriptor;
		
        return ImageDescriptor.createFromImage(EditorUtils.getUnknownSmallIcon());
	}
	
	private static String getCategory(ICreationTool tool) {
		return tool.getCategory();
	}

	private static Set getDataModelContainers(IDesignerDataModel dataModel) {
		final Set containers = new HashSet();
		ComponentInstanceVisitor.Visitor visitor = new ComponentInstanceVisitor.Visitor() {
			public Object visit(IComponentInstance ci) {
				IContainer container = Adapters.getContainer(ci.getEObject());
				if (container != null)
					containers.add(container);
				
				return null;
			}
		};
		
		ComponentInstanceVisitor.preOrderTraversal(dataModel.getRootContainers(), visitor);
		
		return containers;
	}
	
	/*
	 * Allow each container in the model to veto the components in the component set
	 * and return only those components allowed by those containers
	 */
	private static Set filterComponentsByContainersInModel(IDesignerDataModel model, 
			IComponentSet cs, Set checkedContainers, boolean restrictive) {
		Set includedComponents = new HashSet();
		Set containers = getDataModelContainers(model);
		// iterate the components first, so we can break when some container allows it
		for (Iterator componentIter = cs.iterator(); componentIter.hasNext();) {
			IComponent component = (IComponent) componentIter.next();
			for (Iterator containerIter = containers.iterator(); containerIter.hasNext();) {
				IContainer container = (IContainer) containerIter.next();
				IComponent containerComponent = Adapters.getComponent(container.getEObject());
				checkedContainers.add(containerComponent.getId());
				boolean allowedByContainer = restrictive ? 
						container.canContainComponent(component, null) :
						container.isValidComponentInPalette(component);
				if (allowedByContainer) {
					includedComponents.add(component.getId());
					break;
				}
			}
		}
		
		return includedComponents;
	}
	
	/*
	 * Each of the included components can itself be a container and it may allow more components to be included.
	 * Create a temporary model to instantiate the container components to get their IQueryContainment adapter.
	 * Filter the component set by any containers found in the component set.
	 */
	private static Set filterByComponentContainers(IDesignerDataModel model, IComponentSet cs, 
								Set includedComponents, Set checkedContainers) {
		// only test component that have not been included already
		Set testComponents = new HashSet();
		for (Iterator iter = cs.iterator(); iter.hasNext();) {
			IComponent component = (IComponent) iter.next();
			String componentId = component.getId();
			if (!includedComponents.contains(componentId))
				testComponents.add(componentId);
		}
		
		Set filteredComponents = null;
		try {
			IDesignerDataModel tempModel = model.getProvider().createCompatibleTemporary(model);
			filteredComponents = filterByComponentContainers(tempModel, cs, 
								includedComponents, checkedContainers, testComponents);
			tempModel.dispose();
		} 
		catch (CoreException e) {
			String message = "PaletteFactory failed to create a temporary data model: "; //$NON-NLS-1$
			IStatus status = Logging.newSimpleStatus(0, IStatus.ERROR, message + e.getMessage(), e);
			Logging.log(UIDesignerPlugin.getDefault(), status);
		}
		
		return filteredComponents;
	}
	
	private static boolean isContainerComponent(IComponent component) {
		IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
		return attributes.getBooleanAttribute(CommonAttributes.IS_LAYOUT_CONTAINER, false);
	}
	
	private static IQueryContainment getQueryContainmentAdapter(IDesignerDataModel model, IComponent component) {
		// create the container instance in the model
		EObject object = model.createRootContainerInstance(component);
		return (IQueryContainment) EcoreUtil.getRegisteredAdapter(object, IQueryContainment.class);
	}
	
	private static boolean isValidComponentInPalette(IComponent component) {
        IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
        return !component.isAbstract() &&
            !attributes.getBooleanAttribute(CommonAttributes.NOT_IN_TOOLBOX, false);
	}

	private static Set setDifference(Set set1, Set set2) {
		Set difference = new HashSet();
		difference.addAll(set1);
		difference.removeAll(set2);
		
		return difference;
	}
	
	/*
	 * Use the data model to create instances of components that are containers and test the test components.
	 */
	private static Set filterByComponentContainers(IDesignerDataModel model, IComponentSet cs,
								Set includedComponents, Set checkedContainers, Set currentTestComponents) {

		// only test components that have not been included already
		Set testComponents = setDifference(currentTestComponents, includedComponents);
		// only check included components that have not been checked as containers already
		Set checkPotentialContainers = setDifference(includedComponents, checkedContainers);
		
		// for every container included, check if it will allow any of the test components
		for (Iterator iter = checkPotentialContainers.iterator(); iter.hasNext();) {
			String potentialContainerId = (String) iter.next();
			IComponent potentialContainer = cs.lookupComponent(potentialContainerId);
			if (isContainerComponent(potentialContainer)) {
				checkedContainers.add(potentialContainerId);
				IQueryContainment queryContainment = getQueryContainmentAdapter(model, potentialContainer);
				if (queryContainment == null)
					continue;
				
				// for every test component, if valid, add to the included set
				// if valid component is a container, add
				for (Iterator testIter = testComponents.iterator(); testIter.hasNext();) {
					String componentId = (String) testIter.next();
					IComponent component = cs.lookupComponent(componentId);
					if (queryContainment.isValidComponentInPalette(component)) {
						if (!includedComponents.contains(componentId)) {
							includedComponents.add(componentId);
							includedComponents.addAll(
									filterByComponentContainers(model, cs,
											includedComponents, checkedContainers, testComponents));
						}
					}
				}
			}
		}
		
		return includedComponents;
	}

	/**
	 * Creates the PaletteRoot and adds all palette elements. Use this factory
	 * method to create a new palette for your graphical editor.
	 * 
	 * @return a new PaletteRoot
	 */
	public static PaletteRoot createPalette(IDesignerEditor editor, 
				List<String> openDrawerNames, boolean closeDrawers, boolean restrictive) {
		PaletteRoot palette = new PaletteRoot();
		palette.add(createToolsGroupAndDefaultEntry(palette, editor));
		IDesignerDataModel dataModel = editor.getDataModel();
		
        if (dataModel == null)
            return palette;
        
		IComponentSet cs = dataModel.getComponentSet();
//		for (IComponent component : cs) {
//			System.out.println(component.toString());
//		}

		Set checkedContainers = new HashSet();
		Set includedComponentIds = filterComponentsByContainersInModel(dataModel, cs, checkedContainers, restrictive);
		if (!restrictive)
			includedComponentIds = filterByComponentContainers(dataModel, cs, includedComponentIds, checkedContainers);
		
		Set<ICreationTool> includedCreationTools = new HashSet();
		for (Iterator iter = includedComponentIds.iterator(); iter.hasNext();) {
			IComponent component = cs.lookupComponent((String) iter.next());
			if (isValidComponentInPalette(component)) {
				includedCreationTools.add(new ComponentCreationTool(component));
			}
		}
		
		includedCreationTools.addAll(getCreationToolsFromProviders(dataModel, editor));
		
		// organize the tools into a category->List<ICreationTool> map
		SortedMap<String, List<ICreationTool>> toolMap = new TreeMap();
		for (Iterator<ICreationTool> iter = includedCreationTools.iterator(); iter.hasNext();) {
			ICreationTool tool = iter.next();
			String category = getCategory(tool);
			if (category == null)
				category = UNCATEGORIZED;
			if (!toolMap.containsKey(category)) {
				List<ICreationTool> toolList = new ArrayList();
				toolMap.put(category, toolList);
			}
			
			List<ICreationTool> toolList = toolMap.get(category);
			toolList.add(tool);
		}
		
		if (openDrawerNames == null)
			openDrawerNames = getContainerOpenDrawers(dataModel);
		
		// add drawers and entries
		for (Iterator<String> categoryIterator = toolMap.keySet().iterator(); categoryIterator.hasNext();) {
			String category = categoryIterator.next();
			PaletteDrawer drawer = new PaletteDrawer(category);
			boolean keepOpen = (openDrawerNames != null) && openDrawerNames.contains(category);
			if (closeDrawers && !keepOpen)
				drawer.setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
			List<ICreationTool> toolList = toolMap.get(category);
			Collections.sort(toolList, new Comparator<ICreationTool>() {
				public int compare(ICreationTool t1, ICreationTool t2) {
					String name1 = (t1 == null ? null : t1.getLabel());
					String name2 = (t2 == null ? null : t2.getLabel());
					if (name1 == null && name2 == null)
						return 0;
					if (name1 == null)
						return 1;
					if (name2 == null)
						return -1;
					return name1.compareTo(name2);
				}
			});
			for (Iterator<ICreationTool> iter = toolList.iterator(); iter.hasNext();) {
				ICreationTool tool = iter.next();
				CombinedTemplateCreationEntry entry = 
					new CreationToolEntry(getLabel(tool), 
							getShortDescription(tool), tool,
							new CreationFactory(tool), 
							getSmallIconDescriptor(tool), 
							getLargeIconDescriptor(tool));

				drawer.add(entry);
			}
			palette.add(drawer);
		}
		
		return palette;
	}
	
	private static Collection<ICreationTool> getCreationToolsFromProviders(IDesignerDataModel dataModel, final IDesignerEditor editor) {
		final Collection<ICreationTool> tools = new ArrayList();
		
		ComponentInstanceVisitor.preOrderTraversal(dataModel.getRootContainers(), new Visitor() {
			public Object visit(IComponentInstance ci) {
				ICreationToolProvider provider = Adapters.getCreationToolProvider(ci.getEObject());
				if (provider != null)
					tools.addAll(provider.getCreationTools(editor));
				
				return null;
			}
		});
		return tools;
	}

	private static List<String> getContainerOpenDrawers(IDesignerDataModel dataModel) {
		List<String> allOpenDrawers = new ArrayList();
		Set dataModelContainers = getDataModelContainers(dataModel);
		for (Iterator<IContainer> iter = dataModelContainers.iterator(); iter.hasNext();) {
			IAttributes attributes = Adapters.getAttributes(iter.next().getEObject());
			String attribute = attributes.getAttribute(CommonAttributes.ASSOCIATED_PALETTE_DRAWER);
			if (attribute != null) {
				IComponent component = attributes.getComponent();
				String categoryText = component.getProvider().getCategoryText(attribute);
				allOpenDrawers.add(categoryText);
			}
		}
		
		return allOpenDrawers;
	}
	
	private static String getLabel(ICreationTool tool) {
		return tool.getLabel();
	}

	/** Create the "Tools" group. */
	private static PaletteContainer createToolsGroupAndDefaultEntry(PaletteRoot palette, final IDesignerEditor editor) {
		PaletteGroup toolGroup = new PaletteGroup(Strings.getString("PaletteFactory.ToolsGroupName")); //$NON-NLS-1$

		// Add a selection tool to the group
		ToolEntry tool = new SelectionToolEntry() {
			private Tool selectionTool;
			/* (non-Javadoc)
			 * @see org.eclipse.gef.palette.SelectionToolEntry#createTool()
			 */
			public Tool createTool() {
				if (selectionTool == null) {
					selectionTool = new ViewEditorSelectionTool(editor);
					editor.getEditDomain().setDefaultTool(selectionTool);
				}
				return selectionTool;
			}
			
		};
		toolGroup.add(tool);
		palette.setDefaultEntry(tool);

		// Add a marquee tool to the group
		toolGroup.add(new MarqueeToolEntry(
				Strings.getString("PaletteFactory.MarqueeToolLabel"), //$NON-NLS-1$ 
				Strings.getString("PaletteFactory.MarqueeToolDesc"))); //$NON-NLS-1$

		// Add a (unnamed) separator to the group
		toolGroup.add(new PaletteSeparator());

		return toolGroup;
	}

}
