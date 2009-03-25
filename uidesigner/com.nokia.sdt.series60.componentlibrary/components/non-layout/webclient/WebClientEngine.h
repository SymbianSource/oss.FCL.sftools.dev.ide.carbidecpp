/*
* Copyright (c) 2005-2006 Nokia Corporation and/or its subsidiary(-ies).
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


#ifndef WEBCLIENTENGINE_H
#define WEBCLIENTENGINE_H

// INCLUDES
#include <coecntrl.h>
#include <http.h>
#include <http/mhttpauthenticationcallback.h>
#include <es_sock.h>

// CONSTANTS
const TInt KMaxHeaderNameLength     = 32;
const TInt KMaxHeaderValueLength    = 128;
const TInt KMaxAuthTypeLength       = 128;
const TInt KMaxDateTimeStringLength = 40;
const TInt KMaxStatusTextLength     = 32;
const TInt KMaxUserNameLength       = 128;
const TInt KMaxPasswordLength       = 128;

// Used user agent for requests
_LIT8( KUserAgent, "WebClient 1.0" );

// This client accepts all content types.
// (change to e.g. "text/plain" for plain text only)
_LIT8( KAccept, "*/*" );

// Format for output of data/time values
_LIT( KDateFormat,"%D%M%Y%/0%1%/1%2%/2%3%/3 %:0%H%:1%T%:2%S.%C%:3" );

// Some texts for header output
_LIT( KColon, ": " );
_LIT( Krealm, "Realm: " );

class CWebClientEngine;

// CLASS DECLARATION

/**
* MWebClientObserver
* CWebClientEngine passes events and responses body data with this interface. 
* An instance of this class must be provided for construction of CWebClientEngine.
*/
class MWebClientObserver 
    {
    public:
    	/**
    	 * ClientOpenSessionFailedL( CWebClientEngine& anEngine )
    	 * An error occurred opening the HTTP session. The calling code
    	 * will leave after this method returns.
    	 * @param anError: the error code
    	 * Most common error; no access point configured, and session creation
         * leaves with KErrNotFound.
    	 */
    	virtual void ClientOpenSessionFailedL( CWebClientEngine& anEngine, TInt anError ) = 0;
 		/**
         * ClientConnectingL()
         * Called to notify that a connection was initiated
         */
        virtual void ClientConnectingL( CWebClientEngine& anEngine ) = 0;
 
		/**
        * ClientHeaderReceivedL()
        * Called when HTTP header is received.
        * @param aHeaderData: Header field name and value
        */
        virtual void ClientHeaderReceivedL( CWebClientEngine& anEngine, const TDesC& aHeaderData ) = 0;

 		/**
        * ClientBodyReceivedL()
        * Called when a part of the HTTP body is received.
        * @param aBodyData:  Part of the body data received. (e.g. part of
        *                    the received HTML page)
        */
        virtual void ClientBodyReceivedL( CWebClientEngine& anEngine, const TDesC8& aBodyData ) = 0;
               
 		/**
         * ClientConnectionCanceledL()
         * Called to notify that a connection attempt has been canceled
         */
        virtual void ClientConnectionCanceledL( CWebClientEngine& anEngine ) = 0;
        
 		/**
         * ClientResponseCompleteL
         * Called to notify that a transaction's response is complete.
         * See TTransactionEvent::EResponseComplete
         */
        virtual void ClientResponseCompleteL( CWebClientEngine& anEngine ) = 0;
        
 		/**
         * ClientTransactionSucceeded()
         * Called to notify that a transaction completed successfully
         * See TTransactionEvent::ESucceeded
         */
        virtual void ClientTransactionSucceededL( CWebClientEngine& anEngine ) = 0;
        
 		/** 
         * ClientTransactionFailed()
         * Catch-all for failure. 
         * See TTransactionEvent::EFailed
         */
         virtual void ClientTransactionFailedL( CWebClientEngine& anEngine ) = 0;
         
  		/**
          * ClientUnknownEventL()
          * Called to notify that an unknown HTTP event has
          * been received.
          * @param aStatus: the iStatus field of the event
          * See THTTPEvent::iStatus
          */
         virtual void ClientUnknownEventL( CWebClientEngine& anEngine , TInt aStatus ) = 0;
        
 		/**
         * ClientRunErrorL()
         * Called when a error occurs in the handling of a transaction event.
         * @param anError: the error code
         */
        virtual void ClientRunErrorL( CWebClientEngine& anEngine , TInt anError) = 0;
        
 		/**
         * ClientGetCredentialsL()
         * Called when authentication has been requested by the server.
         * Return EFalse for no authentication or e.g. the user cancels
         * an input dialog. Otherwise return the user name and password 
         * as out parameters along with an ETrue result.
         * @param aUri: the current URI
         * @param aRealm: the realm associated with the request
         * @param aUserName: the returned user name
         * @param aPassword: the returned password
         */
        virtual TBool ClientGetCredentialsL( CWebClientEngine& anEngine, const TUriC8& aUri, 
                                const TDesC8& aRealm, 
                                TDes& aUsername, 
                                TDes& aPassword ) = 0;
        
    };

