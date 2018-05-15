package porua.plugin.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import porua.plugin.editors.PoruaXMLEditor;
import porua.plugin.pojos.TagData;
import porua.plugin.transfer.TagDataTransfer;
import porua.plugin.utility.PluginConstants;

public class FlowCanvas extends Composite {
	private PoruaXMLEditor poruaXMLEditor;

	public FlowCanvas(Composite parent, PoruaXMLEditor poruaXMLEditor) {
		super(parent, SWT.NONE);
		this.poruaXMLEditor = poruaXMLEditor;
		initComponent();
	}

	private void initComponent() {
		makeLayout();
		makeDropTarget();
		pack();
	}

	private void makeLayout() {
		setBackground(this.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		RowLayout vLayout = new RowLayout(SWT.VERTICAL);
		vLayout.spacing = 10;
		vLayout.marginHeight = 400;
		vLayout.marginWidth = 500;

		setLayout(vLayout);

	}

	private void makeDropTarget() {
		DropTarget dtComposite = new DropTarget(this, DND.DROP_MOVE);
		dtComposite.setTransfer(new Transfer[] { TagDataTransfer.getInstance() });
		dtComposite.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				TagData tagData = (TagData) event.data;
				if (PluginConstants.TAG_FLOW.equals(tagData.getTag())) {
					poruaXMLEditor.modifyNamespaces(tagData);
					poruaXMLEditor.makeChangesToXml(tagData.getTagNamespacePrefix() + ":" + tagData.getTag());
					pack();
				}
			}
		});
	}

	@Override
	protected void checkSubclass() {
	}
}
