import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static int MAX_QUEUE = 100;
    public static int TEXT_LENGTH = 10_000;
    public static int NUMBER_OF_SENTENCES = 100_000;
    public static BlockingQueue<String> forA = new ArrayBlockingQueue<>(MAX_QUEUE);
    public static BlockingQueue<String> forB = new ArrayBlockingQueue<>(MAX_QUEUE);
    public static BlockingQueue<String> forC = new ArrayBlockingQueue<>(MAX_QUEUE);

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < NUMBER_OF_SENTENCES; i++) {
                String text = generateText("abc", TEXT_LENGTH);
                try {
                    forA.put(text);
                    forB.put(text);
                    forC.put(text);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        new Thread(() -> {
            int maxNumberOfA = 0;
            int currentNumberOfA = 0;
            String textWithMaxA = null;
            for (int i = 0; i < NUMBER_OF_SENTENCES; i++) {
                try {
                    String currentText = forA.take();
                    for (char character : currentText.toCharArray()) {
                        if (character == 'a') {
                            currentNumberOfA++;
                        }
                    }
                    if (currentNumberOfA > maxNumberOfA) {
                        maxNumberOfA = currentNumberOfA;
                        textWithMaxA = currentText;
                    }
                } catch (InterruptedException e) {
                    return;
                }
                currentNumberOfA = 0;
            }
            System.out.println("Текст с максимальным \"A\" (" + maxNumberOfA + "): " + textWithMaxA);
        }).start();

        new Thread(() -> {
            int maxNumberOfB = 0;
            int currentNumberOfB = 0;
            String textWithMaxB = null;
            for (int i = 0; i < NUMBER_OF_SENTENCES; i++) {
                try {
                    String currentText = forB.take();
                    for (char character : currentText.toCharArray()) {
                        if (character == 'b') {
                            currentNumberOfB++;
                        }
                    }
                    if (currentNumberOfB > maxNumberOfB) {
                        maxNumberOfB = currentNumberOfB;
                        textWithMaxB = currentText;
                    }
                } catch (InterruptedException e) {
                    return;
                }
                currentNumberOfB = 0;
            }
            System.out.println("Текст с максимальным \"B\" (" + maxNumberOfB + "): " + textWithMaxB);
        }).start();

        new Thread(() -> {
            int maxNumberOfC = 0;
            int currentNumberOfC = 0;
            String textWithMaxC = null;
            for (int i = 0; i < NUMBER_OF_SENTENCES; i++) {
                try {
                    String currentText = forC.take();
                    for (char character : currentText.toCharArray()) {
                        if (character == 'c') {
                            currentNumberOfC++;
                        }
                    }
                    if (currentNumberOfC > maxNumberOfC) {
                        maxNumberOfC = currentNumberOfC;
                        textWithMaxC = currentText;
                    }
                } catch (InterruptedException e) {
                    return;
                }
                currentNumberOfC = 0;
            }
            System.out.println("Текст с максимальным \"C\" (" + maxNumberOfC + "): " + textWithMaxC);
        }).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
