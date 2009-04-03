/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.XMLLookAndFeel;
import com.nokia.sdt.displaymodel.adapter.*;
import com.nokia.sdt.symbian.S60ComponentAttributes;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.sdk.SdkUtilities;
import com.nokia.sdt.symbian.workspace.ISymbianDataModelSpecifier;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.ui.skin.ISkin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.nokia.sdt.workspace.IProjectContext;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier.IRunWithModelAction;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.CProjectDescriptionEvent;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Version;
import org.w3c.dom.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

import javax.xml.parsers.*;

/**
 * This display model supports S60 semantics.
 * <ul> 
 * <li>Layout data is stored on each instance using a location and size property.
 * The layout management is typically controlled by a container, which implements
 * ILayout by iterating its children and setting these properties.  
 * </li>
 * <li>Layout area configurations are generated based on the root container's
 * 'supported-layouts' attribute, which corresponds to skin and look and feel
 * files in this plugin. Other properties like 'layoutAware' and 'layoutConfig'
 * may be used to restrict or force the layout.  The content of such skins and LAFs otherwise
 * remain constant between loads of the component system.   One UI Configuration is set
 * as the default by a user action.
 * </li>  
 * </ul>
 * The display model has an associated set of LAF, skin, font, and image
 * files.  These are all located in this plugin under a 'data/s60' directory.
 * 
 *
 */
public class DisplayModelS60 extends DisplayModelBase implements IDisplayModel {

	/**
	 * This file contains per sdk layouts as a semicolon-delimited list of
	 * layout names supported by a container component. These names correspond
	 * to various skins supported by the Symbian UI designer.
	 */
	private static final String SUPPORTED_LAYOUTS_FILE = "supportedLayouts.xml"; //$NON-NLS-1$
	private static final String MAX_SDK_VERSION_ATTRIB = "maxSDKVersion"; //$NON-NLS-1$
	private static final String MIN_SDK_VERSION_ATTRIB = "minSDKVersion"; //$NON-NLS-1$
	private static final String LAYOUT_ELEMENT = "layout"; //$NON-NLS-1$
	
	static public final String LAYOUT_CONFIG_PROPERTY = "layoutConfig"; //$NON-NLS-1$
	static final String LAYOUT__CONFIG_PROPERTY_VARIABLE = "variable"; //$NON-NLS-1$
	static public final String LAYOUT_AWARE_PROPERTY = "layoutAware"; //$NON-NLS-1$
	static public final String ORIENTATION_PROPERTY = "orientation"; //$NON-NLS-1$
	static public final String ORIENTATION_PORTRAIT = "portrait"; //$NON-NLS-1$
	static public final String ORIENTATION_LANDSCAPE = "landscape"; //$NON-NLS-1$
	static public final String PORTRAIT_VALUE = "EAppUiOrientationPortrait"; //$NON-NLS-1$
	static public final String LANDSCAPE_VALUE = "EAppUiOrientationLandscape"; //$NON-NLS-1$
	
	private ICProjectDescriptionListener projectDescListener;
	
	public DisplayModelS60() {
		super();
	}
	
	@Override
	protected URL getBaseDataURL() throws IOException {
		return getDataURL();
	}
	
	private static URL getDataURL() throws IOException {
		return FileLocator.find(ComponentSystemPlugin.getDefault().getBundle(), 
				new org.eclipse.core.runtime.Path("data/s60/"), null); //$NON-NLS-1$
	}
	
	public void initialize(IDesignerDataModel dataModel, EObject rootContainer) throws CoreException {
		super.initialize(dataModel, rootContainer);
		IProjectContext projectContext = dataModel.getProjectContext();
		final CoreException[] exceptionHolder = { null };
		if (projectContext != null) {
			final IProject project = projectContext.getProject();
			CoreModel cModel = CCorePlugin.getDefault().getCoreModel();
			cModel.addCProjectDescriptionListener(projectDescListener = new ICProjectDescriptionListener() {
				public void handleEvent(CProjectDescriptionEvent event) {
					try {
						if (event.getProject().equals(project)) {
							if (configurations != null)
								initConfigurations();
						}
					} catch (CoreException e) {
						exceptionHolder[0] = e;
					}
				}
			}, CProjectDescriptionEvent.APPLIED);
		}
	}
	
	public void dispose() {
		if (projectDescListener != null) {
			CoreModel cModel = CCorePlugin.getDefault().getCoreModel();
			cModel.removeCProjectDescriptionListener(projectDescListener);
		}
			
		super.dispose();
	}
	
