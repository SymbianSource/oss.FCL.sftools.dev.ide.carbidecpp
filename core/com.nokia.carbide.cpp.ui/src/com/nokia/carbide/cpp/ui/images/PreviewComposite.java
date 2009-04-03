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

package com.nokia.carbide.cpp.ui.images;

import com.nokia.carbide.cpp.internal.ui.Messages;
import com.nokia.sdt.utils.ImageUtils;
import com.nokia.sdt.utils.ui.ImageIcon;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;

import java.text.MessageFormat;


/**
 * Widget showing a preview of an image which scrolls when the image
 * becomes too large.
 *
 */
public class PreviewComposite extends Composite {

	private ImageIcon imageIcon;
	private Label infoLabel;
	private Hyperlink errorLink;
	private ScrolledComposite imageScroller;
	private String errorText;
	protected Image missingImage;

	static class SelectableMessageDialog extends Dialog {

		private final String text;
		private final String title;

		public SelectableMessageDialog(Shell parentShell, String title, String text) {
			super(parentShell);
			this.title = title;
			this.text = text;
		}
		
		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText(title);
		}
		
		@Override
		protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);
			
			Text textWidget = new Text(composite, SWT.WRAP | SWT.READ_ONLY);
			textWidget.setText(text);
			return composite;
		}
		
		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			Button okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
					true);
			okButton.setFocus();
		}
		
	}

	/**
	 * @param parent
	 * @param style
	 */
	public PreviewComposite(Composite parent, int style) {
		super(parent, style);
		
		ImageData data = ImageUtils.createStandard32BitImageData(1, 1);
		data.data[0] = -1;
		this.missingImage = new Image(Display.getDefault(), data);
		

		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 1;
		setLayout(gridLayout_2);

		final Label previewLabel = new Label(this, SWT.NONE);
		previewLabel.setText(Messages.getString("PreviewComposite.PreviewLabel")); //$NON-NLS-1$
		previewLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));

		imageScroller = new ScrolledComposite(this, SWT.V_SCROLL | SWT.H_SCROLL);
		imageScroller.setLayoutData(new GridData(SWT.CENTER, GridData.BEGINNING, true, true));
		
		imageIcon = new ImageIcon(imageScroller, SWT.NONE);
		imageIcon.setToolTipText(Messages.getString("PreviewComposite.PreviewIconTooltip")); //$NON-NLS-1$
		imageIcon.setMinimumSize(null);
		imageIcon.setMaximumSize(null);
		imageIcon.setLayout(new FillLayout());
		imageScroller.setContent(imageIcon);
		imageScroller.setAlwaysShowScrollBars(false);
		imageScroller.setExpandHorizontal(true);
		imageScroller.setExpandVertical(true);

		infoLabel = new Label(this, SWT.NONE);
		infoLabel.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
		
		errorLink = new Hyperlink(this, SWT.NONE);
		errorLink.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
		errorLink.addHyperlinkListener(new IHyperlinkListener() {

			public void linkActivated(HyperlinkEvent e) {
				SelectableMessageDialog dialog = new SelectableMessageDialog(
						getShell(),
						Messages.getString("PreviewComposite.CannotLoadImageLabel"),//$NON-NLS-1$
						e.getHref().toString());
				dialog.open();
			}

			public void linkEntered(HyperlinkEvent e) {
			}

			public void linkExited(HyperlinkEvent e) {
			}
			
		});
		errorLink.setText(""); //$NON-NLS-1$
		errorLink.setVisible(false);
	}
	
	@Override
	public void dispose() {
		if (missingImage != null) {
			missingImage.dispose();
			missingImage = null;
		}
		super.dispose();
	}
	
	/**
	 * 
	 */
	public void refresh(IImageModel model) {
		if (model == null) {
			refresh(null, null);
		} else {
			errorText = null;
			Image realImage = null;
			try {
				realImage = model.getImageDescriptor(null).createImage();
			} catch (CoreException e) {
				errorText = e.getLocalizedMessage();
			}
			refresh(realImage, errorText);
		}
	}
	
	/**
	 * 
	 */
	public void refresh(Image image, String errorText) {
		Image oldImage = imageIcon.getImage();
		if (oldImage != null && oldImage != missingImage) {
			oldImage.dispose();
		}
		
		
		if (image == null && errorText == null) {
			imageIcon.setVisible(true);
			imageIcon.setImage(null);
			infoLabel.setText(errorText != null ? errorText : ""); //$NON-NLS-1$
		} else {
			imageIcon.setVisible(true);
			imageIcon.setImage(image);
			if (image == null || image == missingImage) {
				imageScroller.setMinSize(1, 1);
				imageIcon.setMinimumSize(new Point(16, 16));
				// errorText is set from #errorReported()
				infoLabel.setVisible(false);
				errorLink.setVisible(true);
				errorLink.setText(Messages.getString("PreviewComposite.CannotLoadImageLabel")); //$NON-NLS-1$
				if (errorText == null)
					errorText = Messages.getString("PreviewComposite.UnknownErrorDescription"); //$NON-NLS-1$
				errorLink.setHref(errorText);
				errorLink.setUnderlined(true);
			}
			else {
				Rectangle bounds = image.getBounds();
				imageScroller.setMinSize(bounds.width, bounds.height);
				imageIcon.setMinimumSize(null);
				infoLabel.setText(MessageFormat.format(Messages.getString("PreviewComposite.ImageDimensionsLabel"), //$NON-NLS-1$
						new Object[] { bounds.width, bounds.height }));
				infoLabel.setVisible(true);
				errorLink.setVisible(false);
			}
		}
		layout();
	}
}
