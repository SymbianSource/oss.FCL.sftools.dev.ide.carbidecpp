/*
 *  PSerialCommPref.h 
 *
 *  Copyright 2002 metrowerks inc.  All rights reserved.
 *
 */

#ifndef __PSERIALCOMMPREF_H__
#define __PSERIALCOMMPREF_H__

//#include "DEPrefTypes.h"

enum menu_port
{
	PortCOM1=0,
	PortCOM2=1,
	PortCOM3=2,
	PortCOM4=3,
	PortCOM5=4,
	PortCOM6=5,
	PortCOM7=6,
	PortCOM8=7,
	PortCOM9=8
};

enum menu_rate
{
	Baud300=0,
	Baud1200=1,
	Baud2400=2,
	Baud4800=3,
	Baud9600=4,
	Baud19200=5,
	Baud38400=6,
	Baud57600=7,
	Baud115200=8,
	Baud230400=9
};

enum menu_databits
{
	DataBits4=0,
	DataBits5=1,
	DataBits6=2,
	DataBits7=3,
	DataBits8=4
};

enum menu_stopbits
{
	StopBits1=0,
	StopBits15=1,
	StopBits2=2
};

enum menu_parity
{
	ParNone=0,
	ParOdd=1,
	ParEven=2
};

enum menu_flowcontrol
{
	FlowNone=0,
	FlowHardware=1,
	FlowSoftware=2
};

#if (0)
#define PSerialCommPrefPanelDisplayName	"Serial"

// --- DEPREF --- 
// Comment line above is required by deprefs.pl tool, which this header is run 
// through as part of the DE builds (standalone debugger engine). There are 
// various restrictions and requirements that tool imposes on this header, so 
// if you change anything past here, please run this header through deprefs.pl
// and ensure no errors result.

#define PSerialCommPrefPanelName	"Serial Communications"

#define PSERIALCOMMPREFVERSION		2

typedef struct PSerialCommPref {
	short version;
	
	// serial 1
	signed char	port;
	signed char	rate;
	signed char	databits;
	signed char	stopbits;
	signed char	parity;
	signed char	flowcontrol;
	BOOLCHAR	logdata;
//#if (HOSTOS == SOLARIS) || (HOSTOS == LINUX)
//	unsigned char	pad1[3];
// 	char			dynamicPort[256];
//#endif

} PSerialCommPref, **PSerialCommPrefHandle;
#endif

#endif	// __PSERIALCOMMPREF_H__
