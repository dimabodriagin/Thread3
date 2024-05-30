import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger counterForLength3 = new AtomicInteger(0);
    public static AtomicInteger counterForLength4 = new AtomicInteger(0);
    public static AtomicInteger counterForLength5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread threadForLength3 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    addNickname(text.length());
                }
            }
        });
        threadForLength3.start();

        Thread threadForLength4 = new Thread(() -> {
            for (String text : texts) {
                if (isFromSingleLetter(text)) {
                   addNickname(text.length());
                }
            }
        });
        threadForLength4.start();

        Thread threadForLength5 = new Thread(() -> {
            for (String text : texts) {
                if (isAscendingOrder(text)) {
                    addNickname(text.length());
                }
            }
        });
        threadForLength5.start();

        try {
            threadForLength3.join();
            threadForLength4.join();
            threadForLength5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Красивых слов с длиной 3: %d шт\n", counterForLength3.get());
        System.out.printf("Красивых слов с длиной 4: %d шт\n", counterForLength4.get());
        System.out.printf("Красивых слов с длиной 5: %d шт\n", counterForLength5.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String nickname) {
        for (int i = 0; i < nickname.length() - 1 - i; i++) {
            if (nickname.charAt(i) != nickname.charAt(nickname.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFromSingleLetter(String nickname) {
        Character letter = nickname.charAt(0);
        for (int i = 1; i < nickname.length(); i++) {
            if (letter != nickname.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscendingOrder(String nickname) {
        for (int i = 0; i < nickname.length() - 1; i++) {
            if (nickname.charAt(i) > nickname.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static void addNickname(int length) {
        switch (length) {
            case 3 -> counterForLength3.addAndGet(1);
            case 4 -> counterForLength4.addAndGet(1);
            case 5 -> counterForLength5.addAndGet(1);
        }
    }
}
