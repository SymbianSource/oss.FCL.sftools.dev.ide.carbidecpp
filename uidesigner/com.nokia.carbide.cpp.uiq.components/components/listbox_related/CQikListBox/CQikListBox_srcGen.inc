<!-- START_USECASES: CU13 END_USECASES -->

<sourceGen>
	<template id="ListBoxPrivateOwnedTypes" location="VIEWDIALOGBASE_CLASS_PRIVATE_OWNED_TYPES">
<![CDATA[
typedef void ( ${className}::*ListBoxEventHandler ) ( 
		CQikListBox* aListBox, 
		TQikListBoxEvent aEventType, 
		TInt aItemIndex, 
		TInt aSlotId );
class TListBoxEventDispatch
	{
public:
	CQikListBox* iListBox;
	TQikListBoxEvent iEventType;
	ListBoxEventHandler iHandler;
	};
RArray < TListBoxEventDispatch > iListBoxEventDispatch;
class MListBoxItemInfo
	{
public:
	TInt iSlotId;
	virtual void AddDataL( MQikListBoxData* aListBoxData ) = 0;
	};
class TListBoxItemInfoText : public MListBoxItemInfo
	{
public:
	TDesC* iText;
	void AddDataL( MQikListBoxData* aListBoxData )
		{
		aListBoxData->AddTextL( *iText, iSlotId );
		};
	};
class TListBoxItemInfoIcon : public MListBoxItemInfo
	{
public:
	CQikContent* iIcon;
	void AddDataL( MQikListBoxData* aListBoxData )
		{
		aListBoxData->AddIconL( iIcon, iSlotId );
		};
	};
class TListBoxItemInfoThumbnailImage : public MListBoxItemInfo
	{
public:
	TDesC* iThumbnailImageFileName;
	void AddDataL( MQikListBoxData* aListBoxData )
		{
		aListBoxData->AddThumbnailImageL( *iThumbnailImageFileName, iSlotId );
		};
	};
class TListBoxDataItem
	{
public:
	TInt iListBoxLayoutId;
	RPointerArray < MListBoxItemInfo > iListBoxItemInfo;
	void AddListBoxItemInfo( MQikListBoxData* aListBoxData )
		{
		aListBoxData->SetLayoutId( iListBoxLayoutId );
		for (TInt i = 0; i < iListBoxItemInfo.Count(); i++)
			{
			MListBoxItemInfo*& itemInfo = iListBoxItemInfo[i];
			itemInfo->AddDataL( aListBoxData );
			}
		};
	};
]]>
	</template>
	
	<!--TODO: Moved to previous template because it is duplicating this variable member.
		Once bug 5658 is fixed, we can return to this-->
	<!--<template form="InstanceGen" phase="ClassPrivateInstanceVariables">
<![CDATA[RArray < TListBoxEventDispatch > iListBoxEventDispatch;]]>
	</template>-->
	
	<template form="InstanceGen" phase="Destroy">
<![CDATA[iListBoxEventDispatch.Reset();]]>
	</template>
	
	<template form="InstanceGen" phase="Initialize">
<![CDATA[iListBoxEventDispatch.Reset();]]>
	</template>
	
	<expandMacro name="SetupControlInstanceVariable"
		Type="${this.getCppClassName(instance)}"
		InstanceMemberName="${instanceMemberName}"
		InstanceTitle="${instanceName$title}"
		Headers="QikListBox.h"/>
	
	<expandMacro name="AddCppIncludes"
		Headers="MQikListBoxObserver.h"
		Phase="HeaderEventHandlerIncludes"/>
	
	<expandMacro name="ClassTemplate"
		BaseClassName="${className}Base"
		ExtraBaseClasses="MQikListBoxObserver"/>
	
	<template id="ListBoxObserver" phase="InitializeControlHandlers">
<![CDATA[
if ( ${instanceMemberName} != NULL )
	{
	${instanceMemberName}->SetListBoxObserver( this );
	}
]]>
	</template>
	
	<expandMacro name="GenerateMethodWithOwnedBody"
		FunctionLocationId="VIEWDIALOGBASE_CLASS_HANDLELISTBOXEVENT"
		IsOwned="false"
		DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
		FunctionName="HandleListBoxEventL"
		FunctionArgs="CQikListBox* aListBox, TQikListBoxEvent aEventType, TInt aItemIndex, TInt aSlotId"
		ClassName="${className}"
		
		DeclLocation="VIEWDIALOGBASE_CLASS_PUBLIC">
		<expandArgument name="FunctionComment">
/**
 * Handles list box events.
 * 
 * This function is invoked by CQikListBox to
 * notify the observer of list box events.
 * 
 * @param	aListBox	The originating list box.
 * @param	aEventType	A code for the event. Further information may be obtained by accessing the list box itself.
 * @param	aItemIndex	The item index, if applicable, else EQikListBoxParamNotApplicable.
 * @param	aSlotId		The item slot id the event was generated in, if applicable, else EQikListBoxParamNotApplicable.
 */
		</expandArgument>
		<expandArgument name="FunctionBody">
