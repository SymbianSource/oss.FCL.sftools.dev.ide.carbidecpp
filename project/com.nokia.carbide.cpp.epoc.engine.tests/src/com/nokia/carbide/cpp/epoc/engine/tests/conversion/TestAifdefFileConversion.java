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

package com.nokia.carbide.cpp.epoc.engine.tests.conversion;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.IAifDefFileConverter;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.IPathResolver;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.LegacyImageFileConverterFactory;
import com.nokia.cpp.internal.api.utils.core.FileUtils;


public class TestAifdefFileConversion extends BaseTest {

	private static final String BAD_PROJECT_ROOT = "c:/workspace/CarbideTest/FormTime/";
	private File tmpdir;
	private File tmpMbmdef;
	private IPathResolver resolver;

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		tmpdir = FileUtils.getTemporaryDirectory();
		tmpdir = tmpdir.getCanonicalFile();
		tmpMbmdef = File.createTempFile("aif", ".mbmdef", tmpdir);
		
		File dir = new File(tmpdir, "aif");
		dir.mkdir();
		new File(dir, "FormTimeAif.rss").createNewFile();
		
		resolver = new IPathResolver() {

			public IPath resolvePath(String pathStr) {
				IPath path = FileUtils.createPossiblyRelativePath(pathStr);
				IPath tmpdirPath = new Path(tmpdir.getAbsolutePath());
				if (path.isAbsolute()) {
					IPath badprojPath = new Path(BAD_PROJECT_ROOT);
					if (badprojPath.isPrefixOf(path)) {
						path = tmpdirPath.append(path.removeFirstSegments(badprojPath.segmentCount()).setDevice(null));
					}
					try {
						path = new Path(path.toFile().getCanonicalPath());
					} catch (IOException e) {
					}
				}
				return path;
			}
		};
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		tmpdir.delete();
		tmpMbmdef.delete();
		super.tearDown();
	}
	
	
	static final String AIFDEF_1 =
		BAD_PROJECT_ROOT + "|aif/formtimeaif.rss|c4,1|aif/context_pane_icon.bmp|aif/context_pane_icon_mask.bmp|aif/list_icon.bmp|aif/list_icon_mask.bmp\r\n" + 
		"";
	
	public void testAifdef1() throws CoreException {
		IAifDefFileConverter conv = LegacyImageFileConverterFactory.createAifdefConverter();
		IMMPAIFInfo aifInfo = conv.convert(new Path("resource/apps/Foo"), 
				"Foo.aifdef", AIFDEF_1, resolver);
		assertNotNull(aifInfo);
		assertEquals(new Path("Foo.aif"), aifInfo.getTarget());
		// we still use full paths but these will be project-relative in workspace
		assertEquals(new Path(new File(tmpdir, "aif/FormTimeAif.rss").getAbsolutePath()), aifInfo.getResource());
		List<IBitmapSourceReference> sources = aifInfo.getSourceBitmaps();
		assertEquals(2, sources.size());
		
		assertTrue(aifInfo.isColor());
		assertEquals(4, aifInfo.getColorDepth());
		assertEquals(1, aifInfo.getMaskDepth());
		
		IBitmapSourceReference src;
		
		src = sources.get(0);
		assertTrue(src.isValid());
		assertEquals(new Path("aif/context_pane_icon.bmp"), src.getPath());
		assertEquals(new Path("aif/context_pane_icon_mask.bmp"), src.getMaskPath());
		
		src = sources.get(1);
		assertTrue(src.isValid());
		assertEquals(new Path("aif/list_icon.bmp"), src.getPath());
		assertEquals(new Path("aif/list_icon_mask.bmp"), src.getMaskPath());

		assertTrue(aifInfo.isValid());

	}
	
	
	public void testAifdefWithMbmdef1() throws CoreException {
		// variant that has an .mbmdef 
		
		String AIFDEF_W_MBMDEF_1 =
			"\r\n"+
			tmpdir.getAbsolutePath() + "/|aif/FormTimeAif.rss|" + tmpMbmdef.getName() + "\r\n" + 
			"\r\n"+
			"";
		String AIFDEF_MBMDEF_1 = 
			"\r\n"+
			"c8,8|aif/first.bmp|aif/first_mask.bmp"+
			"\r\n";
			
		FileUtils.writeFileContents(tmpMbmdef, AIFDEF_MBMDEF_1.toCharArray(), null);
		
		IAifDefFileConverter conv = LegacyImageFileConverterFactory.createAifdefConverter();
		IMMPAIFInfo aifInfo = conv.convert(new Path("resource/apps/Foo"), 
				"Foo.aifdef", AIFDEF_W_MBMDEF_1, resolver);
		assertNotNull(aifInfo);
		assertEquals(new Path("Foo.aif"), aifInfo.getTarget());
		assertEquals(new Path(tmpdir.getAbsolutePath()+"/aif/FormTimeAif.rss"), aifInfo.getResource());
		List<IBitmapSourceReference> sources = aifInfo.getSourceBitmaps();
		assertEquals(1, sources.size());
		
		assertTrue(aifInfo.isColor());
		assertEquals(8, aifInfo.getColorDepth());
		assertEquals(8, aifInfo.getMaskDepth());
		
		IBitmapSourceReference src;
		
		src = sources.get(0);
		assertTrue(src.isValid());
		assertEquals(new Path("aif/first.bmp"), src.getPath());
		assertEquals(new Path("aif/first_mask.bmp"), src.getMaskPath());
	
		assertTrue(aifInfo.isValid());

	}	

	public void testAifdefWithMbmdef2() throws CoreException {
		// variant that has an .mbmdef and multiple depths
		
		String AIFDEF_W_MBMDEF_2 =
			"\r\n"+
			tmpdir.getAbsolutePath() + "/|aif/FormTimeAif.rss|" + tmpMbmdef.getName() + "\r\n" + 
			"\r\n"+
			"";
		String AIFDEF_MBMDEF_2 = 
			"\r\n"+
			"c8,8|aif/first.bmp|aif/first_mask.bmp\r\n"+
			"c4|gfx/second.bmp"+
			"\r\n";
			
		FileUtils.writeFileContents(tmpMbmdef, AIFDEF_MBMDEF_2.toCharArray(), null);
		
		IAifDefFileConverter conv = LegacyImageFileConverterFactory.createAifdefConverter();
		IMMPAIFInfo aifInfo = conv.convert(new Path("resource/apps/Foo"), 
				"Foo.aifdef", AIFDEF_W_MBMDEF_2, resolver);
		assertNotNull(aifInfo);
		assertEquals(new Path("Foo.aif"), aifInfo.getTarget());
		assertEquals(new Path(tmpdir.getAbsolutePath()+"/aif/FormTimeAif.rss"), aifInfo.getResource());
		List<IBitmapSourceReference> sources = aifInfo.getSourceBitmaps();
		
		// toss out non-masked entry
		assertEquals(1, sources.size());
		
		assertTrue(aifInfo.isColor());
		assertEquals(8, aifInfo.getColorDepth());
		assertEquals(8, aifInfo.getMaskDepth());
		
		IBitmapSourceReference src;
		
		src = sources.get(0);
		assertTrue(src.isValid());
		assertEquals(new Path("aif/first.bmp"), src.getPath());
		assertEquals(new Path("aif/first_mask.bmp"), src.getMaskPath());
	
		assertTrue(aifInfo.isValid());

	}
	
	public void testAifdefWithMbmdef3() throws CoreException {
		// variant that has an .mbmdef and multiple depths
		
		String AIFDEF_W_MBMDEF_3 =
			"\r\n"+
			tmpdir.getAbsolutePath() + "/|aif/FormTimeAif.rss|" + tmpMbmdef.getName() + "\r\n" + 
			"\r\n"+
			"";
		String AIFDEF_MBMDEF_3 = 
			"\r\n"+
			"c8|aif/first.bmp\r\n"+
			"c4,1|gfx/second.bmp|gfx/second_mask.bmp\r\n"+
			"\r\n";
			
		FileUtils.writeFileContents(tmpMbmdef, AIFDEF_MBMDEF_3.toCharArray(), null);
		
		IAifDefFileConverter conv = LegacyImageFileConverterFactory.createAifdefConverter();
		IMMPAIFInfo aifInfo = conv.convert(new Path("resource/apps/Foo"), 
				"Foo.aifdef", AIFDEF_W_MBMDEF_3,resolver);
		assertNotNull(aifInfo);
		assertEquals(new Path("Foo.aif"), aifInfo.getTarget());
		assertEquals(new Path(tmpdir.getAbsolutePath()+"/aif/FormTimeAif.rss"), aifInfo.getResource());
		List<IBitmapSourceReference> sources = aifInfo.getSourceBitmaps();
		
		assertEquals(2, sources.size());
		
		assertTrue(aifInfo.isColor());
		assertEquals(8, aifInfo.getColorDepth());
		assertEquals(0, aifInfo.getMaskDepth());
		
		IBitmapSourceReference src;
		
		src = sources.get(0);
		assertTrue(src.isValid());
		assertEquals(new Path("aif/first.bmp"), src.getPath());
		assertNull(src.getMaskPath());
	
		src = sources.get(1);
		assertTrue(src.isValid());
		assertEquals(new Path("gfx/second.bmp"), src.getPath());
		assertEquals(new Path("gfx/second_mask.bmp"), src.getMaskPath());

		assertTrue(aifInfo.isValid());

	}
}