	private LayoutConfiguration createConfigurationFromName(String layoutName) {
		// For each layout name we look for a matching skin and laf file.
		// If both exist then the layout is added to the set of layout configurations
		// The ISkin and XMLLookAndFeelInstances are managed in caches kept in the
		// global cache. Therefore these instances are shared, and freed when the last
		// display model goes away.
		URL skinURL = getSkinURL(layoutName + ".skin"); //$NON-NLS-1$ 
		URL lafURL = getLAFURL(layoutName + ".xml"); //$NON-NLS-1$ //$NON-NLS-2$
		
		ISkin skin = getSkin(skinURL);
		XMLLookAndFeel laf = getLookAndFeel(lafURL);
		if (skin != null && laf != null)
			return new LayoutConfiguration(skin, laf);
			
		return null;
	}
	
	private void noValidConfigurationsError() throws CoreException {
		String fmt = Messages.getString("DisplayModelS60.NoLayoutsAvailable"); //$NON-NLS-1$
		String componentID = ModelUtils.getComponentInstance(rootContainer).getComponentId();
		Object params[] = {componentID};
		String msg = MessageFormat.format(fmt, params);
		Logging.log(ComponentSystemPlugin.getDefault(), 
				Logging.newStatus(ComponentSystemPlugin.getDefault(),
						IStatus.ERROR, msg, null));

		// make a dummy configuration
		LayoutConfiguration dummyLC = new LayoutConfiguration(createDummySkin(), createDummyLookAndFeel());
		configurations.add(dummyLC);
		setCurrentConfiguration(dummyLC);
	}

	protected void initConfigurations() throws CoreException {
		configurations.clear();
		String[] layoutNames = getAllSupportedLayoutNames(dataModel);
		if (layoutNames != null && layoutNames.length > 0) {
			for (int i = 0; i < layoutNames.length; i++) {
				LayoutConfiguration lc = createConfigurationFromName(layoutNames[i]);
				if (lc != null)
					configurations.add(lc);			
			}
		}
			
		if (configurations.size() > 0) {
			Collections.sort(configurations, new Comparator<LayoutAreaConfiguration>() {
				public int compare(LayoutAreaConfiguration l1, LayoutAreaConfiguration l2) {
					Rectangle r1 = l1.getSkin().getEditorAreaBounds();
					Rectangle r2 = l2.getSkin().getEditorAreaBounds();
					int result = (r1.width + r1.height) - (r2.width + r2.height);
					if (result == 0)
						result = r1.width - r2.width;
					if (result == 0)
						result = r1.height - r2.height;
					if (result == 0)
						result = l1.getDisplayName().compareTo(l2.getDisplayName());
					return result;
				}
			});
			LayoutConfiguration defaultLC = matchLayoutConfigurationToRoot();
			if (defaultLC == null) {
				Collection<LayoutAreaConfiguration> configurations = getLayoutAreaConfigurations();
				defaultLC = (LayoutConfiguration) configurations.iterator().next();
			}
			setCurrentConfiguration(defaultLC);
		}
		else {
			noValidConfigurationsError();
		}
	}
	
	private static String[] getAllSupportedLayoutNames(IDesignerDataModel dataModel) {
		NodeList layoutNodes = getSupportedLayoutsFileNodes();
		if (layoutNodes == null)
			return null;
		
		Set<String> layoutsSet = new HashSet();
		IProjectContext projectContext = dataModel.getProjectContext();
		if (projectContext != null) {
			IProject project = projectContext.getProject();
			
			// get from sdks in project
			List<ISymbianSDK> sksInProject = SdkUtilities.getSDKsUsedInProject(project);
			for (ISymbianSDK symbianSDK : sksInProject) {
				String[] layouts = getSupportedLayoutsFromSDKVersion(layoutNodes, symbianSDK.getSDKVersion());
				if (layouts != null)
					layoutsSet.addAll(Arrays.asList(layouts));
			}
		}
		
		// get from baseline sdk as well - in case sdk doesn't have sdk version set
		String versionStr = SymbianModelUtils.getSDKVersion(dataModel);
		if (versionStr != null) {
			Version sdkVersion = new Version(versionStr);
			String[] layouts = getSupportedLayoutsFromSDKVersion(layoutNodes, sdkVersion);
			layoutsSet.addAll(Arrays.asList(layouts));
		}
		
		return (String[]) layoutsSet.toArray(new String[layoutsSet.size()]);
	}
	
	private static NodeList getSupportedLayoutsFileNodes() {
		File layoutsFile = null;
		try {
			URL url = new URL(getDataURL(), SUPPORTED_LAYOUTS_FILE);
			layoutsFile = new File(FileLocator.toFileURL(url).getFile());
		} catch (IOException e) {
			Logging.log(ComponentSystemPlugin.getDefault(), Logging.newSimpleStatus(IStatus.ERROR, e));
			return null;
		}
		
		Document document = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = db.parse(layoutsFile);
		} catch (ParserConfigurationException e) {
			Check.checkContract(false); // should never occur
		} catch (Exception e) {
			Logging.log(ComponentSystemPlugin.getDefault(), Logging.newSimpleStatus(IStatus.ERROR, e));
			return null;
		}
		
