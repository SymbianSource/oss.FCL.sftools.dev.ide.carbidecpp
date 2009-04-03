/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg;

import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * Internal class for providing PKG model services - should be moved to
 * EpocEnginePlugin, when becomes API
 */
public class PKGModelHelper {

	private static IModelProvider<IPKGOwnedModel, IPKGModel> pkgModelProvider;

	/**
	 * Get the provider that manages access to PKG files in the workspace
	 * 
	 * @return provider
	 */
	public static synchronized IModelProvider<IPKGOwnedModel, IPKGModel> getPKGModelProvider() {
		if (pkgModelProvider == null) {
			pkgModelProvider = ModelProviderFactory
					.createModelProvider(new PKGModelFactory());
		}

		return pkgModelProvider;
	}

	/**
	 * Get a shared instance of the given PKG model, create a view with the
	 * given configuration, and run user code using the model.
	 * <p>
	 * The model and view are automatically released.
	 * <p>
	 * If the model cannot be loaded or an exception is thrown, the runnable's
	 * #failedLoad() is called.
	 * 
	 * Note that the PKG model/view are currently experimental and internal.
	 * These are not intended to be used by third parties, and are subject to
	 * change without notice.
	 * 
	 * @param modelPath
	 *            workspace-relative path
	 * @param viewConfiguration
	 *            configuration for pkg file (may not be null)
	 * @param runnable
	 *            the code to run when the model is loaded (or fails)
	 * @return result from runnable#run
	 */
	public static Object runWithPKGView(IPath modelPath,
			IViewConfiguration viewConfiguration, IPKGViewRunnable runnable) {
		IPKGModel model;
		if (runnable instanceof ViewRunnableAdapter) {
			((ViewRunnableAdapter) runnable).setModelPath(modelPath);
		}
		try {
			model = getPKGModelProvider().getSharedModel(modelPath);
		} catch (CoreException e) {
			return runnable.failedLoad(e);
		} catch (Throwable t) {
			return runnable.failedLoad(new CoreException(Logging.newStatus(
					EpocEnginePlugin.getDefault(), t)));
		}
		IPKGView view = null;
		if (model == null) {
			return runnable.failedLoad(null);
		}
		try {
			view = model.createView(viewConfiguration);
			return runnable.run(view);
		} finally {
			if (view != null)
				view.dispose();
			getPKGModelProvider().releaseSharedModel(model);
		}
	}

}
