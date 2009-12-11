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
package com.nokia.sdt.component.symbian.actions;

import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.sourcegen.SourceGenAdapterFactory;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import java.util.*;
import java.util.List;

/**
 * Control debugging of source generation code, by specifying whether
 * to write sourcegen files, and what directory.
 * 
 *
 */
public class EnableSourceGenDebugAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow workbenchWindow;
    IWorkbenchPart workbenchPart;
    IEditorPart editor;
    
    public void init(IWorkbenchWindow window) {
        workbenchWindow = window;
    }
    
    public void dispose() {

    }

	/**
	 * Put up a dialog showing components for selected projects and allow
	 * user to select which of these is debugged.
	 * @param allComponentIds list of all components
	 * @param debuggableComponentIds modified list of selected components
	 * @return true if changes made
	 */
	private boolean selectComponents(final Collection<String> allComponentIds, final Collection<String> debuggableComponentIds) {
		
		final List<String> sortedComponentIds = new ArrayList<String>(allComponentIds);
		Collections.sort(sortedComponentIds);
        ListSelectionDialog dlg = new ListSelectionDialog(
                workbenchWindow.getShell(), new Object(),
                new IStructuredContentProvider() {

					public void dispose() {
					}

					public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
					}

					public Object[] getElements(Object arg0) {
						return sortedComponentIds.toArray();
					}
                	
                },
                new LabelProvider() {

					public String getText(Object element) {
						return element.toString();
					}
                }, 
                "Select components for which to save generated sourcegen scripts:");

        dlg.setInitialSelections(debuggableComponentIds.toArray());
        dlg.setTitle("Debug Component Sourcegen"); //$NON-NLS-1$
        
        int result = dlg.open();
        if (result == IDialogConstants.CANCEL_ID)
            return false;

        debuggableComponentIds.clear();
        List results = Arrays.asList(dlg.getResult());
        for (Iterator iter = results.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			debuggableComponentIds.add((String) obj);
		}
        
        return true;
	}

	class MainDialog extends Dialog {
		private String dir;
		private Collection<String> allComponentIds;
		private Collection<String> debuggableComponentIds;
		private Label componentsLabel;

		protected MainDialog(Shell arg0) {
			super(arg0);
			dir = null;
			allComponentIds = Collections.EMPTY_LIST;
			debuggableComponentIds = Collections.EMPTY_LIST;
		}
		
		public void setOutputDir(String dir) {
			this.dir = dir;
		}
		public String getOutputDir() {
			return dir.length() == 0 ? null : dir;
		}

		protected Control createDialogArea(Composite parent) {
			Composite container = (Composite) super.createDialogArea(parent);

			getShell().setText("Configure SourceGen Debugging");

			Composite gridComposite = new Composite(container, SWT.NONE);
			gridComposite.setLayout(new GridLayout(2, false));
			
			Label label = new Label(gridComposite, SWT.WRAP);
			label.setText("The UI Designer will write the\n" +
					"generated script for the selected\n" +
					"components' <sourceGen> elements to disk,\n"+
					"in addition to any with the debug=\"true\" attribute.\n\n");
			label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, true, true, 2, 1));
			
			Label dirLabel = new Label(gridComposite, SWT.NONE);
			dirLabel.setText("Output directory:");
			dirLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, true, true, 2, 1));

			final Text text = new Text(gridComposite, SWT.BORDER);
			text.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.keyCode == SWT.TAB)
						e.doit = true;
				}
			});
			text.addModifyListener(new ModifyListener(){
				public void modifyText(ModifyEvent e) {
					dir = text.getText();
				} 
			});
			text.setText(dir != null ? dir : "");
			text.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, true, 1, 1));

			Button button = new Button(gridComposite, SWT.PUSH);
			button.setText("Browse...");
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					DirectoryDialog dlg = new DirectoryDialog(getShell());
					BrowseDialogUtils.initializeFrom(dlg, text);
					dlg.setText("Select Output Directory");
					dlg.setMessage("Select output directory");
					String newDir = dlg.open();
					if (newDir != null) {
						text.setText(newDir);
						dir = newDir;
					}
				}
			});
			///////////
			
			componentsLabel = new Label(gridComposite, SWT.WRAP);
			componentsLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, true, true, 2, 1));

			Button componentsButton = new Button(gridComposite, SWT.PUSH);
			componentsButton.setText("Select...");
			componentsButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					List<String> currentList = new ArrayList(debuggableComponentIds);
					if (selectComponents(allComponentIds, currentList)) {
						// save changes
						debuggableComponentIds = currentList;
						updateComponentsLabel();
						getShell().pack();

			    	}
				}
			});
			
			updateComponentsLabel();

			return container;
		}

		private void updateComponentsLabel() {
			StringBuffer ids = new StringBuffer();
			int cnt = 5;
			for (String id : debuggableComponentIds) {
				if (--cnt < 0) {
					ids.append("\t...");
					break;
				}
				ids.append('\t');
				ids.append(id);
				ids.append('\n');
			}
			componentsLabel.setText("Components:\n" + ids);
		}

		public void setComponents(Collection<String> allComponentIds) {
			this.allComponentIds = allComponentIds;
		}

		/** Set the selected components */
		public void setSelectedComponents(Collection<String> selectedIds) {
			this.debuggableComponentIds = new ArrayList<String>(selectedIds);
		}

		public Collection<String> getSelectedComponents() {
			return debuggableComponentIds;
		}
		
	}
	
    public void run(IAction action) {
		IComponentProvider componentProvider;
		try {
			componentProvider = ComponentSystem.getComponentSystem().getProvider(ComponentProvider.PROVIDER_ID);
			Check.checkState(componentProvider != null);
		} catch (CoreException e) {
			ComponentSystemPlugin.log(e);
			return;
		}
    	Collection<String> allComponentIds = getComponentIds(componentProvider);
    	Collection<String> debuggableComponentIds = SourceGenAdapterFactory.getDebuggableComponents();

    	MainDialog dialog = new MainDialog(workbenchWindow.getShell());
    	IPath outputDir = SourceGenAdapterFactory.getDebugOutputDir();
		dialog.setOutputDir(outputDir != null ? outputDir.toOSString() : null);
    	dialog.setComponents(allComponentIds);
    	dialog.setSelectedComponents(debuggableComponentIds);
    	int ret = dialog.open();
    	if (ret == IDialogConstants.OK_ID) {
    		SourceGenAdapterFactory.getDebuggableComponents().clear();
    		SourceGenAdapterFactory.getDebuggableComponents().addAll(dialog.getSelectedComponents());
    		SourceGenAdapterFactory.setDebugOutputDir(new Path(dialog.getOutputDir()));
    		
    		SourceGenAdapterFactory.saveDebugInfo();
    	}
    }

	private Collection<String> getComponentIds(IComponentProvider componentProvider) {
		ComponentSetResult result = componentProvider.queryComponents(null);
		if (result == null || result.getComponentSet() == null)
			return Collections.EMPTY_LIST;
		
		Set<String> componentIds = new HashSet<String>();
		for (Iterator iter = result.getComponentSet().iterator(); iter.hasNext();) {
			componentIds.add(((IComponent)iter.next()).getId());
		}
		return componentIds;
	}

	public void selectionChanged(IAction action, ISelection selection) {
    	action.setEnabled(false);
		try {
			IComponentProvider componentProvider = ComponentSystem.getComponentSystem().getProvider(ComponentProvider.PROVIDER_ID);
			if (componentProvider != null) {
				action.setEnabled(true);
			}
		} catch (CoreException e) {
		}
    }
}
