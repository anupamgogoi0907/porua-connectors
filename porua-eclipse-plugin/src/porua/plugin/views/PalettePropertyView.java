package porua.plugin.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import porua.plugin.components.AddConfigurationDialog;
import porua.plugin.editors.PoruaXMLEditor;
import porua.plugin.pojos.ComponentData;
import porua.plugin.pojos.TagData;
import porua.plugin.utility.PluginUtility;

public class PalettePropertyView extends ViewPart implements IViewData {
	public static final String ID = "porua.plugin.views.PalettePropertyView";

	private PoruaXMLEditor poruaXmlEditor;
	private Composite parent;
	private ComponentData compData;
	private List<Text> listTextInput;
	private String comboSelectedConfig;

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
	}

	@Override
	public void setFocus() {
		this.parent.setFocus();
	}

	@Override
	public void setViewData(PoruaXMLEditor poruaXmlEditor, Object data) {
		this.poruaXmlEditor = poruaXmlEditor;
		this.compData = (ComponentData) data;
		this.listTextInput = new ArrayList<>();
		deleteControls();
		getTagData();
	}

	private void deleteControls() {
		for (Control c : this.parent.getChildren()) {
			c.dispose();
		}
		this.parent.pack();
	}

	private void getTagData() {
		Node nodeFlow = poruaXmlEditor.findFlowNodeInDom(compData.getGroupName());
		Node nodeComp = nodeFlow.getChildNodes().item(compData.getIndex());
		TagData tagData = PluginUtility.getTagDataByTagName(nodeComp.getNodeName());
		if (tagData != null) {
			drawTagAttributes(nodeComp, tagData);
		}
	}

	private Group makeHorizontalGroup() {
		RowLayout layout = new RowLayout();
		layout.type = SWT.HORIZONTAL;
		layout.spacing = 10;
		layout.center = true;

		Group group = new Group(parent, SWT.NONE);
		group.setLayout(layout);
		return group;
	}

	private void makeLabelAndText(String property, String text) {
		Group group = makeHorizontalGroup();

		Label label = new Label(group, SWT.NONE);
		label.setText(property);
		label.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
		label.pack();

		Text textInput = new Text(group, SWT.NONE);
		textInput.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		textInput.setData(property);
		textInput.setText(text);
		textInput.pack();
		textInput.setLayoutData(new RowData(400, 20));
		listTextInput.add(textInput);

		group.pack();
	}

	private void loadComboAndSetValue(String tag, String configPropValue, Combo comboDropDown) {
		comboDropDown.setItems(new String[] {});
		comboSelectedConfig = "";
		NodeList nl = poruaXmlEditor.getXmlDoc().getElementsByTagName(tag);
		if (nl != null && nl.getLength() != 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				NamedNodeMap mapConfigNodeAtt = nl.item(i).getAttributes();
				Node nodeAtt = mapConfigNodeAtt.getNamedItem("name");
				comboDropDown.add(nodeAtt.getTextContent());
				comboDropDown.pack();
			}
			if (configPropValue != null) {
				int i = 0;
				for (String s : comboDropDown.getItems()) {
					if (s.equals(configPropValue)) {
						comboDropDown.select(i);
						comboSelectedConfig = comboDropDown.getItem(i);
						break;
					}
					i++;
				}
			}

		}
	}

	private void makeLabelAndCombo(Map<String, TagData> mapConfig, String previousConfig) {
		try {
			if (mapConfig != null && mapConfig.size() != 0) {
				String prop = (String) mapConfig.keySet().toArray()[0];
				TagData tagData = mapConfig.get(prop);
				String tag = tagData.getTagNamespacePrefix() + ":" + tagData.getTag();
				Group group = makeHorizontalGroup();

				// Label
				Label label = new Label(group, SWT.NONE);
				label.setText(prop);
				label.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
				label.pack();

				// Combo
				Combo comboDropDown = new Combo(group, SWT.DROP_DOWN | SWT.BORDER);
				comboDropDown.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Combo c = (Combo) e.widget;
						comboSelectedConfig = c.getItem(c.getSelectionIndex());

					}
				});
				loadComboAndSetValue(tag, previousConfig, comboDropDown);

				// Add
				Button btnAdd = new Button(group, SWT.PUSH);
				btnAdd.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						AddConfigurationDialog dlg = new AddConfigurationDialog(parent.getShell(), poruaXmlEditor, tagData);
						dlg.open();
						loadComboAndSetValue(tag, previousConfig, comboDropDown);
					}
				});
				btnAdd.setText("+");
				btnAdd.pack();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void drawTagAttributes(Node nodeComp, TagData tagData) {
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		layout.spacing = 10;
		parent.setLayout(layout);

		NamedNodeMap mapNodeCompAtt = nodeComp.getAttributes();

		// Connector Attributes ## Label & Text.
		if (tagData.getProps() != null && tagData.getProps().size() != 0) {
			for (int i = 0; i < tagData.getProps().size(); i++) {
				String property = tagData.getProps().get(i);

				Node nodeCompAtt = null;
				if (mapNodeCompAtt != null && mapNodeCompAtt.getLength() != 0) {
					nodeCompAtt = mapNodeCompAtt.getNamedItem(property);
				}
				makeLabelAndText(property, nodeCompAtt == null ? "" : nodeCompAtt.getTextContent());
			}

		}
		// Configuration Attributes ## Label & Combo.
		String configPropName = getConfigName(tagData);
		if (configPropName != null) {
			Node nodeConfigAtt = mapNodeCompAtt.getNamedItem(configPropName);
			makeLabelAndCombo(tagData.getConfig(), nodeConfigAtt == null ? null : nodeConfigAtt.getTextContent());
		}

		// Save
		if (tagData.getProps() != null || tagData.getConfig() != null) {
			Button btnSave = new Button(parent, SWT.NONE);
			btnSave.setText("Save");
			btnSave.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listTextInput.forEach((t) -> {
						((Element) nodeComp).setAttribute(t.getData().toString(), t.getText());
					});

					if (configPropName != null) {
						if (comboSelectedConfig != null) {
							((Element) nodeComp).setAttribute(configPropName, comboSelectedConfig);
						} else {
							MessageDialog.openError(parent.getShell(), "Warning", "Configuration is compulsory.");
						}
					}
					poruaXmlEditor.redrawComposite();
				}

			});
		}
		parent.pack();
	}

	private String getConfigName(TagData tagData) {
		if (tagData.getConfig() != null && tagData.getConfig().size() != 0) {
			return (String) tagData.getConfig().keySet().toArray()[0];
		} else {
			return null;
		}
	}
}
