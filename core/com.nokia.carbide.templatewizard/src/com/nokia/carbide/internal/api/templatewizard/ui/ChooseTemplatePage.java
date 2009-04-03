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

import com.nokia.carbide.internal.api.template.engine.TemplateEngine;
import com.nokia.carbide.template.engine.*;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;

public class ChooseTemplatePage extends WizardSelectionPage {
	
	private static final String UID_KEY = ".uid"; //$NON-NLS-1$
	private static final String FOLDER_ICON = "icons/fldr_obj.gif"; //$NON-NLS-1$
	private List<ITemplate> templates;
	private Button filterCheckbox;
	private TreeViewer templateTreeViewer;
	private Text templateDescriptionText;
	private IFilter templateFilter;
	private String filterCheckboxLabel;
	private boolean hideFilterCheckbox;
	private ILoadedTemplate selectedTemplate;
	private Image folderImage;
	private Map<ITemplate, Image> templateImages;
	private boolean inited;
	private ILoadedTemplateUI selectedTemplateUI;
	
	public ChooseTemplatePage() {
		super("ChooseTemplatePage"); //$NON-NLS-1$
		filterCheckboxLabel = Messages.getString("ChooseTemplatePage.FilterCheckboxLabel"); //$NON-NLS-1$
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite container = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.makeColumnsEqualWidth = true;
		container.setLayout(gridLayout);
		
		container.setData(UID_KEY, "ChooseTemplatePage"); //$NON-NLS-1$
		container.setData("ChooseTemplatePage", this); //$NON-NLS-1$

        setControl(container);

		final Label chooseTemplateLabel = new Label(container, SWT.NONE);
		chooseTemplateLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		chooseTemplateLabel.setText(Messages.getString("ChooseTemplatePage.ChooseTemplateLabel")); //$NON-NLS-1$

		final SashForm sashForm = new SashForm(container, SWT.VERTICAL);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		sashForm.setLayoutData(gridData);

		templateTreeViewer = new TreeViewer(sashForm, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		templateTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent e) {
				handleTemplateSelectionChanged();
			}
		});
		templateTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				handleTemplateSelectionChanged();
				if (getSelectedLoadedTemplate() != null)
					getContainer().showPage(getNextPage());
			}
		});
		templateTreeViewer.setContentProvider(new TreeNodeContentProvider());
		templateTreeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				Check.checkContract(element instanceof TreeNode);
				Object value = ((TreeNode) element).getValue();
				if (value instanceof String) {
					if (folderImage == null)
						folderImage = TemplateWizardPlugin.getImageDescriptor(FOLDER_ICON).createImage();
					return folderImage;
				}
				if (value instanceof ITemplate) {
					ITemplate template = (ITemplate) value;
					if (templateImages == null)
						templateImages = new HashMap<ITemplate, Image>();
					if (!templateImages.containsKey(template)) {
						Image templateImage = null;
						ImageDescriptor imageDescriptor = template.getImageDescriptor();
						if (imageDescriptor != null)
							templateImage = imageDescriptor.createImage();
						templateImages.put(template, templateImage);
					}
					return templateImages.get(template);
				}
				return super.getImage(element);
			}

			@Override
			public String getText(Object element) {
				Check.checkContract(element instanceof TreeNode);
				Object value = ((TreeNode) element).getValue();
				if (value instanceof String) {
					return value.toString();
				}
				if (value instanceof ITemplate) {
					return ((ITemplate)value).getDisplayName();
				}
				return ((TreeNode) element).getValue().toString();
			}
		});
		templateTreeViewer.getTree().setData(UID_KEY, "templateTree"); //$NON-NLS-1$

		templateDescriptionText = new Text(sashForm, SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
		templateDescriptionText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		templateDescriptionText.setEditable(false);
		templateDescriptionText.setData(UID_KEY, "templateDescription"); //$NON-NLS-1$
		sashForm.setWeights(new int[] { 250, 100 });

		filterCheckbox = new Button(container, SWT.CHECK);
		filterCheckbox.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		filterCheckbox.setText(filterCheckboxLabel);
		filterCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				initTreeViewer(false);
			}
		});
		if (hideFilterCheckbox) {
			filterCheckbox.setVisible(false);
		}
		filterCheckbox.setSelection(!hideFilterCheckbox);
		filterCheckbox.setData(UID_KEY, "filterCheckbox"); //$NON-NLS-1$

		String helpContextId = TemplateWizardPlugin.ID + ".choose_template_page"; //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), helpContextId );
	}
	
	
	
	private void initTreeViewer(boolean resetSelection) {
		templateTreeViewer.setInput(createTreeNodes(getSortedTemplates()));
		templateTreeViewer.expandAll();
		if (resetSelection) {
			TreeNode[] input = (TreeNode[]) templateTreeViewer.getInput();
			if (input != null && input.length > 0) {
				templateTreeViewer.setSelection(new StructuredSelection(input[0]));
			}
		}
		templateTreeViewer.getTree().setFocus();
		ISelection selection = templateTreeViewer.getSelection();
		if (selection != null && !selection.isEmpty()) {
			templateTreeViewer.reveal(((StructuredSelection) selection).getFirstElement());
		}
		inited = true;
	}
	
	@Override
	public void dispose() {
		if (folderImage != null)
			folderImage.dispose();
		if (templateImages != null) {
			for (Iterator<ITemplate> iter = templateImages.keySet().iterator(); iter.hasNext();) {
				Image image = templateImages.get(iter.next());
				if (image != null)
					image.dispose();
			}
		}
		super.dispose();
	}
	
	private List<ITemplate> getSortedTemplates() {
		if (templates == null) {
			if (TemplateEngine.isLoaded())
				templates = TemplateEngine.getInstance().getTemplates(getWizardId());
			else {
				try {
					// blocks until returns, even if fork is true (per javadoc)
					getWizard().getContainer().run(true, false, new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor) {
							monitor.beginTask(Messages.getString("ChooseTemplatePage.LoadingTemplatesTaskName"), 1); //$NON-NLS-1$
							templates = TemplateEngine.getInstance().getTemplates(getWizardId());
							monitor.worked(1);
						}
					});
				} catch (InvocationTargetException e) {
					TemplateEngine.logError(Messages.getString("ChooseTemplatePage.CouldNotLoadTemplates"), e); //$NON-NLS-1$
				} catch (InterruptedException e) {
					TemplateEngine.logError(Messages.getString("ChooseTemplatePage.TemplateLoadingInterrupted"), e); //$NON-NLS-1$
					// Nothing to do if the user interrupts.
				}
			}

			if (templates == null) {
				Logging.log(null, Logging.newStatus(TemplateWizardPlugin.getDefault(), 
						IStatus.ERROR, Messages.getString("ChooseTemplatePage.TemplatesNotRead"))); //$NON-NLS-1$
				return Collections.emptyList();
			}
			
			Collections.sort(templates, new Comparator<ITemplate>() {
				public int compare(ITemplate t1, ITemplate t2) {
					String label1 = t1.getGroupLabel();
					String label2 = t2.getGroupLabel();
					if (label1.equals(label2)) {
						return t1.getDisplayName().compareToIgnoreCase(t2.getDisplayName());
					}
					return label1.compareTo(label2);
				}
			});
		}
		
		return templates;
	}
	
	private void handleTemplateSelectionChanged() {
		ISelection selection = templateTreeViewer.getSelection();
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;
			TreeNode treeNode = (TreeNode) treeSelection.getFirstElement();
			if (!treeSelection.isEmpty()) {
				Object value = treeNode.getValue();
				if (value instanceof ITemplate) {
					if (selectedTemplateUI != null) {
						selectedTemplateUI.dispose();
					}
					try {
						selectedTemplate = ((ITemplate) value).getLoadedTemplate();
						String description = 
							TextUtils.catenateBrokenLines(selectedTemplate.getDescription());
						description = selectedTemplate.getTemplate().getLocalizedString(description);
						templateDescriptionText.setText(description);
						setSelectedNode(new TemplatePagesNode((TemplateWizard) getWizard()));
						
						// create the UI for the template and initialize its settings
						selectedTemplateUI = selectedTemplate.createLoadedTemplateUI();
						selectedTemplate.getTemplate().getTemplateValues().clear();
						selectedTemplateUI.loadSettings(((TemplateWizard) getWizard()).getPersistedSettingsStorage());
						
						((TemplateWizard) getWizard()).notifyTemplateChanged();
						
					} catch (CoreException e) {
						selectedTemplate = null;
						selectedTemplateUI = null;
						
						Logging.log(null, Logging.newStatus(TemplateWizardPlugin.getDefault(), 
								IStatus.ERROR, Messages.getString("ChooseTemplatePage.FailedToLoadTemplate"), e)); //$NON-NLS-1$
						templateDescriptionText.setText(
								MessageFormat.format(
									Messages.getString("ChooseTemplatePage.FailedToLoadTemplateError"), //$NON-NLS-1$
									e.getMessage()));
					}
					return;
				}
			}
		}
		templateDescriptionText.setText(""); //$NON-NLS-1$
		setSelectedNode(null);
		selectedTemplate = null;
		((TemplateWizard) getWizard()).notifyTemplateChanged();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			if (!inited)
				initTreeViewer(true);
			else
				templateTreeViewer.getTree().setFocus();
		}
	}

	private String getWizardId() {
		IWizard wizard = getWizard();
		Check.checkContract(wizard instanceof TemplateWizard);
		return ((TemplateWizard) wizard).getWizardId();
	}

	private Map<String, List<ITemplate>> createTemplateGroupMap(List<ITemplate> templates) {
		SortedMap<String, List<ITemplate>> treeContent = new TreeMap<String, List<ITemplate>>(new Comparator<String>() {
			public int compare(String o1, String o2) {
				if (o1 == null)
					if (o2 == null)
						return 0;
					else
						return -1;
				else if (o2 == null)
					return 1;
				return o1.compareTo(o2);
			}
		});
		for (Iterator<ITemplate> iter = templates.iterator(); iter.hasNext();) {
			ITemplate template = iter.next();
			if (!filterCheckbox.getSelection() || 
					templateFilter == null || 
					templateFilter.select(template)) {
				String groupLabel = template.getGroupLabel();
				if (!treeContent.containsKey(groupLabel))
					treeContent.put(groupLabel, new ArrayList<ITemplate>());
				treeContent.get(groupLabel).add(template);
			}
		}
		return treeContent;
	}
	
	private TreeNode[] createTreeNodes(List<ITemplate> templates) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		Map<String, List<ITemplate>> map = createTemplateGroupMap(templates);
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			String groupLabel = iter.next();
			TreeNode treeNode = new TreeNode(groupLabel);
			treeNodes.add(treeNode);
			List<ITemplate> childList = map.get(groupLabel);
			TreeNode[] childNodes = new TreeNode[childList.size()];
			int index = 0;
			for (Iterator<ITemplate> iterator = childList.iterator(); iterator.hasNext();) {
				ITemplate template = iterator.next();
				childNodes[index++] = new TreeNode(template);
			}
			treeNode.setChildren(childNodes);
		}
		
		return (TreeNode[]) treeNodes.toArray(new TreeNode[treeNodes.size()]);
	}

	/**
	 * @param filterCheckboxLabel The filterCheckboxLabel to set.
	 */
	public void setFilterCheckboxLabel(String filterCheckboxLabel) {
		this.filterCheckboxLabel = filterCheckboxLabel;
	}

	/**
	 * @param templateFilter The templateFilter to set.
	 */
	public void setTemplateFilter(IFilter templateFilter) {
		this.templateFilter = templateFilter;
	}

	/**
	 * @param hideFilterCheckbox whether to hide the filter checkbox - default false
	 */
	public void setHideFilterCheckbox(boolean hideFilterCheckbox) {
		this.hideFilterCheckbox = hideFilterCheckbox;
	}

	public ITemplate getSelectedTemplate() {
		return selectedTemplate != null ? selectedTemplate.getTemplate() : null;
	}

	public ILoadedTemplate getSelectedLoadedTemplate() {
		return selectedTemplate;
	}
	
	public ILoadedTemplateUI getSelectedTemplateUI() {
		return selectedTemplateUI;
	}

	@Override
	public void setWizard(IWizard newWizard) {
		super.setWizard(newWizard);
		Check.checkContract(newWizard instanceof TemplateWizard);
		TemplateWizard templateWizard = ((TemplateWizard) newWizard);
		setTitle(templateWizard.getChooseTemplatePageTitle());
		setDescription(templateWizard.getChooseTemplatePageDescription());
		setImageDescriptor(ImageDescriptor.createFromImage(newWizard.getDefaultPageImage()));
	}
}
