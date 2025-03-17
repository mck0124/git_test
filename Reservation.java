import java.time.LocalDateTime;
import java.lang.System;

public class Reservation {
    // �ʵ�
    // ��, ���� ID, ��, üũ�� ��¥, üũ�ƿ� ��¥
    private Customer customer;
    private int reservID;
    private Room room;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Customer members;
    private int visitCount;
    private static int nextReservID = 1; // ���� ID �ڵ� ������ ���� ���� ����

    public Reservation() {
        // default������
    }

    // ������
    public Reservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.room = room;
        this.members = members;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservID = nextReservID++; // ���� ID �ڵ� ����
    }

    // ���۷��̼�
    // �����߰�
    public void addReservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room.setFull(true); // ���� ����Ǿ����� ǥ��
        this.members = members;
        this.visitCount++;
        this.reservID = nextReservID++; // ���� ID �ڵ� ����
    }

    public void printReserv(int reservID) {
        if (this.reservID == reservID) {
            System.out.println("----- ���� ���� -----");
            System.out.println("���� ���� ���:");
            System.out.println("�� �̸�: " + customer.getName());
            System.out.println("���� ID: " + this.reservID);
            System.out.println("���ȣ: " + room.getRoomID());
            System.out.println("üũ�� ��¥: " + startDate);
            System.out.println("üũ�ƿ� ��¥: " + endDate);
            long days = java.time.Duration.between(startDate, endDate).toDays();
            System.out.println("����: " + room.getPrice() * days + "��");
        } else {
            System.out.println("�ش� ���� ID�� ã�� �� �����ϴ�.");
        }
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
