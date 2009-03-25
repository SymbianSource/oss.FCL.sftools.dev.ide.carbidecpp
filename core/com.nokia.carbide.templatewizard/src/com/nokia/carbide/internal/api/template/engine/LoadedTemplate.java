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


package com.nokia.carbide.internal.api.template.engine;

import com.nokia.carbide.internal.api.templatewizard.ui.IExtraPagesProvider;
import com.nokia.carbide.internal.template.gen.Template.*;
import com.nokia.carbide.internal.templatewizard.process.Parameter;
import com.nokia.carbide.template.engine.*;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.carbide.templatewizard.process.IProcess;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.StatusBuilder;

import org.eclipse.core.runtime.*;
import org.eclipse.swt.widgets.Display;

import java.text.MessageFormat;
import java.util.*;

/**
 * Implementation of ILoadedTemplate 
 *
 */
public class LoadedTemplate implements ILoadedTemplate {
	private ITemplate template;
	private TemplateType templateType;
	private HashMap<String, String> metadata;
	private IExtraPagesProvider extraPagesProvider;

	public LoadedTemplate(ITemplate template, 
					TemplateType templateType) {
		this.template = template;
		this.templateType = templateType;
	}
	
	public TemplateType getTemplateType() {
		return templateType;
	}

	@Override
	public String toString() {
		if (templateType == null)
			return super.toString();
		
		return "Loaded Template " + template.toString(); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ILoadedTemplate#getDescription()
	 */
	public String getDescription() {
		return template.getLocalizedString(getTemplateType().getDescription());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ILoadedTemplate#getLabel()
	 */
	public String getLabel() {
		return template.getLocalizedString(getTemplateType().getLabel());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ILoadedTemplate#getTemplate()
	 */
	public ITemplate getTemplate() {
		return template;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ITemplate#runProcesses(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IStatus runProcesses(IProgressMonitor monitor) {
		StatusBuilder statusBuilder = new StatusBuilder(TemplateWizardPlugin.getDefault());
		List<ProcessType> processes = getTemplateType().getProcess();
		monitor.beginTask("", processes.size());
		for (ProcessType processType : processes) {
			String processId = processType.getName();
			if (processId == null)
				processId = processType.getClass_();
			Class processClass = null;
			try {
				processClass = TemplateProcessEngine.loadClassFromProcess(
						processType, template.getTemplateUrl(), template.getBundle());
			} 
			catch (Exception e) {
				TemplateEngine.logError(null, e);
				statusBuilder.add(Logging.newSimpleStatus(TemplateWizardPlugin.getDefault(), 
						IStatus.ERROR, null, e));
			}
			IProcess process = null;
			if (processClass != null) {
				try {
					monitor.subTask("Running template processes...");
					process = (IProcess) processClass.newInstance();
				} catch (Exception e) {
					String error = MessageFormat.format(Messages.getString("Template.ProcessInstantiateError"),  //$NON-NLS-1$
							new Object[] { processId });
					TemplateEngine.logError(error, e);
					statusBuilder.add(Logging.newSimpleStatus(TemplateWizardPlugin.getDefault(), 
							IStatus.ERROR, error, e));
				}
			}
			if (process != null) {
				try {
					List<IParameter> parametersList = createParametersList(processType);
					if (process.mustRunInUIThread())
						runInUIThread(process, parametersList, new SubProgressMonitor(monitor, 1));
					else
						process.process(template, parametersList, new SubProgressMonitor(monitor, 1));
				} 
				catch (CoreException e) {
					String error = MessageFormat.format(Messages.getString("Template.ProcessRunError"),  //$NON-NLS-1$
							new Object[] { processId });
					TemplateEngine.logError(error, e);
					statusBuilder.add(e.getStatus());
				}
			}
		}
		monitor.done();
		return statusBuilder.getErrorCount() == 0 ? Status.OK_STATUS :
			statusBuilder.createMultiStatus(Messages.getString("Template.ProcessesRunError"), new Object[0]); //$NON-NLS-1$
	}
	
	private List<IParameter> createParametersList(ProcessType processType) {
		List<IParameter> parameters = new ArrayList();
		List<ParameterType> parameterTypes = processType.getParameter();
		for (ParameterType parameterType : parameterTypes) {
			parameters.add(new Parameter(parameterType, template));
		}
		
		return parameters;
	}


	private void runInUIThread(final IProcess process, final List<IParameter> parametersList,
			final IProgressMonitor monitor) throws CoreException {
		final CoreException[] coreExceptionHolder = new CoreException[1];
		coreExceptionHolder[0] = null;
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {
					process.process(template, parametersList, monitor);
				} catch (CoreException e) {
					coreExceptionHolder[0] = e;
				}
			}
		});
		if (coreExceptionHolder[0] != null)
			throw coreExceptionHolder[0];
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ILoadedTemplate#getMetadata()
	 */
	public synchronized Map<String, String> getMetadata() {
		if (metadata == null) {
			metadata = new HashMap<String, String>();
			TemplateType templateType = getTemplateType();
			for (Object obj : templateType.getMetadata()) {
				if (obj instanceof MetadataType) {
					MetadataType meta = (MetadataType) obj;
					metadata.put(meta.getName(), meta.getValue1());
				}
			}
		}
		return metadata;
	}
	
	public void setExtraPagesProvider(IExtraPagesProvider extraPagesProvider) {
		this.extraPagesProvider = extraPagesProvider;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.template.engine.ILoadedTemplate#createLoadedTemplateUI(com.nokia.carbide.template.engine.IPersistedSettingStorage)
	 */
	public ILoadedTemplateUI createLoadedTemplateUI() {
		return new LoadedTemplateUI(template, templateType, extraPagesProvider);
	}
}
