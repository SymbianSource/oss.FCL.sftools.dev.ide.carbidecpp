/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.nokia.carbide.search.system.internal.ui;

import org.eclipse.osgi.util.NLS;

public final class SearchMessages extends NLS {

	private static final String BUNDLE_NAME= "com.nokia.carbide.search.system.internal.ui.SearchMessages";//$NON-NLS-1$


	private SearchMessages() {
		// Do not instantiate
	}

	public static String FileSearchPage_dateFormat;
	public static String FileSearchPage_dateModified;
	public static String FileSearchPage_name;
	public static String FileSearchPage_Path;
	public static String FileSearchPage_size;
	public static String FileSearchPage_type;
	public static String FileTextSearchScope_scope_empty;
	public static String FileTextSearchScope_scope_single;
	public static String FileTextSearchScope_scope_double;
	public static String FileTextSearchScope_scope_multiple;
	
	public static String FileTextSearchScope_ws_scope_empty;
	public static String FileTextSearchScope_ws_scope_single;
	public static String FileTextSearchScope_ws_scope_double;
	public static String FileTextSearchScope_ws_scope_multiple;
	public static String NewTextSearchActionGroup_defaultEditor;
	public static String NewTextSearchActionGroup_open;
	public static String NewTextSearchActionGroup_openToolTip;

