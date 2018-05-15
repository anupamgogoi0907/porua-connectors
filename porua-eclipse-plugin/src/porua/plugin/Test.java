package porua.plugin;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import porua.plugin.components.AddConfigurationDialog;
import porua.plugin.pojos.TagData;
import porua.plugin.transfer.TagDataTransfer;
import porua.plugin.utility.PluginUtility;

public class Test {

	public static void display() {
		Display display = new Display();

		// Shell
		Shell shell = new Shell(display);
		shell.setLayout(new RowLayout());

		Label lblSource = new Label(shell, SWT.NONE);
		lblSource.setText("Drag Me");
		lblSource.pack();
		// Drag Source.
		DragSource ds = new DragSource(lblSource, DND.DROP_MOVE);
		ds.setTransfer(new Transfer[] { TagDataTransfer.getInstance() });
		ds.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				event.data = new TagData();
				System.out.println();

			}
		});

		// Drop target
		DropTarget dtGroup = new DropTarget(shell, DND.DROP_MOVE);
		dtGroup.setTransfer(new Transfer[] { TagDataTransfer.getInstance() });
		dtGroup.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				Object obj = event.data;
				System.out.println(obj);
			}
		});

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public static void makeDialog(Shell shell) {
		TagData pojo = new TagData();
		pojo.setProps(Arrays.asList("a", "b"));
		AddConfigurationDialog cd = new AddConfigurationDialog(shell, null, pojo);
		cd.open();
	}

	public static void makeImage(Shell shell) {
		ImageData imgData = new ImageData("/Users/ac-agogoi/git/ant-esb/porua-eclipse-plugin/resources/icons/ant_16.png");
		Image im = new Image(shell.getDisplay(), imgData);
		Label label = new Label(shell, SWT.NONE);
		label.setSize(100, 100);
		label.setImage(im);
		label.pack();
	}

	public static String read() throws Exception {
		String fileName = "/Users/ac-agogoi/git/ant-esb/porua-eclipse-plugin/resources/config/init.xml";
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		StringBuilder sb = new StringBuilder("");
		for (String s : lines) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		
		PluginUtility.loadTags();
	}

}
