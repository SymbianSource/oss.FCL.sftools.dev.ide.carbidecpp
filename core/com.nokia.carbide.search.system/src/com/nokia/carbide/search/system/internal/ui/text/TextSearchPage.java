/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Juerg Billeter, juergbi@ethz.ch - 47136 Search view should show match objects
 *     Ulrich Etter, etteru@ethz.ch - 47136 Search view should show match objects
 *     Roman Fuchs, fuchsro@ethz.ch - 47136 Search view should show match objects
 *******************************************************************************/
package com.nokia.carbide.search.system.internal.ui.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.FindReplaceDocumentAdapterContentProposalProvider;

import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.ide.FileStoreEditorInput;

import com.nokia.carbide.search.system.internal.ui.ISearchHelpContextIds;
import com.nokia.carbide.search.system.internal.ui.SearchMessages;
import com.nokia.carbide.search.system.internal.ui.SearchPlugin;
import com.nokia.carbide.search.system.internal.ui.util.FileTypeEditor;
import com.nokia.carbide.search.system.internal.ui.util.SWTUtil;
import com.nokia.carbide.search.system.ui.ISearchQuery;
import com.nokia.carbide.search.system.ui.ISearchResultPage;
import com.nokia.carbide.search.system.ui.ISearchResultViewPart;
import com.nokia.carbide.search.system.ui.NewSearchUI;
import com.nokia.carbide.search.system.ui.text.FileTextSearchScope;
import com.nokia.carbide.search.system.ui.text.TextSearchQueryProvider;
import com.nokia.carbide.search.system.ui.text.TextSearchQueryProvider.TextSearchInput;


public class TextSearchPage extends DialogPage implements ISearchPage {//, IReplacePage {

    private static final int HISTORY_SIZE= 12;
	public static final String EXTENSION_POINT_ID= "com.nokia.carbide.search.system.internal.ui.text.TextSearchPage"; //$NON-NLS-1$

	// Dialog store id constants
	private static final String PAGE_NAME= "TextSearchPage"; //$NON-NLS-1$
	private static final String STORE_CASE_SENSITIVE= "CASE_SENSITIVE"; //$NON-NLS-1$
	private static final String STORE_IS_REG_EX_SEARCH= "REG_EX_SEARCH"; //$NON-NLS-1$
	private static final String STORE_SEARCH_DERIVED= "SEARCH_DERIVED"; //$NON-NLS-1$
	private static final String STORE_ADDED_FILENAMES= "ADDED_FILENAMES"; //$NON-NLS-1$
	private static final String STORE_ADDED_FOLDERS= "ADDED_FOLDERS"; //$NON-NLS-1$
	private static final String STORE_INCLUDE_SUBFOLDERS= "INCLUDE_SUBFOLDERS"; //$NON-NLS-1$
	private static final String STORE_INCLUDE_HIDDEN= "INCLUDE_HIDDEN"; //$NON-NLS-1$
	private static final String STORE_HISTORY= "HISTORY"; //$NON-NLS-1$
	private static final String STORE_HISTORY_SIZE= "HISTORY_SIZE"; //$NON-NLS-1$
	
	// a list to hold previous search data
	private ArrayList<SearchPatternData> fPreviousSearchPatterns = new ArrayList<SearchPatternData>();

	// search utility
//	private SystemSearchUtil fUtil;

	// flag to indicate whether showing the dialog for the first time
	private boolean fFirstTime= true;

	private boolean fIsCaseSensitive;
	private boolean fIsRegExSearch;
	private boolean fIncludeSubfolders;
	private boolean fIncludeHidden;
	private boolean fSearchDerived = false;
	private boolean fIncludeArchives = false;
	
	private ArrayList<String> fFileExtensions = new ArrayList<String>();
	private ArrayList<String> fFolders = new ArrayList<String>();
	
	private Combo fPattern;
	private Button fIsCaseSensitiveCheckbox;

	private Combo fExtensions;
	private Button fIsRegExCheckbox;
	private CLabel fStatusLabel;

	private Combo fFolder;
	private Button fIncludeSubfoldersCheckbox;
	private Button fIncludeHiddenCheckbox;
	private Button fIncludeArchivesCheckbox;
	
	// folder name editor
	private FolderNameEditor folderNameEditor;
	
	// search page fContainer
	private ISearchPageContainer fContainer;

	private FileTypeEditor fFileTypeEditor;

	private ContentAssistCommandAdapter fPatterFieldContentAssist;
    
	private static class SearchPatternData {
		public final boolean isCaseSensitive;
		public final boolean isRegExSearch;
		public final String textPattern;
		public final String[] fileNamePatterns;
		public final int scope;
		public final String folderName;
		public final boolean includeSubfolders;
		public final boolean includeHidden;
		public final boolean includeArchives;
	