/**
* CWebClientEngine
* Provides simple interface to HTTP Client API.
*/
class CWebClientEngine : public CBase, 
                         public MHTTPTransactionCallback,
                         public MHTTPAuthenticationCallback
    {
    public:
 		/**
        * NewL()
        * Create a CWebClientEngine object.
        * @param  iObserver: 
        * @return A pointer to the created instance of CWebClientEngine
        */
        static CWebClientEngine* NewL( MWebClientObserver& aObserver );

 		/**
        * NewLC()
        * Create a CWebClientEngine object.
        * @param  iObserver:
        * @return A pointer to the created instance of CWebClientEngine
        */
        static CWebClientEngine* NewLC( MWebClientObserver& aObserver );

 		/**
        * ~CWebClientEngine()
        * Destroy the object
        */
        ~CWebClientEngine();
        
 		/**
         * Opens the HTTP session. Automatically called when a session is initiated,
         * or can be called separately.
         */
        void OpenSessionL();

 		/**
        * IssueHTTPGetL()
        * Starts a new HTTP GET transaction.
        * @param aUri: URI to get. (e.g. http://host.org")
        */
        void IssueHTTPGetL( const TDesC8& aUri );

 		/**
        * CancelTransactionL()
        * Closes currently running transaction and frees resources related to it.
        */
        void CancelTransactionL();

 		/**
        * IsRunning()
        * Checks if the transaction is running.
        * @return ETrue, if transaction is currently running.
        */
        inline TBool IsRunning() { return iRunning; };

    private:
 		/**
        * ConstructL()
        * Perform the second phase construction of a CWebClientEngine object.
        */
        void ConstructL();

 		/**
        * CWebClientEngine()
        * Perform the first phase of two phase construction.
        * @param iObserver: 
        */
        CWebClientEngine( MWebClientObserver& iObserver );

 		/**
        * SetHeaderL()
        * Sets header value of an HTTP request.
        * @param aHeaders:  Headers of the HTTP request
        * @param aHdrField: Enumerated HTTP header field, e.g. HTTP::EUserAgent
        * @param aHdrValue: New value for header field
        */
        void SetHeaderL( RHTTPHeaders aHeaders, TInt aHdrField, 
                         const TDesC8& aHdrValue );

 		/**
        * DumpRespHeadersL()
        * Called when HTTP header is received.
        * Displays HTTP header field names and values
        * @param aTransaction: The transaction that is processed.
        */
        void DumpRespHeadersL( RHTTPTransaction& aTransantion );

 		/**
        * HandleRunErrorL()
        * Called from MHFRunError() when *leave* occurs in handling of transaction event. 
        * @param aError:       The leave code that occured.
        */
        void HandleRunErrorL( TInt aError );

    /**
    * From MHTTPSessionEventCallback
    */
    private:
 		/**
        * MHFRunL()
        * Called by framework to notify about transaction events.
        * @param aTransaction: Transaction, where the event occured.
        * @param aEvent:       Occured event.
        */
        void MHFRunL( RHTTPTransaction aTransaction, const THTTPEvent& aEvent );

 		/**
        * MHFRunError()
        * Called by framework when *leave* occurs in handling of transaction event. 
        * @param aError:       The leave code that occured.
        * @param aTransaction: The transaction that was being processed when leave occured.
        * @param aEvent:       The event that was being processed when leave occured.
        * @return KErrNone,    if the error was handled. Otherwise the value of aError, or
        *                      some other error value. Returning error value causes causes 
        *                      HTTP-CORE 6 panic.
        */
        TInt MHFRunError( TInt aError, 
                          RHTTPTransaction aTransaction, 
                          const THTTPEvent& aEvent );

    /**
    * From MHTTPAuthenticationCallback (needed for HTTP authentication)
    */
    private:
 		/**
        * GetCredentialsL()
        * Called by framework when username and password for requested URI is 
        * needed.
        * @param aURI: The URI being requested (e.g. "http://host.org")
        * @param aRealm: The realm being requested (e.g. "user@host.org")
        * @param aAuthenticationType: Authentication type. (e.g. "Basic")
        * @param aUsername: Given user name.
        * @param aPassword: Given password.
        * @return A pointer to the created document
        */
        TBool GetCredentialsL(  const TUriC8& aUri, 
                                RString aRealm, 
                                RStringF aAuthenticationType, 
                                RString& aUsername, 
                                RString& aPassword );
                                
    private: // Data
        RHTTPSession            iSession;
        RHTTPTransaction        iTransaction;
        RSocketServ 			iSocketServ;
		RConnection 			iConnection;
        
        MWebClientObserver&     iObserver;      // Used for passing body data and
                                                // events to UI.
        TBool					iSessionOpened; // ETrue, if session successfully opened
        TBool                   iRunning;       // ETrue, if transaction running
    };

#endif // WEBCLIENTENGINE_H
