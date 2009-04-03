/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.images.uriHandlers;

import com.nokia.sdt.symbian.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A scheme handler which matches a regex for the URI but cannot render any images.
 * 
 *
 */
public class UIQSystemURIImageInfoSchemeHandler extends NullURIImageInfoSchemeHandler {

	private final static Pattern UIQ_THEMED_QUERY_PATTERN = 
		Pattern.compile("((frame=\\d+|index=\\d+|index_dimmed=\\d+)&?)+"); //$NON-NLS-1$
	private final static Pattern UIQ_ICON_OR_APPICON_QUERY_PATTERN = 
		Pattern.compile("size=(small|medium|large)"); //$NON-NLS-1$
	private final static Pattern UIQ_HEX_ICON_PATTERN = 
		Pattern.compile("[0-9a-fA-F]+"); //$NON-NLS-1$
	
	private final static Map<String, Pattern> pathPatterns = new HashMap<String, Pattern>();
	private final static Map<String, Pattern> queryPatterns = new HashMap<String, Pattern>();
	
	static {
		pathPatterns.put("uiq_themed", UIQ_HEX_ICON_PATTERN); //$NON-NLS-1$
		pathPatterns.put("uiq_icon", UIQ_HEX_ICON_PATTERN); //$NON-NLS-1$
		pathPatterns.put("uiq_appicon", UIQ_HEX_ICON_PATTERN); //$NON-NLS-1$
		queryPatterns.put("uiq_themed", UIQ_THEMED_QUERY_PATTERN); //$NON-NLS-1$
		queryPatterns.put("uiq_icon", UIQ_ICON_OR_APPICON_QUERY_PATTERN); //$NON-NLS-1$
		queryPatterns.put("uiq_appicon", UIQ_ICON_OR_APPICON_QUERY_PATTERN); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IURIImageInfoSchemeHandler#validate(com.nokia.sdt.datamodel.images.IProjectImageInfo, java.lang.String, java.lang.String, java.lang.String)
	 */
	public IStatus validate(String scheme, String path, String query) {
		/*
uiq_themed:00000000?frame=0&index=0&index_dimmed=0
uiq_icon:00000000?size=small|medium|large
uiq_appicon:00000000?size=small|medium|large
		*/
		Pattern pattern = pathPatterns.get(scheme);
		Check.checkState(pattern != null);
		
		if (!pattern.matcher(path).matches())
			return createStatus(IStatus.ERROR,
					MessageFormat.format(Messages.getString("UIQSystemURIImageInfoSchemeHandler.InvalidPathPatternFormat"), //$NON-NLS-1$
					new Object[] { path, pattern.toString() }));

		pattern = queryPatterns.get(scheme);
		Check.checkState(pattern != null);

		if (query == null)
			query = ""; //$NON-NLS-1$
		
		if (!pattern.matcher(query).matches())
			return createStatus(IStatus.ERROR,
					MessageFormat.format(Messages.getString("UIQSystemURIImageInfoSchemeHandler.InvalidQueryFormat"), //$NON-NLS-1$
					new Object[] { query, pattern.toString() }));
		
		return Status.OK_STATUS;
	}


}
