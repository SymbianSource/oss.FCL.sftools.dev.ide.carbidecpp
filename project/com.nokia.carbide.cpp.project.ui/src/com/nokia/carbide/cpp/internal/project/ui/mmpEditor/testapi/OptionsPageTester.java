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

package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.testapi;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.CompilerSectionPart;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.KernelSectionPart;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.LinkerSectionPart;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.OptionsPage;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.RuntimeSectionPart;

/**
 * An interface for testing the Options page of the MMP Editor
 */
public class OptionsPageTester {

	/**
	 * Retrieves any error message from the Options page.
	 * @param page - Options page provided by caller
	 * @return error message string if success, null otherwise
	 */
	public static String getErrorMessage(FormPage page) {
		if (page != null && page instanceof OptionsPage) {
			OptionsPage optionsPage = (OptionsPage) page;
			return optionsPage.getErrorMessage();
		}
		return null;
	}

	/**
	 * Retrieves the "Runtime" section of the Options page.
	 * @param page - Options page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getRuntimeSection(FormPage page) {
		if (page != null && page instanceof OptionsPage) {
			OptionsPage optionsPage = (OptionsPage) page;
			return optionsPage.getRuntimeSection();
		}
		return null;
	}

	/**
	 * Retrieves the "Compiler" section of the Options page.
	 * @param page - Options page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getCompilerSection(FormPage page) {
		if (page != null && page instanceof OptionsPage) {
			OptionsPage optionsPage = (OptionsPage) page;
			return optionsPage.getCompilerSection();
		}
		return null;
	}

	/**
	 * Retrieves the "Linker" section of the Options page.
	 * @param page - Options page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getLinkerSection(FormPage page) {
		if (page != null && page instanceof OptionsPage) {
			OptionsPage optionsPage = (OptionsPage) page;
			return optionsPage.getLinkerSection();
		}
		return null;
	}

	/**
	 * Retrieves the "Kernel" section of the Options page.
	 * @param page - Options page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getKernelSection(FormPage page) {
		if (page != null && page instanceof OptionsPage) {
			OptionsPage optionsPage = (OptionsPage) page;
			return optionsPage.getKernelSection();
		}
		return null;
	}

	/**
	 * Retrieves the "Choose" button widget of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getChooseButton(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getChooseButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Capabilities" text widget of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getCapabilitiesText(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getCapabilitiesText();
		}
		return null;
	}

	/**
	 * Retrieves the "Minimum heap size" text widget of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getMinHeapSizeText(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getMinHeapSizeText();
		}
		return null;
	}

	/**
	 * Retrieves the "Maximum heap size" text widget of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getMaxHeapSizeText(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getMaxHeapSizeText();
		}
		return null;
	}

	/**
	 * Retrieves the "Stack size" text widget of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getStackSizeText(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getStackSizeText();
		}
		return null;
	}

	/**
	 * Retrieves the "Process priority" combo widget of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - combo widget if success, null otherwise
	 */
	public static Combo getProcessPriorityCombo(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getProcessPriorityCombo();
		}
		return null;
	}

	/**
	 * Retrieves the "Secure ID" text widget of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getSecureIDText(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getSecureIDText();
		}
		return null;
	}

	/**
	 * Retrieves the "Vendor ID" text widget of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getVendorIDText(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getVendorIDText();
		}
		return null;
	}

	/**
	 * Retrieves the "Enable Debugging" button widget of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getDebuggableButton(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getDebuggableButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Paging mode" combo widget of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - combo widget if success, null otherwise
	 */
	public static Combo getPagingModeCombo(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getPagingModeCombo();
		}
		return null;
	}

	/**
	 * Retrieves the capabilities dialog of the Runtime section.
	 * @param part - Runtime section provided by caller
	 * @return - capabilities dialog if success, null otherwise
	 */
	public static Dialog getCapabilitiesDialog(SectionPart part) {
		if (part != null && part instanceof RuntimeSectionPart) {
			RuntimeSectionPart rPart = (RuntimeSectionPart) part;
			return rPart.getCapabilitiesDialog();
		}
		return null;
	}

	/**
	 * Retrieves the compiler tree viewer of the Compiler section.
	 * @param part - Compiler section provided by caller
	 * @return - tree viewer if success, null otherwise
	 */
	public static TreeViewer getCompilerTreeViewer(SectionPart part) {
		if (part != null && part instanceof CompilerSectionPart) {
			CompilerSectionPart cPart = (CompilerSectionPart) part;
			return cPart.getCompilerTreeViewer();
		}
		return null;
	}

	/**
	 * Retrieves the "Add" button widget of the Compiler section.
	 * @param part - Compiler section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getAddButton(SectionPart part) {
		if (part != null && part instanceof CompilerSectionPart) {
			CompilerSectionPart cPart = (CompilerSectionPart) part;
			return cPart.getAddButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Edit" button widget of the Compiler section.
	 * @param part - Compiler section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getEditButton(SectionPart part) {
		if (part != null && part instanceof CompilerSectionPart) {
			CompilerSectionPart cPart = (CompilerSectionPart) part;
			return cPart.getEditButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Up" button widget of the Compiler section.
	 * @param part - Compiler section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getUpButton(SectionPart part) {
		if (part != null && part instanceof CompilerSectionPart) {
			CompilerSectionPart cPart = (CompilerSectionPart) part;
			return cPart.getUpButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Down" button widget of the Compiler section.
	 * @param part - Compiler section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getDownButton(SectionPart part) {
		if (part != null && part instanceof CompilerSectionPart) {
			CompilerSectionPart cPart = (CompilerSectionPart) part;
			return cPart.getDownButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Remove" button widget of the Compiler section.
	 * @param part - Compiler section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getRemoveButton(SectionPart part) {
		if (part != null && part instanceof CompilerSectionPart) {
			CompilerSectionPart cPart = (CompilerSectionPart) part;
			return cPart.getRemoveButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Disable optimizations in debug build" button widget of the Compiler section.
	 * @param part - Compiler section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getSrcDebugButton(SectionPart part) {
		if (part != null && part instanceof CompilerSectionPart) {
			CompilerSectionPart cPart = (CompilerSectionPart) part;
			return cPart.getSrcDebugButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Per-variant include dependencies" button widget of the Compiler section.
	 * @param part - Compiler section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getStrictDependenciesButton(SectionPart part) {
		if (part != null && part instanceof CompilerSectionPart) {
			CompilerSectionPart cPart = (CompilerSectionPart) part;
			return cPart.getStrictDependenciesButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Linker definition file" combo widget of the Linker section.
	 * @param part - Linker section provided by caller
	 * @return - combo widget if success, null otherwise
	 */
	public static Combo getDefFileCombo(SectionPart part) {
		if (part != null && part instanceof LinkerSectionPart) {
			LinkerSectionPart lPart = (LinkerSectionPart) part;
			return lPart.getDefFileCombo();
		}
		return null;
	}

	/**
	 * Retrieves the "Browse" button widget of the Linker section.
	 * @param part - Linker section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getBrowseButton(SectionPart part) {
		if (part != null && part instanceof LinkerSectionPart) {
			LinkerSectionPart lPart = (LinkerSectionPart) part;
			return lPart.getBrowseButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Export unfrozen" button widget of the Linker section.
	 * @param part - Linker section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getExportUnfrozenButton(SectionPart part) {
		if (part != null && part instanceof LinkerSectionPart) {
			LinkerSectionPart lPart = (LinkerSectionPart) part;
			return lPart.getExportUnfrozenButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Don't export library" button widget of the Linker section.
	 * @param part - Linker section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getDontExportLibraryButton(SectionPart part) {
		if (part != null && part instanceof LinkerSectionPart) {
			LinkerSectionPart lPart = (LinkerSectionPart) part;
			return lPart.getDontExportLibraryButton();
		}
		return null;
	}

	/**
	 * Retrieves the "No strict .def file" button widget of the Linker section.
	 * @param part - Linker section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getNoStrictdefButton(SectionPart part) {
		if (part != null && part instanceof LinkerSectionPart) {
			LinkerSectionPart lPart = (LinkerSectionPart) part;
			return lPart.getNoStrictdefButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Compress target executable" button widget of the Linker section.
	 * @param part - Linker section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getCompressTargetExecutableButton(SectionPart part) {
		if (part != null && part instanceof LinkerSectionPart) {
			LinkerSectionPart lPart = (LinkerSectionPart) part;
			return lPart.getCompressTargetExecutableButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Link as internal name" text widget of the Linker section.
	 * @param part - Linker section provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getLinkAsText(SectionPart part) {
		if (part != null && part instanceof LinkerSectionPart) {
			LinkerSectionPart lPart = (LinkerSectionPart) part;
			return lPart.getLinkAsText();
		}
		return null;
	}

	/**
	 * Retrieves the "Win32 base address" text widget of the Linker section.
	 * @param part - Linker section provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getWin32BaseAddrText(SectionPart part) {
		if (part != null && part instanceof LinkerSectionPart) {
			LinkerSectionPart lPart = (LinkerSectionPart) part;
			return lPart.getWin32BaseAddrText();
		}
		return null;
	}

	/**
	 * Retrieves the "ASSP ABI" button widget of the Kernel section.
	 * @param part - Kernel section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getAsspAbiButton(SectionPart part) {
		if (part != null && part instanceof KernelSectionPart) {
			KernelSectionPart kPart = (KernelSectionPart) part;
			return kPart.getAsspAbiButton();
		}
		return null;
	}

	/**
	 * Retrieves the "ASSP exports" button widget of the Kernel section.
	 * @param part - Kernel section provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getAsspExportsButton(SectionPart part) {
		if (part != null && part instanceof KernelSectionPart) {
			KernelSectionPart kPart = (KernelSectionPart) part;
			return kPart.getAsspExportsButton();
		}
		return null;
	}

}
