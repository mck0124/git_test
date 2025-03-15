import java.io.FileWriter;
import java.util.Scanner;
import java.lang.System;

public class Customer {
	// fields
    private String name;
    private String phoneNum;
    private Reservation reservID;
    private int members;
    private int visitCount = 0;
    
    FileWriter writer = null;

	// default 생성자
	public Customer() {}
    
	// 생성자
    public Customer(String name, String phoneNum) {
        super();
        this.name = name;
        this.phoneNum = phoneNum;
    }
    
	// 고객 정보 저장
    public void saveInfo() {
        try {
            String fileName;
            visitCount++;
            
            if (visitCount == 1) {
                fileName = "신규고객 리스트.txt";
            } else if (visitCount == 2) {
                fileName = "재방문고객 리스트.txt";
            } else {
                fileName = "단골고객 리스트.txt";
            }
            
            writer = new FileWriter(fileName, true);
            writer.write("이름: " + name + ", 전화번호: " + phoneNum + ", 예약번호: " + reservID.toString() + ", 예약 인원: " + members + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e2) {
            }
        }
    }
    
	// 고객 정보 입력
    public void inputInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("이름: ");
        name = sc.nextLine();
        System.out.println("전화번호: ");
        phoneNum = sc.nextLine();
        System.out.println("예약 인원: ");
        members = Integer.parseInt(sc.nextLine());
    }
    
	// 고객 정보 출력
    public void printInfo() {
        System.out.println("이름: " + name);
        System.out.println("전화번호: " + phoneNum);
        System.out.println("예약번호: " + reservID);
        System.out.println("예약 인원: " + members);
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

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }
}