		private static final String STORE_DATA_TEXT_PATTERN = "textPattern"; //$NON-NLS-1$
		private static final String STORE_DATA_CASE_SENSITIVE = "caseSensitive"; //$NON-NLS-1$
		private static final String STORE_DATA_STRING_REGEX = "regExSearch"; //$NON-NLS-1$
		private static final String STORE_DATA_FILE_NAMES = "fileNames"; //$NON-NLS-1$
		private static final String STORE_DATA_SCOPE = "scope"; //$NON-NLS-1$
		private static final String STORE_DATA_FOLDER_NAME = "folderName"; //$NON-NLS-1$
		private static final String STORE_DATA_INCLUDE_SUBFOLDERS = "includeSubfolders"; //$NON-NLS-1$
		private static final String STORE_DATA_INCLUDE_HIDDEN = "includeHidden"; //$NON-NLS-1$
		private static final String STORE_DATA_INCLUDE_ARCHIVES = "includeArchives"; //$NON-NLS-1$

		public SearchPatternData(String textPattern, boolean isCaseSensitive, boolean isRegExSearch, String[] fileNamePatterns,
				int scope, String folderName, boolean includeSubfolders, boolean includeHidden, boolean includeArchives) {
			Assert.isNotNull(fileNamePatterns);
			this.isCaseSensitive = isCaseSensitive;
			this.isRegExSearch = isRegExSearch;
			this.textPattern = textPattern;
			this.fileNamePatterns = fileNamePatterns;
			this.scope = scope;
			this.folderName = folderName; // can be null
			this.includeSubfolders = includeSubfolders;
			this.includeHidden = includeHidden;
			this.includeArchives = includeArchives;
		}
		
		public void store(IDialogSettings settings) {
			settings.put(STORE_DATA_CASE_SENSITIVE, isCaseSensitive); //$NON-NLS-1$
			settings.put(STORE_DATA_STRING_REGEX, isRegExSearch); //$NON-NLS-1$
			settings.put(STORE_DATA_TEXT_PATTERN, textPattern); //$NON-NLS-1$
			settings.put(STORE_DATA_FILE_NAMES, fileNamePatterns); //$NON-NLS-1$
			settings.put(STORE_DATA_SCOPE, scope); //$NON-NLS-1$
			settings.put(STORE_DATA_FOLDER_NAME, folderName);
			settings.put(STORE_DATA_INCLUDE_SUBFOLDERS, includeSubfolders);
			settings.put(STORE_DATA_INCLUDE_HIDDEN, includeHidden);
			settings.put(STORE_DATA_INCLUDE_ARCHIVES, includeArchives);
		}
		
		public static SearchPatternData create(IDialogSettings settings) {
			String textPattern = settings.get(STORE_DATA_TEXT_PATTERN); //$NON-NLS-1$
			String[] fileNamePatterns = settings.getArray(STORE_DATA_FILE_NAMES); //$NON-NLS-1$
			if (fileNamePatterns == null) {
				fileNamePatterns= new String[0];
			}
			String folderName = settings.get(STORE_DATA_FOLDER_NAME);
			try {
				int scope= settings.getInt(STORE_DATA_SCOPE); //$NON-NLS-1$
				boolean isRegExSearch = settings.getBoolean(STORE_DATA_STRING_REGEX); //$NON-NLS-1$
				boolean isCaseSensitive = settings.getBoolean(STORE_DATA_CASE_SENSITIVE); //$NON-NLS-1$
				boolean includeSubfolders = settings.getBoolean(STORE_DATA_INCLUDE_SUBFOLDERS); //$NON-NLS-1$
				boolean includeHidden = settings.getBoolean(STORE_DATA_INCLUDE_HIDDEN); //$NON-NLS-1$
				boolean includeArchives = settings.getBoolean(STORE_DATA_INCLUDE_ARCHIVES); //$NON-NLS-1$

				return	new SearchPatternData(textPattern, isCaseSensitive, isRegExSearch, fileNamePatterns, scope,
						folderName, includeSubfolders, includeHidden, includeArchives);
			} catch (NumberFormatException e) {
				return null;
			}
		}
	}
	
	private static class TextSearchPageInput extends TextSearchInput {
		
		private final String fSearchText;
		private final boolean fIsCaseSensitive;
		private final boolean fIsRegEx;
		private final FileTextSearchScope fScope;

		public TextSearchPageInput(String searchText, boolean isCaseSensitive, boolean isRegEx, FileTextSearchScope scope) {
			fSearchText = searchText;
			fIsCaseSensitive = isCaseSensitive;
			fIsRegEx = isRegEx;
			fScope = scope;
		}

