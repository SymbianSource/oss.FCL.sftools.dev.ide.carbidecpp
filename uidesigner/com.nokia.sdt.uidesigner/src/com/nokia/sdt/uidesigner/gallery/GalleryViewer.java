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
package com.nokia.sdt.uidesigner.gallery;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.ControlVisitor;

import org.eclipse.jface.util.ListenerList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.forms.widgets.ColumnLayoutData;

import java.util.*;

public class GalleryViewer extends ContentViewer {

	private GalleryMouseListener mouseListener = new GalleryMouseListener();
						// must be 0.5, 0.75, or 1.0
	public static final double DEFAULT_SCALE = 0.75;
	public static final int MAX_COLUMNS = 10;

	private FormToolkit toolkit;
	private ScrolledForm form;
	private ArrayList sections = new ArrayList();
	private HashMap controlToObjectMap = new HashMap();
	private Object selectedObject;
	private double currentScaling = DEFAULT_SCALE;
	private ListenerList openListeners = new ListenerList(1);
	
	public GalleryViewer(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(parent);

		TableWrapLayout layout = new TableWrapLayout();
		form.getBody().setLayout(layout);
		form.setDelayedReflow(true);
		
		form.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				doDispose();
			}
		});
		
		form.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				form.reflow(true);
				formResized();
			}
		});

	}
	
	private void doDispose() {
		toolkit.dispose();
	}
	
	private void formResized() {
		Rectangle formBounds = form.getBody().getClientArea();
		Control[] children = form.getBody().getChildren();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				Control control = children[i];
				if (control instanceof Section) {
					Point size = control.getSize();
					size.x = formBounds.width;
					control.setSize(size);
				}
			}
		}
	}
	
	void layoutForm() {
		form.getBody().layout(true, true);
		formResized();
	}
	
	protected void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);
		refresh();
	}

	public Control getControl() {
		return form;
	}

	public ISelection getSelection() {
		ISelection result;
		if (selectedObject != null)
			result = new StructuredSelection(selectedObject);
		else
			result = new StructuredSelection();
		return result;
	}
	
	private void clearContents() {
		// clear the viewer's menu from the children
		ControlVisitor.visitChildren(form.getBody(), 
				new ControlVisitor.Visitor() {
					public Object visit(Control c) {
						c.setMenu(null);
						return null;
					}
				});
		
		Control[] children = form.getBody().getChildren();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				children[i].setMenu(null);
				children[i].dispose();
			}
		}
		controlToObjectMap.clear();
		sections.clear();
	}
	
	public void addOpenListener(IOpenListener listener) {
		openListeners.add(listener);
	}
	
	private void fireOpenEvent(final OpenEvent event) {
		Object[] listeners = openListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final IOpenListener curr = (IOpenListener) listeners[i];
			SafeRunnable.run(new SafeRunnable() {
				public void run() {
					curr.open(event);
				}
			});
		}
	}
	
	public void setScaling(double scaling) {
		if (scaling == 0.5 || scaling == 0.75 || scaling == 1.0) {
			if (scaling != currentScaling) {
				for (Iterator iter = controlToObjectMap.values().iterator(); iter.hasNext();) {
					LabeledImage li = (LabeledImage) iter.next();
					li.setScaling(scaling);
				}
				currentScaling = scaling;
				form.reflow(true);	
			}
		}
	}
		
	public double getScaling() {
		return currentScaling;
	}
	
	public void setFocus() {
		form.setFocus();
	}
	
	private void checkMenu() {
		Menu m = form.getMenu();
		Check.checkState(m == null || !m.isDisposed());
	}
	
	class ImageInfo implements Comparable {
		Object model;
		String label;
		Image image;
	
		public ImageInfo(Object model, String label, Image image) {
			this.model = model;
			this.label = label;
			this.image = image;
		}

		public int compareTo(Object o) {
			Check.checkArg(o instanceof ImageInfo);
			ImageInfo other = (ImageInfo) o;
			return label.compareToIgnoreCase(other.label);
		}
	}

	public void refresh() {
		checkMenu();
		clearContents();
		checkMenu();
		Object input = getInput();
		if (input == null)
			return;
		
		ITreeContentProvider tcp = (ITreeContentProvider) getContentProvider();
		ILabelProvider lp = (ILabelProvider) getLabelProvider();
		
		Object[] sectionObjects = tcp.getElements(input);
		if (sectionObjects != null) {
			for (int i = 0; i < sectionObjects.length; i++) {
				String text = lp.getText(sectionObjects[i]);
				Section section = addSection(text);
				Object[] imageObjects = tcp.getChildren(sectionObjects[i]);
				if (imageObjects != null) {
					ArrayList entries = new ArrayList();
					for (int j = 0; j < imageObjects.length; j++) {
						String label = lp.getText(imageObjects[j]);
						Image image = lp.getImage(imageObjects[j]);
						if (image != null) {
							ImageInfo info = new ImageInfo(imageObjects[j], label, image);
							entries.add(info);
						}
					}
					Collections.sort(entries);
					for (Iterator iter = entries.iterator(); iter.hasNext();) {
						ImageInfo info = (ImageInfo) iter.next();
						addImageToSection(section, info.image, info.label, info.model);
					}
				}
			}
		}
		updateColumnLayouts();
		form.reflow(true);
	}
	
	private Section addSection(String projectName) {
		Section section = toolkit.createSection(form.getBody(), 
				Section.DESCRIPTION|Section.TWISTIE|Section.EXPANDED);
		sections.add(section);
		TableWrapData twd = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(twd);
			
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
			}
		});
		section.setText(projectName);
		toolkit.createCompositeSeparator(section);
		Composite sectionClient = toolkit.createComposite(section);
		ColumnLayout layout = new ColumnLayout();
		layout.horizontalSpacing = 1;
		layout.leftMargin = 3;
		sectionClient.setLayout(layout);
		
		section.setClient(sectionClient);
		return section;
	}
	
	private void addImageToSection(Section section, Image image, String name, Object modelObject) {
		Composite sectionClient = (Composite) section.getClient();
		LabeledImage li = new LabeledImage(sectionClient);
		li.setImage(image);
		li.setText(name);
		li.setModelObject(modelObject);
		li.setScaling(currentScaling);
		li.setMenu(sectionClient.getMenu());
		controlToObjectMap.put(modelObject, li);
		li.addMouseListener(mouseListener);
		ColumnLayoutData cld = new ColumnLayoutData();
		cld.horizontalAlignment = ColumnLayoutData.LEFT;
		li.setLayoutData(cld);
		
		FormColors colors = toolkit.getColors();
		li.setBackground(colors.getBackground());
	}
	
	public Section[] getSections() {
		return (Section[]) sections.toArray(new Section[sections.size()]);
	}
	
	private void updateColumnLayouts() {
		Control[] children = form.getBody().getChildren();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				Control control = children[i];
				if (control instanceof Section) {
					Section section = (Section) control;
					Composite sectionClient = (Composite) section.getClient();
					Control[] sectionChildren = sectionClient.getChildren();
					int numChildren = sectionChildren != null? 
							sectionChildren.length : 1;
					ColumnLayout layout = (ColumnLayout) sectionClient.getLayout();
					layout.maxNumColumns = Math.min(MAX_COLUMNS, Math.max(1, numChildren));
				}
			}
		}
	}
	
	private void selectObject(Object modelObject) {
		if (selectedObject != modelObject) {
			if (selectedObject != null) {
				LabeledImage oldImage = findControlForModelObject(selectedObject);
				if (oldImage != null)
					oldImage.setSelected(false);
			}
			selectedObject = modelObject;
			if (selectedObject != null) {
				LabeledImage newImage = findControlForModelObject(selectedObject);
				if (newImage != null)
					newImage.setSelected(true);
			}
		}
	}
	
	void unselectObject(Object modelObject) {
		LabeledImage image = findControlForModelObject(modelObject);
		if (image != null)
			image.setSelected(false);
		if (modelObject == selectedObject)
			selectedObject = null;
	}
	
	LabeledImage findControlForModelObject(Object modelObject) {
		LabeledImage result = (LabeledImage) controlToObjectMap.get(modelObject);
		return result;
	}
	
	public void setSelection(ISelection selection, boolean reveal) {

	}
	
class GalleryMouseListener extends MouseAdapter {
		
		Object getModelObjectFromEvent(MouseEvent e) {
			Object result = null;
			Object source = e.getSource();
			if (source instanceof LabeledImage) {
				result = ((LabeledImage)source).getModelObject();
			}
			return result;
		}

		public void mouseDoubleClick(MouseEvent e) {
			OpenEvent event = new OpenEvent(GalleryViewer.this, getSelection());
			fireOpenEvent(event);
		}

		public void mouseDown(MouseEvent e) {
			if (e.button != 3){
				Object mo = getModelObjectFromEvent(e);
				if (mo != null) {
					if ((e.stateMask & SWT.SHIFT) == 0)
						selectObject(mo);
					else
						unselectObject(mo);
				}
			}
		}

		public void mouseUp(MouseEvent e) {
		}
	}
}
