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
package com.nokia.carbide.cdt.internal.builder.ui;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.internal.builder.CarbideProjectInfo;


public class MMPChangedActionDialog extends StatusDialog {

	public static class MMPChangedAction {
		public IPath fullMMPPath;
		public int mmpAction;
		public boolean isTest;
		
		public MMPChangedAction(IPath fullMMPPath, int mmpAction, boolean isTest) {
			this.fullMMPPath = fullMMPPath;
			this.mmpAction = mmpAction;
			this.isTest = isTest;
		}
	}

	class MMPChangedActionContentProvider implements IStructuredContentProvider {	
	
		public Object[] getElements(Object input) {
			return mmpList.toArray();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}
	
	}
	
	class MMPChangedActionLabelProvider extends LabelProvider implements ITableLabelProvider {

		/**
		 * @see ITableLabelProvider#getColumnText(Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof MMPChangedAction) {
				MMPChangedAction mmp = (MMPChangedAction)element;
				switch(columnIndex) {
					case 0:
						return mmp.fullMMPPath.toOSString();
					case 1:
						switch(mmp.mmpAction) {
						case 0:
							return Messages.getString("SharedPrefs.ActionNone"); //$NON-NLS-1$
						case 1:
							return Messages.getString("SharedPrefs.ActionLinkOnly"); //$NON-NLS-1$
						case 2:
							return Messages.getString("SharedPrefs.ActionCompileAndLink"); //$NON-NLS-1$
						}
				}
			}
			return element.toString();
		}

		/**
		 * @see ITableLabelProvider#getColumnImage(Object, int)
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

	}

	private TableViewer tableViewer;
	private TableEditor tableEditor;
	private Button setNoneButton;
	private Button setRelinkButton;
	private Button setRecompileButton;
	private Button dontAskAgainButton;
	private Link prefsLink;

	private IProject project;
	private List<MMPChangedAction> mmpList;
	private boolean dontAskAgain;

	
	/**
	 * Create the dialog
	 * @param parent
	 * @param file
	 */
	public MMPChangedActionDialog(Shell parent, IProject project, List<MMPChangedAction> mmpList) {
		super(parent);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.project = project;
		this.mmpList = mmpList;
	}

