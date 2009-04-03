/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.test.srcmapping;

import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IMessageListener;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.ComponentSDKSelector;
import com.nokia.sdt.sourcegen.IComponentSourceGen;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.utils.*;

import org.eclipse.core.runtime.*;
import org.osgi.framework.Version;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Validate that all the S60 components have valid source mappings
 * (also checking well-formedness to ensure we're not missing broken
 * components altogether)
 * <p>
 * Also test sourceGen while we're at it.  Plan to move this to the
 * right place soon.
 * 
 *
 */
public class SrcMappingTestS60Components extends SrcMappingBase {
	public void testS60() throws Exception {
		// forcibly reload
		ComponentProvider provider = new ComponentProvider();
		
		final StringBuffer buffer = new StringBuffer();
		
		// listen to log messages
		ILogListener loglistener = new ILogListener() {

			public void logging(IStatus status, String plugin) {
				if (status.getMessage().contains(".series60.")) {
					buffer.append(status.getMessage());
					buffer.append("\n");
				}
			}
			
		};
		Logging.addListener(loglistener);
		
		// listen to messages from other arenas
		IMessageListener listener = new IMessageListener() {
			public boolean isHandlingMessage(IMessage msg) {
				return true;
			}

			public void emitMessage(IMessage msg) {
				buffer.append(msg.getMessage());
				buffer.append("\n");
			}
			
		};
		MessageReporting.addListener(listener);

		try {
			runComponents(provider, buffer, new Version(2, 6, 0));
			runComponents(provider, buffer, new Version(2, 8, 0));
			runComponents(provider, buffer, new Version(3, 0, 0));
		} finally {
			Logging.removeListener(loglistener);
			MessageReporting.removeListener(listener);
		}
		
		if (buffer.length() > 0)
			TestCase.fail(buffer.toString());
		else
			checkNoMessages(); // last resort
		
	}
	
	private void runComponents(ComponentProvider provider, StringBuffer buffer, Version version) throws Exception {
		ComponentSDKSelector selector = new ComponentSDKSelector
			(SymbianModelUtils.S60_SDK, version);

		ComponentSetResult result = provider.queryComponents(selector);
		
		// some will fail, from the test plugin
		MultiStatus status = (MultiStatus) result.getStatus();
		if (status != null) {
			IStatus[] kids = status.getChildren();
			for (int i = 0; i < kids.length; i++) {
				if (kids[i].getMessage().contains(".series60.")) {
					buffer.append(kids[i].getMessage());
					buffer.append("\n");
				}
			}
		}
	
		IComponentSet set = result.getComponentSet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			IComponent component = (IComponent) iter.next();
			getSourceMapping(component);
			getSourceGen(component);
		}
		
	}

	/**
	 * @param component
	 */
	private void getSourceGen(IComponent component) {
		IComponentSourceGen csg = (IComponentSourceGen) component.getAdapter(IComponentSourceGen.class);
		if (csg != null)
			csg.getSourceGenInfo();
	}
}
