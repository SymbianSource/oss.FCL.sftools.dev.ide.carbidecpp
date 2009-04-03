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


package com.nokia.carbide.cpp.uiq.components.layoutComponents;

import com.nokia.carbide.cpp.uiq.components.UIQComponentPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.symbian.dm.UIQModelUtils;

import org.eclipse.core.runtime.IStatus;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * A code based component library that exposes layout components as
 * a UIQ component library.
 */
public class ComponentLibrary implements IComponentLibrary {
	private static final String ROOT_DIR = "component";
	private static final String CONTROL_COLLECTION_ITEM_BASE = 
				"com.nokia.carbide.uiq.ControlCollectionItemBase"; //$NON-NLS-1$
	public static final String LAYOUT_CONTROL_BASE = 
				"com.nokia.carbide.uiq.LayoutControlBase"; //$NON-NLS-1$
	
	private static final String LAYOUT_COMPONENTS_VERSION = "1.0";

	private static final String LAYOUT_COMPONENT_SUFFIX = "_Layout"; //$NON-NLS-1$
	
	private static final ComponentFactory COMP_FACTORY = ComponentFactory.eINSTANCE;
	private static final Pattern UIQ_VENDOR_PATTERN = Pattern.compile("com\\.uiq"); //$NON-NLS-1$
	
	private IComponentProvider provider;
	private boolean isLoaded;
	private List<IComponent> components;
	
	public ComponentLibrary() {
		components = new ArrayList();
	}

	public IComponent[] getComponents(IComponentFilter filter) {
		List<IComponent> compList = new ArrayList();
		for (IComponent component : components) {
			if (filter.accept(component))
				compList.add(component);
		}
		
		return (IComponent[]) compList.toArray(new IComponent[compList.size()]);
	}

