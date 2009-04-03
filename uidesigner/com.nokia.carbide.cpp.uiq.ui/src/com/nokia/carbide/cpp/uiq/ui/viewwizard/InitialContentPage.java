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
/* START_USECASES: CU2 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.viewwizard;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.*;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI;
import com.nokia.carbide.cpp.uiq.ui.UIQUserInterfacePlugin;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.ImageUtils;
import com.nokia.sdt.utils.ui.ThumbnailWithDescriptionComposite;
import com.swtdesigner.ResourceManager;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import java.util.*;

public class InitialContentPage extends ViewWizardPageBase {
	
	private ThumbnailWithDescriptionComposite thumbnailComposite;
	private boolean thumbnailInitialized;
	private boolean needsNewCustomizerUI;
	
	public static final String PAGE_NAME = "InitialContent"; //$NON-NLS-1$
	
	private static final Object NO_CONTENT_ELEMENT = new Object();
	private static final String NO_CONTENT_NAME = Messages.getString("InitialContentPage.Empty"); //$NON-NLS-1$
	private static final Image NO_CONTENT_TN = 
		ResourceManager.getPluginImage(UIQUserInterfacePlugin.getDefault(), "icons/none_tn.png"); //$NON-NLS-1$
	private static final String NO_CONTENT_DESC = Messages.getString("InitialContentPage.EmptyDescription"); //$NON-NLS-1$
	
	public InitialContentPage(ViewWizardManager manager) {
		super(PAGE_NAME, manager);
		setTitle(Messages.getString("InitialContentPage.PageTitle")); //$NON-NLS-1$
		setDescription(Messages.getString("InitialContentPage.PageDescription")); //$NON-NLS-1$
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		thumbnailComposite = new ThumbnailWithDescriptionComposite(parent, SWT.NULL);
		thumbnailComposite.setData(NAME_KEY, "thumbnailComposite"); //$NON-NLS-1$
		setControl(thumbnailComposite);
        setHelpContextId(ViewWizardManager.INITIAL_CONTENT_PAGE);

		thumbnailComposite.getThumbnailViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.isEmpty())
					return;
				handleInitialContentChanged(selection.getFirstElement());
			}
		});

		thumbnailComposite.getThumbnailViewer().setContentProvider(new ArrayContentProvider());
		thumbnailComposite.getThumbnailViewer().setLabelProvider(new LabelProvider() {
			Map<IComponent, Image> thumbnailMap = new HashMap<IComponent, Image>();

			@Override
			public void dispose() {
				super.dispose();
				for (Image image : thumbnailMap.values()) {
					image.dispose();
				}
			}
			
			public Image getImage(Object element) {
				if (element.equals(NO_CONTENT_ELEMENT)) {
					return NO_CONTENT_TN;
				}
				
				Check.checkArg(element instanceof IComponent);
				IComponent component = (IComponent) element;
				Image image = thumbnailMap.get(component);
				if (image == null) {
					IDesignerImages images = (IDesignerImages) component.getAdapter(IDesignerImages.class);
					if (images != null) {
						image = images.getThumbnailImage();
						image = ImageUtils.copyImage(getShell().getDisplay(), image);
						thumbnailMap.put(component, image);
					}
				}
				return image;
			}

			public String getText(Object element) {
				if (element.equals(NO_CONTENT_ELEMENT)) {
					return NO_CONTENT_NAME;
				}
				
				Check.checkArg(element instanceof IComponent);
				IComponent component = (IComponent) element;
				return component.getFriendlyName();
			}
		});
		thumbnailComposite.setViewerTitle(Messages.getString("InitialContentPage.ListPrompt")); //$NON-NLS-1$
		thumbnailComposite.setDescriptionTitle(Messages.getString("InitialContentPage.DescriptionPrompt")); //$NON-NLS-1$

	}

	@Override
	protected void enteringPage() {
		super.enteringPage();
		if (!thumbnailInitialized) {
			thumbnailInitialized = true;
			// initialization has to be moved here, because sdks (and component set)
			// unavailable until SDKOptions page has selection
			thumbnailComposite.getThumbnailViewer().setInput(getInitialContentComponents());
			thumbnailComposite.getThumbnailViewer().selectFirst();
			getWizard().getContainer().getShell().setSize(600, 400);
		}
		thumbnailComposite.getThumbnailViewer().getComposite().forceFocus();
	}
	
	@Override
	public IWizardPage getNextPage() {
		getCustomizerUI();
		return super.getNextPage();
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return true;
	}

	private void handleInitialContentChanged(Object selectedObject) {
		// if nothing has changed, return
		if (selectedObject.equals(getWizardManager().getDataStore().get(ViewWizardManager.CONTENT_COMPONENT_KEY)))
			return;
		
		needsNewCustomizerUI = true;
		
		// remove any customizer command factory
		getWizardManager().getDataStore().remove(ViewWizardManager.CUSTOMIZER_COMMAND_FACTORY_KEY);
		
		// handle the NO_CONTENT_ELEMENT specially
		if (selectedObject.equals(NO_CONTENT_ELEMENT)) {
			thumbnailComposite.getDescriptionText().setText(NO_CONTENT_DESC);
			// remove the component
			getWizardManager().getDataStore().remove(ViewWizardManager.CONTENT_COMPONENT_KEY);
		}
		else {
			// else it has to be a component
			Check.checkState(selectedObject instanceof IComponent);
			IComponent component = (IComponent) selectedObject;
			// store the new selection
			getWizardManager().getDataStore().put(ViewWizardManager.CONTENT_COMPONENT_KEY, component);
			IDocumentation documentation = (IDocumentation) component.getAdapter(IDocumentation.class);
			String description = documentation.getWizardDescription();
			if (description != null)
				thumbnailComposite.getDescriptionText().setText(description);
		}
		
		getWizard().getContainer().updateButtons();
	}

	private void getCustomizerUI() {
		if (!needsNewCustomizerUI)
			return;
		
		needsNewCustomizerUI = false;
		final IComponent contentComponent = 
			(IComponent) getWizardManager().getDataStore().get(ViewWizardManager.CONTENT_COMPONENT_KEY);
		if (contentComponent == null) {
			getWizardManager().setCustomizerUI(null);
			return;
		}
		
		Runnable runnable = new Runnable() {

			public void run() {
				IComponentCustomizerUI customizerUI = null;
				getWizardManager().disposeStoredModel(ViewWizardManager.TEMP_DATA_MODEL_KEY);
				try {
					IDesignerDataModel dataModel = getWizardManager().createEmptyViewModel();
					getWizardManager().getDataStore().put(ViewWizardManager.TEMP_DATA_MODEL_KEY, dataModel);
					IAttributes attributes = (IAttributes) contentComponent.getAdapter(IAttributes.class);
					EObject rootContainer = createRootContainer(dataModel, 
							attributes.isAttributeDefined(CommonAttributes.DISPLAY_MODEL_CLASS));
					EObject contentObject = rootContainer;
										
					Check.checkState(contentObject != null);
					customizerUI = WizardUtils.getCustomizerUI(contentObject); 
					getWizardManager().setCustomizerUI(customizerUI);
				}
				catch (Exception e) {
					Check.reportFailure(Messages.getString("InitialContentPage.CreateViewModelError"), e); //$NON-NLS-1$
				}
			}
		};
		
		BusyIndicator.showWhile(getShell().getDisplay(), runnable);
	}

	private EObject createRootContainer(IDesignerDataModel dataModel, boolean hasDisplayModelClass) throws CoreException {
		IComponent contentComponent = (IComponent) getWizardManager().getDataStore().get(ViewWizardManager.CONTENT_COMPONENT_KEY);
		Check.checkContract(contentComponent != null);
		
		IComponent containerComponent = contentComponent;
		if (!hasDisplayModelClass) {
			containerComponent = getWizardManager().getComponentSet().lookupComponent(ViewWizardManager.VIEW_COMPONENT_ID);
			Check.checkState(containerComponent != null);
		}
		EObject rootInstance = WizardUtils.addRootInstance(dataModel, containerComponent, null, null);
		//dataModel.getDisplayModelForRootContainer(rootInstance);
		return rootInstance;
	}
	
	protected List<IComponent> getInitialContentComponents() {
		List<IComponent> initialContentComponents = new ArrayList<IComponent>();
		IComponentSet componentSet = getWizardManager().getComponentSet();
		boolean isNewProjectWizard = this.isNewProjectWizard();
		
		for (Iterator<IComponent> iter = componentSet.iterator(); iter.hasNext();) {
			IComponent component = iter.next();
			IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);

			if (!component.isAbstract() &&
					attributes.getBooleanAttribute(CommonAttributes.IS_INITIAL_CONTENT, false)) {
				if (!isNewProjectWizard || isNewProjectWizard && attributes.getBooleanAttribute(UIQModelUtils.VIEWDIALOG_ATTRIBUTE_ISAPPUICONTAINER, false))
					initialContentComponents.add(component);
			}
		}

		Collections.sort(initialContentComponents, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((IComponent) o1).getFriendlyName().compareTo(
						((IComponent) o2).getFriendlyName());
			}
		});
		
		
		return initialContentComponents;
	}
	
	protected boolean isNewProjectWizard() {
		return getWizardManager().getDataStore().get(ViewWizardManager.ROOT_MODEL_KEY) == null && !isAddingUIDesignToLegacyProject();
	}
	
	protected boolean isAddingUIDesignToLegacyProject() {
		if (getWizardManager().getWizard() instanceof ViewWizard) {
			ViewWizard viewWizard = (ViewWizard)getWizardManager().getWizard();
			if (viewWizard.getCurrentTemplate() != null) {
				return true;
			}
		}	
		return false;
	}

}
