/*
==============================================================================
 Name		: ImageDisplayer.h

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

#ifndef IMAGEDISPLAYER_H_
#define IMAGEDISPLAYER_H_

#include <e32base.h>
#include <ImageConversion.h>

class CImageDisplayer : public CActive
{
public:
	CImageDisplayer();
	~CImageDisplayer();
	
	void ConstructL();
	
	void DisplayImageL(const TDesC16& imageTitle, const TDesC8& imageData);
	
protected:	
	void RunL();
	void DoCancel();
	
private:
	void Clear();

	RBuf16		iImageTitle;
	RBuf8		iImageData;
	CImageDecoder *iDecoder;
	CFbsBitmap *iBitmap;
};

#endif /*IMAGEDISPLAYER_H_*/
