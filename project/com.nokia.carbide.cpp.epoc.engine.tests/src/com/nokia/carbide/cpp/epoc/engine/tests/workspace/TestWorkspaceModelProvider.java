/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests.workspace;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.IBldInfViewRunnable;
import com.nokia.carbide.cpp.epoc.engine.IImageMakefileViewRunnable;
import com.nokia.carbide.cpp.epoc.engine.IMMPViewRunnable;
import com.nokia.carbide.cpp.epoc.engine.MMPViewRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.image.ISVGSource;
import com.nokia.carbide.cpp.epoc.engine.model.IModelProvider;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfModel;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageBuilderCommandLineConverter;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileModel;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.ImageBuilderCommandLineConverterFactory;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.model.ModelProviderThreadTests;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.sdt.utils.StandaloneFileTracker;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;

/**
 * Basic tests of workspace sanity (real tests in TestModelProvider)
 *
 */
public class TestWorkspaceModelProvider extends BaseProjectTests {

	private static final String TEST_BLDINF_0 = "PRJ_PLATFORMS default\n"+
	"PRJ_MMPFILES\n"+
	"test.mmp\n";
	private static final String TEST_MMP_0 = "";
	private static final String TEST_MMP_USER_0 = "SOURCE bar.cpp\n";
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		((ModelProviderBase) bldInfModelProvider).reset();
		((ModelProviderBase) mmpModelProvider).reset();
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	protected IPath getFullPath(IPath wsPath) {
		return ResourcesPlugin.getWorkspace().getRoot().getLocation().append(wsPath);
	}
	public void testCreateModel() throws CoreException {
		IPath thePath = (new Path(projectName + "/group/bld.inf"));
		IBldInfOwnedModel model = bldInfModelProvider.createModel(thePath);
		assertNotNull(model);
		model.setDocument(DocumentFactory.createDocument(TEST_BLDINF_0));

		bldInfModelProvider.registerModel(model);
		
		// assert the file now exists
		IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(model.getPath());
		assertTrue(files.length > 0 && files[0].exists());
		
		// and it's got the right contents
		InputStream is = files[0].getContents();
		char[] cs = FileUtils.readInputStreamContents(is, null);
		assertEquals(TEST_BLDINF_0, new String(cs));
		
		// and ensure we can find it
		IBldInfModel sharedModel = bldInfModelProvider.findSharedModel(thePath);
		assertNotNull(sharedModel);
		
		bldInfModelProvider.releaseSharedModel(sharedModel);
		bldInfModelProvider.releaseSharedModel(model);
		
	}
	
	public void testModelResourceListenersConflict() throws Exception {
		
		IPath thePath = (new Path(projectName + "/group/bld.inf"));
		IBldInfOwnedModel ownedModel = bldInfModelProvider.createModel(thePath);
		ownedModel.setDocument(DocumentFactory.createDocument(TEST_BLDINF_0));
		bldInfModelProvider.registerModel(ownedModel);

		IBldInfModel model = bldInfModelProvider.getSharedModel(thePath);
		IBldInfView view = model.createView(configuration);
	
		// make the mmp, empty
		IPath mmpPath = new Path(projectName).append(view.getAllMMPReferences()[0].getPath());
		
		IMMPOwnedModel ownedMmp = mmpModelProvider.createModel(mmpPath);
		ownedMmp.setDocument(DocumentFactory.createDocument(TEST_MMP_0));
		mmpModelProvider.registerModel(ownedMmp);
		mmpModelProvider.releaseSharedModel(ownedMmp);
		
		IMMPModel mmp = mmpModelProvider.getSharedModel(mmpPath);
		IMMPView mmpView = mmp.createView(mmpConfiguration);
		
		mmpView.getSources().add(new Path("src/foo.cpp"));
		
		// now, someone changes the mmp on disk!
		IFile mmpFile = ResourcesPlugin.getWorkspace().getRoot().getFile(mmpPath);
		InputStream source = new ByteArrayInputStream(TEST_MMP_USER_0.getBytes());
		mmpFile.setContents(source, false, true, new NullProgressMonitor());
		source.close();
		
		// wait for resource listener
		sleep();

		// ensure the view is dirty
		assertTrue(mmpView.needsSynchonize());
		
		mmpView.revert();
		
		assertEquals(1, mmpView.getSources().size());
		assertEquals(new Path("group/bar.cpp"), mmpView.getSources().get(0));
		
		mmpView.dispose();
		mmpModelProvider.releaseSharedModel(mmp);
		mmpModelProvider.unregisterModel(ownedMmp);
		
		bldInfModelProvider.releaseSharedModel(model);
		bldInfModelProvider.unregisterModel(ownedModel);
		
	}
	
