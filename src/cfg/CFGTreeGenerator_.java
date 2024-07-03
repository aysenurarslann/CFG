package cfg;

public class CFGTreeGenerator_ {

    private String[] generatedWords;// üretilen kelimelerin depolanacağı bir dizi tanımlar
    private int wordIndex; // Üretilen kelimelerin depolanacağı bir dizi tanımlar

    public CFGTreeGenerator_(int maxWordCount) {//Sınıfın yapıcı metodunu tanımlar. Bu metod en fazla kaç kelime üretileceğini 
    	// belirten bir parametre alır ve gerekli  başlangıç ayarlamalarını yapar.
    	//Yapıcı Metotlar (Constructors) Nesnenin ilk oluşturulma anında çalışan metot tipidir
        generatedWords = new String[maxWordCount];// 'generateWords' dizisini belirten 'maxWordCount' büyüklüğünde bir dizi olarak oluşturur
        wordIndex = 0; //başlangıçta üretilen kelime sayısını sıfıra ayarlar
    }

    private void expand(String symbol, StringBuilder currentWord) {// 'expand ' adında bir metod tanımlar. bu metod verilen sembolü genişletir
    	//ve üretilen kelimeleri takip eder.
        if (symbol.equals("")) {// sembol boşsa yani genişletme tamamlandıysa
            String word = currentWord.toString();// şu andaki kelimeyi bir stringe çevirir
            if (!containsWord(word, wordIndex)) {
                generatedWords[wordIndex++] = word;
                //Eğer bu kelime daha önce üretilmişse , 'generatedWords' dizisine ekler ve kelime sayısı artırılır.
            }
            return;// metodu sonlandırır
        }

        char firstChar = symbol.charAt(0);//sembolün ilk karakterini alır
        if (Character.isUpperCase(firstChar)) {//Eğer ilk karakter büyük harfse, yani non-terminal sembolse:
            // Büyük harf ise non-terminal sembol, genişlet
            for (String choice : getProductionChoices(firstChar)) {
                expand(choice + symbol.substring(1), currentWord);
                //Bu non-terminal sembolü genişletir ve yeni sembolü ile birlikte genişletme işlemini devam ettrir
            }
        } else {// İlk karakter küçük harfse, yani terminal sembolse
            // Küçük harf ise terminal sembol, ekle ve devam et
            currentWord.append(firstChar);// terminal sembolü şu andaki kelimeye ekler
            expand(symbol.substring(1), currentWord);//kalan sembolü ile genişletme işlemine devam edr
            currentWord.deleteCharAt(currentWord.length() - 1);//eklenen terminal sembolü geri çıkarı
        }
    }

    private String[] getProductionChoices(char nonTerminal) {// nu metot non-terminal sembollere karşılık gelen üretim seçeneklerini döndürür
        switch (nonTerminal) {
            case 'S':
                return new String[]{"aa", "bX", "aXX"};
            case 'X':
                return new String[]{"ab", "b"};
            default:
                return new String[]{};
        }
    }

    private boolean containsWord(String word, int endIndex) {
    	//bu metot, daha önce üretilen kelimeler içinde belirli bir kelimenin olup olmadığını kontrol eder.
        for (int i = 0; i < endIndex; i++) {
            if (generatedWords[i] != null && generatedWords[i].equals(word)) {
                return true;
            }
        }
        return false;
    }

    public void generateTree() {//bu metot, ağacı üretmek için başlatma noktasıdır
        expand("S", new StringBuilder());//'S' sembolüyle başlayarak genişletme işlemine başlar
        printGeneratedWords();//üretilen kelimeleri ekrana basan metodu çağırır
    }

    private void printGeneratedWords() { // üretilen kelimeleri ekrana yazdırmak için
        System.out.println("Üretilen Kelimeler:");
        for (int i = 0; i < wordIndex; i++) {
            System.out.print(generatedWords[i] + ", ");
        }
        System.out.println("\nTekrarlanan kelimeler:");
        for (int i = 0; i < wordIndex; i++) {
            if (!containsWord(generatedWords[i], i)) {
                System.out.print(generatedWords[i] + ", ");
            }
        }
    }

    public static void main(String[] args) {
        CFGTreeGenerator_ treeGenerator = new CFGTreeGenerator_(100); // Maksimum kelime sayısını belirtin
        // 'CFGTreeGenerator_' sınıfından bir nesne oluşturur ve maksimum kelime sayısını belirtir
        treeGenerator.generateTree();// ağacı üretmek için 'generateTree' metodu çağırılır
    }
}

