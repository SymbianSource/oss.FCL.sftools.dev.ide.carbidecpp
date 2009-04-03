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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.hover;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.core.model.CModelManager;
import org.eclipse.cdt.internal.ui.util.EditorUtility;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;

import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.AsynchronousLookup;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibPluginController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibProperties;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXIndexController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXProperties;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.DevLibContent;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

public class TestHelper {

	private static String projectName="HW";

	public static Set<DevLibProperties> getAvailableDevLibProps()
			throws Exception {

		Set<DevLibProperties> devLibProps = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();

		return devLibProps;
	}

	public static InterXProperties getAnInterXProps() {
		Set<DevLibProperties> devPropSet = null;
		try {
			devPropSet = getAvailableDevLibProps();
		} catch (Exception e) {
			return null;
		}
		if (devPropSet.isEmpty()) {
			return null;
		} else {
			Iterator<DevLibProperties> it = devPropSet.iterator();
			DevLibProperties devLib = it.next();
			return devLib.getInterXProperties();
		}
	}

	public static void waitIndexingComplete(long timeout) throws Exception {
		long sleep = 100;
		Display display = Activator.getDefault().getWorkbench().getDisplay();
		int totalSleep = 0;
		Thread.sleep(2000);
		Logger.logDebug("Waiting to timeout spend" + timeout);
		while (true) {
			if (totalSleep > timeout) {
				throw new Exception(
						"Timeout. Indexing has not finished in timout period:"
								+ timeout);
			}
			boolean indexingCompleted = InterXIndexController
					.isIndexingCompleted();
			if (indexingCompleted) {
				break;
			} else {
				display.readAndDispatch();
				Thread.sleep(sleep);
			}
			totalSleep += sleep;
		}
		Logger.logDebug("Waiting has finished " + timeout);
	}

	public static FutureTask<DevLibContent> createAsynchronousLookup(String fqn) {
		Callable<DevLibContent> callable = new AsynchronousLookup(fqn);
		FutureTask<DevLibContent> ft = new FutureTask<DevLibContent>(callable);
		new Thread(ft).start();
		return ft;
	}

	// this method creates a feature which simulates waiting
	public static FutureTask<DevLibContent> createAsynchronousLookupFuture(
			final String fqn) {

		Callable<DevLibContent> waitCallable = new Callable<DevLibContent>() {
			@Override
			public DevLibContent call() throws Exception {
				Thread.sleep(2000);
				Callable<DevLibContent> callable = new AsynchronousLookup(fqn);
				FutureTask<DevLibContent> ft1 = new FutureTask<DevLibContent>(
						callable);
				new Thread(ft1).start();
				Display display = Activator.getDefault().getWorkbench()
						.getDisplay();
				int sleep = 100;
				while (true) {
					if (ft1.isDone()) {
						return ft1.get();
					}

					if (ft1.isCancelled()) {
						return null;
					}

					display.readAndDispatch();
					Thread.sleep(sleep);
				}
			}
		};

		FutureTask<DevLibContent> ft = new FutureTask<DevLibContent>(
				waitCallable);
		new Thread(ft).start();
		return ft;
	}

	public static IEditorPart getIEditorPart() throws Exception{		
		IProject project=getProject();
		IFile file =  project.getFile("/src/HW.cpp");
		IEditorPart editorPart =EditorUtility.openInEditor(file);
		return editorPart;
//		IEditorPart findEditor = WorkbenchPage.this.getEditorManager().findEditor(new FileEditorInput(file));
		//WorkbenchHelper
	}
	
	
	private static IProject getProject() throws Exception{
		IProject project;
		IWorkspaceRoot root = CUIPlugin.getWorkspace().getRoot();
		IPath location = root.getLocation();
		URL src = FileLocator.find(ActivatorTest.getDefault().getBundle(),
				new Path("resource/HW.zip"), null);
		String dest = location.toOSString();
		uncompressedZip(src, dest);
		root.refreshLocal(0, null);
		Thread.sleep(2000);		
		project = CUIPlugin.getWorkspace().getRoot().getProject(projectName);
		if (!project.isOpen()){
			project.create(null);
			project.open(null);	
		}		
		return project;
	}
	
	public static ITranslationUnit getTranslationUnit()
			throws Exception {
	
		IProject project=getProject();
		IFile file =  project.getFile("/src/HW.cpp");		
		ITranslationUnit tUnit = (ITranslationUnit) CoreModel.getDefault()
				.create(file);
		return tUnit;
	}

	private static void uncompressedZip(URL jarSrc, String dest)
			throws Exception {
		InputStream os = jarSrc.openStream();
		BufferedInputStream bis = new BufferedInputStream(os);
		ZipInputStream zis = new ZipInputStream(bis);
		int i = 0;
		while (true) {
			ZipEntry zipEntry = zis.getNextEntry();

			if (zis.available() == 0 || zipEntry == null)
				break;

			uncompressZipEntry(zis, zipEntry, dest);
			i++;
		}
	}

	private static long uncompressZipEntry(ZipInputStream zis,
			ZipEntry zipEntry, String dest) throws Exception {
		byte[] buf = new byte[1024];
		String entryName = zipEntry.getName();
		int n;
		File rootDir = new File(dest);
		FileOutputStream fileoutputstream;
		File newFile = new File(rootDir, entryName);
		long totalLen = 0;

		if (zipEntry.isDirectory()) {
			if (!newFile.exists()) {
				newFile.mkdir();
			}
			return 0;
		}
		fileoutputstream = new FileOutputStream(newFile);

		while ((n = zis.read(buf, 0, 1024)) > -1) {
			fileoutputstream.write(buf, 0, n);
			totalLen += n;
		}

		fileoutputstream.close();
		zis.closeEntry();
		return totalLen;
	}
}
