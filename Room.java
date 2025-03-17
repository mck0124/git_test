import java.lang.System;

public class Room {
    // field
    private int roomID;
    private String type;
    private int fullMember;
    private int price;
    private boolean isFull;
    
    // default 생성자
    public Room() {}

    // 생성자
    public Room(int roomID, String type, int fullMember, int price, boolean isFull) {
        super();
        this.roomID = roomID;
        this.type = type;
        this.fullMember = fullMember;
        this.price = price;
        this.isFull = isFull;
    }
    
	// 방 정보 출력
    public void printRoomInfo() {
    	System.out.println("----- 방 정보 -----");
        System.out.println("방번호: " + roomID);
        System.out.println("타입: " + type);
        System.out.println("방 정원: " + fullMember);
        System.out.println("가격: " + price);
        System.out.println("예약가능여부: " + (isFull ? "불가" : "가능"));
        System.out.println();
    }
    
	// 방 예약 가능 여부 확인
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
