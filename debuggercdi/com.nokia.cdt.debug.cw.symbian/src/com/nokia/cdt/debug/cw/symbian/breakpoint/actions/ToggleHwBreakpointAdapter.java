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
package com.nokia.cdt.debug.cw.symbian.breakpoint.actions;

import org.eclipse.cdt.core.IAddress;
import org.eclipse.cdt.debug.core.CDIDebugModel;
import org.eclipse.cdt.debug.core.model.ICAddressBreakpoint;
import org.eclipse.cdt.debug.core.model.ICBreakpointType;
import org.eclipse.cdt.debug.core.model.ICLineBreakpoint;
import org.eclipse.cdt.debug.internal.ui.CDebugUIUtils;
import org.eclipse.cdt.debug.internal.ui.IInternalCDebugUIConstants;
import org.eclipse.cdt.debug.internal.ui.actions.BreakpointLocationVerifier;
import org.eclipse.cdt.debug.internal.ui.actions.ToggleBreakpointAdapter;
import org.eclipse.cdt.debug.internal.ui.views.disassembly.DisassemblyEditorInput;
import org.eclipse.cdt.debug.internal.ui.views.disassembly.DisassemblyView;
import org.eclipse.cdt.debug.ui.CDebugUIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.freescale.cdt.debug.cw.core.cdi.BreakpointManager;

public class ToggleHwBreakpointAdapter extends ToggleBreakpointAdapter {
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.actions.IToggleBreakpointsTarget#toggleLineBreakpoints(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 * This function is overridden only for the purpose of setting a breakpoint attribute to hardware.
	 * 
	 * TODO: there's a hardware breakpoint type, but we're setting the type as regular and then another attribute
	 *       as hardware.  I don't know the history around this so for now I'm just going to make it work as it did
	 *       before, but fix the fact that the wrong CDIDebugModel.create... methods are being called with CDT 5.0
	 *       changes.  But it looks like perhaps we don't need this class anymore?
	 */
	public void toggleLineBreakpoints( IWorkbenchPart part, ISelection selection ) throws CoreException {
		String errorMessage = null;
		if ( part instanceof ITextEditor ) {
			ITextEditor textEditor = (ITextEditor)part;
			IEditorInput input = textEditor.getEditorInput();			
			
			if ( input == null ) {
				errorMessage = Messages.getString( "ToggleHwBreakpointAdapter.Empty_editor_1" ); //$NON-NLS-1$
			}
			else {
				IDocument document = textEditor.getDocumentProvider().getDocument( input );
				if ( document == null ) {
					errorMessage = Messages.getString( "ToggleHwBreakpointAdapter.Missing_document_1" ); //$NON-NLS-1$
				}
				else {
					IResource resource = getResource( textEditor );
					if ( resource == null ) {
						errorMessage = Messages.getString( "ToggleHwBreakpointAdapter.Missing_resource_1" ); //$NON-NLS-1$
					}
					else {
						BreakpointLocationVerifier bv = new BreakpointLocationVerifier();
						int lineNumber = bv.getValidLineBreakpointLocation( document, ((ITextSelection)selection).getStartLine() );
						if ( lineNumber == -1 ) {
							errorMessage = Messages.getString( "ToggleHwBreakpointAdapter.Invalid_line_1" ); //$NON-NLS-1$
						}
						else {
							String sourceHandle = getSourceHandle( input );
							ICLineBreakpoint breakpoint = CDIDebugModel.lineBreakpointExists( sourceHandle, resource, lineNumber );
							if ( breakpoint != null ) {
								DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint( breakpoint, true );
							}
							else {
								ICLineBreakpoint lineBreakpoint = CDIDebugModel.createLineBreakpoint( sourceHandle, 
																									resource,
																									ICBreakpointType.REGULAR,
																									lineNumber, 
																									true, 
																									0, 
																									"", //$NON-NLS-1$
																									true );
								if (lineBreakpoint != null) {
									setHwBreakAttribute(lineBreakpoint);
								}
							}
							return;
						}
					}
				}
			}
		}
		else if ( part instanceof DisassemblyView ) {
			IEditorInput input = ((DisassemblyView)part).getInput();
			if ( !(input instanceof DisassemblyEditorInput) ) {
				errorMessage = Messages.getString( "ToggleHwBreakpointAdapter.Empty_editor_1" ); //$NON-NLS-1$
			}
			else {
				BreakpointLocationVerifier bv = new BreakpointLocationVerifier();
				int lineNumber = bv.getValidAddressBreakpointLocation( null, ((ITextSelection)selection).getStartLine() );
				if ( lineNumber == -1 ) {
					errorMessage = Messages.getString( "ToggleHwBreakpointAdapter.Invalid_line_1" ); //$NON-NLS-1$
				}
				else {
					IAddress address = ((DisassemblyEditorInput)input).getAddress( lineNumber );
					if ( address == null ) {
						errorMessage = Messages.getString( "ToggleHwBreakpointAdapter.Invalid_line_1" ); //$NON-NLS-1$						
					}
					else {
						ICLineBreakpoint breakpoint = ((DisassemblyEditorInput)input).breakpointExists( address );
						if ( breakpoint != null ) {
							DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint( breakpoint, true );
						}
						else {
							String module = ((DisassemblyEditorInput)input).getModuleFile();
							IResource resource = getAddressBreakpointResource( ((DisassemblyEditorInput)input).getSourceFile() );
							String sourceHandle = getSourceHandle( input );
							ICAddressBreakpoint addressBreakpoint = CDIDebugModel.createAddressBreakpoint( module,
																   										sourceHandle, 
																   										resource,
																   										ICBreakpointType.REGULAR,
																   										((DisassemblyEditorInput)input).getSourceLine( lineNumber ),
																   										address, 
																   										true, 
																   										0, 
																   										"", //$NON-NLS-1$
																   										true );
							if (addressBreakpoint != null) {
								setHwBreakAttribute(addressBreakpoint);
							}
						}
						return;
					}
				}
			}
		}
		else {
			errorMessage = Messages.getString( "ToggleHwBreakpointAdapter.Operation_is_not_supported_1" ); //$NON-NLS-1$
		}
		throw new CoreException( new Status( IStatus.ERROR, CDebugUIPlugin.getUniqueIdentifier(), IInternalCDebugUIConstants.INTERNAL_ERROR, errorMessage, null ) );
	}
	
	private String getSourceHandle( IEditorInput input ) throws CoreException {
		return CDebugUIUtils.getEditorFilePath(input);
	}
	
	private IResource getAddressBreakpointResource( String fileName ) {
		if ( fileName != null ) {
			IPath path = new Path( fileName );
			if ( path.isValidPath( fileName ) ) {
				IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation( path );
				if ( files.length > 0 )
					return files[0];
			}
		}
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	private void setHwBreakAttribute(IBreakpoint bkpt) {
		
		try {
			IMarker bkptMarker = bkpt.getMarker();
			bkptMarker.setAttribute(BreakpointManager.IS_BREAK_TYPE_HARDWARE, true);
			
		} catch (CoreException ce) {
			// Should never happen, just incase.
			ce.printStackTrace();
		}					
	}
}
