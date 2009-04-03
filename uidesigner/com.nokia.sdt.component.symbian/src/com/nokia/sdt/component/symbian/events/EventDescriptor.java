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
package com.nokia.sdt.component.symbian.events;

import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.event.HandlerMethodInformation;
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.datamodel.util.*;
import com.nokia.sdt.emf.component.EventType;
import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.sourcegen.SourceLocation;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.workspace.IProjectContext;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.*;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventDescriptor implements IEventDescriptor {
	
	private EventType event;
	private Component component;
	private ILocalizedStrings strings;
	
	// regexp to match {*}. The strings inside are NodePath expressions or a fixed token representing a user specified
	// name, i.e {name}
	static final String REGEXP_PATTERN = "\\{(handler-name|\\(([^)]+)\\)|(\\b\\w+\\b)\\(([^)]+)\\))\\}"; //$NON-NLS-1$
	static final Pattern regexp = Pattern.compile(REGEXP_PATTERN);
	// when a {*} pattern is the fixed string {handler-name} then it is replaced with the user specified
	// handler name.
	static final String USER_SPECIFIED_NAME = "handler-name"; //$NON-NLS-1$
	static final String LOWER = "lower"; //$NON-NLS-1$
	static final String UPPER = "upper"; //$NON-NLS-1$
	static final String TITLE = "title"; //$NON-NLS-1$
		
	EventDescriptor(EventType event, Component component) {
		Check.checkArg(event);
		Check.checkArg(component);
		this.event = event;
		this.component = component;
		this.strings = component.getLocalizedStrings();
	}
	
	// consider them equal if the event id and category are the same
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof EventDescriptor) {
			EventDescriptor other = (EventDescriptor) obj;
			result = 
				ObjectUtils.equals(event.getName(), other.event.getName()) &&
				ObjectUtils.equals(event.getCategory(), other.event.getCategory());
		}
		return result;
	}
	
	public int hashCode() {
		String key = event.getName() + event.getCategory();
		return key.hashCode();
	}

	public String getId() {
		return event.getName();
	}

	public String getDisplayText() {
		String result = strings.checkPercentKey(event.getDisplayName());
		if (result == null) {
			// in the absence of a specified display name before
			// using the internal name look for a key with the internal
			// name.
			String defaultKey = event.getName();
			if (strings.hasString(defaultKey)) {
				result = strings.getString(defaultKey);
			}
			else {
				result = event.getName();
			}
		}
		return result;
	}

	public String getCategory() {
		String result = strings.checkPercentKey(event.getCategory());
		if (!TextUtils.isEmpty(result)) {
			IComponentSet cs = component.getComponentSet();
			if (cs != null)
				result = cs.getProvider().getCategoryText(result);
		}
		return result;
	}

	public String getDescription() {
		String result = null;
		String key = event.getDescriptionKey();
		if (TextUtils.strlen(key) > 0) {
			result = strings.getString(key);
		}
		return result;
	}

	public String getHelpKey() {
		return event.getHelpKey();
	}

	public String getGroup() {
		return event.getGroup();
	}
	
	public HandlerMethodInformation generateHandlerMethodInfo(
			IComponentInstance instance, String userSpecifiedName) {
		
		// Get the simple handler name, either using the user-specified name
		// or loading from the template.
		String handlerName;
		if (userSpecifiedName != null) {
			handlerName = userSpecifiedName;
		}
		else {
			String displayTemplate = event.getHandlerNameTemplate();
			handlerName = expandTemplate(displayTemplate, null, instance);
		}
		Check.checkState(TextUtils.strlen(handlerName) > 0);
		
		return new HandlerMethodInformation(handlerName);
	}
	/**
	 * Uses regular expressions to locate NodePath expressions
	 * and replace them with their resolved values.
	 * 
	 */
	private String expandTemplate(String template, String userSpecifiedName, IComponentInstance instance) {
		String result = null;
		StringBuffer buf = new StringBuffer();
		if (TextUtils.strlen(template) > 0) {
			Matcher m = regexp.matcher(template);
			
			/*
			 * The regular expression uses groups to identify what
			 * was matched and the distinct subparts
			 * group 0: the complete match, i.e. {...} including braces
			 * group 1: everything inside the braces
			 * group 2: a simple nodepath expression when upper|title|lower
			 * 			is not used. Does not include parentheses
			 * group 3: the "function" used, i.e. upper|lower|title
			 * group 4: the nodepath expression when a function is used.
			 * 			Does not include parentheses
			 */
			
			while (m.find()) {
				if (ObjectUtils.equals(m.group(1), USER_SPECIFIED_NAME)) {
					// handle {handler-name} substitution
					m.appendReplacement(buf, userSpecifiedName);
				}
				else {
					String resolvedExpr = null;
					String function = m.group(3);
					if (TextUtils.strlen(function) > 0) {
						// handle {title(expr)} | {upper(expr)} | {lower(expr)}
						resolvedExpr = resolveNodePath(m.group(4), instance);
						if (TITLE.equals(function)) {
							resolvedExpr = TextUtils.titleCase(resolvedExpr);
						}
						else if (LOWER.equals(function)) {
							resolvedExpr = resolvedExpr.toLowerCase();
						}
						else if (UPPER.equals(function)) {
							resolvedExpr = resolvedExpr.toUpperCase();
						}
						else {
							String fmt = Messages.EventDescriptor_5;
							Object params[] = {getId(), function};
							String msg = MessageFormat.format(fmt, params);
							ComponentSystemPlugin.log(null, msg);
						}
					}
					else {
						// handle {(expr)}
						resolvedExpr = resolveNodePath(m.group(2), instance);
					}
					m.appendReplacement(buf, resolvedExpr);
				}
			}
			m.appendTail(buf);
			result = new String(buf);
		}		
		return result;
	}
	
	private String resolveNodePath(String expr, IComponentInstance instance) {
		String result = null;
		if (TextUtils.strlen(expr) > 0) {
			NodePath np = new NodePath(expr);
			IPropertySource ps = ModelUtils.getPropertySource(instance.getEObject());
			NodePathLookupResult npResult = np.readFromPath(instance, ps, false);
			if (npResult.result != null) {
				result = npResult.result.toString();
			}
			else {
				ComponentSystemPlugin.log(npResult.status);
				result = "<error>"; //$NON-NLS-1$
			}
		}
		else
			result = "<error>"; //$NON-NLS-1$
		return result;
	}

	public String isValidHandlerName(String proposedName) {
		String result = null;
		if (!NamePropertySupport.isLegalName(proposedName)) {
			result = NamePropertySupport.illegalNameMessage(proposedName);
		}
		return result;
	}

	public IStatus gotoHandlerCode(IEventBinding binding, boolean newBinding) {
		try {
			IComponentInstance instance = ModelUtils.getComponentInstance(binding.getOwner());
			IProjectContext pc = instance.getDesignerDataModel().getProjectContext();
			ISourceGenProvider provider = null;
			if (pc != null)
				provider = pc.getSourceGenProvider();
			if (provider == null) {
				return Logging.newStatus(ComponentSystemPlugin.getDefault(),
						IStatus.ERROR,
						Messages.EventDescriptor_4);
			}
			
			SourceLocation location = provider.lookupEventBindingSource(binding);

			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			
			// get the default editor for the file type
			IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(location.file.getName());
			if (desc == null) {
				return Logging.newStatus(ComponentSystemPlugin.getDefault(),
						IStatus.ERROR,
						MessageFormat.format(Messages.EventDescriptor_3,
								new Object[] { location.file.getLocation() }));
			}
			
			IEditorPart part = page.openEditor(new FileEditorInput(location.file), desc.getId());
			
			// now go to the correct position
			if (part instanceof ITextEditor) {
				ITextEditor textEditor = (ITextEditor) part;
				
				textEditor.setHighlightRange(location.offset, location.length, true);
				textEditor.selectAndReveal(location.insertOffset, 0);
				textEditor.setFocus();
			}
			return null;
			
		} catch (CoreException e) {
			return e.getStatus();
		}
	}

}
