/*
* Copyright (c) 2002-2005 Nokia Corporation and/or its subsidiary(-ies).
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


/**
@file

Contains a number of macros that may be included (or not) which affect how the components are built.

*/

#ifndef BTT_OS_VER_H__
#define BTT_OS_VER_H__

////////////////////////////////////////////////////////////////////////////////


//Defines the version of the OS to build BTT for. This is currently set
//semi-automatically - we can decide between Beech and Cedar based on whether
//EKA2 is defined.
//
// To change the environment, define one, and only one, of the following:
//
#define BUILD_FOR_MIKE
//#define BUILD_FOR_YANKEE
//#define BUILD_FOR_SIERRA
//#define BUILD_FOR_ZEPHYR

////////////////////////////////////////////////////////////////////////////////
//Include this macro to support UIQ extensions
//
//#define USE_UIQ_EXTENSIONS

//Include this macro to support custom extensions
//
//#define USE_CUSTOM_EXTENSIONS

////////////////////////////////////////////////////////////////////////////////
//The following macros are included or excluded dependent on the dispatch layer that is being built. Currenlty the 
//Symbian dispatch layer macros are:
//USE_TTPCOM_STACK
//USE_TI_CONDAT_STACK
//USE_STUBBED
//USE_LAYER_3
//USE_ICERA_STACK

//Note: DO NOT #define them here.  They should be included in the mmp files by using the macro keyword.


//Depedent on which dispatch layer is built will affect other macros that need to be included.
#if defined USE_TTPCOM_STACK || defined USE_TI_CONDAT_STACK || defined USE_LAYER_3 || defined USE_ICERA_STACK

	//include this when dispatch layer supports WCDMA
	//#define WCDMA_STUB

	//Include this dependent on target h/w.  
	//Note: Compiling with the H2 set will actually also work when executing with DSample.
	
	//#define DSAMPLE
	//#define P2SAMPLE
	#define H2
	
#elif defined USE_STUBBED

	//This is included for stubbed dispatch layer as this supports WCDMA features.
	#define WCDMA_STUB
#endif 

////////////////////////////////////////////////////////////////////////////////

// Define this macro to log the request queue. This will produce a very large
// log file.

//#define REQUEST_QUEUE_LOGGING

////////////////////////////////////////////////////////////////////////////////

// Helper #defines based on the ones at the top.

#ifndef BUILD_FOR_ZEPHYR
#  ifndef BUILD_FOR_SIERRA
#    ifndef BUILD_FOR_YANKEE
#      ifndef BUILD_FOR_MIKE
#        error "You must have one of the OS configuration macros defined!"
#      else
#        define SUPPORT_MIKE_API
#        define SUPPORT_YANKEE_API
#        define SUPPORT_SIERRA_API
#        define SUPPORT_ZEPHYR_API
#      endif
#    else
#      define SUPPORT_YANKEE_API
#      define SUPPORT_SIERRA_API
#      define SUPPORT_ZEPHYR_API
#    endif
#  else
#    define SUPPORT_SIERRA_API
#    define SUPPORT_ZEPHYR_API
#  endif
#else
#  define SUPPORT_ZEPHYR_API
#endif


#endif // BTT_OS_VER_H__
