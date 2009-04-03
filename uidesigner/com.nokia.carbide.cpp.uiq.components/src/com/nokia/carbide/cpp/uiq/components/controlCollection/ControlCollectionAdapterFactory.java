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


package com.nokia.carbide.cpp.uiq.components.controlCollection;

import com.nokia.carbide.cpp.uiq.components.layoutComponents.ComponentLibrary;
import com.nokia.sdt.component.*;
import com.nokia.sdt.component.adapter.*;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.editor.ICreationTool;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import java.io.File;
import java.util.*;

public class ControlCollectionAdapterFactory implements IImplFactory {
	
	private final class CreationToolProvider implements ICreationToolProvider {
		private EObject object;
		private IDesignerEditor editor;
		private IComponentInstancePropertyListener propertyListener;
		private IComponentInstanceChildListener childListener;

		private CreationToolProvider(EObject object) {
			this.object = object;
		}

		private void initEditor(IDesignerEditor editor) {
			if (this.editor != null)
				return;
			this.editor = editor;
			propertyListener = new IComponentInstancePropertyListener() {
				public void propertyChanged(EObject componentInstance, Object propertyId) {
					if (propertyId.equals(CreationTool.NAME_PROPERTY))
						CreationToolProvider.this.editor.updatePalette(true);
				}
			};
			childListener = new IComponentInstanceChildListener() {
				public void childAdded(EObject parent, EObject child) {
					ModelUtils.getComponentInstance(child).addPropertyListener(propertyListener);
				}

				public void childRemoved(EObject parent, EObject child) {}
				public void childrenReordered(EObject parent) {}
			};
			ModelUtils.getComponentInstance(getEObject()).addChildListener(childListener);
		}

		public Collection<ICreationTool> getCreationTools(IDesignerEditor editor) {
			initEditor(editor);
			
			List<ICreationTool> tools = new ArrayList<ICreationTool>();
			
			IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
			for (EObject child : ci.getChildren()) {
				tools.add(new CreationTool(this, child));
			}
			
			return tools;
		}
		
		public EObject getEObject() {
			return object;
		}
	}

	public static class CreationTool implements ICreationTool, IComponent {
		private static final String REF = "Ref"; //$NON-NLS-1$
		
		public static final String NAME_PROPERTY = "name"; //$NON-NLS-1$
		public static final String REFERENCE_PROPERTY_NAME = "control"; //$NON-NLS-1$
		
		public static IComponent getLayoutComponent(IDesignerDataModel dataModel, IComponent referenceComponent) {
			if (referenceComponent == null)
				return null;
			String layoutComponentId = ComponentLibrary.getLayoutComponentId(referenceComponent);
			return dataModel.getComponentSet().lookupComponent(layoutComponentId);
		}
		
		public static String generateLayoutControlName(IDesignerDataModel dataModel, String referenceName) {
			return NamePropertySupport.generateNameForModel(dataModel, null, referenceName + REF, true);
		}
		
		private IComponentInstance instance;
		private final CreationToolProvider provider;
		private EObject newObject;
		private IComponent layoutComponent;
		
		public CreationTool(CreationToolProvider provider, EObject object) {
			this.provider = provider;
			instance = ModelUtils.getComponentInstance(object);
		}

		public EObject createNewObject(IDesignerDataModel dataModel) {
			IComponent layoutComponent = getLayoutComponent();
			Check.checkState(layoutComponent != null);
			return newObject = dataModel.createNewComponentInstance(layoutComponent);
		}
		
		public String getCategory() {
			return Messages.getString("ControlCollectionAdapterFactory.Category"); //$NON-NLS-1$
		}

		public String getDescription() {
			IComponent component = instance.getComponent();
			if (component == null)
				return null;
			IDocumentation documentation = (IDocumentation) component.getAdapter(IDocumentation.class);
			return documentation != null ? documentation.getInformation() : null;
		}

		public ImageDescriptor getIcon16() {
			IComponent component = instance.getComponent();
			if (component == null)
				return null;
			IDesignerImages images = (IDesignerImages) component.getAdapter(IDesignerImages.class);
			return images != null ? images.getSmallIconDescriptor() : null;
		}

		public ImageDescriptor getIcon24() {
			IComponent component = instance.getComponent();
			if (component == null)
				return null;
			IDesignerImages images = (IDesignerImages) component.getAdapter(IDesignerImages.class);
			return images != null ? images.getLargeIconDescriptor() : null;
		}

		public String getLabel() {
			return instance.getName();
		}

		public Object getAdapter(Class adapter) {
			if (adapter.isInstance(this))
				return this;
			return getLayoutComponent().getAdapter(adapter);
		}
		
		public String getControlRefValue() {
			return instance.getName();
		}

		public void addNotify(EObject parent) {
			// set component reference
			IPropertySource ps = ModelUtils.getPropertySource(newObject);
			ps.setPropertyValue(REFERENCE_PROPERTY_NAME, getControlRefValue());
			
			// set name
			IDesignerDataModel model = instance.getDesignerDataModel();
			String name = generateLayoutControlName(model, instance.getName());
			ps.setPropertyValue(CreationTool.NAME_PROPERTY, name);
		}
		
		private IComponent getLayoutComponent() {
			if (layoutComponent == null) {
				layoutComponent = getLayoutComponent(instance.getDesignerDataModel(), instance.getComponent());
			}
			
			return layoutComponent;
		}
		
		public MessageLocation createMessageLocation() {
			return getLayoutComponent().createMessageLocation();
		}

		public String getBaseComponentId() {
			return getLayoutComponent().getBaseComponentId();
		}

		public File getBaseDirectory() {
			return getLayoutComponent().getBaseDirectory();
		}

		public Bundle getBundle() {
			return getLayoutComponent().getBundle();
		}

		public IComponent getComponentBase() {
			return getLayoutComponent().getComponentBase();
		}

		public IComponentSet getComponentSet() {
			return getLayoutComponent().getComponentSet();
		}

		public Version getComponentVersion() {
			return getLayoutComponent().getComponentVersion();
		}

		public String getFriendlyName() {
			return getLayoutComponent().getFriendlyName();
		}

		public String getId() {
			return getLayoutComponent().getId();
		}

		public String getInstanceNameRoot() {
			return getLayoutComponent().getInstanceNameRoot();
		}

		public IComponentProvider getProvider() {
			return getLayoutComponent().getProvider();
		}

		public boolean isAbstract() {
			return getLayoutComponent().isAbstract();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject object) {
		return new CreationToolProvider(object);
	}

}
