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

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.Document;

import com.nokia.carbide.cpp.epoc.engine.model.IModelProvider;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPViewConfiguration;

public class ModelProviderThreadTests {


	private static boolean modelThreadFailed;
	class Reader implements Runnable {

		private IPath path;
		private String result;
		private IModelProvider<IMMPOwnedModel, IMMPModel> provider;
		private IMMPViewConfiguration viewConfiguration;
		
		public Reader(IModelProvider<IMMPOwnedModel, IMMPModel> provider, IMMPViewConfiguration viewConfiguration, IPath path) {
			this.provider = provider;
			this.viewConfiguration = viewConfiguration;
			this.path = path;
		}
		
		public String getResult() {
			return result;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			while (!modelThreadFailed && modelWritingCount < MAX_ITERATIONS) {
				try {
					doRead();
				} catch (CoreException e) {
					TestCase.fail(e.getMessage());
					return;
				}
				Thread.yield();
				if (Thread.interrupted())
					return;
			}
			// failed, so force early exit
			modelWritingCount = MAX_ITERATIONS;
		}
		
		/**
		 * 
		 */
		private void doRead() throws CoreException {
			IMMPModel model = provider.getSharedModel(path);
			TestCase.assertNotNull(model);
			try {
				IMMPView view = model.createView(viewConfiguration);
				try {
					if (DUMP) System.out.println(Thread.currentThread().getName()+ "> r");
				} finally {
					view.dispose();
				}
			} finally {
				provider.releaseSharedModel(model);
			}
		}


	}
	
	final boolean DUMP = false;
	final int MAX_ITERATIONS = 50;
	public static int modelWritingCount;
	synchronized int nextModelWritingCount() {
		return modelWritingCount++;
	}
	class Writer implements Runnable {

		private IPath path;
		private IModelProvider<IMMPOwnedModel, IMMPModel> provider;
		private IMMPViewConfiguration viewConfiguration;
		
		public Writer(IModelProvider<IMMPOwnedModel, IMMPModel> provider, IMMPViewConfiguration viewConfiguration, IPath path) {
			this.provider = provider;
			this.viewConfiguration = viewConfiguration;
			this.path = path;
		}
		
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			int cnt;
			while (!modelThreadFailed && (cnt = nextModelWritingCount()) < MAX_ITERATIONS) {
				try {
					addSource(cnt);
				} catch (CoreException e) {
					TestCase.fail(e.getMessage());
					return;
				}

				if (Thread.interrupted())
					return;
			}
		}

		/**
		 * 
		 */
		private void addSource(final int count) throws CoreException {
			IMMPModel model = provider.getSharedModel(path);
			TestCase.assertNotNull(model);
			try {
				IMMPView view = model.createView(viewConfiguration);
				try {
					if (DUMP) System.out.println(Thread.currentThread().getName()+ "> w"+count);
					while (!modelThreadFailed) {
						try {
							view.getSources().add(new Path("" + count));
							view.commit();
							break;
						} catch (IllegalStateException e) {
							if (DUMP) System.out.println(Thread.currentThread().getName()+ "> out of sync, trying again");
							view.revert();
						} catch (Throwable t) {
							if (DUMP) System.out.println(Thread.currentThread().getName()+ "> failure while committing");
							t.printStackTrace();
							modelThreadFailed = true;
						}
					}
				} finally {
					view.dispose();
				}
			} finally {
				provider.releaseSharedModel(model);
			}
		}
	}
	
	/**
	 * Ensure the reader thread got sensible results.
	 * @param result
	 */
	private void validate(String result) {
		if (result.trim().length() == 0)
			return;
		int maxNum = -1;
		Set<Integer> found = new HashSet<Integer>(); 
		String[] nums = result.split("\\s+");
		for (String num : nums) {
			int n = Integer.parseInt(num);
			found.add(n);
			maxNum = Math.max(n, maxNum);
		}
		if (maxNum + 1 != found.size()) {
			TestCase.fail("Invalid model contents: " + result);
		}
	}

	/**
	 * Ensure the models work sanely with threading.
	 * @throws Exception
	 */
	public void testThreading(IModelProvider<IMMPOwnedModel, IMMPModel> provider,
			IMMPViewConfiguration viewConfig,
			IPath mmpPath) throws CoreException {
		
		// create the initial content
		IMMPOwnedModel ownedModel = provider.createModel(mmpPath);
		ownedModel.setDocument(new Document("// test file\n"));
		provider.registerModel(ownedModel);
		provider.releaseSharedModel(ownedModel);
		
		final int READER_COUNT = 5;
		final int WRITER_COUNT = 2;
		Reader[] readers = new Reader[READER_COUNT];
		Writer[] writers = new Writer[WRITER_COUNT];
		Thread[] readerThreads = new Thread[READER_COUNT];
		Thread[] writerThreads = new Thread[WRITER_COUNT];
		
		try {
			for (int i = 0; i < WRITER_COUNT; i++) {
				writers[i] = new Writer(provider, viewConfig, mmpPath);
				writerThreads[i] = new Thread(writers[i]);
				writerThreads[i].start();
			}
			
			for (int i = 0; i < READER_COUNT; i++) {
				readers[i] = new Reader(provider, viewConfig, mmpPath);
				readerThreads[i] = new Thread(readers[i]);
				readerThreads[i].start();
			}
			
			while (modelWritingCount < MAX_ITERATIONS) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					TestCase.fail();
				}
			}
	
		} catch (Throwable t) {
			for (int i = 0; i <  WRITER_COUNT; i++) {
				writerThreads[i].interrupt();
			}
			for (int i = 0; i <  READER_COUNT; i++) {
				readerThreads[i].interrupt();
			}
			TestCase.fail(t.toString());
		}

		try {
			for (int i = 0; i <  WRITER_COUNT; i++) {
				writerThreads[i].join();
			}
			for (int i = 0; i <  READER_COUNT; i++) {
				readerThreads[i].join();
			}
		} catch (InterruptedException e) {
			TestCase.fail();
		}

		TestCase.assertFalse(modelThreadFailed);

		// now validate results
		IMMPModel model = provider.getSharedModel(mmpPath);
		TestCase.assertNotNull(model);
		try {
			IMMPView view = model.createView(viewConfig);
			try {
				StringBuilder builder = new StringBuilder();
				for (IPath path : view.getSources()) {
					builder.append(path.lastSegment());
					builder.append(' ');
				}
				
				// can't actually validate -- we have no idea whether writers'
				// commits have come in order or not, since one writer may have
				// been out-of-sync and had to retry its commit late.
				validate(builder.toString());
			} finally {
				view.dispose();
			}
		} finally {
			provider.releaseSharedModel(model);
		}
	}
	
}
