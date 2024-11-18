package crm_app07.entity;

public class TaskEntity {
	private int id;
	private String name;
	private String description;
	private String startDate;
	private String endDate;
	private int assigneeID;
	private int projectID;
	private int statusID;

	private String assigneeName;
	private String assigneeEmail;
	private String projectName;
	private String statusName;

	public TaskEntity(int id, String name, String startDate, String endDate, int projectID) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectID = projectID;
	}

	public TaskEntity() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getAssigneeID() {
		return assigneeID;
	}

	public void setAssigneeID(int assigneeID) {
		this.assigneeID = assigneeID;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getAssigneeEmail() {
		return assigneeEmail;
	}

	public void setAssigneeEmail(String assigneeEmail) {
		this.assigneeEmail = assigneeEmail;
	}

}
