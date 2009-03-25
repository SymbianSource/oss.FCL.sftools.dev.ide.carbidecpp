#ifndef UIQ_MULTIPAGE_3_0MULTIPAGEVIEW_H
#define UIQ_MULTIPAGE_3_0MULTIPAGEVIEW_H

// [[[ begin generated region: do not modify [Generated System Includes]
#include <QikMultiPageViewBase.h>
#include <MQikListBoxModel.h>
#include <MQikListBoxData.h>
// ]]] end generated region [Generated System Includes]


// [[[ begin [Event Handler Includes]
#include <MQikListBoxObserver.h>
#include <eikedwin.h>
#include <eikedwob.h>
#include <eikenv.h>
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated User Includes]
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Typedefs]
typedef CQikMultiPageViewBase CUIQ_MultiPage_3_0MultiPageViewBase;
// ]]] end generated region [Generated Typedefs]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

// [[[ begin generated region: do not modify [Generated Forward Declarations]

class CEikBitmapButton;
				

class CEikChoiceList;
				

class CQikColorSelector;
				

class CEikComboBox;
				

class CEikCommandButton;
				

class CEikImage;
				

class CEikLabel;
				

class CEikLabeledCheckBox;
				

class CQikListBox;
				

class CEikProgressInfo;
				

class CEikTextButton;
				

class CQikVertOptionButtonList;
				

class CQikDateEditor;
				

class CQikFloatingPointEditor;
				

class CQikNumberEditor;
				

class CEikEdwin;
				

class CEikRichTextEditor;
				

class CQikTimeEditor;
				
// ]]] end generated region [Generated Forward Declarations]

/**
 * Class for UIQ_MultiPage_3_0MultiPageView.
 * @class	CUIQ_MultiPage_3_0MultiPageView UIQ_MultiPage_3_0MultiPageView.h
 */
			
class CUIQ_MultiPage_3_0MultiPageView : public CUIQ_MultiPage_3_0MultiPageViewBase
	// [[[ begin generated region: do not modify [Generated Content]
	// ]]] end generated region [Generated Content]
	
	, MQikListBoxObserver	, MEikEdwinObserver	{
	
	
	// [[[ begin [Public Section]
public:
	
	// [[[ begin generated region: do not modify [Generated Types]
	// ]]] end generated region [Generated Types]
	
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	~CUIQ_MultiPage_3_0MultiPageView();
	static CUIQ_MultiPage_3_0MultiPageView* NewLC( 
			CQikAppUi& aAppUi,
			const TVwsViewId aParentViewId );
	TVwsViewId ViewId() const;
	void HandleCommandL( CQikCommand& aCommand );
	void HandleControlEventL( 
			CCoeControl* aControl,
			TCoeEvent aEventType );
	void HandleListBoxEventL( 
			CQikListBox* aListBox,
			TQikListBoxEvent aEventType,
			TInt aItemIndex,
			TInt aSlotId );
	void HandleEdwinEventL( 
			CEikEdwin* aEdwin,
			TEdwinEvent aEventType );
	// ]]] end [Public Section]
	
	
	// [[[ begin [Protected Section]
protected:
	
	// [[[ begin generated region: do not modify [Overridden Methods]
	// ]]] end generated region [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
	// ]]] end [User Handlers]
	
	void ViewConstructL();
	// ]]] end [Protected Section]
	
	
	// [[[ begin [Private Section]
private:
	
	// [[[ begin generated region: do not modify [Generated Types]
	
	typedef void ( CUIQ_MultiPage_3_0MultiPageView::*ListBoxEventHandler ) ( 
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
	
	// ]]] end generated region [Generated Types]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	CEikBitmapButton* iBitmapButton1;
	CEikChoiceList* iChoiceList1;
	CQikColorSelector* iColorSelector1;
	CEikComboBox* iComboBox1;
	CEikCommandButton* iCommandButton1;
	CEikImage* iImage1;
	CEikLabel* iLabel1;
	CEikLabeledCheckBox* iLabeledCheckBox1;
	CQikListBox* iListBox1;
	CEikProgressInfo* iProgressInfo1;
	CEikTextButton* iTextButton1;
	CQikVertOptionButtonList* iVertOptionButtonList1;
	CQikDateEditor* iDateEditor1;
	CQikFloatingPointEditor* iFloatingPointEditor1;
	CQikNumberEditor* iNumberEditor1;
	CEikEdwin* iPlainTextEditor1;
	CEikRichTextEditor* iRichTextEditor1;
	CQikTimeEditor* iTimeEditor1;
	CQikColorSelector* iColorSelector2;
	CEikLabel* iLabel2;
	CEikImage* iImage2;
	CQikNumberEditor* iNumberEditor2;
	CQikFloatingPointEditor* iFloatingPointEditor2;
	// ]]] end generated region [Generated Instance Variables]
	
	// [[[ begin generated region: do not modify [Generated Owned Methods]
	
		typedef void ( CUIQ_MultiPage_3_0MultiPageView::*ControlEventHandler )( 
					CCoeControl* aControl, TCoeEvent aEvent );
		class TControlEventDispatch 
			{
		public: 
			CCoeControl* src; 
			TCoeEvent event; 
			ControlEventHandler handler;
			};
			
		RArray< TControlEventDispatch > iControlEventDispatch;
	
		typedef void ( CUIQ_MultiPage_3_0MultiPageView::*CommandEventHandler )( 
						CQikCommand& aCommand  );
		class TCommandEventDispatch 
			{
		public: 
			TInt id; 
			CommandEventHandler handler;
			};
				
		RArray< TCommandEventDispatch > iCommandEventDispatch;
							
	
	void AddListBoxEventHandlerL( 
			CQikListBox* aListBox,
			TQikListBoxEvent aEventType,
			ListBoxEventHandler aHandler );
	void AddListBoxItemL( 
			TInt aListBoxUniqueHandle,
			MQikListBoxModel::MQikListBoxModelDataType aDataType,
			TListBoxDataItem aListBoxDataItem );
	void RemoveListBoxItemsL( 
			TInt aListBoxUniqueHandle,
			RArray<TInt>& aItemIndexes );
	// ]]] end generated region [Generated Owned Methods]
	
	
	// [[[ begin [Generated Not Owned Methods]
		typedef void ( CUIQ_MultiPage_3_0MultiPageView::*EdwinEventHandler )( 
					CEikEdwin* aEdwin, TEdwinEvent aEvent );
		class TEdwinEventDispatch 
			{
		public: 
			CEikEdwin* src; 
			TEdwinEvent event; 
			EdwinEventHandler handler;
			};
			
		RArray< TEdwinEventDispatch > iEdwinEventDispatch;	
	// ]]] end [Generated Not Owned Methods]
	
	CUIQ_MultiPage_3_0MultiPageView( 
			CQikAppUi& aAppUi,
			const TVwsViewId aParentViewId );
	void ConstructL();
	void InitializeControlsL();
	void AddControlEventHandlerL( 
			CCoeControl* aControl,
			TCoeEvent aEvent,
			ControlEventHandler aHandler );
	void AddCommandEventHandlerL( 
			TInt aId,
			CommandEventHandler aHandler );
	void AddEdwinEventHandlerL( 
			CEikEdwin* aEdwin,
			TEdwinEvent aEvent,
			EdwinEventHandler aHandler );
	// ]]] end [Private Section]
	
	
	
	
	};

#endif // UIQ_MULTIPAGE_3_0MULTIPAGEVIEW_H
