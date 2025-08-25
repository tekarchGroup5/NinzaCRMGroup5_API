package api_POJOS;

public class R_CreateUserPOJO {
    
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
    
    
    // ✅ Full Constructor
    public R_CreateUserPOJO(String department, String designation, String dob, String email,
                             String empName, String experience, String mobileNo,
                             String role, String username, String password) {
        this.department = department;
        this.designation = designation;
        this.dob = dob;
        this.email = email;
        this.empName = empName;
        this.experience = experience;
        this.mobileNo = mobileNo;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    // ✅ Getters & Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

