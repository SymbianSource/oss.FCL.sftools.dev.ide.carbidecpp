/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * Images provided by the Carbide.c++ UI Plugin.
 * 
 * Images are accessed by the String keys defined in this class.
 * The dimensions are encoded into the end of the key, e.g. IMAGE_EXAMPLE_X_Y is 
 * X pixels horizontal by Y pixels vertical.
 * <br/><br/>
 * The Javadoc for each image key includes the plugin-relative path to the image file. The image
 * files can be referenced via these paths. 
 * <br/><br/>
 * For example, to reference an image from an HTML help file use a statement like:
 * <br/>
 * &lt;img src="PLUGINS_ROOT/com.nokia.carbide.cpp.ui/icons/Carbide_c_icon_16x16.png"/&gt;
 */
public interface ICarbideSharedImages {
	
	// Keys for image names. 
	
	/**
	 * File path: icons/_New_Symbian_S_Class_16x16.png
	 */
	public static final String IMG_NEW_SYMBIAN_CLASS_16_16 = "_New_Symbian_S_Class_16x16.png";					//$NON-NLS-1$
	/**
	 * File path: icons/New_Symbian_OS_Class_55x45.png
	 */
	public static final String IMG_NEW_SYMBIAN_CLASS_55_45 = "New_Symbian_OS_Class_55x45.png";			//$NON-NLS-1$
	/**
	 * File path: icons/Add_Image_file.png
	 */
	public static final String IMG_ADD_IMAGE_FILE_16_16 = "Add_Image_file.png";						//$NON-NLS-1$
	/**
	 * File path: icons/Add_MMP_Definition_file.png
	 */
	public static final String IMG_ADD_MMP_FILE_16_16 = "Add_MMP_Definition_file.png";					//$NON-NLS-1$
	/**
	 * File path: icons/AIF_Definition_file.png
	 */
	public static final String IMG_AIF_FILE_16_16 = "AIF_Definition_file.png";							//$NON-NLS-1$
	/**
	 * File path: icons/Application_badge.png
	 */
	public static final String IMG_APPLICATION_BADGE_32_32 = "Application_badge.png";					//$NON-NLS-1$
	/**
	 * File path: icons/
	 */
	public static final String IMG_BLD_INF_16_16 = "Bld_inf_Definition_file.png";						//$NON-NLS-1$
	/**
	 * File path: icons/Bld_inf_Definition_file.png
	 */
	public static final String IMG_BUILD_ALL_16_16 = "Build_All_Targets_16x16.png";					//$NON-NLS-1$
	/**
	 * File path: icons/Build_All_Targets_24x24.png
	 */
	public static final String IMG_BUILD_ALL_24_24 = "Build_All_Targets_24x24.png";					//$NON-NLS-1$
	/**
	 * File path: icons/Build_Symbian_Component.png
	 */
	public static final String IMG_BUILD_SYMBIAN_COMPONENT = "Build_Symbian_Component.png";			//$NON-NLS-1$
	/**
	 * File path: icons/Carbide_c_icon_16x16.png
	 */
	public static final String IMG_CARBIDE_C_ICON_16_16 = "Carbide_c_icon_16x16.png";					//$NON-NLS-1$
	/**
	 * File path: icons/Carbide_C_C++_perspective.png
	 */
	public static final String IMG_CARBIDE_CPP_PERSPECTIVE_16_16 = "Carbide_C_C++_perspective.png";	//$NON-NLS-1$
	/**
	 * File path: icons/
	 */
	public static final String IMG_COMPILER_OPTIONS_16_16 = "Compiler_Options.png";					//$NON-NLS-1$
	/**
	 * File path: icons/Compiler_Options.png
	 */
	public static final String IMG_CUSTOM_TRACE_BADGE_24_24 = "Custom_Trace_badge.png";				//$NON-NLS-1$
	/**
	 * File path: icons/Examples_16x16.png
	 */
	public static final String IMG_EXAMPLES_16_16 = "Examples_16x16.png";								//$NON-NLS-1$
	/**
	 * File path: icons/Examples_24x24.png
	 */
	public static final String IMG_EXAMPLES_24_24 = "Examples_24x24.png";								//$NON-NLS-1$
	/**
	 * File path: icons/Examples_32x32.png
	 */
	public static final String IMG_EXAMPLES_16_16_32_32 = "Examples_32x32.png";						//$NON-NLS-1$
	/**
	 * File path: icons/Examples_48x48.png
	 */
	public static final String IMG_EXAMPLES_48_48 = "Examples_48x48.png";								//$NON-NLS-1$
	/**
	 * File path: icons/Examples_72x72.png
	 */
	public static final String IMG_EXAMPLES_72_72 = "Examples_72x72.png";								//$NON-NLS-1$
	/**
	 * File path: icons/Filter.png
	 */
	public static final String IMG_FILTER_16_16 = "Filter.png";										//$NON-NLS-1$
	/**
	 * File path: icons/Freeze_Exports_16x16.png
	 */
	public static final String IMG_FREEZE_EXPORTS_16_16 = "Freeze_Exports_16x16.png";					//$NON-NLS-1$
	/**
	 * File path: icons/Freeze_Exports_24x24.png
	 */
	public static final String IMG_FREEZE_EXPORTS_24_24 = "Freeze_Exports_24x24.png";					//$NON-NLS-1$
	/**
	 * File path: icons/Import_BldInf_wizard_banner.png
	 */
	public static final String IMG_IMPORT_BLDINF_WIZARD_BANNER = "Import_BldInf_wizard_banner.png";	//$NON-NLS-1$
	/**
	 * File path: icons/Import_SOS_Exe_wizard_banner.png
	 */
	public static final String IMG_IMPORT_SOS_EXE_WIZARD_BANNER = "Import_SOS_Exe_wizard_banner.png";	//$NON-NLS-1$
	/**
	 * File path: icons/Languages.png
	 */
	public static final String IMG_LANGUAGES_16_16 = "Languages.png";									//$NON-NLS-1$
	/**
	 * File path: icons/Macros.png
	 */
	public static final String IMG_MACROS_16_16 = "Macros.png";										//$NON-NLS-1$
	/**
	 * File path: icons/MBM_MIF_AIF_Editor.png
	 */
	public static final String IMG_MBM_MIF_AIF_EDITOR_16_16 = "MBM_MIF_AIF_Editor.png";				//$NON-NLS-1$
	/**
	 * File path: icons/MIF_Definition_file.png
	 */
	public static final String IMG_MIF_FILE_16_16 = "MIF_Definition_file.png";							//$NON-NLS-1$
	/**
	 * File path: icons/MMP_Definition_file.png
	 */
	public static final String IMG_MMP_FILE_16_16 = "MMP_Definition_file.png";							//$NON-NLS-1$
	/**
	 * File path: icons/MBM_Definition_file.png
	 */
	public static final String IMG_MBM_FILE_16_16 = "MBM_Definition_file.png";							//$NON-NLS-1$
	/**
	 * File path: icons/New_Launch_Config_wizard_banner.png
	 */
	public static final String IMG_NEW_LAUNCH_CONFIG_WIZARD_BANNER = "New_Launch_Config_wizard_banner.png";	//$NON-NLS-1$
	/**
	 * File path: icons/New_MMP_file_wizard_banner.png
	 */
	public static final String IMG_NEW_MMP_FILE_WIZARD_BANNER = "New_MMP_file_wizard_banner.png";		//$NON-NLS-1$
	/**
	 * File path: icons/New_Project.png
	 */
	public static final String IMG_NEW_PROJECT_16_16 = "New_Project.png";								//$NON-NLS-1$
	/**
	 * File path: icons/New_Symbian_OS_C++_Project_16x16.png
	 */
	public static final String IMG_NEW_CARBIDE_PROJECT_16_16 = "New_Symbian_OS_C++_Project_16x16.png";	//$NON-NLS-1$
	/**
	 * File path: icons/New_Symbian_OS_C++_Project_55x45.png
	 */
	public static final String IMG_NEW_CARBIDE_PROJECT_55_45 = "New_Symbian_OS_C++_Project_55x45.png";	//$NON-NLS-1$
	/**
	 * File path: icons/New_Symbian_OS_C++_Project_wizard_banner.png
	 */
	public static final String IMG_NEW_CARBIDE_PROJECT_WIZARD_BANNER = "New_Symbian_OS_C++_Project_wizard_banner.png";	//$NON-NLS-1$
	/**
	 * File path: icons/New_Symbian_OS_Class_wizard_banner.png
	 */
	public static final String IMG_NEW_SOS_CLASS_WIZARD_BANNER = "New_Symbian_OS_Class_wizard_banner.png";	//$NON-NLS-1$
	/**
	 * File path: icons/New_UI_Design_wizard_banner.png
	 */
	public static final String IMG_NEW_UI_DESIGN_WIZARD_BANNER = "New_UI_Design_wizard_banner.png";	//$NON-NLS-1$
	/**
	 * File path: icons/Phone_ROM_badge.png
	 */
	public static final String IMG_PHONE_ROM_BADGE_32_32 = "Phone_ROM_badge.png";						//$NON-NLS-1$
	/**
	 * File path: icons/PI_Application_16x20.png
	 */
	public static final String IMG_PI_PHONE_ROM_FILE_16_20 = "PI_Application_16x20.png";				//$NON-NLS-1$
	/**
	 * File path: icons/PI_Application_20x20.png
	 */
	public static final String IMG_PI_PHONE_ROM_FILE_20_20 = "PI_Application_20x20.png";				//$NON-NLS-1$
	/**
	 * File path: icons/PI_Application_42x42.png
	 */
	public static final String IMG_PI_PHONE_ROM_FILE_42_42 = "PI_Application_42x42.png";				//$NON-NLS-1$
	/**
	 * File path: icons/PI_Application_64x64.png
	 */
	public static final String IMG_PI_PHONE_ROM_FILE_64_64 = "PI_Application_64x64.png";				//$NON-NLS-1$
	/**
	 * File path: icons/PI_Import_APP_42x42.png
	 */
	public static final String IMG_PI_IMPORT_APP_42_42 = "PI_Import_APP_42x42.png";					//$NON-NLS-1$
	/**
	 * File path: icons/PI_Import_ROM_42x42.png
	 */
	public static final String IMG_PI_IMPORT_ROM_42_42 = "PI_Import_ROM_42x42.png";					//$NON-NLS-1$
	/**
	 * File path: icons/PI_Import_NONE_42x42.png
	 */
	public static final String IMG_PI_IMPORT_NONE_100_42 = "PI_Import_NONE_100x42.png";					//$NON-NLS-1$
	/**
	 * File path: icons/PI_Meter_16x16.png
	 */
	public static final String IMG_PI_METER_16_16 = "PI_Meter_16x16.png";								//$NON-NLS-1$
	/**
	 * File path: icons/PI_Meter_20x20.png
	 */
	public static final String IMG_PI_METER_20_20 = "PI_Meter_20x20.png";								//$NON-NLS-1$
	/**
	 * File path: icons/
	 */
	public static final String IMG_PI_IMPORT_ROM_AND_APP_100_42 = "PI_Import_ROM_AND_APP_100x42.png";	//$NON-NLS-1$
	/**
	 * File path: icons/PI_Import_ROM_AND_APP_100x42.png
	 */
	public static final String IMG_PI_IMPORT_WIZARD_BANNER_75_66 = "PI_Import_Wizard_Banner_75x66.png";	//$NON-NLS-1$
	/**
	 * File path: icons/Resource_List.png
	 */
	public static final String IMG_RESOURCE_LIST_16_16 = "Resource_List.png";							//$NON-NLS-1$
	/**
	 * File path: icons/ROM_Image.png
	 */
	public static final String IMG_ROM_IMAGE_16_16 = "ROM_Image.png";									//$NON-NLS-1$
	/**
	 * File path: icons/ROM_Log.png
	 */
	public static final String IMG_ROM_LOG_16_16 = "ROM_Log.png";										//$NON-NLS-1$
	/**
	 * File path: icons/Start_Resource_Block.png
	 */
	public static final String IMG_START_RESOURCE_BLOCK_16_16 = "Start_Resource_Block.png";			//$NON-NLS-1$
	/**
	 * File path: icons/Symbian_OS_Bld_inf_MMP_file.png
	 */
	public static final String IMG_BLD_INF_MMP_55_45 = "Symbian_OS_Bld_inf_MMP_file.png";				//$NON-NLS-1$
	/**
	 * File path: icons/Symbian_OS_C++_Class.png
	 */
	public static final String IMG_SYMBIAN_CPP_CLASS_16_16 = "Symbian_OS_C++_Class.png";				//$NON-NLS-1$
	/**
	 * File path: icons/Symbian_OS_C++_Project.png
	 */
	public static final String IMG_SYMBIAN_CPP_PROJECT_16_16 = "Symbian_OS_C++_Project.png";			//$NON-NLS-1$
	/**
	 * File path: icons/System_Includes.png
	 */
	public static final String IMG_SYSTEM_INCLUDES_16_16 = "System_Includes.png";						//$NON-NLS-1$
	/**
	 * File path: icons/System_Resource_List.png
	 */
	public static final String IMG_SYSTEM_RESOURCE_LIST_16_16 = "System_Resource_List.png";			//$NON-NLS-1$
	/**
	 * File path: icons/TRK_Debug_Agent_16x20_Mask.png
	 */
	public static final String IMG_TRK_MASK_16_20 = "TRK_Debug_Agent_16x20_Mask.png";					//$NON-NLS-1$
	/**
	 * File path: icons/TRK_Debug_Agent_16x20.png
	 */
	public static final String IMG_TRK_16_20 = "TRK_Debug_Agent_16x20.png";							//$NON-NLS-1$
	/**
	 * File path: icons/TRK_Debug_Agent_20x20_Mask.png
	 */
	public static final String IMG_TRK_MASK_20_20 = "TRK_Debug_Agent_20x20_Mask.png";					//$NON-NLS-1$
	/**
	 * File path: icons/TRK_Debug_Agent_20x20.png
	 */
	public static final String IMG_TRK_20_20 = "TRK_Debug_Agent_20x20.png";							//$NON-NLS-1$
	/**
	 * File path: icons/TRK_Debug_Agent_42x42_Mask.png
	 */
	public static final String IMG_TRK_MASK_42_42 = "TRK_Debug_Agent_42x42_Mask.png";					//$NON-NLS-1$
	/**
	 * File path: icons/TRK_Debug_Agent_42x42.png
	 */
	public static final String IMG_TRK_42_42 = "TRK_Debug_Agent_42x42.png";							//$NON-NLS-1$
	/**
	 * File path: icons/TRK_Debug_Agent_64x64_Mask.png
	 */
	public static final String IMG_TRK_MASK_64_64 = "TRK_Debug_Agent_64x64_Mask.png";					//$NON-NLS-1$
	/**
	 * File path: icons/TRK_Debug_Agent_64x64.png
	 */
	public static final String IMG_TRK_64_64 = "TRK_Debug_Agent_64x64.png";							//$NON-NLS-1$
	/**
	 * File path: icons/Update_Project.png
	 */
	public static final String IMG_UPDATE_PROJECT_16_16 = "Update_Project.png";						//$NON-NLS-1$
	/**
	 * File path: icons/User_Includes.png
	 */
	public static final String IMG_USER_INCLUDES_16_16 = "User_Includes.png";							//$NON-NLS-1$

	/**
	 * Access to a shared image object, do not dispose of these images.
	 * @param key one of the IMG_* image names defined in this class
	 */
	Image getImage(String key);
	
	/**
	 * Access to a shared image descriptor. The caller is responsible 
	 * for disposing of any images created via this descriptor.
	 * @param key one of the IMG_* image names defined in this class
	 */
	ImageDescriptor getImageDescriptor(String key);
}
