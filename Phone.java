import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

interface Telephone {
    void powerOn();
    void dial(String phoneNumber);
    void answer();
    boolean callPhone(String phoneNumber);
    boolean isRinging();
}

class DeskPhone implements Telephone {
    private String myNumber;
    private boolean isRinging;

    public DeskPhone(String myNumber) {
        this.myNumber = myNumber;
    }

    @Override
    public void powerOn() {
        System.out.println("\nDesk phone is always powered.");
    }

    @Override
    public void dial(String phoneNumber) {
        System.out.println("\n\nNow ringing " + phoneNumber + " on desk phone");
    }

    @Override
    public void answer() {
        if (isRinging) {
            System.out.println("\n\nAnswering the desk phone");
            isRinging = false;
        } else {
            System.out.println("\n\nPhone is not ringing");
        }
    }

    @Override
    public boolean callPhone(String phoneNumber) {
        if (phoneNumber.equals(myNumber)) {
            dial(phoneNumber);
            isRinging = true;
            System.out.println("\n\nPhone ringing");
        } else {
            System.out.println("\n\nCall rejected. Invalid phone number.");
            isRinging = false;
        }
        return isRinging;
    }

    @Override
    public boolean isRinging() {
        return isRinging;
    }
}

class MobilePhone implements Telephone {
    private String myNumber;
    private boolean isRinging;
    private boolean isPowerOn;

    public MobilePhone(String myNumber) {
        this.myNumber = myNumber;
    }

    @Override
    public void powerOn() {
        isPowerOn = true;
        System.out.println("\n\nMobile Phone powered on.");
    }

    @Override
    public void dial(String phoneNumber) {
        System.out.println("\nNow ringing " + phoneNumber + " on mobile phone");
    }

    @Override
    public void answer() {
        if (isRinging && isPowerOn) {
            System.out.println("\n\nAnswering the phone");
            isRinging = false;
        }
    }

    @Override
    public boolean callPhone(String phoneNumber) {
        if (phoneNumber.equals(myNumber) && isPowerOn) {
            isRinging = true;
            dial(phoneNumber);
            System.out.println("\nPhone ringing");
        } else {
            System.out.println("\nCall rejected. Invalid phone number or phone is off.");
            isRinging = false;
        }
        return isRinging;
    }

    @Override
    public boolean isRinging() {
        return isRinging;
    }
}

class Phone {
    private static final int UNANSWERED_DELAY = 10000; // 10 seconds

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DeskPhone myDeskPhone = new DeskPhone("12345");
        MobilePhone myMobilePhone = new MobilePhone("123456");
        myDeskPhone.powerOn();
        myMobilePhone.powerOn();

        System.out.println("Enter the phone number you want to call: ");
        String phoneNumberToCall = scanner.nextLine();

        if (myDeskPhone.callPhone(phoneNumberToCall) || myMobilePhone.callPhone(phoneNumberToCall)) {
            System.out.println("\n\nWaiting for the call to be answered...");

            Timer unansweredTimer = new Timer();
            unansweredTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (myDeskPhone.isRinging()) {
                        myDeskPhone.answer();
                        unansweredTimer.cancel();
                    } else if (myMobilePhone.isRinging()) {
                        myMobilePhone.answer();
                        unansweredTimer.cancel();
                    } else {
                        System.out.println("\n\nCall rejected or unanswered.");
                    }
                }
            }, UNANSWERED_DELAY);
        } else {
            System.out.println("\n\nCall rejected or unanswered.");
        }

        scanner.close();
    }
}