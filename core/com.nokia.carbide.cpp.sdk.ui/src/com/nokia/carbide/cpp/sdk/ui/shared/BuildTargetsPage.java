/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.sdk.ui.shared;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.nokia.carbide.cpp.internal.api.sdk.ui.TemplateUtils;
import com.nokia.carbide.cpp.internal.sdk.ui.Messages;
import com.nokia.carbide.cpp.internal.sdk.ui.SDKUIHelpIds;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.HostOS;

/**
 * Wizard page used to select a list of Carbide.c++ build configurations.  Currently used
 * by the new project and import bld.inf wizards, but can be used by any wizard that needs
 * to get a list of build configs.
 */
public class BuildTargetsPage extends WizardPage implements IWizardDataPage {
	
	protected static final String UID = ".uid"; //$NON-NLS-1$
	protected static final String SELECTED_BUILD_CONFIGS_VALUE_KEY = "selectedBuildConfigs"; //$NON-NLS-1$
	
	/**
	 * @since 1.4
	 */
	public static final String SBSV2BUILDER = "useSBSv2Builder"; //$NON-NLS-1$

	protected static class FilteringContentProviderWrapper implements ITreeContentProvider {

		protected ITreeContentProvider wrappedProvider;
		protected IFilter filter;
		protected IFilter configFilter;
		
		public FilteringContentProviderWrapper(ITreeContentProvider wrappedProvider) {
			super();
			Check.checkArg(wrappedProvider);
			this.wrappedProvider = wrappedProvider;
		}

		public Object[] getElements(Object inputElement) {
			List<Object> filteredElements = new ArrayList<Object>();
			Object[] elements = wrappedProvider.getElements(inputElement);
			for (int i = 0; i < elements.length; i++) {
				Object object = elements[i];
				if (filter == null || filter.select(object))
					filteredElements.add(object);
			}
			
			return filteredElements.toArray();
		}

		public void dispose() {
			wrappedProvider.dispose();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			wrappedProvider.inputChanged(viewer, oldInput, newInput);
		}

		public Object[] getChildren(Object parentElement) {
			List<Object> filteredElements = new ArrayList<Object>();
			Object[] elements = wrappedProvider.getChildren(parentElement);
			if (elements != null) {
				for (int i = 0; i < elements.length; i++) {
					Object object = elements[i];
					if (configFilter == null || configFilter.select(object))
						filteredElements.add(object);
				}
			}
			
			return filteredElements.toArray();
		}

		public Object getParent(Object element) {
			return wrappedProvider.getParent(element);
		}

		public boolean hasChildren(Object element) {
			return wrappedProvider.hasChildren(element);
		}

		public void setFilter(IFilter filter) {
			this.filter = filter;
		}
		
		public void setConfigFilter(IFilter filter) {
			this.configFilter = filter;
		}
	}
	
	static class TemplateSDKFilter implements IFilter {
		
		private ITemplate template;

		public void setTemplate(ITemplate template) {
			this.template = template;
		}
		
		public boolean select(Object toTest) {
			if (template == null)
				return true;
			
			Check.checkArg(toTest instanceof BuildTargetTreeNode);
			ISymbianSDK symbianSDK = ((BuildTargetTreeNode) toTest).getSymbianSDK();
			return TemplateUtils.sdkMatchesTemplate(symbianSDK, template);
		}
	}
	
	private Button filterCheckbox;
	protected ContainerCheckedTreeViewer viewer;
	private Label configLabel;
	private boolean hideFilterCheckbox;
	protected FilteringContentProviderWrapper filteringContentProviderWrapper;
	private TemplateSDKFilter templateFilter;
	private boolean inited;
	private boolean useSBSv2Builder;

	/**
	 * Default constructor
	 */
	public BuildTargetsPage() {
		super(Messages.getString("BuildTargetsPage.BuildTargetsTitle")); //$NON-NLS-1$
		setTitle(Messages.getString("BuildTargetsPage.SymbianSDKS")); //$NON-NLS-1$
		setDescription(Messages.getString("BuildTargetsPage.BuildTargetsDescription")); //$NON-NLS-1$
		templateFilter = new TemplateSDKFilter();
	}

