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

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.XMLLookAndFeel;
import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.sdt.ui.skin.ISkin;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Bundle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;

/**
 * This display model supports UIQ semantics.
 * <ul> 
 * <li>Layout data is stored transiently in layout objects and containers based
 * on the actions of a layout manager.  The layout manager is referenced by the top-level
 * content container, which implements ILayout by iterating children and applying
 * the particular layout manager algorithm to them.  (The actual algorithm is more
 * complicated, involving a first pass to query desired sizes of children, then 
 * adapting those sizes to the constraints of the container, then applying.) 
 * Layout managers may exist on every container and containers may be nested.  
 * </li>
 * <li>Layout area configurations are generated based on the 'UI Configurations'
 * group in the root container (View or Dialog).  UI Configurations may be created
 * or deleted or modified while editing the design.  One UI Configuration is set
 * as the default by a user action.
 * </li>  
 * The display model has an associated set of LAF, skin, font, and image
 * files.  These are all located in the com.nokia.carbide.cpp.uiq.components
 * plugin under a 'data' directory, or under this component under a 'data/uiq'
 * directory if the former is not found.
 * 
 *
 */
public class DisplayModelUIQ extends DisplayModelBase {

	private static final String DEFAULT_LAYOUT_SKIN_NAME = "KQikSoftkeyStylePortrait"; //$NON-NLS-1$
	private static final String UIQ_COMPONENTS_PLUGIN_ID = "com.nokia.carbide.cpp.uiq.components"; //$NON-NLS-1$
	/**
	 * 
	 */
	public DisplayModelUIQ() {
		super();
	}

