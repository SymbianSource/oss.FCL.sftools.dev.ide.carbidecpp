/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************
 *	NOTE: Copied from GEF sources to use internal SetValueCommand and ResetValueCommand
 *******************************************************************************/

package com.nokia.sdt.uidesigner.derived.ui;

import com.nokia.sdt.uidesigner.ui.command.ResetValueCommand;
import com.nokia.sdt.uidesigner.ui.command.SetValueCommand;

import org.eclipse.gef.commands.*;
import org.eclipse.ui.views.properties.*;

import java.util.*;

/**
 * <p>
 * UndoablePropertySheetEntry provides undo support for changes made to
 * IPropertySources by the
 * {@link org.eclipse.ui.views.properties.PropertySheetViewer}. Clients can
 * construct a {@link org.eclipse.ui.views.properties.PropertySheetPage} and use
 * this class as the root entry. All changes made to property sources displayed
 * on that page will be done using the provided command stack.
 * <p>
 * <b>NOTE:</b> If you intend to use an IPropertySourceProvider for a
 * PropertySheetPage whose root entry is an instance of of
 * UndoablePropertySheetEntry, you should set the IPropertySourceProvider on
 * that root entry, rather than the PropertySheetPage.
 */
public abstract class UndoablePropertySheetEntry extends PropertySheetEntry {

	protected CommandStackListener commandStackListener;

	protected CommandStack stack;

	protected UndoablePropertySheetEntry() {
	}

	/**
	 * Constructs the root entry using the given command stack.
	 * 
	 * @param stack
	 *            the command stack
	 * @since 3.1
	 */
	public UndoablePropertySheetEntry(CommandStack stack) {
		setCommandStack(stack);
	}
	

	/**
	 * @see org.eclipse.ui.views.properties.PropertySheetEntry#createChildEntry()
	 */
	protected abstract PropertySheetEntry createChildEntry();

	/**
	 * @param entry current PropertySheetEntry
	 * @param propertySource IPropertySource
	 * @param setValueCommand SetValueCommand
	 * @return Command extended command or null
	 */
	protected abstract Command getExtendedCommand(UndoablePropertySheetEntry entry, 
													IPropertySource propertySource, 
														Command command);

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySheetEntry#dispose()
	 */
	public void dispose() {
		if (stack != null)
			stack.removeCommandStackListener(commandStackListener);
		super.dispose();
	}

	protected CommandStack getCommandStack() {
		// only the root has, and is listening too, the command stack
		if (getParent() != null)
			return ((UndoablePropertySheetEntry) getParent()).getCommandStack();
		return stack;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySheetEntry#resetPropertyValue()
	 */
	public void resetPropertyValue() {
		CompoundCommand cc = new CompoundCommand();
		ResetValueCommand restoreCmd;

		if (getParent() == null)
			// root does not have a default value
			return;

		// Use our parent's values to reset our values.
		boolean change = false;
		Object[] objects = getParent().getValues();
		for (int i = 0; i < objects.length; i++) {
			IPropertySource source = getPropertySource(objects[i]);
			if (source.isPropertySet(getDescriptor().getId())) {
				// source.resetPropertyValue(getDescriptor()getId());
				restoreCmd = new ResetValueCommand();
				restoreCmd.setTarget(source);
				restoreCmd.setPropertyId(getDescriptor().getId());
				Command extendedCommand = getExtendedCommand(this, source, restoreCmd);
				if (extendedCommand != null)
					cc.add(extendedCommand);
				else
					cc.add(restoreCmd);
				change = true;
			}
		}
		if (change) {
			getCommandStack().execute(cc);
			refreshFromRoot();
		}
	}

	protected void setCommandStack(CommandStack stack) {
		this.stack = stack;
		commandStackListener = new CommandStackListener() {
			public void commandStackChanged(EventObject e) {
				refreshFromRoot();
			}
		};
		stack.addCommandStackListener(commandStackListener);
	}

	/**
	 * @see PropertySheetEntry#valueChanged(PropertySheetEntry)
	 */
	protected void valueChanged(PropertySheetEntry child) {
		valueChanged((UndoablePropertySheetEntry) child,
				new ForwardUndoCompoundCommand());
	}
	
	protected void valueChanged(UndoablePropertySheetEntry child, CompoundCommand command) {
		CompoundCommand cc = new CompoundCommand();
		command.add(cc);

		SetValueCommand setCommand;
		for (int i = 0; i < getValues().length; i++) {
			setCommand = new SetValueCommand(child.getDisplayName());
			IPropertySource propertySource = getPropertySource(getValues()[i]);
			setCommand.setTarget(propertySource);
			setCommand.setPropertyId(child.getDescriptor().getId());
			// setPropertyValue creates the undo value so must be called after setTarget and setPropertyId
			setCommand.setPropertyValue(child.getValues()[i]);
			// check for extender
			Command extendedCommand = getExtendedCommand(child, propertySource, setCommand);
			if (extendedCommand != null)
				cc.add(extendedCommand);
			else
				cc.add(setCommand);
		}

		// inform our parent
		if (getParent() != null)
			((UndoablePropertySheetEntry) getParent()).valueChanged(this, command);
		else {
			// I am the root entry
			stack.execute(command);
		}
	}

    /* (non-Javadoc)
     * @see org.eclipse.ui.views.properties.IPropertySheetEntry#getChildEntries()
     */
    public IPropertySheetEntry[] getChildEntries() {
    	IPropertySheetEntry[] entries = super.getChildEntries();
    	List inList = Arrays.asList(entries);
    	List outList = new ArrayList();
    	for (Iterator iter = inList.iterator(); iter.hasNext();) {
			UndoablePropertySheetEntry entry = (UndoablePropertySheetEntry) iter.next();
			String category = entry.getCategory();
			if ((category == null) || !category.equals("*HIDDEN*")) //$NON-NLS-1$
				outList.add(entry);
			else
				entry.dispose();
		}
    	
    	return (IPropertySheetEntry[]) outList.toArray(new IPropertySheetEntry[outList.size()]);
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.uidesigner.derived.ui.PropertySheetEntry#getDescriptor()
	 */
	public IPropertyDescriptor getDescriptor() {
		// was protected
		return super.getDescriptor();
	}

}
