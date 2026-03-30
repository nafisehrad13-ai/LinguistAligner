import java.util.*;

public class TextProcessor {

    // 1. split sentences
    public List<String> splitIntoSentences(String text) {
        List<String> sentences = new ArrayList<>();
        String regex = "(?<=[.!?؟])\\s+";
        String[] parts = text.split(regex);
        for (String p : parts) {
            sentences.add(p.trim());
        }
        return sentences;
    }

    // 2. tokenize (FIXED + Persian support)
    public String[] tokenize(String sentence) {
        return sentence
                .toLowerCase()
                .replaceAll("[^\\p{L}\\p{Nd}\\s]", "")
                .replace("\u200C", "") // حذف نیم فاصله فارسی
                .split("\\s+");
    }

    // 3. stopwords (improved)
    public List<String> removeStopWords(String[] words) {
        List<String> stopWords = Arrays.asList(
                "و","در","از","است","من","به","با","که","این","آن",
                "i","is","it","a","the",
                "می","میکنم","می‌کنم","بود","شد"
        );

        List<String> filtered = new ArrayList<>();

        for (String w : words) {
            if (!stopWords.contains(w) && !w.isEmpty()) {
                filtered.add(w);
            }
        }
        return filtered;
    }

    // 4. TF
    public Map<String, Double> computeTF(String sentence) {
        Map<String, Double> tf = new HashMap<>();
        String[] words = tokenize(sentence);

        for (String w : words) {
            if (w.isEmpty()) continue;
            tf.put(w, tf.getOrDefault(w, 0.0) + 1.0);
        }

        int total = words.length;
        for (String key : tf.keySet()) {
            tf.put(key, tf.get(key) / total);
        }

        return tf;
    }

    // 5. IDF
    public Map<String, Double> computeIDF(List<String> sentences) {
        Map<String, Double> idf = new HashMap<>();
        int N = sentences.size();

        for (String sentence : sentences) {
            Set<String> seen = new HashSet<>(Arrays.asList(tokenize(sentence)));

            for (String word : seen) {
                if (word.isEmpty()) continue;
                idf.put(word, idf.getOrDefault(word, 0.0) + 1);
            }
        }

        for (String word : idf.keySet()) {
            idf.put(word, Math.log((double) N / idf.get(word)));
        }

        return idf;
    }

    // 6. TF-IDF
    public Map<String, Double> computeTFIDF(String sentence, Map<String, Double> idf) {
        Map<String, Double> tf = computeTF(sentence);
        Map<String, Double> tfidf = new HashMap<>();

        for (String word : tf.keySet()) {
            double idfValue = idf.getOrDefault(word, 0.0);
            tfidf.put(word, tf.get(word) * idfValue);
        }

        return tfidf;
    }

    // 7. cosine similarity
    public double cosineSimilarity(Map<String, Double> v1, Map<String, Double> v2) {

        double dot = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (String key : v1.keySet()) {
            dot += v1.get(key) * v2.getOrDefault(key, 0.0);
        }

        for (double val : v1.values()) {
            norm1 += val * val;
        }

        for (double val : v2.values()) {
            norm2 += val * val;
        }

        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2) + 1e-9);
    }

    // 8. SMART DICTIONARY (multi-level)

    public String translateWord(String word) {

        word = word.toLowerCase();

        // Level 2: partial matching (smart)
        if (word.startsWith("process")) return "پردازش";
        if (word.startsWith("learn")) return "یادگیری";

        Map<String, List<String>> dict = new HashMap<>();

        // Level 1: multi-word mapping
        dict.put("nlp", Arrays.asList("پردازش", "زبان"));
        dict.put("learning", Arrays.asList("یادگیری"));
        dict.put("study", Arrays.asList("مطالعه"));
        dict.put("great", Arrays.asList("عالی"));
        dict.put("fun", Arrays.asList("لذت", "لذت‌بخش"));

        if (dict.containsKey(word)) {
            return String.join(" ", dict.get(word));
        }

        return word;
    }
}