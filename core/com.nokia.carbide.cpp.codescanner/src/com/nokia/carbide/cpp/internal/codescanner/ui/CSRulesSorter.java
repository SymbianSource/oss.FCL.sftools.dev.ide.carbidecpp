/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies). 
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

package com.nokia.carbide.cpp.internal.codescanner.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.nokia.carbide.cpp.internal.codescanner.config.CSRule;

/**
 * A class to to handle the sorting of the CodeScanner Rules table.
 */
public class CSRulesSorter extends ViewerSorter {
	// public constants indicating sorting type
	public final static int SORT_BY_RULE = 0;
	public final static int SORT_BY_CATEGORY = 1;
	public final static int SORT_BY_SEVERITY = 2;

	// private members of this sorter
	private int sortingType;

	/**
	 * Create an instance of this sorter.
	 */
	public CSRulesSorter() {
		super();
		sortingType = SORT_BY_RULE;
	}

    /*
     * @see org.eclipse.jface.viewers.ViewerComparator#compare(Viewer, Object, Object)
     */
	@SuppressWarnings("unchecked")
	public int compare(Viewer viewer, Object e1, Object e2) {
        if ((e1 != null) && (e1 instanceof CSRule) &&
        	(e2 != null) && (e2 instanceof CSRule)) {
        	CSRule rule1 = (CSRule)e1;
        	CSRule rule2 = (CSRule)e2;
            String name1;
            String name2;

			switch (sortingType) {
				case SORT_BY_RULE:
					name1 = rule1.getScript().toString();
					name2 = rule2.getScript().toString();
					return getComparator().compare(name1, name2);
				case SORT_BY_CATEGORY:
					name1 = rule1.getCategory().toString();
					name2 = rule2.getCategory().toString();
					return getComparator().compare(name1, name2);
				case SORT_BY_SEVERITY:
					return compareSeverity(rule1, rule2);
				default:
					return 0;
			}
        }
        else {
        	return super.compare(viewer, e1, e2);
        }
	}

	/**
	 * Get the type of sorting to be performed by this sorter.
	 */
	public int getSortingType() {
		return sortingType;
	}

	/**
	 * Set the type of sorting to be performed by this sorter.
	 * @param newSortingType - new sorting type
	 */
	public void setSortingType(int newSortingType) {
		if ((newSortingType >= SORT_BY_RULE) && (newSortingType <= SORT_BY_SEVERITY)) {
			sortingType = newSortingType;
		}
		else {
			sortingType = SORT_BY_RULE;			
		}
	}

    /**
     * Compare the severity of first and second rule and return a 
     * negative, zero, or positive number depending on result of the
     * comparison.
     * @param rule1 - the first rule
     * @param rule2 - the second rule
     */
	private int compareSeverity(CSRule rule1, CSRule rule2) {
		int value1 = rule1.getSeverity().ordinal();
		int value2 = rule2.getSeverity().ordinal();
		if (value1 < value2) return -1;
		if (value1 > value2) return 1;
		return 0;
	}

}
