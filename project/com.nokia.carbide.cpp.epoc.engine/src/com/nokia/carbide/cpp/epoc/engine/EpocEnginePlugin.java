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
package com.nokia.carbide.cpp.epoc.engine;

import org.eclipse.core.runtime.*;
import org.osgi.framework.BundleContext;

import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.epoc.engine.model.bsf.*;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileModel;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.model.sbv.*;
import com.nokia.carbide.internal.cpp.epoc.engine.model.SBVModelFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewDataCache;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.MultiResourceChangeListenerDispatcher;

/**
 * The main plugin class to be used in the desktop.
 */
public class EpocEnginePlugin extends Plugin {

	public static final String EPOC_ENGINE_PLUGIN_ID = "com.nokia.carbide.cpp.epoc.engine"; //$NON-NLS-1$

	/** balance the # of likely MMPs vs. the amount of data they consume */
	private static final int MMP_CACHE_SIZE = 500;
	/** one per project */
	private static final int BLDINF_CACHE_SIZE = 20;
	/** one or more per project but small footprint and expensive to read */
	private static final int IMAGE_MAKEFILE_CACHE_SIZE = 100;
	
	//The shared instance.
	private static EpocEnginePlugin plugin;
	static private IModelProvider<IMMPOwnedModel, IMMPModel> mmpModelProvider;
	private static IModelProvider<IBldInfOwnedModel, IBldInfModel> bldInfModelProvider;
	private static MultiResourceChangeListenerDispatcher multiResourceChangeListenerDispatcher;
	private static IModelProvider<IMakefileOwnedModel, IMakefileModel> makefileModelProvider;
	private static IModelProvider<IImageMakefileOwnedModel, IImageMakefileModel> imageMakefileModelProvider;
	private static IModelProvider<IBSFOwnedModel, IBSFModel> bsfModelProvider;
	private static IModelProvider<ISBVOwnedModel, ISBVModel> sbvModelProvider;

	private static ViewDataCache<IMMPOwnedModel, IMMPModel, IMMPView, IMMPData> mmpViewDataCache;

	private static ViewDataCache<IBldInfOwnedModel, IBldInfModel, IBldInfView, IBldInfData> bldInfViewDataCache;

	private static ViewDataCache<IImageMakefileOwnedModel, IImageMakefileModel, IImageMakefileView, IImageMakefileData> imageMakefileViewDataCache;
	/**
	 * The constructor.
	 */
	public EpocEnginePlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static EpocEnginePlugin getDefault() {
		return plugin;
	}

	static public void log(IStatus status) {
		Logging.log(plugin, status);
	}
	
	static public void log(Throwable thr) {
		Logging.log(plugin, Logging.newStatus(plugin, thr));
	}
	
	static public void log(Throwable thr, String msg) {
		Logging.log(plugin, Logging.newStatus(plugin, IStatus.ERROR, msg, thr));
	}

	/**
	 * Get the provider that manages access to MMP files in the workspace.
	 */
	public static synchronized IModelProvider<IMMPOwnedModel, IMMPModel> getMMPModelProvider() {
		if (mmpModelProvider == null) {
			mmpModelProvider = ModelProviderFactory.createModelProvider(new MMPModelFactory());
		}
		return mmpModelProvider;
	}
	
	/**
	 * Get the provider that manages access to bld.inf files in the workspace.
	 * @return provider, never null
	 */
	public static synchronized IModelProvider<IBldInfOwnedModel, IBldInfModel> getBldInfModelProvider() {
		if (bldInfModelProvider == null) {
			bldInfModelProvider = ModelProviderFactory.createModelProvider(new BldInfModelFactory());
		}
		return bldInfModelProvider;
	}

	/**
	 * Get the provider that manages access to ordinary makefiles in the workspace.
	 * @return provider, never null
	 */
	public static synchronized IModelProvider<IMakefileOwnedModel, IMakefileModel> getMakefileModelProvider() {
		if (makefileModelProvider == null) {
			makefileModelProvider = ModelProviderFactory.createModelProvider(new MakefileModelFactory());
		}
		return makefileModelProvider;
	}

	/**
	 * Get the provider that manages access to image (scalable icon) makefiles in the workspace.
	 * @return provider, never null
	 */
	public static synchronized IModelProvider<IImageMakefileOwnedModel, IImageMakefileModel> getImageMakefileModelProvider() {
		if (imageMakefileModelProvider == null) {
			imageMakefileModelProvider = ModelProviderFactory.createModelProvider(new ImageMakefileModelFactory());
		}
		return imageMakefileModelProvider;
	}
	
