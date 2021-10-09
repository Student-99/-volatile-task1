import java.util.Random;

public class Main {
    volatile static boolean isToggleSwitchOn = false;

    public static void main(String[] args) {

        int frequencyOfToyChecksMillis = 500;
        int countOfToggleOn = new Random().nextInt(10);
        Runnable threadToys = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    if (isToggleSwitchOn) {
                        System.out.println("Игрушка выключила тумблер");
                        isToggleSwitchOn = false;
                    }
                    Thread.sleep(frequencyOfToyChecksMillis);
                }
            } catch (InterruptedException e) {

            } finally {
                System.out.println("Игрушка выключила тумблер");
                isToggleSwitchOn = false;
                System.out.println("Игра окончена");
            }
        };

        Thread toy = new Thread(threadToys);

        Runnable threadEmployee = () -> {
            for (int i = 0; i < countOfToggleOn; i++) {
                while (isToggleSwitchOn) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!isToggleSwitchOn) {
                    System.out.println("Пользователь включил тумблер");
                    isToggleSwitchOn = true;
                }
            }
            toy.interrupt();
        };

        Thread employee = new Thread(threadEmployee);
        employee.start();
        toy.start();
    }
}
