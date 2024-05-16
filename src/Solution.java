import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import static java.lang.StringTemplate.STR;

public class Solution {
    private static ArrayList<Character> ALPHABET() {
        ArrayList<Character> alphabet = new ArrayList<>();
        addCharsRange(alphabet, 'А', 'Я');
        addCharsRange(alphabet, 'а', 'я');
        addCharsRange(alphabet, ' ', '?');

        return alphabet;
    }

    private static void addCharsRange(ArrayList<Character> alphabet, char from, char to) {
        for (char i = from; i <= to; i++) {
            alphabet.add(i);
        }
    }

    private int getKey() {
        System.out.println("Введите ключ");
        Scanner scanner = new Scanner(System.in);

        return scanner.nextInt();
    }

    private void encryption() {
        ArrayList<Character> list = ALPHABET();
        Path forReader = enteringFile();
        Path forWrite = Path.of(STR."\{forReader.getParent()}\\EncryptionFile.txt");

        if (Files.exists(forReader)) {
            int key = getKey();
            HashMap<Character, Character> map = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                map.put(list.get(i), list.get((i + key) % list.size()));
            }

            try (BufferedWriter buf = Files.newBufferedWriter(Files.createFile(forWrite))) {
                char[] arrayUserFails = Files.readString(forReader).toCharArray();
                char[] arrayFiles = new char[arrayUserFails.length];
                for (int i = 0; i < arrayUserFails.length; i++) {
                    arrayFiles[i] = map.get(arrayUserFails[i]);
                }
                buf.write(arrayFiles);
                System.out.println(STR."Создан файл. Путь: \{forWrite}");

            } catch (IOException e) {
                System.out.println(STR."Ошибка создания файла \{e.getMessage()}");

            }
        } else System.out.println("Такого файла нет");
    }
    public void decoding(){
        ArrayList<Character> list = ALPHABET();
        Path forReader = enteringFile();
        Path forWrite = Path.of(STR."\{forReader.getParent()}\\DecodingFile.txt");

        if (Files.exists(forReader)) {
            int key = getKey();
            HashMap<Character, Character> map = new HashMap<>();

            for (int i = key; i < list.size(); i++) {
                map.put(list.get(i), list.get((i - key) % list.size()));
            }

            try (BufferedWriter buf = Files.newBufferedWriter(Files.createFile(forWrite))) {
                char[] arrayUserFails = Files.readString(forReader).toCharArray();
                char[] arrayFiles = new char[arrayUserFails.length];
                for (int i = 0; i < arrayUserFails.length; i++) {
                    arrayFiles[i] = map.get(arrayUserFails[i]);
                }
                buf.write(arrayFiles);
                System.out.println(STR."Создан файл. Путь: \{forWrite}");

            } catch (IOException e) {
                System.out.println(STR."Ошибка создания файла \{e.getMessage()}");

            }
        } else System.out.println("По указанноу пути такого файла нет");
    }


    public int mainMenu() {
        System.out.println("Введите цифру меню");
        System.out.println("1. Шифрование текста.\n2. Расшифрование текста.");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void inputMenuNumber() {
        switch (mainMenu()) {
            case 1 -> encryption();
            case 2 -> decoding();
            default -> System.out.println("Такого варианта нет");
        }
    }

    public Path enteringFile() {
        System.out.println("Укажите путь к файлу с расширением txt");
        Scanner scanner = new Scanner(System.in);
        String way = scanner.nextLine();
        return Paths.get(way);
    }

    public void main(String[] args) {
        inputMenuNumber();

    }
}

