/*
==============================================================================
 Name		: YahooSearchModel.cpp

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

#include "YahooSearchModel.h"
#include <e32debug.h>

// XML tags
_LIT8(kResultSetTag, "ResultSet");
_LIT8(kResultTag, "Result");
_LIT8(kTitleTag, "Title");
_LIT8(kSummaryTag, "Summary");
_LIT8(kUrlTag, "Url");
_LIT8(kFileSizeTag, "FileSize");
_LIT8(kFileFormatTag, "FileFormat");
_LIT8(kHeightTag, "Height");
_LIT8(kWidthTag, "Width");
_LIT8(kThumbnailTag, "Thumbnail");

CYahooSearchModel::CYahooSearchModel()
{
	iParser = NULL;
	iInEntry = false;
	iCurrContent = NULL;
	iImages = NULL;
	iTagStack = NULL;
}

CYahooSearchModel::~CYahooSearchModel()
{
	iFeedText.Close();	
	delete iImages;
	delete iTagStack;
	delete iParser;
}

void CYahooSearchModel::ConstructL() 
{
	iImages = new(ELeave) CArrayFixFlat<TYahooImageInfo>(4);	
	iTagStack = new(ELeave) CArrayFixFlat<TTagBuf>(4);
}
		
void CYahooSearchModel::AppendL(const TDesC8& text)
{
	int newLength = iFeedText.Length() + text.Length();
	if (iFeedText.MaxLength() < newLength)
	{
		iFeedText.ReAllocL(newLength);
	}
	iFeedText.Append(text);
}
		
void CYahooSearchModel::ParseL()
{
	iImages->Reset();
	int len = iFeedText.Length();
	
	_LIT8(mimeType, "text/xml");
	iParser = Xml::CParser::NewL(mimeType, *this);

	iTagStack->Reset();
	
	Xml::ParseL(*iParser, iFeedText);
	iFeedText.SetLength(0);
	iFeedText.ReAlloc(0);
	delete iParser;
	iParser = NULL;
}
		
void CYahooSearchModel::OnStartDocumentL(const Xml::RDocumentParameters& aDocParam, TInt aErrorCode)
{
}

void CYahooSearchModel::OnEndDocumentL(TInt aErrorCode)
{
}

void CYahooSearchModel::OnStartElementL(const Xml::RTagInfo& aElement, const Xml::RAttributeArray& aAttributes, 
								 TInt aErrorCode)
{
	const TDesC8& tag = aElement.LocalName().DesC();
	iCurrContent = NULL;
	if (tag == kResultTag)
	{
		iInEntry = true;
		iPendingImage.title.SetLength(0);
		iPendingImage.summary.SetLength(0);
		iPendingImage.url.SetLength(0);
		iPendingImage.fileSize = 0;
		iPendingImage.width = 0;
		iPendingImage.height = 0;
		iPendingImage.thumbnailUrl.SetLength(0);
		iPendingImage.thumbnailWidth = 0;
		iPendingImage.thumbnailHeight = 0;
		iPendingImage.invalid = false;
	} else if (tag == kTitleTag) 
	{
		iCurrContent = &iPendingImage.title;
	} else if (tag == kSummaryTag) 
	{
		iCurrContent = &iPendingImage.summary;
	} else if (tag == kUrlTag) 
	{
		TTagBuf currTag = PeekTag();
		if (currTag == kThumbnailTag)
		{
			iCurrContent = &iPendingImage.thumbnailUrl;
		} else
		{
			iCurrContent = &iPendingImage.url;
		}
	} else if (tag == kFileFormatTag) 
	{
		iCurrContent = &iPendingImage.fileFormat;
	} else if (tag == kHeightTag || tag == kWidthTag || tag == kFileSizeTag) {
		iCurrContent = &iNumberText;	
	}
	PushTagL(tag);
}

void CYahooSearchModel::OnEndElementL(const Xml::RTagInfo& aElement, TInt aErrorCode)
{
	TTagBuf poppedTag = PopTag();
	TTagBuf currTag = PeekTag();
	if (poppedTag == kResultTag)
	{
		iImages->AppendL(iPendingImage);
		iInEntry = false;
	} else if (poppedTag == kFileSizeTag || poppedTag == kWidthTag || poppedTag == kHeightTag) 
	{
		TLex lex(iNumberText);
		TInt val;
		if (lex.Val(val) == KErrNone)
		{
			if (poppedTag == kFileSizeTag) {
				iPendingImage.fileSize = val;
			} else 
			{ 
				if (currTag == kThumbnailTag)
				{
					if (poppedTag == kWidthTag)
						iPendingImage.thumbnailWidth = val;
					else if (poppedTag == kHeightTag)
						iPendingImage.thumbnailHeight = val;
					else
						iPendingImage.invalid = true;
					
				} else if (currTag == kResultTag) 
				{
					if (poppedTag == kWidthTag)
						iPendingImage.width = val;
					else if (poppedTag == kHeightTag)
						iPendingImage.height = val;
					else
						iPendingImage.invalid = true;					
				} else
				{
					iPendingImage.invalid = true;
				}
			}
		}
	}
	iCurrContent = NULL;
}

void CYahooSearchModel::OnContentL(const TDesC8& aBytes, TInt aErrorCode)
{
	if (iCurrContent)
	{
		AppendDesCL(aBytes, *iCurrContent);
	}
}

void CYahooSearchModel::OnStartPrefixMappingL(const RString& aPrefix, const RString& aUri, 
												TInt aErrorCode)
{
	
}

void CYahooSearchModel::OnEndPrefixMappingL(const RString& aPrefix, TInt aErrorCode)
{
	
}

void CYahooSearchModel::OnIgnorableWhiteSpaceL(const TDesC8& aBytes, TInt aErrorCode)
{
	
}

void CYahooSearchModel::OnSkippedEntityL(const RString& aName, TInt aErrorCode)
{
	
}

void CYahooSearchModel::OnProcessingInstructionL(const TDesC8& aTarget, const TDesC8& aData, 
													TInt aErrorCode)
{
	
}

void CYahooSearchModel::OnError(TInt aErrorCode)
{
	
}

TAny* CYahooSearchModel::GetExtendedInterface(const TInt32 aUid)
{
	return NULL;
}

void CYahooSearchModel::PushTagL(TTagBuf tag)
{
	iTagStack->AppendL(tag);
}

CYahooSearchModel::TTagBuf CYahooSearchModel::PeekTag()
{
	TTagBuf result;
	if (iTagStack->Count() > 0)
		result = (*iTagStack)[iTagStack->Count()-1];
	return result;
}

CYahooSearchModel::TTagBuf CYahooSearchModel::PopTag()
{
	TTagBuf result;
	if (iTagStack->Count() > 0) 
	{
		result = (*iTagStack)[iTagStack->Count()-1];
		// since we're making the array smaller it ResizeL will not leave
		TRAPD(err, iTagStack->ResizeL(iTagStack->Count()-1));
	}
	return result;
}

void CYahooSearchModel::AppendDesCL(const TDesC8& src, TDes& dest)
{
	int length = src.Length();
	RBuf buf;
	buf.CreateL(length);
	buf.Copy(src);

	if (length + dest.Length() > dest.MaxLength())
	{
		length = dest.MaxLength() - dest.Length();
		buf.SetLength(length);
	}
	dest.Append(buf);
	buf.Close();
}
