/*
==============================================================================
 Name		: YahooSearchModel.h

 Description: 

 Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
 All rights reserved.
 This component and the accompanying materials are made available
 under the terms of the License "Eclipse Public License v1.0"
 which accompanies this distribution, and is available
 at the URL "http://www.eclipse.org/legal/epl-v10.html".

 Contributors:
 Nokia Corporation - initial contribution.
==============================================================================
*/

#ifndef CYahooSearchModel_H_
#define CYahooSearchModel_H_

#include <e32std.h>
#include <e32base.h>
#include <e32cmn.h>
#include <e32des8.h>
#include <xml/parser.h>
#include <xml/contenthandler.h>
#include <stringpool.h>

const int MAX_TITLE_LENGTH = 255;
const int MAX_SUMMARY_LENGTH = 255;
const int MAX_URL_LENGTH = 1024;
const int MAX_DATETIME_LENGTH = 100;
const int MAX_TAG_LENGTH = 32;
const int MAX_FORMAT_LENGTH = 10;
const int MAX_NUMBER_LENGTH = 10;

class TYahooImageInfo
{
public:
	TBuf<MAX_TITLE_LENGTH> 	  title;
	TBuf<MAX_SUMMARY_LENGTH>  summary;
	TBuf<MAX_URL_LENGTH>	  url;
	TBuf<MAX_FORMAT_LENGTH>	  fileFormat;
	int						  fileSize;
	int						  width;
	int						  height;
	TBuf<MAX_URL_LENGTH>	  thumbnailUrl;
	int						  thumbnailWidth;
	int						  thumbnailHeight;
	bool					  invalid;
};

class CYahooSearchModel : public CBase, public Xml::MContentHandler
{
public:

		CYahooSearchModel();
		
		void ConstructL();
		
		~CYahooSearchModel();
		
		void AppendL(const TDesC8& text);
		
		void ParseL();
		
		int	 GetImageCount() {return iImages->Count();}
		
		void GetImageInfo(int index, TYahooImageInfo& image) {image = (*iImages)[index];}
	
		// MContentHandler methods	
		
		void OnStartDocumentL(const Xml::RDocumentParameters& aDocParam, TInt aErrorCode);
		void OnEndDocumentL(TInt aErrorCode);
		void OnStartElementL(const Xml::RTagInfo& aElement, const Xml::RAttributeArray& aAttributes, 
										 TInt aErrorCode);
		void OnEndElementL(const Xml::RTagInfo& aElement, TInt aErrorCode);
		void OnContentL(const TDesC8& aBytes, TInt aErrorCode);
		void OnStartPrefixMappingL(const RString& aPrefix, const RString& aUri, 
												TInt aErrorCode);
		void OnEndPrefixMappingL(const RString& aPrefix, TInt aErrorCode);
		void OnIgnorableWhiteSpaceL(const TDesC8& aBytes, TInt aErrorCode);
		void OnSkippedEntityL(const RString& aName, TInt aErrorCode);
		void OnProcessingInstructionL(const TDesC8& aTarget, const TDesC8& aData, 
												TInt aErrorCode);
		void OnError(TInt aErrorCode);
		TAny* GetExtendedInterface(const TInt32 aUid);

private:

	RBuf8  iFeedText;
	CArrayFixFlat<TYahooImageInfo> *iImages;
	bool iInEntry;
	TYahooImageInfo iPendingImage;
	
	Xml::CParser *iParser;
	
	typedef TBuf8<MAX_TAG_LENGTH> TTagBuf;
	CArrayFixFlat<TTagBuf> *iTagStack;
	TBuf<MAX_NUMBER_LENGTH> iNumberText;
	TDes *iCurrContent;
	
	void PushTagL(TTagBuf tag);
	TTagBuf PeekTag();
	TTagBuf PopTag();
	void AppendDesCL(const TDesC8& src, TDes& dest);
};

#endif /*CYahooSearchModel_H_*/

