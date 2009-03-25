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
/* START_USECASES: CU6 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.vieweditor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import com.nokia.sdt.symbian.dm.UIQModelUtils;
/**
 * Class for serializing elements from the local model to/from a byte array
 * The used format is: 
 * int : length of the instance's name
 * String : instance's name
 * int: length of the instance's component ID
 * String: instance´s component ID
 * int: length of the command list name
 * String: name of the command list parent * 
 */
public class CommandElementTransfer extends ByteArrayTransfer{
	private static CommandElementTransfer instance = new CommandElementTransfer();
	private static final String TYPE_NAME = "element-transfer-format"; //$NON-NLS-1$
	private static final int TYPEID = registerType(TYPE_NAME);
	private final String UI_COMMAND_LIST = UIQModelUtils.UIQ_COMMAND_LIST;
	private final String UI_ANONYMOUS_GROUP = "com.nokia.carbide.uiq.AnonymousGroup"; //$NON-NLS-1$
	private final String UI_NAMED_GROUP = "com.nokia.carbide.uiq.NamedGroup"; //$NON-NLS-1$
	private final String UI_COMMAND = UIQModelUtils.UIQ_COMMAND;
	private final String UI_COMMAND_ID = UIQModelUtils.UIQ_COMMAND_ID;

	/**
	 * Returns the singleton element transfer instance.
	 */
	public static CommandElementTransfer getInstance() {
	     return instance;
	}
	/**
	 * Avoid explicit instantiation
	 */
	private CommandElementTransfer() {
	}
	
	/**
	 * Method declared on Transfer.
	 */
	protected Object nativeToJava(TransferData transferData) {
		if (isSupportedType(transferData)) {
			byte[] bytes = (byte[])super.nativeToJava(transferData);
			return fromByteArray(bytes);
		}
		else
			return null;
	} 
	
	/**
	 * Converts from ByteArray to the ElementTrasnfer format
	 * @param bytes
	 * @return
	 */
	public Object fromByteArray(byte[] bytes) {
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
		ElementTransfer element = null;
		try {
			int size = in.readInt();
			byte[] name = new byte[size];
			in.read(name);
			String nameInstance = new String (name);
			size = in.readInt();
			name = new byte[size];
	    	in.read(name);
	    	String componentID = new String (name);
	    	size = in.readInt();
	    	name = new byte[size];
	    	in.read(name);
	    	String nameCommandList = new String (name);	         
	    	element = new ElementTransfer(nameInstance);
	        element.setComponentID(componentID);
	        element.setCommandListName(nameCommandList);
	     } catch (IOException e) {
	    	 System.out.println(e.toString());
	         return null;
	     }
	   return (Object)element;
	}
	
	/**
	 * Types accepted
	 */
	protected int[] getTypeIds() {
		return new int[] { TYPEID };
	}

   /**
    * Method declared on Transfer.
    */
   protected String[] getTypeNames() {
	   return new String[] { TYPE_NAME };
   }
   
   /**
    * Method declared on Transfer.
    */
   protected void javaToNative(Object object, TransferData transferData) {
	   byte[] bytes = null;
	   Object[] objects = (Object[]) object;
	   if ((objects[0]) instanceof CommandModel){
		   bytes = commandToByte((CommandElementBase) objects[0]);
	   }
	   else if ((objects[0] instanceof NamedGroup) || (objects[0] instanceof AnonymousGroup)){
		   bytes = commandToByte((CommandElementBase) objects[0]);
	   }
	   if (bytes != null)
		   super.javaToNative(bytes, transferData);
   }
		  
   /**
    * Transfer data is an description of the element.  Serialized version is:
    * name of the object
	* id of the component
	* name of the commandList
	*/
   private byte[] commandToByte(CommandElementBase object) {
	   ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	   DataOutputStream out = new DataOutputStream(byteOut);
	   String componentID = ""; //$NON-NLS-1$
	   byte[] bytes = null;
	   //Get the componentID
	   if (object instanceof CommandModel){
		   componentID = UI_COMMAND;
	   }
	   else if (object instanceof AnonymousGroup){
		   componentID = UI_ANONYMOUS_GROUP;
	   }
	   else if (object instanceof NamedGroup){
		   componentID = UI_NAMED_GROUP;
	   }
	   try {
		   /* write number of markers */
		   byte[] buffer =object.getName().getBytes();
		   out.writeInt(buffer.length);
		   out.write(buffer);
		   buffer =componentID.getBytes();
		   out.writeInt(buffer.length);
		   out.write(buffer);
		   buffer = object.getParent().getName().getBytes();
		   out.writeInt(buffer.length);
		   out.write(buffer);
		   out.close();
		   bytes = byteOut.toByteArray();
	   } catch (IOException e) {
		   //when in doubt send nothing
		   System.out.println(e.toString());
	   }
	   return bytes;
   }   
	
}
