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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.CMacroEntry;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICIncludePathEntry;
import org.eclipse.cdt.core.settings.model.ICLanguageSettingEntry;
import org.eclipse.cdt.core.settings.model.ICMacroEntry;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.extension.CConfigurationData;
import org.eclipse.cdt.core.settings.model.extension.CFolderData;
import org.eclipse.cdt.core.settings.model.extension.CLanguageData;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Tree;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.DefaultIncludeFileLocator;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.internal.builder.CarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefaultModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefaultTranslationUnitProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.MacroScanner;

/**
 * Paths and Symbols tab for the Carbide Build Configurations page
 *
 */
public class PathsAndSymbolsTabComposite extends Composite {

	private static TreeViewer treeViewer;
	private ICarbideBuildConfiguration buildConfig;

	private class IncludePathsContainer {
		
		private CLanguageData languageData;

		IncludePathsContainer(CLanguageData languageData) {
			this.languageData = languageData;
		}
		
		@Override
		public String toString() {
			return Messages.getString("PathsAndSymbolsTab.IncludesContainerName"); //$NON-NLS-1$;
		}
		
		Object[] getChildren() {
			return languageData.getEntries(ICLanguageSettingEntry.INCLUDE_PATH);
		}
		
		Object getParent() {
			return languageData;
		}
	}
	
	private class SymbolsContainer {
		private CLanguageData languageData;

		SymbolsContainer(CLanguageData languageData) {
			this.languageData = languageData;
		}
		
		@Override
		public String toString() {
			return Messages.getString("PathsAndSymbolsTab.SymbolsContainerName"); //$NON-NLS-1$;
		}
		
		Object[] getChildren() {
			List<ICLanguageSettingEntry> macroEntries = new ArrayList<ICLanguageSettingEntry>();
			macroEntries.addAll(Arrays.asList(languageData.getEntries(ICLanguageSettingEntry.MACRO)));
			Collections.sort(macroEntries, new Comparator<ICLanguageSettingEntry>() {

				public int compare(ICLanguageSettingEntry arg0,	ICLanguageSettingEntry arg1) {
					return arg0.getName().compareTo(arg1.getName());
				}
				
			});

			// add the macros from the macro file if any
			CarbideProjectInfo cpi = (CarbideProjectInfo)buildConfig.getCarbideProject();
			String macrosFile = cpi.getMacrosFile();
			if (macrosFile != null && macrosFile.length() > 0) {
				MacroScanner scanner = new MacroScanner(
						new DefaultIncludeFileLocator(buildConfig.getCarbideProject().getProject(), buildConfig.getBuildContext()),
						DefaultModelDocumentProvider.getInstance(), 
						DefaultTranslationUnitProvider.getInstance());
				scanner.scanFile(new File(macrosFile));
				for (IDefine define : scanner.getMacroDefinitions()) {
					macroEntries.add(new CMacroEntry(define.getNameAndArguments(), define.getExpansion(), 0));
				}
			}

			return macroEntries.toArray();
		}

		Object getParent() {
			return languageData;
		}
	}

	private class PathsAndSymbolsContentProvider implements ITreeContentProvider {

		private IncludePathsContainer includesContainer;
		private SymbolsContainer symbolsContainer;
		
		public Object[] getChildren(Object parentElement) {
			
			if (parentElement instanceof CLanguageData) {
				return new Object[] {includesContainer, symbolsContainer};
			} else if (parentElement instanceof IncludePathsContainer) {
				return ((IncludePathsContainer)parentElement).getChildren();
			} else if (parentElement instanceof SymbolsContainer) {
				return ((SymbolsContainer)parentElement).getChildren();
			}
			return null;
		}

		public Object getParent(Object element) {
			if (element instanceof IncludePathsContainer) {
				return ((IncludePathsContainer)element).getParent();
			} else if (element instanceof SymbolsContainer) {
				return ((SymbolsContainer)element).getParent();
			} else if (element instanceof ICIncludePathEntry) {
				return includesContainer;
			} else if (element instanceof ICMacroEntry) {
				return symbolsContainer;
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			Object[] children = getChildren(element);
			if (children != null && children.length > 0) {
				return true;
			}
			return false;
		}

		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if (newInput instanceof CLanguageData) {
				CLanguageData languageData = (CLanguageData)newInput;
				includesContainer = new IncludePathsContainer(languageData);
				symbolsContainer = new SymbolsContainer(languageData);
			}
		}
	}
	
