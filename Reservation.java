import java.time.LocalDateTime;
import java.lang.System;

public class Reservation {
    // 필드
    // 고객, 예약 ID, 방, 체크인 날짜, 체크아웃 날짜
    private Customer customer;
    private int reservID;
    private Room room;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Customer members;
    private int visitCount;
    private static int nextReservID = 1; // 예약 ID 자동 증가를 위한 정적 변수

    public Reservation() {
        // default생성자
    }

    // 생성자
    public Reservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.room = room;
        this.members = members;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservID = nextReservID++; // 예약 ID 자동 생성
    }

    // 오퍼레이션
    // 예약추가
    public void addReservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room.setFull(true); // 방이 예약되었음을 표시
        this.members = members;
        this.visitCount++;
        this.reservID = nextReservID++; // 예약 ID 자동 생성
    }

    public void printReserv(int reservID) {
        if (this.reservID == reservID) {
            System.out.println("----- 예약 내역 -----");
            System.out.println("예약 내역 출력:");
            System.out.println("고객 이름: " + customer.getName());
            System.out.println("예약 ID: " + this.reservID);
            System.out.println("방번호: " + room.getRoomID());
            System.out.println("체크인 날짜: " + startDate);
            System.out.println("체크아웃 날짜: " + endDate);
            long days = java.time.Duration.between(startDate, endDate).toDays();
            System.out.println("가격: " + room.getPrice() * days + "원");
        } else {
            System.out.println("해당 예약 ID를 찾을 수 없습니다.");
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
