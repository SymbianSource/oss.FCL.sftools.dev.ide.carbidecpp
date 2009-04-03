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
package com.nokia.carbide.cdt.builder.test;

import com.nokia.carbide.cpp.epoc.engine.model.IModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.runtime.CoreException;

import java.io.File;

import junit.framework.TestCase;

public class TestDefaultTranslationUnitProvider extends TestCase {

	private ITranslationUnitProvider tuProvider;
	private IModelDocumentProvider cachingModelDocumentProvider;
	private File tmpDir;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tuProvider = DefaultTranslationUnitProvider.getInstance();
		assertNotNull(tuProvider);

		cachingModelDocumentProvider = DefaultModelDocumentProvider.getInstance();
		tmpDir = FileUtils.getTemporaryDirectory();
	}
	
	public void testBasic() throws Exception {
		File file1 = new File(tmpDir, "file1.txt");
		FileUtils.writeFileContents(file1, "text".toCharArray(), null);
		try {
			ITranslationUnit tu = tuProvider.getTranslationUnit(file1, cachingModelDocumentProvider);
			assertNotNull(tu);
			ITranslationUnit tu2 = tuProvider.getTranslationUnit(file1, cachingModelDocumentProvider);
			assertNotNull(tu2);
			assertSame(tu, tu2);
			
			// detect change
			Thread.sleep(500);
			FileUtils.writeFileContents(file1, "text2".toCharArray(), null);
			tu2 = tuProvider.getTranslationUnit(file1, cachingModelDocumentProvider);
			assertNotNull(tu2);
			assertNotSame(tu, tu2);
			
			// not changed again
			ITranslationUnit tu3 = tuProvider.getTranslationUnit(file1, cachingModelDocumentProvider);
			assertNotNull(tu3);
			assertSame(tu2, tu3);
		} finally {
			file1.delete();
		}
	}
	
	// we had an issue where the flushing was concurrently modifying its lists
	public void testFlushingSize() throws Exception {
		final int COUNT = 1000;
		File[] files = new File[COUNT];
		try {
			for (int idx = 0; idx < COUNT; idx++) {
				files[idx] = new File(tmpDir, "file" + idx + ".txt");
				FileUtils.writeFileContents(files[idx], (""+idx).toCharArray(), null);
			}
	
			for (int idx = 0; idx < COUNT; idx++) {
				ITranslationUnit tu = tuProvider.getTranslationUnit(files[idx], cachingModelDocumentProvider);
				assertNotNull(tu);
			}
			for (int idx = 0; idx < COUNT; idx++) {
				ITranslationUnit tu = tuProvider.getTranslationUnit(files[idx], cachingModelDocumentProvider);
				assertNotNull(tu);
			}
			for (int idx = 0; idx < COUNT; idx++) {
				ITranslationUnit tu = tuProvider.getTranslationUnit(files[idx], cachingModelDocumentProvider);
				assertNotNull(tu);
			}
		} finally {
			for (int idx = 0; idx < COUNT; idx++) {
				if (files[idx] != null)
					files[idx].delete();
			}

		}
	}
	
	public void testFlushingModified() throws Exception {
		final int COUNT = 1000;
		File[] files = new File[COUNT];
		try {
			for (int idx = 0; idx < COUNT; idx++) {
				files[idx] = new File(tmpDir, "file" + idx + ".txt");
				FileUtils.writeFileContents(files[idx], (""+idx).toCharArray(), null);
			}
	
			for (int idx = 0; idx < COUNT; idx++) {
				// populate
				ITranslationUnit tu = tuProvider.getTranslationUnit(files[idx], cachingModelDocumentProvider);
				assertNotNull(tu);
			}
			for (int idx = 0; idx < COUNT; idx++) {
				// randomly change contents and ensure no crashes
				if (Math.random() < 0.1)
					FileUtils.writeFileContents(files[idx], (""+idx).toCharArray(), null);
				ITranslationUnit tu = tuProvider.getTranslationUnit(files[idx], cachingModelDocumentProvider);
				assertNotNull(tu);
			}
			for (int idx = 0; idx < COUNT; idx++) {
				// randomly delete and ensure no crashes or invalidly cached tus
				boolean deleted = false;
				if (Math.random() < 0.1) {
					files[idx].delete();
					deleted = true;
				}
				ITranslationUnit tu = tuProvider.getTranslationUnit(files[idx], cachingModelDocumentProvider);
				if (deleted)
					assertNull(tu);
				else
					assertNotNull(tu);
			}
		} finally {
			for (int idx = 0; idx < COUNT; idx++) {
				if (files[idx] != null)
					files[idx].delete();
			}

		}
	}

	public void testThreadedCaching() throws Exception {
		final int COUNT = 100;
		final File[] files = new File[COUNT];
		
		
		try {
			
			for (int iteration = 0; iteration < 10; iteration++) {
				for (int idx = 0; idx < COUNT; idx++) {
					files[idx] = new File(tmpDir, "file" + idx + ".txt");
					FileUtils.writeFileContents(files[idx], (""+idx).toCharArray(), null);
				}
				
		
				for (int idx = 0; idx < COUNT; idx++) {
					// populate
					ITranslationUnit tu = tuProvider.getTranslationUnit(files[idx], cachingModelDocumentProvider);
					assertNotNull(tu);
				}
				
				Thread thread1 = new Thread(new Runnable() {

					public void run() {
						for (int idx = 0; idx < COUNT; idx++) {
							// randomly change contents and ensure no crashes
							if (Math.random() < 0.1) {
								try {
									FileUtils.writeFileContents(files[idx], (""+idx).toCharArray(), null);
								} catch (CoreException e) {
									fail(e.toString());
								}
							}
							ITranslationUnit tu = tuProvider.getTranslationUnit(files[idx], cachingModelDocumentProvider);
							assertNotNull(tu);
						}
					}
					
				});
				thread1.run();
				
				for (int idx = 0; idx < COUNT; idx++) {
					// randomly delete and ensure no crashes or invalidly cached tus
					boolean deleted = false;
					if (Math.random() < 0.1) {
						files[idx].delete();
						deleted = true;
					}
					ITranslationUnit tu = tuProvider.getTranslationUnit(files[idx], cachingModelDocumentProvider);
					if (deleted)
						assertNull(tu);
					else
						assertNotNull(tu);
				}
				
				thread1.join();
			}
		} finally {
			for (int idx = 0; idx < COUNT; idx++) {
				if (files[idx] != null)
					files[idx].delete();
			}

		}
	}
	
}
