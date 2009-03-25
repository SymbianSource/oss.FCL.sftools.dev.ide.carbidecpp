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


package com.nokia.sdt.uidesigner.ui.utils;

import com.nokia.carbide.internal.api.updater.IProjectUpdateSession;
import com.nokia.carbide.updater.CarbideUpdaterPlugin;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.DesignerEditorPage;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart;
import com.nokia.sdt.uidesigner.ui.figure.ScrollingContainerFigure.ScrollPane;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.sdt.utils.*;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.swtdesigner.ResourceManager;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.*;
import org.eclipse.gef.palette.*;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.views.contentoutline.ContentOutline;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class EditorUtils {

	public static final String CONSTRAINT = "CreateRequest.constraint"; //$NON-NLS-1$
	private final static QualifiedName QUALIFIED_LAST_ZOOM = 
		new QualifiedName(UIDesignerPlugin.getDefault().toString(), "LastZoomLevel"); //$NON-NLS-1$
	private static final CreateRequest CREATE_REQ = new CreateRequest();

	public final static String LAST_CONFIG = "LayoutSizeComboContributionItem.LastSetConfiguration"; //$NON-NLS-1$
	public final static QualifiedName QUALIFIED_LAST_CONFIG = 
						new QualifiedName(UIDesignerPlugin.getDefault().toString(), LAST_CONFIG);

	private static final Point PRIVATE_POINT = new Point();

	static boolean isRootContainer(EObject obj) {
		IComponentInstance componentInstance = Adapters.getComponentInstance(obj);
		EObject parent = componentInstance.getParent();
		return parent == null || Adapters.getLayoutContainer(parent) == null;
	}
	
	public static boolean canContainChild(EObject parent, EObject child) {
		if (parent == null)
			return false;
		
		IComponentInstance parentInstance = Adapters.getComponentInstance(parent);
		IDesignerDataModel dataModel = parentInstance.getDesignerDataModel();
		if (isRootContainer(parent) && isNonLayoutObject(child, dataModel))
			return true;
		
		IContainer container = Adapters.getContainer(parent);
		if (container == null)
			return false;

		// for layout objects, explicitly test
		ILayoutObject layoutObject = Adapters.getLayoutObject(child);
		if (layoutObject != null)
			return container.canContainChild(Adapters.getComponentInstance(child), null);
		
		// otherwise, see if the container can contain objects of the component type
		IComponent component = getComponent(child, dataModel);
		if (component != null)
			return container.canContainComponent(component, null);
		
		return false;
	}
	
	public static boolean isNonLayoutObject(EObject object, IDesignerDataModel model) {
		return isNonLayoutComponent(getComponent(object, model));
	}
	
	public static IComponent getComponent(EObject object, IDesignerDataModel model) {
		IComponentInstance componentInstance = Adapters.getComponentInstance(object);
		if (componentInstance != null) {
			IDesignerDataModel componentModel = componentInstance.getDesignerDataModel();
			// if the component instance comes from the clipboard, use the other model
			if (componentModel == null) // try other model
				componentModel = model;
			if (componentModel == null)
				return null;
			IComponentSet componentSet = componentModel.getComponentSet();
			Check.checkContract(componentSet != null);
			return componentSet.lookupComponent(componentInstance.getComponentId());
		}
		return null;
		
	}

	public static void saveLastZoom(IDesignerEditor editor, double level) {
		IResource resource = editor.getDataModelResource();
		try {
			resource.setPersistentProperty(QUALIFIED_LAST_ZOOM, String.valueOf(level));
		} 
		catch (CoreException e) {} // don't do anything if this fails
	}
	
	public static double getSavedZoom(IDesignerEditor editor) {
		double level = 1.0;
		IResource resource = editor.getDataModelResource();
		try {
			String levelAsString = resource.getPersistentProperty(QUALIFIED_LAST_ZOOM);
			if (levelAsString != null)
				level = Double.parseDouble(levelAsString);
		} 
		// don't do anything if this fails
		catch (CoreException e) {} 
		catch (NumberFormatException x) {}
		return level;
	}

    public static Image getUnknownSmallIcon() {
        return ResourceManager.getPluginImage(UIDesignerPlugin.getDefault(), "icons/unknown_sm.png"); //$NON-NLS-1$
    }

    public static Image getUnknownLargeIcon() {
        return ResourceManager.getPluginImage(UIDesignerPlugin.getDefault(), "icons/unknown.png"); //$NON-NLS-1$
    }
    
    public static Point getAbsoluteLocation(EObject object, IDesignerDataModel dataModel) {
		EObject rootContainer = dataModel.getRootContainers()[0];
		ILayoutObject layoutObject = Adapters.getLayoutObject(object);
		
		// this may happen in malconfigured component tree
		if (layoutObject == null)
			return new Point(0, 0);
		
		org.eclipse.swt.graphics.Rectangle bounds = layoutObject.getBounds();
		Rectangle d2dBounds = new Rectangle(bounds);
		Point absLocation = d2dBounds.getLocation();

		if (object.equals(rootContainer))
			return new Point(0, 0);
		
		EObject parent = Adapters.getComponentInstance(object).getParent();
		while (!parent.equals(rootContainer)) {
			ILayoutObject parentLayoutObject = Adapters.getLayoutObject(parent);
			// this may happen in malconfigured component tree
			if (parentLayoutObject == null)
				break;
			absLocation.translate(
					new Rectangle(parentLayoutObject.getBounds()).getLocation());
			parent = Adapters.getComponentInstance(parent).getParent();
		}
		
		return absLocation;
	}

	public static EObject getCommonDirectParent(Collection potentialSiblings) {
		// return the common direct parent only if all members of collection are siblings
		EObject parent = null;
		for (Iterator iter = potentialSiblings.iterator(); iter.hasNext();) {
			EObject object = (EObject) iter.next();
			EObject curParent = Adapters.getComponentInstance(object).getParent();
			if (parent == null)
				parent = curParent;
			else if (!curParent.equals(parent))
				return null;
		}
		
		return parent;
	}
	
	// Taken from GEF code for tooltip figure creation
	public static IFigure createToolTip(String message) {
		if (message == null || message.length() == 0)
			return null;

		FlowPage fp = new FlowPage() {
			public Dimension getPreferredSize(int w, int h) {
				Dimension d = super.getPreferredSize(-1, -1);
				if (d.width > 150)
					d = super.getPreferredSize(150, -1);
				return d;
			}
		};
		fp.setOpaque(true);
		fp.setBorder(new MarginBorder(0, 2, 1, 0));
		TextFlow tf = new TextFlow();
		tf.setText(message);
		fp.add(tf);
		return fp;
	}

	public static EditPart getPrimaryEditPartForObject(IDesignerEditor editor, EObject object) {
		EditPartViewer upperViewer = editor.getUpperGraphicalViewer();
		EditPart part = (EditPart) upperViewer.getEditPartRegistry().get(object);
		if (part == null) {
			EditPartViewer lowerViewer = editor.getLowerGraphicalViewer();
			part = (EditPart) lowerViewer.getEditPartRegistry().get(object);
		}
		return part;
	}
	
	private static boolean isOutlineActive(IDesignerEditor editor) {
		Check.checkArg(editor);
		IEditorPart editorPart = (IEditorPart) editor.getAdapter(IEditorPart.class);
		IWorkbenchPartSite workbenchPartSite = editorPart.getSite();
		IWorkbenchPage workbenchPage = workbenchPartSite.getPage();
		IWorkbenchPart activePart = workbenchPage.getActivePart();
		return activePart instanceof ContentOutline;
	}
	
	public static void setDisplayModelSelectedObjects(IDisplayModel displayModel, Collection selectedObjects) {
		if (selectedObjects.isEmpty())
			return;
		
		EObject[] objects = (EObject[]) selectedObjects.toArray(new EObject[selectedObjects.size()]);
		displayModel.setSelectedObjects(objects);
	}

	public static void setSelectionToAffectedObjects(IDesignerEditor editor, Collection affectedObjects) {
		setDisplayModelSelectedObjects(editor.getDisplayModel(), affectedObjects);
		
		boolean setFocusToGraphicalViewer = !isOutlineActive(editor);
		List selectedParts = new ArrayList();
		for (Iterator iter = affectedObjects.iterator(); iter.hasNext();) {
			EObject object = (EObject) iter.next();
			EditPart part = getPrimaryEditPartForObject(editor, object);
			if (part != null) {
				selectedParts.add(part);
				if (setFocusToGraphicalViewer)
					part.getViewer().getControl().setFocus();
			}
		}
		if (!selectedParts.isEmpty()) {
			StructuredSelection selection = new StructuredSelection(selectedParts);
			editor.getSelectionManager().setSelection(selection);
		}
		else {
			if (setFocusToGraphicalViewer)
				editor.getUpperGraphicalViewer().getControl().setFocus();
			editor.getSelectionManager().setSelection(StructuredSelection.EMPTY);
		}
	}
	
	public static void eraseLastTargetFeedback(EditPartViewer viewer) {
		ModelObjectEditPart lastTargetEditPart = 
			(ModelObjectEditPart) viewer.getProperty(DesignerEditorPage.LAST_FEEDBACK_TARGET);
		if (lastTargetEditPart != null) {
			lastTargetEditPart.eraseTargetFeedback(CREATE_REQ);
		}
	}

	public static boolean isTransient(Object object) {
		return findTransientObject(object) != null;
	}
	
	public static boolean isSameObjectOrChild(EObject root, EObject object) {
		if (object != null) {
			if (object.equals(root))
				return true;
			
			IComponentInstance instance = Adapters.getComponentInstance(object);
			if (instance != null) {
				return isSameObjectOrChild(root, instance.getParent());
			}
		}
		
		return false;
	}

	public static EObject findTransientObject(Object object) {
		if (object instanceof EObject) {
			if (Adapters.isTransientObject((EObject) object))
				return (EObject) object;
			
			IComponentInstance componentInstance = Adapters.getComponentInstance((EObject) object);
			if (componentInstance != null) 
				return  findTransientObject(componentInstance.getParent());
		}
		
		return null;
	}

	public static IFigure findDeepestChildAt(IFigure parent, int x, int y) {
		PRIVATE_POINT.setLocation(x, y);
		parent.translateFromParent(PRIVATE_POINT);
		int locx = PRIVATE_POINT.x;
		int locy = PRIVATE_POINT.y;
		IFigure child;
		List children = parent.getChildren();
		for (int i = children.size(); i > 0;) {
			i--;
			child = (IFigure) children.get(i);
			if (child.isVisible()) {
				if (parent instanceof ScrollPane) {
					ScrollPane scrollPane = (ScrollPane) parent;
					child = scrollPane.findFigureAt(locx, locy);
				}
				else
					child = findDeepestChildAt(child, locx, locy);
				if (child != null)
					return child;
			}
		}
		if (parent.containsPoint(x, y))
			return parent;
		
		return null;
	}

	public static boolean canReorderChildren(EObject object, List<EObject> children) {
		IContainer container = Adapters.getContainer(object);
		if (container == null)
			return false;
		
		for (EObject child : children) {
			if (!container.canReorderChild(child))
				return false;
		}

		return true;
	}
	
	public static void replacePaletteRoot(final DesignerEditorPage editor, final PaletteRoot newRoot) {
		// We must do this after any potential DND completion event 
		// referencing some stale palette entry has been processed.
		Display.getCurrent().asyncExec(new Runnable() {
			public void run() {
				DefaultEditDomain editDomain = editor.getEditDomain();
				Check.checkState(editDomain != null);
				PaletteViewer paletteViewer = editDomain.getPaletteViewer();
				Check.checkContract(paletteViewer != null);
				paletteViewer.getPaletteRoot().setDefaultEntry(null);
				paletteViewer.setActiveTool(null);
				editDomain.setPaletteRoot(newRoot);
				paletteViewer.getPaletteRoot().setDefaultEntry(newRoot.getDefaultEntry());
				editor.setOpenDrawerNames(null);
			}
		});
	}

	public static List<String> getOpenPaletteDrawerNames(DesignerEditorPage editor, PaletteRoot palette) {
		List<String> openDrawerNames = editor.getOpenDrawerNames();
		if (openDrawerNames != null)
			return openDrawerNames;
		
		openDrawerNames = new ArrayList<String>();
		DefaultEditDomain editDomain = editor.getEditDomain();
		Check.checkState(editDomain != null);
		PaletteViewer paletteViewer = editDomain.getPaletteViewer();
		Check.checkContract(paletteViewer != null);
		List drawers = palette.getChildren();
		for (Iterator iter = drawers.iterator(); iter.hasNext();) {
			PaletteEntry entry = (PaletteEntry) iter.next();
			if (entry instanceof PaletteDrawer) {
				PaletteDrawer drawer = (PaletteDrawer) entry;
				if (paletteViewer.isExpanded(drawer))
					openDrawerNames.add(drawer.getLabel());
			}
		}
		
		editor.setOpenDrawerNames(openDrawerNames);
		return openDrawerNames;
	}
	
	private static boolean equalEntries(PaletteEntry entry1, PaletteEntry entry2) {
		PaletteDrawer d1 = null;
		PaletteDrawer d2 = null;
		if (entry1 instanceof PaletteDrawer)
			d1 = (PaletteDrawer) entry1;
		if (entry2 instanceof PaletteDrawer)
			d2 = (PaletteDrawer) entry2;
		
		if ((d1 != null) && (d2 != null)) {
			if (d1.getLabel().equals(d2.getLabel())) {
				List children1 = d1.getChildren();
				List children2 = d2.getChildren();
				if (children1.size() != children2.size())
					return false;
				
				// assume they're in the same order
				Iterator<PaletteEntry> iter1 = children1.iterator();
				Iterator<PaletteEntry> iter2 = children2.iterator();
				while (iter1.hasNext() && iter2.hasNext()) {
					PaletteEntry e1 = (PaletteEntry) iter1.next();
					PaletteEntry e2 = (PaletteEntry) iter2.next();
					if (!equalEntries(e1, e2))
						return false;
				}
			}
		}
		
		if ((entry1 instanceof CombinedTemplateCreationEntry)) {
			return entry1.equals(entry2);
		}
		
		return entry1.getLabel().equals(entry2.getLabel());
	}

	public static boolean equalPalettes(PaletteRoot p1, PaletteRoot p2) {
		if (((p1 == null) || (p2 == null)) && (p1 != p2))
			return false;
		
		List children1 = p1.getChildren();
		List children2 = p2.getChildren();
		if (children1.size() != children2.size())
			return false;
		
		// assume they're in the same order
		Iterator<PaletteEntry> iter1 = children1.iterator();
		Iterator<PaletteEntry> iter2 = children2.iterator();
		while (iter1.hasNext() && iter2.hasNext()) {
			PaletteEntry entry1 = (PaletteEntry) iter1.next();
			PaletteEntry entry2 = (PaletteEntry) iter2.next();
			if (!equalEntries(entry1, entry2))
				return false;
		}
		return true;
	}
	
	/**
	 * Test whether parent can contain at least one child of children. Test components only
	 * because this is checking clipboard and ILayoutObject adapter is not available.
	 * @param parent EObject
	 * @param children Collection<EObject>
	 * @param statuses List<IStatus> contains IStatus for children that cannot be contained
	 * @return true if one child can be contained by parent 
	 */
	public static boolean canContainAtLeastOneComponent(EObject parent, 
							Collection<EObject> children, List<IStatus> statuses) {
		if (children.isEmpty())
			return true;
		
		IContainer container = Adapters.getContainer(parent);
		if (container == null)
			return false;

		boolean canContainOneComponent = false;
		StatusHolder statusHolder = null;
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			statusHolder = new StatusHolder();
			EObject object = (EObject) iter.next();
			IComponent component = Adapters.getComponent(object);
			if (container.canContainComponent(component, statusHolder)) {
				canContainOneComponent = true;
				break;
			}
			else
				statuses.add(statusHolder.getStatus());
		}
		
		return canContainOneComponent;
	}

	public static LayoutAreaConfiguration getSavedConfiguration(IDisplayModel displayModel, IResource resource) {
		String savedConfig = null;
		
		// first try the resource
		try {
			if (resource != null)
				savedConfig = resource.getPersistentProperty(QUALIFIED_LAST_CONFIG);
		} 
		catch (CoreException e) {} // don't do anything if this fails

		// then try the plugin setting
		if ((savedConfig == null) || (savedConfig.length() == 0))
			savedConfig = UIModelPlugin.getDefault().getPreferenceStore().getString(LAST_CONFIG);

		for (Iterator iter = displayModel.getLayoutAreaConfigurations().iterator(); iter.hasNext();) {
			LayoutAreaConfiguration aConfig = (LayoutAreaConfiguration) iter.next();
			if (aConfig.getID().equals(savedConfig)) {
				return aConfig;
			}
		}
		
		return null;
	}
	
	public static void saveCurrentLayout(IDesignerEditor editor) {
		if (editor == null)
			return;
		IDisplayModel displayModel = editor.getDisplayModel();
		LayoutAreaConfiguration currentLayout = displayModel.getCurrentConfiguration();
		// save in the resource
		if (currentLayout != null) {
			IDesignerDataModel dataModel = editor.getDataModel();
			IResource resource = dataModel.getModelSpecifier().getPrimaryResource();
			try {
				resource.setPersistentProperty(EditorUtils.QUALIFIED_LAST_CONFIG, currentLayout.getID());
			} 
			catch (CoreException e) {} // don't do anything if this fails
		}
	}
	
	public static boolean isScrollableVertically(Object object) {
		if (object instanceof EObject) {
			IAttributes attributes = Adapters.getAttributes((EObject) object);
			return attributes.getBooleanAttribute(
					CommonAttributes.VERTICAL_SCROLLABLE_CONTAINER, false);
		}
		
		return false;
	}
	
	public static boolean isScrollableHorizontally(Object object) {
		if (object instanceof EObject) {
			IAttributes attributes = Adapters.getAttributes((EObject) object);
			return attributes.getBooleanAttribute(
					CommonAttributes.HORIZONTAL_SCROLLABLE_CONTAINER, false);
		}
		
		return false;
	}
	
	public static void tryToUpdateProject(IProject project) {
		doUpdateSession(project, true);
	}
	
	public static void tryToUpgradeProjectDesigns(IProject project) {	
		doUpdateSession(project, false);
	}
	
	static class UpdaterRunnable implements IRunnableWithProgress, IProjectUpdateSession.IStateListener {
		private final IProjectUpdateSession session;
		private boolean waiting = true;

		UpdaterRunnable(IProjectUpdateSession session) {
			this.session = session;
			session.addStateListener(this);
		}

		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			session.updateProjects();
			while (waiting) {
				Thread.sleep(100);
			}
		}

		public void stateChanged(IProjectUpdateSession session) {
			if (session.getState() == IProjectUpdateSession.State.COMPLETE) {
				waiting = false;
				session.removeStateListener(this);
			}
		}
	}
	
	private static void doUpdateSession(IProject project, boolean showProjectUpdateDialog) {
		Shell shell = WorkbenchUtils.getSafeShell();
		if (shell == null || !shell.isVisible())
			return;
	
		IProjectUpdateSession updateSession = CarbideUpdaterPlugin.getProjectUpdateManager().createSession();
		updateSession.addUpdatableProject(project);
		updateSession.addRefactoringProject(project);
		updateSession.setShowProjectUpdateDialog(showProjectUpdateDialog);
		updateSession.setSilentIfEmpty(true);
		
		UpdaterRunnable operation = new UpdaterRunnable(updateSession);
		try {
			new ProgressMonitorDialog(shell).run(true, false, operation);
		} catch (InvocationTargetException x) {
			UIDesignerPlugin.getDefault().log(x);
			
		} catch (InterruptedException x) {
			UIDesignerPlugin.getDefault().log(x);
		}
	}

	public static IDesignerDataModel loadModel(IDesignerDataModelSpecifier modelSpecifier, StatusHolder holder) {
		LoadResult loadResult = modelSpecifier.load();
		IStatus status = loadResult.getStatus();
		if (status != null) {
			// try to use the message from the IStatus. If none default to generic error
			String reason = status.getMessage();
			if (TextUtils.isEmpty(reason)) {
				reason = Strings.getString("AbstractDesignerDataModelEditor.LoadFailedError"); //$NON-NLS-1$
			}
			holder.setStatus(makeOrReportEditorOpenStatus(reason, status)); //$NON-NLS-1$		
		}
		return loadResult.getModel();
	}

	public static IStatus makeOrReportEditorOpenStatus(String reasonString, IStatus status) {
        if (status == null) {
        	status = Logging.newStatus(UIDesignerPlugin.getDefault(), IStatus.ERROR, reasonString);
        }
		Logging.log(UIDesignerPlugin.getDefault(), status);
		return status;
	}
	
	public static boolean isRequestForTargetFeedback(Request request) {
		return RequestConstants.REQ_ADD.equals(request.getType()) || 
					RequestConstants.REQ_CREATE.equals(request.getType()) ||
							RequestConstants.REQ_MOVE.equals(request.getType());
	}

	public static boolean isNonLayoutComponent(IAdaptable adaptable) {
		if (adaptable != null) {
			IAttributes attributes = (IAttributes) adaptable.getAdapter(IAttributes.class);
			if (attributes != null)
				return attributes.getBooleanAttribute(CommonAttributes.IS_NON_LAYOUT_OBJECT, false);
		}
		
		return false;
	}

	public static boolean getNoTransientChildClipping(EObject object) {
		IAttributes attributes = Adapters.getAttributes(object);
		if (attributes != null)
			return attributes.getBooleanAttribute(CommonAttributes.NO_TRANSIENT_CHILD_CLIPPING, false);
		return false;
	}
}

