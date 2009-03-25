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


package com.nokia.sdt.series60.viewwizard;

import java.text.MessageFormat;
import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.ui.TemplateUtils;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.carbide.internal.api.template.engine.Template;
import com.nokia.carbide.internal.api.template.engine.TemplateEngine;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.carbide.internal.api.templatewizard.ui.TemplateWizardPage;
import com.nokia.carbide.template.engine.*;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.series60.viewwizard.ViewWizardManager.IProjectSelectionListener;
import com.nokia.sdt.symbian.workspace.SymbianProjectUtils;
import com.nokia.sdt.symbian.workspace.SymbianProjectUtils.ES60ProjectType;

/**
 * 
 *
 */
public class ViewWizard extends BasicNewResourceWizard {

	private ViewWizardManager wizardManager;
	private ITemplate currentTemplate;
	private ILoadedTemplate currentLoadedTemplate;
	private ILoadedTemplateUI currentLoadedTemplateUI;
	private List<TemplateWizardPage> templatePages;
	private String projectName;
	private PagesProvider pagesProvider;
	private boolean finishPressed;
	
	public ViewWizard() {
		templatePages = new ArrayList();
		setDialogSettings(TemplateWizardPlugin.getDefault().getDialogSettings());
	}
	
	protected void updateProject(IProject project) {
		if (project == null)
			return;
		if (!isDesignerProject(project)) {
			ITemplate template = getTemplateFromProject(project);
			if ((template != null) && !template.equals(currentTemplate)) {
				clearOldTemplatePages();
				Composite pageContainer = wizardManager.getCurrentPage().getControl().getParent();
				currentTemplate = template;
				try {
					currentLoadedTemplate = template.getLoadedTemplate();
				} catch (CoreException e) {
					String message = MessageFormat.format(
							"There was an error loading the template (''{0}'')... please check the Error Log.",
							currentTemplate.getTemplateUrl());
					MessageDialog.openError(getShell(), "Broken Template", message);
					currentTemplate = null;
					return;
				}
				currentLoadedTemplateUI = currentLoadedTemplate.createLoadedTemplateUI();
				projectName = project.getName();
				List<TemplateWizardPage> pages = currentLoadedTemplateUI.getWizardPages(this);
				for (TemplateWizardPage wizardPage : pages) {
					wizardPage.setWizard(this);
					wizardPage.createControl(pageContainer);
					wizardPage.getControl().setVisible(false);
					templatePages.add(wizardPage);
				}
				pageContainer.layout();
			}
		}
		else {
			if (currentTemplate != null) {
				clearOldTemplatePages();
				currentTemplate = null;
			}
		}
	}

