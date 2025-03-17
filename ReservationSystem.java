import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.System;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReservationSystem {
    private List<Reservation> reservations;
    private List<Room> rooms;
    OutputStreamWriter writer = null;

    public ReservationSystem(List<Reservation> reservations, List<Room> rooms) {
        this.reservations = reservations;
        this.rooms = rooms;
    }

    // 예약 list[]에 추가
    public Reservation addReservation(Customer customer, Room room, int members, LocalDate startDate, LocalDate endDate) {
        Reservation reservation = new Reservation(customer, room, members, startDate, endDate);
        reservations.add(reservation);
        return reservation;
    }

    public void deleteReservation(Scanner sc) {
        System.out.println("===== 예약 삭제 =====");
        System.out.print("삭제할 예약 ID를 입력하세요: ");
        int reservID = Integer.parseInt(sc.nextLine());
        Reservation foundReservation = reservations.stream()
            .filter(r -> r.getReservID() == reservID)
            .findFirst().orElse(null);
        if (foundReservation != null) {
            reservations.remove(foundReservation);
            foundReservation.getRoom().setFull(false); // 방을 비어있음으로 표시
            System.out.println("예약이 삭제되었습니다.");
        } else {
            System.out.println("해당 예약 ID를 찾을 수 없습니다.");
        }
        System.out.println();
    }

    // 예약가능한 방 수 출력
    public Map<String, Long> getAvailableRoomCounts(LocalDate startDate, LocalDate endDate) {
        return rooms.stream()
                .filter(room -> reservations.stream()
                        .noneMatch(reservation -> reservation.getRoom().equals(room) &&
                                (reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate))))
                .collect(Collectors.groupingBy(Room::getType, Collectors.counting()));
    }

    // 예약가능한 방 출력
    public List<Room> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        return rooms.stream()
                .filter(room -> reservations.stream()
                        .noneMatch(reservation -> reservation.getRoom().equals(room) &&
                                (reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate))))
                .collect(Collectors.toList());
    }

    // 고객 정보 저장
    public void saveInfo(Customer customer, int reservID, Room room, LocalDateTime startDate, LocalDateTime endDate) { 
    try { 
        String fileName = "고객 예약내역 리스트.txt"; 
        writer = new OutputStreamWriter(new FileOutputStream(fileName, true), "EUC-KR"); 
        writer.write("이름: " + customer.getName() + 
                     ", 전화번호: " + customer.getPhoneNum() + 
                     ", 예약번호: " + reservID + 
                     ", 방 번호: " + room.getRoomID() + 
                     ", 방 타입: " + room.getType() + 
                     ", 체크인 날짜: " + startDate.toString() + 
                     ", 체크아웃 날짜: " + endDate.toString() + "\n"); 
    } catch (Exception e) {
        e.printStackTrace();
    } finally { 
        try { 
            if (writer != null) { 
                writer.close(); 
            } 
        } catch (Exception e2) {
            e2.printStackTrace(); 
        } 
    } 
}

    public void checkIn(Scanner sc) {
        System.out.println("===== 체크인 =====");
        System.out.print("체크인할 예약 ID를 입력하세요: ");
        try {
            int reservID = Integer.parseInt(sc.nextLine());
            for (Reservation reservation : reservations) {
                if (reservation.getReservID() == reservID) {
                    if (reservation.isCheckedIn()) {
                        System.out.println("이미 체크인된 예약입니다.");
                        return;
                    }
                    System.out.println("예약번호 " + reservation.getReservID() + "로 체크인 완료.");
                    for (Room room : rooms) {
                        if (room.getRoomID() == reservation.getRoom().getRoomID()) {
                            System.out.println("방 번호는 " + room.getRoomID() + "입니다. 즐거운 시간 되세요!");
                            room.setFull(true);
                            reservation.setCheckedIn(true);
                            break;
                        }
                    }
                    return;
                }
            }
            System.out.println("유효한 예약 ID(숫자)를 입력하세요.");
        } catch (NumberFormatException e) {
            System.out.println("유효한 예약 ID(숫자)를 입력하세요.");
        }
        System.out.println();
    }

    public void checkOut(Scanner sc) {
        System.out.println("===== 체크아웃 =====");
        System.out.print("체크아웃할 예약 ID를 입력하세요: ");
        try {
            int reservID = Integer.parseInt(sc.nextLine());
            for (Reservation reservation : reservations) {
                if (reservation.getReservID() == reservID) {
                    if (reservation.isCheckedOut()) {
                        System.out.println("이미 체크아웃된 예약입니다.");
                        return;
                    }
                    System.out.println("예약번호 " + reservation.getReservID() + "로 체크아웃 완료.");
                    System.out.println("다음 이용부터 " + getnextGrade(reservation.getVisitCount() + 1) + " 등급입니다.");
                    System.out.print("추가 결제가 필요하십니까? (네/아니오) : ");
                    String response = sc.nextLine();

                    if (response.equalsIgnoreCase("네")) {
                        System.out.print("추가 결제 금액을 입력하세요: ");
                        int additionalPayment = Integer.parseInt(sc.nextLine());
                        additionalPayment(additionalPayment);
                    } else {
                        System.out.println("방문해주셔서 감사합니다.");
                    }

                    for (Room room : rooms) {
                        if (room.getRoomID() == reservation.getRoom().getRoomID()) {
                            room.setFull(false);
                            reservation.setCheckedOut(true);
                            break;
                        }
                    }
                    return;
                }
            }
            System.out.println("유효한 예약 ID(숫자)를 입력하세요.");
        } catch (NumberFormatException e) {
            System.out.println("유효한 예약 ID(숫자)를 입력하세요.");
        }
        System.out.println();
    }

    private void additionalPayment(int amount) {
        System.out.println("추가 결제 " + amount + "원이 완료되었습니다.");
        System.out.println("방문해주셔서 감사합니다. ");
    }

    private String getnextGrade(int visitCount) {
            if (visitCount == 1) {
                return "재방문고객";
            } else {
                return "단골고객";
            }
        }
}
