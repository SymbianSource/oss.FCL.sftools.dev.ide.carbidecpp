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

package com.nokia.sdt.ui.skin;


import org.eclipse.swt.graphics.*;

import java.util.Collection;


/**
 * 
 * <br><br>
 * The definition of a skin<br>
 * A skin has an editor area whose image size should match the<br>
 * size of the layout area configuration to which it belongs<br>
 * The editor area should not be larger than that size<br>
 * The skin has an optional background color to allow matching it to the editor's background.<br>
 * A skin has optional hotzones
 */
public interface ISkin {
	
	/**
	 * The definition of a hotzone
	 * A hotzone has a bounds, a listener, and optional states
	 */
	public interface Hotzone {
		
		/**
		 * A hotzone defined state
		 * A hotzone state has an optional image
		 */
		public interface State {
			
			/**
			 * @return the name of the state as <code>String</code>
			 */
			public String getName();
			
			/**
			 * @return the image of the state as <code>org.eclipse.swt.graphics.Image</code> or <code>null</code>
			 */
			public Image getImage();
		}
	
		/**
		 * @return the bounds of the hotzone as <code>org.eclipse.swt.graphics.Rectangle</code>
		 */
		public Rectangle getBounds();
		
		/**
		 * @return the ID of the hotzone as <code>Object</code>
		 */
		public Object getID();
		
		/**
		 * @return <code>true</code> if this hotzone is "sticky"<br>
		 * Sticky hotzones change state when pressed
		 */
		public boolean isSticky();
		
		/**
		 * @return the current state of this hotzone as <code>State</code>
		 */
		public State getCurrentState();
		
		/**
		 * @return the supported states of this hotzone as <code>java.util.Collection</code>
		 */
		public Collection getStates();
		
		/**
		 * Set the hotzone to the next state
		 */
		public void setNextState();
		
		/**
		 * @param listener a <code>SkinHotzoneListener</code>
		 */
		public void addListener(SkinHotzoneListener listener);

		/**
		 * @param listener a <code>SkinHotzoneListener</code>
		 */
		public void removeListener(SkinHotzoneListener listener);
	}
	
	/**
	 * @return the image of this skin as <code>org.eclipse.swt.graphics.Image</code>
	 */
	public Image getImage();
	
	/**
	 * Returns the width and height of the entire skin
	 */
	Point getSkinSize();
	
	/**
	 * @return the editor area of this skin as <code>org.eclipse.swt.graphics.Rectangle</code>
	 */
	public Rectangle getEditorAreaBounds();
	
	/**
	 * @return the hotzones of this skin as <code>java.util.Collection</code>
	 */
	public Collection getHotzones();
	
	/**
	 * Return an internal, non-localized name for the skin.
	 */
	String getID();
	
	/**
	 * @return the display name of this skin as <code>String</code>
	 */
	String getDisplayName();
	
	/**
	 * @return the background color of this skin as <code>RGB</code>
	 */
	RGB getBackgroundRGB();
}