	/**
	 * @see Windows#configureShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Modified MMP Files Detected");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, CarbideCPPBuilderUIHelpIds.CARBIDE_BUILDER_MMP_CHANGED_ACTION_DIALOG);
	}		

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		
		Label descLabel = new Label(composite, SWT.NONE);
		descLabel.setText("Carbide.c++ has detected changes in one or more MMP files since the last build.\nSpecify the build actions to take for the project MMP files.");
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd.horizontalIndent = 5;
		gd.verticalIndent = 5;
		descLabel.setLayoutData(gd);

		final Table table = new Table(composite, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		table.setLayout(new TableLayout());

		TableColumn column1 = new TableColumn(table, SWT.NULL);
		column1.setText("Modified MMP Path");
		column1.setWidth(500);
	
		TableColumn column2 = new TableColumn(table, SWT.NULL);
		column2.setText("Action");
		column2.setWidth(120);

		tableEditor = new TableEditor(table);
		tableEditor.grabHorizontal = true;
		tableEditor.grabVertical = true;
		tableEditor.setColumn(1);
		
		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null) {
					oldEditor.dispose();
				}
				
				TableItem item = (TableItem) event.item;
				if (item == null) {
					return;
				}

				MMPChangedAction mmp = (MMPChangedAction) item.getData();
				CCombo newEditor = new CCombo(table, SWT.READ_ONLY);
				newEditor.add(Messages.getString("SharedPrefs.ActionNone")); //$NON-NLS-1$);
				newEditor.add(Messages.getString("SharedPrefs.ActionLinkOnly")); //$NON-NLS-1$);
				newEditor.add(Messages.getString("SharedPrefs.ActionCompileAndLink")); //$NON-NLS-1$);
				newEditor.select(mmp.mmpAction);
				
				newEditor.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						CCombo combo = (CCombo) tableEditor.getEditor();
						int index = combo.getSelectionIndex();
						if (index != -1) {
							TableItem item = tableEditor.getItem();
							item.setText(1, combo.getText());
							
							MMPChangedAction mmp = (MMPChangedAction) item.getData();
							mmp.mmpAction = index;
						}
					}
				});
				
				newEditor.setFocus();
				tableEditor.setEditor(newEditor, item, 1);
			}
		});

		tableViewer = new TableViewer(table);			
		tableViewer.setLabelProvider(new MMPChangedActionLabelProvider());
		tableViewer.setContentProvider(new MMPChangedActionContentProvider());

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent evt) {
				enableButtons();
			}
		});
		
		tableViewer.setInput(mmpList);

		Composite buttons = new Composite(composite, SWT.NULL);
		buttons.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
		buttons.setLayout(new GridLayout());
		
		setNoneButton = createPushButton(buttons, "None");
		setNoneButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				setSelectedMMPsAction(0);
			}
		});
		
		setRelinkButton = createPushButton(buttons, "Link Only");
		setRelinkButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				setSelectedMMPsAction(1);
			}
		});
		
		setRecompileButton = createPushButton(buttons, "Compile and Link");
		setRecompileButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				setSelectedMMPsAction(2);
			}
		});

		Composite bottomComposite = new Composite(composite, SWT.NULL);
		bottomComposite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		bottomComposite.setLayout(new GridLayout());
		
		dontAskAgainButton = new Button(bottomComposite, SWT.CHECK);
		dontAskAgainButton.setText("Don't ask me again");
		dontAskAgainButton.setToolTipText("The default action from the preferences will be taken rather than asking each time.");
		dontAskAgainButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		
		CarbideProjectInfo cpi = (CarbideProjectInfo)CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			final boolean useProjectSettings = cpi.overrideWorkspaceBuildSettingsProjectValue();
			String linkText = useProjectSettings ? Messages.getString("MMPChangedActionDialog.ConfigureProjectSettings") : Messages.getString("MMPChangedActionDialog.ConfigureWorkspaceSettings"); //$NON-NLS-1$ //$NON-NLS-2$

			prefsLink = new Link(bottomComposite, SWT.NONE);
			prefsLink.setText("<a>" + linkText + "...</a>"); //$NON-NLS-1$ //$NON-NLS-2$
			prefsLink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			prefsLink.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (useProjectSettings) {
						PreferencesUtil.createPropertyDialogOn(getShell(), project, "com.nokia.carbide.cdt.builder.ui.CarbideCPPProjectSettingsPage", null, null).open(); //$NON-NLS-1$
					} else {
						PreferencesUtil.createPreferenceDialogOn(getShell(), "com.nokia.carbide.cdt.internal.builder.ui.BuilderPreferencePage", null, null).open(); //$NON-NLS-1$
					}
				}
			});
		}
		
		enableButtons();
		
		applyDialogFont(composite);		

		return composite;
	}

	private void enableButtons() {
		int selectionCount = ((IStructuredSelection)tableViewer.getSelection()).size();
		setNoneButton.setEnabled(selectionCount > 0);
		setRelinkButton.setEnabled(selectionCount > 0);
		setRecompileButton.setEnabled(selectionCount > 0);
	}
	
	@SuppressWarnings("unchecked")
	private void setSelectedMMPsAction(int action) {
		// remove any editors
		Control oldEditor = tableEditor.getEditor();
		if (oldEditor != null) {
			oldEditor.dispose();
		}
		
		IStructuredSelection selection = (IStructuredSelection)tableViewer.getSelection();
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			((MMPChangedAction)iter.next()).mmpAction = action;
		}
		tableViewer.refresh();
	}

	protected Button createPushButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		if (label != null) {
			button.setText(label);
		}
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));	
		return button;
	}

	@Override
	protected Point getInitialSize() {
		if (mmpList.size() > 7) {
			return new Point(754, 300);
		}
		return new Point(738, 300);
	}
	
	@Override
	protected void okPressed() {
		dontAskAgain = dontAskAgainButton.getSelection();
		super.okPressed();
	}

	public boolean dontAskAgain() {
		return dontAskAgain;
	}
}
