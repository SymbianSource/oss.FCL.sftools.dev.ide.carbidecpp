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
package com.nokia.cdt.internal.debug.launch.wizard;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Pair;

public class MainExecutableSelectionWizardPage extends WizardPage {
    
	public interface IPathValidator {
		/**
		 * @param path IPath
		 * @return Error string or <code>null</code> if is valid
		 */
		String isValidPath(IPath path);
	}

	private static final String EMULATOR = "Emulator"; //$NON-NLS-1$
	private static final String BROWSE_ITEM = Messages.getString("MainExecutableSelectionWizardPage.BrowseLabel"); //$NON-NLS-1$
	private static final String EXE = "exe"; //$NON-NLS-1$
	private static final String DLL = "dll"; //$NON-NLS-1$

	// the following two arrays need to correspond
	private static final String[] FILTER_EXTS  = { 
		"*." + EXE, //$NON-NLS-1$
		"*.*" //$NON-NLS-1$
	};
	private static final String[] FILTER_EXT_NAMES  = { 
		Messages.getString("MainExecutableSelectionWizardPage.ExecutableFilesLabel"), //$NON-NLS-1$
		Messages.getString("MainExecutableSelectionWizardPage.AllFilesLabel") //$NON-NLS-1$
	};
	
	
	private ComboViewer viewer;
	private Text exePathLabel;
	private Button usePathCheck;
	private Text pathText;
    private boolean isDisposed = false;
    private String selectedItem;
    private String targetPath;
    private Map<String, Pair<IPath, IPath>> displayStringToExeMmpPair;
    private List<Pair<IPath, IPath>> unDisplayedDlls;
    private IPath emulatorPath;
	private boolean asProcessToLaunch;
	private boolean useEmulatorByDefault;
	private final ISummaryTextItemContainer summaryTextItemContainer;
	private IPathValidator pathValidator;
	private List<String> input;
	private IPath defaultExecutable;
    
    public MainExecutableSelectionWizardPage(List<IPath> mmps, List<IPath> exes, 
    			IPath defaultExecutable, boolean asProcessToLaunch, IPath emulatorPath, boolean emulatorOnly, ISummaryTextItemContainer summaryTextItemContainer) {
        super("MainExecutableSelectionWizardPage"); //$NON-NLS-1$
		Check.checkArg(summaryTextItemContainer);
        this.asProcessToLaunch = asProcessToLaunch;
		this.emulatorPath = emulatorPath;
		this.useEmulatorByDefault = emulatorOnly;
		this.summaryTextItemContainer = summaryTextItemContainer;
		this.defaultExecutable = defaultExecutable;
        
        createExesMap(exes, mmps);
        setPageComplete(false);
        setTitle(getAltString("MainExecutableSelectionWizardPage.Title")); //$NON-NLS-1$
        setDescription(getAltString("MainExecutableSelectionWizardPage.Description")); //$NON-NLS-1$
    }
    
	private void createExesMap(List<IPath> exes, List<IPath> mmps) {
		displayStringToExeMmpPair = new HashMap<String, Pair<IPath, IPath>>();
		unDisplayedDlls = new ArrayList<Pair<IPath,IPath>>();
		// mmps may be empty if executables project
		boolean hasMMPs = !mmps.isEmpty();
		for (int i = 0; i < exes.size(); i++) {
			IPath exePath = exes.get(i);
			Pair<IPath, IPath> pair = new Pair<IPath, IPath>(exePath, hasMMPs ? mmps.get(i) : null);
			// if used as process to launch, then only use .exe paths
			if (!asProcessToLaunch || EXE.equalsIgnoreCase(exePath.getFileExtension())) {
				displayStringToExeMmpPair.put(exePath.lastSegment(), pair);
			}
			else if (DLL.equalsIgnoreCase(exePath.getFileExtension())) {
				unDisplayedDlls.add(pair);
			}
		}		
	}

    public void dispose() {
    	isDisposed = true;
    	getValues();
    	super.dispose();
    }

	private void getValues() {
		if (selectedItem != null || targetPath != null)
			return;
		
		boolean usePath = usePathCheck != null && usePathCheck.getSelection();
    	if (!usePath) {
    		ISelection selection = viewer.getSelection();
    		if (!selection.isEmpty()) {
    			selectedItem = (String) ((IStructuredSelection) selection).getFirstElement();
    		}
    	}
    	else {
    		targetPath = pathText.getText();
    	}
	}
    
