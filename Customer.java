import java.io.FileWriter;
import java.util.Scanner;

public class Customer {
	private String name;
	private String phoneNum;
	private Reservation reservID;
	
	FileWriter writer = null;
	
	public Customer(String name, String phoneNum, Reservation reservID) {
		super();
		this.name = name;
		this.phoneNum = phoneNum;
		this.reservID = reservID;
	}
	
	public void saveInfo() {
		try {
			writer = new FileWriter("customerInfo.txt", true);
			writer.write("이름: " + name + ", 전화번호: " + phoneNum + ", 예약번호: " +reservID.toString() + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception e2) {
			}
		}
	}
	
	public void inputInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("이름: ");
		name = sc.nextLine();
		System.out.println("전화번호: ");
		phoneNum = sc.nextLine();
	}
	
	public void printInfo() {
		System.out.println("이름: " + name);
		System.out.println("전화번호: " + phoneNum);
		System.out.println("예약번호: " + reservID);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Reservation getReservID() {
		return reservID;
	}

	public void setReservID(Reservation reservID) {
		this.reservID = reservID;
	}
	
	
	
}
