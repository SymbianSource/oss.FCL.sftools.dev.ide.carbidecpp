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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.internal.builder.CarbideBuildConfiguration;
import com.nokia.carbide.cpp.internal.api.sdk.BuildArgumentsInfo;
import com.nokia.carbide.cpp.internal.api.sdk.IBuildArgumentsInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;

/**
 * Arguments tab for the Carbide Build Configurations page
 *
 */
public class ArgumentsTabComposite extends Composite {

	private Text bldMakeBldfilesEdit;
	private Text bldMakeCleanEdit;
	private Text abldBuildEdit;
	private Text abldExportEdit;
	private Text abldMakefileEdit;
	private Text abldLibraryEdit;
	private Text abldResourceEdit;
	private Text abldTargetEdit;
	private Text abldFinalEdit;
	private Text abldCleanEdit;
	private Text abldFreezeEdit;

	
	public ArgumentsTabComposite(TabItem tabItem) {
		super(tabItem.getParent(), SWT.NONE);
	}

	public void createControls() {
		setLayout(new GridLayout());

		Group bldmakeGroup = new Group(this, SWT.NONE);
		bldmakeGroup.setText(Messages.getString("ArgumentsTab.BldmakeArguments")); //$NON-NLS-1$
		bldmakeGroup.setToolTipText(Messages.getString("ArgumentsTab.BldmakeArgumentsToolTip")); //$NON-NLS-1$
		bldmakeGroup.setLayout(new GridLayout(2, false));
		bldmakeGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label bldMakeBldfilesLabel = new Label(bldmakeGroup, SWT.NONE);
		bldMakeBldfilesLabel.setText(Messages.getString("ArgumentsTab.BldfilesArguments")); //$NON-NLS-1$
		bldMakeBldfilesLabel.setToolTipText(Messages.getString("ArgumentsTab.BldfilesArgumentsToolTip")); //$NON-NLS-1$

		bldMakeBldfilesEdit = new Text(bldmakeGroup, SWT.BORDER);
		bldMakeBldfilesEdit.setToolTipText(Messages.getString("ArgumentsTab.BldfilesArgumentsToolTip")); //$NON-NLS-1$
		bldMakeBldfilesEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label bldMakeCleanLabel = new Label(bldmakeGroup, SWT.NONE);
		bldMakeCleanLabel.setText(Messages.getString("ArgumentsTab.BldmakeCleanArguments")); //$NON-NLS-1$
		bldMakeCleanLabel.setToolTipText(Messages.getString("ArgumentsTab.BldmakeCleanArgumentsToolTip")); //$NON-NLS-1$

		bldMakeCleanEdit = new Text(bldmakeGroup, SWT.BORDER);
		bldMakeCleanEdit.setToolTipText(Messages.getString("ArgumentsTab.BldmakeCleanArgumentsToolTip")); //$NON-NLS-1$
		bldMakeCleanEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Group abldGroup = new Group(this, SWT.NONE);
		abldGroup.setText(Messages.getString("ArgumentsTab.AbldArguments")); //$NON-NLS-1$
		abldGroup.setToolTipText(Messages.getString("ArgumentsTab.AbldArgumentsToolTip")); //$NON-NLS-1$
		abldGroup.setLayout(new GridLayout(2, false));
		abldGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label abldBuildLabel = new Label(abldGroup, SWT.NONE);
		abldBuildLabel.setText(Messages.getString("ArgumentsTab.BuildArguments")); //$NON-NLS-1$
		abldBuildLabel.setToolTipText(Messages.getString("ArgumentsTab.BuildArgumentsToolTip")); //$NON-NLS-1$

		abldBuildEdit = new Text(abldGroup, SWT.BORDER);
		abldBuildEdit.setToolTipText(Messages.getString("ArgumentsTab.BuildArgumentsToolTip")); //$NON-NLS-1$
		abldBuildEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label abldExportLabel = new Label(abldGroup, SWT.NONE);
		abldExportLabel.setText(Messages.getString("ArgumentsTab.ExportArguments")); //$NON-NLS-1$
		abldExportLabel.setToolTipText(Messages.getString("ArgumentsTab.ExportArgumentsToolTip")); //$NON-NLS-1$

		abldExportEdit = new Text(abldGroup, SWT.BORDER);
		abldExportEdit.setToolTipText(Messages.getString("ArgumentsTab.ExportArgumentsToolTip")); //$NON-NLS-1$
		abldExportEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label abldMakefileLabel = new Label(abldGroup, SWT.NONE);
		abldMakefileLabel.setText(Messages.getString("ArgumentsTab.MakefileArguments")); //$NON-NLS-1$
		abldMakefileLabel.setToolTipText(Messages.getString("ArgumentsTab.MakefileArgumentsToolTip")); //$NON-NLS-1$

		abldMakefileEdit = new Text(abldGroup, SWT.BORDER);
		abldMakefileEdit.setToolTipText(Messages.getString("ArgumentsTab.MakefileArgumentsToolTip")); //$NON-NLS-1$
		abldMakefileEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label abldLibraryLabel = new Label(abldGroup, SWT.NONE);
		abldLibraryLabel.setText(Messages.getString("ArgumentsTab.LibraryArguments")); //$NON-NLS-1$
		abldLibraryLabel.setToolTipText(Messages.getString("ArgumentsTab.LibraryArgumentsToolTip")); //$NON-NLS-1$

		abldLibraryEdit = new Text(abldGroup, SWT.BORDER);
		abldLibraryEdit.setToolTipText(Messages.getString("ArgumentsTab.LibraryArgumentsToolTip")); //$NON-NLS-1$
		abldLibraryEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label abldResourceLabel = new Label(abldGroup, SWT.NONE);
		abldResourceLabel.setText(Messages.getString("ArgumentsTab.ResourceArguments")); //$NON-NLS-1$
		abldResourceLabel.setToolTipText(Messages.getString("ArgumentsTab.ResourceArgumentsToolTip")); //$NON-NLS-1$

		abldResourceEdit = new Text(abldGroup, SWT.BORDER);
		abldResourceEdit.setToolTipText(Messages.getString("ArgumentsTab.ResourceArgumentsToolTip")); //$NON-NLS-1$
		abldResourceEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label abldTargetLabel = new Label(abldGroup, SWT.NONE);
		abldTargetLabel.setText(Messages.getString("ArgumentsTab.TargetArguments")); //$NON-NLS-1$
		abldTargetLabel.setToolTipText(Messages.getString("ArgumentsTab.TargetArgumentsToolTip")); //$NON-NLS-1$

		abldTargetEdit = new Text(abldGroup, SWT.BORDER);
		abldTargetEdit.setToolTipText(Messages.getString("ArgumentsTab.TargetArgumentsToolTip")); //$NON-NLS-1$
		abldTargetEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label abldFinalLabel = new Label(abldGroup, SWT.NONE);
		abldFinalLabel.setText(Messages.getString("ArgumentsTab.FinalArguments")); //$NON-NLS-1$
		abldFinalLabel.setToolTipText(Messages.getString("ArgumentsTab.FinalArgumentsToolTip")); //$NON-NLS-1$

		abldFinalEdit = new Text(abldGroup, SWT.BORDER);
		abldFinalEdit.setToolTipText(Messages.getString("ArgumentsTab.FinalArgumentsToolTip")); //$NON-NLS-1$
		abldFinalEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label abldCleanLabel = new Label(abldGroup, SWT.NONE);
		abldCleanLabel.setText(Messages.getString("ArgumentsTab.AbldCleanArguments")); //$NON-NLS-1$
		abldCleanLabel.setToolTipText(Messages.getString("ArgumentsTab.AbldCleanArgumentsToolTip")); //$NON-NLS-1$

		abldCleanEdit = new Text(abldGroup, SWT.BORDER);
		abldCleanEdit.setToolTipText(Messages.getString("ArgumentsTab.AbldCleanArgumentsToolTip")); //$NON-NLS-1$
		abldCleanEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label abldFreezeLabel = new Label(abldGroup, SWT.NONE);
		abldFreezeLabel.setText(Messages.getString("ArgumentsTab.FreezeArguments")); //$NON-NLS-1$
		abldFreezeLabel.setToolTipText(Messages.getString("ArgumentsTab.FreezeArgumentsToolTip")); //$NON-NLS-1$

		abldFreezeEdit = new Text(abldGroup, SWT.BORDER);
		abldFreezeEdit.setToolTipText(Messages.getString("ArgumentsTab.FreezeArgumentsToolTip")); //$NON-NLS-1$
		abldFreezeEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}
	
