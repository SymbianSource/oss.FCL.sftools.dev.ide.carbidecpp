/**
 * Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
 * All rights reserved.
 * This component and the accompanying materials are made available
 * under the terms of the License "Eclipse Public License v1.0"
 * which accompanies this distribution, and is available
 * at the URL "http://www.eclipse.org/legal/epl-v10.html".
 *
 * Contributors:
 * 	David Dubrow
 *  Euclides Neto (Motorola) - Externalize strings.
 *  Gustavo de Paula (Motorola) - Add change permission in a linux OS  
 */

package org.eclipse.sequoyah.pulsar.internal.core.action;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.internal.p2.touchpoint.natives.Util;
import org.eclipse.equinox.internal.p2.touchpoint.natives.actions.ActionConstants;
import org.eclipse.equinox.internal.p2.touchpoint.natives.actions.UnzipAction;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.sequoyah.pulsar.internal.core.action.execution.ExecutionFactory;
import org.eclipse.sequoyah.pulsar.internal.core.action.execution.ExecutionHandler;

import com.nokia.carbide.discovery.ui.Activator;

@SuppressWarnings("restriction")
public class UnzipAndExecuteAction extends UnzipAction {

    public static final String ACTION_UNZIPANDEXECUTE = "unzipAndExecute"; //$NON-NLS-1$
    private static final String PARM_EXECUTABLE = "executable"; //$NON-NLS-1$

    /* (non-Javadoc)
     * @see org.eclipse.equinox.internal.p2.touchpoint.natives.actions.UnzipAction#execute(java.util.Map)
     */
    @Override
    public IStatus execute(Map<String, Object> parameters) {
        IStatus status = super.execute(parameters);
        if (!status.isOK()) {
            return status;
        }

        String executable = (String) parameters.get(PARM_EXECUTABLE);
        if (executable == null) {
            return Activator.makeErrorStatus(MessageFormat.format(
            		"The \"{0}\" parameter was not set in the \"{1}\" action", PARM_EXECUTABLE,
                    ACTION_UNZIPANDEXECUTE), null);
        }

        IProfile profile = (IProfile) parameters
                .get(ActionConstants.PARM_PROFILE);
        String installFolder = Util.getInstallFolder(profile);
        if (installFolder == null) {
            return Activator.makeErrorStatus(
                    "The profile did not have an install folder", null);
        } else {
            IPath path = new Path(installFolder);
            path = path.append(executable);
            executable = path.toOSString();
            try {
                ExecutionHandler handler = ExecutionFactory.getExecutionHandler(
                        System.getProperty("os.name"), executable);

                IStatus status2 = handler.handleExecution();

                if (!status2.isOK()) {
                    return status;
                }
            } catch (Exception e) {
                return Activator.makeErrorStatus(MessageFormat.format(
                		"could not execute \"{0}\"", executable), e);
            }
        }

        return Status.OK_STATUS;
    }

    /* (non-Javadoc)
     * @see org.eclipse.equinox.internal.p2.touchpoint.natives.actions.UnzipAction#undo(java.util.Map)
     */
    @Override
    public IStatus undo(Map<String, Object> parameters) {
        return Activator.makeErrorStatus(
        		"undo not supported for exectutables", null);
    }
}
