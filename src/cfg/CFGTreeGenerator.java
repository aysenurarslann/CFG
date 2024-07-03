package cfg;
/*bu java programı cfg ağacı oluşturan bir sınıf içermektedir. cgf yi temsil eden
 ve belirli üretim kurallarını içeren bir ağacı oluşturur ve bu ağaç üzerinden 
 rastgele kelimeler üretir*/
import java.util.HashSet;
import java.util.Set;

public class CFGTreeGenerator {//

    private Set<String> generatedWords;// bu set, üretilen kelimeleri tutar ve tekrar eden kelimlerin önlenmesine yardımcı olur
    

    public CFGTreeGenerator() { // bu sınıf bir nesnesi oluşturulduğunda çağırılır ve 'generatedwords' setini başlatır
        generatedWords = new HashSet<>();
    }

    private void expand(String symbol, StringBuilder currentWord) {//bu metot bir sembolü genişletmek için kullanılı. Eğer sembol boşsa,
    	//mevcut kelimeyi 'generatesWords' setine ekler. Aksi taktirde. Sembolün ilk karakterine bakar. Eğer bu büyük harfse(non-Terminal sembol)
    	// ilgili üretim seçeneklerini alır ve genişletmeye devam eder. Eğer küçük harfse (terminal sembol), kelimeye ekler ve genişletmeye devam eder.
        if (symbol.equals("")) {
            generatedWords.add(currentWord.toString());
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

    private String[] getProductionChoices(char nonTerminal) {/*Bu metot, belirli bir non-terminal sembol için üretim seçenekleri sağlar. 
    Örneğin,'S' için {"aa","bX","aXX} ve 'X' için {"ab","b"} gibi*/
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

    private void printGeneratedWords() { //Ekrana yazdırma
        System.out.println("Üretilen Kelimeler:");
        for (String word : generatedWords) {
            System.out.print(word + ", ");
        }
        System.out.println("\nTekrarlanan kelimeler:");
        Set<String> uniqueWords = new HashSet<>(generatedWords);
        for (String word : uniqueWords) {
            System.out.print(word + ", ");
        }
    }

    public static void main(String[] args) {/*programı çalıştırmak için*/
        CFGTreeGenerator treeGenerator = new CFGTreeGenerator();//CFGTreeGenerator örneğini oluşturur
        treeGenerator.generateTree();//generateTree metodu çağırılarak .cfg ağacı oluşturur, üretilen kelimeler yazdırılır
    }
}

/*Bu programın çalışma mantığı şu şekildedir:

generateTree metodu, 'S' sembolü ile genişletmeye başlar ve tüm olası genişletmeleri yapar.
Her genişleme sonucunda elde edilen kelimeler generatedWords set'ine eklenir.
printGeneratedWords metodu, tüm üretilen kelimeleri ve tekrarlanan kelimeleri ekrana yazdırır.
Bu tür bir CFG kullanarak, belirli bir dilin sentaksını veya gramerini modelleyebilir ve rastgele kelimeler üreterek bu dildeki yapıları inceleyebilirsiniz.*/