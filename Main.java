import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Customer> customers = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        List<Reservation> reservations = new ArrayList<>();
        ReservationSystem system = new ReservationSystem(reservations, rooms);
        
        //ì´ˆê¸° ë°? ?°?´?„° ì¶”ê?? (?…Œ?Š¤?Š¸?š©)
       rooms.add(new Room(101, "Single", 2, 50000, false));
       rooms.add(new Room(102, "Single", 2, 50000, false));
       rooms.add(new Room(201, "Double", 4, 80000, false));
       rooms.add(new Room(202, "Double", 4, 80000, false));
       
       
       //?‚ ì§? ?˜•?‹ ì§?? • ex) 2025-03-20 14:00
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        while (true) {
        	System.out.println("===== ë©”ë‰´ =====");
            System.out.println("1. ê³ ê° ? •ë³? ?…? ¥");
            System.out.println("2. ë°? ?˜ˆ?•½");
            System.out.println("3. ?˜ˆ?•½ ? •ë³? ì¶œë ¥");
            System.out.println("4. ì²´í¬?¸");
            System.out.println("5. ì²´í¬?•„?›ƒ");
            System.out.println("6. ? „ì²? ?˜ˆ?•½ ?ŒŒ?¼ ?ƒ?„±");
            System.out.println("7. ì¢…ë£Œ");
            System.out.print("ë©”ë‰´ë¥? ?„ ?ƒ?•˜?„¸?š”: ");
            String menu = sc.nextLine();
            System.out.println();

            switch (menu) {
                case "1": //ê³ ê° ? •ë³? ?…? ¥ 
                	System.out.println("===== ê³ ê° ? •ë³? ?…? ¥ =====");
                	Customer customer = new Customer(); // ê³ ê° ê°ì²´ ?ƒ?„±
                	customer.inputInfo();
                	customers.add(customer);
                	System.out.println();
                    break;
                    
                case "2": //ë°? ?˜ˆ?•½ 
                	//ê³ ê° ? „?™”ë²ˆí˜¸ ?…? ¥ (+ì¡´ì¬ ?—¬ë¶? ?™•?¸)
                	System.out.println("===== ë°? ?˜ˆ?•½ =====");
                    System.out.print("?˜ˆ?•½?•  ê³ ê° ? „?™”ë²ˆí˜¸ë¥? ?…? ¥?•˜?„¸?š”: ");
                    String phoneNum = sc.nextLine();
                    System.out.println();
                    Customer foundCustomer = customers.stream()
                    		.filter(c -> c.getPhoneNum().equals(phoneNum))
                    		.findFirst().orElse(null);
                    if (foundCustomer == null) {
                    	System.out.println("ê³ ê°?„ ì°¾ì„ ?ˆ˜ ?—†?Šµ?‹ˆ?‹¤.");
                    	System.out.println();
                    	break;
                    }
                    
                    //?˜ˆ?•½ ?‚ ì§? ?…? ¥
                    System.out.println("----- ?˜ˆ?•½ ?‚ ì§? ?…? ¥ -----");
                    LocalDateTime startDate = null, endDate = null;
                    boolean validDate = false;
                    while (!validDate) {
                        System.out.println("ì²´í¬?¸ ?‚ ì§œë?? ?…? ¥?•˜?„¸?š” (ex. 2025-03-20 14:00): ");
                        String startDateStr = sc.nextLine();
                        System.out.println();
                        System.out.println("ì²´í¬?•„?›ƒ ?‚ ì§œë?? ?…? ¥?•˜?„¸?š” (ex. 2025-03-20 14:00): ");
                        String endDateStr = sc.nextLine();
                        System.out.println();
                        try {
                    	    startDate = LocalDateTime.parse(startDateStr, formatter);
                    	    endDate = LocalDateTime.parse(endDateStr, formatter);
                    	    
                    	    // ?œ ?š¨?•œ ?˜ˆ?•½ ?‚ ì§œì¸ì§? ?™•?¸ ? ˆì°? (?˜¤?…? ¥ ë°©ì??)
                    	    if (startDate.isAfter(endDate)) {
                    	    	System.out.println("ì²´í¬?¸ ?‚ ì§œëŠ” ì²´í¬?•„?›ƒ ?‚ ì§œë³´?‹¤ ?´? „?´?–´?•¼ ?•©?‹ˆ?‹¤. ?‹¤?‹œ ?…? ¥?•´ ì£¼ì„¸?š”.");
                    	    } else if (startDate.isBefore(LocalDateTime.now())) {
                    	    	System.out.println("ì²´í¬?¸ ?‚ ì§œëŠ” ?˜„?¬ë³´ë‹¤ ë¯¸ë˜?—¬?•¼ ?•©?‹ˆ?‹¤. ?‹¤?‹œ ?…? ¥?•´ì£¼ì„¸?š”.");
                    	    } else validDate = true;
                        } catch (Exception e) {
                        	System.out.println("?‚ ì§? ?˜•?‹?´ ?˜ëª»ë˜?—ˆ?Šµ?‹ˆ?‹¤. (ex. 2025-03-20 14:00)");
                        	System.out.println();
                        }
                    }
                    
                    //?•´?‹¹ ?‚ ì§œì— ?˜ˆ?•½ ê°??Š¥?•œ ë°? ëª©ë¡ (?•„?„°ë§?)
                    Map<String, Long> availableRooms = system.getAvailableRoomCounts(startDate, endDate);
                    if (availableRooms.isEmpty()) {
                 	    System.out.println(startDate.toLocalDate() + "?— ?˜ˆ?•½ ê°??Š¥?•œ ë°©ì´ ?—†?Šµ?‹ˆ?‹¤.");
                 	    System.out.println();
                 	    break;
                    }
                   
                    //ë°? ????…ë³„ë¡œ ê·¸ë£¹?™” ?•´?„œ ì¶œë ¥?•˜ê¸?
                    System.out.println("----- " + startDate.toLocalDate() + "?— ?˜ˆ?•½ ê°??Š¥?•œ ë°? -----");
                    availableRooms.forEach((type, count) -> 
                    	System.out.println(type + "ë£?: " + count + "ê°? ?‚¨?Œ"));
                    System.out.println();

                    //ë°? ????… ?„ ?ƒ
                    System.out.print("?˜ˆ?•½?•  ë°? ????…?„ ?…? ¥?•˜?„¸?š” (single/double): ");
                    String roomType = sc.nextLine();
                    System.out.println();
                    Room selectedRoom = system.getAvailableRooms(startDate, endDate).stream()
                	 	   .filter(r -> r.getType().equalsIgnoreCase(roomType))
                	 	   .findFirst().orElse(null);
                   
                    if (selectedRoom == null) {
                	    System.out.println("?„ ?ƒ?•˜?‹  ë°©ì?? ?˜ˆ?•½?•˜?‹¤ ?ˆ˜ ?—†?Šµ?‹ˆ?‹¤.");
                	    System.out.println();
                	    break;
                    }
                   
                    //?˜ˆ?•½ ?ƒ?„±
                    Reservation reservation = system.addReservation(foundCustomer, selectedRoom, foundCustomer, startDate, endDate);
                    foundCustomer.setReservID(reservation);
                    System.out.println("----- ?˜ˆ?•½ ?™„ë£? -----");
                    System.out.println("?˜ˆ?•½?´ ?™„ë£Œë˜?—ˆ?Šµ?‹ˆ?‹¤.\n?˜ˆ?•½ ID: " + reservation.getReservID());
                    System.out.println();
                    break;
                    
                case "3": //¿¹¾à Á¤º¸ Ãâ·Â 
                    System.out.println("===== ¿¹¾à Á¤º¸ Ãâ·Â =====");
                    System.out.print("¿¹¾à ID¸¦ ÀÔ·ÂÇÏ¼¼¿ä: ");
                    int reservID = Integer.parseInt(sc.nextLine());
                    Reservation foundReservation = reservations.stream()
                        .filter(r -> r.getReservID() == reservID)
                        .findFirst().orElse(null);
                    if (foundReservation != null) {
                        foundReservation.printReserv(reservID);
                    } else {
                        System.out.println("¿¹¾àÀ» Ã£À» ¼ö ¾ø½À´Ï´Ù.");
                    }
                    System.out.println();
                    break;
                    
                case "4": //ì²´í¬?¸ 
                	System.out.println("===== ì²´í¬?¸ =====");
                	System.out.print("ì²´í¬?¸?•  ?˜ˆ?•½ IDë¥? ?…? ¥?•˜?„¸?š”: ");
                	try {
						int checkInId = Integer.parseInt(sc.nextLine());
						system.checkIn(checkInId);
					} catch (NumberFormatException e) {
						System.out.println("?œ ?š¨?•œ ?˜ˆ?•½ ID(?ˆ«?)ë¥? ?…? ¥?•˜?„¸?š”.");
					}
                	System.out.println();
                    break;
                    
                case "5": //ì²´í¬?•„?›ƒ 
                	System.out.println("===== ì²´í¬?•„?›ƒ =====");
                    System.out.print("ì²´í¬?•„?›ƒ?•  ?˜ˆ?•½ IDë¥? ?…? ¥?•˜?„¸?š”: ");
                    try {
						int checkOutId = Integer.parseInt(sc.nextLine());
						system.checkOut(checkOutId);
					} catch (NumberFormatException e) {
						System.out.println("?œ ?š¨?•œ ?˜ˆ?•½ ID(?ˆ«?)ë¥? ?…? ¥?•˜?„¸?š”.");
					}
                    System.out.println();
                    break;
                    
                case "6": //ê³ ê° ? •ë³? ?“±ë¡? 
                	System.out.println("===== ? „ì²? ?˜ˆ?•½ ?ŒŒ?¼ ?ƒ?„± =====");
                    for (Customer c : customers) {
                    	system.saveInfo(c, c.getReservID().getReservID(), c.getMembers());
                    }
                    System.out.println("ê³ ê° ?˜ˆ?•½?‚´?—­ ë¦¬ìŠ¤?Š¸.txt ?ŒŒ?¼?— ?“±ë¡ë˜?—ˆ?Šµ?‹ˆ?‹¤.");
                    System.out.println();
                    break;
                    
                case "7": //ì¢…ë£Œ 
                    System.out.println("?”„ë¡œê·¸?¨?„ ì¢…ë£Œ?•©?‹ˆ?‹¤.");
                    return;
                    
                default:
                    System.out.println("?˜ëª»ëœ ?„ ?ƒ?…?‹ˆ?‹¤. ?‹¤?‹œ ?‹œ?„?•˜?„¸?š”.");
                    System.out.println();
            }
        }
    }
}
