package porua.plugin.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import porua.plugin.components.FlowCanvas;
import porua.plugin.components.FlowComponent;
import porua.plugin.components.PaletteComponent;
import porua.plugin.pojos.TagData;
import porua.plugin.utility.PluginConstants;
import porua.plugin.utility.PluginUtility;
import porua.plugin.views.PalettePropertyView;

public class PoruaXMLEditor extends MultiPageEditorPart implements IResourceChangeListener {
	public static int flowCount = 0;

	private TextEditor editor;

	private ScrolledComposite scrollComposite;
	private FlowCanvas composite;
	private Document xmlDoc = null;

	/**
	 * Creates a multi-page editor example.
	 */
	public PoruaXMLEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	/**
	 * Creates page 0 of the multi-page editor, which contains a text editor.
	 */
	void createEditor() {
		try {
			editor = new TextEditor();
			int index = addPage(editor, getEditorInput());
			setPageText(index, "xml");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), "Error creating nested text editor", null, e.getStatus());
		}
	}

	void createPreview() {
		initComposite();
		xmlToVisualComponent(readEditorData());

		// After adding components pack it.
		composite.pack();

		// Add page
		int index = addPage(scrollComposite);
		setPageText(index, "Preview");
	}

	public void initComposite() {
		scrollComposite = new ScrolledComposite(getContainer(), SWT.V_SCROLL | SWT.H_SCROLL);
		scrollComposite.setLayout(new FillLayout());
		composite = new FlowCanvas(scrollComposite, this);
		scrollComposite.setContent(composite);
	}

	public void xmlToVisualComponent(String editorData) {
		if (editorData != null && !editorData.equals("")) {
			xmlDoc = PluginUtility.xmlToDom(editorData);
			NodeList listFlow = xmlDoc.getElementsByTagName(findFlowTag());
			for (int i = 0; i < listFlow.getLength(); i++) {
				// Flow
				Node nodeFlow = listFlow.item(i);
				// Flow Elements.
				if (nodeFlow.getNodeType() == Node.ELEMENT_NODE) {
					NamedNodeMap mapAtt = nodeFlow.getAttributes();
					Node nodeAtt = mapAtt.getNamedItem("id");
					if (nodeAtt != null) {
						Group group = addFlowGroupToComposite(nodeAtt.getTextContent());
						NodeList listFlowElm = nodeFlow.getChildNodes();
						for (int j = 0; j < listFlowElm.getLength(); j++) {
							Node nodeFlowElm = listFlowElm.item(j);
							if (nodeFlowElm.getNodeType() == Node.ELEMENT_NODE) {
								addPaletteComponentToGroup(group, nodeFlowElm.getNodeName(), j);
							}
						}
					}
				}

			}
		}
	}

	public String findFlowTag() {
		Node root = xmlDoc.getFirstChild();
		NodeList listNode = root.getChildNodes();
		for (int i = 0; i < listNode.getLength(); i++) {
			Node n = listNode.item(i);
			String name = n.getNodeName();
			if (name.contains(PluginConstants.TAG_FLOW)) {
				return name;
			}
		}
		return "";
	}

	public Group addFlowGroupToComposite(String name) {
		Group group = new FlowComponent(composite, this, name);
		flowCount++;
		return group;
	}

	public void addPaletteComponentToGroup(Group parent, String tagComponent, Integer index) {
		new PaletteComponent(parent, this, tagComponent, index);
	}

	public Node findFlowNodeInDom(String groupName) {
		NodeList listFlow = xmlDoc.getElementsByTagName(findFlowTag());
		for (int i = 0; i < listFlow.getLength(); i++) {
			// Flow
			Node nodeFlow = listFlow.item(i);
			NamedNodeMap mapAtt = nodeFlow.getAttributes();
			Node attId = mapAtt.getNamedItem("id");
			if (groupName.equals(attId.getTextContent())) {
				return nodeFlow;
			}
		}
		return null;
	}

	public void makeChangesToXml(String tagFlow) {
		Element elm = xmlDoc.createElement(tagFlow);
		elm.setAttribute("id", "flow-" + (++flowCount));
		xmlDoc.getFirstChild().appendChild(elm);
		redrawComposite();
	}

	public void makeChangesToXml(String targetFlowName, String tagComponent) {
		Node nodeFlow = findFlowNodeInDom(targetFlowName);
		Element elm = xmlDoc.createElement(tagComponent);
		nodeFlow.appendChild(elm);
		redrawComposite();
	}

	public void redrawComposite() {
		redrawComposite(PluginUtility.domToXml(xmlDoc));
	}

	public void redrawComposite(String xml) {
		deleteCompositeControls();
		xmlToVisualComponent(xml);
		writeEditorData(xml);
		composite.layout();
		composite.pack();
	}

	public String readEditorData() {
		IEditorInput input = editor.getEditorInput();
		IDocument doc = editor.getDocumentProvider().getDocument(input);
		String content = doc.get();
		return content;
	}

	public void writeEditorData(String xml) {
		IEditorInput input = editor.getEditorInput();
		IDocument doc = editor.getDocumentProvider().getDocument(input);
		doc.set(xml);
	}

	public void deleteCompositeControls() {
		for (Control c : composite.getChildren()) {
			c.dispose();
		}
	}

	public void modifyNamespaces(TagData tagData) {
		NamedNodeMap mapAtt = xmlDoc.getFirstChild().getAttributes();
		boolean nsFound = false;
		for (int i = 0; i < mapAtt.getLength(); i++) {
			Node n = mapAtt.item(i);
			if (tagData.getTagNamespace().equals(n.getTextContent())) {
				nsFound = true;
				break;
			}
		}
		if (!nsFound) {
			((Element) xmlDoc.getFirstChild()).setAttribute("xmlns:" + tagData.getTagNamespacePrefix(), tagData.getTagNamespace());
			String schemaLoc = xmlDoc.getFirstChild().getAttributes().getNamedItem("xsi:schemaLocation").getTextContent();
			schemaLoc = schemaLoc + " " + tagData.getTagNamespace() + " " + tagData.getTagSchemaLocation();
			xmlDoc.getFirstChild().getAttributes().getNamedItem("xsi:schemaLocation").setTextContent(schemaLoc);
		}

	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		initComposite();
		createEditor();
		createPreview();
	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this
	 * <code>IWorkbenchPart</code> method disposes all nested editors. Subclasses
	 * may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	/**
	 * Saves the multi-page editor's document as another file. Also updates the text
	 * for page 0's tab, and updates this multi-page editor's input to correspond to
	 * the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method checks
	 * that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if(newPageIndex==0) {
			IViewPart part=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(PalettePropertyView.ID);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(part);
		}
		else if (newPageIndex == 1) {
			redrawComposite(readEditorData());
		}
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i < pages.length; i++) {
						if (((FileEditorInput) editor.getEditorInput()).getFile().getProject().equals(event.getResource())) {
							IEditorPart editorPart = pages[i].findEditor(editor.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

	public Document getXmlDoc() {
		return xmlDoc;
	}

}
