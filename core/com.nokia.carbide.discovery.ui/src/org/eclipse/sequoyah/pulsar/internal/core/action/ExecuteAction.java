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
 *  Gustavo de Paula (Motorola) - Add change permission in a linux OS
 */

package org.eclipse.sequoyah.pulsar.internal.core.action;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.p2.engine.spi.ProvisioningAction;
import org.eclipse.sequoyah.pulsar.internal.core.action.execution.ExecutionFactory;
import org.eclipse.sequoyah.pulsar.internal.core.action.execution.ExecutionHandler;

import com.nokia.carbide.discovery.ui.Activator;

public class ExecuteAction extends ProvisioningAction {

    public static final String ACTION_EXECUTE = "execute"; //$NON-NLS-1$
    private static final String PARM_EXECUTABLE = "executable"; //$NON-NLS-1$

    /* (non-Javadoc)
     * @see org.eclipse.equinox.internal.provisional.p2.engine.ProvisioningAction#execute(java.util.Map)
     */
    @Override
    public IStatus execute(Map<String, Object> parameters) {
        String executable = (String) parameters.get(PARM_EXECUTABLE);
        if (executable == null)
            return Activator.makeErrorStatus(MessageFormat.format(
                    "The \"{0}\" parameter was not set in the \"{1}\" action", PARM_EXECUTABLE,
                    ACTION_EXECUTE), null);

//        IInstallableUnit iu = (IInstallableUnit) parameters
//                .get(ActionConstants.PARM_IU);
//        if (executable.equals(ActionConstants.PARM_ARTIFACT)) {
//            IArtifactKey artifactKey = iu.getArtifacts();
//
//            IFileArtifactRepository downloadCache;
//            try {
//                downloadCache = Util.getDownloadCacheRepo();
//            } catch (ProvisionException e) {
//                return e.getStatus();
//            }
//            File fileLocation = downloadCache.getArtifactFile(artifactKey);
//            if ((fileLocation == null) || !fileLocation.exists())
//                return Activator.makeErrorStatus(MessageFormat.format(
//                        Messages.ExecuteAction_MissingArtifactError,
//                        artifactKey), null);
//            executable = fileLocation.getAbsolutePath();
//        }
        try {
            ExecutionHandler handler = ExecutionFactory.getExecutionHandler(
                    System.getProperty("os.name"), executable);

            IStatus status = handler.handleExecution();

            if (!status.isOK()) {
                return status;
            }
        } catch (Exception e) {
            return Activator.makeErrorStatus(MessageFormat.format(
                    "could not execute \"{0}\"", executable), e);
        }

        return Status.OK_STATUS;
    }

    /* (non-Javadoc)
     * @see org.eclipse.equinox.internal.provisional.p2.engine.ProvisioningAction#undo(java.util.Map)
     */
    @Override
    public IStatus undo(Map<String, Object> parameters) {
        return Activator.makeErrorStatus(
                "undo not supported for exectutables", null);
    }
}
