/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.dm;

import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.sourcegen.ISourceFormatting;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.utils.*;

import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.*;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

/**
 * Source formatting rules derived from project settings.
 * 
 *
 */
public class WorkspaceSourceFormatting implements ISourceFormatting, IPropertyChangeListener, IDisposable, IPreferenceChangeListener {

    /** Copied from CDT
     * see org.eclipse.cdt.internal.ui.editor.CEditor.SPACES_FOR_TABS
     */
    private static final String SPACES_FOR_TABS = "spacesForTabs";  //$NON-NLS-1$
    
    private boolean usingTabs;
    private int indentSpaces;

	private String prefsEndOfLine;
	private String endOfLine;

	//private IProject project;

	private IEclipsePreferences runtimePreferences;

    /**
     * 
     */
    public WorkspaceSourceFormatting(IProject project) {
        CUIPlugin.getDefault().getCombinedPreferenceStore().addPropertyChangeListener(this);
        
        //this.project = project;
        this.usingTabs = true;
        this.indentSpaces = 4;
        this.prefsEndOfLine = this.endOfLine = "\n"; //$NON-NLS-1$
        
        // @see LineDelimiterEditor
        IScopeContext scopeContext;

        scopeContext = new InstanceScope();
        
		runtimePreferences = scopeContext.getNode(Platform.PI_RUNTIME);
		runtimePreferences.addPreferenceChangeListener(this);
		
        rereadSettings();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IDisposable#dispose()
     */
    public void dispose() {
        CUIPlugin.getDefault().getCombinedPreferenceStore().removePropertyChangeListener(this);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceFormatting#getEOL()
     */
    public String getEOL() {
    	return endOfLine; 
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceFormatting#setEOL(java.lang.String)
     */
    public void setEOL(String eol) {
    	if (eol == null)
    		eol = prefsEndOfLine;
    	this.endOfLine = eol;

    	// NOTE: do not support MacOS-style line endings since
		// CDT is utterly unprepared to deal with these, 
		// and crashes everywhere
		//if (endOfLine.equals("\r")) //$NON-NLS-1$
		//	endOfLine = "\n"; //$NON-NLS-1$
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceFormatting#isUsingTabs()
     */
    public boolean isUsingTabs() {
        return usingTabs;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.ISourceFormatting#getIndentSpaces()
     */
    public int getIndentSpaces() {
        return indentSpaces;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getProperty().equals(SPACES_FOR_TABS)
                || event.getProperty().equals(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH)) {
            rereadSettings();
        }
    }

    
    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener#preferenceChange(org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent)
     */
    public void preferenceChange(PreferenceChangeEvent event) {
    	if (event.getKey().equals(Platform.PREF_LINE_SEPARATOR))
    		rereadSettings();
    }

    /**
     * Read CDT editor settings for information about tabbing and indentation.
     */
    private void rereadSettings() {
        IPreferenceStore cprefs = CUIPlugin.getDefault().getCombinedPreferenceStore();
        usingTabs = !cprefs.getBoolean(SPACES_FOR_TABS);
        indentSpaces = cprefs.getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);
        
        endOfLine = runtimePreferences.get(Platform.PREF_LINE_SEPARATOR, null);
		
		if (endOfLine == null)
			endOfLine = System.getProperty("line.separator"); //$NON-NLS-1$
		
		// NOTE: do not support MacOS-style line endings since
		// CDT is utterly unprepared to deal with these, 
		// and crashes everywhere
		if (endOfLine.equals("\r")) { //$NON-NLS-1$
			endOfLine = "\n"; //$NON-NLS-1$
			Message msg = new Message(IMessage.WARNING,
            		new MessageLocation(ResourcesPlugin.getWorkspace().getRoot().getLocation()),
                    "WorkspaceSourceFormatting.IgnoringMacOSLineEndingSetting", //$NON-NLS-1$
                    Messages.getString("WorkspaceSourceFormatting.IgnoringMacOSLineEndingSetting"), //$NON-NLS-1$
                    new String[0]);
            MessageReporting.emitMessage(msg); 
    	}
		
		prefsEndOfLine = endOfLine;
    }

    
}
