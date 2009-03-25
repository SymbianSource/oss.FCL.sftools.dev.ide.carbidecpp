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


package com.nokia.sdt.uidesigner.ui;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.editor.*;
import com.nokia.sdt.uidesigner.ui.editparts.*;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;

import java.util.*;

/**
 * SelectionManager
 * 
 * <li>A selection provider that will unify the selections of both
 * GraphicalViewers so as to provide a single selection from the EditorPart.
 * <li>Also, a SelectionSynchronizer implementation that allows selection
 * setting from other sources besides viewers.
 * <li>Handles left over selections from editor reload operation.
 */
public class SelectionManager implements ISelectionManager {

	private IDesignerEditor editor;
	private ListenerList selectionChangedListeners = new ListenerList(ListenerList.IDENTITY);
	private IPartService partService;
	private IPartListener partListener;
	private List leftoverSelectedNames;
	private Set savedComponentEditors = new HashSet();
	private List partsWithVisibleScrollbars = new ArrayList();
	private ISelection currentSelection;
	
	private List viewers = new ArrayList();
	private boolean isDispatching;
	
	class PartListener implements IPartListener {
		
		private IDesignerEditor editor;
		
		public PartListener(IDesignerEditor editor) {
			this.editor = editor;
		}
		
		public void partActivated(IWorkbenchPart part) {
			if (part.equals(editor.getAdapter(IWorkbenchPart.class))) {
				if (leftoverSelectedNames != null) {
					setSelectionFromNames(leftoverSelectedNames);
					leftoverSelectedNames = null;
				}
			}
		}

		public void partBroughtToTop(IWorkbenchPart part) {
		}

		public void partClosed(IWorkbenchPart part) {
		}

		public void partDeactivated(IWorkbenchPart part) {
		}

		public void partOpened(IWorkbenchPart part) {
			if (!part.equals(editor))
				return;

			getLeftoverSelectedNames();
		}
		
	}
	
	public SelectionManager(IDesignerEditor editor, IPartService partService) {
		this.editor = editor;
		this.partService = partService;
		partListener = new PartListener(editor);
		partService.addPartListener(partListener);
	}
	
	public void dispose() {
		partService.removePartListener(partListener);
		editor = null;
		selectionChangedListeners.clear();
		viewers.clear();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}
	
	private boolean editPartListContainsModel(List<EditPart> editPartList, Object model) {
		for (EditPart editPart : editPartList) {
			if (editPart.getModel().equals(model))
				return true;
		}
		
		return false;
	}
	
	private void addUniqueSelectedPartsFromViewer(
			List<EditPart> selectedEditParts, EditPartViewer viewer) {
		if (viewer != null) {
			for (EditPart editPart : getPrunedEditParts(viewer.getSelectedEditParts())) {
				if (!editPartListContainsModel(selectedEditParts, editPart.getModel())) {
					selectedEditParts.add(editPart);
				}
			}
		}
	}
	
	private void addModelsFromViewer(Collection<EObject> selectedObjects, EditPartViewer viewer) {
		if (viewer != null) {
			for (EditPart editPart : getPrunedEditParts(viewer.getSelectedEditParts())) {
				selectedObjects.add((EObject) editPart.getModel());
			}
		}
	}
	
	private boolean containsLayoutObjects(Collection<EObject> selectedObjects) {
		for (EObject object : selectedObjects) {
			if (Adapters.getLayoutObject(object) != null &&
					EditorUtils.findTransientObject(object) == null)
				return true;
		}
		return false;
	}

	private boolean containsOutlineOnlyObjects(Collection<EObject> selectedObjects) {
		for (EObject object : selectedObjects) {
			if (Adapters.getLayoutObject(object) == null && !EditorUtils.isNonLayoutObject(object, null))
				return true;
		}
		return false;
	}
	
