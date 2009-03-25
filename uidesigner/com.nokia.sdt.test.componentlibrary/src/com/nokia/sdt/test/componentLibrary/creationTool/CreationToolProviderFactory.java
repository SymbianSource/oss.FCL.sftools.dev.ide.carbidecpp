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


package com.nokia.sdt.test.componentLibrary.creationTool;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.*;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ICreationToolProvider;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.ICreationTool;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

public class CreationToolProviderFactory implements IImplFactory {
	
	private final class CreationToolProvider implements ICreationToolProvider {
		private EObject object;
		private IDesignerEditor editor;
		private List<EObject> clones;

		public class CloneTool implements ICreationTool {
			private IComponentInstance originalInstance;
			private EObject clonedObject;
			
			public CloneTool(CreationToolProvider provider, EObject object) {
				originalInstance = Utilities.getComponentInstance(object);
			}

			public EObject createNewObject(IDesignerDataModel dataModel) {
				clonedObject = dataModel.createNewComponentInstance(originalInstance.getComponent());
				clones.add(clonedObject);
				return clonedObject;
			}

			public String getCategory() {
				return "NLO cloners";
			}

			public String getDescription() {
				IComponent component = originalInstance.getComponent();
				IDocumentation documentation = (IDocumentation) component.getAdapter(IDocumentation.class);
				return documentation != null ? documentation.getInformation() : null;
			}

			public ImageDescriptor getIcon16() {
				IComponent component = originalInstance.getComponent();
				IDesignerImages images = (IDesignerImages) component.getAdapter(IDesignerImages.class);
				return images != null ? images.getSmallIconDescriptor() : null;
			}

			public ImageDescriptor getIcon24() {
				IComponent component = originalInstance.getComponent();
				IDesignerImages images = (IDesignerImages) component.getAdapter(IDesignerImages.class);
				return images != null ? images.getLargeIconDescriptor() : null;
			}

			public String getLabel() {
				return originalInstance.getName();
			}

			public Object getAdapter(Class adapter) {
				return originalInstance.getComponent().getAdapter(adapter);
			}

			public void addNotify(EObject parent) {
				Check.checkState(editor.getNonLayoutRoot().equals(parent));
				copyProperties(originalInstance.getEObject(), clonedObject);
			}
		}
		
		private CreationToolProvider(EObject object) {
			this.object = object;
			clones = new ArrayList();
		}

		private void initEditor(IDesignerEditor editor) {
			this.editor = editor;
		}

		protected void copyProperties(EObject original, EObject copy) {
			IPropertySource originalPs = Utilities.getPropertySource(original);
			IPropertySource ps = Utilities.getPropertySource(copy);
			for (IPropertyDescriptor pd : ps.getPropertyDescriptors()) {
				Object id = pd.getId();
				if (!id.equals("name")) // don't copy name
					ps.setPropertyValue(id, originalPs.getPropertyValue(id));
			}
		}

		protected boolean isClone(EObject object) {
			return clones.contains(object);
		}
		
		public Collection<ICreationTool> getCreationTools(IDesignerEditor editor) {
			initEditor(editor);
			
			List<ICreationTool> tools = new ArrayList<ICreationTool>();
			
			EObject nonLayoutRoot = (EObject) this.editor.getNonLayoutRoot();
			IComponentInstance ci = ModelUtils.getComponentInstance(nonLayoutRoot);
			for (EObject child : ci.getChildren()) {
				String clonerId = ModelUtils.getComponentInstance(object).getComponentId();
				boolean isCloner = ModelUtils.getComponentInstance(child).getComponentId().equals(clonerId);
				if (!isCloner && Utilities.isNonLayoutObject(child) && !isClone(child)) {
					tools.add(new CloneTool(this, child));
				}
			}
			
			return tools;
		}
		
		public EObject getEObject() {
			return object;
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject object) {
		return new CreationToolProvider(object);
	}

}
