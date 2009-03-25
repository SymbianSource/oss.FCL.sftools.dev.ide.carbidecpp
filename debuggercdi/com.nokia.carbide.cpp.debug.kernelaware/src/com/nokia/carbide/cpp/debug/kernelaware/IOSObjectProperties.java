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
package com.nokia.carbide.cpp.debug.kernelaware;

import cwdbg.EclipseDEConstants;

public interface IOSObjectProperties {
	public interface ID {
		// These need not and should not be localized.
		
		public static final String ObjectType = EclipseDEConstants.J_OSObjectProperty_ObjectType; 
		public static final String Name = EclipseDEConstants.J_OSObjectProperty_Name;
		
		// Process properties
		public static final String Attribute = EclipseDEConstants.J_OSObjectProperty_Attribute;
		public static final String BssSize = EclipseDEConstants.J_OSObjectProperty_BssSize;
		public static final String CodeLoadAddress = EclipseDEConstants.J_OSObjectProperty_CodeLoadAddress;
		public static final String CodeRunAddress = EclipseDEConstants.J_OSObjectProperty_CodeRunAddress;
		public static final String DataBssRunAddress = EclipseDEConstants.J_OSObjectProperty_DataBssRunAddress;
		public static final String DataBssStackChunk = EclipseDEConstants.J_OSObjectProperty_DataBssStackChunk;
		public static final String DataRunAddress = EclipseDEConstants.J_OSObjectProperty_DataRunAddress;
		public static final String DataLoadAddress = EclipseDEConstants.J_OSObjectProperty_DataLoadAddress;
		public static final String DataSize = EclipseDEConstants.J_OSObjectProperty_DataSize;
		public static final String DependencyCount = EclipseDEConstants.J_OSObjectProperty_DependencyCount;
		public static final String DependencyFiles = EclipseDEConstants.J_OSObjectProperty_DependencyFiles;
		public static final String ID = EclipseDEConstants.J_OSObjectProperty_ID;
		public static final String Priority = EclipseDEConstants.J_OSObjectProperty_Priority;
		public static final String TextSize = EclipseDEConstants.J_OSObjectProperty_TextSize;

		// Thread ones
		public static final String ContextType = EclipseDEConstants.J_OSObjectProperty_ContextType;
		// ID
		public static final String OwningProcessID = EclipseDEConstants.J_OSObjectProperty_OwningProcessID;
		public static final String OwningProcessName = EclipseDEConstants.J_OSObjectProperty_OwningProcessName;
		// Priority
		public static final String SavedStackAddr = EclipseDEConstants.J_OSObjectProperty_SavedStackAddr;
		public static final String State = EclipseDEConstants.J_OSObjectProperty_State;
		public static final String SupervisorStackAddr = EclipseDEConstants.J_OSObjectProperty_SupervisorStackAddr;
		public static final String SupervisorStackSize = EclipseDEConstants.J_OSObjectProperty_SupervisorStackSize;
		public static final String ThreadBase = EclipseDEConstants.J_OSObjectProperty_ThreadBase;
		public static final String Type = EclipseDEConstants.J_OSObjectProperty_Type;
		public static final String UserStackAddr = EclipseDEConstants.J_OSObjectProperty_UserStackAddr;
		public static final String UserStackSize = EclipseDEConstants.J_OSObjectProperty_UserStackSize;

		// more for Chunk
		public static final String HomeAddress = EclipseDEConstants.J_OSObjectProperty_HomeAddress;
		public static final String Size = EclipseDEConstants.J_OSObjectProperty_Size;

		// more for library
		public static final String MapCount = EclipseDEConstants.J_OSObjectProperty_MapCount;
	};

	public interface DisplayName {
		// These can be localized.
		
		public static final String ObjectType = "Object Type"; 
		public static final String Name = "Name";
		
		// process properties
		public static final String Attribute = "Attribute";
		public static final String BssSize = "Bss Size";
		public static final String CodeLoadAddress = "Code Load Address";
		public static final String CodeRunAddress = "Code Run Address";
		public static final String DataBssRunAddress = "Data Bss Run Address";
		public static final String DataBssStackChunk = "Data Bss Stack Chunk";
		public static final String DataLoadAddress = "Data Load Address";
		public static final String DataRunAddress = "Data Run Address";
		public static final String DataSize = "Data Size";
		public static final String DependencyCount = "Dependency Count";
		public static final String DependencyFiles = "Dependency Files";
		public static final String ID = "ID";
		public static final String Priority = "Priority";
		public static final String TextSize = "Text Size";

		// Thread ones
		public static final String ContextType = "Context ObjectType";
		// ID
		public static final String OwningProcessID = "Owning Process ID";
		public static final String OwningProcessName = "Owning Process Name";
		// Priority
		public static final String SavedStackAddr = "Saved stack address";
		public static final String State = "State";
		public static final String SupervisorStackAddr = "Supervisor stack address";
		public static final String SupervisorStackSize = "Supervisor stack size";
		public static final String ThreadBase = "Thread Base";
		public static final String Type = "Type";
		public static final String UserStackAddr = "User stack address";
		public static final String UserStackSize = "User stack size";

		// more for Chunk
		public static final String HomeAddress = "Home Address";
		public static final String Size = "Size";

		// more for library
		public static final String MapCount = "Map Count";
	};
}