	/**
	 * Get the provider that manages access to BSF files in an SDK.
	 * @return provider, never null
	 */
	public static synchronized IModelProvider<IBSFOwnedModel, IBSFModel> getBSFModelProvider() {
		if (bsfModelProvider == null) {
			bsfModelProvider = ModelProviderFactory.createModelProvider(new BSFModelFactory());
		}
		return bsfModelProvider;
	}
	
	/**
	 * Get the provider that manages access to the .var files in the SDK (for Symbian Binary Variation support).
	 * @return provider, never null
	 */
	public static synchronized IModelProvider<ISBVOwnedModel, ISBVModel> getSBVModelProvider() {
		if (sbvModelProvider == null) {
			sbvModelProvider = ModelProviderFactory.createModelProvider(new SBVModelFactory());
		}
		return sbvModelProvider;
	}

	/**
	 * Get the dispatcher for resource change events handling multiple paths.
	 * @return dispatcher, never null
	 */
	public static synchronized MultiResourceChangeListenerDispatcher getMultiResourceChangeListenerDispatcher() {
		if (multiResourceChangeListenerDispatcher == null) {
			multiResourceChangeListenerDispatcher = new MultiResourceChangeListenerDispatcher();
		}
		return multiResourceChangeListenerDispatcher;
	}
	
	/**
	 * Get a shared instance of the given MMP model, create a view
	 * with the given configuration, and run user code using the model.
	 * <p>
	 * The model and view are automatically released.
	 * <p>
	 * If the model cannot be loaded or an exception is thrown, the runnable's 
	 * #failedLoad() is called.
	 * @param modelPath workspace-relative path
	 * @param viewConfiguration configuration for MMP (may not be null)
	 * @param runnable the code to run when the model is loaded (or fails)
	 * @return result from runnable#run
	 */
	public static Object runWithMMPView(IPath modelPath, 
				IMMPViewConfiguration viewConfiguration,
				IMMPViewRunnable runnable) {
		
		IMMPModel model;
		if (runnable instanceof ViewRunnableAdapter) {
			((ViewRunnableAdapter) runnable).setModelPath(modelPath);
		}
		try {
			model = getMMPModelProvider().getSharedModel(modelPath);
		} catch (CoreException e) {
			return runnable.failedLoad(e);
		} catch (Throwable t) {
			return runnable.failedLoad(new CoreException(Logging.newStatus(EpocEnginePlugin.getDefault(), t)));
		}
		IMMPView view = null;
		if (model == null) {
			return runnable.failedLoad(null);
		}
		try {
			view = model.createView(viewConfiguration);
			return runnable.run(view);
		} finally {
			if (view != null)
				view.dispose();
			getMMPModelProvider().releaseSharedModel(model);
		}
	}
	
	/**
	 * Get a shared instance of the given MMP model, create a view
	 * with the given configuration, and run user code using the model.
	 * <p>
	 * The model and view are automatically released.
	 * <p>
	 * If the model cannot be loaded or an exception is thrown, the runnable's 
	 * #failedLoad() is called.
	 * @param modelPath workspace-relative path
	 * @param viewConfiguration configuration for bld.inf (may not be null)
	 * @param runnable the code to run when the model is loaded (or fails)
	 * @return result from runnable#run
	 */
	public static Object runWithBldInfView(IPath modelPath, 
				IViewConfiguration viewConfiguration,
				IBldInfViewRunnable runnable) {
		IBldInfModel model;
		if (runnable instanceof ViewRunnableAdapter) {
			((ViewRunnableAdapter) runnable).setModelPath(modelPath);
		}
		try {
			model = getBldInfModelProvider().getSharedModel(modelPath);
		} catch (CoreException e) {
			return runnable.failedLoad(e);
		} catch (Throwable t) {
			return runnable.failedLoad(new CoreException(Logging.newStatus(EpocEnginePlugin.getDefault(), t)));
		}
		IBldInfView view = null;
		if (model == null) {
			return runnable.failedLoad(null);
		}
		try {
			view = model.createView(viewConfiguration);
			return runnable.run(view);
		} finally {
			if (view != null)
				view.dispose();
			getBldInfModelProvider().releaseSharedModel(model);
		}
	}