	private class PathsAndSymbolsLabelProvider extends LabelProvider {
		
		Image includePathsContainerImage = CarbideBuilderPlugin.getImageDescriptor("/icons/wsp_includefolder.gif").createImage();
		Image symbolsContainerImage = CarbideBuilderPlugin.getImageDescriptor("/icons/define_obj.gif").createImage();
		Image includePathEntryImage = CarbideBuilderPlugin.getImageDescriptor("/icons/includes_container.gif").createImage();

		@Override
		public Image getImage(Object element) {
			if (element instanceof IncludePathsContainer) {
				return includePathsContainerImage;
			} else if (element instanceof ICIncludePathEntry) {
				return includePathEntryImage;
			} else if (element instanceof SymbolsContainer || element instanceof ICMacroEntry) {
				return symbolsContainerImage;
			}
			return super.getImage(element);
		}

		@Override
		public String getText(Object element) {
			if (element instanceof ICIncludePathEntry) {
				ICIncludePathEntry incEntry = (ICIncludePathEntry)element;
				String text = incEntry.getLocation().toOSString();
				if (!incEntry.isLocal()) {
					text = text + " [System]"; //$NON-NLS-1$;
				}
				return text;
			} else if (element instanceof ICMacroEntry) {
				ICMacroEntry macroEntry = (ICMacroEntry)element;
				String text = macroEntry.getName();
				String value = macroEntry.getValue();
				if (value.length() > 0) {
					text = text + " = " + value; //$NON-NLS-1$;
				}

				return text;
			}
			return super.getText(element);
		}
		
	}
	
	
	public PathsAndSymbolsTabComposite(TabItem tabItem) {
		super(tabItem.getParent(), SWT.NONE);
	}

	public void createControls() {
		setLayout(new GridLayout());

		Label bldMakeBldfilesLabel = new Label(this, SWT.NONE);
		bldMakeBldfilesLabel.setText(Messages.getString("PathsAndSymbolsTab.DescriptionLabel")); //$NON-NLS-1$
		bldMakeBldfilesLabel.setToolTipText(Messages.getString("PathsAndSymbolsTab.DescriptionToolTip")); //$NON-NLS-1$

		treeViewer = new TreeViewer(this);
		treeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		treeViewer.setLabelProvider(new PathsAndSymbolsLabelProvider());
		treeViewer.setContentProvider(new PathsAndSymbolsContentProvider());

		Tree tree = treeViewer.getTree();
		tree.setToolTipText(Messages.getString("PathsAndSymbolsTab.DescriptionToolTip")); //$NON-NLS-1$
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}
	
	public void initData(ICarbideBuildConfiguration buildConfig) {
		this.buildConfig = buildConfig;
		
		ICProjectDescription projDes = CoreModel.getDefault().getProjectDescription(buildConfig.getCarbideProject().getProject());
		if (projDes != null) {
			ICConfigurationDescription configDes = projDes.getConfigurationById(buildConfig.getBuildContext().getConfigurationID());
			if (configDes != null) {
				CConfigurationData configData = configDes.getConfigurationData();
				if (configData != null) {
					CFolderData rootFolderData = configData.getRootFolderData();
					if (rootFolderData != null) {
						CLanguageData[] languageDatas = rootFolderData.getLanguageDatas();
						if (languageDatas != null && languageDatas.length > 0) {
							// there's only one for Carbide anyway
							CLanguageData languageData = languageDatas[0];
							treeViewer.setInput(languageData);
						}
					}
				}
			}
		}
	}
	
	public boolean compareConfigurationSettings(ICarbideBuildConfiguration selectedConfig, boolean writeToConfig) {
		// read only tab so no settings will ever change
		return true;
	}
	
	public void performDefaults(ISymbianSDK sdk) {
	}
	
	public static void refresh() {
		if (treeViewer != null) {
			treeViewer.refresh();
		}
	}
}