<![CDATA[
for ( int i = 0; i < iListBoxEventDispatch.Count(); i++)
	{
	const TListBoxEventDispatch& currEntry = iListBoxEventDispatch[i];
	if ( currEntry.iListBox == aListBox && currEntry.iEventType == aEventType )
		{
		( this->*currEntry.iHandler )( aListBox, aEventType, aItemIndex, aSlotId );
		break;
		}
	}]]>
		</expandArgument>
	</expandMacro>
	
	<expandMacro name="GenerateMethodWithOwnedBody"
		FunctionLocationId="VIEWDIALOGBASE_CLASS_ADDLISTBOXEVENTHANDLER"
		DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
		FunctionName="AddListBoxEventHandlerL"
		FunctionArgs="CQikListBox* aListBox, TQikListBoxEvent aEventType, ListBoxEventHandler aHandler"
		ClassName="${className}"
		
		DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE_OWNED_METHODS">
		<expandArgument name="FunctionComment">
/**
 * Helper function to register MQikListBoxObserver event handlers
 */
		</expandArgument>
		<expandArgument name="FunctionBody">
<![CDATA[
TListBoxEventDispatch entry;
entry.iListBox = aListBox;
entry.iEventType = aEventType;
entry.iHandler = aHandler;
iListBoxEventDispatch.AppendL( entry );]]>
		</expandArgument>
	</expandMacro>
	
	<templateGroup>
		
		<expandMacro name="AddCppIncludes"
			Headers="MQikListBoxModel.h MQikListBoxData.h"
			Location="VIEWDIALOGBASE_HEADER_OWNED_SYSTEM_INCLUDES"/>
			
		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE_OWNED_METHODS"
			FunctionName="AddListBoxItemL"
			FunctionArgs="TInt aListBoxUniqueHandle, MQikListBoxModel::MQikListBoxModelDataType aDataType, TListBoxDataItem aListBoxDataItem"
			FunctionLocationId="VIEWDIALOGBASE_CLASS_ADDLISTBOXITEM"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}">
			<expandArgument name="FunctionComment">
/**
 * This function add an item to the list box referenced by aListBoxUniqueHandle.
 * 
 * @param	aListBoxUniqueHandle	The unique handle of the listbox the item will be added to.
 * @param	aDataType				The type of data created, can't be changed later on.
 * @param	aListBoxDataItem		Defines the listbox item to add.
 */
			</expandArgument>
			<expandArgument name="FunctionBody">
<![CDATA[
CQikListBox* listbox = LocateControlByUniqueHandle < CQikListBox > ( aListBoxUniqueHandle );
if ( listbox != NULL )
	{
	MQikListBoxModel& model( listbox->Model() );
	model.ModelBeginUpdateLC();
	MQikListBoxData* data = model.NewDataLC( aDataType );
	aListBoxDataItem.AddListBoxItemInfo( data );
	CleanupStack::PopAndDestroy( data );
	model.ModelEndUpdateL();
	}]]>
			</expandArgument>
		</expandMacro>
		
		<expandMacro name="GenerateMethodWithOwnedBody"
			DeclLocation="VIEWDIALOGBASE_CLASS_PRIVATE_OWNED_METHODS"
			FunctionName="RemoveListBoxItemsL"
			FunctionLocationId="VIEWDIALOGBASE_CLASS_REMOVELISTBOXITEMS"
			DefnLocation="VIEWDIALOGBASE_SOURCE_FILE"
			ClassName="${className}">
			<expandArgument name="FunctionArgs">
<![CDATA[TInt aListBoxUniqueHandle, RArray<TInt>& aItemIndexes]]>
			</expandArgument>
			<expandArgument name="FunctionComment">
/**
 * This functions deletes a set of items from a list box.
 * 
 * @param	aListBoxUniqueHandle	The unique handle of the listbox items will be removed from.
 * @param	aItemIndexes			Indexes of the items to be deleted.
 */
			</expandArgument>
			<expandArgument name="FunctionBody">
<![CDATA[
CQikListBox* listbox = LocateControlByUniqueHandle < CQikListBox > ( aListBoxUniqueHandle );
if ( listbox != NULL )
	{
	MQikListBoxModel& model( listbox->Model() );
	model.ModelBeginUpdateLC();
	//Remember to remove the data from the end and forward, to ensure that the indexes are correct
	aItemIndexes.Sort();
	for (TInt i = aItemIndexes.Count() - 1; i >= 0; i--)
		{
		model.RemoveDataL(aItemIndexes[i]);
		}
	model.ModelEndUpdateL();
	}]]>
			</expandArgument>
		</expandMacro>
		
	</templateGroup>
	
</sourceGen>