		return document.getElementsByTagName(LAYOUT_ELEMENT);
	}
	
	private static String[] getSupportedLayoutsFromSDKVersion(NodeList layoutNodes, Version sdkVersion) {
		String value = null;
		for (int i = 0; i < layoutNodes.getLength(); i++) {
			Element layout = (Element) layoutNodes.item(i);
			Version min = null;
			String minStr = layout.getAttribute(MIN_SDK_VERSION_ATTRIB);
			if (minStr != null && minStr.length() > 0)
				min = new Version(minStr);
			if (min == null || min.compareTo(sdkVersion) <= 0) {
				Version max = null;
				String maxStr = layout.getAttribute(MAX_SDK_VERSION_ATTRIB);
				if (maxStr != null && maxStr.length() > 0)
					max = new Version(maxStr);
				if (max == null || max.compareTo(sdkVersion) >= 0) {
					value = layout.getTextContent();
					break;
				}
			}
		}
		
		if (value != null) {
			return value.trim().split(";"); //$NON-NLS-1$
		}
		
		return null;
	}
	
	@Override
	public Collection<LayoutAreaConfiguration> getLayoutAreaConfigurations() {
		return getRootContainerSupportedLayouts();
	}
	
	public Collection<LayoutAreaConfiguration> getAllLayoutAreaConfigurations() {
		return configurations;
	}
	
	// Assumes the layout strings have the words "portrait" or "landscape" in the name
	// and those that don't have either one, matches both!!
	private static void getOrientationLayouts(List<LayoutAreaConfiguration> configurations, 
						boolean portrait, ArrayList<LayoutAreaConfiguration> specifiedLayouts) {
		// match the opposite of requested, and add to specified if doesn't match
		String unMatchString = portrait ? ORIENTATION_LANDSCAPE : ORIENTATION_PORTRAIT;
		for (LayoutAreaConfiguration configuration : configurations) {
			if (configuration.getID().indexOf(unMatchString) == -1)
				specifiedLayouts.add(configuration);
		}
	}
	
	private static LayoutAreaConfiguration findConfigurationById(
						List<LayoutAreaConfiguration> configurations, String id) {
		for (LayoutAreaConfiguration configuration : configurations) {
			if (configuration.getID().equals(id))
				return configuration;
		}
		
		return null;
	}
	
	// Search the display model roots for "layoutAware" property. If found to exist and "false"
	// get the non-layout-aware attribute and make it the only forced layout.
	// If no layout aware property found, or it has value of "true", 
	// then search for "orientation" property, if found and not set to "unspecified"
	// return all layouts that either conform to portrait or landscape, depending on setting.
	private static void getForcedLayouts(List<LayoutAreaConfiguration> configurations,
						IDesignerDataModel dataModel, ArrayList<LayoutAreaConfiguration> forcedLayouts) {
		EObject[] roots = dataModel.getRootContainers();
		for (int i = 0; i < roots.length; i++) {
			IPropertySource ps = ModelUtils.getPropertySource(roots[i]);
			Object pv = ps.getPropertyValue(LAYOUT_AWARE_PROPERTY);
			if (pv != null && pv.equals(Boolean.FALSE)) {
				IComponent component = ModelUtils.getComponentInstance(roots[i]).getComponent();
				if (component != null) {
					IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
					if (attr != null) {
						String layout = 
							attr.getAttribute(S60ComponentAttributes.NON_LAYOUT_AWARE_LAYOUT);
						LayoutAreaConfiguration c = findConfigurationById(configurations, layout);
						if (c != null) {
							forcedLayouts.add(c);
							return;
						}
						break;
					} 
				}
			}
		}
		for (int i = 0; i < roots.length; i++) {
			IPropertySource ps = ModelUtils.getPropertySource(roots[i]);
			Object pv = ps.getPropertyValue(ORIENTATION_PROPERTY);
			if (pv != null) {
				if (pv.equals(PORTRAIT_VALUE)) {
					getOrientationLayouts(configurations, true, forcedLayouts);
				}
				else if (pv.equals(LANDSCAPE_VALUE)) {
					getOrientationLayouts(configurations, false, forcedLayouts);
				}
				break;
			}
		}
	}
	
		// Return the layouts supported by the root container. 
	public Collection<LayoutAreaConfiguration> getRootContainerSupportedLayouts() {
		final ArrayList<LayoutAreaConfiguration> layouts = new ArrayList();
		
		ISymbianDataModelSpecifier modelSpecifier = 
			(ISymbianDataModelSpecifier) dataModel.getModelSpecifier();
		if ((modelSpecifier != null) && modelSpecifier.isRoot()) {
			getForcedLayouts(configurations, dataModel, layouts);
		}
		else {
			ISymbianProjectContext spctx = dataModel.getProjectContext() != null ?
				(ISymbianProjectContext) dataModel.getProjectContext().getAdapter(ISymbianProjectContext.class) : null;
			if (spctx != null) {
				IDesignerDataModelSpecifier rootSpec = spctx.getRootModel();
				if (rootSpec != null) {
					rootSpec.runWithLoadedModelNoSourceGen(new IRunWithModelAction() {
						public Object run(LoadResult lr) {
							if (lr.getModel() != null) {
								getForcedLayouts(configurations, lr.getModel(), layouts);
							}
							return null;
						} 
					});
				}
			}
		}

		if (!layouts.isEmpty()) {
			// If not layout aware, use the forced layout
			return layouts;
		} 
		else {

			// Look for a 'layout' property on the root container. If there is none,
			// or its value is 'variable' then there's no restriction. Otherwise the
			// value is the name of the only allowed layout
			IPropertySource ps = ModelUtils.getPropertySource(rootContainer);
			Object pv = ps.getPropertyValue(LAYOUT_CONFIG_PROPERTY);
			if (pv != null && !(LAYOUT__CONFIG_PROPERTY_VARIABLE.equals(pv)) && pv.toString().length() != 0) {
				String layoutId = pv.toString();
				layouts.add(findConfigurationById(configurations, layoutId));
			}
			else {
				// Layouts are not restricted by the 'layout' property. Now get the list of supported
				// layouts from a component attribute
				layouts.addAll(configurations);
			}
		}
		return layouts;
	}
	
		// find first skin that matches the root container bounds
	private LayoutConfiguration matchLayoutConfigurationToRoot() {
		LayoutConfiguration result = null;
		if (rootContainer != null) {
			ILayoutObject rootLO = ModelUtils.getLayoutObject(rootContainer);
			if (rootLO != null) {
				Rectangle bounds = rootLO.getBounds();
				Collection<LayoutAreaConfiguration> configurations = getLayoutAreaConfigurations();
				for (Iterator iter = configurations.iterator(); iter.hasNext();) {
					LayoutConfiguration lc = (LayoutConfiguration) iter.next();
					ISkin skin = lc.getSkin();
					if (skin != null) {
						Rectangle skinBounds = skin.getEditorAreaBounds();
						// we don't care about top left of either rectangle
						if (bounds.width == skinBounds.width && 
							bounds.height == skinBounds.height) {
							result = lc;
							break;
						}
					}
				}
			}
		}
		return result;
	}

	boolean hasOptionsMenuProperty(EObject container) {
		if (container == null)
			return false;
		
		return Utilities.getStringAttribute(container, 
				S60ComponentAttributes.OPTIONS_MENU_PROPERTY_NAME) != null;
	}

	public IContainer createContainer(IComponentInstance ci) {
		return new Container(this, ci);
	}
	
	protected ILayoutContainer _createLayoutContainer(IComponentInstance ci) {
		return new LayoutContainerUsingLayoutProperties(this, ci);
	}
	
	public ILayoutObject createLayoutObject(IComponentInstance ci) {
		return new LayoutObjectUsingLayoutProperties(this, ci);
	}
	
	public EObject getNonLayoutRoot() {
		// Assume that either the root container or the content container will
		// be the parent of the non-layout objects. 
		// Check for the existence of the optionsMenu property.
		// If only one has it, it will be the non-layout root.
		// If both or neither has it, use the content container.
		
		EObject rootContainer = dataModel.getRootContainers()[0];
		EObject topLevelContentContainer = getContentContainer();
		
		if (hasOptionsMenuProperty(topLevelContentContainer))
			return topLevelContentContainer;
		
		if (hasOptionsMenuProperty(rootContainer))
			return rootContainer;
		
		return topLevelContentContainer;
	}
	
	@Override
	protected ComponentInstanceListener addComponentInstanceListener(
			IComponentInstance ci) {
		return new ComponentInstanceListener(this, ci) {
			@Override
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				super.propertyChanged(componentInstance, propertyId);
				// Check for size or position change
				if (Utilities.LOCATION_PROPERTY.equals(propertyId)
						|| Utilities.SIZE_PROPERTY.equals(propertyId)) {
					Utilities.fireBoundsChanged(componentInstance);
				}
				// Check for layout property change on root container
				else if (propertyId.equals(DisplayModelS60.LAYOUT_CONFIG_PROPERTY)
						|| propertyId.equals(DisplayModelS60.LAYOUT_AWARE_PROPERTY)
						|| propertyId.equals(DisplayModelS60.ORIENTATION_PROPERTY)) {
					ILayoutContainer lc = ModelUtils.getLayoutContainer(componentInstance);
					if (lc != null && displayModel.isRootContainer(lc)) {
						displayModel.layoutPropertyChanged();
					}
				}
			}
		};
	}

}
