package api_POJOS;

public class CreateUser_POJO {
	
	private String department;
	private String designation;
	private String dob;
	private String email;
	private String empName;
	private String experience;
	private String mobileNo;
	private String role;
	private String username;
	private String password;
	
	// Default constructor
	public CreateUser_POJO
	(String department, 
	String designation, 
	String dob, 
	String email, 
	String empName,
	String experience,
	String mobileNo,
	String role,
	String username,
	String password)
	{
		this.department = department;
        this.designation = designation;
        this.dob = dob;
        this.email = email;
        this.empName = empName;
        this.experience = experience;
        this.mobileNo = mobileNo;
        this.role =role;
        this.username = username;
        this.password =password;
		
	}
	
	public String getdepartment() {
		return department;
	}
	
	public String getdesignation() {
		return designation;
	}
	
	public String getdob() {
		return dob;
	}
	
	public String getemail() {
		return email;
	}
	
	public String getempName() {
		return empName;
	}
	
	public String getexp() {
		return experience;
	}
	
	public String getmobileNo() {
		return mobileNo;
	}
	
	public String getrole() {
		return role;
	}
	
	public String getusername() {
		return username;
	}
	
	public String getpassword() {
		return password;
	}
	
	

	

}
