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

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.image.IImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.ISVGSource;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.IMbmMifDefFileConverter;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.IPathResolver;
import com.nokia.carbide.internal.api.cpp.epoc.engine.image.LegacyImageFileConverterFactory;
import com.nokia.cpp.internal.api.utils.core.FileUtils;


public class TestLegacyImageFileConversion extends BaseTest {
	static final String MBMDEF_1 = 
		"\r\n"+
		"c12,1|gfx/list_icon.bmp|gfx/list_icon_mask.bmp\r\n" + 
		"\r\n"+
		"c4|gfx/bitmap1.bmp\r\n";
	private IPathResolver resolver;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		resolver = new IPathResolver() {

			public IPath resolvePath(String pathString) {
				IPath path = FileUtils.createPossiblyRelativePath(pathString);
				IPath resolvedPath = path; 
				return resolvedPath;
			}
		};
	}
	
	public void testConvertMbmdef1() throws CoreException {
		IMbmMifDefFileConverter conv = LegacyImageFileConverterFactory.createMbmdefConverter();
		IMultiImageSource source = conv.convert(new Path("resource/apps/Foo"), "Foo_mbm.mbmdef", MBMDEF_1, resolver);
		assertNotNull(source);
		assertNull(source.getGeneratedHeaderFilePath());
		assertEquals(new Path("epoc32/include/Foo_mbm.mbg"),
				source.getDefaultGeneratedHeaderFilePath());
		assertEquals(EGeneratedHeaderFlags.Header, source.getHeaderFlags());
		assertEquals("Foo_mbm.mbm", source.getTargetFile());
		assertEquals(new Path("resource/apps/Foo"), source.getTargetPath());
		List<IImageSource> sources = source.getSources();
		assertEquals(2, sources.size());
		
		IImageSource src;
		
		src = sources.get(0);
		assertTrue(src instanceof IBitmapSource);
		assertEquals(true, src.isColor());
		assertEquals(12, src.getDepth());
		assertEquals(1, src.getMaskDepth());
		assertEquals(new Path("gfx/list_icon.bmp"), src.getPath());
		assertEquals(new Path("gfx/list_icon_mask.bmp"), ((IBitmapSource) src).getMaskPath());
		
		src = sources.get(1);
		assertTrue(src instanceof IBitmapSource);
		assertEquals(true, src.isColor());
		assertEquals(4, src.getDepth());
		assertEquals(0, src.getMaskDepth());
		assertEquals(new Path("gfx/bitmap1.bmp"), src.getPath());
		assertNull(((IBitmapSource) src).getMaskPath());
		
	}

	static final String MBMDEF_2 = 
		"c12,1|c:/pix/aif/list_icon.bmp|list_icon_mask.bmp\r\n" + 
		"c4,1|aif/train4_1.bmp|aif/train4_1_mask.bmp\r\n" + 
		"c24,8|aif/train24_8.bmp|aif/train24_8_mask.bmp\r\n" + 
		"";
	public void testConvertMbmdef2() throws CoreException {
		IMbmMifDefFileConverter conv = LegacyImageFileConverterFactory.createMbmdefConverter();
		IMultiImageSource source = conv.convert(new Path("resource/apps/Foo"),
				"F.mbmdef", MBMDEF_2, resolver);
		assertNotNull(source);
		assertNull(source.getGeneratedHeaderFilePath());
		assertEquals(new Path("epoc32/include/F.mbg"),
				source.getDefaultGeneratedHeaderFilePath());
		assertEquals(EGeneratedHeaderFlags.Header, source.getHeaderFlags());
		assertEquals("F.mbm", source.getTargetFile());
		assertEquals(new Path("resource/apps/Foo"), source.getTargetPath());
		List<IImageSource> sources = source.getSources();
		assertEquals(3, sources.size());
		
		IImageSource src;
		
		src = sources.get(0);
		assertTrue(src instanceof IBitmapSource);
		assertEquals(true, src.isColor());
		assertEquals(12, src.getDepth());
		assertEquals(1, src.getMaskDepth());
		assertEquals(new Path("c:/pix/aif/list_icon.bmp"), src.getPath());
		assertEquals(new Path("list_icon_mask.bmp"), ((IBitmapSource) src).getMaskPath());
		
		src = sources.get(1);
		assertTrue(src instanceof IBitmapSource);
		assertEquals(true, src.isColor());
		assertEquals(4, src.getDepth());
		assertEquals(1, src.getMaskDepth());
		assertEquals(new Path("aif/train4_1.bmp"), src.getPath());
		assertEquals(new Path("aif/train4_1_mask.bmp"), ((IBitmapSource) src).getMaskPath());
		
		src = sources.get(2);
		assertTrue(src instanceof IBitmapSource);
		assertEquals(true, src.isColor());
		assertEquals(24, src.getDepth());
		assertEquals(8, src.getMaskDepth());
		assertEquals(new Path("aif/train24_8.bmp"), src.getPath());
		assertEquals(new Path("aif/train24_8_mask.bmp"), ((IBitmapSource) src).getMaskPath());
		
	}

	static final String MIFDEF_1 = 
		"\r\n"+
		"c32,8|gfx/qgn_menu_SVG30_2.svg\r\n" + 
		"\r\n"+
		"c32|gfx/shapes.svg\r\n" + 
		"";
	public void testConvertMifdef1() throws CoreException {
		IMbmMifDefFileConverter conv = LegacyImageFileConverterFactory.createMifdefConverter();
		IMultiImageSource source = conv.convert(new Path("sys/resource"),
				"MyProject_mif.mifdef", MIFDEF_1, resolver);
		assertNotNull(source);

		assertEquals(new Path("epoc32/include/MyProject_mif.mbg"),
				source.getGeneratedHeaderFilePath());
		assertEquals(EGeneratedHeaderFlags.Header, source.getHeaderFlags());
		assertEquals("MyProject_mif.mif", source.getTargetFile());
		assertEquals(new Path("sys/resource"), source.getTargetPath());
		List<IImageSource> sources = source.getSources();
		assertEquals(2, sources.size());

		ISVGSource src;
		src = (ISVGSource) sources.get(0);
		assertNotNull(src);
		assertEquals(true, src.isColor());
		assertEquals(32, src.getDepth());
		assertEquals(8, src.getMaskDepth());
		assertEquals(new Path("gfx/qgn_menu_SVG30_2.svg"), src.getPath());
		
		src = (ISVGSource) sources.get(1);
		assertNotNull(src);
		assertEquals(true, src.isColor());
		assertEquals(32, src.getDepth());
		assertEquals(0, src.getMaskDepth());
		assertEquals(new Path("gfx/shapes.svg"), src.getPath());
		
	}
}
