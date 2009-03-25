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

import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.sourcegen.ISourceGenTemplate;

import java.util.regex.Pattern;

/**
 * A single source contribution.  This represents an unbroken
 * string of text which is emitted as a block inside a location
 * in source.  Contributions needn't be constant; they can be
 * filled in with arguments at runtime via #setFormattedText().
 * <p>
 * A contribution is not valid until its location and text are
 * set.  A phase is a proxy for a location that a parent fills
 * in later.
 * 
 *
 */
public interface IContribution {
	/** Separator dividing form names in getForm()/setForm() */
    String FORM_SEPARATOR = ","; //$NON-NLS-1$

    /** mode that is used to force a contribution to the end of its location */
    String MODE_AT_END = "at-end";

    /** mode that indicates a contribution will be used to upgrade one version
     * of a component to another
     */
	Pattern MODE_UPGRADE_PATTERN = Pattern.compile("\\s*upgrade\\(\\s*(\\d+(?:\\.(?:\\d+)){0,3})\\s*,\\s*(\\d+(?:\\.(?:\\d+)){0,3})\\s*\\)\\s*"); //$NON-NLS-1$
    
    /**
     * Set the generating template -- may be null
     */
    void setTemplate(ISourceGenTemplate template);
    
    /**
     * Get the generating template -- may be null
     */
    ISourceGenTemplate getTemplate();
    

    /** Set the phase
     * @param phase a container-specific token used to collate contributions;
     * may be null
     */
    void setPhase(String phase);
    
    /** Get the phase */
    String getPhase();

    /** Set the form (a FORM_SEPARATOR-separated list of strings; "" by default)
     * @see #FORM_SEPARATOR 
     */
    void setForm(String form);

    /** Get the form (a FORM_SEPARATOR-separated list of strings or "") 
     * @see #FORM_SEPARATOR 
     */
    String getForm();

    /** Set the location */
    void setLocation(ILocation location);

    /** Get the location */
    ILocation getLocation();
    
    /** Set the identifier
     * @param id unique identifier, within a component, distinguishing
     * contribution from others (note: in &lt;template&gt; and
     * &lt;template-group&gt; id attributes may be shared, but in this
     * API they must be unique, e.g. with the template group name prepended);
     * may be null 
     */
    void setId(String id);
    
    /** Get the identifier (may be null) */
    String getId();
    
    /** Set the literal text (may not be null) */
    void setText(String text);
    
    /** Set the text by expanding the MessageFormat string (may not be null) */
    void setFormattedText(String format, Object[] args);
    
    /** Get the literal or formatted text (may be null until set) */
    String getText();

    /** Tell whether the contribution is valid, e.g., whether
     * the location and text are set */
    boolean isValid();

    /**
     * Set flag indicating that this contribution defines its location
     * @param b 
     */
    void setDefinesLocation(boolean b);
    
    /**
     * Tell whether the contribution defines its location
     */
    boolean definesLocation();
    
    /**
     * Adjust indentation for this contribution only.  
     * Note: the location controls the overall indentation.
     * @param adjust relative value (1 for extra indent, -1 for less indent) 
     */
    void indentAdjust(int adjust);
    
    /**
     * Get indentation adjustment
     */
    int getIndentAdjust();

    /**
     * Get a message location referring to the best location to
     * reference when reporting problems with sourcegen.
     * @return MessageLocation
     */
    MessageLocation getMessageLocation();

    /**
     * Get the component id responsible for this contribution, for error reporting
     * @return String
     */
    String getComponentId();
    
    /**
     * Mark the contribution as a continuation of others written
     * to the same location
     */
    void setContinuation();
    
    /**
     * Tell if the contribution is a continuation
     */
    boolean isContinuation();
    
    /**
     * Get the mode
     * @return mode string, may be null
     */
    String getMode();

    /**
     * Set the mode
     * @param mode mode string, may be null
     */
    void setMode(String mode);

}