	public String getId() {
		return UIQComponentPlugin.ID;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public Iterator iterator() {
		return components.iterator();
	}
	
	static class UiqControlCollectionItemFilter extends StandardComponentLibraryFilter implements IComponentFilter {

		private IComponentLibrary me;

		public UiqControlCollectionItemFilter(IComponentLibrary me) {
			super(UIQModelUtils.UIQ_SDK); //$NON-NLS-1$
			this.me = me;
		}
		public boolean accept(IComponentLibrary lib) {
			// don't recurse
			if (lib == me)
				return false;
			
			// only look at UIQ libraries
			return super.accept(lib);
		}

		public boolean accept(IComponent xmlComponent) {
			// we can't check hierarchies here
			return true;
		}

		
	}
	public void loadComponents() throws ComponentSystemException {
		if (isLoaded)
			return;
		
		components.clear();
		ComponentSetResult csResult = 
				provider.queryComponents(new UiqControlCollectionItemFilter(this));
		
		// ignore errors: our library filtering may result in some broken components
		//IStatus status = csResult.getStatus();
		//Check.checkContract(status == null || status.isOK());
		
		IComponentSet cs = csResult.getComponentSet();
		for (IComponent xmlComponent : cs) {
			if (ModelUtils.isOfType(xmlComponent, CONTROL_COLLECTION_ITEM_BASE) &&
					!xmlComponent.isAbstract()) {
				components.add(createReferenceComponent(xmlComponent));
			}
		}

		isLoaded = true;
	}

	private IComponent createReferenceComponent(IComponent xmlComponent) throws ComponentSystemException {
		ComponentType componentType = COMP_FACTORY.createComponentType();

		componentType.setVersion(LAYOUT_COMPONENTS_VERSION);
		loadComponentInfo(xmlComponent, componentType);
	
		try {
			return EMFComponentLibraryFactory.createComponent(provider, componentType, 
					FileUtils.pluginRelativeFile(UIQComponentPlugin.getDefault(), ROOT_DIR), getBundle(), 
					createDelegatingLocalizedStrings(xmlComponent));
		}
		catch (IOException e) {
			throw new ComponentSystemException(Logging.newStatus(UIQComponentPlugin.getDefault(), e));
		}
	}

	private void loadComponentInfo(IComponent referent, ComponentType componentType) {
		// set base component
		componentType.setBaseComponent(LAYOUT_CONTROL_BASE);
		
		// set qualified name
		componentType.setQualifiedName(referent.getId() + LAYOUT_COMPONENT_SUFFIX);
		
		// set category and override abstract
		componentType.setCategory("layout controls");
		
		// copy friendly name
		componentType.setFriendlyName(referent.getFriendlyName());
		
		// copy designer images
		DesignerImagesType imagesType = COMP_FACTORY.createDesignerImagesType();
		copyDesignerImages(referent, imagesType);
		componentType.setDesignerImages(imagesType);

		// copy Symbian info
		SymbianType symbianType = COMP_FACTORY.createSymbianType();
		copySymbianInfo(referent, symbianType);
		componentType.setSymbian(symbianType);
		
		// copy attributes
		AttributesType attributesType = COMP_FACTORY.createAttributesType();
		copyAttributes(referent, attributesType);
		componentType.setAttributes(attributesType);
		
		// copy implementations
		ImplementationsType implementationsType = COMP_FACTORY.createImplementationsType();
		copyImplementations(referent, implementationsType);
		componentType.setImplementations(implementationsType);
		
		// copy events
		EventsType eventsType = COMP_FACTORY.createEventsType();
		copyEvents(referent, eventsType);
		componentType.setEvents(eventsType);
		
		// create sourcegen
		SourceGenType sourcegenType = COMP_FACTORY.createSourceGenType();
		UseTemplateGroupType useTemplateGroupType = COMP_FACTORY.createUseTemplateGroupType();
		useTemplateGroupType.setIds("*"); //$NON-NLS-1$
		sourcegenType.getUseTemplateGroup().add(useTemplateGroupType);
		componentType.setSourceGen(sourcegenType);
	}
	
	private ILocalizedStrings createDelegatingLocalizedStrings(final IComponent xmlComponent) {
		return new ILocalizedStrings() {
			public String checkPercentKey(String s) {
				String result = null;
				IComponent c = xmlComponent;
				while (c != null) {
					result = getLocalizedStrings(c).checkPercentKey(s);
					if (result == null || result.matches("!.+!"))
						c = c.getComponentBase();
					else
						break;
				}
				return result;
			}

			public String getString(String key) {
				String result = null;
				IComponent c = xmlComponent;
				while (c != null) {
					result = getLocalizedStrings(c).getString(key);
					if (result == null || result.matches("!.+!"))
						c = c.getComponentBase();
					else
						break;
				}
				return result;
			}

			public String getString(String key, Locale l) {
				String result = null;
				IComponent c = xmlComponent;
				while (c != null) {
					result = getLocalizedStrings(c).getString(key, l);
					if (result == null)
						c = c.getComponentBase();
					else
						break;
				}
				return result;
			}

			public boolean hasString(String key) {
				return getString(key) != null;
			}

			public boolean hasStringForLocale(String key, Locale l) {
				return getString(key, l) != null;
			}
		};
	}

	private ILocalizedStrings getLocalizedStrings(IComponent referent) {
		Check.checkContract(referent instanceof Component);
		// we have to go directly into the Component class, since this is not exposed via API
		Component c = (Component) referent;
		return c.getLocalizedStrings();
	}

	private void copyDesignerImages(IComponent referent, DesignerImagesType imagesType) {
		Check.checkContract(referent instanceof Component);
		// we have to go directly into the Component class, since this is not exposed via API
		Component c = (Component) referent;
		ComponentType referentComponentType = c.getEMFComponent();
		DesignerImagesType referentImages = referentComponentType.getDesignerImages();
		if (referentImages != null) {
			imagesType.setLargeIconFile(makeAbsolutePath(referent, referentImages.getLargeIconFile()));
			imagesType.setLayoutImageFile(makeAbsolutePath(referent, referentImages.getLayoutImageFile()));
			imagesType.setSmallIconFile(makeAbsolutePath(referent, referentImages.getSmallIconFile()));
			imagesType.setThumbnailFile(makeAbsolutePath(referent, referentImages.getThumbnailFile()));
		}
	}

	private static String makeAbsolutePath(IComponent component, String relativePath) {
		if (relativePath == null || relativePath.length() == 0)
			return null;
		
		IFacetContainer fc = (IFacetContainer) component;
		File baseDir = fc.getBaseDirectory();
		File file = new File(baseDir, relativePath);
		String path = null;
		try {
			path = file.getCanonicalPath();
		}
		catch (IOException x) {
			Object args[] = {component.getId(), file.getAbsolutePath()};
			String msg = MessageFormat.format(Messages.getString("badPath"), args);
			UIQComponentPlugin plugin = UIQComponentPlugin.getDefault();
			Logging.log(plugin, Logging.newStatus(plugin, IStatus.ERROR, msg));
		}
		
		return path;
	}

	private void copySymbianInfo(IComponent referent, SymbianType symbianType) {
		Check.checkContract(referent instanceof Component);
		// we have to go directly into the Component class, since this is not exposed via API
		Component c = (Component) referent;
		symbianType.setSdkName(c.getSDKName());
		Version v = c.getMaxSDKVersion();
		if (v != null)
			symbianType.setMaxSDKVersion(v.toString());
		v = c.getMinSDKVersion();
		if (v != null)
			symbianType.setMinSDKVersion(v.toString());
	}

	private void copyAttributes(IComponent referent, AttributesType attributesType) {
		IComponent fromComponent = referent;
		// copy from all bases until but not including control collection item base
		while (!fromComponent.getId().equals(CONTROL_COLLECTION_ITEM_BASE)) {
			Check.checkContract(fromComponent instanceof Component);
			// we have to go directly into the Component class, since this is not exposed via API
			Component c = (Component) fromComponent;
			ComponentType fromComponentType = c.getEMFComponent();
	
			AttributesType fromAttributes = fromComponentType.getAttributes();
			if (fromAttributes != null) {
				for (Iterator iterator = fromAttributes.getAttribute().iterator(); iterator.hasNext();) {
					AttributeType attribute = (AttributeType) iterator.next();
					AttributeType attributeCopy = COMP_FACTORY.createAttributeType();
					attributeCopy.setKey(attribute.getKey());
					attributeCopy.setValue(attribute.getValue());
					attributesType.getAttribute().add(attributeCopy);
				}
			}
			fromComponent = fromComponent.getComponentBase();
		}
	}
	
	private void copyImplementations(IComponent referent, ImplementationsType implementationsType) {
		IComponent fromComponent = referent;
		// copy from all bases until but not including control collection item base
		while (!fromComponent.getId().equals(CONTROL_COLLECTION_ITEM_BASE)) {
			Check.checkContract(fromComponent instanceof Component);
			// we have to go directly into the Component class, since this is not exposed via API
			Component c = (Component) fromComponent;
			ComponentType fromComponentType = c.getEMFComponent();
			
			ImplementationsType fromImplementations = fromComponentType.getImplementations();
			if (fromImplementations != null) {
				for (Iterator iterator = fromImplementations.getImplementation().iterator(); iterator.hasNext();) {
					ImplementationType fromImpType = (ImplementationType) iterator.next();
					ImplementationType impTypeCopy = COMP_FACTORY.createImplementationType();
					implementationsType.getImplementation().add(impTypeCopy);
					for (Iterator iter = fromImpType.getInterface().iterator(); iter.hasNext();) {
						InterfaceType intf = (InterfaceType) iter.next();
						InterfaceType intfCopy = COMP_FACTORY.createInterfaceType();
						intfCopy.setId(intf.getId());
						impTypeCopy.getInterface().add(intfCopy);
					}
					CodeType ct = fromImpType.getCode();
					if (ct != null) {
						CodeType ctCopy = COMP_FACTORY.createCodeType();
						ctCopy.setPlugin(ct.getPlugin());
						ctCopy.setClass(ct.getClass_());
						impTypeCopy.setCode(ctCopy);
					}
					ScriptType st = fromImpType.getScript();
					if (st != null) {
						ScriptType stCopy = COMP_FACTORY.createScriptType();
						String file = st.getFile();
						if (!new File(file).isAbsolute()) {
							IFacetContainer fc = (IFacetContainer) referent;
							File baseDir = fc.getBaseDirectory();
							File abs = new File(baseDir, file);
							file = abs.getAbsolutePath();
						}
						stCopy.setFile(file);
						stCopy.setPrototype(st.getPrototype());
						impTypeCopy.setScript(stCopy);
					}
				}
			}
			fromComponent = fromComponent.getComponentBase();
		}
	}
	
	private void copyEvents(IComponent referent, EventsType eventsType) {
		String defaultEventName = null;
		IComponent fromComponent = referent;
		// copy from all bases until but not including control collection item base
		while (!fromComponent.getId().equals(CONTROL_COLLECTION_ITEM_BASE)) {
			Check.checkContract(fromComponent instanceof Component);
			// we have to go directly into the Component class, since this is not exposed via API
			Component c = (Component) fromComponent;
			ComponentType fromComponentType = c.getEMFComponent();
			
			EventsType fromEvents = fromComponentType.getEvents();
			if (fromEvents != null) {
				if (defaultEventName == null) // once it has a value, stop
					defaultEventName = fromEvents.getDefaultEventName();
				for (Iterator iterator = fromEvents.getEvent().iterator(); iterator.hasNext();) {
					EventType event = (EventType) iterator.next();
					EventType eventCopy = COMP_FACTORY.createEventType();
					eventCopy.setCategory(event.getCategory());
					eventCopy.setDescriptionKey(event.getDescriptionKey());
					eventCopy.setDisplayName(event.getDisplayName());
					eventCopy.setGroup(event.getGroup());
					eventCopy.setHandlerNameTemplate(event.getHandlerNameTemplate());
					eventCopy.setHelpKey(event.getHelpKey());
					eventCopy.setName(event.getName());
					eventsType.getEvent().add(eventCopy);
				}
			}
			fromComponent = fromComponent.getComponentBase();
		}
		eventsType.setDefaultEventName(defaultEventName);
	}

	public void refreshComponents() throws ComponentSystemException {
		isLoaded = false;
		loadComponents();
	}

	public Bundle getBundle() {
		return UIQComponentPlugin.getDefault().getBundle();
	}

	public void setBundle(Bundle bundle) {
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.IComponentLibrary#getSDKVendorPattern()
	 */
	public Pattern getSDKVendorPattern() {
		return UIQ_VENDOR_PATTERN;
	}
	
	public IComponentProvider getProvider() {
		return provider;
	}

	public void setProvider(IComponentProvider provider) {
		this.provider = (ComponentProvider) provider;
	}
	
	public static String getLayoutComponentId(IComponent referent) {
		return referent.getId() + LAYOUT_COMPONENT_SUFFIX;
	}
	
}
