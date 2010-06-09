package com.nokia.carbide.cpp.internal.api.sdk.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv2;
import com.nokia.carbide.cpp.sdk.core.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.ui.TextAndDialogCellEditor;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

public class SBSv2ConfigManager extends Composite {

	private CheckboxTableViewer sbsConfigTableViewer;
	private List<ISBSv2BuildContext> sbsContexts;
	List<String> globalBuildAliasList = new ArrayList<String>();
	List<String> globalProductList = new ArrayList<String>();
	List<ISBSv2BuildContext> sbsBuildContexts = new ArrayList<ISBSv2BuildContext>();
	
	private class SBSv2BuildContextLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			ISBSv2BuildContext buildContext = (ISBSv2BuildContext) element;
			switch (columnIndex) {
			case 1:
				return buildContext.getSBSv2Alias();
			case 2:
				return buildContext.getBuildVariationName();
			case 3:
				return buildContext.getDisplayString();
			default:
				return "";
			}
		}
	}
	
	private class BuildAliasEditingSupport extends EditingSupport {
		private BuildAliasCellEditor editor;

		public BuildAliasEditingSupport(ColumnViewer viewer) {
			super(viewer);
			editor = new BuildAliasCellEditor((Composite) viewer.getControl());
		}

		@Override
		protected boolean canEdit(Object element) {
			if (element instanceof ISBSv2BuildContext) {
				return true;
			}
			return false;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected Object getValue(Object element) {
			ISBSv2BuildContext context = (ISBSv2BuildContext) element;
			return context.getSBSv2Alias();
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (value == null)
				return;
			
			ISBSv2BuildContext context = (ISBSv2BuildContext) element;
			
			// TODO: Use real data. Here we can find an existing build context that works, or create a new one if a variant was applied
			// We would need to run the query here to fill out extra params
			String alias = value.toString();
			String aliasTokens[] = alias.split("_");
			String platform = aliasTokens[0].toUpperCase();
			String target = aliasTokens[1].toUpperCase();
			String displayName = platform + " " + target;
			String configID = ISBSv2BuildContext.BUILDER_ID + "." + value.toString() +  "." + context.getSDK().getUniqueId();
			ISBSv2BuildContext newContext = new BuildContextSBSv2(context.getSDK(), platform, target, alias, displayName, configID);

			if (contextExists(newContext)){
				return;
			}
			
						// TODO: Handle edit and store data if alias changes
			sbsBuildContexts.add(newContext);
			sbsBuildContexts.remove(sbsBuildContexts.indexOf(context));
			addBuildConfigurationTableItems();
			getViewer().refresh();
		}

		private boolean contextExists(ISBSv2BuildContext context) {
			for (ISBSv2BuildContext prefContext : sbsBuildContexts){
				if (prefContext.equals(context)){
					return true;
				}
			}
			return false;
		}
	}
	
	private class BuildAliasCellEditor extends TextAndDialogCellEditor {
		private Button button;
		private Text text;

		public BuildAliasCellEditor(Composite parent) {
			super(parent);
		}
		
		@Override
		protected Control createControl(Composite parent) {
			Control control = super.createControl(parent);
			button = getButton();
			button.setText("..."); //$NON-NLS-1$
			return control;
		}

		@Override
		protected Object openDialogBox(Control cellEditorWindow) {
			DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
			BrowseDialogUtils.initializeFrom(dialog, text);
			return dialog.open();
		}
	}
	
	public SBSv2ConfigManager(Composite parent) {
		super(parent, SWT.NONE);
	}

	public void createControls() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		setLayout(gridLayout);
		
		initData();
		
		sbsConfigTableViewer = CheckboxTableViewer.newCheckList(this, 
				SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		createSBSConfigTable();
	
		addBuildConfigurationTableItems();	
	}

	private void initData() {
		
		////////////////////TODO: DUMMY DATA. GET FROM RAPTOR QUERY
		globalBuildAliasList.add("armv5_udeb");
		globalBuildAliasList.add("armv5_urel");
		globalBuildAliasList.add("winscw_udeb");
		globalBuildAliasList.add("winscw_urel");
		globalBuildAliasList.add("tools2_deb");
		globalBuildAliasList.add("tools2_rel");
		/////////////////////
		
		/////// TODO: DUMMY SBSv2 BUILD CONTEXTS FOR TABLE
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDKList().get(0);
		
		ISBSv2BuildContext testContext = new BuildContextSBSv2(sdk, "ARMV5", "DEBUG", "arvm5_udeb", "ARMV5 Debug", ISBSv2BuildContext.BUILDER_ID + ".arvm5_udeb." + sdk.getUniqueId());
		sbsBuildContexts.add(testContext);
		
		testContext = new BuildContextSBSv2(sdk, "ARMV5", "Release", "arvm5_urel", "ARMV5 Release", ISBSv2BuildContext.BUILDER_ID + ".arvm5_udeb." + sdk.getUniqueId());
		sbsBuildContexts.add(testContext);
		
		testContext = new BuildContextSBSv2(sdk, "ARMV5", "Release", "arvm5_urel_gcce", "ARMV5 Release", ISBSv2BuildContext.BUILDER_ID + ".arvm5_udeb_gcce." + sdk.getUniqueId());
		sbsBuildContexts.add(testContext);
		
		testContext = new BuildContextSBSv2(sdk, "ARMV5", "Release", "arvm5_urel_gcce", "ARMV5 Release", ISBSv2BuildContext.BUILDER_ID + ".arvm5_udeb_gcce." + sdk.getUniqueId());
		sbsBuildContexts.add(testContext);
		
		testContext = new BuildContextSBSv2(sdk, "WINSCW", "Release", "winscw_urel", "WINSCW Release", ISBSv2BuildContext.BUILDER_ID + ".winscw_urel." + sdk.getUniqueId());
		sbsBuildContexts.add(testContext);
		
		testContext = new BuildContextSBSv2(sdk, "WINSCW", "Debug", "winsw_udeb", "WINSCW Debug", ISBSv2BuildContext.BUILDER_ID + ".winscw_udeb." + sdk.getUniqueId());
		sbsBuildContexts.add(testContext);
		
		
		
		
	}

	private void createSBSConfigTable() {
		final Table table = sbsConfigTableViewer.getTable();
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = 425;
		gridData.heightHint = table.getItemHeight() * 10;
		table.setLayoutData(gridData);
		table.setHeaderVisible(true);
		table.setLinesVisible(false);

		
		TableViewerColumn enabledCol = new TableViewerColumn(sbsConfigTableViewer, SWT.LEFT);
		enabledCol.getColumn().setText("Enabled"); //$NON-NLS-1$
		enabledCol.getColumn().setWidth(50);
		
		TableViewerColumn aliasCol = new TableViewerColumn(sbsConfigTableViewer, SWT.LEFT);
		aliasCol.setEditingSupport(new BuildAliasEditingSupport(sbsConfigTableViewer)); //$NON-NLS-1$
		aliasCol.getColumn().setText("SBS Configuration");
		aliasCol.getColumn().setWidth(160);
		
		TableViewerColumn variantCol = new TableViewerColumn(sbsConfigTableViewer, SWT.LEFT);
		//aliasCol.setEditingSupport(new IdEditingSupport(sbsConfigTableViewer)); //$NON-NLS-1$
		variantCol.getColumn().setText("Product Variant");
		variantCol.getColumn().setWidth(120);
		
		TableViewerColumn displaNameCol = new TableViewerColumn(sbsConfigTableViewer, SWT.LEFT);
		//aliasCol.setEditingSupport(new IdEditingSupport(sbsConfigTableViewer)); //$NON-NLS-1$
		displaNameCol.getColumn().setText("Display Name");
		displaNameCol.getColumn().setWidth(160);
		
	}
	
	private void addBuildConfigurationTableItems() {
		sbsConfigTableViewer.setLabelProvider(new SBSv2BuildContextLabelProvider());
		sbsConfigTableViewer.setContentProvider(new ArrayContentProvider());
		sbsContexts = getGlobalSBSContexts();
		sbsConfigTableViewer.setInput(sbsContexts.toArray());
		sbsConfigTableViewer.getTable().setToolTipText("TODO Tooltip"); //$NON-NLS-1$
		setCheckedElements();
//		addSDKTableViewerListeners();
		if (sbsContexts == null || sbsContexts.size() == 0){
//			statusError(Messages.getString("SDKPreferencePage.No_SDKs_Available_Message")); //$NON-NLS-1$
		}
	}

	// TODO: Get context list from SDKManager for SBSv2
	private List<ISBSv2BuildContext> getGlobalSBSContexts() {
		
		return sbsBuildContexts;
	}

	private void setCheckedElements() {
		// TODO Auto-generated method stub
		
	}

}
