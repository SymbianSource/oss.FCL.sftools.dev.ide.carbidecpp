/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.nokia.carbide.search.system.internal.ui.text;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.OpenFileAction;
import org.eclipse.ui.actions.OpenWithMenu;
import org.eclipse.ui.dialogs.EditorSelectionDialog;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.ide.DialogUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.part.FileEditorInput;

import com.nokia.carbide.search.system.internal.ui.SearchMessages;
import com.nokia.carbide.search.system.internal.ui.SearchPlugin;
import com.nokia.carbide.search.system.ui.IContextMenuConstants;

/**
 * Action group that adds the Text search actions to a context menu and
 * the global menu bar.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @since 2.1
 */
public class NewTextSearchActionGroup extends ActionGroup {

	private ISelectionProvider fSelectionProvider;		
	private IWorkbenchPartSite fSite;
	private IWorkbenchPage fPage;
	private OpenFileAction fOpenAction;
	private Provider fProvider;
	private PropertyDialogAction fOpenPropertiesDialog;

	private IWorkspaceRoot fWorkspaceRoot;
	private IEditorRegistry fRegistry;
	private IFileStore fSelectedFile;

	private static Hashtable imageCache = new Hashtable(11);
	
	// dummy provider so that the property dialog has an IFile, not an IFileStore
	private class Provider implements ISelectionProvider {
		ISelection keep;

		public void addSelectionChangedListener(ISelectionChangedListener arg0) {
		}

		public ISelection getSelection() {
			return keep;
		}

		public void removeSelectionChangedListener(ISelectionChangedListener arg0) {
		}

		public void setSelection(ISelection selection) {
			keep = selection;
		}		
	}

	public NewTextSearchActionGroup(IViewPart part) {
		Assert.isNotNull(part);

		fWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		fRegistry = PlatformUI.getWorkbench().getEditorRegistry();
		
		fSite= part.getSite();
		fSelectionProvider= fSite.getSelectionProvider();
		fPage= fSite.getPage();

		fProvider = new Provider();
		fOpenPropertiesDialog = new PropertyDialogAction(fSite, fProvider);

		fOpenAction= new OpenFileAction(fPage);
		ISelection selection= fSelectionProvider.getSelection();

		if (selection instanceof IStructuredSelection)
			fOpenPropertiesDialog.selectionChanged((IStructuredSelection)selection);
		else
			fOpenPropertiesDialog.selectionChanged(selection);
		
		fOpenPropertiesDialog.setEnabled(true);
	}
	
