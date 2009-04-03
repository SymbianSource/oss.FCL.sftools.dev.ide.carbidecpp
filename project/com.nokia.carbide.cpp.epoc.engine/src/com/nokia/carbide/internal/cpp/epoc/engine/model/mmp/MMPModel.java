/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp.IMMPParserConfiguration;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;


public class MMPModel extends ModelBase<IMMPView> implements IMMPOwnedModel {

	public MMPModel(IPath path, IDocument document) {
		super(path, document);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase#createView(com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase, com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration)
	 */
	@Override
	protected IMMPView createView(ModelBase model, IViewConfiguration configuration) {
		Check.checkArg(configuration instanceof IMMPViewConfiguration);
		IMMPParserConfiguration parserConfiguration = createParserConfiguration(
				((IMMPViewConfiguration) configuration));
		return new MMPView(model, 
				ParserFactory.createMMPParser(parserConfiguration), 
				(IMMPViewConfiguration) configuration);
	}

	/**
	 * @param configuration
	 * @return
	 */
	private IMMPParserConfiguration createParserConfiguration(final IMMPViewConfiguration configuration) {
		return new IMMPParserConfiguration() {

			public int categorizeStatement(String keyword) {
				try {
					EMMPStatement stmt = EMMPStatement.valueOf(keyword.toUpperCase());
					if (configuration.isStatementSupported(stmt))
						return stmt.getCategory();
				} catch (IllegalArgumentException e) {
				}
				return IMMPParserConfiguration.UNKNOWN_STATEMENT;
			}

			public boolean isAifStatementRecognized() {
				return configuration.isStatementSupported(EMMPStatement.AIF);
			}

			public boolean isBitmapSourceStatementRecognized() {
				return configuration.isStatementSupported(EMMPStatement.BITMAP_SOURCE);
			}

			public boolean isOptionStatementRecognized() {
				return configuration.isStatementSupported(EMMPStatement.OPTION);
			}

			public boolean isStartBlockStatementRecognized() {
				return configuration.isStatementSupported(EMMPStatement.START_BLOCK);
			}

			public boolean isUidStatementRecognized() {
				return configuration.isStatementSupported(EMMPStatement.UID);
			}
			
		};
		
	}

}
