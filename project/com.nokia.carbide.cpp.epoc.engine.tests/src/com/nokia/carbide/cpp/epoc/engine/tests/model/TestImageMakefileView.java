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

package com.nokia.carbide.cpp.epoc.engine.tests.model;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.BasicIncludeFileLocator;
import com.nokia.carbide.internal.cpp.epoc.engine.model.StandaloneModelProvider;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.HostOS;

import org.eclipse.cdt.make.core.makefile.IDirective;
import org.eclipse.cdt.make.core.makefile.gnu.IInclude;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;

import java.io.File;
import java.util.*;

public class TestImageMakefileView extends BaseTest {
	private AcceptedNodesViewFilter viewFilter;
	private ArrayList<IDefine> macros;
	private IImageMakefileViewConfiguration viewConfig;
	private IPath path;
	private IImageMakefileOwnedModel model;
	private File tempDir;
		
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		this.tempDir = FileUtils.getTemporaryDirectory();
		File tempMkDir = new File(tempDir, "makefiles");
		FileUtils.delTree(tempMkDir);
		tempMkDir.mkdirs();
		projectPath = new Path(tempMkDir.getAbsolutePath());
		
		super.setUp();
		
		ignoreWhiteSpaceInRefTest = true;
		
		this.path = projectPath.append("/group/Icons_scalable_dc.mk");
		