	public void fillContextMenu(IMenuManager menu) {
		// view must exist if we create a context menu for it.
		
		ISelection selection= getContext().getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structured = (IStructuredSelection) selection;
			addOpenWithMenu(menu, structured);
			if (fOpenPropertiesDialog != null && fOpenPropertiesDialog.isEnabled() && fOpenPropertiesDialog.isApplicableForSelection(structured))
				if (structured.size() > 0 && structured.getFirstElement() instanceof IFileStore) {
					IFileStore fileStore = (IFileStore) structured.getFirstElement();
					IFile[] ifiles = null;

					try {
						ifiles = fWorkspaceRoot.findFilesForLocation(new Path(fileStore.toLocalFile(EFS.NONE, null).getAbsolutePath()));
					} catch (CoreException e) {
					}

					if (ifiles != null && ifiles.length > 0) {
						structured = new StructuredSelection(ifiles[0]);

						fProvider.setSelection(structured);
						fOpenPropertiesDialog.selectionChanged(structured);
						menu.appendToGroup(IContextMenuConstants.GROUP_PROPERTIES, fOpenPropertiesDialog);
					}
				}
		}
	}
	
	private void addOpenWithMenu(IMenuManager menu, IStructuredSelection selection) {
		if (selection == null)
			return;
	
		fOpenAction.selectionChanged(selection);
		if (fOpenAction.isEnabled()) {
			menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, fOpenAction);
		}
		
		if (selection.size() != 1) {
			return;
		}
		
		Object o= selection.getFirstElement();
	
		if (!(o instanceof IAdaptable))
			return;

		// Open With... context submenu
		IMenuManager submenu= new MenuManager(SearchMessages.OpenWithMenu_label); 

		if (o instanceof IFileStore) {
			// see if o is in the workspace
			IFileStore fileStore = (IFileStore) o;
			IFile[] ifiles = null;

			try {
				ifiles = fWorkspaceRoot.findFilesForLocation(new Path(fileStore.toLocalFile(EFS.NONE, null).getAbsolutePath()));
			} catch (CoreException e) {
			}

			if (ifiles == null || ifiles.length == 0) {
				// file is not in the workspace
				fSelectedFile = fileStore;
				
				// Open item
				Action action = new Action() {
					public void run() {
						String editorID = null;
						IEditorDescriptor des = getDefaultEditor(fSelectedFile);
						if (des != null) {
							editorID = des.getId();
						} else {
							editorID = "org.eclipse.ui.DefaultTextEditor"; //$NON-NLS-1$
						}
						
						try {
							IDE.openEditor(fPage, fSelectedFile.toURI(), editorID, true);	
						}
						catch (PartInitException e) {
							String msg =  NLS.bind(IDEWorkbenchMessages.OpenLocalFileAction_message_errorOnOpen, fSelectedFile.getName());
							IDEWorkbenchPlugin.log(msg,e.getStatus());
							MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), IDEWorkbenchMessages.OpenLocalFileAction_title, msg);
						}
					}
				};
				action.setText(SearchMessages.NewTextSearchActionGroup_open);
				action.setToolTipText(SearchMessages.NewTextSearchActionGroup_openToolTip);
				menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, action);

				// OpenWith submenu
				submenu.add(new SystemOpenWithMenu(fPage, (IAdaptable) fileStore));
				menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, submenu);
				return;
			} else {
				// file is in the workspace
				o = ifiles[0];
				
				// fall through
			}
		}

		// OpenWith submenu
		submenu.add(new OpenWithMenu(fPage, (IAdaptable)o));
		menu.appendToGroup(IContextMenuConstants.GROUP_OPEN, submenu);
	}

	/* (non-Javadoc)
	 * Method declared in ActionGroup
	 */
	public void fillActionBars(IActionBars actionBar) {
		super.fillActionBars(actionBar);
		setGlobalActionHandlers(actionBar);
	}
	
	private void setGlobalActionHandlers(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.PROPERTIES.getId(), fOpenPropertiesDialog);		
	}

	private IEditorDescriptor getPreferredEditor(IFileStore fileStore)
	{
		IFile[] ifiles = null;
		try {
			ifiles = fWorkspaceRoot.findFilesForLocation(new Path(fileStore.toLocalFile(EFS.NONE, null).getAbsolutePath()));
		} catch (CoreException e) {
		}

		if (ifiles == null || ifiles.length == 0) {
			return fRegistry.getDefaultEditor(fileStore.getName());
		} else {
			return IDE.getDefaultEditor(ifiles[0]);
		}
	}

	private IEditorDescriptor getDefaultEditor(IFileStore fileStore)
	{

		IFile[] ifiles = null;
		try {
			ifiles = fWorkspaceRoot.findFilesForLocation(new Path(fileStore.toLocalFile(EFS.NONE, null).getAbsolutePath()));
		} catch (CoreException e) {
		}

		if (ifiles == null || ifiles.length == 0) {
			return fRegistry.getDefaultEditor(fileStore.getName());
		} else {
			IEditorDescriptor descriptor = IDE.getDefaultEditor(ifiles[0]);
			if (descriptor == null)
			{
				descriptor = fRegistry.findEditor("org.eclipse.ui.DefaultTextEditor"); //$NON-NLS-1$;
			}
			return descriptor;
		}
	}

	/*
	 * Compares the labels from two IEditorDescriptor objects 
	 */
	private static final Comparator comparer = new Comparator() 
	{
		private Collator collator = Collator.getInstance();

		public int compare(Object arg0, Object arg1) {
			String s1 = ((IEditorDescriptor)arg0).getLabel();
			String s2 = ((IEditorDescriptor)arg1).getLabel();
			return collator.compare(s1, s2);
		}
	};
	
	public class SystemOpenAction extends Action {
		
	}

	private class SystemOpenWithMenu extends ContributionItem {
	    private IWorkbenchPage page;
	    private IFileStore fileStore;

	    /**
	     * Match both the input and id, so that different types of editor can be opened on the same input.
	     */
	    private static final int MATCH_BOTH = IWorkbenchPage.MATCH_INPUT | IWorkbenchPage.MATCH_ID;
	    
	    /**
	     * Constructs a new instance of <code>OpenWithMenu</code>.  
	     *
	     * @param page the page where the editor is opened if an item within
	     *		the menu is selected
	     * @param file the selected file
	     */
	    public SystemOpenWithMenu(IWorkbenchPage page, IAdaptable file) {
	    	super();
	        this.page = page;
	        this.fileStore = (FileStore) file;
	    }

		/* (non-Javadoc)
		 * Fills the menu with perspective items.
		 */
		public void fill(Menu menu, int index) 
		{
			IEditorDescriptor defaultEditor = fRegistry.findEditor("org.eclipse.ui.DefaultTextEditor"); // may be null //$NON-NLS-1$
			IEditorDescriptor preferredEditor = getPreferredEditor(fileStore); // may be null
			
			Object[] editors = fRegistry.getEditors(fileStore.getName());
			Collections.sort(Arrays.asList(editors), comparer);

			boolean defaultFound = false;
			
			//Check that we don't add it twice. This is possible
			//if the same editor goes to two mappings.
			ArrayList alreadyMapped= new ArrayList();

			for (int i = 0; i < editors.length; i++) {
				IEditorDescriptor editor = (IEditorDescriptor) editors[i];
				if(!alreadyMapped.contains(editor)){
					createMenuItem(menu, editor, preferredEditor);
					if (defaultEditor != null && editor.getId().equals(defaultEditor.getId()))
						defaultFound = true;
					alreadyMapped.add(editor);
				}		
			}

			// Add default editor. Check it if it is saved as the preference.
			if (!defaultFound && defaultEditor != null) {
				createMenuItem(menu, defaultEditor, preferredEditor);
				new MenuItem(menu, SWT.SEPARATOR);
			} else {
				// Only add a separator if there is something to separate
				if (editors.length > 0)
					new MenuItem(menu, SWT.SEPARATOR);
			}

			// Add system editor (should never be null)
			IEditorDescriptor descriptor = fRegistry.findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
			createMenuItem(menu, descriptor, preferredEditor);
			
			// Add system in-place editor if the file is in the workspace
			IFile[] ifiles = null;
			try {
				ifiles = fWorkspaceRoot.findFilesForLocation(new Path(fileStore.toLocalFile(EFS.NONE, null).getAbsolutePath()));
			} catch (CoreException e) {
			}

			if (ifiles == null || ifiles.length == 0) {
				descriptor = fRegistry.findEditor(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
				if (descriptor != null && (ifiles.length > 0)) {
					createMenuItem(menu, descriptor, preferredEditor);
				}
			}

			createDefaultMenuItem(menu, fileStore);
		}

		/**
		 * Creates a menu item for the editor descriptor.
		 *
		 * @param menu the menu to add the item to
		 * @param descriptor the editor descriptor, or null for the system editor
		 * @param preferredEditor the descriptor of the preferred editor, or <code>null</code>
		 */
		protected void createMenuItem(Menu menu, final IEditorDescriptor descriptor, final IEditorDescriptor preferredEditor) 
		{
			// XXX: Would be better to use bold here, but SWT does not support it.
			final MenuItem menuItem = new MenuItem(menu, SWT.RADIO);
//			boolean isPreferred = preferredEditor != null && descriptor.getId().equals(preferredEditor.getId());
//			menuItem.setSelection(isPreferred);
			menuItem.setSelection(false);
			menuItem.setText(descriptor.getLabel());
			Image image = getImage(descriptor);
			if (image != null) {
				menuItem.setImage(image);
			}
			Listener listener = new Listener() {
				public void handleEvent(Event event) {
					switch (event.type) {
						case SWT.Selection:
							if(menuItem.getSelection())
							{
								try {
									IWorkbenchPage wbPage= SearchPlugin.getActivePage();
									
									String id = null;
									if (descriptor == null)
										id = IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID;
									else
										id = descriptor.getId();
									
									IDE.openEditor(wbPage, fSelectedFile.toURI(), id, true);	

								}
								catch (PartInitException e) {
									String msg =  NLS.bind(IDEWorkbenchMessages.OpenLocalFileAction_message_errorOnOpen, fSelectedFile.getName());
									IDEWorkbenchPlugin.log(msg,e.getStatus());
									MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), IDEWorkbenchMessages.OpenLocalFileAction_title, msg);
								}
							}
							break;
					}
				}
			};
			menuItem.addListener(SWT.Selection, listener);
		}

		/**
		 * Returns an image to show for the corresponding editor descriptor.
		 *
		 * @param editorDesc the editor descriptor, or null for the system editor
		 * @return the image or null
		 */
		protected Image getImage(IEditorDescriptor editorDesc) {
			ImageDescriptor imageDesc = getImageDescriptor(editorDesc);
			if (imageDesc == null) {
				return null;
			}
			Image image = (Image) imageCache.get(imageDesc);
			if (image == null) {
				image = imageDesc.createImage();
				imageCache.put(imageDesc, image);
			}
			return image;
		}

		/**
		 * Returns the image descriptor for the given editor descriptor,
		 * or null if it has no image.
		 */
		private ImageDescriptor getImageDescriptor(IEditorDescriptor editorDesc) {
			ImageDescriptor imageDesc = null;
			if (editorDesc == null) {
				imageDesc = fRegistry.getImageDescriptor(fSelectedFile.getName());
			}
			else {
				imageDesc = editorDesc.getImageDescriptor();
			}
			if (imageDesc == null && editorDesc != null) {
				if (editorDesc.getId().equals(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID))
					imageDesc = fRegistry.getSystemExternalEditorImageDescriptor(fSelectedFile.getName());
			}
			return imageDesc;
		}

		/**
		 * Creates the menu item for clearing the current selection.
		 *
		 * @param menu the menu to add the item to
		 * @param file the file being edited
		 */
		private void createDefaultMenuItem(Menu menu, final IFileStore fileStore) 
		{
			final MenuItem menuItem = new MenuItem(menu, SWT.RADIO);
			IEditorDescriptor defaultEditor = getDefaultEditor(fileStore);
			menuItem.setSelection(defaultEditor == null);
			menuItem.setText(SearchMessages.NewTextSearchActionGroup_defaultEditor); 
			
			Listener listener = new Listener() 
			{
				public void handleEvent(Event event) 
				{
					switch (event.type) 
					{
						case SWT.Selection:
							if(menuItem.getSelection()) 
							{
								setDefaultEditor(fileStore, null);
				
								IEditorDescriptor descriptor = null;
								
								try {
									descriptor = IDE.getEditorDescriptor(fileStore.getName(), true);
									IWorkbenchPage wbPage= SearchPlugin.getActivePage();
									
									String id = null;
									if (descriptor == null)
										id = IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID;
									else
										id = descriptor.getId();
									
									IDE.openEditor(wbPage, fileStore.toURI(), id, true);	

								}
								catch (PartInitException e) {
									String msg =  NLS.bind(IDEWorkbenchMessages.OpenLocalFileAction_message_errorOnOpen, fileStore.getName());
									IDEWorkbenchPlugin.log(msg,e.getStatus());
									MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), IDEWorkbenchMessages.OpenLocalFileAction_title, msg);
								}
							}
							break;
					}
				}
			};
			
			menuItem.addListener(SWT.Selection, listener);
		}
		
		private void setDefaultEditor(IFileStore fileStore, String editorId)
		{
			IFile[] ifiles = null;
			try {
				ifiles = fWorkspaceRoot.findFilesForLocation(new Path(fileStore.toLocalFile(EFS.NONE, null).getAbsolutePath()));
			} catch (CoreException e) {
			}

			if (ifiles == null || ifiles.length == 0) {
				fRegistry.setDefaultEditor(fileStore.getName(), editorId);
			}
			else {
				IDE.setDefaultEditor(ifiles[0], editorId);
			}
		}

	    /**
	     * Creates the Other... menu item
	     *
	     * @param menu the menu to add the item to
	     */
	    private void createOtherMenuItem(final Menu menu) {
	    	final IFile fileResource = getFileResource();
			if (fileResource == null) {
	    		return;
	    	}
	        new MenuItem(menu, SWT.SEPARATOR);
	        final MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
	        menuItem.setText(IDEWorkbenchMessages.OpenWithMenu_Other);
	        Listener listener = new Listener() {
	            public void handleEvent(Event event) {
	                switch (event.type) {
	                case SWT.Selection:
	                   	EditorSelectionDialog dialog = new EditorSelectionDialog(
								menu.getShell());
						dialog
								.setMessage(NLS
										.bind(
												IDEWorkbenchMessages.OpenWithMenu_OtherDialogDescription,
												fileResource.getName()));
						if (dialog.open() == Window.OK) {
							IEditorDescriptor editor = dialog.getSelectedEditor();
							if (editor != null) {
								openEditor(editor, editor.isOpenExternal());
							}
						}
	                    break;
	                }
	            }
	        };
	        menuItem.addListener(SWT.Selection, listener);
	    }
	    
	    /* (non-Javadoc)
	     * Fills the menu with perspective items.
	     */
	    public void fill2(Menu menu, int index) {
	        IFile file = getFileResource();
	        if (file == null) {
	            return;
	        }

	        IEditorDescriptor defaultEditor = fRegistry
	                .findEditor(IDEWorkbenchPlugin.DEFAULT_TEXT_EDITOR_ID); // may be null
	        IEditorDescriptor preferredEditor = IDE.getDefaultEditor(file); // may be null

	        Object[] editors = fRegistry.getEditors(file.getName(), IDE.getContentType(file));
	        Collections.sort(Arrays.asList(editors), comparer);

	        boolean defaultFound = false;

	        //Check that we don't add it twice. This is possible
	        //if the same editor goes to two mappings.
	        ArrayList alreadyMapped = new ArrayList();

	        for (int i = 0; i < editors.length; i++) {
	            IEditorDescriptor editor = (IEditorDescriptor) editors[i];
	            if (!alreadyMapped.contains(editor)) {
	                createMenuItem(menu, editor, preferredEditor);
	                if (defaultEditor != null
	                        && editor.getId().equals(defaultEditor.getId())) {
						defaultFound = true;
					}
	                alreadyMapped.add(editor);
	            }
	        }

	        // Only add a separator if there is something to separate
	        if (editors.length > 0) {
				new MenuItem(menu, SWT.SEPARATOR);
			}

	        // Add default editor. Check it if it is saved as the preference.
	        if (!defaultFound && defaultEditor != null) {
	            createMenuItem(menu, defaultEditor, preferredEditor);
	        }

	        // Add system editor (should never be null)
	        IEditorDescriptor descriptor = fRegistry
	                .findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
	        createMenuItem(menu, descriptor, preferredEditor);

	        // Add system in-place editor (can be null)
	        descriptor = fRegistry
	                .findEditor(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
	        if (descriptor != null) {
	            createMenuItem(menu, descriptor, preferredEditor);
	        }
	        createDefaultMenuItem(menu, file);
	        
	        // add Other... menu item
	        createOtherMenuItem(menu);
	    }
		

	    /**
	     * Converts the IAdaptable file to IFile or null.
	     */
	    private IFile getFileResource() {
	        if (this.fileStore instanceof IFile) {
	            return (IFile) this.fileStore;
	        }
	        IResource resource = (IResource) this.fileStore
	                .getAdapter(IResource.class);
	        if (resource instanceof IFile) {
	            return (IFile) resource;
	        }
	       
	        return null;
	    }

	    /* (non-Javadoc)
	     * Returns whether this menu is dynamic.
	     */
	    public boolean isDynamic() {
	        return true;
	    }

	    /**
	     * Opens the given editor on the selected file.
	     *
	     * @param editor the editor descriptor, or null for the system editor
	     * @param openUsingDescriptor use the descriptor's editor ID for opening if false (normal case),
	     * or use the descriptor itself if true (needed to fix bug 178235).
	     */
	    private void openEditor(IEditorDescriptor editor, boolean openUsingDescriptor) {
	        IFile file = getFileResource();
	        if (file == null) {
	            return;
	        }
	        try {
	        	if (openUsingDescriptor) {
	        		((WorkbenchPage) page).openEditorFromDescriptor(new FileEditorInput(file), editor, true, null);
	        	} else {
		            String editorId = editor == null ? IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID
		                    : editor.getId();
		            
		            ((WorkbenchPage) page).openEditor(new FileEditorInput(file), editorId, true, MATCH_BOTH);
		            // only remember the default editor if the open succeeds
		            IDE.setDefaultEditor(file, editorId);
	        	}
	        } catch (PartInitException e) {
	            DialogUtil.openError(page.getWorkbenchWindow().getShell(),
	                    IDEWorkbenchMessages.OpenWithMenu_dialogTitle,
	                    e.getMessage(), e);
	        }
	    }

	    /**
	     * Creates the menu item for clearing the current selection.
	     *
	     * @param menu the menu to add the item to
	     * @param file the file being edited
	     */
	    private void createDefaultMenuItem(Menu menu, final IFile file) {
	        final MenuItem menuItem = new MenuItem(menu, SWT.RADIO);
	        menuItem.setSelection(IDE.getDefaultEditor(file) == null);
	        menuItem.setText(IDEWorkbenchMessages.DefaultEditorDescription_name);

	        Listener listener = new Listener() {
	            public void handleEvent(Event event) {
	                switch (event.type) {
	                case SWT.Selection:
	                    if (menuItem.getSelection()) {
	                        IDE.setDefaultEditor(file, null);
	                        try {
	                        	IEditorDescriptor desc = IDE.getEditorDescriptor(file);
	                            page.openEditor(new FileEditorInput(file), desc.getId(), true, MATCH_BOTH);
	                        } catch (PartInitException e) {
	                            DialogUtil.openError(page.getWorkbenchWindow()
	                                    .getShell(), IDEWorkbenchMessages.OpenWithMenu_dialogTitle,
	                                    e.getMessage(), e);
	                        }
	                    }
	                    break;
	                }
	            }
	        };

	        menuItem.addListener(SWT.Selection, listener);
	    }
	}}	
