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
    public Customer() {
    }

    // ������
    public Customer(String name, String phoneNum) {
        super();
        this.name = name;
        this.phoneNum = phoneNum;
    }

    // �� ���� �Է�
    public void inputInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== �� ���� �Է� =====");
        System.out.print("�̸�: ");
        name = sc.nextLine();
        System.out.print("��ȭ��ȣ (ex. 010-1234-5678): ");
        while (true) {
            phoneNum = sc.nextLine();
            if (phoneNum.matches("\\d{3}-\\d{4}-\\d{4}"))
                break; // ��ȭ��ȣ ������ �ùٸ��� Ȯ��
            else {
                System.out.print("��ȭ��ȣ ������ �߸��Ǿ����ϴ�.\n�ٽ� �Է��� �ֽʽÿ�: ");
            }
        }

        System.out.print("���� �ο�: ");
        try {
            members = Integer.parseInt(sc.nextLine());
            if (members <= 0)
                throw new NumberFormatException(); // �ο� ���ڰ� ������� Ȯ��
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
