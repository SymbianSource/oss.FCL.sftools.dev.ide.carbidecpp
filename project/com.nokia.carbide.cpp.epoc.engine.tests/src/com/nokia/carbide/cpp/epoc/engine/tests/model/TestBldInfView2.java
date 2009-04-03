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

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewASTBase;

import org.eclipse.core.runtime.Path;

public class TestBldInfView2 extends BaseBldInfViewTest {

	/**
	 * Ignore reorderings across documents.
	 * Initially bug 2974, but this behavior is suspicious.
	 */
	public void testSplitInOtherDocument() {
		String incFile =
			"PRJ_MMPFILES\n" +
			"first.mmp\n"+
			"second.mmp\n";
		
		String mainFile = "#include \"inc.inf\"\n";
		
		String inclFile = projectPath.append("group/inc.inf").toOSString();
		parserConfig.getFilesystem().put(inclFile, incFile);
		makeModel(mainFile);
		
		IBldInfView view = getView(config);
		
		dumpSourceInfo(((ViewASTBase)view).getFilteredTranslationUnit());
		
		assertEquals(2, view.getMakMakeReferences().size());
		
		IMMPReference ref = view.createMMPReference();
		ref.setPath(new Path("group/new.mmp"));
		view.getMakMakeReferences().add(1, ref);
		
		commitTest(view, 
				"#include \"inc.inf\"\n" + 
				"\n" + 
				"PRJ_MMPFILES\n" + 
				"new.mmp\n"+
				"");
		assertEquals("PRJ_MMPFILES\n" +
				"first.mmp\n"+
				"second.mmp\n"+
				"", 
				parserConfig.getFilesystem().get(inclFile));

		
	}
	
