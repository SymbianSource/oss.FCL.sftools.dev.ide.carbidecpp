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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.image.ISVGSourceReference;
import com.nokia.carbide.cpp.epoc.engine.model.MMPModelFactory;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.images.CarbideImageModelFactory;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.Document;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class AIFEditorContext extends
		MultiImageEditorContextBase {

	private IMMPAIFInfo aifInfo;
	private IMMPAIFInfo ownedAifInfo;
	private List<IPath> eligibleRssFiles;
	private MMPViewPathHelper pathHelper;
	private List<IMMPAIFInfo> allAifs;

	/**
	 * 
	 */
	public AIFEditorContext() {
		super();
	}

	/** Generic reaction to any change in the history. */
	protected void updateDirtyState() {
		isDirty = !aifInfo.equals(ownedAifInfo);
		super.updateDirtyState();
	}

	public void initFromAIF(ICarbideProjectInfo projectInfo, IPath modelPath, IMMPAIFInfo aifInfo, List<IMMPAIFInfo> allAifs,
			MMPViewPathHelper pathHelper, List<IPath> rssFiles) {
		this.ownedAifInfo = aifInfo;
		this.aifInfo = aifInfo.copy();
		IPath target = aifInfo.getTarget();
		if (target != null)
			setTargetFileName(target.lastSegment());
		else
			setTargetFileName("untitled.rss"); //$NON-NLS-1$
		this.allAifs = allAifs;
		this.carbideProjectInfo = projectInfo;
		this.project = projectInfo != null ? projectInfo.getProject() : null;
		this.eligibleRssFiles = rssFiles;
		this.pathHelper = pathHelper;
		this.containerModel = CarbideImageModelFactory.createAIFImageContainerModel(
				carbideProjectInfo, modelPath, aifInfo);
		commonInit();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.MultiImageEditorContextBase#createBitmapSourceReference()
	 */
	@Override
	public IBitmapSourceReference createBitmapSourceReference() {
		return aifInfo.createBitmapSourceReference();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.MultiImageEditorContextBase#createSVGSourceReference()
	 */
	@Override
	public ISVGSourceReference createSVGSourceReference() {
		return null;
	}

	/**
	 * @return
	 */
	public IMMPAIFInfo getMMPAIFInfo() {
		return aifInfo;
	}

	/**
	 * @return
	 */
	public IStatus getValidationStatus() {
		StringBuilder errors = new StringBuilder();
		StringBuilder warnings = new StringBuilder();
		
		if (aifInfo.getTarget() == null || aifInfo.getTarget().segmentCount() == 0) {
			errors.append(Messages.getString("AIFEditorContext.EmptyTargetError")); //$NON-NLS-1$
		} else if (allAifs != null) {
			IPath myPath = aifInfo.getTarget();
			if (myPath == null) 
				myPath = new Path(""); //$NON-NLS-1$
			for (IMMPAIFInfo info : allAifs) {
				if (info != aifInfo && info != ownedAifInfo) {
					IPath thePath = info.getTarget();
					if (thePath == null)
						thePath = new Path(""); //$NON-NLS-1$
					if (myPath.toOSString().equalsIgnoreCase(thePath.toOSString())) {
						errors.append(Messages.getString("AIFEditorContext.SameTargetFilePathError")); //$NON-NLS-1$
					} else if (myPath.lastSegment().equalsIgnoreCase(thePath.lastSegment())) {
						warnings.append(Messages.getString("AIFEditorContext.SameTargetFileError")); //$NON-NLS-1$
						
					}
				}
			}
		}
		if (aifInfo.getResource() == null || aifInfo.getResource().segmentCount() == 0) {
			errors.append(Messages.getString("AIFEditorContext.EmptyResourceFileError")); //$NON-NLS-1$
		}
		if (aifInfo.getColorDepth() == 0 && aifInfo.getSourceBitmaps().size()  > 0) {
			errors.append(Messages.getString("AIFEditorContext.BitmapsWithoutColorDepthError")); //$NON-NLS-1$
		}
		else if (aifInfo.getColorDepth() != 0 && aifInfo.getSourceBitmaps().size() == 0) {
			errors.append(Messages.getString("AIFEditorContext.NeedOneBitmapError")); //$NON-NLS-1$
		}
		if (aifInfo.getMaskDepth() != 0) {
			for (IBitmapSourceReference ref : aifInfo.getSourceBitmaps()) {
				if (ref.getMaskPath() == null) {
					errors.append(MessageFormat.format( 
							Messages.getString("AIFEditorContext.NoPairedImageMaskError"), //$NON-NLS-1$
							new Object[] { ref.getPath() }));
				}
			}
		} else if (aifInfo.getColorDepth() != 0) {
			warnings.append(Messages.getString("AIFEditorContext.NeedMaskDepthWarning")); //$NON-NLS-1$
		}

		// check for invalid filenames
		for (IBitmapSourceReference source : aifInfo.getSourceBitmaps()) {
			if (!isValidPath(source.getPath())) {
				warnings.append(MessageFormat.format(Messages.getString("AIFEditorContext.InvalidFilenameEnumeratorWarning"), //$NON-NLS-1$
								new Object[] { source.getPath() })); 
			}
			IPath maskPath = source.getMaskPath();
			if (maskPath != null && !isValidPath(maskPath)) {
				warnings.append(MessageFormat.format(Messages.getString("AIFEditorContext.InvalidFilenameEnumeratorWarning"), //$NON-NLS-1$
								new Object[] { maskPath })); 
			}
		}

		if (errors.length() > 0) {
			return Logging.newStatus(ProjectUIPlugin.getDefault(), IStatus.ERROR, 
					errors.toString());
		}
		else if (warnings.length() > 0) {
			return Logging.newStatus(ProjectUIPlugin.getDefault(), IStatus.WARNING, 
					warnings.toString());
		}
		Check.checkState(aifInfo.isValid());
		return Status.OK_STATUS;
	}
	
	public List<IPath> getRSSFiles() {
		return eligibleRssFiles;
	}

	/**
	 * @return
	 */
	public MMPViewPathHelper getMMPViewPathHelper() {
		if (pathHelper == null) {
			pathHelper = project != null ? 
				new MMPViewPathHelper(project, null) :
				new MMPViewPathHelper(new Path("."), null); //$NON-NLS-1$
		}
		return pathHelper;
	}
	
	public void doSave() {
		ownedAifInfo.set(aifInfo);
	}
	
	public static void main(String[] args) {
		final String TEST_PREFS = "c:/temp/mii.prefs"; //$NON-NLS-1$

		Display display = new Display();
		
		IMMPOwnedModel model = new MMPModelFactory().createModel(new Path("test.mmp"), new Document()); //$NON-NLS-1$
		model.parse();
		
		IMMPView view = model.createView(new DefaultMMPViewConfiguration(
				new Path("c:/bld.inf"), new AcceptedNodesViewFilter())); //$NON-NLS-1$
		
		IMMPAIFInfo aifInfo = view.createMMPAIFInfo();
		aifInfo.setColor(true);
		aifInfo.setColorDepth(8);
		aifInfo.setMaskDepth(8);
		
		aifInfo.setTarget(new Path("\\resources\\apps\\foo.aif")); //$NON-NLS-1$
		IBitmapSourceReference bm = aifInfo.createBitmapSourceReference();
		bm.setPath(new Path("gfx/pic.bmp")); //$NON-NLS-1$
		bm.setMaskPath(new Path("mask-.bmp")); //$NON-NLS-1$
		aifInfo.getSourceBitmaps().add(bm);
		
		bm = aifInfo.createBitmapSourceReference();
		bm.setPath(new Path("gfx/pic2-.bmp")); //$NON-NLS-1$
		bm.setMaskPath(new Path("gfx/mask2.bmp")); //$NON-NLS-1$
		aifInfo.getSourceBitmaps().add(bm);

		bm = aifInfo.createBitmapSourceReference();
		bm.setPath(new Path("gfx/pic3.bmp")); //$NON-NLS-1$
		bm.setMaskPath(new Path("gfx/pic3_mask.bmp")); //$NON-NLS-1$
		aifInfo.getSourceBitmaps().add(bm);

		bm = aifInfo.createBitmapSourceReference();
		bm.setPath(new Path("pic4.bmp")); //$NON-NLS-1$
		bm.setMaskPath(new Path("pic4_mask.bmp")); //$NON-NLS-1$
		aifInfo.getSourceBitmaps().add(bm);
	

		AIFEditorContext context = new AIFEditorContext();

		try {
			context.getDialogSettings().load(TEST_PREFS);
		} catch (IOException e) {
		}

		context.initFromAIF(null, null, aifInfo, null, new MMPViewPathHelper(view, (IPath)null), null);
		
		AIFEditorDialog dialog = new AIFEditorDialog((Shell)null, context);
		dialog.open();
		context.dispose();
		
		if (ProjectUIPlugin.getDefault() == null) {
			try {
				context.getDialogSettings().save(TEST_PREFS);
			} catch (IOException e) {
			}
		}

		display.dispose();
	}

}
