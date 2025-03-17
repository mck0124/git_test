import java.lang.System;

public class Room {
    // field
    private int roomID;
    private String type;
    private int fullMember;
    private int price;
    private boolean isFull;
    
    // default ������
    public Room() {}

    // ������
    public Room(int roomID, String type, int fullMember, int price, boolean isFull) {
        super();
        this.roomID = roomID;
        this.type = type;
        this.fullMember = fullMember;
        this.price = price;
        this.isFull = isFull;
    }
    
	// �� ���� ���
    public void printRoomInfo() {
    	System.out.println("----- �� ���� -----");
        System.out.println("���ȣ: " + roomID);
        System.out.println("Ÿ��: " + type);
        System.out.println("�� ����: " + fullMember);
        System.out.println("����: " + price);
        System.out.println("���డ�ɿ���: " + (isFull ? "�Ұ�" : "����"));
        System.out.println();
    }
    
	// �� ���� ���� ���� Ȯ��
//    public boolean roomAvailable(String startDate, String endDate, String roomType, boolean isFull) { 
//        if (this.type.equals(roomType) && !this.isFull) {
//			printRoomInfo();
//            return true;
//        }
//        return false;
//    }
    
    public boolean isAvailable() {
    	return !isFull;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFullMember() {
        return fullMember;
    }

    public void setFullMember(int fullMember) {
        this.fullMember = fullMember;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean isFull) {
        this.isFull = isFull;
    }
}
