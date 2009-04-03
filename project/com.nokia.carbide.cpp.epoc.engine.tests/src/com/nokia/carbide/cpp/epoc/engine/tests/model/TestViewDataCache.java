/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests.model;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.cpp.epoc.engine.tests.TestsPlugin;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.BasicIncludeFileLocator;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewDataCache;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.MultiResourceChangeListenerDispatcher;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;
import com.nokia.cpp.internal.api.utils.core.MultiResourceChangeListenerDispatcher.IResourceChangeHandler;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import java.io.File;
import java.util.ArrayList;


public class TestViewDataCache extends BaseTest {

	/**
	 * 
	 */
	private static final int TEST_TIME_MS = 7500;
	private ViewDataCache<IMMPOwnedModel, IMMPModel, IMMPView, IMMPData> viewDataCache;
	private IModelProvider modelProvider;
	private File tmpDir;
	private File projectDir;

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		modelProvider = ModelProviderFactory.createModelProvider(new MMPModelFactory());
		viewDataCache = new ViewDataCache<IMMPOwnedModel, IMMPModel, IMMPView, IMMPData>(
				modelProvider, 10);
		tmpDir = new File(FileUtils.getTemporaryDirectory(), ""+System.currentTimeMillis());
		tmpDir.mkdirs();
		projectDir = tmpDir;
		projectPath = new Path(tmpDir.getAbsolutePath());
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		FileUtils.delTree(tmpDir);
	}
	
	protected IPath makeFile(String relPath, String content) throws Exception {
		File file = new File(tmpDir, relPath);
		file.getParentFile().mkdirs();
		FileUtils.writeFileContents(file, content.toCharArray(), null);
		return new Path(file.getAbsolutePath());
	}
	
	public void testBasicNoProject() throws Exception {
		IPath mmpPath = makeFile("group/foo.mmp",
				"SOURCEPATH .\n"+
				"SOURCE foo.cpp\n");
		
		IMMPData data = viewDataCache.getData(mmpPath, new DefaultMMPViewConfiguration(projectPath));
		assertNotNull(data);
		assertEquals(1, data.getSources().size());
		assertEquals(new Path("group/foo.cpp"), data.getSources().get(0));
		
		IMMPData data2 = viewDataCache.getData(mmpPath, new DefaultMMPViewConfiguration(projectPath));
		assertSame(data, data2);
		
		// sanity check
		IPath mmpPathBar = makeFile("group/bar.mmp",
				"SOURCEPATH .\n"+
				"SOURCE bar.cpp\n");
		IMMPData dataBar = viewDataCache.getData(mmpPathBar, new DefaultMMPViewConfiguration(projectPath));
		assertNotSame(data, dataBar);
		
	}

	public void testInvalidateToplevelFile() throws Exception {
		IPath mmpPath = makeFile("group/foo.mmp",
				"SOURCEPATH .\n"+
				"SOURCE foo.cpp\n");
		
		IMMPData data = viewDataCache.getData(mmpPath, new DefaultMMPViewConfiguration(projectPath));
		assertNotNull(data);
		assertEquals(1, data.getSources().size());
		assertEquals(new Path("group/foo.cpp"), data.getSources().get(0));
		
		// change foo.mmp on disk
		Thread.sleep(50);
		makeFile("group/foo.mmp",
				"SOURCEPATH .\n"+
				"SOURCE foo.cpp bar.cpp\n");
		// cache throttles file timestamp checks
		Thread.sleep(55);

		IMMPData data2 = viewDataCache.getData(mmpPath, new DefaultMMPViewConfiguration(projectPath));
		assertNotNull(data2);
		assertNotSame(data, data2);
		assertEquals(2, data2.getSources().size());
		assertEquals(new Path("group/foo.cpp"), data2.getSources().get(0));
		assertEquals(new Path("group/bar.cpp"), data2.getSources().get(1));

	}

	public void testInvalidateIncludeFile() throws Exception {
		makeFile("group/header.mmh",
				"BASEADDRESS 0x6000");
		IPath mmpPath = makeFile("group/foo.mmp",
				"#include \"header.mmh\"\n"+
				"SOURCEPATH .\n"+
				"SOURCE foo.cpp\n");
		
		IMMPData data = viewDataCache.getData(mmpPath, new DefaultMMPViewConfiguration(projectPath));
		assertNotNull(data);
		assertEquals(1, data.getSources().size());
		assertEquals(new Path("group/foo.cpp"), data.getSources().get(0));
		assertEquals("0x6000", data.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));
		
		// change foo.mmh on disk
		Thread.sleep(5);
		makeFile("group/header.mmh",
				"BASEADDRESS 0x1111");
		
		// cache throttles disk time checks
		Thread.sleep(55);
		
		IMMPData data2 = viewDataCache.getData(mmpPath, new DefaultMMPViewConfiguration(projectPath));
		assertNotNull(data2);
		assertNotSame(data, data2);
		assertEquals(1, data2.getSources().size());
		assertEquals(new Path("group/foo.cpp"), data2.getSources().get(0));
		assertEquals("0x1111", data2.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));

		IMMPData data3 = viewDataCache.getData(mmpPath, new DefaultMMPViewConfiguration(projectPath));
		assertNotNull(data3);
		assertSame(data3, data2);

	}

	public void testInvalidateMacros() throws Exception {
		makeFile("group/header.mmh",
				"BASEADDRESS 0x6000");
		IPath mmpPath = makeFile("group/foo.mmp",
				"#include \"header.mmh\"\n"+
				"SOURCEPATH .\n"+
				"SOURCE MYSOURCE\n");
		
		DefaultMMPViewConfiguration viewConfig = new DefaultMMPViewConfiguration(projectPath);
		viewConfig.getExtraMacros().add(DefineFactory.createSimpleFreeformDefine("MYSOURCE=foo.cpp"));
		
		IMMPData data = viewDataCache.getData(mmpPath, viewConfig);
		assertNotNull(data);
		assertEquals(1, data.getSources().size());
		assertEquals(new Path("group/foo.cpp"), data.getSources().get(0));
		assertEquals("0x6000", data.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));
		
		// change macro

		viewConfig = new DefaultMMPViewConfiguration(projectPath);
		viewConfig.getExtraMacros().add(DefineFactory.createSimpleFreeformDefine("MYSOURCE=bar.cpp"));

		IMMPData data2 = viewDataCache.getData(mmpPath, viewConfig);
		assertNotNull(data2);
		assertNotSame(data, data2);
		assertEquals(1, data2.getSources().size());
		assertEquals(new Path("group/bar.cpp"), data2.getSources().get(0));
		assertEquals("0x6000", data2.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));

		// and no macros
		viewConfig = new DefaultMMPViewConfiguration(projectPath);
		IMMPData data3 = viewDataCache.getData(mmpPath, viewConfig);
		assertNotNull(data3);
		assertNotSame(data3, data2);

		assertEquals(1, data3.getSources().size());
		assertEquals(new Path("group/MYSOURCE"), data3.getSources().get(0));
		assertEquals("0x6000", data3.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));

	}

	public void testInvalidateMacros2() throws Exception {
		IPath mmpPath = makeFile("group/foo.mmp",
				"SOURCEPATH SOURCEDIR\n"+
				"SOURCE MYSOURCE\n");
		
		DefaultMMPViewConfiguration viewConfig = new DefaultMMPViewConfiguration(projectPath);
		viewConfig.getExtraMacros().add(DefineFactory.createSimpleFreeformDefine("MYSOURCE=foo.cpp"));
		viewConfig.getExtraMacros().add(DefineFactory.createSimpleFreeformDefine("SOURCEDIR=."));
		
		IMMPData data = viewDataCache.getData(mmpPath, viewConfig);
		assertNotNull(data);
		assertEquals(1, data.getSources().size());
		assertEquals(new Path("group/foo.cpp"), data.getSources().get(0));
		
		// change macro order only
		viewConfig = new DefaultMMPViewConfiguration(projectPath);
		viewConfig.getExtraMacros().add(DefineFactory.createSimpleFreeformDefine("SOURCEDIR=."));
		viewConfig.getExtraMacros().add(DefineFactory.createSimpleFreeformDefine("MYSOURCE=foo.cpp"));

		IMMPData data2 = viewDataCache.getData(mmpPath, viewConfig);
		assertNotNull(data2);
		//assertSame(data, data2);
		assertNotSame(data, data2);		// don't waste time sorting macros
		assertEquals(1, data2.getSources().size());
		assertEquals(new Path("group/foo.cpp"), data2.getSources().get(0));

		// now change macros
		viewConfig = new DefaultMMPViewConfiguration(projectPath);
		viewConfig.getExtraMacros().add(DefineFactory.createSimpleFreeformDefine("SOURCEDIR=.."));
		viewConfig.getExtraMacros().add(DefineFactory.createSimpleFreeformDefine("MYSOURCE=out.cpp"));
		
		IMMPData data3 = viewDataCache.getData(mmpPath, viewConfig);
		assertNotNull(data3);
		assertNotSame(data3, data2);

		assertEquals(1, data3.getSources().size());
		assertEquals(new Path("out.cpp"), data3.getSources().get(0));

	}
	
	public void testInvalidateIncludePaths() throws Exception {
		makeFile("inc/header.mmh",
				"BASEADDRESS 0x6000");
		makeFile("alt/header.mmh",
				"BASEADDRESS 0x1111");
		IPath mmpPath = makeFile("group/foo.mmp",
				"#include \"header.mmh\"\n"+
				"SOURCEPATH .\n"+
				"SOURCE foo.cpp\n");
		
		IIncludeFileLocator mylocator1 = new BasicIncludeFileLocator(
				new File[] { new File(projectDir, "inc") }, new File[0]);
		IIncludeFileLocator mylocator2 = new BasicIncludeFileLocator(
				new File[] { new File(projectDir, "alt") }, new File[0]);
		
		DefaultMMPViewConfiguration viewConfig = new DefaultMMPViewConfiguration(projectPath);
		((DefaultViewParserConfiguration) viewConfig.getViewParserConfiguration()).setIncludeFileLocator(mylocator1);
		
		IMMPData data = viewDataCache.getData(mmpPath, viewConfig);
		assertNotNull(data);
		assertEquals(1, data.getSources().size());
		assertEquals(new Path("group/foo.cpp"), data.getSources().get(0));
		assertEquals("0x6000", data.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));
		
		// change include path order
		viewConfig = new DefaultMMPViewConfiguration(projectPath);
		((DefaultViewParserConfiguration) viewConfig.getViewParserConfiguration()).setIncludeFileLocator(mylocator2);

		IMMPData data2 = viewDataCache.getData(mmpPath, viewConfig);
		assertNotNull(data2);
		assertNotSame(data, data2);
		assertEquals(1, data2.getSources().size());
		assertEquals(new Path("group/foo.cpp"), data2.getSources().get(0));
		assertEquals("0x1111", data2.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));
		
		viewConfig = new DefaultMMPViewConfiguration(projectPath);
		((DefaultViewParserConfiguration) viewConfig.getViewParserConfiguration()).setIncludeFileLocator(mylocator2);
		
		IMMPData data3 = viewDataCache.getData(mmpPath, viewConfig);
		assertNotNull(data3);
		assertSame(data3, data2);
	}
	
	class ViewWriter {
		private final IPath path;
		private final IViewConfiguration config;
		private final char token;

		public ViewWriter(IPath path, IViewConfiguration config, char token) {
			this.path = path;
			this.config = config;
			this.token = token;
		}
		
		public void doWork() {
			try {
				System.out.print(token);
				// get the data from cache...
				IMMPData data = viewDataCache.getData(path, config);
				
				IMMPModel model = (IMMPModel) modelProvider.getSharedModel(path);
				IMMPView view = model.createView(config);
				if (data.getFlags().contains(EMMPStatement.DEBUGGABLE)) {
					view.getFlags().remove(EMMPStatement.DEBUGGABLE);
				} else {
					view.getFlags().add(EMMPStatement.DEBUGGABLE);
				}

				try {
					view.commit();
				} catch (IllegalStateException e) {
					view.revert();
				}
			} catch (CoreException e) {
				throw new RuntimeException(e);	// get me out of here
			}
		}
	}
	
	class ViewReader {
		private final IPath path;
		private final IViewConfiguration config;
		private final char token;

		public ViewReader(IPath path, IViewConfiguration config, char token) {
			this.path = path;
			this.config = config;
			this.token = token;
		}
		
		public void doWork() {
			System.out.print(token);
			// get the data from cache...
			IMMPData data;
			try {
				data = viewDataCache.getData(path, config);
				data = viewDataCache.getData(path, config);
				data = viewDataCache.getData(path, config);
				data = viewDataCache.getData(path, config);
			} catch (CoreException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	class ViewWriterThread extends Thread {
		private ViewWriter accessor;
		private final int delay;

		public ViewWriterThread(IPath path, IViewConfiguration config, char token, int delay) {
			this.delay = delay;
			accessor = new ViewWriter(path, config, token);
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (!isInterrupted()) {
				accessor.doWork();
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
	
	class ViewWriterListener implements IResourceChangeHandler {
		private ViewWriter accessor;

		public ViewWriterListener(IPath path, IViewConfiguration config, char token) {
			accessor = new ViewWriter(path, config, token);
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.utils.MultiResourceChangeListenerDispatcher.IResourceChangeHandler#resourceChanged(org.eclipse.core.runtime.IPath)
		 */
		public void resourceChanged(IPath workspacePath) {
			// write in a resource job
			// (resource change listeners are not allowed to perform
			// resource changes on the files they're listening to)
			WorkspaceJob job = new WorkspaceJob("foo") {

				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor)
						throws CoreException {
					accessor.doWork();
					return Status.OK_STATUS;
				}
			};
			job.setRule(FileUtils.convertFileToExistingResource(this.accessor.path.toFile()));
			job.schedule();
		}
		
	}

	
	class ViewReaderListener implements IResourceChangeHandler {
		private ViewReader accessor;

		public ViewReaderListener(IPath path, IViewConfiguration config, char token) {
			accessor = new ViewReader(path, config, token);
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.utils.MultiResourceChangeListenerDispatcher.IResourceChangeHandler#resourceChanged(org.eclipse.core.runtime.IPath)
		 */
		public void resourceChanged(IPath workspacePath) {
			accessor.doWork();
		}
		
	}
	/** Bug found while fixing bug 7882 -- view commits were synchronized, but not
	 * CREATING views, which also needed to be.
	 * @throws Exception
	 */
	public void testViewCommitThreading() throws Exception {
		IPath mmpPath = makeFile("group/foo.mmp",
				"SOURCEPATH .\n"+
				"SOURCE foo.cpp\n"+
				"\n"+
				"TARGET foo.exe\n"+
				"TARGETTYPE exe\n"+
				"\n"+
				"LIBRARY a b c\n");
		
		IViewConfiguration config = new DefaultMMPViewConfiguration(projectPath);
		
		long timeout = System.currentTimeMillis() + TEST_TIME_MS;
		
		Thread a = new ViewWriterThread(mmpPath, config, 'a', 0);
		Thread b = new ViewWriterThread(mmpPath, config, 'b', 0);
		a.start();
		b.start();
		
		while (System.currentTimeMillis() < timeout) {
			Thread.sleep(100);
		}
		
		a.interrupt();
		b.interrupt();
		
		System.out.println();
		waitForThreads(a, b);
	}

	
	/**
	 * Bug found while fixing bug 7882: a resource listener which tries to commit a view would have
	 * parts of the model/view/cache set locked, while committing a view on another
	 * thread would have another set of objects locked in a different order, leading to deadlock.
	 * @throws Exception
	 */
	// XXX: EJS: this test fails sporadically and its fix or methodology needs more investigation.  It did not directly reflect the bug fixed, so I don't know if it's really an issue.
	public void UNSTABLE_testViewCommitResourceThreading() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription workspaceDesc = workspace.getDescription();
		workspaceDesc.setAutoBuilding(false);
		workspace.setDescription(workspaceDesc);

		String projectName = "threading-viewcommit";
		
		IProject project = ProjectCorePlugin.createProject(projectName, null);

		ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", TestsPlugin.getUsableBuildConfigs(), 
				new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());

		projectPath = project.getLocation();
		IPath mmpPath = projectPath.append("foo.mmp");
		FileUtils.writeFileContents(mmpPath.toFile(),
				("SOURCEPATH .\n"+
				"SOURCE foo.cpp\n"+
				"\n"+
				"TARGET foo.exe\n"+
				"TARGETTYPE exe\n"+
				"\n"+
				"LIBRARY a b c\n").toCharArray(), null);
		project.refreshLocal(1, null);
		
		
		IViewConfiguration config = new DefaultMMPViewConfiguration(projectPath);
		
		long timeout = System.currentTimeMillis() + TEST_TIME_MS;
		
		// listen to resource changes
		MultiResourceChangeListenerDispatcher dispatcher1 = new MultiResourceChangeListenerDispatcher();
		MultiResourceChangeListenerDispatcher dispatcher2 = new MultiResourceChangeListenerDispatcher();
		try {
			dispatcher1.addResource(FileUtils.convertToWorkspacePath(mmpPath), 
					new ViewWriterListener(
					FileUtils.convertToWorkspaceLocation(mmpPath), 
					config, 'l'));
			dispatcher2.addResource(FileUtils.convertToWorkspacePath(mmpPath), 
					new ViewWriterListener(
							FileUtils.convertToWorkspaceLocation(mmpPath), 
							config, 'm'));
			
			Thread a = new ViewWriterThread(mmpPath, config, 'a', 150);
			Thread b = new ViewWriterThread(mmpPath, config, 'b', 50);
			a.start();
			b.start();
			
			while (System.currentTimeMillis() < timeout) {
				Thread.sleep(100);
			}
			
			a.interrupt();
			b.interrupt();
			
			System.out.println();
			waitForThreads(a, b);
			
		} finally {
			dispatcher1.removeAll();
			dispatcher2.removeAll();
		}
		
		// do this outside the finally -- else the resources held by 
		// possibly deadlocked threads prevent it from finishing
		ProjectUtils.closeAndDeleteProject(projectName);
	}
	
	/**
	 * Bug 7882: when a view committed, it immediately alerted listeners (including the
	 * model provider), which kept the model/view locked while trying to save.
	 * A resource listener paying attention to this would try to read the view data
	 * cache, but since the model just changed, it would need to recreate the view.
	 * In this case, there are two threads locked on the model/view and view data cache.
	 * <p>
	 * We can break the cache lock.  We had it in order to avoid rereading the data 
	 * into the cache twice.  But we sacrifice that to avoid a deadlock.  The other
	 * tests above address the other deadlocks that could arise from simultaneous commits.
	 * @throws Exception
	 */
	public void testViewDataThreading() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription workspaceDesc = workspace.getDescription();
		workspaceDesc.setAutoBuilding(false);
		workspace.setDescription(workspaceDesc);

		String projectName = "threading-viewdatacache";
		
		IProject project = ProjectCorePlugin.createProject(projectName, null);

		ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", TestsPlugin.getUsableBuildConfigs(), 
				new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());

		projectPath = project.getLocation();
		IPath mmpPath = projectPath.append("foo.mmp");
		FileUtils.writeFileContents(mmpPath.toFile(),
				("SOURCEPATH .\n"+
				"SOURCE foo.cpp\n"+
				"\n"+
				"TARGET foo.exe\n"+
				"TARGETTYPE exe\n"+
				"\n"+
				"LIBRARY a b c\n").toCharArray(), null);
		project.refreshLocal(1, null);
		
		
		IViewConfiguration config = new DefaultMMPViewConfiguration(projectPath);
		
		long timeout = System.currentTimeMillis() + TEST_TIME_MS;
		
		// listen to resource changes
		MultiResourceChangeListenerDispatcher dispatcher1 = new MultiResourceChangeListenerDispatcher();
		MultiResourceChangeListenerDispatcher dispatcher2 = new MultiResourceChangeListenerDispatcher();
		try {
			dispatcher1.addResource(FileUtils.convertToWorkspacePath(mmpPath), 
					new ViewReaderListener(
					FileUtils.convertToWorkspaceLocation(mmpPath), 
					config, 'r'));
			dispatcher2.addResource(FileUtils.convertToWorkspacePath(mmpPath), 
					new ViewReaderListener(
							FileUtils.convertToWorkspaceLocation(mmpPath), 
							config, 's'));
			
			Thread a = new ViewWriterThread(mmpPath, config, 'a', 150);
			Thread b = new ViewWriterThread(mmpPath, config, 'b', 50);
			a.start();
			b.start();
			
			while (System.currentTimeMillis() < timeout) {
				Thread.sleep(100);
			}
			
			a.interrupt();
			b.interrupt();
			
			System.out.println();
			waitForThreads(a, b);
			
		} finally {
			dispatcher1.removeAll();
			dispatcher2.removeAll();
		}
		
		// do this outside the finally -- else the resources held by 
		// possibly deadlocked threads prevent it from finishing
		ProjectUtils.closeAndDeleteProject(projectName);
	}

	/**
	 * @param a
	 * @param b
	 */
	private void waitForThreads(Thread a, Thread b) throws Exception {
		a.join(5000);
		b.join(5000);
		if (a.isAlive() || b.isAlive()) {
			fail("Threads deadlocked");
		}
	}
}
