import java.lang.System;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationSystem {
    private List<Reservation> reservations;
    private List<Room> rooms;
    
    public ReservationSystem(List<Reservation> reservations, List<Room> rooms) {
		this.reservations = reservations;
		this.rooms = rooms;
	}
    
    //해당 날짜에 예약 가능한 방 목록 반환
    public List<Room> getAvailableRooms(LocalDateTime startDate, LocalDateTime endDate) {
    	return rooms.stream()
    			.filter(room -> isRoomAvailable(room, startDate, endDate))
    			.collect(Collectors.toList());
    }
    
    //방 타입별 예약 가능한 개수 반환
    public Map<String, Long> getAvailableRoomCounts(LocalDateTime startDate, LocalDateTime endDate) {
    	return getAvailableRooms(startDate, endDate).stream()
    			.collect(Collectors.groupingBy(Room::getType, Collectors.counting()));
    }
    
    //방이 예약 가능한지 확인
    private boolean isRoomAvailable(Room room, LocalDateTime startDate, LocalDateTime endDate) {
    	if (!room.isAvailable()) return false;
    	for (Reservation res:reservations) {
    		if (res.getRoom().getRoomID() == room.getRoomID()) {
    			LocalDateTime resStart = res.getStartDate();
    			LocalDateTime resEnd = res.getEndDate();
    			if (!(endDate.isBefore(resStart)||(startDate.isAfter(resEnd)))) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    //예약 추가
    public Reservation addReservation(Customer customer, Room room, int members, LocalDateTime startDate, LocalDateTime endDate) {
    	Reservation reservation = new Reservation();
    	reservation.addReserve(customer, room, members, startDate, endDate);
    	reservations.add(reservation);
    	return reservation;
    }

	public void checkIn(int reservID) {
		Reservation res = findReservation(reservID);
		if (res != null) {
			System.out.println("예약번호 " + res.getReservID() + "로 체크인 완료.");
	        System.out.println("방 번호는 " + res.getRoom().getRoomID() + "입니다. 즐거운 시간 되세요!");
		} else {
			System.out.println("예약을 찾을 수 없습니다.");
		}
        
    }

    public void checkOut(int reservID) {
    	Reservation res = findReservation(reservID);
    	if (res != null) {
            System.out.println("예약번호 " + res.getReservID() + "로 체크아웃 완료.");
            res.getCustomer().setVisitCount(res.getCustomer().getVisitCount() + 1);
            System.out.println(res.getCustomer().getVisitCount() + "번째 방문이므로 다음 이용부터 " + getGrade(res.getCustomer().getVisitCount()) + " 등급입니다.");
            System.out.println("추가 결제가 필요하십니까?");
            res.deleteReserv();
    	} else {
    		System.out.println("예약을 찾을 수 없습니다.");
    	}
    }
    
    private Reservation findReservation(int reservID) {
    	return reservations.stream()
    			.filter(r -> r.getReservID() == reservID)
    			.findFirst().orElse(null);
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
