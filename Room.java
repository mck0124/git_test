public class Room {
	private int roomID;
	private String type;
	private int fullMember;
	private int price;
	private boolean isFull;
	
	public Room(int roomID, String type, int fullMember, int price, boolean isFull) {
		super();
		this.roomID = roomID;
		this.type = type;
		this.fullMember = fullMember;
		this.price = price;
		this.isFull = isFull;
	}
	
	
	public void printRoomInfo() {
		
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
