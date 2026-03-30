import java.util.List;
import java.util.Map;

public class MainApp {
    public static void main(String[] args) {
        TextProcessor processor = new TextProcessor();

        // متن نمونه برای تحلیل (می‌توانی جملات بیشتری اضافه کنی)
        String englishText = "I study NLP. It is fun. Learning NLP is great.";
        String persianText = "من پردازش زبان طبیعی مطالعه می‌کنم. این لذت‌بخش است. یادگیری پردازش زبان طبیعی عالی است.";

        // ۱. جدا کردن جملات
        List<String> enSentences = processor.splitIntoSentences(englishText);
        List<String> faSentences = processor.splitIntoSentences(persianText);

        System.out.println("=== گزارش تحلیل هوشمند زبانی ===");

        for (int i = 0; i < enSentences.size(); i++) {
            // ۲. تکه‌تکه کردن و پاکسازی
            String[] enWords = processor.tokenize(enSentences.get(i));
            String[] faWords = processor.tokenize(faSentences.get(i));

            List<String> enClean = processor.removeStopWords(enWords);
            List<String> faClean = processor.removeStopWords(faWords);

            // ۳. نمایش جفت‌ها و کلمات کلیدی
            System.out.println("\n[جمله " + (i + 1) + "]");
            System.out.println("EN Keywords: " + enClean);
            System.out.println("FA Keywords: " + faClean);
        }

        // ۴. تحلیل فراوانی کل متن فارسی (برای کشف موضوع اصلی)
        // تمام کلمات جملات فارسی را در یک لیست می‌ریزیم
        String fullPersian = String.join(" ", faSentences);
        List<String> allFaClean = processor.removeStopWords(processor.tokenize(fullPersian));
        Map<String, Integer> freq = processor.countWordFrequency(allFaClean);

        System.out.println("\n=== پرتکرارترین کلمات (موضوع متن) ===");
        System.out.println(freq);
    }
}