		public String getSearchText() {
			return fSearchText;
		}

		public boolean isCaseSensitiveSearch() {
			return fIsCaseSensitive;
		}

		public boolean isRegExSearch() {
			return fIsRegEx;
		}

		public FileTextSearchScope getScope() {
			return fScope;
		}
	}
	
	//---- Action Handling ------------------------------------------------
	
	private ISearchQuery newQuery() throws CoreException {
		SearchPatternData data= getPatternData();
		TextSearchPageInput input= new TextSearchPageInput(data.textPattern, data.isCaseSensitive, data.isRegExSearch,
				createTextSearchScope(data.folderName, data.includeSubfolders, data.includeHidden, data.includeArchives));
		return TextSearchQueryProvider.getPreferred().createQuery(input);
	}
	
	public boolean performAction() {
		try {
			NewSearchUI.runQueryInBackground(newQuery());
		} catch (CoreException e) {
			ErrorDialog.openError(getShell(), SearchMessages.TextSearchPage_replace_searchproblems_title, SearchMessages.TextSearchPage_replace_searchproblems_message, e.getStatus());
			return false;
		}
 		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.search.system.ui.IReplacePage#performReplace()
	 */
	public boolean performReplace() {
		try {
			IStatus status= NewSearchUI.runQueryInForeground(getContainer().getRunnableContext(), newQuery());
			if (status.matches(IStatus.CANCEL)) {
				return false;
			}
			if (!status.isOK()) {
				ErrorDialog.openError(getShell(), SearchMessages.TextSearchPage_replace_searchproblems_title, SearchMessages.TextSearchPage_replace_runproblem_message, status);
			}
				
			Display.getCurrent().asyncExec(new Runnable() {
				public void run() {
					ISearchResultViewPart view= NewSearchUI.activateSearchResultView();
					if (view != null) {
						ISearchResultPage page= view.getActivePage();
						if (page instanceof FileSearchPage) {
							FileSearchPage filePage= (FileSearchPage) page;
							Object[] elements= filePage.getInput().getElements();
							IFileStore[] files= new IFileStore[elements.length];
							System.arraycopy(elements, 0, files, 0, files.length);
							new ReplaceAction2(filePage, files).run();
						}
					}
				}
			});
			return true;
		} catch (CoreException e) {
			ErrorDialog.openError(getShell(), SearchMessages.TextSearchPage_replace_searchproblems_title, SearchMessages.TextSearchPage_replace_querycreationproblem_message, e.getStatus());
			return false;
		}
	}

	private String getPattern() {
		return fPattern.getText();
	}
	
	// system search does not use the regular scope widgets 
	private FileTextSearchScope createTextSearchScope(String folderName, boolean includeSubfolders, boolean includeHidden, boolean includeArchives) {
		return FileTextSearchScope.newSystemScope(folderName, getExtensions(), folderName, includeSubfolders, includeHidden, includeArchives);
	}
	
	private SearchPatternData findInPrevious(String pattern) {
		for (Iterator<SearchPatternData> iter= fPreviousSearchPatterns.iterator(); iter.hasNext();) {
			SearchPatternData element= (SearchPatternData) iter.next();
			if (pattern.equals(element.textPattern)) {
				return element;
			}
		}
		return null;
	}

	/**
	 * Return search pattern data and update previous searches.
	 * An existing entry will be updated.
	 * @return the search pattern data
	 */
	private SearchPatternData getPatternData() {
		SearchPatternData match= findInPrevious(fPattern.getText());
		if (match != null) {
			fPreviousSearchPatterns.remove(match);
		}
		match= new SearchPatternData(
					getPattern(),
					isCaseSensitive(),
					fIsRegExCheckbox.getSelection(),
					getExtensions(),
					com.nokia.carbide.search.system.ui.ISearchPageContainer.SYSTEM_SCOPE,
					fFolder.getText(),
					fIncludeSubfoldersCheckbox.getSelection(),
					fIncludeHiddenCheckbox.getSelection(),
					fIncludeArchivesCheckbox != null ? fIncludeArchivesCheckbox.getSelection() : false);
		fPreviousSearchPatterns.add(0, match);
		return match;
	}

	private String[] getPreviousExtensions() {
		return (String[]) fFileExtensions.toArray(new String[fFileExtensions.size()]);
	}

	private String[] getPreviousSearchPatterns() {
		int size= fPreviousSearchPatterns.size();
		String [] patterns= new String[size];
		for (int i= 0; i < size; i++)
			patterns[i]= ((SearchPatternData) fPreviousSearchPatterns.get(i)).textPattern;
		return patterns;
	}

	private String[] getPreviousFolders() {
		return (String[]) fFolders.toArray(new String[fFolders.size()]);
	}
		
	private String[] getExtensions() {
		return fFileTypeEditor.getFileTypes();
	}

	private boolean isCaseSensitive() {
		return fIsCaseSensitiveCheckbox.getSelection();
	}

	/*
	 * Implements method from IDialogPage
	 */
	public void setVisible(boolean visible) {
		if (visible && fPattern != null) {
			if (fFirstTime) {
				fFirstTime= false;
				// Set item and text here to prevent page from resizing
				fPattern.setItems(getPreviousSearchPatterns());
				
				String[] values = getPreviousExtensions();
				if (values != null)
					fExtensions.setItems(values);
				values = getPreviousFolders();
				if (values != null)
					fFolder.setItems(values);

				if (!initializePatternControl()) {
					fPattern.select(0);
					fExtensions.setText("*"); //$NON-NLS-1$
					fFolder.select(0);
					fIncludeSubfoldersCheckbox.setSelection(true);
					fIncludeSubfolders = true;
					handleWidgetSelected();
				}
			}
			fPattern.setFocus();
		}
		updateOKStatus();
		super.setVisible(visible);
	}
	
	final void updateOKStatus() {
		boolean regexStatus= validateRegex();
		boolean hasFilePattern= fExtensions.getText().length() > 0;
		getContainer().setPerformActionEnabled(isFolderNameValid() && regexStatus && hasFilePattern);
	}

	//---- Widget creation ------------------------------------------------

	public void createControl(Composite parent) {
		
		// compute horizontal and vertical units
		initializeDialogUnits(parent);
		
		// read configuration
		readConfiguration();
		
		// main composite
		Composite result = new Composite(parent, SWT.NONE);
		result.setFont(parent.getFont());
		GridLayout layout = new GridLayout(2, false);
		result.setLayout(layout);
		
		// create controls for search string
		addTextPatternControls(result);
		
		Label separator= new Label(result, SWT.NONE);
		separator.setVisible(false);
		GridData data= new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1);
		data.heightHint= convertHeightInCharsToPixels(1) / 3;
		separator.setLayoutData(data);
		
		// create controls for file names
		addFileNameControls(result);
		
		separator= new Label(result, SWT.NONE);
		separator.setVisible(false);
		data= new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1);
		data.heightHint= convertHeightInCharsToPixels(1) / 3;
		separator.setLayoutData(data);
		
