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
* Test the ProjectExportsGatherer class
*
*/
package com.nokia.carbide.cdt.builder.test;

import com.nokia.carbide.cdt.internal.api.builder.ProjectExportsGatherer;

import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import junit.framework.TestCase;

public class TestProjectExportsGatherer extends TestCase {
	private IPath projectDataPath;
	private IPath epocRoot;
    
	 /**
     * Load a file relative to this plugin in the running workbench 
     * @param file
     * @return File
     * @throws IOException
     */
    public static File pluginRelativeFile(String file) throws IOException {
        if (TestPlugin.getDefault() == null)
            return null;
        Bundle bundle = TestPlugin.getDefault().getBundle();
        if (bundle == null)
            return null;
        URL url = FileLocator.find(bundle, new Path("."), null);
        if (url == null)
            TestCase.fail("could not make URL from bundle " + bundle + " and path " + file);
        url = FileLocator.resolve(url);
        TestCase.assertEquals("file", url.getProtocol());
        return new File(url.getPath(), file);
        
    }

    /**
     *  Find a file relative to the project.  Works if running
     *  in the workbench or standalone.
     *  @param file the relative path (from the project) to the file
     *  @return File
     */
    public static File projectRelativeFile(String file) throws Exception {
        File f;
        if (!Platform.isRunning()) {
            // get file relative to CWD (i.e. this project)
            f = new File(file);
            f = f.getCanonicalFile();
        } else {
            // get file relative to running plugin (still this project)
            f = pluginRelativeFile(file);
        }
        if (f == null)
            TestCase.fail("Cannot find file " + file + " relative to project");
        return f;
    }

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		epocRoot = new Path("C:\\symbian\\9.4\\S60_5th");
				
	}

	private IPath getProjectData(String name) throws Exception {
		 return new Path(projectRelativeFile("data/exports-gathering").getAbsolutePath()).append(name);
	}
	
	// no exports except for generated files
	public void testNothingExported() throws Exception {
		projectDataPath = getProjectData("none");
		ProjectExportsGatherer gatherer = createGatherer();
		assertEquals(0, gatherer.getAllFilesystemToEpocVisibleMap().size());
		assertEquals(0, gatherer.getFilesystemToEpocExportMap().size());
		assertEquals(0, gatherer.getFilesystemToPkgInstallFileMap().size());
		assertEquals(0, gatherer.getUninstalledFilesystemToEpocExportsMap().size());
	}

	private ProjectExportsGatherer createGatherer() {
		return new ProjectExportsGatherer(
				projectDataPath, projectDataPath.append("group/bld.inf"),
				epocRoot, "armv5", "udeb");
	}
	
	// only PRJ_[TEST]EXPORTS in bld.inf
	public void testBldInfExports() throws Exception {
		projectDataPath = getProjectData("exportOnly");
		ProjectExportsGatherer gatherer = createGatherer();
		IPath src1 = projectDataPath.append("gfx/foo.svg");
		IPath src2 = projectDataPath.append("doc/README.txt");
		IPath epoc1 = new Path("c:\\private\\12345678\\foo.svg");
		IPath epoc2 = new Path("c:\\sys\\readmes\\README.txt");
		
		Map<IPath, IPath> filesystemToEpocExportMap = gatherer.getFilesystemToEpocExportMap();
		assertEquals(2, filesystemToEpocExportMap.size());
		assertEquals(epoc1, filesystemToEpocExportMap.get(src1));
		assertEquals(epoc2, filesystemToEpocExportMap.get(src2));
		
		assertEquals(0, gatherer.getFilesystemToPkgInstallFileMap().size());
		
		Map<IPath, IPath> uninstalledFilesystemToEpocExportsMap = gatherer.getUninstalledFilesystemToEpocExportsMap();
		assertEquals(filesystemToEpocExportMap, uninstalledFilesystemToEpocExportsMap);

		
		Map<IPath, IPath> allFilesystemToEpocVisibleMap = gatherer.getAllFilesystemToEpocVisibleMap();
		assertEquals(filesystemToEpocExportMap, allFilesystemToEpocVisibleMap);
		
		
	}
	
	// only install files in .pkg
	public void testPkgInstallFiles() throws Exception {
		projectDataPath = getProjectData("pkgOnly");
		ProjectExportsGatherer gatherer = createGatherer();
		
		IPath epoc1a = projectDataPath.append("sis\\text\\englishfile.txt");
		IPath epoc1b = epocRoot.append("text\\frenchfile.txt");
		IPath dev1 = new Path("$:\\private\\10000005\\import\\InstTest\\lang.txt");
		
		IPath epoc2 = epocRoot.append("epoc32\\text\\file1.txt");
		IPath dev2 = new Path("!:\\private\\10000005\\import\\InstTest\\file1.txt");

		IPath epoc3 = epocRoot.append("epoc32\\files\\option1.txt");
		IPath dev3 = new Path("!:\\private\\10000005\\import\\InstTest\\option1.txt");

		Map<IPath, IPath> filesystemToEpocExportMap = gatherer.getFilesystemToEpocExportMap();
		assertEquals(0, filesystemToEpocExportMap.size());
		
		Map<IPath, IPath> filesystemToPkgInstallFileMap = gatherer.getFilesystemToPkgInstallFileMap();
		assertEquals(4, filesystemToPkgInstallFileMap.size());
		assertEquals(dev1, filesystemToPkgInstallFileMap.get(epoc1a));
		assertEquals(dev1, filesystemToPkgInstallFileMap.get(epoc1b));
		assertEquals(dev2, filesystemToPkgInstallFileMap.get(epoc2));
		assertEquals(dev3, filesystemToPkgInstallFileMap.get(epoc3));

		Map<IPath, IPath> uninstalledFilesystemToEpocExportsMap = gatherer.getUninstalledFilesystemToEpocExportsMap();
		assertEquals(0, uninstalledFilesystemToEpocExportsMap.size());

		// the source location for these is EPOCROOT since we don't know how the user got them there
		Map<IPath, IPath> allFilesystemToEpocVisibleMap = gatherer.getAllFilesystemToEpocVisibleMap();
		assertEquals(4, allFilesystemToEpocVisibleMap.size());
		assertEquals(dev1, allFilesystemToEpocVisibleMap.get(epoc1a));
		assertEquals(dev1, allFilesystemToEpocVisibleMap.get(epoc1b));
		assertEquals(dev2, allFilesystemToEpocVisibleMap.get(epoc2));
		assertEquals(dev3, allFilesystemToEpocVisibleMap.get(epoc3));
		
	}
	
	// both exports in bld.inf and install files in .pkg
	public void testExportsInBldInfToPkgInstallFiles() throws Exception {
		projectDataPath = getProjectData("exportAndPkg");
		ProjectExportsGatherer gatherer = createGatherer();
		
		IPath src1a = projectDataPath.append("sis\\text\\englishfile.txt");
		//IPath epoc1a = projectDataPath.append("sis\\text\\englishfile.txt");
		//IPath src1b = projectDataPath.append("sis\\text\\frenchfile.txt");
		IPath epoc1b = epocRoot.append("epoc32\\text\\frenchfile.txt");
		IPath dev1 = new Path("$:\\private\\10000005\\import\\InstTest\\lang.txt");
		
		IPath src2 = projectDataPath.append("gfx\\foo.svg");
		IPath epocTarget2 = new Path("c:\\private\\12345678\\foo.svg");
		IPath epocHost2 = epocRoot.append("epoc32\\data\\c\\private\\12345678\\foo.svg");
		IPath dev2 = new Path("!:\\private\\10000005\\foo.svg");

		IPath src3 = projectDataPath.append("doc\\README.txt");
		IPath epocHost3 = epocRoot.append("epoc32\\release\\armv5\\udeb\\z\\sys\\readmes\\README.txt");
		IPath epoc3 = new Path("z:\\sys\\readmes\\README.txt");
		IPath dev3 = new Path("!:\\private\\10000005\\import\\InstTest\\README.txt");

		IPath src4 = projectDataPath.append("inc/Test.h");
		IPath epoc4 = new Path("/epoc32/include/Test.h");
		IPath src5 = projectDataPath.append("inc/Test00.h");
		IPath epoc5 = new Path("/epoc32/include/TestPrefix.h");
		
		Map<IPath, IPath> filesystemToEpocExportMap = gatherer.getFilesystemToEpocExportMap();
		assertEquals(4, filesystemToEpocExportMap.size());
		assertEquals(epocTarget2, filesystemToEpocExportMap.get(src2));
		assertEquals(epoc3, filesystemToEpocExportMap.get(src3));
		assertEquals(epoc4, filesystemToEpocExportMap.get(src4));
		assertEquals(epoc5, filesystemToEpocExportMap.get(src5));
		
		Map<IPath, IPath> filesystemToPkgInstallFileMap = gatherer.getFilesystemToPkgInstallFileMap();
		assertEquals(4, filesystemToPkgInstallFileMap.size());
		assertEquals(dev2, filesystemToPkgInstallFileMap.get(epocHost2));
		assertEquals(dev3, filesystemToPkgInstallFileMap.get(epocHost3));
		assertEquals(dev1, filesystemToPkgInstallFileMap.get(src1a));
		assertEquals(dev1, filesystemToPkgInstallFileMap.get(epoc1b));
		
		Map<IPath, IPath> uninstalledFilesystemToEpocExportsMap = gatherer.getUninstalledFilesystemToEpocExportsMap();
		assertEquals(2, uninstalledFilesystemToEpocExportsMap.size());
		assertEquals(epoc4, filesystemToEpocExportMap.get(src4));
		assertEquals(epoc5, filesystemToEpocExportMap.get(src5));

		Map<IPath, IPath> allFilesystemToEpocVisibleMap = gatherer.getAllFilesystemToEpocVisibleMap();
		assertEquals(8, allFilesystemToEpocVisibleMap.size());
		assertEquals(dev1, allFilesystemToEpocVisibleMap.get(src1a));
		assertEquals(dev1, allFilesystemToEpocVisibleMap.get(epoc1b));
		assertEquals(epocTarget2, allFilesystemToEpocVisibleMap.get(src2));
		assertEquals(epoc3, allFilesystemToEpocVisibleMap.get(src3));
		assertEquals(epoc4, allFilesystemToEpocVisibleMap.get(src4));
		assertEquals(epoc5, allFilesystemToEpocVisibleMap.get(src5));
		
		
	}

}