	/**
	 * Get a shared instance of the given MMP model, create a view
	 * with the given configuration, and run user code using the model.
	 * <p>
	 * The model and view are automatically released.
	 * <p>
	 * If the model cannot be loaded or an exception is thrown, the runnable's 
	 * #failedLoad() is called.
	 * @param modelPath workspace-relative path
	 * @param viewConfiguration configuration for image makefile (may not be null)
	 * @param runnable the code to run when the model is loaded (or fails)
	 * @return result from runnable#run
	 */
	public static Object runWithImageMakefileView(IPath modelPath, 
				IImageMakefileViewConfiguration viewConfiguration,
				IImageMakefileViewRunnable runnable) {
		IImageMakefileModel model;
		if (runnable instanceof ViewRunnableAdapter) {
			((ViewRunnableAdapter) runnable).setModelPath(modelPath);
		}
		try {
			model = getImageMakefileModelProvider().getSharedModel(modelPath);
		} catch (CoreException e) {
			return runnable.failedLoad(e);
		} catch (Throwable t) {
			return runnable.failedLoad(new CoreException(Logging.newStatus(EpocEnginePlugin.getDefault(), t)));
		}
		IImageMakefileView view = null;
		if (model == null) {
			return runnable.failedLoad(null);
		}
		try {
			view = model.createView(viewConfiguration);
			return runnable.run(view);
		} finally {
			if (view != null)
				view.dispose();
			getImageMakefileModelProvider().releaseSharedModel(model);
		}
	}
	
	/**
	 * Get a shared instance of the given BSF model, create a view
	 * with the given configuration, and run user code using the model.
	 * <p>
	 * The model and view are automatically released.
	 * <p>
	 * If the model cannot be loaded or an exception is thrown, the runnable's 
	 * #failedLoad() is called.
	 * @param modelPath workspace-relative path
	 * @param runnable the code to run when the model is loaded (or fails)
	 * @return result from runnable#run
	 */
	public static Object runWithBSFView(IPath modelPath, 
				IBSFViewRunnable runnable) {
		IBSFModel model;
		if (runnable instanceof ViewRunnableAdapter) {
			((ViewRunnableAdapter) runnable).setModelPath(modelPath);
		}
		try {
			model = getBSFModelProvider().getSharedModel(modelPath);
		} catch (CoreException e) {
			return runnable.failedLoad(e);
		} catch (Throwable t) {
			return runnable.failedLoad(new CoreException(Logging.newStatus(EpocEnginePlugin.getDefault(), t)));
		}
		IBSFView view = null;
		if (model == null) {
			return runnable.failedLoad(null);
		}
		try {
			view = model.createView(null);
			return runnable.run(view);
		} finally {
			if (view != null)
				view.dispose();
			getBSFModelProvider().releaseSharedModel(model);
		}
	}
	
	/**
	 * Get a shared instance of the given SBV model, create a view
	 * with the given configuration, and run user code using the model.
	 * <p>
	 * The model and view are automatically released.
	 * <p>
	 * If the model cannot be loaded or an exception is thrown, the runnable's 
	 * #failedLoad() is called.
	 * @param modelPath workspace-relative path
	 * @param runnable the code to run when the model is loaded (or fails)
	 * @return result from runnable#run
	 */
	public static Object runWithSBVView(IPath modelPath, 
				ISBVViewRunnable runnable) {
		ISBVModel model;
		if (runnable instanceof ViewRunnableAdapter) {
			((ViewRunnableAdapter) runnable).setModelPath(modelPath);
		}
		try {
			model = getSBVModelProvider().getSharedModel(modelPath);
		} catch (CoreException e) {
			return runnable.failedLoad(e);
		} catch (Throwable t) {
			return runnable.failedLoad(new CoreException(Logging.newStatus(EpocEnginePlugin.getDefault(), t)));
		}
		ISBVView view = null;
		if (model == null) {
			return runnable.failedLoad(null);
		}
		try {
			view = model.createView(null);
			return runnable.run(view);
		} finally {
			if (view != null)
				view.dispose();
			getSBVModelProvider().releaseSharedModel(model);
		}
	}

	
	/**
	 * Get a read-only copy of data for the given MMP view
	 * with the given configuration, and run user code using the model.
	 * <p>
	 * The data may be cached.  It will throw exceptions if any attempts are made
	 * to modify the contents.  #runWithMMPView() should be used if there is
	 * any need to modify the MMP.
	 * <p>
	 * If the model cannot be loaded or an exception is thrown, the runnable's 
	 * #failedLoad() is called.
	 * @param modelPath workspace-relative path
	 * @param viewConfiguration configuration for MMP (may not be null)
	 * @param runnable the code to run when the model is loaded (or fails)
	 * @return result from runnable#run
	 */
	public static Object runWithMMPData(IPath modelPath, 
				IMMPViewConfiguration viewConfiguration,
				IMMPDataRunnable runnable) {
		
		if (mmpViewDataCache == null) {
			mmpViewDataCache = new ViewDataCache<IMMPOwnedModel, IMMPModel, IMMPView, IMMPData>(
					getMMPModelProvider(), MMP_CACHE_SIZE);
		}

		if (runnable instanceof ViewRunnableAdapter) {
			((ViewRunnableAdapter) runnable).setModelPath(modelPath);
		}

		IMMPData data = null;
		try {
			data = mmpViewDataCache.getData(modelPath, viewConfiguration);
			
			if (data == null) {
				return runnable.failedLoad(null);
			}
		} catch (CoreException e) {
			return runnable.failedLoad(e);
		}
		
		return runnable.run(data);
	}

