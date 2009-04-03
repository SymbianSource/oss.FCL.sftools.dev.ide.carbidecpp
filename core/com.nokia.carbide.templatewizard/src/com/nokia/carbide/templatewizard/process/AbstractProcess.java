/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.templatewizard.process;

import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.List;

/**
 * An abstract base class to enable implementing 
 * <code>com.nokia.carbide.templatewizard.process.IProcess</code> 
 * Includes methods for initialization, failure handling and accessing parameters.
 */
public abstract class AbstractProcess implements IProcess {

	private static final String NO_PARAMETER_ERROR = Messages.getString("AbstractProcess.MissingRequiredParameterError"); //$NON-NLS-1$
	private static final String NO_ATTR_ERROR = Messages.getString("AbstractProcess.MissingRequiredAttributeError"); //$NON-NLS-1$
	private boolean runInUIThread;

	
	/**
	 * The class attribute of the &lt;process&gt; element.
	 * @return <code>java.lang.String</code>
	 */
	protected String getProcessName() {
		return getClass().getCanonicalName();
	}

	/**
	 * Must be implemented by subclasses to provide the {@link Plugin}
	 * needed to construct a {@link CoreException}.
	 * 
	 * @return <code>org.eclipse.core.runtime.Plugin</code>
	 */
	protected abstract Plugin getPlugin();
	
	/**
	 * Called by subclasses to encapsulate throwing a CoreException from 
	 * {@link IProcess#process(ITemplate, List, IProgressMonitor)}
	 * 
	 * @param message the String message of the exception
	 * @param t a Throwable
	 * @throws CoreException
	 */
	protected void fail(String message, Throwable t) throws CoreException {
		throw new CoreException(Logging.newStatus(getPlugin(), IStatus.ERROR, message, t));
	}
	
	/**
	 * Called by subclasses to encapsulate throwing a CoreException from 
	 * {@link IProcess#process(ITemplate, List, IProgressMonitor)}
	 * 
	 * @param message the String message of the exception
	 * @throws CoreException
	 */
	protected void fail(String message) throws CoreException {
		throw new CoreException(Logging.newStatus(getPlugin(), IStatus.ERROR, message));
	}
	
	/**
	 * Called by subclasses to encapsulate throwing a CoreException from 
	 * {@link IProcess#process(ITemplate, List, IProgressMonitor)}
	 * 
	 * @param condition throws CoreException if false
	 * @param message the String message of the exception
	 * @throws CoreException
	 */
	protected void failIfFalse(boolean condition, String message) throws CoreException {
		if (!condition)
			fail(message);
	}
	
	/**
	 * Called by subclasses to encapsulate throwing a CoreException from 
	 * {@link IProcess#process(ITemplate, List, IProgressMonitor)}
	 * 
	 * @param object throws CoreException if null
	 * @param message the String message of the exception
	 * @throws CoreException
	 */
	protected void failIfNull(Object object, String message) throws CoreException {
		failIfFalse(object != null, message);
	}

	/**
	 * Must be implemented by subclasses.<br>
	 * Called by {@link IProcess#process(ITemplate, List, IProgressMonitor)}
	 * to separate initialization from processing code.
	 * 
	 * @param template the ITemplate argument to process
	 * @param parameters the parameter list argument to process
	 * @throws CoreException
	 * @see com.nokia.carbide.templatewizard.process.IProcess#process(com.nokia.carbide.template.engine.ITemplate, java.util.List, org.eclipse.core.runtime.IProgressMonitor) 
	 */
	protected abstract void init(ITemplate template, List<IParameter> parameters) throws CoreException;

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.process.IProcess#process(com.nokia.carbide.template.engine.ITemplate, java.util.List, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		init(template, parameters);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.process.IProcess#mustRunInUIThread()
	 */
	public boolean mustRunInUIThread() {
		return runInUIThread;
	}

	/**
	 * Can be called in constructor to set the return value of {@link IProcess#mustRunInUIThread()}
	 * @param runInUIThread the return value of {@link IProcess#mustRunInUIThread()}
	 */
	public void setRunInUIThread(boolean runInUIThread) {
		this.runInUIThread = runInUIThread;
	}
	
	/**
	 * Encapsulates getting a singleton {@link IParameter} that may or may not exist,<br>
	 * returning <code>null</code> if none is found.<br>
	 * Returns the first {@link IParameter} found by name.
	 * Optional parameters can be accessed via the parameters list.
	 * 
	 * @param parameterName the name of the parameter
	 * @param parameters the list of parameters
	 * @return the first IParameter with name parameterName or <code>null</code>
	 */
	protected IParameter findParameterByName(String parameterName, List<IParameter> parameters) 
																				throws CoreException {
		Check.checkArg(parameters);
		
		IParameter foundParameter = null;
		
		for (IParameter parameter : parameters) {
			if (parameter.getName().equals(parameterName)) {
				foundParameter = parameter;
				break;
			}
		}
		
		return foundParameter;
	}
	
	/**
	 * Encapsulates getting a singleton {@link IParameter} that is required to exist,<br>
	 * or throwing a {@link CoreException}, if none is found.<br>
	 * Returns the first {@link IParameter} found by name.
	 * Optional parameters can be accessed via the parameters list.
	 * 
	 * @param parameterName the name of the parameter
	 * @param parameters the list of parameters
	 * @return the first IParameter with name parameterName
	 * @throws CoreException if parameter is not found
	 */
	protected IParameter getRequiredParameterByName(String parameterName, List<IParameter> parameters) 
																				throws CoreException {
		Check.checkArg(parameters);
		String error = MessageFormat.format(NO_PARAMETER_ERROR, 
				new Object[] { getProcessName(), parameterName });
		
		IParameter foundParameter = findParameterByName(parameterName, parameters);

		failIfNull(foundParameter, error);
		
		return foundParameter;
	}
	
	/**
	 * Encapsulates getting a required attribute from an {@link IParameter} by name,<br>
	 * or throwing a {@link CoreException}, if none is found.<br>
	 * Optional attributes can be accessed via {@link IParameter#getAttributeValue(String)}
	 * 
	 * @param parameter the IParameter
	 * @param attributeName the attribute name
	 * @return the String attribute from parameter with attributeName 
	 * @throws CoreException if attribute is not found
	 */
	protected String getRequiredAttributeFromParameter(IParameter parameter, String attributeName) 
																				throws CoreException {
		Check.checkArg(parameter);
		String error = MessageFormat.format(NO_ATTR_ERROR, 
				new Object[] { getProcessName(), parameter.getName(), attributeName });
		
		String attribute = parameter.getAttributeValue(attributeName);
		failIfNull(attribute, error);
		
		return attribute;
	}
}



