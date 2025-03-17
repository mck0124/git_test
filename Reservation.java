import java.time.LocalDateTime;
import java.lang.System;

public class Reservation {
    // ?•„?“œ
    // ê³ ê°, ?˜ˆ?•½ ID, ë°?, ì²´í¬?¸ ?‚ ì§?, ì²´í¬?•„?›ƒ ?‚ ì§?
    private Customer customer;
    private int reservID;
    private Room room;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Customer members;
    private int visitCount;
    private static int nextReservID = 1; // ?˜ˆ?•½ ID ??™ ì¦ê??ë¥? ?œ„?•œ ? •?  ë³??ˆ˜

    public Reservation() {
        // default?ƒ?„±?
    }

    // ?ƒ?„±?
    public Reservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.room = room;
        this.members = members;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservID = nextReservID++; // ?˜ˆ?•½ ID ??™ ?ƒ?„±
    }

    // ?˜¤?¼? ˆ?´?…˜
    // ?˜ˆ?•½ì¶”ê??
    public void addReservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room.setFull(true); // ë°©ì´ ?˜ˆ?•½?˜?—ˆ?Œ?„ ?‘œ?‹œ
        this.members = members;
        this.visitCount++;
        this.reservID = nextReservID++; // ?˜ˆ?•½ ID ??™ ?ƒ?„±
    }

    public void printReserv() {
    	System.out.println("----- ?˜ˆ?•½ ?‚´?—­ -----");
        System.out.println("?˜ˆ?•½ ?‚´?—­ ì¶œë ¥:");
        System.out.println("ê³ ê° ?´ë¦?: " + customer.getName());
        System.out.println("?˜ˆ?•½ ID: " + reservID);
        System.out.println("ë°©ë²ˆ?˜¸: " + room.getRoomID());
        System.out.println("ì²´í¬?¸ ?‚ ì§?: " + startDate);
        System.out.println("ì²´í¬?•„?›ƒ ?‚ ì§?: " + endDate);
        int days = (int)java.time.Duration.between(startDate, endDate).toDays()+1;
        System.out.println("ê°?ê²?: " + room.getPrice()*days + "?›");
    }

    public void deleteReserv() {
        this.customer = null;
        this.room = null;
        this.startDate = null;
        this.endDate = null;
    }

    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getReservID() {
        return reservID;
    }

    public void setReservID(int reservID) {
        this.reservID = reservID;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Customer getMembers() {
        return members;
    }

    public Customer setMembers(Customer members) {
        this.members = members;
        return this.members;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }
}
