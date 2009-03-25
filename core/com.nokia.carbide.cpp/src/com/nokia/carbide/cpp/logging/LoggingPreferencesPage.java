package com.nokia.carbide.cpp.logging;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class LoggingPreferencesPage extends PreferencePage implements IWorkbenchPreferencePage {

	class TreeContentProvider implements IStructuredContentProvider, ITreeContentProvider {
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object inputElement) {
			return DiagnosticLogManager.getDiagnosticLogManager().getLogGroups();
		}

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof DiagnosticLogGroup) {
				return ((DiagnosticLogGroup) parentElement).getLogs();
			}
			return new Object[0];
		}

		public Object getParent(Object element) {
			return null;
		}

		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}
	}

	class TreeLabelProvider extends LabelProvider {
		public String getText(Object element) {
			if (element instanceof DiagnosticLogGroup) {
				return ((DiagnosticLogGroup) element).getName();
			}
			if (element instanceof DiagnosticLog) {
				return ((DiagnosticLog) element).getName();
			}
			return super.getText(element);
		}

		public Image getImage(Object element) {
			return null;
		}
	}

	private Tree tree;
	private CheckboxTreeViewer checkboxTreeViewer;
	private Text logPath;

	/**
	 * Create the preference page
	 */
	public LoggingPreferencesPage() {
		super();
		setTitle("Carbide.c++ Support");
		setDescription("Diagnostic Logs");
	}

	/**
	 * Create contents of the preference page
	 * 
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		checkboxTreeViewer = new CheckboxTreeViewer(container, SWT.BORDER);
		checkboxTreeViewer.setContentProvider(new TreeContentProvider());
		checkboxTreeViewer.setLabelProvider(new TreeLabelProvider());
		tree = checkboxTreeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		logPath = new Text(container, SWT.WRAP | SWT.READ_ONLY | SWT.MULTI | SWT.BORDER);
		final GridData gd_logPath = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_logPath.heightHint = 32;
		logPath.setLayoutData(gd_logPath);
		//
		
		checkboxTreeViewer.setInput(this);
		checkboxTreeViewer.setExpandedElements(DiagnosticLogManager.getDiagnosticLogManager().getLogGroups());

		for (DiagnosticLogGroup logGroup : DiagnosticLogManager.getDiagnosticLogManager().getLogGroups()) {
			DiagnosticLog[] logs = logGroup.getLogs();
			for (int i = 0; i < logs.length; i++) {
				Logger logger = logs[i].getLogger();
				checkboxTreeViewer.setChecked(logs[i], logger.getLevel() == null || !logger.getLevel().equals(Level.OFF));
			}
		}

		updateGroupsCheckedState();
		
		checkboxTreeViewer.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				handleLogChecked(event);			
			}});
		
		checkboxTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				showLogLocation(event.getSelection());
			}});

		final Button clearLogsButton = new Button(container, SWT.NONE);
		final GridData gd_clearLogsButton = new GridData();
		clearLogsButton.setLayoutData(gd_clearLogsButton);
		clearLogsButton.setText("Delete");

		final Button openLogsButton = new Button(container, SWT.NONE);
		openLogsButton.setLayoutData(new GridData());
		openLogsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent event) {
				openLogs();
			}
		});
		openLogsButton.setText("Open");
		return container;
	}

	protected void handleLogChecked(CheckStateChangedEvent event) {
		Object element = event.getElement();
		if (element instanceof DiagnosticLogGroup)
		{
			DiagnosticLogGroup logGroup = (DiagnosticLogGroup) element;
			DiagnosticLog[] logs = logGroup.getLogs();
			for (int i = 0; i < logs.length; i++) {
				{
				checkboxTreeViewer.setChecked(logs[i], event.getChecked());
				enableLog(logs[i], event.getChecked());
				}
			}			
		}
		else
			if (element instanceof DiagnosticLog)
			{
				DiagnosticLog log = (DiagnosticLog) element;
				enableLog(log, event.getChecked());
			}
		updateGroupsCheckedState();
	}

	private void enableLog(DiagnosticLog log, boolean enabled) {
		if (enabled)
		{
			log.getLogger().setLevel(Level.ALL);
			try {
				logPath.setText(log.getFile().getCanonicalPath());
			} catch (IOException e) { e.printStackTrace(); }
		}
		else
		{
			log.getLogger().setLevel(Level.OFF);
		}
		DiagnosticLogManager.saveLogSettings();
	}

	protected void showLogLocation(ISelection selection) {
		if (selection instanceof IStructuredSelection)
		{
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object firstElement = structuredSelection.getFirstElement();
			if (firstElement instanceof DiagnosticLog)
			{
				DiagnosticLog log = (DiagnosticLog) firstElement;
				try {
					logPath.setText(log.getFile().getCanonicalPath());
				} catch (IOException e) { e.printStackTrace(); }
			}					
		}
		
	}

	protected void openLogs() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null)
		{
			IWorkbenchPage activePage = window.getActivePage();
			if (activePage != null)
			{						
				Object[] checkedElements = checkboxTreeViewer.getCheckedElements();						
				for (Object object : checkedElements) {
					if (object instanceof DiagnosticLog)
					{
						DiagnosticLog log = (DiagnosticLog) object;
						File logFile = log.getFile();
						if (logFile.exists()) {
							IFileStore fileStore= EFS.getLocalFileSystem().getStore(Path.fromOSString(logFile.getAbsolutePath()));
							try {
								IDE.openEditorOnFileStore(activePage, fileStore);
							} catch (PartInitException e) { e.printStackTrace(); }							
							}
					}										
				}
				
			}					
		}
		
	}

	private void updateGroupsCheckedState() {
		for (DiagnosticLogGroup logGroup : DiagnosticLogManager.getDiagnosticLogManager().getLogGroups()) {
			boolean allChecked = true;
			boolean anyChecked = false;
			DiagnosticLog[] logs = logGroup.getLogs();
			for (int i = 0; i < logs.length; i++) {
				boolean logChecked = checkboxTreeViewer.getChecked(logs[i]);
				allChecked = allChecked && logChecked;
				anyChecked = anyChecked || logChecked;
			}
			checkboxTreeViewer.setChecked(logGroup, allChecked);
			checkboxTreeViewer.setGrayed(logGroup, anyChecked && !allChecked);
		}
		
	}

	protected void mailLogs() {
		Browser browser = new Browser(tree, 0);
		String mailAddress = "ken.ryall@gmail.com";
		String subject = "Carbide.c++ Logs";
		String url = "mailto:" + mailAddress + "?subject=" + subject + "&attachments=";
		url += "'C:\\epocwind.out.txt'";
		browser.setUrl(url);
		browser.dispose();
	}

	/**
	 * Initialize the preference page
	 */
	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}

}
