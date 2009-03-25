/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests.model.dummy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nokia.carbide.cpp.epoc.engine.model.IData;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.model.AddModification;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ChangeModification;
import com.nokia.carbide.internal.cpp.epoc.engine.model.DeleteModification;
import com.nokia.carbide.internal.cpp.epoc.engine.model.IViewModification;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewASTBase;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.Pair;


public class DummyView extends ViewASTBase<IDummyModel> implements IDummyView {

	private ArrayList<String> adds;
	private ArrayList<String> removes;
	private ArrayList<Pair<String,String>> changes;
	private List<IASTStatement> knownStatements;
	
	/**
	 * @param model
	 * @param parser
	 * @param viewConfiguration
	 */
	public DummyView(ModelBase model, IViewConfiguration viewConfiguration) {
		super(model,
				new DummyParser(), 
				viewConfiguration);
		adds = new ArrayList<String>();
		removes = new ArrayList<String>();
		changes = new ArrayList<Pair<String,String>>();
		knownStatements = new ArrayList<IASTStatement>();
	}
	
	/** Modification API */
	public void add(String text) {
		adds.add(text);
		fireChanged();
		
	}
	public void remove(String text) {
		removes.add(text);
		fireChanged();
	}

	public void change(String fromText, String toText) {
		changes.add(new Pair<String, String>(fromText, toText));
		fireChanged();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.internal.ViewBase#internalHasChanges()
	 */
	@Override
	protected boolean internalHasChanges() {
		return adds.size() > 0 || removes.size() > 0 || changes.size() > 0;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.internal.ViewBase#internalRevertChanges()
	 */
	@Override
	protected void internalRevertChanges() {
		adds.clear();
		removes.clear();
		changes.clear();
	}

	@Override
	protected void internalGatherChanges(List<IViewModification> mods, List<IMessage> messages) {
		IASTTranslationUnit tu = getFilteredTranslationUnit();
		for (String text : adds) {
			IASTDummyStatement node = containsNode(tu, text);
			if (node == null) {
				mods.add(new AddModification(tu, new ASTDummyStatement(ASTFactory.createPreprocessorLiteralTextNode(text))));
			}
		}
		for (String text : removes) {
			IASTDummyStatement node = containsNode(tu, text);
			// may have been forced
			if (node != null) {
				mods.add(new DeleteModification(node));
			}
		}
		for (Pair<String, String> change : changes) {
			IASTDummyStatement node = containsNode(tu, change.first);
			// may have been forced
			if (node != null) {
				node.setText(ASTFactory.createPreprocessorLiteralTextNode(change.second));
				mods.add(new ChangeModification(node));
			}
		}
	}
	
	@Override
	protected void internalFinalizePreparserTranslationUnit(
			IASTTranslationUnit tu) {
		
	}
	

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.internal.ViewBase#internalMerge(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode[], org.eclipse.core.runtime.IPath[])
	 */
	@Override
	protected boolean internalMerge(IASTTranslationUnit oldTu) {
		boolean merged = true;
		IASTTranslationUnit tu = getFilteredTranslationUnit();
		for (IASTTopLevelNode node : tu.getNodes()) {
			// our assumption is that nothing is ever duplicated, so
			// we don't need diff info against the previous TU
			String text = node.getNewText();
			if (adds.contains(text))
				adds.remove(text);
		}
		for (String text : removes) {
			if (containsNode(tu, text) == null)
				removes.remove(text);
		}
		for (Pair<String, String> change : changes) {
			IASTDummyStatement stmt = containsNode(tu, change.first);
			if (stmt == null) {
				stmt = containsNode(tu, change.second);
				if (stmt == null)
					merged = false;
				else
					changes.remove(change);
			}
		}
		return merged;
	}

	/**
	 * Tell if the TU contains the given node.
	 * @param tu
	 * @param text
	 * @return
	 */
	private IASTDummyStatement containsNode(IASTTranslationUnit tu, String text) {
		for (IASTTopLevelNode node : tu.getNodes()) {
			if (node instanceof IASTDummyStatement && ((IASTDummyStatement)node).getText().getValue().equals(text))
				return (IASTDummyStatement) node;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#addViewSpecificMessages(java.util.List)
	 */
	@Override
	protected void addViewSpecificMessages(List<IMessage> messageList) {
		
	}
	
	@Override
	public Collection<IASTStatement> getKnownStatements() {
		return knownStatements;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getData()
	 */
	public IData getData() {
		return null;
	}
}
