package com.nokia.carbide.cpp.sdk.core.test;

import java.io.File;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SymbianSDKFactory;

import junit.framework.TestCase;

public class TestCarbideSDKCache extends TestCase {

	private class TestSDKManager extends SDKManager {
		private File cacheFile = new File(System.getProperty("user.home"), CARBIDE_SDK_CACHE_FILE_NAME);
		public File getCacheFile() {
			return cacheFile;
		}

		public Job getScanJob() {
			return scanJob;
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCacheCreation() throws Exception {
		final TestSDKManager manager = new TestSDKManager();
		File cacheFile = manager.getCacheFile();
		if (cacheFile != null && cacheFile.exists()) {
			cacheFile.delete();
		}
		IJobChangeListener listener = new IJobChangeListener() {
			
			public void sleeping(IJobChangeEvent event) {
			}
			
			public void scheduled(IJobChangeEvent event) {
			}
			
			public void running(IJobChangeEvent event) {
			}
			
			public void done(IJobChangeEvent event) {
				File cacheFile = manager.getCacheFile();
				assertNotNull(cacheFile);
				assertTrue(cacheFile.exists());
			}
			
			public void awake(IJobChangeEvent event) {
			}
			
			public void aboutToRun(IJobChangeEvent event) {
			}
		};
		manager.addScanJobListner(listener);
		manager.scanSDKs();
		manager.getScanJob().join();
		manager.removeScanJobLisner(listener);
	}

	public void testCacheModification() throws Exception {
		final TestSDKManager manager = new TestSDKManager();
		long orgTime = manager.getCacheFile().lastModified();
		manager.scanSDKs();
		manager.getScanJob().join();
		assertTrue(manager.getCacheFile().lastModified() > orgTime);
		final String sdkId = "test";
		ISymbianSDK sdk = SymbianSDKFactory.createInstance(sdkId, "C:\\", "com.nokia.s60", 
							new Version("9.5"), new Version("5.0"));
		orgTime = manager.getCacheFile().lastModified();
		waitASecond();
		manager.addSDK(sdk);
		assertTrue(manager.getCacheFile().lastModified() > orgTime);
		orgTime = manager.getCacheFile().lastModified();
		waitASecond();
		manager.updateSDK(sdk);
		assertTrue(manager.getCacheFile().lastModified() > orgTime);
		orgTime = manager.getCacheFile().lastModified();
		waitASecond();
		manager.removeSDK(sdkId);
		assertTrue(manager.getCacheFile().lastModified() > orgTime);
	}

	private void waitASecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
