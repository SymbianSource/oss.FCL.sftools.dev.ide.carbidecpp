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
package com.nokia.sdt.utils.drawing;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ImageDump extends Dialog {

    private static final int NUM_COLUMNS = 8+1;
    private static final int NUM_ROWS = 16;
    private Slider Y_slider;
    private Slider X_slider;
    private Table table;
    private ImageData data;
    private Color[][][] colors;
    public ImageDump(Shell parentShell, Image image) {
        super(parentShell);
        this.data = image.getImageData();
        this.colors = new Color[NUM_ROWS][NUM_COLUMNS][2];
    }

    public void dispose() {
        for (int y = 0; y < NUM_ROWS; y++) {
            for (int x = 1; x < NUM_COLUMNS; x++) {
                if (colors[y][x][0] != null)
                    colors[y][x][0].dispose();
                if (colors[y][x][1] != null)
                    colors[y][x][1].dispose();
            }
        }
    }

    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        container.setLayout(new FormLayout());

        table = new Table(container, SWT.BORDER+SWT.VIRTUAL);
        final FormData formData = new FormData();
        formData.bottom = new FormAttachment(0, 265);
        formData.right = new FormAttachment(0, 485);
        formData.top = new FormAttachment(0, 5);
        formData.left = new FormAttachment(0, 5);
        table.setLayoutData(formData);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        X_slider = new Slider(container, SWT.HORIZONTAL);
        final FormData formData_1 = new FormData();
        formData_1.bottom = new FormAttachment(0, 290);
        formData_1.left = new FormAttachment(0, 20);
        formData_1.right = new FormAttachment(0, 120);
        formData_1.top = new FormAttachment(table, 5, SWT.BOTTOM);
        X_slider.setLayoutData(formData_1);

        Y_slider = new Slider(container, SWT.HORIZONTAL);
        final FormData formData_2 = new FormData();
        formData_2.bottom = new FormAttachment(X_slider, 0, SWT.BOTTOM);
        formData_2.right = new FormAttachment(0, 265);
        formData_2.top = new FormAttachment(X_slider, 0, SWT.TOP);
        formData_2.left = new FormAttachment(0, 170);
        Y_slider.setLayoutData(formData_2);

        final Label xLabel = new Label(container, SWT.NONE);
        final FormData formData_3 = new FormData();
        formData_3.bottom = new FormAttachment(X_slider, 0, SWT.BOTTOM);
        formData_3.right = new FormAttachment(X_slider, -5, SWT.LEFT);
        formData_3.top = new FormAttachment(table, 5, SWT.BOTTOM);
        formData_3.left = new FormAttachment(table, 0, SWT.LEFT);
        xLabel.setLayoutData(formData_3);
        xLabel.setText("X"); //$NON-NLS-1$

        final Label yLabel = new Label(container, SWT.NONE);
        final FormData formData_4 = new FormData();
        formData_4.left = new FormAttachment(0, 145);
        formData_4.right = new FormAttachment(0, 165);
        formData_4.bottom = new FormAttachment(Y_slider, 0, SWT.BOTTOM);
        formData_4.top = new FormAttachment(Y_slider, 0, SWT.TOP);
        yLabel.setLayoutData(formData_4);
        yLabel.setText("Y"); //$NON-NLS-1$
        //
        
        X_slider.setValues(0, 0, data.width, 16, 1, 16);
        Y_slider.setValues(0, 0, data.height, 16, 1, 16);
        
        X_slider.addSelectionListener(new SelectionAdapter() {
            /* (non-Javadoc)
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateTable();
            }
        });
        Y_slider.addSelectionListener(new SelectionAdapter() {
            /* (non-Javadoc)
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateTable();
            }
        });
        
        initTable();
        updateTable();
        
        return container;
    }


    /**
     * 
     */
    private void initTable() {
        for (int x = 0; x < NUM_COLUMNS; x++) {
            TableColumn tableColumn = new TableColumn(table, SWT.NONE);
            tableColumn.setWidth(64);
        }
        for (int y = 0; y < NUM_ROWS; y++) {
            new TableItem(table, SWT.NONE, y);
        }
    }

    /**
     * 
     */
    private void updateTable() {
        
        int x_offs = X_slider.getSelection();
        int y_offs = Y_slider.getSelection();
        for (int y = 0; y < NUM_ROWS; y++) {
            TableItem tableItem = table.getItem(y);
            int absY = (y+y_offs);
            if (absY >= data.height)
            	break;
            tableItem.setText(0, ""+absY); //$NON-NLS-1$
            for (int x = 1; x < NUM_COLUMNS; x++) {
                int absX = x+x_offs;
                if (absX >= data.width)
                	continue;
                int pixel = data.getPixel(absX-1, absY);
                int alpha = data.getAlpha(absX-1, absY);
                RGB rgb = data.palette.getRGB(pixel);
                if (colors[y][x][0] != null)
                    colors[y][x][0].dispose();
                if (colors[y][x][1] != null)
                    colors[y][x][1].dispose();
                colors[y][x][0] = new Color(getShell().getDisplay(), rgb);
                colors[y][x][1] = new Color(getShell().getDisplay(), 
                        new RGB(255-rgb.red, 255-rgb.green, 255-rgb.blue));
                tableItem.setBackground(x, colors[y][x][0]);
                tableItem.setForeground(x, colors[y][x][1]);
                tableItem.setText(x, Integer.toHexString(rgb.red) + "/" //$NON-NLS-1$
                        + Integer.toHexString(rgb.green) + "/" //$NON-NLS-1$
                        + Integer.toHexString(rgb.blue) + "@" //$NON-NLS-1$
                        + Integer.toHexString(alpha));
            }
        }
            
        for (int x = 1; x < NUM_COLUMNS; x++) {
            TableColumn tableColumn = table.getColumn(x);
            tableColumn.setText(""+(x+x_offs-1)); //$NON-NLS-1$
        }
        for (int x = 0; x < NUM_COLUMNS; x++) {
            TableColumn tableColumn = table.getColumn(x);
            tableColumn.pack();
        }
        
    }
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
                true);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
    }

    protected Point getInitialSize() {
        return new Point(500, 375);
    }

    static class AppWindow extends ApplicationWindow {
        public AppWindow() {
            super(null);
        }
        
        public void run() {
            setBlockOnOpen(true);
            Image image = new Image(Display.getCurrent(), "c:/users/eclipse.jpg"); //$NON-NLS-1$
            ImageDump dump = new ImageDump(getShell(), image);
            dump.open();
            
        }
    }
    public static void main(String[] args) {
        new AppWindow().run();
    }
}