		// create controls for folder name
		addFolderControls(result);

		setControl(result);
		Dialog.applyDialogFont(result);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(result, ISearchHelpContextIds.TEXT_SEARCH_PAGE);
	}
	
	// inner class to handle folder name editing
	private class FolderNameEditor extends SelectionAdapter implements DisposeListener {
		
		// holds info for each item in the folder combo
		private Combo folderCombo;
		private Button browseButton;
		
		/**
		 * Constructor for folder name editor.
		 * @param folderCombo the folder name combo.
		 * @param browseButton the folder browse button.
		 */
		private FolderNameEditor(Combo folderCombo, Button browseButton) {
			this.folderCombo = folderCombo;
			this.browseButton = browseButton;
			
			// add dispose listeners to both the combo and the button
			// we make them null when they are disposed for cleanup to occur
			folderCombo.addDisposeListener(this);
			browseButton.addDisposeListener(this);
			
			// add selection listener to the browse button
			browseButton.addSelectionListener(this);
		}
		
		/**
		 * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
		 */
		public void widgetDisposed(DisposeEvent e) {
			
			if (e.widget == folderCombo) {
				folderCombo = null;
			}
			else if (e.widget == browseButton) {
				browseButton = null;
			}
		}

		/**
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(SelectionEvent e) {
			
			if (e.widget == browseButton) {
				
				// get the shell from the page
				Shell shell = TextSearchPage.this.getShell();
				
				DirectoryDialog folder = new DirectoryDialog(shell);
				String filterPath = fFolder.getText();
				if (filterPath != null)
					folder.setFilterPath(filterPath);
				folder.setMessage(SearchMessages.TextSearchPage_browseToFolder);
				String selectedFilePath = folder.open();
				if (selectedFilePath != null) {
					setFolderText(selectedFilePath);
				}
			}
		}
		
		/**
		 * Sets the folder name combo text. Also sets the profile name, connection name and folder path.
		 * @param profileName the profile name.
		 */
		private void setFolderText(String folderPath) {		
			fFolder.setText(folderPath);
		}
	}

	private boolean validateRegex() {
		if (fIsRegExCheckbox.getSelection()) {
			try {
				Pattern.compile(fPattern.getText());
			} catch (PatternSyntaxException e) {
				String locMessage= e.getLocalizedMessage();
				int i= 0;
				while (i < locMessage.length() && "\n\r".indexOf(locMessage.charAt(i)) == -1) { //$NON-NLS-1$
					i++;
				}
				statusMessage(true, locMessage.substring(0, i)); // only take first line
				return false;
			}
			statusMessage(false, ""); //$NON-NLS-1$
		} else {
			statusMessage(false, SearchMessages.SearchPage_containingText_hint); 
		}
		return true;
	}
	
	/**
	 * Returns whether folder name is valid.
	 * @return <code>true</code> if folder name is not <code>null</code> or empty, <code>false</code> otherwise.
	 */
	private boolean isFolderNameValid() {
		
		String folderName = fFolder.getText();
		
		if (folderName == null || folderName.trim().length() == 0) {
			return false;
		}

		File file = new File(folderName);

		return file.exists() && file.isDirectory();
	}

	private void addTextPatternControls(Composite group) {
		// grid layout with 2 columns

		// Info text		
		Label label = new Label(group, SWT.LEAD);
		label.setText(SearchMessages.TextSearchPage_searchString);
		label.setToolTipText(SearchMessages.TextSearchPage_searchStringToolTip);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		label.setFont(group.getFont());

		// Pattern combo
		fPattern= new Combo(group, SWT.SINGLE | SWT.BORDER);
		fPattern.setToolTipText(SearchMessages.TextSearchPage_enterOrSelectToolTip);
		// Not done here to prevent page from resizing
		// fPattern.setItems(getPreviousSearchPatterns());
		
		fPattern.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleWidgetSelected();
				updateOKStatus();
			}
		});
		// add some listeners for regex syntax checking
		fPattern.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateOKStatus();
			}
		});
		fPattern.setFont(group.getFont());
		GridData data= new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1);
		data.widthHint= convertWidthInCharsToPixels(50);
		fPattern.setLayoutData(data);
		
		ComboContentAdapter contentAdapter= new ComboContentAdapter();
		FindReplaceDocumentAdapterContentProposalProvider findProposer= new FindReplaceDocumentAdapterContentProposalProvider(true);
		fPatterFieldContentAssist= new ContentAssistCommandAdapter(
				fPattern,
				contentAdapter,
				findProposer, 
				ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS,
				new char[] {'\\', '[', '('},
				true);
		fPatterFieldContentAssist.setEnabled(fIsRegExSearch);
		
		fIsCaseSensitiveCheckbox= new Button(group, SWT.CHECK);
		fIsCaseSensitiveCheckbox.setText(SearchMessages.SearchPage_caseSensitive); 
		fIsCaseSensitiveCheckbox.setToolTipText(SearchMessages.TextSearchPage_caseSensitiveToolTip);
		fIsCaseSensitiveCheckbox.setSelection(fIsCaseSensitive);

		fIsCaseSensitiveCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fIsCaseSensitive= fIsCaseSensitiveCheckbox.getSelection();
			}
		});
		fIsCaseSensitiveCheckbox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		fIsCaseSensitiveCheckbox.setFont(group.getFont());

		// Text line which explains the special characters
		fStatusLabel= new CLabel(group, SWT.LEAD);
		fStatusLabel.setToolTipText("");
		fStatusLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fStatusLabel.setFont(group.getFont());
		fStatusLabel.setAlignment(SWT.LEFT);
		fStatusLabel.setText(SearchMessages.SearchPage_containingText_hint); 

		// RegEx checkbox
		fIsRegExCheckbox= new Button(group, SWT.CHECK);
		fIsRegExCheckbox.setText(SearchMessages.SearchPage_regularExpression); 
		fIsRegExCheckbox.setSelection(fIsRegExSearch);

		fIsRegExCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fIsRegExSearch= fIsRegExCheckbox.getSelection();
				updateOKStatus();

				writeConfiguration();
				fPatterFieldContentAssist.setEnabled(fIsRegExSearch);
			}
		});
		fIsRegExCheckbox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		fIsRegExCheckbox.setFont(group.getFont());
	}

	private void handleWidgetSelected() {
		int selectionIndex= fPattern.getSelectionIndex();
		if (selectionIndex < 0 || selectionIndex >= fPreviousSearchPatterns.size())
			return;
		
		SearchPatternData patternData= (SearchPatternData) fPreviousSearchPatterns.get(selectionIndex);
		if (patternData == null || !fPattern.getText().equals(patternData.textPattern))
			return;
		
		// set case sensitive
		fIsCaseSensitiveCheckbox.setSelection(patternData.isCaseSensitive);

		// set string regex
		fIsRegExCheckbox.setSelection(patternData.isRegExSearch);

		// set search string properties
		fPattern.setText(patternData.textPattern);

		// set types list
		fFileTypeEditor.setFileTypes(patternData.fileNamePatterns);

		// set working sets
		getContainer().setSelectedScope(patternData.scope);
		
		// set the system folder properties
		folderNameEditor.setFolderText(patternData.folderName);

		// set search subfolders
		fIncludeSubfoldersCheckbox.setSelection(patternData.includeSubfolders);
		
		// set search hidden files
		fIncludeHiddenCheckbox.setSelection(patternData.includeHidden);
		
		// set search archives
//		fIncludeArchivesCheckbox.setSelection(patternData.includeArchives);
	}

	private boolean initializePatternControl() {
		ISelection selection= getSelection();
		if (selection instanceof ITextSelection && !selection.isEmpty()) {
			String text= ((ITextSelection) selection).getText();
			if (text != null) {
				fPattern.setText(insertEscapeChars(text));
				
				if (getPreviousExtensions().length > 0) {
					fExtensions.setText(getPreviousExtensions()[0]);
				} else {
					String extension= getExtensionFromEditor();
					if (extension != null)
						fExtensions.setText(extension);
					else 
						fExtensions.setText("*"); //$NON-NLS-1$
				}
				
				if (getPreviousFolders().length > 0) {
					fFolder.setText(getPreviousFolders()[0]);
				}
				return true;
			}
		}
		return false;
	}
	
	private String insertEscapeChars(String text) {
		if (text == null || text.equals("")) //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		StringBuffer sbIn= new StringBuffer(text);
		BufferedReader reader= new BufferedReader(new StringReader(text));
		int lengthOfFirstLine= 0;
		try {
			lengthOfFirstLine= reader.readLine().length();
		} catch (IOException ex) {
			return ""; //$NON-NLS-1$
		}
		StringBuffer sbOut= new StringBuffer(lengthOfFirstLine + 5);
		int i= 0;
		while (i < lengthOfFirstLine) {
			char ch= sbIn.charAt(i);
			if (ch == '*' || ch == '?' || ch == '\\')
				sbOut.append("\\"); //$NON-NLS-1$
			sbOut.append(ch);
			i++;
		}
		return sbOut.toString();
	}

	private String getExtensionFromEditor() {
		IEditorPart ep= SearchPlugin.getActivePage().getActiveEditor();
		if (ep != null) {
			Object elem= ep.getEditorInput();
			if (elem instanceof IFileEditorInput) {
				String extension= ((IFileEditorInput)elem).getFile().getFileExtension();
				if (extension == null)
					return ((IFileEditorInput)elem).getFile().getName();
				return "*." + extension; //$NON-NLS-1$
			}  else if (elem instanceof FileStoreEditorInput) {
				FileStoreEditorInput input = (FileStoreEditorInput)elem;
				URI uri = input.getURI();
				String extension = uri.getPath();
				if (extension.contains(".")) { //$NON-NLS-1$
					extension = extension.substring(extension.lastIndexOf('.'));
				} else if (extension.replace('\\','/').contains("/")) { //$NON-NLS-1$
					extension = extension.substring(extension.replace('\\','/').lastIndexOf('/'));
				}
				
			}
		}
		return null;
	}

	private void addFileNameControls(Composite group) {
		// grid layout with 2 columns
		
		// Line with label, combo and button
		Label label= new Label(group, SWT.LEAD);
		label.setText(SearchMessages.SearchPage_fileNamePatterns_text); 
		label.setToolTipText(SearchMessages.TextSearchPage_fileNamePatterns);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		label.setFont(group.getFont());
		
		fExtensions= new Combo(group, SWT.SINGLE | SWT.BORDER);
		fExtensions.setToolTipText(SearchMessages.TextSearchPage_enterOrSelectFileNamePatternsToolTip);
		
		if (getPreviousExtensions().length > 0) {
			fExtensions.setText(getPreviousExtensions()[0]);
		}

		fExtensions.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateOKStatus();
			}
		});
		GridData data= new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1);
		data.widthHint= convertWidthInCharsToPixels(50);
		fExtensions.setLayoutData(data);
		fExtensions.setFont(group.getFont());
		
		Button button= new Button(group, SWT.PUSH);
		button.setText(SearchMessages.SearchPage_browse); 
		GridData gridData= new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1);
		gridData.widthHint= SWTUtil.getButtonWidthHint(button);	
		button.setLayoutData(gridData);
		button.setFont(group.getFont());
		
		fFileTypeEditor= new FileTypeEditor(fExtensions, button);
		
		// Text line which explains the special characters
		Label description= new Label(group, SWT.LEAD);
		description.setText(SearchMessages.SearchPage_fileNamePatterns_hint); 
		description.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		description.setFont(group.getFont());
  	}

	/**
	 * Adds controls to specify folder name.
	 */
	private void addFolderControls(Composite group) {
		// grid layout with 2 columns
		
		// Line with label, combo and button
		Label label = new Label(group, SWT.LEFT);
		label.setText(SearchMessages.TextSearchPage_folder);
		label.setToolTipText(SearchMessages.TextSearchPage_folderPath);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		label.setFont(group.getFont());
		
		fFolder = new Combo(group, SWT.SINGLE | SWT.BORDER);
		fFolder.setToolTipText(SearchMessages.TextSearchPage_folderPathToolTip);
		fFolder.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateOKStatus();
			}
		});
		GridData data= new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1);
		data.widthHint= convertWidthInCharsToPixels(50);
		fFolder.setLayoutData(data);
		fFolder.setFont(group.getFont());
		
		// browse types button
		Button button= new Button(group, SWT.PUSH);
		button.setText(SearchMessages.TextSearchPage_browse);
		button.setToolTipText(SearchMessages.TextSearchPage_browseToolTip);
		GridData gridData= new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1);
		gridData.widthHint= SWTUtil.getButtonWidthHint(button);	
		button.setLayoutData(gridData);
		button.setFont(group.getFont());
		
		// create the folder name editor
		folderNameEditor = new FolderNameEditor(fFolder, button);
		
		// line up the checkbox
		new Label(group, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
		
		// checkbox for search in subfolders
		fIncludeSubfoldersCheckbox = new Button(group, SWT.CHECK);
		fIncludeSubfoldersCheckbox.setText(SearchMessages.TextSearchPage_searchSubfolders);
		fIncludeSubfoldersCheckbox.setToolTipText(SearchMessages.TextSearchPage_searchSubfoldersToolTip);
		fIncludeSubfoldersCheckbox.setSelection(fIncludeSubfolders);
		fIncludeSubfoldersCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fIncludeSubfolders= fIncludeSubfoldersCheckbox.getSelection();
				updateOKStatus();
			}
		});
		fIncludeSubfoldersCheckbox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		fIncludeSubfoldersCheckbox.setFont(group.getFont());
		
		// line up the checkbox
		new Label(group, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
		
		// checkbox for search in subfolders
		fIncludeHiddenCheckbox = new Button(group, SWT.CHECK);
		fIncludeHiddenCheckbox.setText(SearchMessages.TextSearchPage_hiddenFiles);
		fIncludeHiddenCheckbox.setToolTipText(SearchMessages.TextSearchPage_hiddenFilesToolTip);
		fIncludeHiddenCheckbox.setSelection(fIncludeHidden);
		fIncludeHiddenCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fIncludeHidden= fIncludeHiddenCheckbox.getSelection();
				updateOKStatus();
			}
		});
		fIncludeHiddenCheckbox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		fIncludeHiddenCheckbox.setFont(group.getFont());
	}

	/**
	 * Sets the search page's container.
	 * @param container the container to set
	 */
	public void setContainer(ISearchPageContainer container) {
		fContainer= container;
	}
	
	private ISearchPageContainer getContainer() {
		return fContainer;
	}
	
	private ISelection getSelection() {
		return fContainer.getSelection();
	}
		

	//--------------- Configuration handling --------------
	
    /* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	public void dispose() {
		writeConfiguration();
		super.dispose();
	}
	
	/**
	 * Returns the page settings for this Text search page.
	 * 
	 * @return the page settings to be used
	 */
	private IDialogSettings getDialogSettings() {
		return SearchPlugin.getDefault().getDialogSettingsSection(PAGE_NAME);
	}
		
	
	/**
	 * Initializes itself from the stored page settings.
	 */
	private void readConfiguration() {
		IDialogSettings s= getDialogSettings();
		fIsCaseSensitive=   s.getBoolean(STORE_CASE_SENSITIVE);
		fIsRegExSearch=     s.getBoolean(STORE_IS_REG_EX_SEARCH);
		fSearchDerived=     s.getBoolean(STORE_SEARCH_DERIVED);
		fIncludeSubfolders= s.getBoolean(STORE_INCLUDE_SUBFOLDERS);
		fIncludeHidden=     s.getBoolean(STORE_INCLUDE_HIDDEN);
		
		String[] values;
		
		values = s.getArray(STORE_ADDED_FILENAMES);
		fFileExtensions.clear();
		for (int i = 0; values != null && i < values.length; i++)
			fFileExtensions.add(values[i]);

		values = s.getArray(STORE_ADDED_FOLDERS);
		fFolders.clear();
		for (int i = 0; values != null && i < values.length; i++)
			fFolders.add(values[i]);

		try {
			int historySize= s.getInt(STORE_HISTORY_SIZE);
			for (int i= 0; i < historySize; i++) {
				IDialogSettings histSettings= s.getSection(STORE_HISTORY + i);
				if (histSettings != null) {
					SearchPatternData data= SearchPatternData.create(histSettings);
					if (data != null) {
						fPreviousSearchPatterns.add(data);
					}
				}
			}
		} catch (NumberFormatException e) {
			// ignore
		}
	}
	
	/**
	 * Stores the current configuration in the dialog store.
	 */
	private void writeConfiguration() {
		IDialogSettings s= getDialogSettings();
		s.put(STORE_CASE_SENSITIVE, fIsCaseSensitive);
		s.put(STORE_IS_REG_EX_SEARCH, fIsRegExSearch);
		s.put(STORE_SEARCH_DERIVED, fSearchDerived);
		s.put(STORE_INCLUDE_SUBFOLDERS, fIncludeSubfolders);
		s.put(STORE_INCLUDE_HIDDEN, fIncludeHidden);
		
		boolean matchFound;

		// add the current folder to the start of the list;
		// if it's in the list twice, remove the second time 
		String folder = fFolder.getText();
		fFolder.add(folder, 0);
		
		for (int i = 1; i < fFolder.getItemCount(); i++) {
			if (fFolder.getItem(i).equals(folder)) {
				fFolder.remove(i);
				break;
			}
		}

		// if there are too many members, remove the highest indexed element
		// not associated with a search pattern
		if (fFolder.getItemCount() > HISTORY_SIZE + 2) {
			matchFound = true;
			int size= fPreviousSearchPatterns.size();
			for (int j = fFolder.getItemCount() - 1; j > 0 && matchFound; j--) {
				matchFound = false;
				String currentFolder = fFolder.getItem(j);
				for (int i= 0; i < size && !matchFound; i++) {
					SearchPatternData data= (SearchPatternData) fPreviousSearchPatterns.get(i);
					matchFound = currentFolder.equals(data.folderName);
				}
				
				if (matchFound) {
					fFolder.remove(j);
					break;
				}
			}
		}

		s.put(STORE_ADDED_FOLDERS, fFolder.getItems());
		
		// add the current file extension to the start of the list;
		// if it's in the list twice, remove the second time
		String fileExtensions = fFileTypeEditor.getFileTypesString();
		fFileExtensions.add(0, fileExtensions);

		for (int i = 1; i < fFileExtensions.size(); i++) {
			if (fileExtensions.equals(fFileExtensions.get(i))) { 
				fFileExtensions.remove(i);
				break;
			}
		}

		// if there are too many members, remove the highest indexed element
		// not associated with a search pattern
		if (fFileExtensions.size() >= HISTORY_SIZE + 2) {
			matchFound = true;
			int size= fPreviousSearchPatterns.size();
			for (int j = fFileExtensions.size() - 1; j > 0 && matchFound; j--) {
				matchFound = false;
				String currentExtension = fFileExtensions.get(j);
				for (int i= 0; i < size && !matchFound; i++) {
					SearchPatternData data= (SearchPatternData) fPreviousSearchPatterns.get(i);
					matchFound = currentExtension.equals(FileTypeEditor.typesToString(data.fileNamePatterns));
				}
				
				if (matchFound) {
					fFileExtensions.remove(j);
					break;
				}
			}
		}

		String [] extensions = new String[fFileExtensions.size()];
		for (int i = 0; i < fFileExtensions.size(); i++)
			extensions[i] = fFileExtensions.get(i);
		s.put(STORE_ADDED_FILENAMES, extensions);
		
		int historySize= Math.min(fPreviousSearchPatterns.size(), HISTORY_SIZE);
		s.put(STORE_HISTORY_SIZE, historySize);
		for (int i= 0; i < historySize; i++) {
			IDialogSettings histSettings= s.addNewSection(STORE_HISTORY + i);
			SearchPatternData data= ((SearchPatternData) fPreviousSearchPatterns.get(i));
			data.store(histSettings);
		}
	}

	private void statusMessage(boolean error, String message) {
		fStatusLabel.setText(message);
		if (error)
			fStatusLabel.setForeground(JFaceColors.getErrorText(fStatusLabel.getDisplay()));
		else
			fStatusLabel.setForeground(null);
	}

}	
