import java.util.*;

public class MainApp {

    public static void main(String[] args) {

        TextProcessor processor = new TextProcessor();

        String englishText = "I study natural language processing. It is fun. Learning NLP is great.";
        String persianText = "این لذت‌بخش است. یادگیری پردازش زبان طبیعی عالی است. من مطالعه می‌کنم.";

        // LEVEL 3: phrase normalization
        englishText = englishText.replace("natural language processing", "nlp");

        List<String> enSentences = processor.splitIntoSentences(englishText);
        List<String> faSentences = processor.splitIntoSentences(persianText);

        Map<String, Double> faIDF = processor.computeIDF(faSentences);

        System.out.println("=== Alignment Results ===");

        for (String en : enSentences) {

            String[] enWords = processor.tokenize(en);

            List<String> translated = new ArrayList<>();

            for (String w : enWords) {
                translated.add(processor.translateWord(w));
            }

            String newEn = String.join(" ", translated);

            Map<String, Double> enVec = processor.computeTFIDF(newEn, faIDF);

            String bestMatch = "";
            double bestScore = -1;

            for (String fa : faSentences) {

                Map<String, Double> faVec = processor.computeTFIDF(fa, faIDF);

                double score = processor.cosineSimilarity(enVec, faVec);

                if (score > bestScore) {
                    bestScore = score;
                    bestMatch = fa;
                }
            }

            System.out.println("\nEN: " + en);
            System.out.println("FA: " + bestMatch);
            System.out.println("Score: " + bestScore);
        }
    }
}