	/**
	 * Get a read-only copy of data for the given bld.inf view
	 * with the given configuration, and run user code using the model.
	 * <p>
	 * The data may be cached.  It will throw exceptions if any attempts are made
	 * to modify the contents.  #runWithBldInfView() should be used if there is
	 * any need to modify the bld.inf.
	 * <p>
	 * If the model cannot be loaded or an exception is thrown, the runnable's 
	 * #failedLoad() is called.
	 * @param modelPath workspace-relative path
	 * @param viewConfiguration configuration for bld.inf (may not be null)
	 * @param runnable the code to run when the model is loaded (or fails)
	 * @return result from runnable#run
	 */
	public static Object runWithBldInfData(IPath modelPath, 
				IViewConfiguration viewConfiguration,
				IBldInfDataRunnable runnable) {
		
		if (bldInfViewDataCache == null) {
			bldInfViewDataCache = new ViewDataCache<IBldInfOwnedModel, IBldInfModel, IBldInfView, IBldInfData>(
					getBldInfModelProvider(), BLDINF_CACHE_SIZE);
		}

		if (runnable instanceof ViewRunnableAdapter) {
			((ViewRunnableAdapter) runnable).setModelPath(modelPath);
		}

		IBldInfData data = null;
		try {
			data = bldInfViewDataCache.getData(modelPath, viewConfiguration);
			
			if (data == null) {
				return runnable.failedLoad(null);
			}
		} catch (CoreException e) {
			return runnable.failedLoad(e);
		}
		
		return runnable.run(data);
	}
	
	/**
	 * Get a read-only copy of data for the given image makefile view
	 * with the given configuration, and run user code using the model.
	 * <p>
	 * The data may be cached.  It will throw exceptions if any attempts are made
	 * to modify the contents.  #runWithImageMakefileView() should be used if there is
	 * any need to modify the bld.inf.
	 * <p>
	 * If the model cannot be loaded or an exception is thrown, the runnable's 
	 * #failedLoad() is called.
	 * @param modelPath workspace-relative path
	 * @param viewConfiguration configuration for makefile (may not be null)
	 * @param runnable the code to run when the model is loaded (or fails)
	 * @return result from runnable#run
	 */
	public static Object runWithImageMakefileData(IPath modelPath, 
				IImageMakefileViewConfiguration viewConfiguration,
				IImageMakefileDataRunnable runnable) {
		
		if (imageMakefileViewDataCache == null) {
			imageMakefileViewDataCache = new ViewDataCache<IImageMakefileOwnedModel, IImageMakefileModel, IImageMakefileView, IImageMakefileData>(
					getImageMakefileModelProvider(), IMAGE_MAKEFILE_CACHE_SIZE);
		}

		if (runnable instanceof ViewRunnableAdapter) {
			((ViewRunnableAdapter) runnable).setModelPath(modelPath);
		}

		IImageMakefileData data = null;
		try {
			data = imageMakefileViewDataCache.getData(modelPath, viewConfiguration);
			
			if (data == null) {
				return runnable.failedLoad(null);
			}
		} catch (CoreException e) {
			return runnable.failedLoad(e);
		}
		
		return runnable.run(data);
	}
	

}
