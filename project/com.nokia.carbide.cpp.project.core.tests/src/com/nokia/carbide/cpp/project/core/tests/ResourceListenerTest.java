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
package com.nokia.carbide.cpp.project.core.tests;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.image.IImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.internal.api.project.core.*;
import com.nokia.carbide.cpp.project.core.*;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ResourceListenerTest extends TestCase {

	private static final String BASE_DIR = "Data/TestProject1/";
	private static final String PROJECT_NAME = "TestProject1";
	private static final String WORKSPACE_RELATIVE_BLDINF_PATH = "TestProject1/Group/bld.inf";
	private static final String PROJECT_RELATIVE_BLDINF_PATH = "Group/bld.inf";
	private static final String WORKSPACE_RELATIVE_MMP1_PATH = "TestProject1/Group/Test1.mmp";
	private static final String WORKSPACE_RELATIVE_MMP2_PATH = "TestProject1/Group/Test2.mmp";
	
	private IProject project;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// turn off the indexer
		CCorePlugin.getIndexManager().setDefaultIndexerId(IPDOMManager.ID_NO_INDEXER);

		ResourceChangeListener.setUpdateQuery(new IUpdateProjectFilesQuery() {
			public boolean shouldUpdate(IProject project, IFile file, List<String> changeDetails, boolean isAdd) {
				return true;
			}
		});
		
		// turn off auto-building
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription workspaceDesc = workspace.getDescription();
		workspaceDesc.setAutoBuilding(false);
		workspace.setDescription(workspaceDesc);

		// stop the listener
		ProjectCorePluginUtility.stopProjectListener();

		// delete the project in case it was left over from last time
		try {
			ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME).delete(true, null);
		} catch (CoreException e) {
		}
		
		// create the test project
		project = ProjectCorePlugin.createProject(PROJECT_NAME, null);
		assertNotNull(project);

		// copy the project contents into the workspace
		File baseDir;
		if (Platform.isRunning()) {
			baseDir = FileUtils.pluginRelativeFile(TestsPlugin.getDefault(), BASE_DIR);
		} else {
			baseDir = new File(BASE_DIR).getAbsoluteFile();
		}

		FileUtils.copyTreeNoParent(baseDir, project.getLocation().toFile(), new FileFilter() {
			public boolean accept(File arg0) {
				return true;
			}
		});
		
		// refresh the workspace
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);

		List<ISymbianBuildContext> configs = TestsPlugin.getUsableBuildConfigs();
		
		ProjectCorePlugin.postProjectCreatedActions(project, PROJECT_RELATIVE_BLDINF_PATH, configs, new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());

		ProjectCorePluginUtility.startProjectListener();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		ProjectCorePluginUtility.stopProjectListener();
		
		project.delete(true, true, null);
	}

	/**
	 * Waste time so Eclipse will run resource listeners.
	 *
	 */
	protected void sleep() throws Exception {
		Job job = new WorkspaceJob("sleeping") {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				monitor.beginTask("sleeping", 2);
				monitor.worked(1);
				monitor.worked(1);
				monitor.done();
				return Status.OK_STATUS;
			}
			
		};
		job.schedule();
		job.join();
	}

	public void testListenerTurnedOff() throws Exception {
		ProjectCorePluginUtility.stopProjectListener();

		int origCount = ResourceChangeListener.getIteration();

		final IPath deletedSource = new Path("Src1/test1.cpp");
		project.getFile(deletedSource).delete(true, null);
		
		// just wait for a bit
		for (int i=0; i<3; i++) {
			  sleep(); // let Eclipse run other jobs
			  // maybe this helps too, to avoid thrashing:
			  Thread.sleep(100);
		}
		
		assertEquals(origCount, ResourceChangeListener.getIteration());
		
		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					
					boolean found = false;
					for (IPath src : view.getSources()) {
						if (src.toOSString().compareToIgnoreCase(deletedSource.toOSString()) == 0) {
							found = true;
						}
					}
					
					if (!found) {
						fail();
					}

					return null;
				}
		});		
	}

	public void testUniqueSourceFileDeleted() throws Exception {
		int origCount = ResourceChangeListener.getIteration();

		final IPath deletedSource = new Path("Src1/test1.cpp");
		project.getFile(deletedSource).delete(true, null);
		
		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					for (IPath src : view.getSources()) {
						if (src.toOSString().compareToIgnoreCase(deletedSource.toOSString()) == 0) {
							fail();
						}
					}
					return null;
				}
		});		
	}

	public void testCommonSourceFileDeleted() throws Exception {
		int origCount = ResourceChangeListener.getIteration();

		final IPath deletedSource = new Path("CommonSrc/testcommon1.cpp");
		project.getFile(deletedSource).delete(true, null);
		
		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is updated for both mmps
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					for (IPath src : view.getSources()) {
						if (src.toOSString().compareToIgnoreCase(deletedSource.toOSString()) == 0) {
							fail();
						}
					}
					return null;
				}
		});		

		// make sure the model is updated for both mmps
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP2_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					for (IPath src : view.getSources()) {
						if (src.toOSString().compareToIgnoreCase(deletedSource.toOSString()) == 0) {
							fail();
						}
					}
					return null;
				}
		});		
	}

	public void testOtherFilesDeleted() throws Exception {
		// delete export, test export, make file, resource, resource block, aif, mbm, deffile, document 
		int origCount = ResourceChangeListener.getIteration();
		int deletedFiles = 0;
		
		final IPath deletedExport = new Path("data/test1.txt");
		project.getFile(deletedExport).delete(true, null);
		deletedFiles++;
		
		final IPath deletedTestExport = new Path("Test/testexport.txt");
		project.getFile(deletedTestExport).delete(true, null);
		deletedFiles++;
		
		final IPath deletedMakefile = new Path("Group/testmk.mk");
		project.getFile(deletedMakefile).delete(true, null);
		deletedFiles++;
		
		final IPath deletedResource = new Path("data/testrss.rss");
		project.getFile(deletedResource).delete(true, null);
		deletedFiles++;

		final IPath deletedSystemResource = new Path("data/testsystemrss.rss");
		project.getFile(deletedSystemResource).delete(true, null);
		deletedFiles++;

		final IPath deletedResourceBlock = new Path("data/testblock.rss");
		project.getFile(deletedResourceBlock).delete(true, null);
		deletedFiles++;

		final IPath deletedAif = new Path("Aif/testaif.rss");
		project.getFile(deletedAif).delete(true, null);
		deletedFiles++;

		final IPath deletedAifBmp = new Path("Aif/test1.bmp");
		project.getFile(deletedAifBmp).delete(true, null);
		deletedFiles++;

		final IPath deletedBmp = new Path("Bmp/test1.bmp");
		project.getFile(deletedBmp).delete(true, null);
		deletedFiles++;

		final IPath deletedDef = new Path("Group/test.def");
		project.getFile(deletedDef).delete(true, null);
		deletedFiles++;

		final IPath deletedDoc = new Path("Doc/testdoc.txt");
		project.getFile(deletedDoc).delete(true, null);
		deletedFiles++;

		// wait a while or until all changes have gone through the resource listener
		for (int i=0; i<100; i++) {
			sleep(); // let Eclipse run other jobs
			// maybe this helps too, to avoid thrashing:
			Thread.sleep(100);
			if (origCount + deletedFiles == ResourceChangeListener.getIteration()) {
				break;
			}
		}
		
		// if our count isn't right by now then something must have gone wrong
		if (origCount + deletedFiles != ResourceChangeListener.getIteration()) {
			fail();
		}
		
		// make sure the inf model is updated
		EpocEnginePlugin.runWithBldInfView(new Path(WORKSPACE_RELATIVE_BLDINF_PATH), new DefaultViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IBldInfViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IBldInfView view) {
					for (IExport export : view.getExports()) {
						if (export.getSourcePath().toOSString().compareToIgnoreCase(deletedExport.toOSString()) == 0) {
							fail();
						}
					}

					for (IExport testexport : view.getTestExports()) {
						if (testexport.getSourcePath().toOSString().compareToIgnoreCase(deletedTestExport.toOSString()) == 0) {
							fail();
						}
					}

					for (IMakMakeReference makefile : view.getMakMakeReferences()) {
						if (makefile.getPath().toOSString().compareToIgnoreCase(deletedMakefile.toOSString()) == 0) {
							fail();
						}
					}
					return null;
				}
		});

		// make sure the mmp model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					for (IPath path : view.getUserResources()) {
						if (path.toOSString().compareToIgnoreCase(deletedResource.toOSString()) == 0) {
							fail();
						}
					}

					for (IPath path : view.getSystemResources()) {
						if (path.toOSString().compareToIgnoreCase(deletedSystemResource.toOSString()) == 0) {
							fail();
						}
					}

					for (IMMPResource resource : view.getResourceBlocks()) {
						if (resource.getSource().toOSString().compareToIgnoreCase(deletedResourceBlock.toOSString()) == 0) {
							fail();
						}
					}
					
					// there should be no aifs - the aif entry should be removed if its rss is removed
					assertTrue(view.getAifs().size() == 0);

					for (IMMPBitmap mbm : view.getBitmaps()) {
						for (IImageSource bmp : mbm.getSources()) {
							if (bmp.getPath().toOSString().compareToIgnoreCase(deletedBmp.toOSString()) == 0) {
								fail();
							}
						}
					}

					// the def file was removed so this should return null
					assertNull(view.getDefFile());
					
					for (IPath path : view.getDocuments()) {
						if (path.toOSString().compareToIgnoreCase(deletedDoc.toOSString()) == 0) {
							fail();
						}
					}
					return null;
				}
		});		
	}

	public void testMakMakeFilesDeleted() throws Exception {
		// delete mmp, extension make file, bld.inf
		int origCount = ResourceChangeListener.getIteration();
		int deletedFiles = 0;
		
		final IPath deletedMMP = new Path("Group/test2.mmp");
		project.getFile(deletedMMP).delete(true, null);
		deletedFiles++;
		
		final IPath deletedMakefile = new Path("Group/testmk.mk");
		project.getFile(deletedMakefile).delete(true, null);
		deletedFiles++;
		
		final IPath deletedTestMMP = new Path("Test/testmmp.mmp");
		project.getFile(deletedTestMMP).delete(true, null);
		deletedFiles++;
		
		// wait a while or until all changes have gone through the resource listener
		for (int i=0; i<100; i++) {
			sleep(); // let Eclipse run other jobs
			// maybe this helps too, to avoid thrashing:
			Thread.sleep(100);
			if (origCount + deletedFiles == ResourceChangeListener.getIteration()) {
				break;
			}
		}
		
		// if our count isn't right by now then something must have gone wrong
		if (origCount + deletedFiles != ResourceChangeListener.getIteration()) {
			fail();
		}
		
		// make sure the inf model is updated
		EpocEnginePlugin.runWithBldInfView(new Path(WORKSPACE_RELATIVE_BLDINF_PATH), new DefaultViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IBldInfViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IBldInfView view) {
					assertTrue(view.getMakMakeReferences().size() == 1);
					assertTrue(view.getTestMakMakeReferences().size() == 0);
					if (view.getMakMakeReferences().get(0).getPath().toOSString().compareToIgnoreCase(new Path("Group/test1.mmp").toOSString()) != 0) {
						fail();
					}
					return null;
				}
		});
	}

	public void testUniqueFolderDeleted() throws Exception {
		int origCount = ResourceChangeListener.getIteration();

		final IPath deletedFolder = new Path("Src1");
		project.getFolder(deletedFolder).delete(true, null);
		
		// these are the sources that should be deleted
		final IPath deletedSource1 = new Path("Src1/test1.cpp");
		final IPath deletedSource2 = new Path("Src1/test2.cpp");
		final IPath deletedSource3 = new Path("Src1/test3.cpp");

		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the sources in that folder have been removed
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(deletedSource1.toOSString()) == 0) {
							fail();
						}
					}
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(deletedSource2.toOSString()) == 0) {
							fail();
						}
					}
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(deletedSource3.toOSString()) == 0) {
							fail();
						}
					}
					
					// make sure the sourcepath has been removed
					for (IPath srcPath: view.getEffectiveSourcePaths()) {
						if (srcPath.toOSString().compareToIgnoreCase(deletedFolder.toOSString()) == 0) {
							fail();
						}
					}
					return null;
				}
		});		
	}

	public void testCommonFolderDeleted() throws Exception {
		int origCount = ResourceChangeListener.getIteration();

		final IPath deletedFolder = new Path("CommonSrc");
		project.getFolder(deletedFolder).delete(true, null);
		
		// these are the sources that should be deleted
		final IPath deletedSource1 = new Path("CommonSrc/testcommon1.cpp");
		final IPath deletedSource2 = new Path("CommonSrc/testcommon2.cpp");
		final IPath deletedSource3 = new Path("CommonSrc/testcommon3.cpp");

		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model for mmp1 is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the sources in that folder have been removed
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(deletedSource1.toOSString()) == 0) {
							fail();
						}
					}
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(deletedSource2.toOSString()) == 0) {
							fail();
						}
					}
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(deletedSource3.toOSString()) == 0) {
							fail();
						}
					}
					
					// make sure the sourcepath has been removed
					for (IPath srcPath: view.getEffectiveSourcePaths()) {
						if (srcPath.toOSString().compareToIgnoreCase(deletedFolder.toOSString()) == 0) {
							fail();
						}
					}
					return null;
				}
		});		

		// make sure the model for mmp2 is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP2_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the sources in that folder have been removed
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(deletedSource1.toOSString()) == 0) {
							fail();
						}
					}
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(deletedSource2.toOSString()) == 0) {
							fail();
						}
					}
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(deletedSource3.toOSString()) == 0) {
							fail();
						}
					}
					
					// make sure the sourcepath has been removed
					for (IPath srcPath: view.getEffectiveSourcePaths()) {
						if (srcPath.toOSString().compareToIgnoreCase(deletedFolder.toOSString()) == 0) {
							fail();
						}
					}
					return null;
				}
		});		
	}

	public void testOtherFoldersDeleted() throws Exception {
		// delete folders containing various types of bld.inf/mmp elements
		// and make sure they are removed from the models
		int origCount = ResourceChangeListener.getIteration();
		int deletedFiles = 0;
		
		project.getFolder(new Path("Aif")).delete(true, null);
		deletedFiles++;

		project.getFolder(new Path("Bmp")).delete(true, null);
		deletedFiles++;

		project.getFolder(new Path("data")).delete(true, null);
		deletedFiles++;
		
		project.getFolder(new Path("Doc")).delete(true, null);
		deletedFiles++;

		project.getFolder(new Path("Inc")).delete(true, null);
		deletedFiles++;

		project.getFolder(new Path("Test")).delete(true, null);
		deletedFiles++;

		// wait a while or until all changes have gone through the resource listener
		for (int i=0; i<100; i++) {
			sleep(); // let Eclipse run other jobs
			// maybe this helps too, to avoid thrashing:
			Thread.sleep(100);
			if (origCount + deletedFiles == ResourceChangeListener.getIteration()) {
				break;
			}
		}
		
		// if our count isn't right by now then something must have gone wrong
		if (origCount + deletedFiles != ResourceChangeListener.getIteration()) {
			fail();
		}
		
		// make sure the inf model is updated
		EpocEnginePlugin.runWithBldInfView(new Path(WORKSPACE_RELATIVE_BLDINF_PATH), new DefaultViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IBldInfViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IBldInfView view) {
					// there should be no exports or test exports
					assertTrue(view.getExports().size() == 0);
					assertTrue(view.getTestExports().size() == 0);

					// there should be no test mmps
					assertTrue(view.getTestMakMakeReferences().size() == 0);
					return null;
				}
		});

		// make sure the mmp model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// there should be no system include
					assertTrue(view.getSystemIncludes().size() == 0);
					
					// there should be one user include, "."
					assertTrue(view.getUserIncludes().size() == 1);
					if (view.getUserIncludes().get(0).toOSString().compareToIgnoreCase(new Path("Group").toOSString()) != 0) {
						fail();
					}
					
					// there should be no resources at all
					assertTrue(view.getUserResources().size() == 0);
					assertTrue(view.getSystemResources().size() == 0);
					assertTrue(view.getResourceBlocks().size() == 0);
					
					// there should be no aifs
					assertTrue(view.getAifs().size() == 0);
					
					// there should be an mbm with no bmps
					assertTrue(view.getBitmaps().size() == 1);
					assertTrue(view.getBitmaps().get(0).getBitmapSources().size() == 0);
					
					// there should be no document
					assertTrue(view.getDocuments().size() == 0);

					return null;
				}
		});		

		// make sure the mmp model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP2_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// there should be no system include
					assertTrue(view.getSystemIncludes().size() == 0);
					
					// there should be one user include, "."
					assertTrue(view.getUserIncludes().size() == 1);
					if (view.getUserIncludes().get(0).toOSString().compareToIgnoreCase(new Path("Group").toOSString()) != 0) {
						fail();
					}

					return null;
				}
		});		
	}

	public void testNonSourceAdded() throws Exception {
		// create a new source file in a folder only used by one mmp
		final IPath newSource = new Path("Src1/testcreated.notasourceextension");
		project.getFile(newSource).create(new InputStream() {

			@Override
			public int read() throws IOException {
				return -1;
			}
			
		}, true, null);
		
		// note that since nothing is to happen we don't check the iterator
		for (int i=0; i<100; i++) {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		};
		
		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the file has not been added
					if (view.getSources().contains(newSource)) {
						fail();
					}
					return null;
				}
		});		
	}

	public void testUniqueSourceAdded() throws Exception {
		int origCount = ResourceChangeListener.getIteration();

		// create a new source file in a folder only used by one mmp
		final IPath newSource = new Path("Src1/testcreated.cpp");
		project.getFile(newSource).create(new InputStream() {

			@Override
			public int read() throws IOException {
				return -1;
			}
			
		}, true, null);
		
		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the source has been added
					if (!view.getSources().contains(newSource)) {
						fail();
					}
					return null;
				}
		});		
	}

	public void testUniqueSourcesAddedToSeparateDirs() throws Exception {
		int origCount = ResourceChangeListener.getIteration();

		final IPath newSource1 = new Path("Src1/testcreated1.cpp");
		final IPath newSource2 = new Path("Src2/testcreated2.cpp");
		
		WorkspaceJob job = new WorkspaceJob("Test") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				// create a new source file in a folder only used by one mmp
				project.getFile(newSource1).create(new InputStream() {

					@Override
					public int read() throws IOException {
						return -1;
					}
					
				}, true, monitor);
				
				project.getFile(newSource2).create(new InputStream() {
					
					@Override
					public int read() throws IOException {
						return -1;
					}
					
				}, true, monitor);
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		
		int numSleeps = 0;
		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		  numSleeps++;
		  if (numSleeps >= 600) // if we wait for a minute, fail
			  fail();
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the source has been added
					if (!view.getSources().contains(newSource1)) {
						fail();
					}
					if (!view.getSources().contains(newSource2)) {
						fail();
					}
					return null;
				}
		});		
	}

	public void testSourceAddedInNewFolder_noMMPsSelected() throws Exception {
		
		ResourceChangeListener.setResolver(new IMMPSelectionResolver() {

			public List<IMMPReference> addSourceToWhichMMPs(List<IMMPReference> allMMPs, List<IMMPReference> mmpsContainingSourcePath) {
				// there should be no mmps containing this sourcepath
				assertTrue(mmpsContainingSourcePath.size() == 0);
				
				// return an empty list - the same as user selecting no mmps from the list, or just hitting cancel
				List<IMMPReference> mmps = new ArrayList<IMMPReference>(0);
				return mmps;
			}
			
		});
		
		int origCount = ResourceChangeListener.getIteration();

		// create a new source file in a new folder
		project.getFolder("NewFolder").create(true, true, null);
		final IPath newSource = new Path("NewFolder/testcreated.cpp");
		project.getFile(newSource).create(new InputStream() {

			@Override
			public int read() throws IOException {
				return -1;
			}
			
		}, true, null);
		
		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is not updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the source has been added
					if (view.getSources().contains(newSource)) {
						fail();
					}
					return null;
				}
		});		

		// make sure the model is not updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP2_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the source has been added
					if (view.getSources().contains(newSource)) {
						fail();
					}
					return null;
				}
		});		
	}

	public void testSourceAddedInNewFolder_oneMMPsSelected() throws Exception {
		
		ResourceChangeListener.setResolver(new IMMPSelectionResolver() {

			public List<IMMPReference> addSourceToWhichMMPs(List<IMMPReference> allMMPs, List<IMMPReference> mmpsContainingSourcePath) {
				// there should be no mmps containing this sourcepath
				assertTrue(mmpsContainingSourcePath.size() == 0);
				
				// return the first mmp
				List<IMMPReference> mmps = new ArrayList<IMMPReference>(0);
				mmps.add(allMMPs.get(0));
				return mmps;
			}
			
		});
		
		int origCount = ResourceChangeListener.getIteration();

		// create a new source file in a new folder
		project.getFolder("NewFolder").create(true, true, null);
		final IPath newSource = new Path("NewFolder/testcreated.cpp");
		project.getFile(newSource).create(new InputStream() {

			@Override
			public int read() throws IOException {
				return -1;
			}
			
		}, true, null);
		
		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the source has been added
					if (!view.getSources().contains(newSource)) {
						fail();
					}
					return null;
				}
		});		

		// make sure the model is not updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP2_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the source has been added
					if (view.getSources().contains(newSource)) {
						fail();
					}
					return null;
				}
		});		
	}

	public void testSourceAddedInCommonFolder_BothMMPsSelected() throws Exception {
		
		ResourceChangeListener.setResolver(new IMMPSelectionResolver() {

			public List<IMMPReference> addSourceToWhichMMPs(List<IMMPReference> allMMPs, List<IMMPReference> mmpsContainingSourcePath) {
				// there should be no mmps containing this sourcepath
				assertTrue(mmpsContainingSourcePath.size() == 2);
				
				// return both mmps
				List<IMMPReference> mmps = new ArrayList<IMMPReference>(0);
				mmps.addAll(mmpsContainingSourcePath);
				return mmps;
			}
			
		});
		
		int origCount = ResourceChangeListener.getIteration();

		// create a new source file in a new folder
		final IPath newSource = new Path("CommonSrc/testcreated.cpp");
		project.getFile(newSource).create(new InputStream() {

			@Override
			public int read() throws IOException {
				return -1;
			}
			
		}, true, null);
		
		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the source has been added
					if (!view.getSources().contains(newSource)) {
						fail();
					}
					return null;
				}
		});		

		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP2_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the source has been added
					if (!view.getSources().contains(newSource)) {
						fail();
					}
					return null;
				}
		});		
	}

	public void testUniqueSourceRenamed() throws Exception {
		int origCount = ResourceChangeListener.getIteration();

		final IPath renamedSource = new Path("Src1/test1.cpp");
		final IPath newSource = new Path("Src1/test1renamed.cpp");
		project.getFile(renamedSource).move(newSource.removeFirstSegments(1), true, null);
		
		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(renamedSource.toOSString()) == 0) {
							fail();
						}
					}
					boolean found = false;
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(newSource.toOSString()) == 0) {
							found = true;
						}
					}
					assertTrue(found);
					return null;
				}
		});		
	}
	
	public void testCommonSourceFileRenamed() throws Exception {
		int origCount = ResourceChangeListener.getIteration();

		final IPath renamedSource = new Path("CommonSrc/testcommon1.cpp");
		final IPath newSource = new Path("CommonSrc/test1renamed.cpp");
		project.getFile(renamedSource).move(newSource.removeFirstSegments(1), true, null);
		
		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is updated for both mmps
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(renamedSource.toOSString()) == 0) {
							fail();
						}
					}
					boolean found = false;
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(newSource.toOSString()) == 0) {
							found = true;
						}
					}
					assertTrue(found);
					return null;
				}
		});		

		// make sure the model is updated for both mmps
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP2_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					for (IPath path : view.getSources()) {
						if (path.toOSString().compareToIgnoreCase(renamedSource.toOSString()) == 0) {
							fail();
						}
					}
					if (!view.getSources().contains(newSource)) {
						fail();
					}
					return null;
				}
		});		
	}

	public void testOtherFilesRenamed() throws Exception {
		// rename export, test export, make file, resource, resource block, aif, mbm, deffile, document 
		int origCount = ResourceChangeListener.getIteration();
		int renamedFiles = 0;
		
		final IPath oldExport = new Path("data/test1.txt");
		final IPath newExport = new Path("data/test1renamed.txt");
		project.getFile(oldExport).move(newExport.removeFirstSegments(1), true, null);
		renamedFiles++;
		
		final IPath oldTestExport = new Path("Test/testexport.txt");
		final IPath newTestExport = new Path("Test/testexportrenamed.txt");
		project.getFile(oldTestExport).move(newTestExport.removeFirstSegments(1), true, null);
		renamedFiles++;
		
		final IPath oldMakefile = new Path("Group/testmk.mk");
		final IPath newMakefile = new Path("Group/testmkrenamed.mk");
		project.getFile(oldMakefile).move(newMakefile.removeFirstSegments(1), true, null);
		renamedFiles++;
		
		final IPath oldResource = new Path("data/testrss.rss");
		final IPath newResource = new Path("data/testrssrenamed.rss");
		project.getFile(oldResource).move(newResource.removeFirstSegments(1), true, null);
		renamedFiles++;

		final IPath oldSystemResource = new Path("data/testsystemrss.rss");
		final IPath newSystemResource = new Path("data/testsystemrssrenamed.rss");
		project.getFile(oldSystemResource).move(newSystemResource.removeFirstSegments(1), true, null);
		renamedFiles++;

		final IPath oldResourceBlock = new Path("data/testblock.rss");
		final IPath newResourceBlock = new Path("data/testblockrenamed.rss");
		project.getFile(oldResourceBlock).move(newResourceBlock.removeFirstSegments(1), true, null);
		renamedFiles++;

		final IPath oldAif = new Path("Aif/testaif.rss");
		final IPath newAif = new Path("Aif/testaifrenamed.rss");
		project.getFile(oldAif).move(newAif.removeFirstSegments(1), true, null);
		renamedFiles++;

		final IPath oldAifBmp = new Path("Aif/test1.bmp");
		final IPath newAifBmp = new Path("Aif/test1renamed.bmp");
		project.getFile(oldAifBmp).move(newAifBmp.removeFirstSegments(1), true, null);
		renamedFiles++;

		final IPath oldBmp = new Path("Bmp/test1.bmp");
		final IPath newBmp = new Path("Bmp/test1renamed.bmp");
		project.getFile(oldBmp).move(newBmp.removeFirstSegments(1), true, null);
		renamedFiles++;

		final IPath oldDef = new Path("Group/test.def");
		final IPath newDef = new Path("Group/testrenamed.def");
		project.getFile(oldDef).move(newDef.removeFirstSegments(1), true, null);
		renamedFiles++;

		final IPath oldDoc = new Path("Doc/testdoc.txt");
		final IPath newDoc = new Path("Doc/testdocrenamed.txt");
		project.getFile(oldDoc).move(newDoc.removeFirstSegments(1), true, null);
		renamedFiles++;

		// wait a while or until all changes have gone through the resource listener
		for (int i=0; i<100; i++) {
			sleep(); // let Eclipse run other jobs
			// maybe this helps too, to avoid thrashing:
			Thread.sleep(100);
			if (origCount + renamedFiles == ResourceChangeListener.getIteration()) {
				break;
			}
		}
		
		// if our count isn't right by now then something must have gone wrong
		if (origCount + renamedFiles != ResourceChangeListener.getIteration()) {
			fail();
		}
		
		// make sure the inf model is updated
		EpocEnginePlugin.runWithBldInfView(new Path(WORKSPACE_RELATIVE_BLDINF_PATH), new DefaultViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IBldInfViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IBldInfView view) {
					boolean foundExport = false;
					for (IExport export : view.getExports()) {
						if (export.getSourcePath().toOSString().compareToIgnoreCase(oldExport.toOSString()) == 0) {
							fail();
						}
						if (export.getSourcePath().equals(newExport)) {
							foundExport = true;
						}
					}
					assertTrue(foundExport);

					boolean foundTestExport = false;
					for (IExport testexport : view.getTestExports()) {
						if (testexport.getSourcePath().toOSString().compareToIgnoreCase(oldTestExport.toOSString()) == 0) {
							fail();
						}
						if (testexport.getSourcePath().equals(newTestExport)) {
							foundTestExport = true;
						}
					}
					assertTrue(foundTestExport);

					boolean foundMakefile = false;
					for (IMakMakeReference makefile : view.getMakMakeReferences()) {
						if (makefile.getPath().toOSString().compareToIgnoreCase(oldMakefile.toOSString()) == 0) {
							fail();
						}
						if (makefile.getPath().equals(newMakefile)) {
							foundMakefile = true;
						}
					}
					assertTrue(foundMakefile);
					
					return null;
				}
		});

		// make sure the mmp model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					for (IPath path : view.getUserResources()) {
						if (path.toOSString().compareToIgnoreCase(oldResource.toOSString()) == 0) {
							fail();
						}
					}
					if (!view.getUserResources().contains(newResource)) {
						fail();
					}

					for (IPath path : view.getSystemResources()) {
						if (path.toOSString().compareToIgnoreCase(oldSystemResource.toOSString()) == 0) {
							fail();
						}
					}
					if (!view.getSystemResources().contains(newSystemResource)) {
						fail();
					}

					boolean foundResourceBlock = false;
					for (IMMPResource resource : view.getResourceBlocks()) {
						if (resource.getSource().toOSString().compareToIgnoreCase(oldResourceBlock.toOSString()) == 0) {
							fail();
						}
						if (resource.getSource().equals(newResourceBlock)) {
							foundResourceBlock = true;
						}
					}
					assertTrue(foundResourceBlock);
					
					boolean foundAif = false;
					for (IMMPAIFInfo aif : view.getAifs()) {
						if (aif.getResource().toOSString().compareToIgnoreCase(oldAif.toOSString()) == 0) {
							fail();
						}
						if (aif.getResource().equals(newAif)) {
							foundAif = true;
						}
						
						boolean foundAifBmp = false;
						for (IBitmapSourceReference bmpSrc : aif.getSourceBitmaps()) {
							if (bmpSrc.getPath().toOSString().compareToIgnoreCase(oldAifBmp.toOSString()) == 0) {
								fail();
							}
							if (bmpSrc.getPath().equals(newAifBmp)) {
								foundAifBmp = true;
							}
						}
						assertTrue(foundAifBmp);
					}
					assertTrue(foundAif);

					boolean foundBmp = false;
					for (IMMPBitmap mbm : view.getBitmaps()) {
						for (IImageSource bmp : mbm.getSources()) {
							if (bmp.getPath().toOSString().compareToIgnoreCase(oldBmp.toOSString()) == 0) {
								fail();
							}
							if (bmp.getPath().equals(newBmp)) {
								foundBmp = true;
							}
						}
					}
					assertTrue(foundBmp);

					if (view.getDefFile().toOSString().compareToIgnoreCase(newDef.toOSString()) != 0) {
						fail();
					}
					
					for (IPath path : view.getDocuments()) {
						if (path.toOSString().compareToIgnoreCase(oldDoc.toOSString()) == 0) {
							fail();
						}
					}
					if (!view.getDocuments().contains(newDoc)) {
						fail();
					}

					return null;
				}
		});		
	}

	public void testMakMakeFilesRenamed() throws Exception {
		// rename mmp, extension make file, bld.inf
		int origCount = ResourceChangeListener.getIteration();
		int renamedFiles = 0;
		
		final IPath oldMMP = new Path("Group/test2.mmp");
		final IPath newMMP = new Path("Group/test2renamed.mmp");
		project.getFile(oldMMP).move(newMMP.removeFirstSegments(1), true, null);
		renamedFiles++;
		
		final IPath oldMakefile = new Path("Group/testmk.mk");
		final IPath newMakefile = new Path("Group/testmkrenamed.mk");
		project.getFile(oldMakefile).move(newMakefile.removeFirstSegments(1), true, null);
		renamedFiles++;
		
		final IPath oldTestMMP = new Path("Test/testmmp.mmp");
		final IPath newTestMMP = new Path("Test/testmmprenamed.mmp");
		project.getFile(oldTestMMP).move(newTestMMP.removeFirstSegments(1), true, null);
		renamedFiles++;
		
		// wait a while or until all changes have gone through the resource listener
		// wait longer in this case since we're moving mmp files which will cause a refresh
		// of the configuration data.
		for (int i=0; i<1000; i++) {
			sleep(); // let Eclipse run other jobs
			// maybe this helps too, to avoid thrashing:
			Thread.sleep(100);
			if (origCount + renamedFiles == ResourceChangeListener.getIteration()) {
				break;
			}
		}
		
		// if our count isn't right by now then something must have gone wrong
		if (origCount + renamedFiles != ResourceChangeListener.getIteration()) {
			fail();
		}
		
		// make sure the inf model is updated
		EpocEnginePlugin.runWithBldInfView(new Path(WORKSPACE_RELATIVE_BLDINF_PATH), new DefaultViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IBldInfViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IBldInfView view) {
					boolean foundMMP = false;
					boolean foundMakefile = false;
					for (IMakMakeReference file : view.getMakMakeReferences()) {
						if (file.getPath().toOSString().compareToIgnoreCase(oldMMP.toOSString()) == 0) {
							fail();
						}
						if (file.getPath().equals(newMMP)) {
							foundMMP = true;
						}
						if (file.getPath().toOSString().compareToIgnoreCase(oldMakefile.toOSString()) == 0) {
							fail();
						}
						if (file.getPath().equals(newMakefile)) {
							foundMakefile = true;
						}
					}
					assertTrue(foundMMP);
					assertTrue(foundMakefile);

					boolean foundTestMMP = false;
					for (IMakMakeReference testMMP : view.getTestMakMakeReferences()) {
						if (testMMP.getPath().toOSString().compareToIgnoreCase(oldTestMMP.toOSString()) == 0) {
							fail();
						}
						if (testMMP.getPath().equals(newTestMMP)) {
							foundTestMMP = true;
						}
					}
					assertTrue(foundTestMMP);

					return null;
				}
		});
	}

	public void testUniqueFolderRenamed() throws Exception {
		int origCount = ResourceChangeListener.getIteration();

		final IPath oldFolder = new Path("Src1");
		final IPath newFolder = new Path("Src1renamed");
		project.getFolder(oldFolder).move(newFolder, true, null);
		
		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the sourcepath has been renamed
					boolean foundFolder = false;
					for (IPath srcPath: view.getEffectiveSourcePaths()) {
						if (srcPath.toOSString().compareToIgnoreCase(oldFolder.toOSString()) == 0) {
							fail();
						}
						if (srcPath.equals(newFolder)) {
							foundFolder = true;
						}
					}
					assertTrue(foundFolder);
					
					return null;
				}
		});		
	}

	public void testCommonFolderRenamed() throws Exception {
		int origCount = ResourceChangeListener.getIteration();

		final IPath oldFolder = new Path("CommonSrc");
		final IPath newFolder = new Path("CommonSrcrenamed");
		project.getFolder(oldFolder).move(newFolder, true, null);

		do {
		  sleep(); // let Eclipse run other jobs
		  // maybe this helps too, to avoid thrashing:
		  Thread.sleep(100);
		} while (origCount == ResourceChangeListener.getIteration());
		
		// make sure the model for mmp1 is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the sourcepath has been renamed
					boolean foundFolder = false;
					for (IPath srcPath: view.getEffectiveSourcePaths()) {
						if (srcPath.toOSString().compareToIgnoreCase(oldFolder.toOSString()) == 0) {
							fail();
						}
						if (srcPath.equals(newFolder)) {
							foundFolder = true;
						}
					}
					assertTrue(foundFolder);

					return null;
				}
		});		

		// make sure the model for mmp2 is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP2_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					// make sure the sourcepath has been renamed
					boolean foundFolder = false;
					for (IPath srcPath: view.getEffectiveSourcePaths()) {
						if (srcPath.toOSString().compareToIgnoreCase(oldFolder.toOSString()) == 0) {
							fail();
						}
						if (srcPath.equals(newFolder)) {
							foundFolder = true;
						}
					}
					assertTrue(foundFolder);

					return null;
				}
		});		
	}

	public void testOtherFoldersRenamed() throws Exception {
		// rename folders containing various types of bld.inf/mmp elements
		// and make sure they are removed from the models
		int origCount = ResourceChangeListener.getIteration();
		int renamedFiles = 0;
		
		final IPath oldAifFolder = new Path("Aif");
		final IPath newAifFolder = new Path("AifRenamed");
		project.getFolder(oldAifFolder).move(newAifFolder, true, null);
		renamedFiles++;

		final IPath oldBmpFolder = new Path("Bmp");
		final IPath newBmpFolder = new Path("BmpRenamed");
		project.getFolder(oldBmpFolder).move(newBmpFolder, true, null);
		renamedFiles++;

		final IPath oldDataFolder = new Path("data");
		final IPath newDataFolder = new Path("dataRenamed");
		project.getFolder(oldDataFolder).move(newDataFolder, true, null);
		renamedFiles++;
		
		final IPath oldDocFolder = new Path("Doc");
		final IPath newDocFolder = new Path("DocRenamed");
		project.getFolder(oldDocFolder).move(newDocFolder, true, null);
		renamedFiles++;

		final IPath oldIncFolder = new Path("Inc");
		final IPath newIncFolder = new Path("IncRenamed");
		project.getFolder(oldIncFolder).move(newIncFolder, true, null);
		renamedFiles++;

		final IPath oldTestFolder = new Path("Test");
		final IPath newTestFolder = new Path("TestRenamed");
		project.getFolder(oldTestFolder).move(newTestFolder, true, null);
		renamedFiles++;

		// wait a while or until all changes have gone through the resource listener
		for (int i=0; i<100; i++) {
			sleep(); // let Eclipse run other jobs
			// maybe this helps too, to avoid thrashing:
			Thread.sleep(100);
			if (origCount + renamedFiles == ResourceChangeListener.getIteration()) {
				break;
			}
		}
		
		// if our count isn't right by now then something must have gone wrong
		if (origCount + renamedFiles != ResourceChangeListener.getIteration()) {
			fail();
		}
		
		// make sure the inf model is updated
		EpocEnginePlugin.runWithBldInfView(new Path(WORKSPACE_RELATIVE_BLDINF_PATH), new DefaultViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IBldInfViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IBldInfView view) {
					
					for (IExport export : view.getExports()) {
						if (!export.getSourcePath().toOSString().toLowerCase().startsWith(newDataFolder.toOSString().toLowerCase())) {
							fail();
						}
					}

					for (IExport export : view.getTestExports()) {
						if (!export.getSourcePath().toOSString().toLowerCase().startsWith(newTestFolder.toOSString().toLowerCase())) {
							fail();
						}
					}

					// there should be no test mmps
					if (!view.getTestMakMakeReferences().get(0).getPath().toOSString().toLowerCase().startsWith(newTestFolder.toOSString().toLowerCase())) {
						fail();
					}
					return null;
				}
		});

		// make sure the mmp model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP1_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					if (!view.getSystemIncludes().get(0).toOSString().toLowerCase().startsWith(newIncFolder.toOSString().toLowerCase())) {
						fail();
					}
					
					if (!view.getUserIncludes().get(1).toOSString().toLowerCase().startsWith(newIncFolder.toOSString().toLowerCase())) {
						fail();
					}
					
					if (!view.getUserResources().get(0).toOSString().toLowerCase().startsWith(newDataFolder.toOSString().toLowerCase())) {
						fail();
					}
					if (!view.getSystemResources().get(0).toOSString().toLowerCase().startsWith(newDataFolder.toOSString().toLowerCase())) {
						fail();
					}
					if (!view.getResourceBlocks().get(0).getSource().toOSString().toLowerCase().startsWith(newDataFolder.toOSString().toLowerCase())) {
						fail();
					}

					if (!view.getAifs().get(0).getResource().toOSString().toLowerCase().startsWith(newAifFolder.toOSString().toLowerCase())) {
						fail();
					}

					for (IImageSource bmp : view.getBitmaps().get(0).getBitmapSources()) {
						if (!bmp.getPath().toOSString().toLowerCase().startsWith(newBmpFolder.toOSString().toLowerCase())) {
							fail();
						}
					}
					
					if (!view.getDocuments().get(0).toOSString().toLowerCase().startsWith(newDocFolder.toOSString().toLowerCase())) {
						fail();
					}

					return null;
				}
		});		

		// make sure the mmp model is updated
		EpocEnginePlugin.runWithMMPView(new Path(WORKSPACE_RELATIVE_MMP2_PATH), new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()), 
			new IMMPViewRunnable() {

				public Object failedLoad(CoreException exception) {
					fail();
					return null;
				}

				public Object run(IMMPView view) {
					if (!view.getSystemIncludes().get(0).toOSString().toLowerCase().startsWith(newIncFolder.toOSString().toLowerCase())) {
						fail();
					}
					
					if (!view.getUserIncludes().get(1).toOSString().toLowerCase().startsWith(newIncFolder.toOSString().toLowerCase())) {
						fail();
					}

					return null;
				}
		});		
	}
}