	private Collection<EditPart> getPrunedEditParts(Collection<EditPart> parts) {
		// we remove content parts because they are not actual model objects
		// also remove the transient edit parts, so the selection will contain the non-layout part
		List<EditPart> prunedParts = new ArrayList<EditPart>();
		for (EditPart editPart : parts) {
			if (editPart.getModel() != null && !editPart.getModel().equals(editor.getModelObject()) && 
					!(editPart instanceof TransientObjectEditPart))
				prunedParts.add(editPart);
		}
		
		return prunedParts;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	public ISelection getSelection() {
		if (currentSelection != null)
			return currentSelection;
		
		// create a new list of edit parts from the various viewers.
		// favor upper viewer parts to outline part to allow alignment and matching actions to work.
		// However, avoid mixing outline with graphical parts - some GEF actions throw NPEs.
		// So if there are outline-only objects in the selection, use only outline parts
		List<EditPart> selectedEditParts = new ArrayList();
		boolean useGraphicalViewers = false;
		EditPartViewer outlineViewer = this.editor.getOutlineViewer();
		if (outlineViewer != null) {
			Set<EObject> selectedObjects = new HashSet();
			addModelsFromViewer(selectedObjects, outlineViewer);
			if (selectedObjects.isEmpty())
				return currentSelection = StructuredSelection.EMPTY;
			
			if (containsOutlineOnlyObjects(selectedObjects))
				addUniqueSelectedPartsFromViewer(selectedEditParts, outlineViewer);
			else
				useGraphicalViewers = true;
		}
		else
			useGraphicalViewers = true;
		
		if (useGraphicalViewers) {
			addUniqueSelectedPartsFromViewer(selectedEditParts, this.editor.getUpperGraphicalViewer());
			addUniqueSelectedPartsFromViewer(selectedEditParts, this.editor.getLowerGraphicalViewer());
		}
		
		// prune the list to not include contents or transient parts 
		List selection = new ArrayList();
		for (EditPart editPart : selectedEditParts) {
			selection.add(editPart);
		}
		
		return currentSelection = new StructuredSelection(selection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	public void setSelection(ISelection newSelection) {
		syncSelection(null, newSelection);
	}

	public void addViewer(EditPartViewer viewer) {
		viewer.addSelectionChangedListener(this);
		viewers.add(viewer);
	}

	public void removeViewer(EditPartViewer viewer) {
		viewer.removeSelectionChangedListener(this);
		viewers.remove(viewer);
	}

	protected EditPart getViewerEditPart(EditPartViewer viewer, Object model) {
		if (model == null)
			return null;
		Object temp = viewer.getEditPartRegistry().get(model);
		EditPart newPart = null;
		if (temp != null) {
			newPart = (EditPart) temp;
		}
		return newPart;
	}
	
	private void syncSelection(EditPartViewer source, ISelection selection) {
		if (editor.getUpperGraphicalViewer() == null)
			return;
		
		determineTransientMode(selection);
		adviseEditors(selection);
		resetContainerScrollbarsVisibility(selection);
		isDispatching = true;
		if (source instanceof org.eclipse.gef.ui.parts.TreeViewer) {
			// tree viewer => all others
			EditorUtils.setDisplayModelSelectedObjects(editor.getDisplayModel(), 
										getModelObjectsFromSelection(selection));
			setViewerSelection(editor.getUpperGraphicalViewer(), selection);
			setViewerSelection(editor.getLowerGraphicalViewer(), selection);
		}
		else if (editor.getUpperGraphicalViewer().equals(source)) {
			// if upper, sel => tree; null => lower
			setViewerSelection(editor.getOutlineViewer(), selection);
			setViewerSelection(editor.getLowerGraphicalViewer(), selection);
		}
		else if ((source != null) && source.equals(editor.getLowerGraphicalViewer())) {
			// if lower, sel => tree; null => upper
			setViewerSelection(editor.getOutlineViewer(), selection);
			setViewerSelection(editor.getUpperGraphicalViewer(), selection);
		}
		else if (source == null) {
			// if null source, set all
			setViewerSelection(editor.getUpperGraphicalViewer(), selection);
			setViewerSelection(editor.getLowerGraphicalViewer(), selection);
			setViewerSelection(editor.getOutlineViewer(), selection);
		}
		isDispatching = false;
		
		if (source != null)
			revealSelectionInViewer(source, selection);
		
		// tell the rest of the world
		fireSelectionChanged(getSelection());
	}

	private List getViewerSelection(EditPartViewer viewer, ISelection selection) {
		if ((viewer == null) || (viewer.getControl() == null))
			return null;
		
		ArrayList result = new ArrayList();
		for (Iterator iter = ((IStructuredSelection) selection).iterator(); iter.hasNext();) {
			EditPart part = getViewerEditPart(viewer, getObjectFromSelected(iter.next()));
			if (part != null)
				result.add(part);
		}
		
		return result;
	}

	private void setViewerSelection(EditPartViewer viewer, ISelection selection) {
		if (viewer == null)
			return;
		List selectedParts = getViewerSelection(viewer, selection);
		if ((selectedParts != null) && (!selectedParts.isEmpty())) {
			viewer.setSelection(new StructuredSelection(selectedParts));
			if (selectedParts.size() > 0) {
				viewer.reveal((EditPart) selectedParts.get(selectedParts.size() - 1));
			}
		}
		else {
			viewer.setSelection(StructuredSelection.EMPTY);
		}
	}
	
	public void revealSelectionInViewer(EditPartViewer viewer, ISelection selection) {
		List selectedParts = getViewerSelection(viewer, selection);
		if ((selectedParts != null) && (!selectedParts.isEmpty())) {
			if (selectedParts.size() > 0) {
				for (Iterator<EditPart> iter = selectedParts.iterator(); iter.hasNext();) {
					EditPart part = iter.next();
					if (part.getModel() instanceof EObject) {
						viewer.reveal(part);
						break;
					}
				}
			}
		}
	}

	public void selectionChanged(SelectionChangedEvent event) {
		currentSelection = null;
		if (isDispatching)
			return;
		
		// this synchronizes between viewers
		EditPartViewer source = (EditPartViewer)event.getSelectionProvider();
		ISelection selection = event.getSelection();
		syncSelection(source, selection);
	}
	
	private void determineTransientMode(ISelection selection) {
		List selectedObjects = getModelObjectsFromSelection(selection);
		
		if (selectedObjects.isEmpty() || containsLayoutObjects(selectedObjects))
			editor.setLayoutMode();
		
		// if all objects belong to the same transient root, set to transient, else set to layout
		// set to transient mode, else set to layout mode
		EObject transientRoot = null;
		boolean makeTransient = false;
		for (Iterator iter = selectedObjects.iterator(); iter.hasNext();) {
			EObject object = (EObject) iter.next();
			if (transientRoot == null) {
				transientRoot = EditorUtils.findTransientObject(object);
			}
			makeTransient = EditorUtils.isSameObjectOrChild(transientRoot, object);
		}
		
		if (makeTransient) {
			Object prevRoot = editor.getTransientRootObject();
			if ((prevRoot != null) && !transientRoot.equals(prevRoot))
				editor.setLayoutMode();
			
			editor.setTransientMode(transientRoot);
		}
		else
			editor.setLayoutMode();
	}

	private List getModelObjectsFromSelection(ISelection selection) {
		List selectedObjects = new ArrayList();
		for (Iterator iter = ((StructuredSelection) selection).toList().iterator(); iter.hasNext();) {
			EObject object = getObjectFromSelected(iter.next());
			if (object != null)
				selectedObjects.add(object);
		}
		return selectedObjects;
	}
	
	private void adviseEditors(ISelection selection) {
		Set newComponentEditors = new HashSet();
		
		// add new editors from selection
		for (Iterator iter = ((StructuredSelection) selection).iterator(); iter.hasNext();) {
			EObject object = getObjectFromSelected(iter.next());
			if (object != null) {
				IComponentEditor componentEditor = Adapters.getComponentEditor(object);
				if (componentEditor != null)
					newComponentEditors.add(componentEditor);
			}
		}
		
		// advise saved editors that are not in the new set to exit edit mode
		for (Iterator iter = savedComponentEditors.iterator(); iter.hasNext();) {
			IComponentEditor componentEditor = (IComponentEditor) iter.next();
			if (!newComponentEditors.contains(componentEditor))
				componentEditor.endEditing();
		}
		
		// advise new component editors that are not in the saved set to enter edit mode
		for (Iterator iter = newComponentEditors.iterator(); iter.hasNext();) {
			IComponentEditor componentEditor = (IComponentEditor) iter.next();
			if (!savedComponentEditors.contains(componentEditor))
				componentEditor.beginEditing(editor);
		}
		
		// replace the saved component editors with the new set
		savedComponentEditors = newComponentEditors;
	}
	
	private void setContainerScrollbarsVisible(EditPart selectedPart, boolean visible) {
		if (!(selectedPart instanceof ModelObjectEditPart))
			return;
		
		if (selectedPart instanceof LayoutContainerEditPart) {
			((LayoutContainerEditPart) selectedPart).setScrollbarsVisible(visible);
			if (visible)
				partsWithVisibleScrollbars.add(selectedPart);
		}
		setContainerScrollbarsVisible(selectedPart.getParent(), visible);
	}
	
	private void resetContainerScrollbarsVisibility(ISelection selection) {
		for (Iterator iter = partsWithVisibleScrollbars.iterator(); iter.hasNext();) {
			EditPart part = (EditPart) iter.next();
			setContainerScrollbarsVisible(part, false);
		}
		
		partsWithVisibleScrollbars.clear();
		GraphicalViewer upperGraphicalViewer = editor.getUpperGraphicalViewer();
		for (Iterator iter = ((StructuredSelection) selection).iterator(); iter.hasNext();) {
			EObject object = getObjectFromSelected(iter.next());
			if (object != null) {
				EditPart part = getPart(upperGraphicalViewer, object);
				setContainerScrollbarsVisible(part, true);
			}
		}
	}

	private void fireSelectionChanged(ISelection selection) {
		 Object[] listeners = selectionChangedListeners.getListeners();
		 selection = getSelection();
		 for (int i = 0; i < listeners.length; ++i) {
			((ISelectionChangedListener) listeners[i]).selectionChanged(
		    		new SelectionChangedEvent(this, selection));
		 }
	}

	private EditPart getPart(EditPartViewer viewer, Object object) {
		if (object == null)
			return null;
		
		Object temp = viewer.getEditPartRegistry().get(object);
		EditPart newPart = null;
		if (temp != null) {
			newPart = (EditPart)temp;
		}
		return newPart;
	}


	public void setSelectionFromNames(List objectNames) {
		List parts = new ArrayList();
		for (Iterator iter = objectNames.iterator(); iter.hasNext();) {
			EObject object = this.editor.getDataModel().findByNameProperty(iter.next().toString());
			EditPart part = getPart(this.editor.getUpperGraphicalViewer(), object);
			if (part != null)
				parts.add(part);
			else if (this.editor.getLowerGraphicalViewer() != null) {
				part = getPart(this.editor.getLowerGraphicalViewer(), object);
				if (part != null)
					parts.add(part);

			}
		}
		setSelection(new StructuredSelection(parts));
	}
	
	public void getLeftoverSelectedNames() {
		try {
			IDesignerDataModelEditor ddme = 
				(IDesignerDataModelEditor) editor.getAdapter(IDesignerDataModelEditor.class);
			QualifiedName key = 
				new QualifiedName(UIDesignerPlugin.getDefault().toString(), ddme.getPartName());
			leftoverSelectedNames = (List) editor.getDataModelResource().getSessionProperty(key);
			editor.getDataModelResource().setSessionProperty(key, null);
		} 
		catch (CoreException e) {
			IStatus status = Logging.newStatus(UIDesignerPlugin.getDefault(), e);
			Logging.log(UIDesignerPlugin.getDefault(), status);
		}
	}
	
	public void reloadSelection() {
		getLeftoverSelectedNames();
		if (leftoverSelectedNames != null) {
			setSelectionFromNames(leftoverSelectedNames);
			leftoverSelectedNames = null;
		}
	}

	public List getSelectedModelNames() {
		ISelection sel = getSelection();
		if (!(sel instanceof IStructuredSelection))
			return null;
		
		List objectNames = new ArrayList();
		IStructuredSelection selection = (IStructuredSelection) sel;
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			EObject object = getObjectFromSelected(iter.next());
			if (object != null) {
				IComponentInstance instance = Adapters.getComponentInstance(object);
				objectNames.add(instance.getName());
			}
		}
		
		return objectNames;
	}

	public boolean isComponentEditing() {
		return !savedComponentEditors.isEmpty();
	}
	
	public void setSync(boolean sync) {
		isDispatching = !sync;
	}
	
	private EObject getObjectFromSelected(Object object) {
		if (object instanceof EditPart) {
			object = ((EditPart) object).getModel();
		}
		if (object instanceof EObject)
			return (EObject) object;
		
		return null;
	}
}