	/** test that normal commit works, but doesn't act screwy */ 
	public void testModelResourceListenersNormalSave() throws Exception {
		
		// make the mmp, empty
		IPath mmpPath = (new Path(projectName + "/group/test.mmp"));
		
		IMMPOwnedModel ownedMmp = mmpModelProvider.createModel(mmpPath);
		ownedMmp.setDocument(DocumentFactory.createDocument(TEST_MMP_0));
		mmpModelProvider.registerModel(ownedMmp);
		mmpModelProvider.releaseSharedModel(ownedMmp);
		
		IMMPModel mmp = mmpModelProvider.getSharedModel(mmpPath);
		IMMPView mmpView = mmp.createView(mmpConfiguration);
		
		mmpView.getSources().add(new Path("src/foo.cpp"));
		
		// now save
		mmpView.commit();
		
		// wait for a while so Eclipse dispatches listeners, etc...
		sleep();

		// ensure the view is okay
		assertFalse(mmpView.needsSynchonize());
		
		mmpView.dispose();
		mmpModelProvider.releaseSharedModel(mmp);

	}
	
	private static final String TEST_MMP_2 = 
		"SOURCEPATH ..\\src\n"+
		"SOURCE a.cpp b.cpp\n"+
		"TARGETTYPE exe\n"+
		"TARGET foo.exe\n";
	private static final String TEST_MMP_2_m = 
		"TARGETTYPE exe\n";
	
	/**
	 * Ensure that the model provider detects a change to the document
	 * and persists it.
	 * @throws Exception
	 */
	public void testModifyModel() throws Exception {
		// make an mmp
		IPath mmpPath = (new Path(projectName + "/group/test.mmp"));
		
		IMMPOwnedModel ownedMmp = mmpModelProvider.createModel(mmpPath);
		ownedMmp.setDocument(DocumentFactory.createDocument(TEST_MMP_2));
		mmpModelProvider.registerModel(ownedMmp);
		mmpModelProvider.releaseSharedModel(ownedMmp);
	
		IMMPModel model = mmpModelProvider.getSharedModel(mmpPath);
		IMMPView view = model.createView(mmpConfiguration);
		view.getSources().clear();
		view.getSingleArgumentSettings().remove(EMMPStatement.TARGET);
		view.commit();
		
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(mmpPath);
		assertNotNull(file);
		InputStream is = file.getContents();
		char[] text = FileUtils.readInputStreamContents(is, null);
		assertEquals(TEST_MMP_2_m, new String(text));
		
	}
	public void testMMPViewRunner() throws Exception {
		// make the mmp
		IPath mmpPath = (new Path(projectName + "/group/test.mmp"));
		
		IMMPOwnedModel ownedMmp = mmpModelProvider.createModel(mmpPath);
		ownedMmp.setDocument(DocumentFactory.createDocument(TEST_MMP_USER_0));
		mmpModelProvider.registerModel(ownedMmp);
		mmpModelProvider.releaseSharedModel(ownedMmp);
		
		///
		final boolean failed[] = new boolean[1];
		Object result = EpocEnginePlugin.runWithMMPView(mmpPath, mmpConfiguration, 
				new IMMPViewRunnable() {

					public Object failedLoad(CoreException exception) {
						failed[0] = true;
						return null;
					}

					public Object run(IMMPView view) {
						return view.getSources().get(0);
					}
		});

		assertEquals(new Path("group/bar.cpp"), result);
		assertFalse(failed[0]);
		
		// twice, with a change
		EpocEnginePlugin.runWithMMPView(mmpPath, mmpConfiguration, 
				new IMMPViewRunnable() {

					public Object failedLoad(CoreException exception) {
						failed[0] = true;
						return null;
					}

					public Object run(IMMPView view) {
						view.getSources().remove(0);
						view.commit();
						return null;
					}
		});

		assertFalse(failed[0]);
	
		// validate change
		result = EpocEnginePlugin.runWithMMPView(mmpPath, mmpConfiguration, 
				new IMMPViewRunnable() {

					public Object failedLoad(CoreException exception) {
						failed[0] = true;
						return null;
					}

					public Object run(IMMPView view) {
						return view.getSources().size();
					}
		});

		assertEquals(0, result);
		assertFalse(failed[0]);

	}
	
	final static String TEST_IMAGE_MAKEFILE_0 =
		"RESOURCE:\n"+
		"\tmifconv foo.mif /c32,8 bar.svg\n";
	
