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
package com.nokia.sdt.symbian.workspace.impl;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.dm.S60ModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.nokia.sdt.workspace.IProjectContext;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier.IRunWithModelAction;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;
import java.util.Collections;

/**
 * Routines for use in managing design references from the {@link DesignFileDeletedJob} and {@link DesignFileMovedJob} jobs.
 */
abstract class DesignReferenceJobUtilities {
	
	static boolean isViewReferencedInApplication(final DesignerDataModelSpecifier viewDesignSpecifier,
			final String relativePath) {
		Boolean result = Boolean.FALSE;
		
		IProjectContext pc = viewDesignSpecifier.getProjectContext();
		if (pc != null) {
			ISymbianProjectContext spc = (ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);
			final IDesignerDataModelSpecifier rootSpecifier = spc.getRootModel();
			if (rootSpecifier != null) {
				result = (Boolean) rootSpecifier.runWithLoadedModelNoSourceGen(new IRunWithModelAction() {
					public Object run(LoadResult lr) {
						Boolean result = Boolean.FALSE;
						if (lr.getModel() != null) {
							EObject designRef = findDesignRefByPath(lr.getModel(), relativePath);
							result = Boolean.valueOf(designRef != null);
						}
						return result;
					}
				});
			}
		}
		return result.booleanValue();
	}

	static IStatus removeViewDesignReference(IProjectContext oldProjectContext,
			final String oldRelativePath, final IProgressMonitor monitor) {
		IStatus result = null;
		ISymbianProjectContext spc = (ISymbianProjectContext) oldProjectContext.getAdapter(ISymbianProjectContext.class);
		final IDesignerDataModelSpecifier rootSpecifier = spc.getRootModel();
		if (rootSpecifier != null) {
			boolean proceed = EditorServices.confirmSaveDirtyModels(Collections.singletonList(rootSpecifier));
			if (proceed) {
				result = (IStatus) rootSpecifier.runWithLoadedModel(new IRunWithModelAction() {
					public Object run(LoadResult lr) {
						IStatus result = null;
						if (lr.getModel() != null) {
							IDesignerDataModel model = lr.getModel();
							
							Display display = Display.getCurrent();
							Shell shell = null;
							if (display != null) {
								shell = display.getActiveShell();
							}
							result = model.validateEdit(shell);
							if (!result.isOK()) {
								return result;
							}
							
							EObject designRefObj = findDesignRefByPath(model,
									oldRelativePath);
							if (designRefObj != null) {
								Command command = model
								.createRemoveComponentInstancesCommand(Collections
										.singletonList(designRefObj));
								Check.checkContract(command != null
										&& command.canExecute());
								command.execute();
								
								command = S60ModelUtils.ensureInitialView(model);
								if (command != null) {
									Check.checkContract(command.canExecute());
									command.execute();
								}
								
								try {
									EditorServices.saveModels(Collections.singletonList(model), monitor);
									result = Status.OK_STATUS;
								} catch (Exception x) {
									SymbianPlugin.getDefault().log(x);
									String fmt = Messages.getString("JobUtilities.errSavingRootDesign"); //$NON-NLS-1$
									Object params[] = {rootSpecifier.getDisplayName()};
									String msg = MessageFormat.format(fmt, params);
									result = Logging.newSimpleStatus(SymbianPlugin.getDefault(), IStatus.ERROR, msg, x);
								}
							}
						}
						return result;
					}
				});
				oldProjectContext.getSourceGenProvider().updateProjectInfo();
			} else {
				result = Status.CANCEL_STATUS;
			}
		}
		return result;
	}

