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

	// default ������
	public Customer() {}
    
	// ������
    public Customer(String name, String phoneNum) {
        super();
        this.name = name;
        this.phoneNum = phoneNum;
    }
    
	// �� ���� ����
    public void saveInfo() {
        try {
            String fileName;
            visitCount++;
            
            if (visitCount == 1) {
                fileName = "�ű԰� ����Ʈ.txt";
            } else if (visitCount == 2) {
                fileName = "��湮�� ����Ʈ.txt";
            } else {
                fileName = "�ܰ�� ����Ʈ.txt";
            }
            
            writer = new FileWriter(fileName, true);
            writer.write("�̸�: " + name + ", ��ȭ��ȣ: " + phoneNum + ", �����ȣ: " + reservID.toString() + ", ���� �ο�: " + members + "\n");
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
    
	// �� ���� �Է�
    public void inputInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("�̸�: ");
        name = sc.nextLine();
        System.out.print("��ȭ��ȣ (ex. 010-1234-5678): ");
        while(true) {
        	phoneNum = sc.nextLine();
        	if (phoneNum.matches("\\d{3}-\\d{4}-\\d{4}")) break; //��ȭ��ȣ ������ �ùٸ��� Ȯ��
        	else {
        		System.out.print("��ȭ��ȣ ������ �߸��Ǿ����ϴ�.\n�ٽ� �Է��� �ֽʽÿ�: ");
        	}
        }
        
        System.out.print("���� �ο�: ");
        try {
        	members = Integer.parseInt(sc.nextLine());
        	if (members <= 0) throw new NumberFormatException(); //�ο� ���ڰ� ������� Ȯ��
		} catch (Exception e) {
			System.out.println("��ȿ���� ���� �ο��Դϴ�. 1������ �����˴ϴ�.");
			members = 1;
		}
        
    }
    
	// �� ���� ���
    public void printInfo() {
        System.out.println("�̸�: " + name);
        System.out.println("��ȭ��ȣ: " + phoneNum);
        System.out.println("�����ȣ: " + reservID);
        System.out.println("���� �ο�: " + members);
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
