import java.lang.System;

public class ReservationSystem {
    private Reservation reservID;
    private Room room;

    public void checkIn() {
        System.out.println("예약번호 " + reservID.getReservID() + "로 체크인 완료.");
        System.out.println("방 번호는 " + room.getRoomID() + "입니다. 즐거운 시간 되세요!");
    }

    public void checkOut() {
        System.out.println("예약번호 " + reservID.getReservID() + "로 체크아웃 완료.");
        System.out.println(reservID.getVisitCount() + "번째 방문이므로 다음 이용부터 " + getGrade(reservID.getVisitCount()) + " 등급입니다.");
        System.out.println("추가 결제가 필요하십니까?");
        room.setFull(false);
    }

    private String getGrade(int visitCount) {
        if (visitCount == 1) {
            return "신규고객";
        } else if (visitCount == 2) {
            return "재방문고객";
        } else {
            return "단골고객";
        }
    }
}
