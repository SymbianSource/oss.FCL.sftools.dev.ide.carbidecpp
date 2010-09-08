package com.nokia.carbide.cpp.sdk.core.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.SDKCacheUtils;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2QueryUtils;
import com.nokia.carbide.cpp.internal.sdk.core.model.AbstractSDKManager;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManagerCacheEntry;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SymbianSDKFactory;

public class TestCarbideSDKCache extends TestCase {

	private class TestSDKManager extends SDKManager {

		@SuppressWarnings("unchecked")
		public TestSDKManager() {
			Map<String, SDKManagerCacheEntry> cache = SDKCacheUtils.getCache().getCachedData(SDK_MANAGER_CACHE_KEY, Map.class, 0);
			if (cache == null) {
				cache = new HashMap<String, SDKManagerCacheEntry>();
				SDKCacheUtils.getCache().putCachedData(SDK_MANAGER_CACHE_KEY, (Serializable)cache, 0);
			}
		}

		public void clearCache() {
			clearSDKCache();
		}

		@SuppressWarnings("unchecked")
		public Map<String, SDKManagerCacheEntry> getCache() {
			Map<String, SDKManagerCacheEntry> cache = SDKCacheUtils.getCache().getCachedData(SDK_MANAGER_CACHE_KEY, Map.class, 0);
			if (cache == null) {
				cache = new HashMap<String, SDKManagerCacheEntry>();
				SDKCacheUtils.getCache().putCachedData(SDK_MANAGER_CACHE_KEY, (Serializable)cache, 0);
			}
			return cache;
		}

		public SDKManagerCacheEntry getCacheEntry(String id) {
			return getSDKCacheEntry(id);
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
		((AbstractSDKManager)manager).init();
		manager.clearCache();
		IJobChangeListener listener = new IJobChangeListener() {
			
			public void sleeping(IJobChangeEvent event) {
			}
			
			public void scheduled(IJobChangeEvent event) {
			}
			
			public void running(IJobChangeEvent event) {
			}
			
			public void done(IJobChangeEvent event) {
				Map<String, SDKManagerCacheEntry> cache = manager.getCache();
				assertNotNull(cache);
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
		((AbstractSDKManager)manager).init();
		manager.scanSDKs();
		manager.getScanJob().join();
		assertTrue(manager.getSDKList().isEmpty() == manager.getCache().isEmpty());
		manager.clearCache();
		assertNull(SDKCacheUtils.getCache().getCache(AbstractSDKManager.SDK_MANAGER_CACHE_KEY));
		final String sdkId = "test";
		ISymbianSDK sdk = SymbianSDKFactory.createInstance(sdkId, "C:\\", new Version("9.5"));
		manager.addSDK(sdk);
		assertFalse(manager.getCache().isEmpty());
		assertNotNull(manager.getCacheEntry(sdkId));
		manager.updateSDK(sdk);
		assertFalse(manager.getCache().isEmpty());
		assertNotNull(manager.getCacheEntry(sdkId));
		manager.removeSDK(sdkId);
		assertNull(manager.getCacheEntry(sdkId));
	}

	public void testSBSv2QueryCache() throws Exception {
		SBSv2QueryUtils.removeAllCachedQueries();
		assertNull(SDKCacheUtils.getCache().getCache(SBSv2QueryUtils.ALIAS_CACHE_KEY));
		assertNull(SDKCacheUtils.getCache().getCache(SBSv2QueryUtils.PRODUCT_CACHE_KEY));
		assertNull(SDKCacheUtils.getCache().getCache(SBSv2QueryUtils.CONFIG_CACHE_KEY));
		final TestSDKManager manager = new TestSDKManager();
		((AbstractSDKManager)manager).init();
		manager.scanSDKs();
		manager.getScanJob().join();
		assertNull(SDKCacheUtils.getCache().getCache(SBSv2QueryUtils.ALIAS_CACHE_KEY));
		assertNull(SDKCacheUtils.getCache().getCache(SBSv2QueryUtils.PRODUCT_CACHE_KEY));
		assertNull(SDKCacheUtils.getCache().getCache(SBSv2QueryUtils.CONFIG_CACHE_KEY));
	}

}
