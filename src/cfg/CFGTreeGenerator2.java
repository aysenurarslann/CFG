package cfg;

import java.util.ArrayList;
import java.util.List;

public class CFGTreeGenerator2 {

    private List<String> generatedWords;

    public CFGTreeGenerator2() {
        generatedWords = new ArrayList<>();
    }

    private void expand(String symbol, StringBuilder currentWord) {
        if (symbol.equals("")) {
            String word = currentWord.toString();
            if (!generatedWords.contains(word)) {
                generatedWords.add(word);
            }
            return;
        }

        char firstChar = symbol.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            // Büyük harf ise non-terminal sembol, genişlet
            for (String choice : getProductionChoices(firstChar)) {
                expand(choice + symbol.substring(1), currentWord);
            }
        } else {
            // Küçük harf ise terminal sembol, ekle ve devam et
            currentWord.append(firstChar);
            expand(symbol.substring(1), currentWord);
            currentWord.deleteCharAt(currentWord.length() - 1);
        }
    }

    private String[] getProductionChoices(char nonTerminal) {
        switch (nonTerminal) {
            case 'S':
                return new String[]{"aa", "bX", "aXX"};
            case 'X':
                return new String[]{"ab", "b"};
            default:
                return new String[]{};
        }
    }

    public void generateTree() {
        expand("S", new StringBuilder());
        printGeneratedWords();
    }

    private void printGeneratedWords() {
        System.out.println("Üretilen Kelimeler:");
        for (String word : generatedWords) {
            System.out.print(word + ", ");
        }
        System.out.println("\nTekrarlanan kelimeler:");
        List<String> uniqueWords = getUniqueWords();
        for (String word : uniqueWords) {
            System.out.print(word + ", ");
        }
    }

    private List<String> getUniqueWords() {
        List<String> uniqueWords = new ArrayList<>();
        for (String word : generatedWords) {
            if (!uniqueWords.contains(word)) {
                uniqueWords.add(word);
            }
        }
        return uniqueWords;
    }

    public static void main(String[] args) {
        CFGTreeGenerator treeGenerator = new CFGTreeGenerator();
        treeGenerator.generateTree();
    }
}
