package com.nokia.carbide.cpp.internal.api.sdk.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv2;
import com.nokia.carbide.cpp.sdk.core.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

public class SBSv2ConfigManager extends Composite {

	private CheckboxTableViewer sbsConfigTableViewer;
	private List<ISBSv2BuildContext> sbsContexts;
	List<String> globalBuildAliasList = new ArrayList<String>();
	List<ISBSv2BuildContext> sbsBuildContexts = new ArrayList<ISBSv2BuildContext>();
	
	private class SBSv2BuildContextLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			ISBSv2BuildContext sdk = (ISBSv2BuildContext) element;
			switch (columnIndex) {
			case 1:
				return sdk.getSBSv2Alias();
			case 2:
				return sdk.getBuildVariationName();
			case 3:
				return sdk.getDisplayString();
			default:
				return "";
			}
		}
	}
	
	private class BuildAliasEditingSupport extends EditingSupport {
		private BuildAliasComboEditor editor;

		public BuildAliasEditingSupport(ColumnViewer viewer) {
			super(viewer);
			editor = new BuildAliasComboEditor((Composite) viewer.getControl());
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
			context = new BuildContextSBSv2(context.getSDK(), "ARMV5", "UDEB", value.toString(), "New Display name", ISBSv2BuildContext.BUILDER_ID + ".arvm5_udeb." + context.getSDK().getUniqueId());
			// TODO: Handle edit and store data if alias changes
			getViewer().refresh();
		}
	}

	class BuildAliasContentProvider implements IStructuredContentProvider {

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public Object[] getElements(Object inputElement) {
			return globalBuildAliasList.toArray();
		}	
		
	}
	
	private class BuildAliasComboEditor extends ComboBoxViewerCellEditor {
		private Text text;

		public BuildAliasComboEditor(Composite parent) {
			super(parent);
		}

		
		@Override
		protected Control createControl(Composite parent) {
			Control control = super.createControl(parent);
			this.setContenProvider(new BuildAliasContentProvider());
			this.setInput(globalBuildAliasList);
			return control;
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
		List<ISBSv2BuildContext> contexts = new ArrayList<ISBSv2BuildContext>();
		
		// TODO: DUMMY TEST DATA
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDKList().get(0);
		ISBSv2BuildContext testContext = new BuildContextSBSv2(sdk, "ARMV5", "DEBUG", "arvm5_udeb", "ARMV5 Debug", ISBSv2BuildContext.BUILDER_ID + ".arvm5_udeb." + sdk.getUniqueId());
		contexts.add(testContext);
		testContext = new BuildContextSBSv2(sdk, "ARMV5", "Release", "arvm5_urel", "ARMV5 Release", ISBSv2BuildContext.BUILDER_ID + ".arvm5_udeb." + sdk.getUniqueId());
		contexts.add(testContext);
		
		return contexts;
	}

	private void setCheckedElements() {
		// TODO Auto-generated method stub
		
	}

}