	@Override
	protected URL getBaseDataURL() throws IOException {
		// these files are relative to the UIQ components plugin
		URL url = null;
		Bundle bundle = Platform.getBundle(UIQ_COMPONENTS_PLUGIN_ID);
		if (bundle != null) {
			url = FileLocator.find(bundle, new Path("data/"), null); //$NON-NLS-1$
		}
		if (url == null) {
			ComponentSystemPlugin.log(new FileNotFoundException("Cannot find 'data' folder in plugin " + UIQ_COMPONENTS_PLUGIN_ID + ", using " + ComponentSystemPlugin.PLUGIN_ID + "/data/uiq instead")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			bundle = ComponentSystemPlugin.getDefault().getBundle();
			if (bundle != null) {
				url = FileLocator.find(bundle, new Path("data/uiq/"), null); //$NON-NLS-1$
			}
			if (url == null) {
				ComponentSystemPlugin.log(new FileNotFoundException("Cannot find 'data/uiq' folder in plugin " + ComponentSystemPlugin.PLUGIN_ID)); //$NON-NLS-1$
			}
		}
		return url;
	}
	
	class LayoutConfigurationUIQ extends LayoutConfiguration {

		private EObject eObject;

		LayoutConfigurationUIQ(ISkin skin, ILookAndFeel laf, EObject object) {
			super(skin, laf);
			this.eObject = object;
		}
		
		public EObject getEObject() {
			return eObject;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.displaymodel.DisplayModelBase#initConfigurations()
	 */
	@Override
	protected void initConfigurations() throws CoreException {
		configurations.clear();

		LayoutAreaConfiguration defaultLC = null;

		EObject[] roots = dataModel.getRootContainers();
		EObject root = null;
		if (roots.length > 0) {
			root = roots[0];
		}
		
		EObject configurationsGroup = ModelUtils.findFirstComponentInstance(root, UIQModelUtils.UIQ_VIEW_CONFIGURATIONS_GROUP, false);
		if (configurationsGroup == null)
			configurationsGroup = ModelUtils.findFirstComponentInstance(root, UIQModelUtils.UIQ_SIMPLEDIALOG_CONFIGURATIONS_GROUP, false);
		if (configurationsGroup == null) {
			String fmt = Messages.getString("DisplayModelUIQ.NoUIConfigurationGroupsAvailable"); //$NON-NLS-1$
			Logging.log(ComponentSystemPlugin.getDefault(), 
					Logging.newStatus(ComponentSystemPlugin.getDefault(),
							IStatus.ERROR, fmt, null));
		} else {
		
			// remember what configuration was in place before
			LayoutAreaConfiguration currentConfig = getCurrentConfiguration();
			
			EObject[] uiconfigs = ModelUtils.getComponentInstance(configurationsGroup).getChildren();
			for (EObject object : uiconfigs) {
				LayoutConfigurationUIQ lac = createLayoutAreaConfiguration(object);
				if (lac != null) {
					configurations.add(lac);
					
					// try to keep the same default as before, when refreshing
					if (currentConfig != null && lac.equals(currentConfig)) {
						defaultLC = lac;
					}
				}
			}
		}
		
		if (defaultLC == null) {
			if (configurations.size() == 0) {
				// emit an error then create something to let us continue
				String fmt = Messages.getString("DisplayModelUIQ.NoUIConfigurationsAvailable"); //$NON-NLS-1$
				String name = configurationsGroup != null 
					? ModelUtils.getComponentInstance(configurationsGroup).getName() : 
						"null"; //$NON-NLS-1$;
				Object params[] = {name};
				String msg = MessageFormat.format(fmt, params);
				Logging.log(ComponentSystemPlugin.getDefault(), 
						Logging.newStatus(ComponentSystemPlugin.getDefault(),
								IStatus.ERROR, msg, null));
				
				// try to make a configuration for a basic layout, as a fallback
				defaultLC = createLayoutAreaConfigurationForEnum(DEFAULT_LAYOUT_SKIN_NAME, null);
				if (defaultLC == null) {
					// nothing available at all!  make a dummy configuration
					defaultLC = new LayoutConfigurationUIQ(createDummySkin(), createDummyLookAndFeel(), null);
				}
				configurations.add(defaultLC);
			}
			defaultLC = (LayoutConfiguration) configurations.get(0);
		}
		
		setCurrentConfiguration(defaultLC);
	}

	/**
	 * Read the given UI configuration instance, take its uiconfig property, and use its internal
	 * value to locate a skin and laf file, then create a layout configuration form that.
	 * @param componentInstance
	 * @return LayoutConfigurationUIQ or null (error logged)
	 */
	protected LayoutConfigurationUIQ createLayoutAreaConfiguration(
			EObject componentInstance) {
		IPropertySource ps = ModelUtils.getPropertySource(componentInstance);
		if (ps == null) {
			ComponentSystemPlugin.log(new IllegalArgumentException("Could not get property source for " + componentInstance));
			return null;
		}
		
		// take the value of the enum property as the filename
		String layoutName = (String) ps.getPropertyValue(UIQModelUtils.UICONFIGURATION_UICONFIG);
		return createLayoutAreaConfigurationForEnum(layoutName, componentInstance);
	}

	private LayoutConfigurationUIQ createLayoutAreaConfigurationForEnum(
			String layoutName, EObject componentInstance) {
		if (layoutName == null)
			return null;
		
		URL skinURL = getSkinURL(layoutName + ".skin"); //$NON-NLS-1$ //$NON-NLS-2$
		URL lafURL = getLAFURL(layoutName + ".xml"); //$NON-NLS-1$ //$NON-NLS-2$
		
		ISkin skin = getSkin(skinURL);
		XMLLookAndFeel laf = getLookAndFeel(lafURL);
		if (skin != null && laf != null) {
			return new LayoutConfigurationUIQ(skin, laf, componentInstance);
		} else {
			// already reported error
			return null;
		}
	}


	/* (non-Javadoc)
	 * @see com.nokia.sdt.displaymodel.IDisplayModel#createContainer(com.nokia.sdt.datamodel.adapter.IComponentInstance)
	 */
	public IContainer createContainer(IComponentInstance ci) {
		return new Container(this, ci);
	}

	protected ILayoutContainer _createLayoutContainer(IComponentInstance ci) {
		// TODO: replace once a layout manager is added
		//return new LayoutContainerUsingCachedBounds(this, ci);
		return new LayoutContainerUsingLayoutProperties(this, ci);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.displaymodel.IDisplayModel#createLayoutObject(com.nokia.sdt.datamodel.adapter.IComponentInstance)
	 */
	public ILayoutObject createLayoutObject(IComponentInstance ci) {
		// TODO: replace once a layout manager is added
		//return new LayoutObjectUsingCachedBounds(this, ci);
		return new LayoutObjectUsingLayoutProperties(this, ci);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.displaymodel.IDisplayModel#getNonLayoutRoot()
	 */
	public EObject getNonLayoutRoot() {
		return dataModel.getRootContainers()[0];
	}

	@Override
	protected ComponentInstanceListener addComponentInstanceListener(
			IComponentInstance ci) {
		EObject obj = ci.getEObject();
		if (ModelUtils.isInstanceOf(obj, UIQModelUtils.UIQ_UI_CONFIGURATION)) {
			// check for changes to the current UI configuration
			return new ComponentInstanceListener(this, ci) {
				@Override
				public void propertyChanged(EObject componentInstance, Object propertyId) {
					super.propertyChanged(componentInstance, propertyId);
					
					LayoutConfigurationUIQ layoutConfig = (LayoutConfigurationUIQ) getCurrentConfiguration();
					if (layoutConfig != null
							&& ModelUtils.isSameComponentInstance(componentInstance, layoutConfig.getEObject())) {
						layoutPropertyChanged();
					}
				}
			};
		} else if (ModelUtils.isInstanceOf(obj, UIQModelUtils.UIQ_SIMPLEDIALOG_CONFIGURATIONS_GROUP)
				|| ModelUtils.isInstanceOf(obj, UIQModelUtils.UIQ_VIEW_CONFIGURATIONS_GROUP)) {
			// listen to children changing
			EObject[] kids = ci.getChildren();
			for (EObject kid : kids) {
				IComponentInstance cikid = ModelUtils.getComponentInstance(kid);
				addComponentInstanceListener(cikid);
			}
			
			// check for UI Configurations being added or removed
			return new ComponentInstanceListener(this, ci) {
				@Override
				public void childAdded(EObject parent, EObject child) {
					super.childAdded(parent, child);
					layoutPropertyChanged();
				}
				
				@Override
				public void childRemoved(EObject parent, EObject child) {
					super.childRemoved(parent, child);
					layoutPropertyChanged();
				}
				
				@Override
				public void childrenReordered(EObject parent) {
					super.childrenReordered(parent);
					layoutPropertyChanged();
				}
			};
		} else {
			// nothing special
			return new ComponentInstanceListener(this, ci);
		}
	}
}