	public void testImageMakefileViewRunner() throws Exception {
		// make the ImageMakefile
		IPath imageMakefilePath = (new Path(projectName + "/group/Icons.mk"));
		
		IModelProvider<IImageMakefileOwnedModel, IImageMakefileModel> imageMakefileModelProvider = EpocEnginePlugin.getImageMakefileModelProvider();
		IImageMakefileOwnedModel ownedImageMakefile = imageMakefileModelProvider.createModel(imageMakefilePath);
		ownedImageMakefile.setDocument(DocumentFactory.createDocument(TEST_IMAGE_MAKEFILE_0));
		imageMakefileModelProvider.registerModel(ownedImageMakefile);
		imageMakefileModelProvider.releaseSharedModel(ownedImageMakefile);

		IImageMakefileViewConfiguration imageMakefileViewConfiguration = new IImageMakefileViewConfiguration() {

			public IImageBuilderCommandLineConverter getImageBuilderCommandLineConverter() {
				return ImageBuilderCommandLineConverterFactory.createMifConvConverter();
			}

			public String getImageBuilderName() {
				return "mifconv";
			}

			public String getMakefileStyle() {
				return "GNU";
			}

			public Collection<IDefine> getMacros() {
				return configuration.getMacros();
			}

			public IViewFilter getViewFilter() {
				return configuration.getViewFilter();
			}

			public IViewParserConfiguration getViewParserConfiguration() {
				return configuration.getViewParserConfiguration();
			}
			
		};
		
		// access it
		///
		final boolean failed[] = new boolean[1];
		Object result = EpocEnginePlugin.runWithImageMakefileView(imageMakefilePath, imageMakefileViewConfiguration, 
				new IImageMakefileViewRunnable() {

					public Object failedLoad(CoreException exception) {
						failed[0] = true;
						return null;
					}

					public Object run(IImageMakefileView view) {
						return view.getMultiImageSources().get(0).getSources().get(0).getPath();
					}
		});

		assertEquals(new Path("group/bar.svg"), result);
		assertFalse(failed[0]);
		
		// twice, with a change
		EpocEnginePlugin.runWithImageMakefileView(imageMakefilePath, imageMakefileViewConfiguration, 
				new IImageMakefileViewRunnable() {

					public Object failedLoad(CoreException exception) {
						failed[0] = true;
						return null;
					}

					public Object run(IImageMakefileView view) {
						ISVGSource svg = view.getMultiImageSources().get(0).createSVGSource();
						svg.setColor(true);
						svg.setDepth(32);
						svg.setMaskDepth(0);
						svg.setPath(new Path("gfx/pix.svg"));
						view.getMultiImageSources().get(0).getSources().add(svg);
						view.commit();
						return null;
					}
		});

		assertFalse(failed[0]);
	
		// validate change
		result = EpocEnginePlugin.runWithImageMakefileView(imageMakefilePath, imageMakefileViewConfiguration, 
				new IImageMakefileViewRunnable() {

					public Object failedLoad(CoreException exception) {
						failed[0] = true;
						return null;
					}

					public Object run(IImageMakefileView view) {
						return view.getMultiImageSources().get(0).getSources().size();
					}
		});

		assertEquals(2, result);
		assertFalse(failed[0]);

	}
	

	/**
	 * Ensure the models work sanely with threading.
	 * @throws Exception
	 */
	public void testThreading() throws CoreException {
		IPath mmpPath = new Path(project.getName()).append("test.mmp");
		
		ModelProviderThreadTests test = new ModelProviderThreadTests();
		test.testThreading(mmpModelProvider, mmpConfiguration, mmpPath);
	}		
	
	/** Inspired by bug 3088 which is a non-bug */
	public void testDetectUpdates() throws CoreException {
		project.getFolder("group").create(false, true, null);
		
		for (int i = 0; i < 5; i++) {
			updateRound();
		}

	}

	private void updateRound() throws CoreException {
		IPath wsBldInfPath = new Path(project.getName()).append("group/bld.inf");
		createOrSaveFile(wsBldInfPath, 
				"PRJ_MMPFILES\n"+
				"orig.mmp\n"+
				"test.mmp\n");
		
		EpocEnginePlugin.runWithBldInfView(wsBldInfPath, configuration, 
				new IBldInfViewRunnable() {

					public Object failedLoad(CoreException exception) {
						return null;
					}

					public Object run(IBldInfView view) {
						assertEquals(2, view.getMakMakeReferences().size());
						return null;
					}
			
		});

		createOrSaveFile(wsBldInfPath,
				"PRJ_MMPFILES\n"+
				"orig.mmp\n");

		EpocEnginePlugin.runWithBldInfView(wsBldInfPath, configuration, 
				new IBldInfViewRunnable() {

					public Object failedLoad(CoreException exception) {
						return null;
					}

					public Object run(IBldInfView view) {
						assertEquals(1, view.getMakMakeReferences().size());
						return null;
					}
			
		});
		
	}
	