	public void initData(ICarbideBuildConfiguration buildConfig) {
		ISBSv1BuildContext sbsv1Context = (ISBSv1BuildContext)buildConfig.getBuildContext();
		IBuildArgumentsInfo argsInfo = sbsv1Context.getBuildArgumentsInfo();
		bldMakeBldfilesEdit.setText(argsInfo.getBldmakeBldFilesArgs());
		bldMakeCleanEdit.setText(argsInfo.getBldmakeCleanArgs());
		abldBuildEdit.setText(argsInfo.getAbldBuildArgs());
		abldExportEdit.setText(argsInfo.getAbldExportArgs());
		abldMakefileEdit.setText(argsInfo.getAbldMakefileArgs());
		abldLibraryEdit.setText(argsInfo.getAbldLibraryArgs());
		abldResourceEdit.setText(argsInfo.getAbldResourceArgs());
		abldTargetEdit.setText(argsInfo.getAbldTargetArgs());
		abldFinalEdit.setText(argsInfo.getAbldFinalArgs());
		abldCleanEdit.setText(argsInfo.getAbldCleanArgs());
		abldFreezeEdit.setText(argsInfo.getAbldFreezeArgs());
	}
	
	public boolean compareConfigurationSettings(ICarbideBuildConfiguration selectedConfig, boolean writeToConfig) {
		boolean settingsEqual = true;
		
		ISBSv1BuildContext sbsv1Context = (ISBSv1BuildContext)selectedConfig.getBuildContext();
		IBuildArgumentsInfo existingInfo = sbsv1Context.getBuildArgumentsInfo();
		settingsEqual = existingInfo.getBldmakeBldFilesArgs().equals(bldMakeBldfilesEdit.getText()) &&
		existingInfo.getBldmakeCleanArgs().equals(bldMakeCleanEdit.getText()) &&
		existingInfo.getAbldBuildArgs().equals(abldBuildEdit.getText()) &&
		existingInfo.getAbldExportArgs().equals(abldExportEdit.getText()) &&
		existingInfo.getAbldMakefileArgs().equals(abldMakefileEdit.getText()) &&
		existingInfo.getAbldLibraryArgs().equals(abldLibraryEdit.getText()) &&
		existingInfo.getAbldResourceArgs().equals(abldResourceEdit.getText()) &&
		existingInfo.getAbldTargetArgs().equals(abldTargetEdit.getText()) &&
		existingInfo.getAbldFinalArgs().equals(abldFinalEdit.getText()) &&
		existingInfo.getAbldCleanArgs().equals(abldCleanEdit.getText()) &&
		existingInfo.getAbldFreezeArgs().equals(abldFreezeEdit.getText());
		
		if (!settingsEqual && writeToConfig) {
			sbsv1Context.setBuildArgumentsInfo(new BuildArgumentsInfo(bldMakeBldfilesEdit.getText(), bldMakeCleanEdit.getText(),
					abldBuildEdit.getText(), abldExportEdit.getText(), abldMakefileEdit.getText(), abldLibraryEdit.getText(), abldResourceEdit.getText(),
					abldTargetEdit.getText(), abldFinalEdit.getText(), abldCleanEdit.getText(), abldFreezeEdit.getText()));
		}
		
		return settingsEqual;
	}
	
	public void performDefaults(ISymbianSDK sdk) {
		bldMakeBldfilesEdit.setText(""); //$NON-NLS-1$
		bldMakeCleanEdit.setText(""); //$NON-NLS-1$
		abldBuildEdit.setText(""); //$NON-NLS-1$
		abldExportEdit.setText(""); //$NON-NLS-1$
		abldMakefileEdit.setText(""); //$NON-NLS-1$
		abldLibraryEdit.setText(""); //$NON-NLS-1$
		abldResourceEdit.setText(""); //$NON-NLS-1$
		abldTargetEdit.setText(""); //$NON-NLS-1$
		abldFinalEdit.setText(""); //$NON-NLS-1$
		abldCleanEdit.setText(""); //$NON-NLS-1$
		abldFreezeEdit.setText(""); //$NON-NLS-1$
		
		if (sdk.getSupportedFeatures().contains(ISymbianSDKFeatures.IS_EKA2)) {
			abldFreezeEdit.setText("-r"); //$NON-NLS-1$
		}
	}
}
