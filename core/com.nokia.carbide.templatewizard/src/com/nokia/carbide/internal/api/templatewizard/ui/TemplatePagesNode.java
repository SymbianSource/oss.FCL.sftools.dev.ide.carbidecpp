/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.api.templatewizard.ui;

import com.nokia.carbide.template.engine.ILoadedTemplate;
import com.nokia.carbide.template.engine.ILoadedTemplateUI;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Wizard node creating the pages after choosing a template
 */
public class TemplatePagesNode implements IWizardNode {
	
	public static class TemplatePagesWizard extends Wizard {

		private TemplatePagesNode node;
		
		/**
		 * @param node
		 */
		public TemplatePagesWizard(TemplatePagesNode node) {
			super();
			this.node = node;
			TemplateWizard parentWizard = node.getParentWizard();
			ILoadedTemplate selectedTemplate = parentWizard.getSelectedLoadedTemplate();
			Check.checkContract(selectedTemplate != null);
			String label = selectedTemplate.getLabel();
			label = selectedTemplate.getTemplate().getLocalizedString(label);
			String title = MessageFormat.format(Messages.getString("TemplatePagesNode.WizardPageTitle"), new Object[] { label } ); //$NON-NLS-1$
			setWindowTitle(title);
			Image defaultPageImage = parentWizard.getDefaultPageImage();
			ImageDescriptor imageDescriptor = ImageDescriptor.createFromImage(defaultPageImage);
			setDefaultPageImageDescriptor(imageDescriptor);
			setNeedsProgressMonitor(true);
			
			ILoadedTemplateUI selectedLoadedTemplateUI = parentWizard.getSelectedTemplateUI();
			selectedLoadedTemplateUI.initExtraPagesProvider(parentWizard, 
					parentWizard.getWorkbench(), parentWizard.getSelection());
		}

		@Override
		public void addPages() {
			List<IWizardDataPage> pagesAfterTemplateChoice = 
							node.getParentWizard().getPagesAfterTemplateChoice();
			if (pagesAfterTemplateChoice != null) {
				for (Iterator<IWizardDataPage> iter = pagesAfterTemplateChoice.iterator(); iter.hasNext();) {
					addPage(iter.next());
				}
			}
			List<IWizardDataPage> extraPagesProviderPages = 
							node.getTemplateUI().getExtraPagesProviderPages(node.getParentWizard());
			for (Iterator<IWizardDataPage> iter = extraPagesProviderPages.iterator(); iter.hasNext();) {
				addPage(iter.next());
			}
			ILoadedTemplateUI templateUI = node.getTemplateUI();
			Check.checkState(templateUI != null);
			List<TemplateWizardPage> wizardPages = templateUI.getWizardPages(node.getParentWizard());
			for (Iterator<TemplateWizardPage> iter = wizardPages.iterator(); iter.hasNext();) {
				addPage(iter.next());
			}
			
			List<IWizardDataPage> pagesAfterTemplatePages = 
				node.getParentWizard().getPagesAfterTemplatePages();
			if (pagesAfterTemplatePages != null) {
				for (Iterator<IWizardDataPage> iter = pagesAfterTemplatePages.iterator(); iter.hasNext();) {
					addPage(iter.next());
				}
			}
			
		}

		@Override
		public boolean performFinish() {
			return true;
		}

		@Override
		public boolean performCancel() {
			return getParentWizard().performCancel();
		}

		@Override
		public IDialogSettings getDialogSettings() {
			return getParentWizard().getDialogSettings();
		}
		
		public IWizard getParentWizard() {
			return node.getParentWizard();
		}
	}

	private IWizard wizard;
	private TemplateWizard parentWizard;

	/**
	 * @param parentWizard
	 */
	public TemplatePagesNode(TemplateWizard parentWizard) {
		super();
		this.parentWizard = parentWizard;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardNode#dispose()
	 */
	public void dispose() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardNode#getExtent()
	 */
	public Point getExtent() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardNode#getWizard()
	 */
	public IWizard getWizard() {
		if (wizard == null) {
			wizard = new TemplatePagesWizard(this);
		}
		return wizard;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardNode#isContentCreated()
	 */
	public boolean isContentCreated() {
		return wizard != null;
	}

	private ILoadedTemplateUI getTemplateUI() {
		return parentWizard.getSelectedTemplateUI();
	}

	public TemplateWizard getParentWizard() {
		return parentWizard;
	}
}