	static IStatus renameViewDesignReference(final DesignerDataModelSpecifier viewDesignSpecifier,
			final String oldRelativePath, final String newRelativePath,
			final IProgressMonitor monitor) {
		IStatus result = null;
		IProjectContext pc = viewDesignSpecifier.getProjectContext();
		ISymbianProjectContext spc = (ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);
		pc.getSourceGenProvider().updateDesignSpecifierInfo(viewDesignSpecifier);
		final IDesignerDataModelSpecifier rootSpecifier = spc.getRootModel();
		if (rootSpecifier != null) {
			boolean proceed = EditorServices.confirmSaveDirtyModels(Collections.singletonList(rootSpecifier));
			if (proceed) {
				result = (IStatus) rootSpecifier.runWithLoadedModel(new IRunWithModelAction() {
					public Object run(LoadResult lr) {
						IStatus result = null;
						if (lr.getModel() != null) {
							IDesignerDataModel model = lr.getModel();
							
							Display display = Display.getCurrent();
							Shell shell = null;
							if (display != null) {
								shell = display.getActiveShell();
							}
							result = model.validateEdit(shell);
							if (!result.isOK()) {
								return result;
							}
							
							EObject designRefObj = findDesignRefByPath(model,
									oldRelativePath);
							if (designRefObj != null) {
								IPropertySource designPS = ModelUtils
								.getPropertySource(designRefObj);
								designPS.setPropertyValue(
										SymbianModelUtils.DESIGNREF_DESIGNPATH,
										newRelativePath);
								try {
									EditorServices.saveModels(Collections.singleton(model), monitor);
									result = Status.OK_STATUS;
								} catch (Exception x) {
									SymbianPlugin.getDefault().log(x);
									String fmt = Messages.getString("JobUtilities.errSavingDesign"); //$NON-NLS-1$
									Object params[] = {rootSpecifier.getDisplayName()};
									String msg = MessageFormat.format(fmt, params);
									result = Logging.newSimpleStatus(SymbianPlugin.getDefault(), IStatus.ERROR, msg, x);
								}
							} else {
								String fmt = Messages.getString("JobUtilities.cantRenameViewNotReferenced"); //$NON-NLS-1$
								Object params[] = {viewDesignSpecifier.getDisplayName()};
								String msg = MessageFormat.format(fmt, params);
								result = Logging.newSimpleStatus(SymbianPlugin.getDefault(), IStatus.INFO, msg, null);
							}
						}
						return result;
					}
				});
				
				// then, save the design which has been renamed, so its sources are up-to-date
				if (result.isOK()) {
					proceed = EditorServices.confirmSaveDirtyModels(Collections.singletonList((IDesignerDataModelSpecifier) viewDesignSpecifier));
					if (proceed) {
						result = (IStatus) viewDesignSpecifier.runWithLoadedModel(new IRunWithModelAction() {
		
							public Object run(LoadResult lr) {
								IStatus result = null;
								if (lr.getModel() != null) {
									IDesignerDataModel model = lr.getModel();
									try {
										EditorServices.saveModels(Collections.singleton(model), monitor);
										result = Status.OK_STATUS;
									} catch (Exception x) {
										SymbianPlugin.getDefault().log(x);
										String fmt = Messages.getString("JobUtilities.errSavingDesign"); //$NON-NLS-1$
										Object params[] = {rootSpecifier.getDisplayName()};
										String msg = MessageFormat.format(fmt, params);
										result = Logging.newSimpleStatus(SymbianPlugin.getDefault(), IStatus.ERROR, msg, x);
									}
								}
								return result;
							}
						});
					}
				}				
			} else {
				result = Status.CANCEL_STATUS;
			}
		}
		return result;
	}

	static EObject findDesignRefByPath(IDesignerDataModel model, String path) {
		EObject result = null;
		EObject appUiObj = SymbianModelUtils.findAppUi(model);
		if (appUiObj != null) {
			IComponentInstance appUi = ModelUtils
					.getComponentInstance(appUiObj);
			for (EObject childObj : appUi.getChildren()) {
				if (SymbianModelUtils.isDesignReference(childObj)) {
					IPropertySource designPS = ModelUtils
							.getPropertySource(childObj);
					String childPath = (String) designPS
							.getPropertyValue(SymbianModelUtils.DESIGNREF_DESIGNPATH);
					if (ObjectUtils.equals(path, childPath)) {
						result = childObj;
						break;
					}
				}
			}
		}
		return result;
	}
	
	
}
