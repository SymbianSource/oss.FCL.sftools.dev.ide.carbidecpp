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

package com.nokia.sdt.sourcegen.contributions;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.sourcegen.ISourceGenTemplate;

import java.text.MessageFormat;

/**
 * A single contribution
 * 
 *
 */
public class Contribution implements IContribution {

    String phase;
    String form;
    ILocation location;
    String id;
    String text;
    private ISourceGenTemplate template;
    private boolean definesLocation;
    private int indentAdjust;
    private boolean isContination;
	String mode;
	final private MessageLocation messageLocation;
	final private String componentId;
    
    /**
     *  Create a contribution at the given location 
     */
    public Contribution(ILocation location, String componentId, MessageLocation messageLocation) {
        Check.checkArg(location);
        Check.checkArg(messageLocation);
        
//        this.location = location;
        location.addContribution(this);
        this.componentId = componentId;
        this.messageLocation = messageLocation;
    }

    /**
     *  Create a contribution for the given phase;
     *  location must be set later 
     */
    public Contribution(String phase, String componentId, MessageLocation messageLocation) {
        Check.checkArg(phase);
        Check.checkArg(messageLocation);

        this.phase = phase;
        this.componentId = componentId;
        this.messageLocation = messageLocation;
    }

    /**
     * @param componentInstance
     */
    public Contribution(MessageLocation messageLocation, String componentId) {
        Check.checkArg(messageLocation);
        this.componentId = componentId;
        this.messageLocation = messageLocation;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String cleaned = TextUtils.formatForDump(TextUtils.cleanUpXMLText(text));
        
        return "<Contribution defines=" + definesLocation + " @" + location  //$NON-NLS-1$ //$NON-NLS-2$
        + " form="+ form + " = '" + cleaned + "'>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#getTemplate()
     */
    public ISourceGenTemplate getTemplate() {
        return template;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#setTemplate(com.nokia.sdt.sourcegen.ISourceGenTemplate)
     */
    public void setTemplate(ISourceGenTemplate template) {
        this.template = template;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#setPhase(java.lang.String)
     */
    public void setPhase(String phase) {
        this.phase = phase;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#getPhase()
     */
    public String getPhase() {
        return phase;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#setForm(java.lang.String)
     */
    public void setForm(String form) {
        this.form = form;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#getForm()
     */
    public String getForm() {
        return form;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#getLocation()
     */
    public ILocation getLocation() {
        return location;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#setLocation(com.nokia.sdt.sourcegen.contributions.ILocation)
     */
    public void setLocation(ILocation location) {
        Check.checkState(this.location == null);
        this.location = location;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#setId(java.lang.String)
     */
    public void setId(String id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#getId()
     */
    public String getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#setText(java.lang.String)
     */
    public void setText(String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#setFormattedText(java.lang.String, java.lang.Object[])
     */
    public void setFormattedText(String format, Object[] args) {
        this.text = MessageFormat.format(format, args);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#getText()
     */
    public String getText() {
        return text;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#isValid()
     */
    public boolean isValid() {
        return location != null && text != null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#definesLocation()
     */
    public boolean definesLocation() {
        return definesLocation;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#setDefinesLocation()
     */
    public void setDefinesLocation(boolean flag) {
        this.definesLocation = flag;
        
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#getIndentAdjust()
     */
    public int getIndentAdjust() {
        return indentAdjust;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#indentAdjust(int)
     */
    public void indentAdjust(int adjust) {
        this.indentAdjust += adjust;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#createMessageLocation()
     */
    public MessageLocation getMessageLocation() {
    	return messageLocation;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.IContribution#getComponentId()
     */
    public String getComponentId() {
    	return componentId;
    }
    
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.contributions.IContribution#setContinuation()
	 */
	public void setContinuation() {
		isContination = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.contributions.IContribution#isContinuation()
	 */
	public boolean isContinuation() {
		return isContination;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.contributions.IContribution#getMode()
	 */
	public String getMode() {
		return mode;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.contributions.IContribution#setMode(java.lang.String)
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
