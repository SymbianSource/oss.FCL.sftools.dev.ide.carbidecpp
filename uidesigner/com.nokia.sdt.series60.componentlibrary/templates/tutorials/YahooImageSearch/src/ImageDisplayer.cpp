/*
==============================================================================
 Name		: ImageDisplayer.cpp

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

#include "ImageDisplayer.h"
#include "YahooImageSearchAppUi.h"
#include "$(baseName).hrh"
#include "SearchResults.h"
#include "ImageContainerView.h"
#include <COEMAIN.h>

CImageDisplayer::CImageDisplayer()
 : CActive(EPriorityStandard), iDecoder(NULL), iBitmap(NULL)
{
}

CImageDisplayer::~CImageDisplayer()
{
	Cancel();
	delete iDecoder;
	delete iBitmap;
	iImageTitle.Close();
}

void CImageDisplayer::ConstructL()
{
	CActiveScheduler::Add(this);
}
	
void CImageDisplayer::DisplayImageL(const TDesC16& imageTitle, const TDesC8& imageData)
{
	Clear();
	iImageTitle.SetLength(0);
	iImageTitle.ReAllocL(imageTitle.Length());
	iImageTitle.Copy(imageTitle);
	iImageData.ReAllocL(imageData.Length());
	iImageData.Copy(imageData);
	RFs& rFs = CCoeEnv::Static()->FsSession();

	_LIT8(kJPEG, "image/jpeg");
	TBuf8<32> mimeType;
	mimeType.Copy(kJPEG);
	
	iDecoder = CImageDecoder::DataNewL(rFs, iImageData, mimeType);
	
	const TFrameInfo& frameInfo = iDecoder->FrameInfo();

	iBitmap = new (ELeave) CFbsBitmap;
	TDisplayMode displayMode = CEikonEnv::Static()->DefaultDisplayMode(); // frameInfo.iFrameDisplayMode
	User::LeaveIfError(iBitmap->Create(frameInfo.iOverallSizeInPixels, displayMode));
	
	iDecoder->Convert(&iStatus, *iBitmap);
	SetActive();	
}

void CImageDisplayer::RunL()
{
	Clear();
	CYahooImageSearchAppUi *appUi = static_cast<CYahooImageSearchAppUi*>(CCoeEnv::Static()->AppUi());
	CImageContainerView *imageView = appUi->ImageContainerView();
	imageView->SetImageL(iImageTitle, iBitmap);
	iBitmap = NULL;
	appUi->ActivateLocalViewL(TUid::Uid(EImageContainerViewId));
}

void CImageDisplayer::Clear()
{
	if (iDecoder)
		iDecoder->Cancel();
	delete iDecoder;
	iDecoder = NULL;
	iImageData.SetLength(0);
	iImageData.ReAlloc(0);
}

void CImageDisplayer::DoCancel()
{
	Clear();
	delete iBitmap;
	iBitmap = NULL;
}
