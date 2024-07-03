package cfg;

public class CFGGenerator3 {

    private String[] generatedWords;// üretilen kelimeleri içeren bir dizi
    private int wordIndex;// dizideki son kullanılan indexi takip eden bir tam sayı

    public CFGGenerator3(int maxWordCount) {//CFGGenerator3 sınıfının kurucu metodudur. maksimum kelime sayısını parametre olarak alır ve 'generatedWord' dizisini	 ve 'wordIndex' deüerini başlatır.
        generatedWords = new String[maxWordCount];
        wordIndex = 0;
    }

    private void expand(String symbol, StringBuilder currentWord) {
        if (symbol.equals("")) {
            String word = currentWord.toString();
            if (!containsWord(word)) {
                generatedWords[wordIndex++] = word;
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

    private boolean containsWord(String word) {
        for (int i = 0; i < wordIndex; i++) {
            if (generatedWords[i] != null && generatedWords[i].equals(word)) {
                return true;
            }
        }
        return false;
    }

    public void generateTree() {
        expand("S", new StringBuilder());
        printGeneratedWords();
    }

    private void printGeneratedWords() {
        System.out.println("Üretilen Kelimeler:");
        for (int i = 0; i < wordIndex; i++) {
            System.out.print(generatedWords[i] + ", ");
        }
        System.out.println("\nTekrarlanan kelimeler:");
        for (int i = 0; i < wordIndex; i++) {
            if (isWordRepeated(i)) {
                System.out.print(generatedWords[i] + ", ");
            }
        }
    }

    private boolean isWordRepeated(int currentIndex) {
        for (int i = 0; i < currentIndex; i++) {
            if (generatedWords[i] != null && generatedWords[i].equals(generatedWords[currentIndex])) {
                return true;
            }
        }
        return false;
    }

     /* private void printGeneratedWords() {
        System.out.println("Üretilen Kelimeler:");
        for (int i = 0; i < wordIndex; i++) {
            System.out.print(generatedWords[i] + ", ");
        }
        System.out.println("\nTekrarlanan kelimeler:");
        for (int i = 0; i < wordIndex; i++) {
            if (!containsWord(generatedWords[i])) {
                System.out.print(generatedWords[i] + ", ");
            }
        }
    }*/
    

    public static void main(String[] args) {
        CFGGenerator3 treeGenerator = new CFGGenerator3(100); // Maksimum kelime sayısını belirtin
        treeGenerator.generateTree();
    }
}

