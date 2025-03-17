import java.time.LocalDateTime;
import java.lang.System;

public class Reservation {
    // ?��?��
    // 고객, ?��?�� ID, �?, 체크?�� ?���?, 체크?��?�� ?���?
    private Customer customer;
    private int reservID;
    private Room room;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Customer members;
    private int visitCount;
    private static int nextReservID = 1; // ?��?�� ID ?��?�� 증�??�? ?��?�� ?��?�� �??��

    public Reservation() {
        // default?��?��?��
    }

    // ?��?��?��
    public Reservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.room = room;
        this.members = members;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservID = nextReservID++; // ?��?�� ID ?��?�� ?��?��
    }

    // ?��?��?��?��?��
    // ?��?��추�??
    public void addReservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room.setFull(true); // 방이 ?��?��?��?��?��?�� ?��?��
        this.members = members;
        this.visitCount++;
        this.reservID = nextReservID++; // ?��?�� ID ?��?�� ?��?��
    }

    public void printReserv() {
    	System.out.println("----- ?��?�� ?��?�� -----");
        System.out.println("?��?�� ?��?�� 출력:");
        System.out.println("고객 ?���?: " + customer.getName());
        System.out.println("?��?�� ID: " + reservID);
        System.out.println("방번?��: " + room.getRoomID());
        System.out.println("체크?�� ?���?: " + startDate);
        System.out.println("체크?��?�� ?���?: " + endDate);
        int days = (int)java.time.Duration.between(startDate, endDate).toDays()+1;
        System.out.println("�?�?: " + room.getPrice()*days + "?��");
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
