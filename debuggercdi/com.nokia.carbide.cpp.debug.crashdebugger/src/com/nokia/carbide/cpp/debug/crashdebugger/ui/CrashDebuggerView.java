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
package com.nokia.carbide.cpp.debug.crashdebugger.ui;

import java.math.BigInteger;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.console.IOConsoleViewer;
import org.eclipse.ui.part.ViewPart;

import com.nokia.carbide.cpp.debug.crashdebugger.CrashDebugger;
import com.nokia.carbide.cpp.debug.crashdebugger.CrashDebuggerConsole;
import com.nokia.carbide.cpp.debug.crashdebugger.HelpIds;
import com.nokia.carbide.cpp.debug.crashdebugger.ISessionChangedListener;
import com.nokia.carbide.cpp.debug.crashdebugger.Images;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerConsts;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerPlugin;

public class CrashDebuggerView extends ViewPart {
	// crash debugger commands
	private static final String FAULT_CMD = "f"; //$NON-NLS-1$

	private static final String HELP_CMD = "h"; //$NON-NLS-1$

	private static final String MEMORY_CMD = "m "; //$NON-NLS-1$

	private static final String MEMORY2_CMD = "z "; //$NON-NLS-1$

	private static final String OBJECT_BRIEF_CMD = "o "; //$NON-NLS-1$

	private static final String OBJECT_FULL_CMD = "q "; //$NON-NLS-1$

	private static final String INFO_CMD = "i"; //$NON-NLS-1$

	private static final String THREADS_CMD = "c 0"; //$NON-NLS-1$

	private static final String PROCESSES_CMD = "c 1"; //$NON-NLS-1$

	private static final String CHUNKS_CMD = "c 2"; //$NON-NLS-1$

	private static final String LIBRARIES_CMD = "c 3"; //$NON-NLS-1$

	private static final String SEMAPHORES_CMD = "c 4"; //$NON-NLS-1$

	private static final String MUTEXES_CMD = "c 5"; //$NON-NLS-1$

	private static final String TIMERS_CMD = "c 6"; //$NON-NLS-1$

	private static final String SERVERS_CMD = "c 7"; //$NON-NLS-1$

	private static final String SESSIONS_CMD = "c 8"; //$NON-NLS-1$

	private static final String LOGICAL_DEVICES_CMD = "c 9"; //$NON-NLS-1$

	private static final String PHYSICAL_DEVICES_CMD = "c A"; //$NON-NLS-1$

	private static final String CHANNELS_CMD = "c B"; //$NON-NLS-1$

	private static final String CHANGE_NOTIFIERS_CMD = "c C"; //$NON-NLS-1$

	private static final String UNDERTAKERS_CMD = "c D"; //$NON-NLS-1$

	private static final String MSG_QUEUES_CMD = "c E"; //$NON-NLS-1$

	private static final String PROPERTY_REFS_CMD = "c F"; //$NON-NLS-1$

	private static final String REGISTERS_CMD = "r"; //$NON-NLS-1$

	private IOConsoleViewer viewer;

	private Action terminateSession;

	private Action clearConsole;

	private Action cutAction;

	private Action copyAction;

	private Action pasteAction;

	private Action selectAllAction;

	// debug command actions
	private Action help_cmd;

	private Action fault_cmd;

	private Action memory_cmd;

	private Action memory2_cmd;

	private Action object_brief_cmd;

	private Action object_full_cmd;

	private Action info_cmd;

	private Action threads_cmd;

	private Action processes_cmd;

	private Action chunks_cmd;

	private Action libraries_cmd;

	private Action semaphores_cmd;

	private Action mutexes_cmd;

	private Action timers_cmd;

	private Action servers_cmd;

	private Action sessions_cmd;

	private Action logical_devices_cmd;

	private Action physical_devices_cmd;

	private Action channels_cmd;

	private Action change_notifiers_cmd;

	private Action undertakers_cmd;

	private Action msg_queues_cmd;

	private Action property_refs_cmd;

	private Action registers_cmd;

