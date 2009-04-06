// ProtocolRegisterItem.h: interface for the ProtocolRegisterItem class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_PROTOCOLREGISTERITEM_H__50FA06CD_9D2E_4A84_BEA7_1E4FD5374D8C__INCLUDED_)
#define AFX_PROTOCOLREGISTERITEM_H__50FA06CD_9D2E_4A84_BEA7_1E4FD5374D8C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "ServerClient.h"
#include "BaseProtocol.h"

class ProtocolRegisterItem  
{
public:
	ProtocolRegisterItem();
	virtual ~ProtocolRegisterItem();

	char m_ProtocolType[MAX_DECODE_FORMAT];
	CBaseProtocol* m_Protocol;
};

#endif // !defined(AFX_PROTOCOLREGISTERITEM_H__50FA06CD_9D2E_4A84_BEA7_1E4FD5374D8C__INCLUDED_)