		viewFilter = new AcceptedNodesViewFilter();
		macros = new ArrayList<IDefine>();
		viewConfig = new IImageMakefileViewConfiguration() {

			public IViewFilter getViewFilter() {
				return viewFilter;
			}

			public Collection<IDefine> getMacros() {
				return macros;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}

			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileViewConfiguration#getMakefileStyle()
			 */
			public String getMakefileStyle() {
				return "GNU";
			}

			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileViewConfiguration#getImageBuilderCommandLineConverter()
			 */
			public IImageBuilderCommandLineConverter getImageBuilderCommandLineConverter() {
				return ImageBuilderCommandLineConverterFactory.createMifConvConverter();
			}

			public String getImageBuilderName() {
				return "mifconv";
			}
		};

	}
	
	protected void makeModel(String text) {
		text = convertForOS(text);
		IDocument document = DocumentFactory.createDocument(text);
		model = new ImageMakefileModelFactory().createModel(path, document);

		model.parse();
	}

	private String convertForOS(String text) {
		// TODO: tests should work with both slash directions on both OSes
		if (HostOS.IS_UNIX) {
			text = text.replaceAll("\\\\(?!\r|\n)", "/");
			text = text.replaceAll("(?<=\\s|^)/([A-Z])", "-$1"); 
			text = text.replaceAll("(?<=\\s|^)/(c?(\\d|,)+)", "-$1");
		}
		return text;
	}
	
	protected IImageMakefileView getView(IImageMakefileViewConfiguration config) {
		IImageMakefileView view = (IImageMakefileView) model.createView(config);
		assertNotNull(view);
		return view;
	}
	
	protected void commitTest(IImageMakefileView view, String refText) {
		refText = convertForOS(refText);
		commitTest(model, view, refText);
	}
	
	private static IPath stockRootedProjectPath;
	private static IPath stockRootedImgPath;
	private static IPath stockRootedSvgPath;
	
	static {
		if (HostOS.IS_WIN32) {
			stockRootedProjectPath = new Path("c:\\symbian\\9.1\\S60_3rd\\S60Ex\\Hello");
			stockRootedImgPath = new Path("c:\\imgs\\");
			stockRootedSvgPath = new Path("c:\\svgs\\");
		} else {
			stockRootedProjectPath = new Path("/home/me/Hello");
			stockRootedImgPath = new Path("/tmp/imgs/");
			stockRootedSvgPath = new Path("/tmp/svgs/");
		}
	}
	
	final String TEST_1 =
		"# ============================================================================\r\n" + 
		"#  Name     : Icons_aif_scalable_dc.mk\r\n" + 
		"#  Part of  : Birthdays\r\n" + 
		"#\r\n" + 
		"#  Description:\r\n" + 
		"# \r\n" + 
		"# ============================================================================\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
		"else\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\data\\z\r\n" + 
		"endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"# TODO: Configure these\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
		"ICONTARGETFILENAME=$(TARGETDIR)\\Birthdays_aif.mif\r\n" + 
		"\r\n" + 
		"ICONDIR=..\\aif\r\n" + 
		"\r\n" + 
		"do_nothing :\r\n" + 
		"	@rem do_nothing\r\n" + 
		"\r\n" + 
		"MAKMAKE : do_nothing\r\n" + 
		"\r\n" + 
		"BLD : do_nothing\r\n" + 
		"\r\n" + 
		"CLEAN : do_nothing\r\n" + 
		"\r\n" + 
		"LIB : do_nothing\r\n" + 
		"\r\n" + 
		"CLEANLIB : do_nothing\r\n" + 
		"\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"# TODO: Configure these.\r\n" + 
		"#\r\n" + 
		"# NOTE 1: DO NOT DEFINE MASK FILE NAMES! They are included automatically by\r\n" + 
		"# MifConv if the mask depth is defined.\r\n" + 
		"#\r\n" + 
		"# NOTE 2: Usually, source paths should not be included in the bitmap\r\n" + 
		"# definitions. MifConv searches for the icons in all icon directories in a\r\n" + 
		"# predefined order, which is currently \\s60\\icons, \\s60\\bitmaps2.\r\n" + 
		"# The directory \\s60\\icons is included in the search only if the feature flag\r\n" + 
		"# __SCALABLE_ICONS is defined.\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"RESOURCE :	\r\n" + 
		"	mifconv $(ICONTARGETFILENAME) \\\r\n" + 
		"		/c32 $(ICONDIR)\\qgn_menu_Birthdays.svg\r\n" + 
		"		\r\n" + 
		"FREEZE : do_nothing\r\n" + 
		"\r\n" + 
		"SAVESPACE : do_nothing\r\n" + 
		"\r\n" + 
		"RELEASABLES :\r\n" + 
		"	@echo $(ICONTARGETFILENAME)\r\n" + 
		"\r\n" + 
		"FINAL : do_nothing\r\n" + 
		"\r\n"; 

	final String TEST_1_1 =
		"# ============================================================================\r\n" + 
		"#  Name     : Icons_aif_scalable_dc.mk\r\n" + 
		"#  Part of  : Birthdays\r\n" + 
		"#\r\n" + 
		"#  Description:\r\n" + 
		"# \r\n" + 
		"# ============================================================================\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
		"else\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\data\\z\r\n" + 
		"endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"# TODO: Configure these\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
		"ICONTARGETFILENAME=$(TARGETDIR)\\Birthdays_aif.mif\r\n" + 
		"\r\n" + 
		"ICONDIR=..\\aif\r\n" + 
		"\r\n" + 
		"do_nothing :\r\n" + 
		"	@rem do_nothing\r\n" + 
		"\r\n" + 
		"MAKMAKE : do_nothing\r\n" + 
		"\r\n" + 
		"BLD : do_nothing\r\n" + 
		"\r\n" + 
		"CLEAN : do_nothing\r\n" + 
		"\r\n" + 
		"LIB : do_nothing\r\n" + 
		"\r\n" + 
		"CLEANLIB : do_nothing\r\n" + 
		"\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"# TODO: Configure these.\r\n" + 
		"#\r\n" + 
		"# NOTE 1: DO NOT DEFINE MASK FILE NAMES! They are included automatically by\r\n" + 
		"# MifConv if the mask depth is defined.\r\n" + 
		"#\r\n" + 
		"# NOTE 2: Usually, source paths should not be included in the bitmap\r\n" + 
		"# definitions. MifConv searches for the icons in all icon directories in a\r\n" + 
		"# predefined order, which is currently \\s60\\icons, \\s60\\bitmaps2.\r\n" + 
		"# The directory \\s60\\icons is included in the search only if the feature flag\r\n" + 
		"# __SCALABLE_ICONS is defined.\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"RESOURCE :	\r\n" +
		"	mifconv $(ICONTARGETFILENAME) \\\r\n" + 
		"		/c32 $(ICONDIR)\\qgn_menu_Birthdays.svg \\\r\n" +
		"		/c32,8 \"$(ICONDIR)\\spacey another.svg\"\r\n" + 
		"		\r\n" + 
		"FREEZE : do_nothing\r\n" + 
		"\r\n" + 
		"SAVESPACE : do_nothing\r\n" + 
		"\r\n" + 
		"RELEASABLES :\r\n" + 
		"	@echo $(ICONTARGETFILENAME)\r\n" + 
		"\r\n" + 
		"FINAL : do_nothing\r\n" + 
		"\r\n"; 

	final String TEST_1_2 =
		"# ============================================================================\r\n" + 
		"#  Name     : Icons_aif_scalable_dc.mk\r\n" + 
		"#  Part of  : Birthdays\r\n" + 
		"#\r\n" + 
		"#  Description:\r\n" + 
		"# \r\n" + 
		"# ============================================================================\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
		"else\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\data\\z\r\n" + 
		"endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"# TODO: Configure these\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
		"ICONTARGETFILENAME=$(TARGETDIR)\\Birthdays_aif.mif\r\n" + 
		"\r\n" + 
		"ICONDIR=..\\aif\r\n" + 
		"\r\n" + 
		"do_nothing :\r\n" + 
		"	@rem do_nothing\r\n" + 
		"\r\n" + 
		"MAKMAKE : do_nothing\r\n" + 
		"\r\n" + 
		"BLD : do_nothing\r\n" + 
		"\r\n" + 
		"CLEAN : do_nothing\r\n" + 
		"\r\n" + 
		"LIB : do_nothing\r\n" + 
		"\r\n" + 
		"CLEANLIB : do_nothing\r\n" + 
		"\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"# TODO: Configure these.\r\n" + 
		"#\r\n" + 
		"# NOTE 1: DO NOT DEFINE MASK FILE NAMES! They are included automatically by\r\n" + 
		"# MifConv if the mask depth is defined.\r\n" + 
		"#\r\n" + 
		"# NOTE 2: Usually, source paths should not be included in the bitmap\r\n" + 
		"# definitions. MifConv searches for the icons in all icon directories in a\r\n" + 
		"# predefined order, which is currently \\s60\\icons, \\s60\\bitmaps2.\r\n" + 
		"# The directory \\s60\\icons is included in the search only if the feature flag\r\n" + 
		"# __SCALABLE_ICONS is defined.\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"RESOURCE :	\r\n" +
		"	mifconv $(ICONTARGETFILENAME) \\\r\n" + 
		"		/1 ..\\gfx\\dot.bmp \\\r\n" +
		"		/c32 $(ICONDIR)\\qgn_menu_Birthdays.svg \\\r\n" +
		"		/c32,8 \"$(ICONDIR)\\spacey another.svg\"\r\n" + 
		"		\r\n" + 
		"FREEZE : do_nothing\r\n" + 
		"\r\n" + 
		"SAVESPACE : do_nothing\r\n" + 
		"\r\n" + 
		"RELEASABLES :\r\n" + 
		"	@echo $(ICONTARGETFILENAME)\r\n" + 
		"\r\n" + 
		"FINAL : do_nothing\r\n" + 
		"\r\n"; 

	final String TEST_1_3 =
		"# ============================================================================\r\n" + 
		"#  Name     : Icons_aif_scalable_dc.mk\r\n" + 
		"#  Part of  : Birthdays\r\n" + 
		"#\r\n" + 
		"#  Description:\r\n" + 
		"# \r\n" + 
		"# ============================================================================\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
		"else\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\data\\z\r\n" + 
		"endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"# TODO: Configure these\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
		"ICONTARGETFILENAME=$(TARGETDIR)\\Birthdays_aif.mif\r\n" + 
		"\r\n" + 
		"ICONDIR=..\\aif\r\n" + 
		"\r\n" + 
		"do_nothing :\r\n" + 
		"	@rem do_nothing\r\n" + 
		"\r\n" + 
		"MAKMAKE : do_nothing\r\n" + 
		"\r\n" + 
		"BLD : do_nothing\r\n" + 
		"\r\n" + 
		"CLEAN : do_nothing\r\n" + 
		"\r\n" + 
		"LIB : do_nothing\r\n" + 
		"\r\n" + 
		"CLEANLIB : do_nothing\r\n" + 
		"\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"# TODO: Configure these.\r\n" + 
		"#\r\n" + 
		"# NOTE 1: DO NOT DEFINE MASK FILE NAMES! They are included automatically by\r\n" + 
		"# MifConv if the mask depth is defined.\r\n" + 
		"#\r\n" + 
		"# NOTE 2: Usually, source paths should not be included in the bitmap\r\n" + 
		"# definitions. MifConv searches for the icons in all icon directories in a\r\n" + 
		"# predefined order, which is currently \\s60\\icons, \\s60\\bitmaps2.\r\n" + 
		"# The directory \\s60\\icons is included in the search only if the feature flag\r\n" + 
		"# __SCALABLE_ICONS is defined.\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"RESOURCE :	\r\n" +
		"	mifconv $(ICONTARGETFILENAME) \\\r\n" + 
		"		/1 ..\\gfx\\dot.bmp \\\r\n" +
		"		/c32 $(ICONDIR)\\qgn_menu_Birthdays.svg \\\r\n" +
		"		/c32,8 $(ICONDIR)\\another2.svg \\\r\n" +
		"		\"$(ICONDIR)\\spacey another.svg\"\r\n" + 
		"		\r\n" + 
		"FREEZE : do_nothing\r\n" + 
		"\r\n" + 
		"SAVESPACE : do_nothing\r\n" + 
		"\r\n" + 
		"RELEASABLES :\r\n" + 
		"	@echo $(ICONTARGETFILENAME)\r\n" + 
		"\r\n" + 
		"FINAL : do_nothing\r\n" + 
		"\r\n"; 

	final String TEST_1_4 =
		"# ============================================================================\r\n" + 
		"#  Name     : Icons_aif_scalable_dc.mk\r\n" + 
		"#  Part of  : Birthdays\r\n" + 
		"#\r\n" + 
		"#  Description:\r\n" + 
		"# \r\n" + 
		"# ============================================================================\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
		"else\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\data\\z\r\n" + 
		"endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"# TODO: Configure these\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
		"ICONTARGETFILENAME=$(TARGETDIR)\\Birthdays_aif.mif\r\n" + 
		"\r\n" + 
		"ICONDIR=..\\aif\r\n" + 
		"\r\n" + 
		"do_nothing :\r\n" + 
		"	@rem do_nothing\r\n" + 
		"\r\n" + 
		"MAKMAKE : do_nothing\r\n" + 
		"\r\n" + 
		"BLD : do_nothing\r\n" + 
		"\r\n" + 
		"CLEAN : do_nothing\r\n" + 
		"\r\n" + 
		"LIB : do_nothing\r\n" + 
		"\r\n" + 
		"CLEANLIB : do_nothing\r\n" + 
		"\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"# TODO: Configure these.\r\n" + 
		"#\r\n" + 
		"# NOTE 1: DO NOT DEFINE MASK FILE NAMES! They are included automatically by\r\n" + 
		"# MifConv if the mask depth is defined.\r\n" + 
		"#\r\n" + 
		"# NOTE 2: Usually, source paths should not be included in the bitmap\r\n" + 
		"# definitions. MifConv searches for the icons in all icon directories in a\r\n" + 
		"# predefined order, which is currently \\s60\\icons, \\s60\\bitmaps2.\r\n" + 
		"# The directory \\s60\\icons is included in the search only if the feature flag\r\n" + 
		"# __SCALABLE_ICONS is defined.\r\n" + 
		"# ----------------------------------------------------------------------------\r\n" + 
		"\r\n" + 
		"RESOURCE :	\r\n" +
		"\t\r\n"+
		"	mifconv $(ZDIR)\\system\\apps\\Birthdays_aif.mif \\\r\n" + 
		"		/1 ..\\gfx\\dot.bmp \\\r\n" +
		"		/c32 $(ICONDIR)\\qgn_menu_Birthdays.svg \\\r\n" +
		"		/c32,8 $(ICONDIR)\\another2.svg \\\r\n" +
		"		\"$(ICONDIR)\\spacey another.svg\"\r\n" + 
		"FREEZE : do_nothing\r\n" + 
		"\r\n" + 
		"SAVESPACE : do_nothing\r\n" + 
		"\r\n" + 
		"RELEASABLES :\r\n" + 
		"	@echo $(ICONTARGETFILENAME)\r\n" + 
		"\r\n" + 
		"FINAL : do_nothing\r\n" + 
		"\r\n"; 
	public void testBasicParsing() {
		makeModel(TEST_1);
		IImageMakefileView view = (IImageMakefileView) model.createView(viewConfig);
		assertNotNull(view);
		assertNotNull(view.getData());
		commitTest(view, TEST_1);
	}
	
	public void testImageContainerParsing1() {
		makeModel(TEST_1);
		IImageMakefileView view = (IImageMakefileView) model.createView(viewConfig);

		_testImageContainerParsing1(view);
		_testImageContainerParsing1(view.getData());
	}

	private void _testImageContainerParsing1(IImageMakefileData data) {
		List<IMultiImageSource> mis = data.getMultiImageSources();
		assertEquals(1, mis.size());
		
		IMultiImageSource c = mis.get(0);
		assertEquals(EGeneratedHeaderFlags.NoHeader, c.getHeaderFlags());
		assertNull(c.getGeneratedHeaderFilePath());
		assertEquals("Birthdays_aif.mif", c.getTargetFile());
		assertEquals(new Path("resource/apps"),
				c.getTargetPath());
		
		List<IImageSource> sources = c.getSources();
		assertEquals(1, sources.size());
		ISVGSource s = (ISVGSource) sources.get(0);
		assertTrue(s.isValid());
		assertEquals(32, s.getDepth());
		assertEquals(0, s.getMaskDepth());
		assertEquals(new Path("aif/qgn_menu_Birthdays.svg"), s.getPath());
	}

	public void testImageChanging0() {
		final String TXT = "RESOURCE:\n"+
		"\tmifconv foo.mif /c8,8 bmp.bmp /c32 file.svg\n";
		final String TXT2 = "RESOURCE:\n"+
		"\tmifconv foo.mif \\\n\t/c8,1 bmp.bmp \\\n\t/c32 file.svg\n";

		makeModel(TXT);
		
		IImageMakefileView view = (IImageMakefileView) model.createView(viewConfig);

		List<IMultiImageSource> mis = view.getMultiImageSources();
		assertEquals(1, mis.size());
		
		IMultiImageSource c = mis.get(0);
		assertEquals("foo.mif", c.getTargetFile());
		
		List<IImageSource> sources = c.getSources();
		assertEquals(2, sources.size());
		IBitmapSource b = (IBitmapSource) sources.get(0);
		assertTrue(b.isValid());
		assertEquals(8, b.getDepth());
		assertEquals(8, b.getMaskDepth());
		assertEquals(new Path("group/bmp.bmp"), b.getPath());
		assertNull(b.getMaskPath());
		assertEquals(new Path("group/bmp_mask_soft.bmp"), b.getDefaultMaskPath());
		
		ISVGSource s = (ISVGSource) sources.get(1);
		assertTrue(s.isValid());
		assertEquals(32, s.getDepth());
		assertEquals(0, s.getMaskDepth());
		assertEquals(new Path("group/file.svg"), s.getPath());

		// changing depth but mask path is null, so no explicit path required
		b.setMaskDepth(1);
		commitTest(view, TXT2);
		
		////////
		
		c = mis.get(0);
		sources = c.getSources();
		b = (IBitmapSource) sources.get(0);

		// setting explicit mask, needs explicit command 
		b.setMaskPath(new Path("group/bmp_mask_soft.bmp"));

		final String TXT3 = "RESOURCE:\n"+
		"\tmifconv foo.mif \\\n\t/c8 bmp.bmp /1 bmp_mask_soft.bmp \\\n\t/c32 file.svg\n";
		commitTest(view, TXT3);

		////////
		
		c = mis.get(0);
		sources = c.getSources();
		b = (IBitmapSource) sources.get(0);

		// default matches expected 
		b.setMaskPath(new Path("group/bmp_mask_soft.bmp"));
		b.setMaskDepth(8);

		// remove extra entry
		sources.remove(1);
		
		final String TXT4 = "RESOURCE:\n"+
		"\tmifconv foo.mif \\\n\t/c8,8 bmp.bmp \\\n\t/c32 file.svg\n";
		commitTest(view, TXT4);
	}
	
	final String TEST_2 =
		"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
		"else\r\n" + 
		"ZDIR=$(EPOCROOT)epoc32\\data\\z\r\n" + 
		"endif\r\n" + 
		"\r\n" + 
		"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
		"ICONTARGETFILENAME=$(TARGETDIR)\\Birthdays_aif.mif\r\n" +
		"HEADERDIR=$(EPOCROOT)epoc32\\include\r\n"+
		"\r\n" + 
		"ICONDIR=..\\aif\r\n" + 
		"\r\n" + 
		"$(ICONTARGETFILENAME): $(ICONDIR)\\icon1.svg $(ICONDIR)\\icon2.svg\r\n" + 
		"	mifconv $< /E /Bbmconv.exe /H$(HEADERDIR)/Birthdays_aif.mbg /c16 $@\r\n" + 
		"	mifconv extra.mif /c8,8 /Fbitmaps.txt\r\n" + 
		"FREEZE : do_nothing\r\n" + 
		"\r\n" + 
		"SAVESPACE : do_nothing\r\n" + 
		"\r\n" + 
		"RELEASABLES :\r\n" + 
		"	@echo $(ICONTARGETFILENAME)\r\n" + 
		"\r\n" + 
		"FINAL : do_nothing\r\n" + 
		"\r\n"; 
		
	final String TEST_2_inc =
		"\t../pix/bitmap1.bmp\t\nbitmap2.bmp\\\n/1,1\n"+stockRootedImgPath.append("dot.bmp")+"\t\n";
	
	public void testImageContainerParsing2()throws Exception {
		File incFile = new File(projectPath.toFile(), "group/bitmaps.txt");
		incFile.getParentFile().mkdir();
		incFile.delete();
		FileUtils.writeFileContents(incFile, convertForOS(TEST_2_inc).toCharArray(), null);
		
		makeModel(TEST_2);
		IImageMakefileView view = (IImageMakefileView) model.createView(viewConfig);

		_testImageContainerParsing2(view);
		_testImageContainerParsing2(view.getData());
		
	}

	private void _testImageContainerParsing2(IImageMakefileData data) {
		List<IMultiImageSource> mis = data.getMultiImageSources();
		assertEquals(2, mis.size());
		
		///////
		IMultiImageSource c = mis.get(0);
		assertEquals(EGeneratedHeaderFlags.Header, c.getHeaderFlags());
		// default value not represented
		assertNull(c.getGeneratedHeaderFilePath());
		assertEquals(new Path("epoc32/include/Birthdays_aif.mbg"), c.getDefaultGeneratedHeaderFilePath());
		assertEquals("Birthdays_aif.mif", c.getTargetFile());
		assertEquals(new Path("resource/apps"),
				c.getTargetPath());
		
		List<IImageSource> sources = c.getSources();
		assertEquals(2, sources.size());
		ISVGSource s = (ISVGSource) sources.get(0);
		assertTrue(s.isValid());
		assertTrue(s.isColor());
		assertEquals(16, s.getDepth());
		assertEquals(0, s.getMaskDepth());
		assertEquals(new Path("aif/icon1.svg"), s.getPath());
		
		s = (ISVGSource) sources.get(1);
		assertTrue(s.isValid());
		assertTrue(s.isColor());
		assertEquals(16, s.getDepth());
		assertEquals(0, s.getMaskDepth());
		assertEquals(new Path("aif/icon2.svg"), s.getPath());
		
		/////
		c = mis.get(1);
		assertEquals(EGeneratedHeaderFlags.NoHeader, c.getHeaderFlags());
		assertNull(c.getGeneratedHeaderFilePath());

		assertEquals("extra.mif", c.getTargetFile());
		assertEquals(new Path(""), c.getTargetPath());
		
		sources = c.getSources();
		assertEquals(3, sources.size());

		IBitmapSource b = (IBitmapSource) sources.get(0);
		assertTrue(b.isValid());
		assertTrue(b.isColor());
		assertEquals(8, b.getDepth());
		assertEquals(8, b.getMaskDepth());
		assertEquals(new Path("pix/bitmap1.bmp"), b.getPath());
		assertNull(b.getMaskPath());
		assertEquals(new Path("pix/bitmap1_mask_soft.bmp"), b.getDefaultMaskPath());
		
		b = (IBitmapSource) sources.get(1);
		assertTrue(b.isValid());
		assertTrue(b.isColor());
		assertEquals(8, b.getDepth());
		assertEquals(8, b.getMaskDepth());
		assertEquals(new Path("group/bitmap2.bmp"), b.getPath());
		assertNull(b.getMaskPath());
		assertEquals(new Path("group/bitmap2_mask_soft.bmp"), b.getDefaultMaskPath());
		
		b = (IBitmapSource) sources.get(2);
		assertTrue(b.isValid());
		assertFalse(b.isColor());
		assertEquals(1, b.getDepth());
		assertEquals(1, b.getMaskDepth());
		assertEquals(stockRootedImgPath.append("dot.bmp"), b.getPath());
		assertNull(b.getMaskPath());
		assertEquals(stockRootedImgPath.append("dot_mask.bmp"), b.getDefaultMaskPath());
	}

	// alternate style uses /epoc32 directly
 	final String TEST_3 = 
 		"# ============================================================================\r\n" + 
 			"#  Name     : Icons_aif_scalable_dc.mk\r\n" + 
 			"#  Part of  : Birthdays\r\n" + 
 			"#\r\n" + 
 			"#  Description:\r\n" + 
 			"# \r\n" + 
 			"# ============================================================================\r\n" + 
 			"\r\n" + 
 			"\r\n" + 
 			"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
 			"ZDIR=\\epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
 			"else\r\n" + 
 			"ZDIR=\\epoc32\\data\\z\r\n" + 
 			"endif\r\n" + 
 			"\r\n" + 
 			"\r\n" + 
 			"# ----------------------------------------------------------------------------\r\n" + 
 			"# TODO: Configure these\r\n" + 
 			"# ----------------------------------------------------------------------------\r\n" + 
 			"\r\n" + 
 			"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
 			"ICONTARGETFILENAME=$(TARGETDIR)\\Birthdays_aif.mif\r\n" +
 			"\r\n" + 
 			"HEADERDIR=\\epoc32\\include\r\n" + 
 			"HEADERFILENAME=$(HEADERDIR)\\Birthdays_aif.mbg\r\n" + 
 			"\r\n" + 
 			"ICONDIR=..\\gfx\r\n" + 
 			"\r\n" + 
 			"do_nothing :\r\n" + 
 			"	@rem do_nothing\r\n" + 
 			"\r\n" + 
 			"MAKMAKE : do_nothing\r\n" + 
 			"\r\n" + 
 			"BLD : do_nothing\r\n" + 
 			"\r\n" + 
 			"CLEAN : do_nothing\r\n" + 
 			"\r\n" + 
 			"LIB : do_nothing\r\n" + 
 			"\r\n" + 
 			"CLEANLIB : do_nothing\r\n" + 
 			"\r\n" + 
 			"RESOURCE :	\r\n" + 
 			"	mifconv $(ICONTARGETFILENAME) /H$(HEADERFILENAME) \\\r\n" + 
 			"		/c32 $(ICONDIR)\\qgn_menu_Birthdays.svg\r\n" + 
 			"		\r\n" + 
 			"FREEZE : do_nothing\r\n" + 
 			"\r\n" + 
 			"SAVESPACE : do_nothing\r\n" + 
 			"\r\n" + 
 			"RELEASABLES :\r\n" + 
 			"	@echo $(ICONTARGETFILENAME)\r\n" + 
 			"\r\n" + 
 			"FINAL : do_nothing\r\n" + 
 			"\r\n" + 
 			""; 
		
	public void testImageContainerParsing3()throws Exception {
		makeModel(TEST_3);
		IImageMakefileView view = (IImageMakefileView) model.createView(viewConfig);

		_testImageContainerParsing3(view);
		_testImageContainerParsing3(view.getData());
		
	}

	private void _testImageContainerParsing3(IImageMakefileData data) {
		List<IMultiImageSource> mis = data.getMultiImageSources();
		assertEquals(1, mis.size());
		
		///////
		IMultiImageSource c = mis.get(0);
		assertEquals(EGeneratedHeaderFlags.Header, c.getHeaderFlags());
		assertEquals(new Path("/epoc32/include/Birthdays_aif.mbg"), c.getGeneratedHeaderFilePath());
		assertEquals("Birthdays_aif.mif", c.getTargetFile());
		assertEquals(new Path("/epoc32/data/z/resource/apps"),
				c.getTargetPath());
		
		List<IImageSource> sources = c.getSources();
		assertEquals(1, sources.size());
		ISVGSource s = (ISVGSource) sources.get(0);
		assertTrue(s.isValid());
		assertTrue(s.isColor());
		assertEquals(32, s.getDepth());
		assertEquals(0, s.getMaskDepth());
		assertEquals(new Path("gfx/qgn_menu_Birthdays.svg"), s.getPath());
	}
	
	// no extension --> svg
 	final String TEST_4 = 
 			"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
 			"ZDIR=/epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
 			"else\r\n" + 
 			"ZDIR=\\epoc32\\data\\z\r\n" + 
 			"endif\r\n" + 
 			"\r\n" + 
 			"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
 			"ICONTARGETFILENAME=$(TARGETDIR)\\Birthdays_aif.mif\r\n" +
 			"\r\n" + 
 			"HEADERDIR=\\epoc32\\include\r\n" + 
 			"HEADERFILENAME=$(HEADERDIR)\\Birthdays_aif.mbg\r\n" + 
 			"\r\n" + 
 			"ICONDIR=..\\gfx\r\n" + 
 			"RESOURCE :	\r\n" + 
 			"	mifconv $(ICONTARGETFILENAME) /H$(HEADERFILENAME) \\\r\n" + 
 			"		/c32 $(ICONDIR)\\qgn_menu_Birthdays\r\n" + 
 			"		\r\n" + 
 			""; 
	public void testImageContainerParsing4()throws Exception {
		makeModel(TEST_4);
		IImageMakefileView view = (IImageMakefileView) model.createView(viewConfig);

		_testImageContainerParsing4(view);
		_testImageContainerParsing4(view.getData());
		
	}

	private void _testImageContainerParsing4(IImageMakefileData data) {
		List<IMultiImageSource> mis = data.getMultiImageSources();
		assertEquals(1, mis.size());
		
		///////
		IMultiImageSource c = mis.get(0);
		List<IImageSource> sources = c.getSources();
		assertEquals(1, sources.size());
		ISVGSource s = (ISVGSource) sources.get(0);
		assertTrue(s.isValid());
		assertTrue(s.isColor());
		assertEquals(32, s.getDepth());
		assertEquals(0, s.getMaskDepth());
		assertEquals(new Path("gfx/qgn_menu_Birthdays"), s.getPath());
	}
	
	public void testImageChanging1() {
		makeModel(TEST_1);
		IImageMakefileView view = (IImageMakefileView) model.createView(viewConfig);

		IMultiImageSource mis = view.getMultiImageSources().get(0);
		ISVGSource img = mis.createSVGSource();
		img.setPath(new Path("aif/spacey another.svg"));
		img.setColor(true);
		img.setDepth(32);
		img.setMaskDepth(8);
		
		mis.getSources().add(img);
		commitTest(view, TEST_1_1);
		
		////
		mis = view.getMultiImageSources().get(0);
		assertEquals(2, mis.getSources().size());
		
		assertTrue(mis.getSources().contains(img));
		commitTest(view, TEST_1_1);
		///
		
		mis = view.getMultiImageSources().get(0);
		IBitmapSource bmp = mis.createBitmapSource();
		bmp.setPath(new Path("gfx/dot.bmp"));
		bmp.setColor(false);
		bmp.setDepth(1);
		bmp.setMaskDepth(0);
		mis.getSources().add(0, bmp);
		commitTest(view, TEST_1_2);
		
		///
		mis = view.getMultiImageSources().get(0);
		img = mis.createSVGSource();
		img.setPath(new Path("aif/another2.svg"));
		img.setColor(true);
		img.setDepth(32);
		img.setMaskDepth(8);
		mis.getSources().add(2, img);
		commitTest(view, TEST_1_3);
		
		///
		mis = view.getMultiImageSources().get(0);
		mis.setTargetPath(new Path("system/apps"));
		commitTest(view, TEST_1_4);
		
	}
	
	final String TEST_CREATE_NEW =
		"$(EPOCROOT)epoc32\\release\\winscw\\udeb\\sys\\bin\\miffile.mif: \\\r\n" + 
		"\t\t..\\aif\\icon1.svg\r\n" + 
		" mifconv $(EPOCROOT)epoc32\\release\\winscw\\udeb\\sys\\bin\\miffile.mif \\\r\n" + 
		" /H$(EPOCROOT)epoc32\\include\\miffile.mbg \\\r\n" + 
		" /c32,8 ..\\aif\\icon1.svg\r\n" + 
		"";
	
	public void testCreateNew() {
		makeModel("");
		
		IImageMakefileView view = (IImageMakefileView) model.createView(viewConfig);
		assertEquals(0, view.getAllMacroDefinitions().length);
		assertEquals(0, view.getMultiImageSources().size());

		
		IMultiImageSource mis = view.createMultiImageSource();
		mis.setHeaderFlags(EGeneratedHeaderFlags.Header);
		mis.setTargetPath(new Path("epoc32/release/winscw/udeb/sys/bin"));
		mis.setTargetFile("miffile.mif");
		
		view.getMultiImageSources().add(mis);
		
		ISVGSource img = mis.createSVGSource();
		img.setPath(new Path("aif/icon1.svg"));
		img.setColor(true);
		img.setDepth(32);
		img.setMaskDepth(8);
		
		mis.getSources().add(img);
		commitTest(view, TEST_CREATE_NEW);
		
	}

	final String TEST_BMP_EXPLICIT_MASK =
		"$(EPOCROOT)epoc32\\release\\winscw\\udeb\\sys\\bin\\miffile.mif: \\\r\n" + 
		"\t\t..\\aif\\bitmap.bmp \\\r\n" + 
		"\t\t..\\aif\\mask.bmp\r\n" + 
		" mifconv $(EPOCROOT)epoc32\\release\\winscw\\udeb\\sys\\bin\\miffile.mif \\\r\n" + 
		" /c32 ..\\aif\\bitmap.bmp /8 ..\\aif\\mask.bmp\r\n" + 
		"";
	
	public void testBmpExplicitMask() {
		makeModel("");
		
		IImageMakefileView view = (IImageMakefileView) model.createView(viewConfig);
		assertEquals(0, view.getAllMacroDefinitions().length);
		assertEquals(0, view.getMultiImageSources().size());
		
		IMultiImageSource mis = view.createMultiImageSource();
		mis.setTargetPath(new Path("epoc32/release/winscw/udeb/sys/bin"));
		mis.setTargetFile("miffile.mif");
		
		view.getMultiImageSources().add(mis);
		
		IBitmapSource bmp = mis.createBitmapSource();
		bmp.setPath(new Path("aif/bitmap.bmp"));
		bmp.setMaskPath(new Path("aif/mask.bmp"));
		bmp.setColor(true);
		bmp.setDepth(32);
		bmp.setMaskDepth(8);
		
		mis.getSources().add(bmp);
		commitTest(view, TEST_BMP_EXPLICIT_MASK);
		
	}
	
	public void testBadCmdline() {
		// incorrect options, so no images detected -- don't crash!
		makeModel("RESOURCE:\n"+
				"\tmifconv hello.mif /m8,8 ../gfx/file.svg\n");
			
		IImageMakefileView view =model.createView(this.viewConfig);
		assertEquals(0, view.getMultiImageSources().get(0).getSources().size());
	}
	
	public void testRootProject() {
		// make sure we handle project root correctly when it's the root directory
		for (int i = 0; i < 2; i++){
			if (HostOS.IS_WIN32)
				parserConfig.projectPath = i == 0 ? new Path("c:\\") : new Path("c:");
			else
				parserConfig.projectPath = new Path("/");
			this.path = stockRootedProjectPath.append("/group/Icons.mk");
			makeModel("RESOURCE:\n"+
					"\tmifconv hello.mif /c8,8 ../gfx/file.svg\n");
			
			IImageMakefileView view =model.createView(this.viewConfig);
			_testRootProject(view);
			_testRootProject(view.getData());
		}
	}

	private void _testRootProject(IImageMakefileData imageMakefileData) {
		IImageSource source = imageMakefileData.getMultiImageSources().get(0).getSources().get(0);
		// important to be relative
		IPath relBase;
		relBase = stockRootedProjectPath.makeRelative().setDevice(null);
		assertEquals(relBase.append("/gfx/file.svg"), source.getPath());
	}
	
	public void testMifconvBugs() {
		makeModel("RESOURCE:\n"+
		"\tmifconv hello.mif /c8,8 "+stockRootedImgPath.append("foo.bmp").toOSString()
		+" /c32 "+stockRootedSvgPath.append("foo.svg") + "\n");
		IImageMakefileView view =model.createView(this.viewConfig);

		IImageSource source = view.getMultiImageSources().get(0).getSources().get(0);
		// BMPs can have drive letter on input...
		assertEquals(stockRootedImgPath.append("foo.bmp"), source.getPath());
		source.setMaskDepth(1);

		source = view.getMultiImageSources().get(0).getSources().get(1);
		// SVGs can have drive letter on input...
		assertEquals(stockRootedSvgPath.append("foo.svg"), source.getPath());

		// drop drive letters on output for bmps
		IPath outBmpPath = stockRootedImgPath.append("foo.bmp").setDevice(null);
		IPath outSvgPath = stockRootedSvgPath.append("foo.svg");
		String refText = "RESOURCE:\n"+
		"\tmifconv hello.mif \\\n\t/c8,1 "+HostOS.convertPathToWindows(outBmpPath) + " " 
		+ "\\\n\t/c32 " + HostOS.convertPathToWindows(outSvgPath) + "\n";
		commitTest(view, refText);
		
		
	}
	
	public void testDeleteTargets() {
		makeModel("output.mif:\n"+
				"\tmifconv output.mif\n");
		
		IImageMakefileView view = model.createView(this.viewConfig);
		assertEquals(1, view.getMultiImageSources().size());
		
		// remove the mis.  Since it's its own target, this should delete the rule
		view.getMultiImageSources().clear();
		
		commitTest(view, "");
		
	}
	public void testDefaultTargets() {
		makeModel("RESOURCE:\n");
		
		IImageMakefileView view = model.createView(this.viewConfig);
		IMultiImageSource mis = view.createMultiImageSource();
		mis.setTargetFile("output.mif");
		view.getMultiImageSources().add(mis);
		
		// no default
		commitTest(view, "RESOURCE:\n"+
					"output.mif:\n"+
					"\tmifconv output.mif\n");
		
		view.setDefaultImageTarget("RESOURCE");
		view.getMultiImageSources().clear();
		commitTest(view, "RESOURCE:\n");
		
		view.getMultiImageSources().add(mis);
		
		// new default
		commitTest(view, "RESOURCE:\n"+
				"\tmifconv output.mif\n");
		
	}
	
	public void testUpdateMultiImageSource() {
		// ensure we detect that the file is changed
		makeModel("ICONDIR=..\\aif\n"+
				"RESOURCE:\n"+
				"\tmifconv $(EPOCROOT)path/to/foo.mif /c32 $(ICONDIR)\\file.svg\n");
		IImageMakefileView view = model.createView(this.viewConfig);
		IMultiImageSource mis = view.getMultiImageSources().get(0);
		IMultiImageSource repl = view.createMultiImageSource();
		repl.setTargetFile(mis.getTargetFile());
		repl.setTargetPath(mis.getTargetPath());
		ISVGSource svg = repl.createSVGSource();
		svg.setColor(true);
		svg.setDepth(32);
		svg.setPath(new Path("gfx/file.svg"));
		repl.getSources().add(svg);
		
		view.getMultiImageSources().remove(mis);
		view.getMultiImageSources().add(repl);
		
		commitTest(view,
				"ICONDIR=..\\aif\n"+
				"RESOURCE:\n"+
				"\tmifconv $(EPOCROOT)path\\to\\foo.mif \\\n\t/c32 ..\\gfx\\file.svg\n");
	}
	
	public void testRepeatedEpocRoot() {
		String common1 = "# ============================================================================\r\n" + 
				"#  Name     : Icons_aif_scalable_dc.mk\r\n" + 
				"#  Part of  : ImgTest\r\n" + 
				"#\r\n" + 
				"#  Description:\r\n" + 
				"# \r\n" + 
				"# ============================================================================\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
				"ZDIR=$(EPOCROOT)epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
				"else\r\n" + 
				"ZDIR=$(EPOCROOT)epoc32\\data\\z\r\n" + 
				"endif\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"# ----------------------------------------------------------------------------\r\n" + 
				"# TODO: Configure these\r\n" + 
				"# ----------------------------------------------------------------------------\r\n" + 
				"\r\n" + 
				"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
				"ICONTARGETFILENAME=$(TARGETDIR)\\ImgTest_aif.mif\r\n" + 
				"\r\n" + 
				"ICONDIR=..\\gfx\r\n" + 
				"\r\n" + 
				"do_nothing :\r\n" + 
				"	@rem do_nothing\r\n" + 
				"\r\n" + 
				"MAKMAKE : do_nothing\r\n" + 
				"\r\n" + 
				"BLD : do_nothing\r\n" + 
				"\r\n" + 
				"CLEAN : do_nothing\r\n" + 
				"\r\n" + 
				"LIB : do_nothing\r\n" + 
				"\r\n" + 
				"CLEANLIB : do_nothing\r\n" + 
				"\r\n";
		String origCmd1 =
				"RESOURCE :	\r\n" + 
				"	mifconv $(ICONTARGETFILENAME) \\\r\n" + 
				"		 /H$(EPOCROOT)epoc32\\include\\ImgTest_aif.mbg /c32 $(ICONDIR)\\qgn_menu_ImgTest.svg  \\\r\n" + 
				"		 /c8,8 $(ICONDIR)\\foo.svg  \\\r\n" + 
				"		 /c24,1 $(ICONDIR)\\mark_icon.bmp  \\\r\n" + 
				"		 /c24 ..\\cursor.bmp";
		String editedCmd = 
			"RESOURCE :	\r\n" + 
			"	mifconv $(ICONTARGETFILENAME) \\\r\n" + 
			"		 /H$(EPOCROOT)epoc32\\include\\ImgTest_aif.mbg \\\r\n"+
			"		/c32 $(ICONDIR)\\qgn_menu_ImgTest.svg  \\\r\n" + 
			"		 /c8,8 $(ICONDIR)\\foo.svg  \\\r\n" + 
			"		 /c24,1 $(ICONDIR)\\mark_icon.bmp  \\\r\n" + 
			"		 /c24 ..\\cursor.bmp";
		String common2 = 
				"		 $(ICONDIR)\\cursor.bmp\r\n" + 
				"		\r\n" + 
				"FREEZE : do_nothing\r\n" + 
				"\r\n" + 
				"SAVESPACE : do_nothing\r\n" + 
				"\r\n" + 
				"RELEASABLES :\r\n" + 
				"	@echo $(ICONTARGETFILENAME)\r\n" + 
				"\r\n" + 
				"FINAL : do_nothing\r\n" + 
				"\r\n"; 

			String origText = common1 + origCmd1 + "  \\\r\n" +
			"		 $(ICONDIR)\\cursor.bmp\r\n" +
			common2;
			
			String modText = common1 + editedCmd + "\r\n" + common2;

			makeModel(origText);

			IImageMakefileView view = model.createView(this.viewConfig);
			
			view.getMultiImageSources().get(0).getSources().remove(4);

			// should only do what we asked, not fudge the /H line
			commitTest(view, modText);
	}
	public void testRepeatedEpocRootVariant2() {
		String common1 = "# ============================================================================\r\n" + 
				"#  Name     : Icons_aif_scalable_dc.mk\r\n" + 
				"#  Part of  : ImgTest\r\n" + 
				"#\r\n" + 
				"#  Description:\r\n" + 
				"# \r\n" + 
				"# ============================================================================\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
				"ZDIR=$(EPOCROOT)epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
				"else\r\n" + 
				"ZDIR=$(EPOCROOT)epoc32\\data\\z\r\n" + 
				"endif\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"# ----------------------------------------------------------------------------\r\n" + 
				"# TODO: Configure these\r\n" + 
				"# ----------------------------------------------------------------------------\r\n" + 
				"\r\n" + 
				"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
				"ICONTARGETFILENAME=$(TARGETDIR)\\ImgTest_aif.mif\r\n" + 
				"\r\n" + 
				"ICONDIR=..\\gfx\r\n" + 
				"\r\n" + 
				"do_nothing :\r\n" + 
				"	@rem do_nothing\r\n" + 
				"\r\n" + 
				"MAKMAKE : do_nothing\r\n" + 
				"\r\n" + 
				"BLD : do_nothing\r\n" + 
				"\r\n" + 
				"CLEAN : do_nothing\r\n" + 
				"\r\n" + 
				"LIB : do_nothing\r\n" + 
				"\r\n" + 
				"CLEANLIB : do_nothing\r\n" + 
				"\r\n";
		String origCmd =
				"RESOURCE :	\r\n" + 
				"	mifconv $(ICONTARGETFILENAME) \\\r\n" + 
				"		 /H$(TARGETDIR)\\ImgTest_aif.mbg /c32 $(ICONDIR)\\qgn_menu_ImgTest.svg  \\\r\n" + 
				"		 /c8,8 $(ICONDIR)\\foo.svg  \\\r\n" + 
				"		 /c24,1 $(ICONDIR)\\mark_icon.bmp  \\\r\n" + 
				"		 /c24 ..\\cursor.bmp";
		String editedCmd =
			"RESOURCE :	\r\n" + 
			"	mifconv $(ICONTARGETFILENAME) \\\r\n" + 
			"		 /H$(TARGETDIR)\\ImgTest_aif.mbg \\\r\n"+
			"		/c32 $(ICONDIR)\\qgn_menu_ImgTest.svg  \\\r\n" + 
			"		 /c8,8 $(ICONDIR)\\foo.svg  \\\r\n" + 
			"		 /c24,1 $(ICONDIR)\\mark_icon.bmp  \\\r\n" + 
			"		 /c24 ..\\cursor.bmp";
		
		String common2 = 
				"		 $(ICONDIR)\\cursor.bmp\r\n" + 
				"		\r\n" + 
				"FREEZE : do_nothing\r\n" + 
				"\r\n" + 
				"SAVESPACE : do_nothing\r\n" + 
				"\r\n" + 
				"RELEASABLES :\r\n" + 
				"	@echo $(ICONTARGETFILENAME)\r\n" + 
				"\r\n" + 
				"FINAL : do_nothing\r\n" + 
				"\r\n"; 

			String origText = common1 + origCmd + "  \\\r\n" +
			"		 $(ICONDIR)\\cursor.bmp\r\n" +
			common2;
			
			String modText = common1 + editedCmd + "\r\n" + common2;

			makeModel(origText);

			IImageMakefileView view = model.createView(this.viewConfig);
			
			view.getMultiImageSources().get(0).getSources().remove(4);

			// should only do what we asked, not fudge the /H line
			commitTest(view, modText);
	}
	
	public void testDanglingCatenations() {
		makeModel("target:\n"+
				"\tmifconv foo.mif\n"+
				"\tmifconv bar.mif \\\n"+
				"\n"+
				"another:\n");
		IImageMakefileView view = model.createView(this.viewConfig);
		view.getMultiImageSources().remove(0);
		commitTest(view, "target:\n"+
				"\tmifconv bar.mif \\\n"+
				"\n"+
				"another:\n");

		commitTest(view, "target:\n"+
				"\tmifconv bar.mif \\\n"+
				"\n"+
				"another:\n");

		IMultiImageSource mis = view.createMultiImageSource();
		mis.setTargetFile("baz.mif");
		view.getMultiImageSources().add(mis);
		commitTest(view, "target:\n"+
				"\tmifconv bar.mif \\\n"+
				"\n"+
				"\tmifconv baz.mif\n"+
				"another:\n");
		
		assertEquals(2, view.getMultiImageSources().size());
		assertEquals(2, view.getMakefile().getRules().length);
		
		view.getMultiImageSources().remove(0);
		view.getMultiImageSources().remove(0);
		assertEquals(0, view.getMultiImageSources().size());
		
		commitTest(view, "target:\n"+
				"another:\n");
		
	}
	
	public void testPreserveUnknownOptions() {
		makeModel("target:\n"+
				"\t# NOTE: if you have JUSTINTIME enabled for your S60 3rd FP1 or newer SDK\r\n" + 
				"\t# and this command crashes, consider adding \"/X\" to the command line.\r\n" + 
				"\t# See <http://forum.nokia.com/document/Forum_Nokia_Technical_Library_v1_35/contents/FNTL/Build_process_fails_at_mif_file_creation_in_S60_3rd_Ed_FP1_SDK.htm>\r\n" + 
				"\tmifconv foo.mif /X /GOO /B /Smyencode.exe /H\\noob.h /c32,8 foo.svg\n");
		IImageMakefileView view = model.createView(this.viewConfig);
		IMultiImageSource mis = view.getMultiImageSources().get(0);
		mis.getSources().get(0).setColor(false);
		commitTest(view, "target:\n"+
			"\t# NOTE: if you have JUSTINTIME enabled for your S60 3rd FP1 or newer SDK\r\n" + 
			"\t# and this command crashes, consider adding \"/X\" to the command line.\r\n" + 
			"\t# See <http://forum.nokia.com/document/Forum_Nokia_Technical_Library_v1_35/contents/FNTL/Build_process_fails_at_mif_file_creation_in_S60_3rd_Ed_FP1_SDK.htm>\r\n" + 
			"\tmifconv foo.mif \\\n"+
			"\t\t/H\\noob.h /X /GOO /B /Smyencode.exe \\\n"+
			"\t\t/32,8 foo.svg\n");

		
	}

	public void testPreserveUnknownOptionsAndExpandingParameterFile() throws Exception {
		String bitmapFile = "/c8,8 file1.bmp file2.bmp";
		File incFile = new File(projectPath.toFile(), "group/bitmaps.txt");
		incFile.getParentFile().mkdir();
		incFile.delete();
		FileUtils.writeFileContents(incFile, convertForOS(bitmapFile).toCharArray(), null);

		makeModel("target:\n"+
				"\tmifconv foo.mif /X /GOO /B /Smyencode.exe /H\\noob.h /Fbitmaps.txt");
		IImageMakefileView view = model.createView(this.viewConfig);
		assertEquals(1, view.getMultiImageSources().size()); 
		IMultiImageSource mis = view.getMultiImageSources().get(0);
		assertEquals(2, mis.getSources().size());
		mis.getSources().get(0).setColor(false);
		
		// drops the /F option and expands contents
		commitTest(view, "target:\n"+
			"\tmifconv foo.mif \\\n"+
			"\t\t/H\\noob.h /X /GOO /B /Smyencode.exe \\\n"+
			"\t\t/8,8 file1.bmp \\\n"+
			"\t\t/c8,8 file2.bmp\n");

		
	}

	public void testAddingToEmptyTarget() {
		// ensure the comments aren't lost
		makeModel("RESOURCE:\n" +
				"	# NOTE: if you have JUSTINTIME enabled for your S60 3rd FP1 or newer SDK\r\n" + 
				"	# and this command crashes, consider adding \"/X\" to the command line.\r\n" + 
				"	# See <http://forum.nokia.com/document/Forum_Nokia_Technical_Library_v1_35/contents/FNTL/Build_process_fails_at_mif_file_creation_in_S60_3rd_Ed_FP1_SDK.htm>\r\n" 
				);
		IImageMakefileView view = model.createView(this.viewConfig);

		IMultiImageSource mis = view.createMultiImageSource();
		mis.setTargetFile("foo.mif");
		ISVGSource img = mis.createSVGSource();
		img.setPath(new Path("file.svg"));
		img.setColor(true);
		img.setDepth(32);
		img.setMaskDepth(8);

		view.setDefaultImageTarget("RESOURCE");
		view.getMultiImageSources().add(mis);
		mis.getSources().add(img);
		commitTest(view, "RESOURCE:\n"+
				"\t# NOTE: if you have JUSTINTIME enabled for your S60 3rd FP1 or newer SDK\r\n" + 
				"\t# and this command crashes, consider adding \"/X\" to the command line.\r\n" + 
				"\t# See <http://forum.nokia.com/document/Forum_Nokia_Technical_Library_v1_35/contents/FNTL/Build_process_fails_at_mif_file_creation_in_S60_3rd_Ed_FP1_SDK.htm>\r\n" + 
				"\tmifconv foo.mif \\\n"+
				"\t\t/c32,8 ..\\file.svg\n");

	}
	
	public void testIncludes_3180() throws Exception {
		String text = "\r\n" + 
				"SECURITY_FLAG=on\r\n" + 
				"SCALABLE_FLAG=on\r\n" + 
				"\r\n" + 
				"include Icons_generic.mk\r\n" + 
				"";
		String incl = "MIFICONS:\r\n" + 
				"	mifconv foo.mif \\\r\n" + 
				"		/c8,1 qgn_prop_nrtyp_date\r\n";

		File incFile = new File(projectPath.toFile(), "inc/Icons_generic.mk");
		incFile.getParentFile().mkdir();
		incFile.delete();
		FileUtils.writeFileContents(incFile, convertForOS(incl).toCharArray(), null);
		
		File normFile = new File(projectPath.toFile(), "group/Icons_scalable.mk");
		normFile.getParentFile().mkdir();
		normFile.delete();
		FileUtils.writeFileContents(normFile, convertForOS(text).toCharArray(), null);

		parserConfig.fileLocator = new BasicIncludeFileLocator(
				new File[] { projectPath.append("inc").toFile() },
				new File[0] );
		path = projectPath.append("group/Icons_scalable.mk");
		IDocument document = DocumentFactory.createDocument(text);
		model = new ImageMakefileModelFactory().createModel(path, document);
		IModelLoadResults res = model.parse();
		assertEquals(0, res.getMessages().length);

		IImageMakefileView view = model.createView(this.viewConfig);
		
		IPath[] referencedFiles = view.getReferencedFiles();
		assertEquals(2, referencedFiles.length);
		
		assertEquals(1, view.getMultiImageSources().size());
	}
	
	/** Test modifications that touch multiple files */
	public void testIncludes_5201() throws Exception {
		String text = "\r\n" + 
				"SECURITY_FLAG=on\r\n" + 
				"SCALABLE_FLAG=on\r\n" + 
				"\r\n" + 
				"include Icons_generic.mk\r\n" + 
				"";
		String incl = "MIFICONS:\r\n" + 
				"	mifconv foo.mif \\\r\n" + 
				"		/c8,1 qgn_prop_nrtyp_date\r\n";

		File incFile = new File(projectPath.toFile(), "inc/Icons_generic.mk");
		incFile.getParentFile().mkdir();
		incFile.delete();
		FileUtils.writeFileContents(incFile, convertForOS(incl).toCharArray(), null);
		
		File normFile = new File(projectPath.toFile(), "group/Icons_scalable.mk");
		normFile.getParentFile().mkdir();
		normFile.delete();
		FileUtils.writeFileContents(normFile, convertForOS(text).toCharArray(), null);

		parserConfig.fileLocator = new BasicIncludeFileLocator(
				new File[] { projectPath.append("inc").toFile() },
				new File[0] );
		path = projectPath.append("group/Icons_scalable.mk");
		
		IModelFactory<IImageMakefileOwnedModel> factory = new ImageMakefileModelFactory();
		IModelProvider<IImageMakefileOwnedModel, IImageMakefileModel> provider = 
			new StandaloneModelProvider<IImageMakefileOwnedModel, IImageMakefileModel>(factory);
		IImageMakefileModel model = provider.getSharedModel(path);

		IImageMakefileView view = model.createView(this.viewConfig);
		
		IPath[] referencedFiles = view.getReferencedFiles();
		assertEquals(2, referencedFiles.length);
		
		referencedFiles = view.getReferencedFiles();
		assertEquals(2, referencedFiles.length);

		assertEquals(1, view.getMultiImageSources().size());
		
		view.getMultiImageSources().remove(0);
		
		view.commit();
		
		String newText = new String(FileUtils.readFileContents(normFile, null));
		assertEquals(text, newText);
		
		String newIncl = new String(FileUtils.readFileContents(incFile, null));
		assertEquals("MIFICONS:\r\n", newIncl);
		
	}

	/** Test modifications that touch multiple files */
	public void testIncludes_5201_2() throws Exception {
		// in this scenario, we have a busted include, then fix it
		String text = "\r\n" + 
				"SECURITY_FLAG=on\r\n" + 
				"SCALABLE_FLAG=on\r\n" + 
				"\r\n" + 
				"include Icons_generic.mk\r\n" + 
				"";
		String incl = "MIFICONS:\r\n" + 
				"	mifconv foo.mif \\\r\n" + 
				"		/c8,1 qgn_prop_nrtyp_date\r\n";

		File normFile = new File(projectPath.toFile(), "group/Icons_scalable.mk");
		normFile.getParentFile().mkdir();
		normFile.delete();
		FileUtils.writeFileContents(normFile, convertForOS(text).toCharArray(), null);

		parserConfig.fileLocator = new BasicIncludeFileLocator(
				new File[] { projectPath.append("inc").toFile() },
				new File[0] );
		path = projectPath.append("group/Icons_scalable.mk");
		
		IModelFactory<IImageMakefileOwnedModel> factory = new ImageMakefileModelFactory();
		IModelProvider<IImageMakefileOwnedModel, IImageMakefileModel> provider = 
			new StandaloneModelProvider<IImageMakefileOwnedModel, IImageMakefileModel>(factory);
		IImageMakefileModel model = provider.getSharedModel(path);

		// the include doesn't exist
		IImageMakefileView view = model.createView(this.viewConfig);
		
		IPath[] referencedFiles = view.getReferencedFiles();
		assertEquals(1, referencedFiles.length);

		assertEquals(0, view.getMultiImageSources().size());

		view.commit();
		
		// make sure nothing breaks 
		String newText = new String(FileUtils.readFileContents(normFile, null));
		assertEquals(text, newText);
		
		/////////
		
		// make the file
		//
		File incFile = new File(projectPath.toFile(), "inc/Icons_generic.mk");
		incFile.getParentFile().mkdir();
		incFile.delete();
		FileUtils.writeFileContents(incFile, convertForOS(incl).toCharArray(), null);
		
		// revert the view and be sure we can get the file now

		view.revert();
		
		referencedFiles = view.getReferencedFiles();
		assertEquals(2, referencedFiles.length);

		assertEquals(1, view.getMultiImageSources().size());
		view.getMultiImageSources().remove(0);
		
		view.commit();
		
		newText = new String(FileUtils.readFileContents(normFile, null));
		assertEquals(text, newText);
		
		String newIncl = new String(FileUtils.readFileContents(incFile, null));
		assertEquals("MIFICONS:\r\n", newIncl);
		
	}

	/** Test modifications that touch multiple files */
	public void testIncludes_5201_3() throws Exception {
		// in this scenario, remove an include
		String text = "\r\n" + 
				"SECURITY_FLAG=on\r\n" + 
				"SCALABLE_FLAG=on\r\n" + 
				"\r\n" + 
				"include Icons_generic.mk\r\n" + 
				"";
		String incl = "MIFICONS:\r\n" + 
				"	mifconv foo.mif \\\r\n" + 
				"		/c8,1 qgn_prop_nrtyp_date\r\n";

		File normFile = new File(projectPath.toFile(), "group/Icons_scalable.mk");
		normFile.getParentFile().mkdir();
		normFile.delete();
		FileUtils.writeFileContents(normFile, convertForOS(text).toCharArray(), null);

		parserConfig.fileLocator = new BasicIncludeFileLocator(
				new File[] { projectPath.append("inc").toFile() },
				new File[0] );
		path = projectPath.append("group/Icons_scalable.mk");

		File incFile = new File(projectPath.toFile(), "inc/Icons_generic.mk");
		incFile.getParentFile().mkdir();
		incFile.delete();
		FileUtils.writeFileContents(incFile, incl.toCharArray(), null);

		IModelFactory<IImageMakefileOwnedModel> factory = new ImageMakefileModelFactory();
		IModelProvider<IImageMakefileOwnedModel, IImageMakefileModel> provider = 
			new StandaloneModelProvider<IImageMakefileOwnedModel, IImageMakefileModel>(factory);
		IImageMakefileModel model = provider.getSharedModel(path);

		IImageMakefileView view = model.createView(this.viewConfig);
		
		IPath[] referencedFiles = view.getReferencedFiles();
		assertEquals(2, referencedFiles.length);

		assertEquals(1, view.getMultiImageSources().size());
		
		// remove the include, but not the model
		for (IDirective directive : view.getMakefile().getDirectives()) {
			if (directive instanceof IInclude) {
				view.deleteDirective(directive);
				break;
			}
		}
		
		view.commit();

		// the model should go into the main file, and the include removed
		String newText = new String(FileUtils.readFileContents(normFile, null));
		assertEquals(convertForOS("\r\n" + 
				"SECURITY_FLAG=on\r\n" + 
				"SCALABLE_FLAG=on\r\n" + 
				"\r\n" + 
				"foo.mif: \\\r\n" + 
				"		qgn_prop_nrtyp_date\r\n" + 
				"	mifc$(SECURITY_FLAG)v foo.mif  \\\r\n" + 
				"		 /c8,1 qgn_prop_nrtyp_date\r\n" + 
				""), convertForOS(newText));
		
		// the include is unchanged
		String newIncl = new String(FileUtils.readFileContents(incFile, null));
		assertEquals(convertForOS(incl), convertForOS(newIncl));
		
		referencedFiles = view.getReferencedFiles();
		assertEquals(1, referencedFiles.length);
		
	}

	public void testBug3552() {
		// tolerate extra 'c'
		String text = "RESOURCE:\n" +
		"\tmifconv foo.mif /c8,c8 dummy.bmp barty.svg";
		makeModel(text);
		IImageMakefileView view = model.createView(this.viewConfig);

		IMultiImageSource mis = view.getMultiImageSources().get(0);
		assertEquals("foo.mif", mis.getTargetFile());
		assertEquals(2, mis.getSources().size());
		
		IBitmapSource bm = (IBitmapSource) mis.getSources().get(0);
		assertTrue(bm.isColor());
		assertEquals(8, bm.getDepth());
		assertEquals(8, bm.getMaskDepth());
		
		ISVGSource img = (ISVGSource) mis.getSources().get(1);
		assertTrue(img.isColor());
		assertEquals(8, img.getDepth());
		assertEquals(8, img.getMaskDepth());
		
		commitTest(view, text);

		bm.setDepth(32);
		img.setDepth(32);

		String text2 = "RESOURCE:\n" +
		"\tmifconv foo.mif \\\n\t/c32,8 dummy.bmp \\\n\tbarty.svg\n";

		commitTest(view, text2);
	}
	
	/** Carbide templates changed to reflect the source dependencies in the 
	 * target rule.  Be sure those are update and not corrupted.
	 * @throws Exception
	 */
	public void testUpdateDependencies1() throws Exception {
		String text = "RESOURCE: foo.mif\n" +
		"foo.mif: dummy.bmp ../gfx/barty.svg\n"+
		"\tmifconv foo.mif /c8,8 dummy.bmp ../gfx/barty.svg";
		
		makeModel(text);
		IImageMakefileView view = model.createView(this.viewConfig);

		IMultiImageSource mis = view.getMultiImageSources().get(0);
		assertEquals("foo.mif", mis.getTargetFile());
		assertEquals(2, mis.getSources().size());

		ISVGSource svgSource = mis.createSVGSource();
		svgSource.setPath(new Path("excess.svg"));
		svgSource.setColor(true);
		svgSource.setDepth(8);
		svgSource.setMaskDepth(8);
		mis.getSources().add(svgSource);

		String text2 = "RESOURCE: foo.mif\n" + 
				"foo.mif: \\\n" + 
				"\t\tdummy.bmp \\\n" + 
				"\t\t..\\gfx\\barty.svg \\\n" + 
				"\t\t..\\excess.svg\n" + 
				"\tmifconv foo.mif \\\n" + 
				"\t\t/c8,8 dummy.bmp \\\n" + 
				"\t\t..\\gfx\\barty.svg \\\n" + 
				"\t\t..\\excess.svg\n" +
				"";

		commitTest(view, text2);

		mis = view.getMultiImageSources().get(0);
		mis.getSources().remove(0);

		String text3 = "RESOURCE: foo.mif\n" + 
				"foo.mif: \\\n" + 
				" ..\\gfx\\barty.svg \\\n" + 
				" ..\\excess.svg\n" + 
				" mifconv foo.mif \\\n" + 
				" /c8,8 ..\\gfx\\barty.svg \\\n" + 
				" ..\\excess.svg\n" + 
				"";

		commitTest(view, text3);

		
	}
	
	/** Carbide templates changed to reflect the source dependencies in the 
	 * target rule.  Be sure those are update and not corrupted.
	 * @throws Exception
	 */
	public void testUpdateDependencies2() throws Exception {
		String text = "RESOURCE: foo.mif\n" +
		"foo.mif: dummy.bmp ../gfx/barty.svg unrelated.txt\n"+
		"\ttype unrelated.txt\n"+
		"\tmifconv foo.mif /c8,8 dummy.bmp ../gfx/barty.svg";
		
		makeModel(text);
		IImageMakefileView view = model.createView(this.viewConfig);

		IMultiImageSource mis = view.getMultiImageSources().get(0);
		assertEquals("foo.mif", mis.getTargetFile());
		assertEquals(2, mis.getSources().size());

		ISVGSource svgSource = mis.createSVGSource();
		svgSource.setPath(new Path("excess.svg"));
		svgSource.setColor(true);
		svgSource.setDepth(8);
		svgSource.setMaskDepth(8);
		mis.getSources().add(svgSource);

		String text2 = "RESOURCE: foo.mif\n" + 
				"foo.mif: \\\n" + 
				"\t\tdummy.bmp \\\n" + 
				"\t\t..\\gfx\\barty.svg \\\n" + 
				"\t\tunrelated.txt \\\n" + 
				"\t\t..\\excess.svg\n" +
				"\ttype unrelated.txt\n"+
				"\tmifconv foo.mif \\\n" + 
				"\t\t/c8,8 dummy.bmp \\\n" + 
				"\t\t..\\gfx\\barty.svg \\\n" + 
				"\t\t..\\excess.svg\n" +
				"";

		commitTest(view, text2);

		mis = view.getMultiImageSources().get(0);
		mis.getSources().remove(0);

		String text3 = "RESOURCE: foo.mif\n" + 
				"foo.mif: \\\n" + 
				" ..\\gfx\\barty.svg \\\n" + 
				"\tunrelated.txt \\\n" + 
				" ..\\excess.svg\n" + 
				"\ttype unrelated.txt\n"+
				" mifconv foo.mif \\\n" + 
				" /c8,8 ..\\gfx\\barty.svg \\\n" + 
				" ..\\excess.svg\n" + 
				"";

		commitTest(view, text3);

		
	}

	/** This bug is essentially that dependencies got messed up because
	 * we did not properly detect \r\n when trying to parse the dependency line. */ 
	public void testUpdateDependencies3_Bug7280() {
		String text =
			"ICONTARGETFILENAME=\\epoc32\\output\\foo.mif\r\n"+
			"ICONDIR=..\\gfx\r\n"+
			"HEADERFILENAME=\\epoc32\\include\\foo.mbg\r\n"+
			"$(ICONTARGETFILENAME): \\\r\n" + 
			"                $(ICONDIR)\\S60NewsReader.svg \\\r\n" + 
			"                $(ICONDIR)\\checked.svg \\\r\n" + 
			"                $(ICONDIR)\\empty.svg \\\r\n" + 
			"                $(ICONDIR)\\read.svg \\\r\n" + 
			"                $(ICONDIR)\\unread.svg \\\r\n" + 
			"                $(ICONDIR)\\checked_gray.svg\r\n" + 
			"\tmifconv $(ICONTARGETFILENAME)  \\\r\n" + 
			"                 /H$(HEADERFILENAME)  \\\r\n" + 
			"                 /c32,8 $(ICONDIR)\\S60NewsReader.svg  \\\r\n" + 
			"                 $(ICONDIR)\\checked.svg  \\\r\n" + 
			"                 $(ICONDIR)\\empty.svg  \\\r\n" + 
			"                 $(ICONDIR)\\read.svg  \\\r\n" + 
			"                 $(ICONDIR)\\unread.svg  \\\r\n" + 
			"                 $(ICONDIR)\\checked_gray.svg\r\n";

		makeModel(text);
		IImageMakefileView view = model.createView(this.viewConfig);

		IMultiImageSource mis = view.getMultiImageSources().get(0);

		ISVGSource svgSource = mis.createSVGSource();
		svgSource.setPath(new Path("gfx/error.svg"));
		svgSource.setColor(true);
		svgSource.setDepth(32);
		svgSource.setMaskDepth(8);
		mis.getSources().add(svgSource);
		
		String text2 = 
			"ICONTARGETFILENAME=\\epoc32\\output\\foo.mif\r\n"+
			"ICONDIR=..\\gfx\r\n"+
			"HEADERFILENAME=\\epoc32\\include\\foo.mbg\r\n"+
			"$(ICONTARGETFILENAME): \\\r\n" + 
			"                $(ICONDIR)\\S60NewsReader.svg \\\r\n" + 
			"                $(ICONDIR)\\checked.svg \\\r\n" + 
			"                $(ICONDIR)\\empty.svg \\\r\n" + 
			"                $(ICONDIR)\\read.svg \\\r\n" + 
			"                $(ICONDIR)\\unread.svg \\\r\n" + 
			"                $(ICONDIR)\\checked_gray.svg \\\r\n" + 
			"                $(ICONDIR)\\error.svg\r\n" + 
			"\tmifconv $(ICONTARGETFILENAME)  \\\r\n" + 
			"                 /H$(HEADERFILENAME)  \\\r\n" + 
			"                 /c32,8 $(ICONDIR)\\S60NewsReader.svg  \\\r\n" + 
			"                 $(ICONDIR)\\checked.svg  \\\r\n" + 
			"                 $(ICONDIR)\\empty.svg  \\\r\n" + 
			"                 $(ICONDIR)\\read.svg  \\\r\n" + 
			"                 $(ICONDIR)\\unread.svg  \\\r\n" + 
			"                 $(ICONDIR)\\checked_gray.svg  \\\r\n" + 
			"                 $(ICONDIR)\\error.svg\r\n";
		commitTest(view, text2);
		
	}
}
