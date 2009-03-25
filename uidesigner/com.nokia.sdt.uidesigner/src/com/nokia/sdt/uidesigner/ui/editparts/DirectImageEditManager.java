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
package com.nokia.sdt.uidesigner.ui.editparts;

import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.uidesigner.ui.utils.Strings;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;

/**
 * This direct image edit manager merely invokes the image
 * editor class.
 * 
 *
 */
public class DirectImageEditManager extends DirectEditManager {

    private String propertyId;
    private EObject object;
    private String propertyPath;
    /** Flag letting us overcome buggy impl which doesn't cleanly let us tell
     * when we can commit() 
     */
    private boolean attached;
    
    public DirectImageEditManager(GraphicalEditPart source, CellEditorLocator cellEditorLocator, String propertyPath, EObject object) {
        super(source, null, cellEditorLocator);
        this.propertyPath = propertyPath;
        int idx = propertyPath.lastIndexOf('.');
        this.propertyId = propertyPath.substring(idx+1);
        this.object = object;
    }

    @Override
    protected CellEditor createCellEditorOn(Composite composite) {
        // get the property -- mainly to find its property source
        NodePathLookupResult result = ModelUtils.readProperty(object, propertyPath, false);
        Check.checkArg(result.status == null);
        
        CellEditor editor = null;

        // find the property descriptor, which tells us the cell editor
        IPropertyDescriptor[] descs = result.properties.getPropertyDescriptors();
        for (int i = 0; i < descs.length; i++) {
            if (descs[i].getId().equals(propertyId)) {
                editor = descs[i].createPropertyEditor(composite);
                break;
            }
        }

        if (editor == null) {
            Logging.log(UIDesignerPlugin.getDefault(),
                    Logging.newStatus(UIDesignerPlugin.getDefault(), 
                            IStatus.ERROR, 
                            MessageFormat.format(Strings.getString("DirectImageEditManager.NoCellEditorError"), //$NON-NLS-1$
                                    new Object[] { propertyPath })));
            return null;
        }
        
        return editor;
    }

    @Override
    protected void initCellEditor() {
        getDirectEditRequest().setDirectEditFeature(propertyPath);
        
        IPropertySource properties = Adapters.getPropertySource(object);
        NodePathLookupResult result = ModelUtils.readProperty(object, properties, propertyPath, false);
        getCellEditor().setValue(result.result);
    }

    @Override
    protected void unhookListeners() {
        super.unhookListeners();
        attached = false;
    }
    
    @Override
    protected void setCellEditor(CellEditor editor) {
        super.setCellEditor(editor);
        
        if (editor != null) {
            Control control = editor.getControl();

            // Tricky code: we don't have direct access to the button
            // that appears in a DialogCellEditor, so we find it,
            // and attach the events there.  We can't attach these
            // events to a Composite (the actual control), 
            // since they are ignored.
            if (control instanceof Composite) {
                Composite composite = (Composite) control;
                Control[] kids = composite.getChildren();
                for (int i = 0; i < kids.length; i++) {
                    if (kids[i] instanceof Button) {
                        control = kids[i];
                        break;
                    }
                }
            }
            
            // make the button go away when the mouse leaves without clicking
            // TODO: fix some other way (the mouse may have already left before we get here)
            control.addMouseTrackListener(new MouseTrackAdapter() {
                public void mouseExit(MouseEvent e) {
                    if (attached) {
                        commit();
                        attached = false;
                    }
                }
            });

            // we can't override hookListeners() to do this cleanly,
            // but we know the super call implicitly attaches listeners
            attached = true;
        }
    }
    
}