	/**
	 * Ensure that files living and modified outside the workspace are reloaded when changed.
	 * @throws Exception
	 */
	public void testExternalModification_bug3557() throws Exception {
		// make the mmp
		IPath fullMmpPath = new Path(getTokenAbsolutePath()).append("tempmodel.mmp");
		fullMmpPath.toFile().getParentFile().mkdirs();
		
		String text = 
			"START BITMAP foo.mbm\n"+
			"SOURCE C8,BAD foo.bmp bar.bmp\n"+
			"END\n";
		
		IMMPOwnedModel ownedMmp = mmpModelProvider.createModel(fullMmpPath);
		ownedMmp.setDocument(DocumentFactory.createDocument(text));
		mmpModelProvider.registerModel(ownedMmp);
		mmpModelProvider.releaseSharedModel(ownedMmp);
		
		IMMPModel model = EpocEnginePlugin.getMMPModelProvider().getSharedModel(fullMmpPath);
		assertNotNull(model);

		IMMPView view = model.createView(mmpConfiguration);
		assertEquals(1, view.getBitmaps().size());
		
		assertEquals(1, view.getBitmaps().get(0).getBitmapSources().size());
		
		String text2 = 
			"START BITMAP foo.mbm\n"+
			"SOURCE C8 foo.bmp bar.bmp\n"+
			"END\n";

		// wait long enough for the timestamp to differ
		Thread.sleep(1000);
		
		// rewrite outside
		new StandaloneFileTracker().saveFileText(fullMmpPath.toFile(), null, text2.toCharArray());
		
		// we don't detect it here
		assertFalse(view.needsSynchonize());
		
		view.dispose();
		
		EpocEnginePlugin.getMMPModelProvider().releaseSharedModel(model);
		
		///
		
		// this version should be updated
		model = EpocEnginePlugin.getMMPModelProvider().getSharedModel(fullMmpPath);
		assertNotNull(model);
		
		assertEquals(text2, ((IOwnedModel)model).getDocument().get());

		view = model.createView(mmpConfiguration);
		assertEquals(1, view.getBitmaps().size());
		
		assertEquals(2, view.getBitmaps().get(0).getBitmapSources().size());
		
		fullMmpPath.toFile().delete();

	}
	
	public void testMultiDocumentStorageTracking() throws Exception {
		IPath wsMmpPath = new Path(project.getName()).append("group/test.mmp");
		createOrSaveFile(wsMmpPath, 
				"#include \"header.mmh\"\n"+
				"SOURCEPATH SRCDIR\n"+
				"SOURCE a.cpp\n");

		IPath wsMmhPath = new Path(project.getName()).append("group/header.mmh");
		createOrSaveFile(wsMmhPath,
				"#define SRCDIR ..\\src\n"+
				"BASEADDRESS 0x6000\n");
		
		IMMPViewConfiguration config = new DefaultMMPViewConfiguration(
				CarbideBuilderPlugin.getBuildManager().getProjectInfo(project),
				new AcceptedNodesViewFilter());

		addWaitingResourceListener(wsMmhPath);
		
		// make change to header
		EpocEnginePlugin.runWithMMPView(wsMmpPath, config, 
				new MMPViewRunnableAdapter() {
					public Object run(IMMPView view) {
						assertEquals(new Path("src/a.cpp"),
								view.getSources().get(0));
						assertEquals("0x6000", 
								view.getSingleArgumentSettings().get(
										EMMPStatement.BASEADDRESS));
						
						view.getSingleArgumentSettings().put(
								EMMPStatement.BASEADDRESS,
								"0");
						view.commit();
						return null;
					}
		});

		// verify model-wise
		EpocEnginePlugin.runWithMMPView(wsMmpPath, config, 
				new MMPViewRunnableAdapter() {
					public Object run(IMMPView view) {
						assertEquals(new Path("src/a.cpp"),
								view.getSources().get(0));
						assertEquals("0", 
								view.getSingleArgumentSettings().get(
										EMMPStatement.BASEADDRESS));
						return null;
					}
		});
		
		// verify file-wise
		while (!waitingResourceChanged) {
			sleep();
		}
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(wsMmhPath);
		assertNotNull(file);
		InputStream is = file.getContents();
		char[] text = FileUtils.readInputStreamContents(is, null);
		assertEquals("#define SRCDIR ..\\src\n"+
				"BASEADDRESS 0\n", 
				new String(text));
		
	}
	
}