	/**
	 * see {@link IDialogPage#createControl(Composite)}
	 */
	public void createControl(Composite parent) {
		setPageComplete(false);
		setErrorMessage(null);
		setMessage(null);

		initializeDialogUnits(parent);
        
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createControls(composite);
		
		setControl(composite);
 
		getControl().setData(UID, "BuildTargetsPage"); //$NON-NLS-1$
		getControl().setData("WizardPage", this); //$NON-NLS-1$
	}

	private void createControls(Composite parent) {
		Font font = parent.getFont();
		
		configLabel = new Label(parent, SWT.LEFT);
		configLabel.setFont(font);
		configLabel.setText(Messages.getString("BuildTargetsPage.BuildConfigsLabel")); //$NON-NLS-1$
		configLabel.setToolTipText(Messages.getString("BuildTargetsPage.BuildConfigsToolTip")); //$NON-NLS-1$
		
        drawSDKConfigView(parent);
		
        filterCheckbox = new Button(parent, SWT.CHECK);
        filterCheckbox.setText(Messages.getString("BuildTargetsPage.FilterText")); //$NON-NLS-1$
        filterCheckbox.setSelection(true); // default to checked
        if (hideFilterCheckbox)
        	filterCheckbox.setVisible(false);
        else
        	filteringContentProviderWrapper.setFilter(templateFilter);
		filterCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TemplateSDKFilter filter = filterCheckbox.getSelection() ? templateFilter : null;
				filteringContentProviderWrapper.setFilter(filter);
				viewer.refresh();
				setPageComplete(validatePage());
			}
        });
		filterCheckbox.setData(UID, "filterCheckbox"); //$NON-NLS-1$

		addOtherControls(parent);
		
		Link fLink = new Link(parent, SWT.WRAP);
		fLink.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fLink.setText(Messages.getString("BuildTargetsPage.Select_Filtering_Prefs_Link")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		fLink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		fLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// I don't see a way to open it to a specific tab, only the page
				if (Window.OK == PreferencesUtil.createPreferenceDialogOn(getShell(), "com.nokia.carbide.cpp.sdk.ui.preferences.BuildPlatformFilterPage", null, null, 0).open()){ //$NON-NLS-1$
					
					inited = false;
					setVisible(true);
					TemplateSDKFilter filter = filterCheckbox.getSelection() ? templateFilter : null;
					filteringContentProviderWrapper.setFilter(filter);
					viewer.getTree().clearAll(true);
					viewer.refresh();
					setPageComplete(validatePage());
				}
			}

		});
		
		setPageComplete(validatePage());
	}
	
	private void drawSDKConfigView(Composite parent) {
		
		viewer = new ContainerCheckedTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setLabelProvider(new LabelProvider());
		
		TreeNodeContentProvider treeNodeContentProvider = new TreeNodeContentProvider();
		filteringContentProviderWrapper = 
			new FilteringContentProviderWrapper(treeNodeContentProvider);
		viewer.setContentProvider(filteringContentProviderWrapper);
		viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				setPageComplete(validatePage());
			}
		});
		viewer.getControl().setData(UID, "buildConfigsTree"); //$NON-NLS-1$
		viewer.getControl().setData("viewer", viewer); //$NON-NLS-1$
		
	}

	/**
	 * Add more controls after the tree and filter checkbox.  Allows subclasses to add
	 * more controls.
	 * @since 1.4
	 */
	protected void addOtherControls(Composite parent) {
	}
	
	/**
	 * If using the template filter checkbox, this allows you to set the
	 * template to filter on.
	 * @param template the template to filter on
	 * @since 1.4
	 */
	public void setSelectedTemplate(ITemplate template) {
		this.templateFilter.setTemplate(template);
		
		// need to init again if they select another template
		inited = false;
	}
	
	/**
	 * Hides the "Filter SDKs based on selected template" checkbox
	 */
	public void setHideFilterCheckbox() {
		hideFilterCheckbox = true;
	}
	
	protected boolean validatePage() {
		boolean isValid = false;
		if (viewer.getTree().getItemCount() == 0) {
			if (filterCheckbox.getSelection())
				setErrorMessage(Messages.getString("BuildTargetsPage.NoMatchingSDKsError")); //$NON-NLS-1$
			else
				setErrorMessage(Messages.getString("BuildTargetsPage.NoSDKsError")); //$NON-NLS-1$
		} else if ((viewer.getCheckedElements()).length > 1) {
			setErrorMessage(null);
			isValid = true;
		} else {
			setErrorMessage(Messages.getString("BuildTargetsPage.NoBuildConfigsSelectedError")); //$NON-NLS-1$
		}
		return isValid;
	}
	
	protected void checkPathWithSDKs(IPath path) {
		setMessage(null);
		
		// drives are relevant in Windows only
		if (!HostOS.IS_WIN32)
			return;
		
		for (ISymbianBuildContext context : getSelectedBuildConfigs()) {
			Path sdkPath = new Path(context.getSDK().getEPOCROOT());
			if (sdkPath.getDevice() != null) {
				if (!useSBSv2Builder) {
					if (!sdkPath.getDevice().equalsIgnoreCase(path.getDevice())) {
						// there are no warnings so set the error message but return true so
						// the user can continue if they choose to ignore our warning.
						String warning = MessageFormat.format(
								Messages.getString("BuildTargetsPage.DifferentDrivesError"),  //$NON-NLS-1$
								new Object[] { context.getSDK().getUniqueId() });
						setMessage(warning, DialogPage.WARNING);
					}
				}
			} else {
				// else devices.xml 'epocroot' has no drive spec, so it will use the implicit epocroot
				String warning = MessageFormat.format(
						Messages.getString("BuildTargetsPage.NoDriveSpecWarning"),  //$NON-NLS-1$
						new Object[] { context.getSDK().getUniqueId() });
				setMessage(warning, DialogPage.WARNING);
			}
			
		}
	}

	/**
	 * Returns the list of build configs selected in this page to template wizards.
	 * @return the map of values for this page - 
	 * a singleton map containing the list of selected build configs mapped to the key "selectedBuildConfigs". 
	 */
	public Map<String, Object> getPageValues() {
		return Collections.singletonMap(SELECTED_BUILD_CONFIGS_VALUE_KEY, (Object)getSelectedBuildConfigs());
	}

	/**
	 * Gets the list of build configs selected in this page
	 * @return the list of build configs checked in the tree viewer
	 */
	public List<ISymbianBuildContext> getSelectedBuildConfigs() {
		List<ISymbianBuildContext> selectedConfigs = new ArrayList<ISymbianBuildContext>();
		Object[] items = viewer.getCheckedElements();
		for (int i=0; i<items.length; i++) {
			TreeNode node = (TreeNode)items[i];
			if (node.getValue() instanceof ISymbianBuildContext) {
				selectedConfigs.add((ISymbianBuildContext)node.getValue());
			}
		}
		return selectedConfigs;
	}

	/**
	 * See {@link IDialogPage#setVisible(boolean)}
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (visible) {
			// set the viewer input when the page becomes visible, but only do it once
			// unless the builder has changed.
			boolean setInput = true;
			
			Object value = getShell().getData(SBSV2BUILDER);
			if (value != null && value instanceof Boolean) {
				boolean sbsv2 = ((Boolean)value).booleanValue();
				if (sbsv2 == useSBSv2Builder) {
					if (inited) {
						setInput = false;
					}
				} else {
					useSBSv2Builder = sbsv2;
				}
			}

			if (setInput) {
		        viewer.setInput(BuildTargetTreeNode.getTreeViewerInput(useSBSv2Builder));
			}

			if (!inited) {
				inited = true;
				TreeItem[] items = viewer.getTree().getItems();
				if (items != null && items.length == 1) {
					viewer.setChecked(items[0].getData(), true);
				}
			}
			
			setPageComplete(validatePage());
		}
	}

	@Override
	public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl().getShell(), SDKUIHelpIds.BUILD_TARGETS_PAGE);
	}
}
