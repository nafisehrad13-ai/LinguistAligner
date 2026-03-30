import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextProcessor {

    // ۱. جدا کردن جملات
    public List<String> splitIntoSentences(String text) {
        List<String> sentences = new ArrayList<>();
        String regex = "(?<=[.!?؟])\\s+";
        String[] parts = text.split(regex);
        for (String p : parts) {
            sentences.add(p.trim());
        }
        return sentences;
    }

    // ۲. تکه‌تکه کردن به کلمات
    public String[] tokenize(String sentence) {
        return sentence.split("\\s+");
    }

    // ۳. حذف کلمات بی‌اثر (Stop Words)
    public List<String> removeStopWords(String[] words) {
        List<String> stopWords = Arrays.asList("و", "در", "از", "است", "من", "به", "با", "که", "این", "آن", "i", "is", "it", "a", "the");
        List<String> filteredWords = new ArrayList<>();

        for (String w : words) {
            if (!stopWords.contains(w.toLowerCase()) && !w.isEmpty()) {
                filteredWords.add(w);
            }
        }
        return filteredWords;
    }

    // ۴. شمارش تکرار کلمات
    public Map<String, Integer> countWordFrequency(List<String> words) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String w : words) {
            frequencyMap.put(w, frequencyMap.getOrDefault(w, 0) + 1);
        }
        return frequencyMap;
    }
}