	/*
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout(1, false));
        composite.setLayoutData(new GridData());
            
		final Label exeLabel = new Label(composite, SWT.NONE);
		exeLabel.setText(getAltString("MainExecutableSelectionWizardPage.ExeLabel")); //$NON-NLS-1$
		exeLabel.setToolTipText(getAltString("MainExecutableSelectionWizardPage.ExeToolTip")); //$NON-NLS-1$
		exeLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		exeLabel.setData(".uid", "MainExecutableSelectionWizardPage.exeLabel");

		viewer = new ComboViewer(composite, SWT.READ_ONLY);
		Combo combo = viewer.getCombo();
		combo.setData(".uid", "MainExecutableCombo"); //$NON-NLS-1$ //$NON-NLS-2$
		combo.setToolTipText(getAltString("MainExecutableSelectionWizardPage.ExeToolTip")); //$NON-NLS-1$
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		combo.setVisibleItemCount(20);
		combo.setData(".uid", "MainExecutableSelectionWizardPage.combo");
		
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider());
		
		viewer.setSorter(new ViewerSorter() {
		    public int compare(Viewer viewer, Object o1, Object o2) {
		    	if (o1.equals(EMULATOR) || o2.equals(BROWSE_ITEM))
		    		return -1;
		    	if (o2.equals(EMULATOR) || o1.equals(BROWSE_ITEM))
		    		return 1;
				if (!asProcessToLaunch) { // put .exe before any other extension, if not as process to launch
					boolean isEXE1 = EXE.equalsIgnoreCase(new Path(o1.toString()).getFileExtension());
					boolean isEXE2 = EXE.equalsIgnoreCase(new Path(o2.toString()).getFileExtension());
					if (isEXE1 != isEXE2) { // if only one is an exe
						return isEXE1 ? -1 : 1; // return -1 if exe path is .exe, 1 otherwise, sorting .exe paths ahead of anything else
					}
				}
				
				return o1.toString().compareToIgnoreCase(o2.toString());
		    }
		});
		
		input = new ArrayList<String>(displayStringToExeMmpPair.keySet());

		if (emulatorPath != null) {
			input.add(EMULATOR); //$NON-NLS-1$
			input.add(BROWSE_ITEM);
		}
		
		if (asProcessToLaunch) {
			Label label = new Label(composite, SWT.NONE);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			label.setText(Messages.getString("MainExecutableSelectionWizardPage.FullPathLabel")); //$NON-NLS-1$
			if (emulatorPath == null)
				label.setVisible(false);
			exePathLabel = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.READ_ONLY);
			exePathLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			exePathLabel.setData(".uid", "MainExecutableSelectionWizardPage.exePathLabel");

			if (emulatorPath == null) {
				exePathLabel.setVisible(false);
				usePathCheck = new Button(composite, SWT.CHECK);
				usePathCheck.setSelection(false);
				usePathCheck.setText(Messages.getString("MainExecutableSelectionWizardPage.UsePathLabel.device")); //$NON-NLS-1$
				usePathCheck.setToolTipText(Messages.getString("MainExecutableSelectionWizardPage.UsePathLabel.device.ToolTip")); //$NON-NLS-1$
				usePathCheck.setData(".uid", "MainExecutableSelectionWizardPage.usePathCheck");
				
				GridData gd = new GridData(SWT.FILL, SWT.CENTER, false, false);
				gd.verticalIndent = 30;
				usePathCheck.setLayoutData(gd);
				usePathCheck.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if (input.isEmpty())
							usePathCheck.setSelection(true);
						enableControls(usePathCheck.getSelection());
					}
				});
				
				pathText = new Text(composite, SWT.BORDER);
				pathText.setEnabled(false);
				pathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				pathText.setToolTipText(Messages.getString("MainExecutableSelectionWizardPage.UsePathLabel.device.ToolTip")); //$NON-NLS-1$
				pathText.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validatePage();
					}
				});
				pathText.setData(".uid", "MainExecutableSelectionWizardPage.pathText");
				
				if (input.isEmpty()) {
					usePathCheck.setSelection(true);
					enableControls(true);
				}
			}
			
			viewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					String item = (String) selection.getFirstElement();
					if (item.equals(BROWSE_ITEM)) {
						FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
						fileDialog.setText(Messages.getString("MainExecutableSelectionWizardPage.SelectExectuableTitle")); //$NON-NLS-1$
						fileDialog.setFilterPath(emulatorPath.removeLastSegments(1).toOSString());
						fileDialog.setFilterExtensions(FILTER_EXTS);
						fileDialog.setFilterNames(FILTER_EXT_NAMES);
						String pathstr = fileDialog.open();
						if (pathstr != null) {
							IPath path = new Path(pathstr);
							String displayString = path.lastSegment();
							Pair<IPath, IPath> pair = new Pair<IPath, IPath>(path, null);
							if (!displayStringToExeMmpPair.values().contains(pair)) {
								displayStringToExeMmpPair.put(displayString, pair);
								input.add(displayString);
								viewer.setInput(input);
							}
							item = displayString;
						}
						else {
							// just set selection to first item in combo, if user cancels browse
							item = viewer.getCombo().getItem(0);
						}
						viewer.setSelection(new StructuredSelection(item));
					}
					else {
						Pair<IPath, IPath> pair = getSelectedExeMmpPair(item);
						exePathLabel.setText(pair.first.toOSString());
						exePathLabel.getParent().layout();
					}
				}
			});
			
		}
		
		viewer.setInput(input);
		if (!input.isEmpty()) {
			int index = (emulatorPath != null && combo.getItemCount() > 1 && !useEmulatorByDefault) ? 1 : 0;
			
			if (index == 1 && defaultExecutable != null)
			{
				int defaultExeIndex = index = combo.indexOf(defaultExecutable.lastSegment());
				if (defaultExeIndex > 0)
					index = defaultExeIndex;
			}
			
			combo.select(index);
			if (exePathLabel != null) {
				Pair<IPath, IPath> pair = getSelectedExeMmpPair(combo.getItem(index));
				exePathLabel.setText(pair.first.toOSString());
			}
		}
		
		setControl(composite);
        Dialog.applyDialogFont(parent);
        validatePage();
    }
    
    protected void enableControls(boolean usePath) {
		viewer.getCombo().setEnabled(!usePath);
		exePathLabel.setEnabled(!usePath);
		pathText.setEnabled(usePath);
		if (usePath)
			pathText.setFocus();
		validatePage();
	}

	public Pair<IPath, IPath> getSelectedExeMmpPair() {
    	if (!isDisposed) {
    		getValues();
     	}
     	
    	if (selectedItem != null)
    		return getSelectedExeMmpPair(selectedItem);
    	else {
    		Collection<Pair<IPath, IPath>> exeMmpPairs = displayStringToExeMmpPair.values();
    		if (targetPath != null) {
	    		// see if we can use the selected path - process to launch string
	    		// by checking if the file name matches any of the ones in our list of exes
	    		IPath path = new Path(targetPath);
	    		String filename = path.lastSegment();
	    		for (Pair<IPath, IPath> exeMmpPair : exeMmpPairs) {
	    			IPath exePath = exeMmpPair.first;
	    			if (filename.equalsIgnoreCase(exePath.lastSegment())) {
	    				return exeMmpPair;
	    			}
	    		}
    		}
    		// none could be found matching the selected path, so use the first in the list, if not empty
    		if (!exeMmpPairs.isEmpty())
    			return exeMmpPairs.iterator().next();
    		
    		// return something from the undisplayed exes
    		if (!unDisplayedDlls.isEmpty())
    			return unDisplayedDlls.get(0);
    		
    		return new Pair(null, null); // debugging will fail due to no program name
    	}
    }

	private Pair<IPath, IPath> getSelectedExeMmpPair(String item) {
		if (item.equals(EMULATOR))
    		return new Pair<IPath, IPath>(emulatorPath, null);
    	
    	return displayStringToExeMmpPair.get(item);
	}
	
	public IPath getProcessToLaunchTargetPath() {
    	if (!isDisposed) {
    		getValues();
     	}
     	
    	if (targetPath != null)
    		return new Path(targetPath);
    	
    	return null;
	}
    
    private String getAltString(String key) {
    	String altKey = key;
    	if (!asProcessToLaunch)
    		altKey += "." + "mainExe"; //$NON-NLS-1$ //$NON-NLS-2$
    	return Messages.getString(altKey);
    }
    
    private void validatePage() {
		boolean isValid = input != null && !((List) input).isEmpty();
    	String error = null;
    	
    	if (usePathCheck != null && usePathCheck.getSelection()) {
    		if (pathValidator != null) {
    			error = pathValidator.isValidPath(new Path(pathText.getText()));
    			isValid = error == null;
    		}
    		else if (pathText.getText().length() == 0) {
    			isValid = false;
     		}
    	}
    	
    	setPageComplete(isValid);
    	if (isValid)
    		setErrorMessage(null);
    	else {
    		if (error != null)
    			setErrorMessage(error);
    		else
    			setErrorMessage(Messages.getString("MainExecutableSelectionWizardPage.NoExesError"));  //$NON-NLS-1$
    	}
    }
    
    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	if (!visible) {
    		boolean usePath = usePathCheck != null && usePathCheck.getSelection();
    		String executable;
    		if (usePath)
    			executable = pathText.getText();
    		else
    			executable = getSelectedExeMmpPair().first.lastSegment();
    		
			summaryTextItemContainer.putSummaryTextItem("Executable",  //$NON-NLS-1$
    				MessageFormat.format("{0} {1}", new Object[] { //$NON-NLS-1$
    						getAltString("MainExecutableSelectionWizardPage.ExeLabel"), //$NON-NLS-1$
    						executable }));
    	}
    }
    
    public void setPathValidator(IPathValidator pathValidator) {
		this.pathValidator = pathValidator;
    }
    
    @Override
    public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(LaunchWizardHelpIds.WIZARD_BINARY_SELECTION_PAGE);
    }
}