	private boolean isDesignerProject(IProject project) {
		boolean hasRootModel = wizardManager.getDataStore().get(ViewWizardManager.ROOT_MODEL_KEY) != null;
		return hasRootModel && SymbianProjectUtils.isWellConfiguredUIDesignerProject(project);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	public void addPages() {
		if (wizardManager == null) {
			setDefaultPageImageDescriptor(
					CarbideUIPlugin.getSharedImages().getImageDescriptor(
							ICarbideSharedImages.IMG_NEW_UI_DESIGN_WIZARD_BANNER));
			pagesProvider = new PagesProvider();
			wizardManager = new ViewWizardManager();
			pagesProvider.setManager(this, wizardManager);
			wizardManager.addProjectSelectionListener(new IProjectSelectionListener() {
				public void projectSelectionChanged(IProject project) {
					updateProject(project);
				}
			});
		}
		// add only the wizardManager pages
		List pages = wizardManager.createPages(this, getWorkbench(), getSelection());
		for (Iterator iter = pages.iterator(); iter.hasNext();) {
			IWizardPage page = (IWizardPage) iter.next();
			addPage(page);
		}
	}
	
	private ITemplate getTemplateFromProject(IProject project) {
		if (project == null)
			return null;
		
		// detect if this is a 2.x only project or if it includes 3.x configs
		// in the latter case, we need to use the 3.x template, otherwise 2.x
		// if this is a mixed config project, we should warn that 3.x libraries will be added.
		
		ES60ProjectType s60projectType = SymbianProjectUtils.getS60ProjectType(project);
		Check.checkContract(s60projectType != ES60ProjectType.NotS60);

		List<ITemplate> templates = 
			TemplateEngine.getInstance().getTemplates(getClass().getName());
		Check.checkState((templates != null) && !templates.isEmpty());
		
		ITemplate template2x = null, template3x = null;
		for (ITemplate template : templates) {
			if (TemplateUtils.sdkMatchesTemplate(new Version(3, 0, 0), ISymbianSDK.S60_FAMILY_ID, template))
				template3x = template;
			if (TemplateUtils.sdkMatchesTemplate(new Version(2, 0, 0), ISymbianSDK.S60_FAMILY_ID, template))
				template2x = template;
		}
		
		if (s60projectType == ES60ProjectType.S602xOnly) {
			return template2x;
		} 
		else if (s60projectType == ES60ProjectType.S603xOnly || s60projectType == ES60ProjectType.S60Mixed) {
			return template3x;
		}
		
		return null;
	}
	
	private void clearOldTemplatePages() {
		if (currentLoadedTemplateUI != null)
			currentLoadedTemplateUI.dispose();
		templatePages.clear();
		if (currentTemplate != null)
			((Template) currentTemplate).reset();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	public IWizardPage getNextPage(IWizardPage page) {
		// if this is a template page, get the next one (template are after wizardManager pages)
		if (page instanceof TemplateWizardPage)
			return getNextTemplatePage((TemplateWizardPage) page);
		
		// get the next page from the wizardManager
		IWizardPage nextPage = null;
		if (!wizardManager.isLastPage(page))
			nextPage = wizardManager.getNextPage(page);
		
		// if it's null, return the first template page
		if ((nextPage == null) && (templatePages.size() > 0)) {
			return templatePages.get(0);
		}

		return nextPage;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#getPreviousPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	public IWizardPage getPreviousPage(IWizardPage page) {
		// if this is a template page, see if there's a previous one
		if (page instanceof TemplateWizardPage) {
			IWizardPage previousPage = getPreviousTemplatePage((TemplateWizardPage) page);
			// if this was a template page, and there is no previous template page,
			// return the last wizardManager page -- i.e. the ContainerSelectionPage
			if (previousPage == null)
				return wizardManager.getPage(ContainerSelectionPage.PAGE_NAME);
			else
				return previousPage;
		}

		return wizardManager.getPreviousPage(page);
	}

	private IWizardPage getNextTemplatePage(TemplateWizardPage page) {
		int curIndex = templatePages.indexOf(page);
		Check.checkState((curIndex >= 0) && (curIndex < templatePages.size()));
		if (curIndex == (templatePages.size() - 1))
			return null;
		return templatePages.get(curIndex + 1);
	}

	private TemplateWizardPage getPreviousTemplatePage(TemplateWizardPage page) {
		int curIndex = templatePages.indexOf(page);
		Check.checkState((curIndex >= 0) && (curIndex < templatePages.size()));
		if (curIndex == 0)
			return null;
		return templatePages.get(curIndex - 1);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#canFinish()
	 */
	public boolean canFinish() {
		if (finishPressed)
			return false;
		
		boolean canFinish = super.canFinish();
		if (canFinish && (templatePages != null)) {
			// check the template pages
			for (Iterator<TemplateWizardPage> iter = templatePages.iterator(); iter.hasNext();) {
				if (!iter.next().isPageComplete())
					return false;
			}
		}
		
		return canFinish;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		wizardManager.setTemplate(currentTemplate);
		if (currentTemplate != null)
			currentTemplate.getTemplateValues().putAll(getAllDataInTemplatePages());
		boolean minimalRootModel = currentTemplate != null;
		wizardManager.generateModels(minimalRootModel);
		if (collisionDetected()) {
			wizardManager.disposeModels(false);
			wizardManager.getCurrentPage().setPageComplete(true);
			return false;
		}
		
		if (currentTemplate != null) {
			currentTemplate.getTemplateValues().putAll(getAllDataInNonTemplatePages());
			currentTemplate.getTemplateValues().put("projectName", projectName); //$NON-NLS-1$
		}

		WorkspaceJob job = new WorkspaceJob(Messages.getString("ViewWizard.ProcessingTitle")) { //$NON-NLS-1$
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				IStatus status = Status.OK_STATUS;
				if (currentTemplate == null) { // adding to a UI Designer project
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							wizardManager.saveModels(false, true, monitor); // don't create the root model
							wizardManager.disposeModels(true);
						}
					});
				}
				else {
					status = TemplateEngine.runProcesses(currentTemplate, monitor);
					((Template) currentTemplate).reset();
				}
				
				return status;
			}
		};
		job.setUser(true);
		job.schedule();

		finishPressed = true;
		getContainer().updateButtons();
	    return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	@Override
	public boolean performCancel() {
		if (currentTemplate != null)
			((Template) currentTemplate).reset();
		
		return true;
	}
	
	private boolean collisionDetected() {
		List<IPath> collisions = wizardManager.getSourceCollisions();
		
		if (collisions.size() > 0) {
			String message = MessageFormat.format(
					Messages.getString("ViewWizard.CollisionMessage"), //$NON-NLS-1$
					new Object[] { TextUtils.formatTabbedList(collisions) });
			if (MessageDialog.openQuestion(getShell(), Messages.getString("ViewWizard.CollisionDialogTitle"), message)) //$NON-NLS-1$
				return false;
			
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		setWindowTitle(Messages.getString("ViewWizard.WizardTitle")); //$NON-NLS-1$
	}

	private Map<String, Object> getAllDataInNonTemplatePages() {
		Map<String, Object> map = new HashMap();
		for (IWizardPage page : getPages()) {
			if (page instanceof IWizardDataPage)
				map.putAll(((IWizardDataPage) page).getPageValues());
		}
		return map;
	}
	
	private Map<String, Object> getAllDataInTemplatePages() {
		Map<String, Object> map = new HashMap();
		for (IWizardPage page : templatePages) {
			if (page instanceof IWizardDataPage)
				map.putAll(((IWizardDataPage) page).getPageValues());
		}
		return map;
	}
}

