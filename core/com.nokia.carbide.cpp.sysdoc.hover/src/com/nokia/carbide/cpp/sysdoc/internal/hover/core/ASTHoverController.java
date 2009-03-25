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
package com.nokia.carbide.cpp.sysdoc.internal.hover.core;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IName;
import org.eclipse.cdt.core.dom.ast.DOMException;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPBinding;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.ProblemBinding;
import org.eclipse.core.runtime.CoreException;

import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.AsynchronousLookup;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.sdk.SDKController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.DevLibContent;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * Class used to obtain AST information such as binding the APIs. After
 * resolving APIs binding, it delagates resolved API to content fetcher to
 * display in popup browser.
 */
public class ASTHoverController {

	public ASTHoverController() {
	}


	public Object getASTHoverInfo(ITranslationUnit tu, int offset,
			int length) {
		try {

			Logger.logDebug("offset="+offset+ " length="+length );
			if (!HoverManager.getInstance().isEnabled()) {
				return null;
			}
			
			IIndex index = CCorePlugin.getIndexManager().getIndex(
					tu.getCProject());

			IASTTranslationUnit asttu = tu.getAST(index,
					ITranslationUnit.AST_SKIP_INDEXED_HEADERS);
			IASTNode node = asttu.getNodeSelector(null).findEnclosingNode(
					offset, length);

			
			if (node == null) {
				return null;
			}
			Future<DevLibContent> computeAsynchronousInput = computeAsynchronousInput(node);
			if (computeAsynchronousInput != null) {
				return computeAsynchronousInput;
			}

		} catch (CoreException ce) {
			Logger.logError(ce);
		}
		return null;
	}
	
	
	
	/**
	 * @param node
	 * @return a handle onto the future input for the specified AST node
	 */
	protected Future<DevLibContent> computeAsynchronousInput(IASTNode node) {
		if (node instanceof IASTName) {

			IASTName name = (IASTName) node;

			IBinding binding = name.resolveBinding();
			binding = name.getBinding();
			if (binding == null)
				return null;
			if (!HoverBindingTypeAcceptor.accepted(name, binding)) {
				return null;
			}
			Future<DevLibContent> ret = computeAsynchronousInput(binding);
			return ret;

		}
		return null;
	}

	/**
	 * @param binding
	 * @return a handle onto the future input for the specified binding
	 */
	protected Future<DevLibContent> computeAsynchronousInput(IBinding binding) {

		if (binding instanceof ProblemBinding) {
			return null;
		}

		String fqn = getFQN(binding);

		Logger.logDebug("fqn:" + fqn);
		if (fqn != null) {
			Callable<DevLibContent> callable = new AsynchronousLookup(fqn);
			FutureTask<DevLibContent> ft = new FutureTask<DevLibContent>(
					callable);
			new Thread(ft).start();
			return ft;
		}
		return null;
	}

	/**
	 * @param binding
	 * @return the fully qualified name of the specified binding delimited by ::
	 */
	private static String getFQN(IBinding binding) {
		String fqn = null;

		try {

			ICPPBinding cppbinding = binding instanceof ICPPBinding ? (ICPPBinding) binding
					: null;

			if (cppbinding != null) {
				StringBuilder sb = new StringBuilder();
				cppbinding.getLinkage().getLinkageName();
				for (String name : cppbinding.getQualifiedName()) {
					sb.append("::" + name);
				}
				fqn = sb.substring(2);
			} else {
				fqn = binding.getName();
			}

		} catch (DOMException de) {
			Logger.logError(de);
		} catch (Exception e) {
			Logger.logError(e);
		}
		return fqn;
	}

	/**
	 * 
	 * An filter method checking if hovered API is a Symbian API. If an API is
	 * defined in Symbian SDK dir, then it is assumed that it is an Symbian API.
	 * 
	 */
	private static class HoverBindingTypeAcceptor {
		public static boolean accepted(IASTName name, IBinding binding) {

			IName[] definitions = name.getTranslationUnit().getDefinitions(
					binding);
			String definitionFileName = null;
			for (IName def : definitions) {
				definitionFileName = def.getFileLocation().getFileName();
			}

			String declarionFileName = null;
			IName[] declarations = name.getTranslationUnit().getDeclarations(
					binding);
			for (IName declaration : declarations) {
				declarionFileName = declaration.getFileLocation().getFileName();
			}

			Logger.logDebug("definition place:" + definitionFileName);
			Logger.logDebug("declation place:" + declarionFileName);

			if (definitionFileName == null && declarionFileName == null) {
				return true;
			}

			boolean resourceInSDKDirectory;
			try {
				resourceInSDKDirectory = SDKController.getInstance()
						.isResourceInSDKDirectory(declarionFileName,
								definitionFileName);
			} catch (Exception e) {
				Logger.logError(e);
				return true;
			}
			Logger.logDebug(name + ": resourceInSDKDirectory "
					+ resourceInSDKDirectory);
			return resourceInSDKDirectory;
		}
	}
}
