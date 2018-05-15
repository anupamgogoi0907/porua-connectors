package porua.plugin.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import porua.plugin.editors.PoruaXMLEditor;
import porua.plugin.pojos.TagData;
import porua.plugin.transfer.TagDataTransfer;
import porua.plugin.utility.PluginConstants;

public class FlowComponent extends Group {
	private PoruaXMLEditor poruaXmlEditor;
	private Composite parent;

	public FlowComponent(Composite parent, PoruaXMLEditor poruaXmlEditor, String name) {
		super(parent, SWT.NONE);
		this.parent = parent;
		this.poruaXmlEditor = poruaXmlEditor;
		setText(name);
		initComponent();
	}

	private void initComponent() {
		makeLayout();
		makeDropTarget();
		pack();
	}

	private void makeDropTarget() {
		DropTarget dtGroup = new DropTarget(this, DND.DROP_MOVE);
		dtGroup.setTransfer(new Transfer[] { TagDataTransfer.getInstance() });
		dtGroup.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				TagData tagData = (TagData) event.data;
				if (!PluginConstants.TAG_FLOW.equals(tagData.getTag())) {
					poruaXmlEditor.modifyNamespaces(tagData);
					poruaXmlEditor.makeChangesToXml(getText(), tagData.getTagNamespacePrefix() + ":" + tagData.getTag());
					parent.pack();
				}
			}
		});
	}

	private void makeLayout() {
		RowLayout hLayout = new RowLayout(SWT.HORIZONTAL);
		hLayout.marginWidth = 100;
		hLayout.marginHeight = 100;
		hLayout.spacing = 100;

		setLayout(hLayout);
	}

	@Override
	protected void checkSubclass() {
	}

}
