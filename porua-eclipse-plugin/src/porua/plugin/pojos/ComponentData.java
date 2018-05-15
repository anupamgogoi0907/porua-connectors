package porua.plugin.pojos;

public class ComponentData {

	private String groupName;
	private Integer index;

	public ComponentData(String groupName, Integer index) {
		super();
		this.groupName = groupName;
		this.index = index;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
