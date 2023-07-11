import java.io.Serializable;

public class Customer implements Serializable{
	private int id;
	private String name;
	private int age;
	private int mobile;
	private String passno;
	private String dob;
	private Account account;
	public static int idCount = 100;
	
	public Customer(String name, int age, int mobile, String passno) {
		super();
		this.id = idCount++;
		this.name = name;
		this.age = age;
		this.mobile = mobile;
		this.passno = passno;
	}
	
	public Customer(int id, String name, int age, int mobile, String passno) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.mobile = mobile;
		this.passno = passno;
	}
	
	public Customer(String name, int age, int mobile, String passno, String dob) {
		super();
		this.id = idCount++;
		this.name = name;
		this.age = age;
		this.mobile = mobile;
		this.passno = passno;
		this.dob = dob;
	}
	
	public Customer(int id, String name, int age, int mobile, String passno, String dob, Account account) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.mobile = mobile;
		this.passno = passno;
		this.dob = dob;
		this.account = account;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getMobile() {
		return mobile;
	}

	public void setMobile(int mobile) {
		this.mobile = mobile;
	}

	public String getPassno() {
		return passno;
	}

	public void setPassno(String passno) {
		this.passno = passno;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", age=" + age + ", mobile=" + mobile + ", passno=" + passno
				+ ", dob=" + dob + ", account=" + account + "]";
	}

	

	
	
}

