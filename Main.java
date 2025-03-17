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
        
        //μ΄κΈ° λ°? ?°?΄?° μΆκ?? (??€?Έ?©)
       rooms.add(new Room(101, "Single", 2, 50000, false));
       rooms.add(new Room(102, "Single", 2, 50000, false));
       rooms.add(new Room(201, "Double", 4, 80000, false));
       rooms.add(new Room(202, "Double", 4, 80000, false));
       
       
       //? μ§? ?? μ§??  ex) 2025-03-20 14:00
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        while (true) {
        	System.out.println("===== λ©λ΄ =====");
            System.out.println("1. κ³ κ° ? λ³? ?? ₯");
            System.out.println("2. λ°? ??½");
            System.out.println("3. ??½ ? λ³? μΆλ ₯");
            System.out.println("4. μ²΄ν¬?Έ");
            System.out.println("5. μ²΄ν¬??");
            System.out.println("6. ? μ²? ??½ ??Ό ??±");
            System.out.println("7. μ’λ£");
            System.out.print("λ©λ΄λ₯? ? ???Έ?: ");
            String menu = sc.nextLine();
            System.out.println();

            switch (menu) {
                case "1": //κ³ κ° ? λ³? ?? ₯ 
                	System.out.println("===== κ³ κ° ? λ³? ?? ₯ =====");
                	Customer customer = new Customer(); // κ³ κ° κ°μ²΄ ??±
                	customer.inputInfo();
                	customers.add(customer);
                	System.out.println();
                    break;
                    
                case "2": //λ°? ??½ 
                	//κ³ κ° ? ?λ²νΈ ?? ₯ (+μ‘΄μ¬ ?¬λΆ? ??Έ)
                	System.out.println("===== λ°? ??½ =====");
                    System.out.print("??½?  κ³ κ° ? ?λ²νΈλ₯? ?? ₯??Έ?: ");
                    String phoneNum = sc.nextLine();
                    System.out.println();
                    Customer foundCustomer = customers.stream()
                    		.filter(c -> c.getPhoneNum().equals(phoneNum))
                    		.findFirst().orElse(null);
                    if (foundCustomer == null) {
                    	System.out.println("κ³ κ°? μ°Ύμ ? ??΅??€.");
                    	System.out.println();
                    	break;
                    }
                    
                    //??½ ? μ§? ?? ₯
                    System.out.println("----- ??½ ? μ§? ?? ₯ -----");
                    LocalDateTime startDate = null, endDate = null;
                    boolean validDate = false;
                    while (!validDate) {
                        System.out.println("μ²΄ν¬?Έ ? μ§λ?? ?? ₯??Έ? (ex. 2025-03-20 14:00): ");
                        String startDateStr = sc.nextLine();
                        System.out.println();
                        System.out.println("μ²΄ν¬?? ? μ§λ?? ?? ₯??Έ? (ex. 2025-03-20 14:00): ");
                        String endDateStr = sc.nextLine();
                        System.out.println();
                        try {
                    	    startDate = LocalDateTime.parse(startDateStr, formatter);
                    	    endDate = LocalDateTime.parse(endDateStr, formatter);
                    	    
                    	    // ? ?¨? ??½ ? μ§μΈμ§? ??Έ ? μ°? (?€?? ₯ λ°©μ??)
                    	    if (startDate.isAfter(endDate)) {
                    	    	System.out.println("μ²΄ν¬?Έ ? μ§λ μ²΄ν¬?? ? μ§λ³΄?€ ?΄? ?΄?΄?Ό ?©??€. ?€? ?? ₯?΄ μ£ΌμΈ?.");
                    	    } else if (startDate.isBefore(LocalDateTime.now())) {
                    	    	System.out.println("μ²΄ν¬?Έ ? μ§λ ??¬λ³΄λ€ λ―Έλ?¬?Ό ?©??€. ?€? ?? ₯?΄μ£ΌμΈ?.");
                    	    } else validDate = true;
                        } catch (Exception e) {
                        	System.out.println("? μ§? ???΄ ?λͺ»λ??΅??€. (ex. 2025-03-20 14:00)");
                        	System.out.println();
                        }
                    }
                    
                    //?΄?Ή ? μ§μ ??½ κ°??₯? λ°? λͺ©λ‘ (??°λ§?)
                    Map<String, Long> availableRooms = system.getAvailableRoomCounts(startDate, endDate);
                    if (availableRooms.isEmpty()) {
                 	    System.out.println(startDate.toLocalDate() + "? ??½ κ°??₯? λ°©μ΄ ??΅??€.");
                 	    System.out.println();
                 	    break;
                    }
                   
                    //λ°? ????λ³λ‘ κ·Έλ£Ή? ?΄? μΆλ ₯?κΈ?
                    System.out.println("----- " + startDate.toLocalDate() + "? ??½ κ°??₯? λ°? -----");
                    availableRooms.forEach((type, count) -> 
                    	System.out.println(type + "λ£?: " + count + "κ°? ?¨?"));
                    System.out.println();

                    //λ°? ???? ? ?
                    System.out.print("??½?  λ°? ????? ?? ₯??Έ? (single/double): ");
                    String roomType = sc.nextLine();
                    System.out.println();
                    Room selectedRoom = system.getAvailableRooms(startDate, endDate).stream()
                	 	   .filter(r -> r.getType().equalsIgnoreCase(roomType))
                	 	   .findFirst().orElse(null);
                   
                    if (selectedRoom == null) {
                	    System.out.println("? ???  λ°©μ?? ??½??€ ? ??΅??€.");
                	    System.out.println();
                	    break;
                    }
                   
                    //??½ ??±
                    Reservation reservation = system.addReservation(foundCustomer, selectedRoom, foundCustomer, startDate, endDate);
                    foundCustomer.setReservID(reservation);
                    System.out.println("----- ??½ ?λ£? -----");
                    System.out.println("??½?΄ ?λ£λ??΅??€.\n??½ ID: " + reservation.getReservID());
                    System.out.println();
                    break;
                    
                case "3": //ΏΉΎΰ Α€ΊΈ Γβ·Β 
                    System.out.println("===== ΏΉΎΰ Α€ΊΈ Γβ·Β =====");
                    System.out.print("ΏΉΎΰ IDΈ¦ ΐΤ·ΒΗΟΌΌΏδ: ");
                    int reservID = Integer.parseInt(sc.nextLine());
                    Reservation foundReservation = reservations.stream()
                        .filter(r -> r.getReservID() == reservID)
                        .findFirst().orElse(null);
                    if (foundReservation != null) {
                        foundReservation.printReserv(reservID);
                    } else {
                        System.out.println("ΏΉΎΰΐ» Γ£ΐ» Όφ Ύψ½ΐ΄Ο΄Ω.");
                    }
                    System.out.println();
                    break;
                    
                case "4": //μ²΄ν¬?Έ 
                	System.out.println("===== μ²΄ν¬?Έ =====");
                	System.out.print("μ²΄ν¬?Έ?  ??½ IDλ₯? ?? ₯??Έ?: ");
                	try {
						int checkInId = Integer.parseInt(sc.nextLine());
						system.checkIn(checkInId);
					} catch (NumberFormatException e) {
						System.out.println("? ?¨? ??½ ID(?«?)λ₯? ?? ₯??Έ?.");
					}
                	System.out.println();
                    break;
                    
                case "5": //μ²΄ν¬?? 
                	System.out.println("===== μ²΄ν¬?? =====");
                    System.out.print("μ²΄ν¬???  ??½ IDλ₯? ?? ₯??Έ?: ");
                    try {
						int checkOutId = Integer.parseInt(sc.nextLine());
						system.checkOut(checkOutId);
					} catch (NumberFormatException e) {
						System.out.println("? ?¨? ??½ ID(?«?)λ₯? ?? ₯??Έ?.");
					}
                    System.out.println();
                    break;
                    
                case "6": //κ³ κ° ? λ³? ?±λ‘? 
                	System.out.println("===== ? μ²? ??½ ??Ό ??± =====");
                    for (Customer c : customers) {
                    	system.saveInfo(c, c.getReservID().getReservID(), c.getMembers());
                    }
                    System.out.println("κ³ κ° ??½?΄?­ λ¦¬μ€?Έ.txt ??Ό? ?±λ‘λ??΅??€.");
                    System.out.println();
                    break;
                    
                case "7": //μ’λ£ 
                    System.out.println("?λ‘κ·Έ?¨? μ’λ£?©??€.");
                    return;
                    
                default:
                    System.out.println("?λͺ»λ ? ????€. ?€? ????Έ?.");
                    System.out.println();
            }
        }
    }
}