	public void testReorder1() {
		String text= "PRJ_MMPFILES\n"+
		"first.mmp\n"+
		"second.mmp\n"+
		"third.mmp\n"+
		"\n"+
		"PRJ_TESTMMPFILES\n"+
		"unrelated\n"+
		"PRJ_EXPORTS\n"+
		"same\n"+
		"";
		
		makeModel(text);
		IBldInfView view = getView(config);
		
		IMakMakeReference third = view.getMakMakeReferences().remove(2);
		view.getMakMakeReferences().add(0, third);
		
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"third.mmp\n"+
				"first.mmp\n"+
				"second.mmp\n"+
				"\n"+
				"PRJ_TESTMMPFILES\n"+
				"unrelated\n"+
				"PRJ_EXPORTS\n"+
				"same\n"+
				"");
	}
	
	public void testReorder2() {
		String text= "PRJ_MMPFILES\n"+
		"first.mmp\n"+
		"#ifndef MACRO\n"+
		"second.mmp\n"+
		"#endif\n"+
		"third.mmp\n"+
		"\n"+
		"PRJ_TESTMMPFILES\n"+
		"unrelated\n"+
		"PRJ_EXPORTS\n"+
		"same\n"+
		"";
		
		makeModel(text);
		IBldInfView view = getView(config);
		
		IMakMakeReference third = view.getMakMakeReferences().remove(2);
		view.getMakMakeReferences().add(0, third);
		
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"third.mmp\n"+
				"#ifndef MACRO\n"+
				"second.mmp\n"+
				"#endif\n"+
				"first.mmp\n"+
				"\n"+
				"PRJ_TESTMMPFILES\n"+
				"unrelated\n"+
				"PRJ_EXPORTS\n"+
				"same\n"+
				"");
	}
	
	public void testReorder3() {
		String text= "PRJ_MMPFILES\n"+
		"first.mmp\n"+
		"#ifndef MACRO\n"+
		"second.mmp\n"+
		"#endif\n"+
		"third.mmp\n"+
		"\n"+
		"PRJ_TESTMMPFILES\n"+
		"unrelated\n"+
		"related\n"+
		"PRJ_EXPORTS\n"+
		"same\n"+
		"PRJ_MMPFILES\n"+
		"fourth.mmp\n"+
		"fifth.mmp\n"+
		"";
		
		makeModel(text);
		IBldInfView view = getView(config);
		
		IMakMakeReference fifth = view.getMakMakeReferences().remove(4);
		IMakMakeReference fourth = view.getMakMakeReferences().remove(3);
		IMakMakeReference third = view.getMakMakeReferences().remove(2);
		IMakMakeReference first = view.getMakMakeReferences().remove(0);
		view.getMakMakeReferences().add(0, third);
		view.getMakMakeReferences().add(1, fifth);
		view.getMakMakeReferences().add(2, first);
		view.getMakMakeReferences().add(3, fourth);
		
		IMakMakeReference unrelated = view.getTestMakMakeReferences().remove(0);
		view.getTestMakMakeReferences().add(unrelated);
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"third.mmp\n"+
				"#ifndef MACRO\n"+
				"second.mmp\n"+
				"#endif\n"+
				"fifth.mmp\n"+
				"\n"+
				"PRJ_TESTMMPFILES\n"+
				"related\n"+
				"unrelated\n"+
				"PRJ_EXPORTS\n"+
				"same\n"+
				"PRJ_MMPFILES\n"+
				"first.mmp\n"+
				"fourth.mmp\n"+
				"");
	}

	String textBug4039 =  
		"#ifdef CTG_PLATFORM_ER90\r\n" + 
		"PRJ_PLATFORMS\r\n" + 
		"  DEFAULT GCCE\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_MMPFILES\r\n" + 
		"\r\n" + 
		"..\\TALXCommon\\TALXCommon.mmp\r\n" + 
		"#if defined(CTG_S60) && !defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXCommon\\TALXCommonDyn.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXOnOff\\TalxFepReload.mmp\r\n" + 
		"..\\TALXOnOff\\TalxFepUnload.mmp\r\n" + 
		"..\\feptest\\TalxFep.mmp\r\n" + 
		"..\\TALXOnOff\\TALXStartupLaunch.mmp\r\n" + 
		"..\\TALXOnOff\\TALXRasterLaunch.mmp\r\n" + 
		"..\\RasterDll\\TALXRaster.mmp\r\n" + 
		"#else\r\n" + 
		"..\\TALXOnOff\\TALXOn.mmp\r\n" + 
		"..\\TALXOnOff\\TALXOff.mmp\r\n" + 
		"#endif\r\n" + 
		"..\\TALXOnOff\\TALXOnLaunch.mmp\r\n" + 
		"\r\n" + 
		"..\\feptest\\feptest.mmp\r\n" + 
		"\r\n" + 
		"#ifdef CTG_S60 \r\n" + 
		"..\\Settings60\\SettingsApp.mmp\r\n" + 
		"# if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro.mmp\r\n" + 
		"#  if !defined(WINS)\r\n" + 
		"..\\Settings60\\SettingsApp_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro_self.mmp\r\n" + 
		"#  endif\r\n" + 
		"# endif\r\n" + 
		"#else\r\n" + 
		"..\\Settings\\SettingsApp.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"..\\SettingsSrv\\SettingsSrv.mmp\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\SettingsSrv\\SettingsSrv_self.mmp\r\n" + 
		"#endif\r\n" + 
		"#if !defined(CTG_PLATFORM_ER61) && !defined(CTG_PLATFORM_ER60)\r\n" + 
		"// Not built in Symbian 6.x\r\n" + 
		"..\\AnimDll\\TALXAnim.mmp\r\n" + 
		"#endif\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW.mmp\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW_self.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_S60)\r\n" + 
		"..\\test\\feponoff\\group\\feponoff.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if 0\r\n" + 
		"// Autostart Recognizer currently not used\r\n" + 
		"..\\TALXR\\TALXR.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"gnumakefile ..\\Settings60\\icons.mk\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_EXPORTS\r\n" + 
		"\r\n" + 
		"#if !defined(CTG_PLATFORM_ER90)\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\wins\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60\\wins\\udeb\\SwiTtsClient2.LIB ..\\release\\wins\\udeb\\SwiTtsClient2.lib\r\n" + 
		"  ..\\..\\libs\\s60\\armi\\urel\\SwiTtsClient2.LIB ..\\release\\armi\\urel\\SwiTtsClient2.lib\r\n" + 
		"#else\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\winscw\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\winscw\\udeb\\psdk_lmwrapper.lib ..\\release\\winscw\\udeb\\psdk_lmwrapper.lib\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\armv5\\urel\\psdk_lmwrapper_gcce.lib ..\\release\\armv5\\urel\\psdk_lmwrapper.lib\r\n" + 
		"#endif\r\n" + 
		"";

	public void testBug4039_1() {

		String textBug4039_2 = 
		"#ifdef CTG_PLATFORM_ER90\r\n" + 
		"PRJ_PLATFORMS\r\n" + 
		"  DEFAULT GCCE\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_MMPFILES\r\n" + 
		"\r\n" + 
		"..\\TALXCommon\\TALXCommon.mmp\r\n" + 
		"#if defined(CTG_S60) && !defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXCommon\\TALXCommonDyn.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXOnOff\\TalxFepReload.mmp\r\n" + 
		"..\\TALXOnOff\\TalxFepUnload.mmp\r\n" + 
		"..\\feptest\\TalxFep.mmp\r\n" + 
		"..\\TALXOnOff\\TALXRasterLaunch.mmp\r\n" + 
		"..\\TALXOnOff\\TALXStartupLaunch.mmp\r\n" + 
		"..\\RasterDll\\TALXRaster.mmp\r\n" + 
		"#else\r\n" + 
		"..\\TALXOnOff\\TALXOn.mmp\r\n" + 
		"..\\TALXOnOff\\TALXOff.mmp\r\n" + 
		"#endif\r\n" + 
		"..\\TALXOnOff\\TALXOnLaunch.mmp\r\n" + 
		"\r\n" + 
		"..\\feptest\\feptest.mmp\r\n" + 
		"\r\n" + 
		"#ifdef CTG_S60 \r\n" + 
		"..\\Settings60\\SettingsApp.mmp\r\n" + 
		"# if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro.mmp\r\n" + 
		"#  if !defined(WINS)\r\n" + 
		"..\\Settings60\\SettingsApp_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro_self.mmp\r\n" + 
		"#  endif\r\n" + 
		"# endif\r\n" + 
		"#else\r\n" + 
		"..\\Settings\\SettingsApp.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"..\\SettingsSrv\\SettingsSrv.mmp\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\SettingsSrv\\SettingsSrv_self.mmp\r\n" + 
		"#endif\r\n" + 
		"#if !defined(CTG_PLATFORM_ER61) && !defined(CTG_PLATFORM_ER60)\r\n" + 
		"// Not built in Symbian 6.x\r\n" + 
		"..\\AnimDll\\TALXAnim.mmp\r\n" + 
		"#endif\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW.mmp\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW_self.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_S60)\r\n" + 
		"..\\test\\feponoff\\group\\feponoff.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if 0\r\n" + 
		"// Autostart Recognizer currently not used\r\n" + 
		"..\\TALXR\\TALXR.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"gnumakefile ..\\Settings60\\icons.mk\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_EXPORTS\r\n" + 
		"\r\n" + 
		"#if !defined(CTG_PLATFORM_ER90)\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\wins\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60\\wins\\udeb\\SwiTtsClient2.LIB ..\\release\\wins\\udeb\\SwiTtsClient2.lib\r\n" + 
		"  ..\\..\\libs\\s60\\armi\\urel\\SwiTtsClient2.LIB ..\\release\\armi\\urel\\SwiTtsClient2.lib\r\n" + 
		"#else\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\winscw\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\winscw\\udeb\\psdk_lmwrapper.lib ..\\release\\winscw\\udeb\\psdk_lmwrapper.lib\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\armv5\\urel\\psdk_lmwrapper_gcce.lib ..\\release\\armv5\\urel\\psdk_lmwrapper.lib\r\n" + 
		"#endif\r\n" + 
		"";

		macros.add(DefineFactory.createSimpleFreeformDefine("CTG_PLATFORM_ER90=1"));
		makeModel(textBug4039);
		IBldInfView view = getView(config);

		IMakMakeReference ref1 = view.getMakMakeReferences().remove(5);
		view.getMakMakeReferences().add(4, ref1);
		
		commitTest(view, textBug4039_2);
		checkNoProblems(view);
	}
	public void testBug4039_1b() {

		String textBug4039_2 = 
		"#ifdef CTG_PLATFORM_ER90\r\n" + 
		"PRJ_PLATFORMS\r\n" + 
		"  DEFAULT GCCE\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_MMPFILES\r\n" + 
		"\r\n" + 
		"..\\TALXCommon\\TALXCommon.mmp\r\n" + 
		"#if defined(CTG_S60) && !defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXCommon\\TALXCommonDyn.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXOnOff\\TalxFepReload.mmp\r\n" + 
		"..\\TALXOnOff\\TalxFepUnload.mmp\r\n" + 
		"..\\feptest\\TalxFep.mmp\r\n" + 
		"..\\TALXOnOff\\TALXStartupLaunch.mmp\r\n" + 
		"..\\TALXOnOff\\TALXRasterLaunch.mmp\r\n" + 
		"..\\RasterDll\\TALXRaster.mmp\r\n" + 
		"#else\r\n" + 
		"..\\TALXOnOff\\TALXOn.mmp\r\n" + 
		"..\\TALXOnOff\\TALXOff.mmp\r\n" + 
		"#endif\r\n" + 
		"..\\feptest\\feptest.mmp\r\n" + 
		"\r\n" + 
		"..\\TALXOnOff\\TALXOnLaunch.mmp\r\n" + 
		"\r\n" + 
		"#ifdef CTG_S60 \r\n" + 
		"..\\Settings60\\SettingsApp.mmp\r\n" + 
		"# if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro.mmp\r\n" + 
		"#  if !defined(WINS)\r\n" + 
		"..\\Settings60\\SettingsApp_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro_self.mmp\r\n" + 
		"#  endif\r\n" + 
		"# endif\r\n" + 
		"#else\r\n" + 
		"..\\Settings\\SettingsApp.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"..\\SettingsSrv\\SettingsSrv.mmp\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\SettingsSrv\\SettingsSrv_self.mmp\r\n" + 
		"#endif\r\n" + 
		"#if !defined(CTG_PLATFORM_ER61) && !defined(CTG_PLATFORM_ER60)\r\n" + 
		"// Not built in Symbian 6.x\r\n" + 
		"..\\AnimDll\\TALXAnim.mmp\r\n" + 
		"#endif\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW.mmp\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW_self.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_S60)\r\n" + 
		"..\\test\\feponoff\\group\\feponoff.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if 0\r\n" + 
		"// Autostart Recognizer currently not used\r\n" + 
		"..\\TALXR\\TALXR.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"gnumakefile ..\\Settings60\\icons.mk\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_EXPORTS\r\n" + 
		"\r\n" + 
		"#if !defined(CTG_PLATFORM_ER90)\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\wins\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60\\wins\\udeb\\SwiTtsClient2.LIB ..\\release\\wins\\udeb\\SwiTtsClient2.lib\r\n" + 
		"  ..\\..\\libs\\s60\\armi\\urel\\SwiTtsClient2.LIB ..\\release\\armi\\urel\\SwiTtsClient2.lib\r\n" + 
		"#else\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\winscw\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\winscw\\udeb\\psdk_lmwrapper.lib ..\\release\\winscw\\udeb\\psdk_lmwrapper.lib\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\armv5\\urel\\psdk_lmwrapper_gcce.lib ..\\release\\armv5\\urel\\psdk_lmwrapper.lib\r\n" + 
		"#endif\r\n" + 
		"";

		makeModel(textBug4039);
		IBldInfView view = getView(config);

		IMakMakeReference ref1 = view.getMakMakeReferences().remove(4);
		view.getMakMakeReferences().add(3, ref1);
		
		commitTest(view, textBug4039_2);
		checkNoProblems(view);
	}
	public void testBug4039_2() {

		String textBug4039_2 = 
		"#ifdef CTG_PLATFORM_ER90\r\n" + 
		"PRJ_PLATFORMS\r\n" + 
		"  DEFAULT GCCE\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_MMPFILES\r\n" + 
		"\r\n" + 
		"..\\TALXCommon\\TALXCommon.mmp\r\n" + 
		"#if defined(CTG_S60) && !defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXCommon\\TALXCommonDyn.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXOnOff\\TalxFepReload.mmp\r\n" + 
		"..\\TALXOnOff\\TalxFepUnload.mmp\r\n" + 
		"..\\feptest\\TalxFep.mmp\r\n" + 
		"..\\TALXOnOff\\TALXStartupLaunch.mmp\r\n" + 
		"..\\TALXOnOff\\TALXRasterLaunch.mmp\r\n" + 
		"..\\RasterDll\\TALXRaster.mmp\r\n" + 
		"#else\r\n" + 
		"..\\TALXOnOff\\TALXOn.mmp\r\n" + 
		"..\\TALXOnOff\\TALXOff.mmp\r\n" + 
		"#endif\r\n" + 
		"..\\feptest\\feptest.mmp MANUAL\r\n" + 
		"\r\n" + 
		"..\\TALXOnOff\\TALXOnLaunch.mmp\r\n" + 
		"\r\n" + 
		"#ifdef CTG_S60 \r\n" + 
		"..\\Settings60\\SettingsApp.mmp\r\n" + 
		"# if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro.mmp\r\n" + 
		"#  if !defined(WINS)\r\n" + 
		"..\\Settings60\\SettingsApp_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro_self.mmp\r\n" + 
		"#  endif\r\n" + 
		"# endif\r\n" + 
		"#else\r\n" + 
		"..\\Settings\\SettingsApp.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"..\\SettingsSrv\\SettingsSrv.mmp\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\SettingsSrv\\SettingsSrv_self.mmp\r\n" + 
		"#endif\r\n" + 
		"#if !defined(CTG_PLATFORM_ER61) && !defined(CTG_PLATFORM_ER60)\r\n" + 
		"// Not built in Symbian 6.x\r\n" + 
		"..\\AnimDll\\TALXAnim.mmp\r\n" + 
		"#endif\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW.mmp\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW_self.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_S60)\r\n" + 
		"..\\test\\feponoff\\group\\feponoff.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if 0\r\n" + 
		"// Autostart Recognizer currently not used\r\n" + 
		"..\\TALXR\\TALXR.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"gnumakefile ..\\Settings60\\icons.mk\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_EXPORTS\r\n" + 
		"\r\n" + 
		"#if !defined(CTG_PLATFORM_ER90)\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\wins\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60\\wins\\udeb\\SwiTtsClient2.LIB ..\\release\\wins\\udeb\\SwiTtsClient2.lib\r\n" + 
		"  ..\\..\\libs\\s60\\armi\\urel\\SwiTtsClient2.LIB ..\\release\\armi\\urel\\SwiTtsClient2.lib\r\n" + 
		"#else\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\winscw\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\winscw\\udeb\\psdk_lmwrapper.lib ..\\release\\winscw\\udeb\\psdk_lmwrapper.lib\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\armv5\\urel\\psdk_lmwrapper_gcce.lib ..\\release\\armv5\\urel\\psdk_lmwrapper.lib\r\n" + 
		"#endif\r\n" + 
		"";

		makeModel(textBug4039);
		IBldInfView view = getView(config);

		// changing one entry while moving it is not a problem
		
		IMakMakeReference ref1 = view.getMakMakeReferences().remove(4);
		ref1.setManual(true);
		view.getMakMakeReferences().add(3, ref1);
		
		commitTest(view, textBug4039_2);
		checkNoProblems(view);
	}

	public void testBug4039_3() {

		String textBug4039_2 = 
		"#ifdef CTG_PLATFORM_ER90\r\n" + 
		"PRJ_PLATFORMS\r\n" + 
		"  DEFAULT GCCE\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_MMPFILES\r\n" + 
		"\r\n" + 
		"..\\TALXCommon\\TALXCommon.mmp MANUAL\r\n" + 
		"#if defined(CTG_S60) && !defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXCommon\\TALXCommonDyn.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXOnOff\\TalxFepReload.mmp\r\n" + 
		"..\\TALXOnOff\\TalxFepUnload.mmp\r\n" + 
		"..\\feptest\\TalxFep.mmp\r\n" + 
		"..\\TALXOnOff\\TALXStartupLaunch.mmp\r\n" + 
		"..\\TALXOnOff\\TALXRasterLaunch.mmp\r\n" + 
		"..\\RasterDll\\TALXRaster.mmp\r\n" + 
		"#else\r\n" + 
		"..\\TALXOnOff\\TALXOn.mmp MANUAL\r\n" + 
		"..\\TALXOnOff\\TALXOff.mmp MANUAL\r\n" + 
		"#endif\r\n" + 
		"..\\feptest\\feptest.mmp MANUAL\r\n" + 
		"\r\n" + 
		"..\\TALXOnOff\\TALXOnLaunch.mmp MANUAL\r\n" + 
		"\r\n" + 
		"#ifdef CTG_S60 \r\n" + 
		"..\\Settings60\\SettingsApp.mmp\r\n" + 
		"# if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro.mmp\r\n" + 
		"#  if !defined(WINS)\r\n" + 
		"..\\Settings60\\SettingsApp_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro_self.mmp\r\n" + 
		"#  endif\r\n" + 
		"# endif\r\n" + 
		"#else\r\n" + 
		"..\\Settings\\SettingsApp.mmp MANUAL\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"..\\SettingsSrv\\SettingsSrv.mmp MANUAL\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\SettingsSrv\\SettingsSrv_self.mmp\r\n" + 
		"#endif\r\n" + 
		"#if !defined(CTG_PLATFORM_ER61) && !defined(CTG_PLATFORM_ER60)\r\n" + 
		"// Not built in Symbian 6.x\r\n" + 
		"..\\AnimDll\\TALXAnim.mmp MANUAL\r\n" + 
		"#endif\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW.mmp MANUAL\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW_self.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_S60)\r\n" + 
		"..\\test\\feponoff\\group\\feponoff.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if 0\r\n" + 
		"// Autostart Recognizer currently not used\r\n" + 
		"..\\TALXR\\TALXR.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"gnumakefile ..\\Settings60\\icons.mk\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_EXPORTS\r\n" + 
		"\r\n" + 
		"#if !defined(CTG_PLATFORM_ER90)\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\wins\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60\\wins\\udeb\\SwiTtsClient2.LIB ..\\release\\wins\\udeb\\SwiTtsClient2.lib\r\n" + 
		"  ..\\..\\libs\\s60\\armi\\urel\\SwiTtsClient2.LIB ..\\release\\armi\\urel\\SwiTtsClient2.lib\r\n" + 
		"#else\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\winscw\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\winscw\\udeb\\psdk_lmwrapper.lib ..\\release\\winscw\\udeb\\psdk_lmwrapper.lib\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\armv5\\urel\\psdk_lmwrapper_gcce.lib ..\\release\\armv5\\urel\\psdk_lmwrapper.lib\r\n" + 
		"#endif\r\n" + 
		"";

		makeModel(textBug4039);
		IBldInfView view = getView(config);

		// changing all the attributes shouldn't break the lookup for reordering

		for (IMakMakeReference ref : view.getMakMakeReferences()) {
			ref.setManual(true);
		}
		IMakMakeReference ref1 = view.getMakMakeReferences().remove(4);
		view.getMakMakeReferences().add(3, ref1);
		
		commitTest(view, textBug4039_2);
				
	}

	public void testBug4039_4() {

		String textBug4039_2 = 
		"#ifdef CTG_PLATFORM_ER90\r\n" + 
		"PRJ_PLATFORMS\r\n" + 
		"  DEFAULT GCCE\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_MMPFILES\r\n" + 
		"\r\n" + 
		"..\\TALXCommon\\TALXCommon.mmp\r\n" + 
		"#if defined(CTG_S60) && !defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXCommon\\TALXCommonDyn.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\TALXOnOff\\TalxFepReload.mmp\r\n" + 
		"..\\TALXOnOff\\TalxFepUnload.mmp\r\n" + 
		"..\\feptest\\TalxFep.mmp\r\n" + 
		"..\\TALXOnOff\\TALXStartupLaunch.mmp\r\n" + 
		"..\\TALXOnOff\\TALXRasterLaunch.mmp\r\n" + 
		"..\\RasterDll\\TALXRaster.mmp\r\n" + 
		"#else\r\n" + 
		"..\\TALXOnOff\\TALXOn.mmp\r\n" + 
		"..\\TALXOnOff\\TALXOff.mmp\r\n" + 
		"#endif\r\n" + 
		"..\\TALXOnOff\\TALXOnLaunch.mmp\r\n" + 
		"\r\n" + 
		"..\\feptest\\feptest.mmp\r\n" + 
		"\r\n" + 
		"#ifdef CTG_S60 \r\n" + 
		"..\\Settings60\\SettingsApp.mmp\r\n" + 
		"# if defined(CTG_PLATFORM_ER90)\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro.mmp\r\n" + 
		"#  if !defined(WINS)\r\n" + 
		"..\\Settings60\\SettingsApp_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_lmpro_self.mmp\r\n" + 
		"..\\Settings60\\SettingsApp_zooms_lmpro_self.mmp\r\n" + 
		"#  endif\r\n" + 
		"# endif\r\n" + 
		"#else\r\n" + 
		"..\\Settings\\SettingsApp.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"..\\SettingsSrv\\SettingsSrv.mmp\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\SettingsSrv\\SettingsSrv_self.mmp\r\n" + 
		"#endif\r\n" + 
		"#if !defined(CTG_PLATFORM_ER61) && !defined(CTG_PLATFORM_ER60)\r\n" + 
		"// Not built in Symbian 6.x\r\n" + 
		"..\\AnimDll\\TALXAnim.mmp\r\n" + 
		"#endif\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW.mmp\r\n" + 
		"#if defined(CTG_PLATFORM_ER90) && !defined(WINS)\r\n" + 
		"..\\TTSAdapt\\TTSAdaptNEW_self.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_S60)\r\n" + 
		"..\\test\\feponoff\\group\\feponoff.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if 0\r\n" + 
		"// Autostart Recognizer currently not used\r\n" + 
		"..\\TALXR\\TALXR.mmp\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"#if defined(CTG_PLATFORM_ER90)\r\n" + 
		"gnumakefile ..\\Settings60\\icons.mk\r\n" + 
		"#endif\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_EXPORTS\r\n" + 
		"\r\n" + 
		"#if !defined(CTG_PLATFORM_ER90)\r\n" + 
		"  ..\\..\\libs\\s60\\armi\\urel\\SwiTtsClient2.LIB ..\\release\\armi\\urel\\SwiTtsClient2.lib\r\n" + 
		"  ..\\..\\libs\\s60\\wins\\udeb\\SwiTtsClient2.LIB ..\\release\\wins\\udeb\\SwiTtsClient2.lib\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\wins\\c\\system\\data\\talx.ini\r\n" + 
		"#else\r\n" + 
		"  ..\\..\\input\\all\\talx.ini ..\\winscw\\c\\system\\data\\talx.ini\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\winscw\\udeb\\psdk_lmwrapper.lib ..\\release\\winscw\\udeb\\psdk_lmwrapper.lib\r\n" + 
		"  ..\\..\\libs\\s60_3rd\\armv5\\urel\\psdk_lmwrapper_gcce.lib ..\\release\\armv5\\urel\\psdk_lmwrapper.lib\r\n" + 
		"#endif\r\n" + 
		"";

		// this should be allowed because all the affected entries are in the same
		// conditional block
		makeModel(textBug4039);
		IBldInfView view = getView(config);

		IExport exp1 = view.getExports().remove(2);
		IExport exp0 = view.getExports().remove(0);
		view.getExports().add(0, exp1);
		view.getExports().add(2, exp0);
		
		commitTest(view, textBug4039_2);
		checkNoProblems(view);
	}

}
