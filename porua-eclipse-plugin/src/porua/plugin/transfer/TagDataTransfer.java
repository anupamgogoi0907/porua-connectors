package porua.plugin.transfer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import porua.plugin.pojos.TagData;

public class TagDataTransfer extends ByteArrayTransfer {
	protected static final String TYPE_NAME = "tag-transfer-format";
	protected static final int TYPE_ID = registerType(TYPE_NAME);
	protected static TagDataTransfer instance;

	public static TagDataTransfer getInstance() {
		if (instance == null) {
			instance = new TagDataTransfer();
		}

		return instance;
	}

	@Override
	public void javaToNative(Object object, TransferData transferData) {
		try {
			if (object instanceof TagData) {
				// TagData tagData = (TagData) object;
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ObjectOutput out = new ObjectOutputStream(os);
				out.writeObject(object);
				out.flush();
				super.javaToNative(os.toByteArray(), transferData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Object nativeToJava(TransferData transferData) {
		try {
			byte[] data = (byte[]) super.nativeToJava(transferData);
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			ObjectInput in = new ObjectInputStream(is);
			return in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected int[] getTypeIds() {
		return new int[] { TYPE_ID };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { TYPE_NAME };
	}

}