	public static String SearchDialog_title;
	public static String SearchDialog_searchAction;
	public static String SearchDialog_replaceAction;
	public static String SearchDialog_customize;
	public static String SearchDialog_noSearchExtension;
	public static String SearchPageSelectionDialog_title;
	public static String SearchPageSelectionDialog_message;
	public static String SearchDialogClosingDialog_title;
	public static String SearchDialogClosingDialog_message;
	public static String SearchDialog_error_pageCreationFailed;
	public static String SearchPlugin_internal_error;
	public static String Search_Error_setDescription_title;
	public static String Search_Error_setDescription_message;
	public static String Search_Error_createSearchPage_title;
	public static String Search_Error_createSearchPage_message;
	public static String Search_Error_createSorter_title;
	public static String Search_Error_createSorter_message;
	public static String SearchPage_containingText_hint;
	public static String SearchPage_browse;
	public static String SearchPage_fileNamePatterns_text;
	public static String SearchPage_fileNamePatterns_hint;
	public static String SearchPage_caseSensitive;
	public static String SearchPage_regularExpression;
	public static String TextSearchEngine_statusMessage;
	public static String TextSearchPage_browse;
	public static String TextSearchPage_browseToFolder;
	public static String TextSearchPage_browseToolTip;
	public static String TextSearchPage_caseSensitiveToolTip;
	public static String TextSearchPage_enterOrSelectFileNamePatternsToolTip;
	public static String TextSearchPage_enterOrSelectToolTip;
	public static String TextSearchPage_fileNamePatterns;
	public static String TextSearchPage_folder;
	public static String TextSearchPage_folderPath;
	public static String TextSearchPage_folderPathToolTip;
	public static String TextSearchPage_hiddenFiles;
	public static String TextSearchPage_hiddenFilesToolTip;
	public static String TextSearchPage_replace_querycreationproblem_message;
	public static String TextSearchPage_replace_runproblem_message;
	public static String TextSearchPage_searchString;
	public static String TextSearchPage_searchStringToolTip;
	public static String TextSearchPage_searchSubfolders;
	public static String TextSearchPage_searchSubfoldersToolTip;
	public static String TextSearchPage_specialCharsToolTip;
	public static String TextSearchVisitor_filesearch_task_label;
	public static String TextSearchVisitor_patterntoocomplex0;
	public static String TextSearchVisitor_progress_updating_job;
	public static String TextSearchVisitor_scanning;
	public static String TextSearchVisitor_error;
	public static String TextSearchVisitor_canceled;
	public static String TextSearchVisitor_textsearch_task_label;
	public static String TextSearchVisitor_unsupportedcharset;
	public static String TextSearchVisitor_illegalcharset;
	public static String TextSearchPage_replace_searchproblems_title;
	public static String TextSearchPage_replace_searchproblems_message;
	public static String FileSearchQuery_label;
	public static String FileSearchQuery_pluralPattern;
	public static String FileSearchQuery_singularLabel;
	public static String FileSearchQuery_singularLabel_fileNameSearch;
	public static String FileSearchQuery_pluralPattern_fileNameSearch;
	public static String OpenSearchDialogAction_label;
	public static String OpenSearchDialogAction_tooltip;
	public static String FileTypeEditor_typeDelimiter;
	public static String FileLabelProvider_count_format;
	public static String FileLabelProvider_removed_resource_label;
	public static String FileSearchPage_sort_name_label;
	public static String FileSearchPage_sort_path_label;
	public static String FileSearchPage_error_marker;
	public static String FileSearchPage_limited_format;
	public static String WorkspaceScope;
	public static String ScopePart_group_text;
	public static String ScopePart_selectedResourcesScope_text;
	public static String ScopePart_enclosingProjectsScope_text;
	public static String ScopePart_workingSetChooseButton_text;
	public static String ScopePart_workingSetText_accessible_label;
	public static String ScopePart_workingSetScope_text;
	public static String ScopePart_workspaceScope_text;
	public static String ScopePart_workingSetConcatenation;
	public static String CopyToClipboardAction_label;
	public static String CopyToClipboardAction_tooltip;
	public static String CopyToClipboardAction_error_title;
	public static String CopyToClipboardAction_error_message;
	public static String ExceptionDialog_seeErrorLogMessage;
	public static String SearchPreferencePage_emphasizePotentialMatches;
	public static String SearchPreferencePage_potentialMatchFgColor;
	public static String SearchPreferencePage_reuseEditor;
	public static String SearchPreferencePage_defaultPerspective;
	public static String SearchPreferencePage_defaultPerspective_none;
	public static String SearchPreferencePage_ignorePotentialMatches;
	public static String ReplaceAction_label_all;
	public static String ReplaceAction_label_selected;
	public static String ReplaceAction2_error_validate_title;
	public static String ReplaceAction2_error_validate_message;
	public static String ReplaceDialog_replace_label;
	public static String ReplaceDialog_with_label;
	public static String ReplaceDialog_replace;
	public static String ReplaceDialog_replaceAllInFile;
	public static String ReplaceDialog_replaceAll;
	public static String ReplaceDialog_skip;
	public static String ReplaceDialog2_regexError_format;
	public static String ReplaceDialog_skipFile;
	public static String ReplaceDialog_dialog_title;
	public static String ReplaceDialog_error_unable_to_open_text_editor;
	public static String ReplaceDialog_error_unable_to_replace;
	public static String SelectAllAction_label;
	public static String SelectAllAction_tooltip;
	public static String OpenWithMenu_label;
	public static String ReadOnlyDialog_skipFile;
	public static String ReadOnlyDialog_skipAll;
	public static String ReadOnlyDialog_message;
	public static String ReplaceDialog_task_replace;
	public static String ReplaceDialog_task_replaceInFile;
	public static String ReplaceDialog_task_replace_replaceAll;
	public static String ReplaceDialog2_error_disableAutobuild;
	public static String ReplaceDialog2_error_restoreAutobuild;
	public static String ReplaceAction_label;
	public static String ReplaceAction_research_error;
	public static String ReplaceAction2_statusMessage;
	public static String SearchAgainConfirmationDialog_outofsync_message;
	public static String SearchAgainConfirmationDialog_outofsync_label;
	public static String SearchAgainConfirmationDialog_stale_message;
	public static String SearchAgainConfirmationDialog_stale_label;
	public static String SearchAgainConfirmationDialog_title;
	public static String ReplaceDialog_isRegex_label;

	static {
		NLS.initializeMessages(BUNDLE_NAME, SearchMessages.class);
	}

	public static String ReplaceDialog2_nomatches_error;
    public static String SearchPreferencePage_textSearchEngine;
	public static String TextSearchEngineRegistry_defaulttextsearch_label;
	public static String FileSearchQuery_singularPatternWithFileExt;
	public static String FileSearchQuery_pluralPatternWithFileExt;
}
