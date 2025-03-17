import java.lang.System;
import java.util.List;
import java.util.ArrayList;

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
    
    public static List<Room> generateInitialRooms() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(101, "Single", 2, 50000, false));
        rooms.add(new Room(102, "Single", 2, 50000, false));
        rooms.add(new Room(103, "Single", 2, 50000, false));
        rooms.add(new Room(201, "Double", 4, 80000, false));
        rooms.add(new Room(202, "Double", 4, 80000, false));
        rooms.add(new Room(203, "Double", 4, 80000, false));
        rooms.add(new Room(401, "Deluxe", 8, 200000, false));
        rooms.add(new Room(402, "Deluxe", 8, 200000, false));
        rooms.add(new Room(403, "Deluxe", 8, 200000, false));
        return rooms;
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
