package porua.plugin.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Element;

import porua.plugin.editors.PoruaXMLEditor;
import porua.plugin.pojos.TagData;

public class AddConfigurationDialog extends Dialog {
	private PoruaXMLEditor poruaXmlEditor;
	private TagData tagData;
	private List<Text> listTextInput;

	public AddConfigurationDialog(Shell parentShell, PoruaXMLEditor poruaXmlEditor, TagData tagData) {
		super(parentShell);
		this.poruaXmlEditor = poruaXmlEditor;
		this.tagData = tagData;
		this.listTextInput = new ArrayList<>();
	}

	private Group makeHorizontalGroup(Composite parent) {
		RowLayout layout = new RowLayout();
		layout.type = SWT.HORIZONTAL;
		layout.spacing = 10;
		layout.center = true;

		Group group = new Group(parent, SWT.NONE);
		group.setLayout(layout);
		return group;
	}

	private void makeLabelAndText(Composite parent, String property, String text) {
		Group group = makeHorizontalGroup(parent);

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

	@Override
	protected Control createDialogArea(Composite container) {
		Composite parent = (Composite) super.createDialogArea(container);
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		layout.spacing = 10;
		parent.setLayout(layout);

		if (tagData.getProps() != null && tagData.getProps().size() != 0) {
			for (String prop : tagData.getProps()) {
				makeLabelAndText(parent, prop, "");
			}
		}

		return parent;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Configuration");
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	@Override
	protected void okPressed() {
		poruaXmlEditor.modifyNamespaces(tagData);
		Element elm = poruaXmlEditor.getXmlDoc().createElement(tagData.getTagNamespacePrefix() + ":" + tagData.getTag());
		listTextInput.stream().forEach((t) -> {
			elm.setAttribute(t.getData().toString(), t.getText());
		});
		poruaXmlEditor.getXmlDoc().getFirstChild().appendChild(elm);
		poruaXmlEditor.redrawComposite();
		this.close();
	}

}