	// text selection listener, used to update selection dependant actions on
	// selection changes
	private ISelectionChangedListener selectionChangedListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			updateSelectionDependentActions();
		}
	};

	// session state listener, used to know when the session begins and ends
	private ISessionChangedListener sessionChangedListener = new ISessionChangedListener() {
		public void sessionStarted() {
			sessionChanged(true);
		}

		public void sessionOutputReceived(String output) {
			StyledText text = viewer.getTextWidget();
			text.append(output);
			text.setCaretOffset(text.getCharCount());
		}

		public void sessionEnded() {
			sessionChanged(false);
		}
	};

	public CrashDebuggerView() {
	}

	public void createPartControl(Composite parent) {
		CrashDebugger.addSessionChangedListener(sessionChangedListener);

		CrashDebuggerConsole console = CrashDebugger.getConsole();

		viewer = new IOConsoleViewer(parent, console);
		viewer.setInput(getViewSite());
		viewer.setDocument(console.getDocument());

		viewer.addTextListener(new ITextListener() {
			public void textChanged(TextEvent event) {
				if (event.getText().compareTo("\r\n") == 0) { //$NON-NLS-1$
					enterKeyPressed();
				}
			}
		});

		makeActions();
		hookContextMenu();
		contributeToActionBars();
		viewer.getSelectionProvider().addSelectionChangedListener(
				selectionChangedListener);
		updateSelectionDependentActions();

		// see if the session is already started and update the actions
		// accordingly
		sessionChanged(null != CrashDebugger.getSession());

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				HelpIds.CRASH_DEBUGGER_VIEW);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				CrashDebuggerView.this.fillMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillMenu(IMenuManager manager) {
		manager.add(fault_cmd);
		manager.add(info_cmd);
		manager.add(registers_cmd);
		manager.add(memory_cmd);
		manager.add(memory2_cmd);
		manager.add(object_brief_cmd);
		manager.add(object_full_cmd);
		manager.add(new GroupMarker("group")); //$NON-NLS-1$
		MenuManager showInSubMenu = new MenuManager(Messages
				.getString("CrashDebuggerView.27")); //$NON-NLS-1$
		showInSubMenu.add(threads_cmd);
		showInSubMenu.add(processes_cmd);
		showInSubMenu.add(chunks_cmd);
		showInSubMenu.add(libraries_cmd);
		showInSubMenu.add(semaphores_cmd);
		showInSubMenu.add(mutexes_cmd);
		showInSubMenu.add(timers_cmd);
		showInSubMenu.add(servers_cmd);
		showInSubMenu.add(sessions_cmd);
		showInSubMenu.add(logical_devices_cmd);
		showInSubMenu.add(physical_devices_cmd);
		showInSubMenu.add(channels_cmd);
		showInSubMenu.add(change_notifiers_cmd);
		showInSubMenu.add(undertakers_cmd);
		showInSubMenu.add(msg_queues_cmd);
		showInSubMenu.add(property_refs_cmd);
		manager.appendToGroup("group", showInSubMenu); //$NON-NLS-1$
		manager.add(help_cmd);

		manager.add(new Separator());
		manager.add(terminateSession);
		manager.add(clearConsole);

		manager.add(new Separator(IWorkbenchActionConstants.M_EDIT));
		manager.add(cutAction);
		manager.add(copyAction);
		manager.add(pasteAction);
		manager.add(selectAllAction);

		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(terminateSession);
		manager.add(clearConsole);
	}

	private void makeActions() {
		terminateSession = new Action() {
			public void run() {
				CrashDebugger.terminateSession();
				terminateSession.setEnabled(false);
			}
		};
		terminateSession.setText(Messages.getString("CrashDebuggerView.29")); //$NON-NLS-1$
		terminateSession.setImageDescriptor(Images.DESC_TERMINATE_ENABLED);
		terminateSession
				.setDisabledImageDescriptor(Images.DESC_TERMINATE_DISABLED);
		terminateSession.setToolTipText(Messages
				.getString("CrashDebuggerView.30")); //$NON-NLS-1$

		clearConsole = new Action() {
			public void run() {
				// there is a bug in IOConsole.clearConsole where after you clear it, it will
				// no longer accept any input.  the code below is a workaround for this.
				// CrashDebugger.getConsole().clearConsole();
			
				// clear the document, and then add the "." prompt
				viewer.getDocument().set(""); //$NON-NLS-1$
				StyledText text = viewer.getTextWidget();
				text.append("."); //$NON-NLS-1$
				text.setCaretOffset(text.getCharCount());
			}
		};
		clearConsole.setText(Messages.getString("CrashDebuggerView.31")); //$NON-NLS-1$
		clearConsole.setImageDescriptor(Images.DESC_CLEAR_ENABLED);
		clearConsole.setDisabledImageDescriptor(Images.DESC_CLEAR_DISABLED);
		clearConsole.setToolTipText(Messages.getString("CrashDebuggerView.32")); //$NON-NLS-1$

		cutAction = new Action() {
			public void run() {
				viewer.doOperation(ITextOperationTarget.CUT);
			}
		};
		cutAction.setText(Messages.getString("CrashDebuggerView.33")); //$NON-NLS-1$
		cutAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_CUT));
		cutAction.setDisabledImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_CUT_DISABLED));

		copyAction = new Action() {
			public void run() {
				viewer.doOperation(ITextOperationTarget.COPY);
			}
		};
		copyAction.setText(Messages.getString("CrashDebuggerView.34")); //$NON-NLS-1$
		copyAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_COPY));
		copyAction.setDisabledImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_COPY_DISABLED));

		pasteAction = new Action() {
			public void run() {
				viewer.doOperation(ITextOperationTarget.PASTE);
			}
		};
		pasteAction.setText(Messages.getString("CrashDebuggerView.35")); //$NON-NLS-1$
		pasteAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_PASTE));
		pasteAction.setDisabledImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_PASTE_DISABLED));

		selectAllAction = new Action() {
			public void run() {
				viewer.doOperation(ITextOperationTarget.SELECT_ALL);
			}
		};
		selectAllAction.setText(Messages.getString("CrashDebuggerView.36")); //$NON-NLS-1$

		fault_cmd = new Action() {
			public void run() {
				sendUICommand(FAULT_CMD);
			}
		};
		fault_cmd.setText(Messages.getString("CrashDebuggerView.37")); //$NON-NLS-1$
		fault_cmd.setToolTipText(Messages.getString("CrashDebuggerView.38")); //$NON-NLS-1$

		help_cmd = new Action() {
			public void run() {
				sendUICommand(HELP_CMD);
			}
		};
		help_cmd.setText(Messages.getString("CrashDebuggerView.39")); //$NON-NLS-1$
		help_cmd.setToolTipText(Messages.getString("CrashDebuggerView.40")); //$NON-NLS-1$

		memory_cmd = new Action() {
			public void run() {
				String address = ""; //$NON-NLS-1$
				String length = ""; //$NON-NLS-1$
				ISelection selection = viewer.getSelection();
				if (selection instanceof TextSelection) {
					String text = ((TextSelection) selection).getText();
					if (text.length() > 0) {
						try {
							if (text.toLowerCase().startsWith("0x")) { //$NON-NLS-1$
								text = text.substring(2, text.length());
							}
							BigInteger integer = new BigInteger(text, 16);
							address = integer.toString(16);

							// display 256 bytes by default
							length = "100"; //$NON-NLS-1$
						} catch (NumberFormatException e) {
						}
					}
					if (address.length() == 0) {
						// show the dialog to get the address and length
						DumpMemoryDialog dlg = new DumpMemoryDialog(
								getViewSite().getShell());
						if (dlg.open() == Window.OK) {
							address = dlg.getAddress();
							length = dlg.getLength();
						}
					}
				}
				if (address.trim().length() > 0 && length.trim().length() > 0) {
					sendUICommand(MEMORY_CMD + address + "+" + length); //$NON-NLS-1$
				}
			}
		};
		memory_cmd.setText(Messages.getString("CrashDebuggerView.46")); //$NON-NLS-1$
		memory_cmd.setToolTipText(Messages.getString("CrashDebuggerView.47")); //$NON-NLS-1$

		memory2_cmd = new Action() {
			public void run() {
				String address = ""; //$NON-NLS-1$
				String length = ""; //$NON-NLS-1$
				ISelection selection = viewer.getSelection();
				if (selection instanceof TextSelection) {
					String text = ((TextSelection) selection).getText();
					if (text.length() > 0) {
						try {
							if (text.toLowerCase().startsWith("0x")) { //$NON-NLS-1$
								text = text.substring(2, text.length());
							}
							BigInteger integer = new BigInteger(text, 16);
							address = integer.toString(16);

							// display 256 bytes by default
							length = "100"; //$NON-NLS-1$
						} catch (NumberFormatException e) {
						}
					}
					if (address.length() == 0) {
						// show the dialog to get the address and length
						DumpMemoryDialog dlg = new DumpMemoryDialog(
								getViewSite().getShell());
						if (dlg.open() == Window.OK) {
							address = dlg.getAddress();
							length = dlg.getLength();
						}
					}
				}
				if (address.trim().length() > 0 && length.trim().length() > 0) {
					sendUICommand(MEMORY2_CMD + address + "+" + length); //$NON-NLS-1$
				}
			}
		};
		memory2_cmd.setText(Messages.getString("CrashDebuggerView.53")); //$NON-NLS-1$
		memory2_cmd.setToolTipText(Messages.getString("CrashDebuggerView.54")); //$NON-NLS-1$

		object_brief_cmd = new Action() {
			public void run() {
				String address = ""; //$NON-NLS-1$
				ISelection selection = viewer.getSelection();
				if (selection instanceof TextSelection) {
					String text = ((TextSelection) selection).getText();
					if (text.length() > 0) {
						try {
							if (text.toLowerCase().startsWith("0x")) { //$NON-NLS-1$
								text = text.substring(2, text.length());
							}
							BigInteger integer = new BigInteger(text, 16);
							address = integer.toString(16);
						} catch (NumberFormatException e) {
						}
					}
					if (address.length() == 0) {
						// show the dialog to get the address
						AddressDialog dlg = new AddressDialog(getViewSite()
								.getShell());
						if (dlg.open() == Window.OK) {
							address = dlg.getAddress();
						}
					}
				}
				if (address.trim().length() > 0) {
					sendUICommand(OBJECT_BRIEF_CMD + address);
				}
			}
		};
		object_brief_cmd.setText(Messages.getString("CrashDebuggerView.57")); //$NON-NLS-1$
		object_brief_cmd.setToolTipText(Messages
				.getString("CrashDebuggerView.58")); //$NON-NLS-1$

		object_full_cmd = new Action() {
			public void run() {
				String address = ""; //$NON-NLS-1$
				ISelection selection = viewer.getSelection();
				if (selection instanceof TextSelection) {
					String text = ((TextSelection) selection).getText();
					if (text.length() > 0) {
						try {
							if (text.toLowerCase().startsWith("0x")) { //$NON-NLS-1$
								text = text.substring(2, text.length());
							}
							BigInteger integer = new BigInteger(text, 16);
							address = integer.toString(16);
						} catch (NumberFormatException e) {
						}
					}
					if (address.length() == 0) {
						// show the dialog to get the address
						AddressDialog dlg = new AddressDialog(getViewSite()
								.getShell());
						if (dlg.open() == Window.OK) {
							address = dlg.getAddress();
						}
					}
				}
				if (address.trim().length() > 0) {
					sendUICommand(OBJECT_FULL_CMD + address);

				}
			}
		};
		object_full_cmd.setText(Messages.getString("CrashDebuggerView.61")); //$NON-NLS-1$
		object_full_cmd.setToolTipText(Messages
				.getString("CrashDebuggerView.62")); //$NON-NLS-1$

		info_cmd = new Action() {
			public void run() {
				sendUICommand(INFO_CMD);
			}
		};
		info_cmd.setText(Messages.getString("CrashDebuggerView.63")); //$NON-NLS-1$
		info_cmd.setToolTipText(Messages.getString("CrashDebuggerView.64")); //$NON-NLS-1$

		threads_cmd = new Action() {
			public void run() {
				sendUICommand(THREADS_CMD);
			}
		};
		threads_cmd.setText(Messages.getString("CrashDebuggerView.65")); //$NON-NLS-1$
		threads_cmd.setToolTipText(Messages.getString("CrashDebuggerView.66")); //$NON-NLS-1$

		processes_cmd = new Action() {
			public void run() {
				sendUICommand(PROCESSES_CMD);
			}
		};
		processes_cmd.setText(Messages.getString("CrashDebuggerView.67")); //$NON-NLS-1$
		processes_cmd
				.setToolTipText(Messages.getString("CrashDebuggerView.68")); //$NON-NLS-1$

		chunks_cmd = new Action() {
			public void run() {
				sendUICommand(CHUNKS_CMD);
			}
		};
		chunks_cmd.setText(Messages.getString("CrashDebuggerView.69")); //$NON-NLS-1$
		chunks_cmd.setToolTipText(Messages.getString("CrashDebuggerView.70")); //$NON-NLS-1$

		libraries_cmd = new Action() {
			public void run() {
				sendUICommand(LIBRARIES_CMD);
			}
		};
		libraries_cmd.setText(Messages.getString("CrashDebuggerView.71")); //$NON-NLS-1$
		libraries_cmd
				.setToolTipText(Messages.getString("CrashDebuggerView.72")); //$NON-NLS-1$

		semaphores_cmd = new Action() {
			public void run() {
				sendUICommand(SEMAPHORES_CMD);
			}
		};
		semaphores_cmd.setText(Messages.getString("CrashDebuggerView.73")); //$NON-NLS-1$
		semaphores_cmd.setToolTipText(Messages
				.getString("CrashDebuggerView.74")); //$NON-NLS-1$

		mutexes_cmd = new Action() {
			public void run() {
				sendUICommand(MUTEXES_CMD);
			}
		};
		mutexes_cmd.setText(Messages.getString("CrashDebuggerView.75")); //$NON-NLS-1$
		mutexes_cmd.setToolTipText(Messages.getString("CrashDebuggerView.76")); //$NON-NLS-1$

		timers_cmd = new Action() {
			public void run() {
				sendUICommand(TIMERS_CMD);
			}
		};
		timers_cmd.setText(Messages.getString("CrashDebuggerView.77")); //$NON-NLS-1$
		timers_cmd.setToolTipText(Messages.getString("CrashDebuggerView.78")); //$NON-NLS-1$

		servers_cmd = new Action() {
			public void run() {
				sendUICommand(SERVERS_CMD);
			}
		};
		servers_cmd.setText(Messages.getString("CrashDebuggerView.79")); //$NON-NLS-1$
		servers_cmd.setToolTipText(Messages.getString("CrashDebuggerView.80")); //$NON-NLS-1$

		sessions_cmd = new Action() {
			public void run() {
				sendUICommand(SESSIONS_CMD);
			}
		};
		sessions_cmd.setText(Messages.getString("CrashDebuggerView.81")); //$NON-NLS-1$
		sessions_cmd.setToolTipText(Messages.getString("CrashDebuggerView.82")); //$NON-NLS-1$

		logical_devices_cmd = new Action() {
			public void run() {
				sendUICommand(LOGICAL_DEVICES_CMD);
			}
		};
		logical_devices_cmd.setText(Messages.getString("CrashDebuggerView.83")); //$NON-NLS-1$
		logical_devices_cmd.setToolTipText(Messages
				.getString("CrashDebuggerView.84")); //$NON-NLS-1$

		physical_devices_cmd = new Action() {
			public void run() {
				sendUICommand(PHYSICAL_DEVICES_CMD);
			}
		};
		physical_devices_cmd
				.setText(Messages.getString("CrashDebuggerView.85")); //$NON-NLS-1$
		physical_devices_cmd.setToolTipText(Messages
				.getString("CrashDebuggerView.86")); //$NON-NLS-1$

		channels_cmd = new Action() {
			public void run() {
				sendUICommand(CHANNELS_CMD);
			}
		};
		channels_cmd.setText(Messages.getString("CrashDebuggerView.87")); //$NON-NLS-1$
		channels_cmd.setToolTipText(Messages.getString("CrashDebuggerView.88")); //$NON-NLS-1$

		change_notifiers_cmd = new Action() {
			public void run() {
				sendUICommand(CHANGE_NOTIFIERS_CMD);
			}
		};
		change_notifiers_cmd
				.setText(Messages.getString("CrashDebuggerView.89")); //$NON-NLS-1$
		change_notifiers_cmd.setToolTipText(Messages
				.getString("CrashDebuggerView.90")); //$NON-NLS-1$

		undertakers_cmd = new Action() {
			public void run() {
				sendUICommand(UNDERTAKERS_CMD);
			}
		};
		undertakers_cmd.setText(Messages.getString("CrashDebuggerView.91")); //$NON-NLS-1$
		undertakers_cmd.setToolTipText(Messages
				.getString("CrashDebuggerView.92")); //$NON-NLS-1$

		msg_queues_cmd = new Action() {
			public void run() {
				sendUICommand(MSG_QUEUES_CMD);
			}
		};
		msg_queues_cmd.setText(Messages.getString("CrashDebuggerView.93")); //$NON-NLS-1$
		msg_queues_cmd.setToolTipText(Messages
				.getString("CrashDebuggerView.94")); //$NON-NLS-1$

		property_refs_cmd = new Action() {
			public void run() {
				sendUICommand(PROPERTY_REFS_CMD);
			}
		};
		property_refs_cmd.setText(Messages.getString("CrashDebuggerView.95")); //$NON-NLS-1$
		property_refs_cmd.setToolTipText(Messages
				.getString("CrashDebuggerView.96")); //$NON-NLS-1$

		registers_cmd = new Action() {
			public void run() {
				sendUICommand(REGISTERS_CMD);
			}
		};
		registers_cmd.setText(Messages.getString("CrashDebuggerView.97")); //$NON-NLS-1$
		registers_cmd
				.setToolTipText(Messages.getString("CrashDebuggerView.98")); //$NON-NLS-1$
	}

	protected void updateSelectionDependentActions() {
		cutAction.setEnabled(viewer.canDoOperation(ITextOperationTarget.CUT));
		copyAction.setEnabled(viewer.canDoOperation(ITextOperationTarget.COPY));
		pasteAction.setEnabled(viewer
				.canDoOperation(ITextOperationTarget.PASTE));
	}

	protected void sessionChanged(final boolean started) {
		terminateSession.setEnabled(started);
		fault_cmd.setEnabled(started);
		help_cmd.setEnabled(started);
		memory_cmd.setEnabled(started);
		memory2_cmd.setEnabled(started);
		object_brief_cmd.setEnabled(started);
		object_full_cmd.setEnabled(started);
		info_cmd.setEnabled(started);
		threads_cmd.setEnabled(started);
		processes_cmd.setEnabled(started);
		chunks_cmd.setEnabled(started);
		libraries_cmd.setEnabled(started);
		semaphores_cmd.setEnabled(started);
		mutexes_cmd.setEnabled(started);
		timers_cmd.setEnabled(started);
		servers_cmd.setEnabled(started);
		sessions_cmd.setEnabled(started);
		logical_devices_cmd.setEnabled(started);
		physical_devices_cmd.setEnabled(started);
		channels_cmd.setEnabled(started);
		change_notifiers_cmd.setEnabled(started);
		undertakers_cmd.setEnabled(started);
		msg_queues_cmd.setEnabled(started);
		property_refs_cmd.setEnabled(started);
		registers_cmd.setEnabled(started);

		final StyledText textWidget = viewer.getTextWidget();
		final CrashDebuggerView cdView = this;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (started) {
					// Make sure the Crash Debugger console is brought to
					// front on session start.
					cdView.getViewSite().getPage().activate(cdView);

					if (textWidget != null) {
						textWidget.setCaretOffset(textWidget.getCharCount());
						textWidget.setEditable(true);
					}
				} else {
					// Disable editing in the console, fix bug 2901.
					if (textWidget != null) {
						textWidget.setCaretOffset(-1);
						textWidget.setEditable(false);
					}
				}
			}
		});
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	private void sendUICommand(String cmd) {
		// append newline/carriage return
		String cmd2 = cmd + "\r\n"; //$NON-NLS-1$
		// echo the command to the console and send it to the port
		StyledText text = viewer.getTextWidget();
		text.append(cmd2);

		sendCommand(cmd2);

		// make sure the caret gets updated correctly
		text.setCaretOffset(text.getCharCount());
	}

	private void sendCommand(String cmd) {
		// send the command to the serial port.
		CrashDebugger.sendCommand(cmd);
	}

	private void enterKeyPressed() {
		IDocument doc = viewer.getDocument();
		if (doc != null) {
			int lines = doc.getNumberOfLines();
			try {
				int lo = doc.getLineOffset(lines - 2);
				int ll = doc.getLineLength(lines - 2);
				String line = doc.get(lo, ll);
				sendCommand(line);
			} catch (BadLocationException e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite,
	 *      org.eclipse.ui.IMemento)
	 */
	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {

		super.init(site, memento);
		FeatureUseTrackerPlugin.getFeatureUseProxy().startUsingFeature(FeatureUseTrackerConsts.CARBIDE_CRASH_DEBUGGER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {

		super.dispose();
		FeatureUseTrackerPlugin.getFeatureUseProxy().stopUsingFeature(FeatureUseTrackerConsts.CARBIDE_CRASH_DEBUGGER);

	
	}
}