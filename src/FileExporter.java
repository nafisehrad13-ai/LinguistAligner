import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileExporter {

    // این متد دو لیست (انگلیسی و فارسی) را می‌گیرد و در یک فایل ذخیره می‌کند
    public void saveToCSV(List<String> englishList, List<String> persianList, String fileName) {

        // FileWriter ابزاری در جاواست که اجازه می‌دهد روی فایل بنویسیم
        try (FileWriter writer = new FileWriter(fileName)) {

            // نوشتن تیتر ستون‌ها (Header)
            writer.append("English,Persian\n");

            // پیدا کردن تعداد جملات (تا جایی که هر دو لیست جمله دارند)
            int size = Math.min(englishList.size(), persianList.size());

            for (int i = 0; i < size; i++) {
                // پاک کردن کاما از داخل جملات (چون در CSV کاما به معنی ستون بعدی است)
                String en = englishList.get(i).replace(",", "");
                String fa = persianList.get(i).replace(",", "");

                // نوشتن جمله انگلیسی + یک کاما + جمله فارسی + رفتن به خط بعد
                writer.append(en).append(",").append(fa).append("\n");
            }

            System.out.println("✅ فایل با موفقیت در اینجا ذخیره شد: " + fileName);

        } catch (IOException e) {
            System.out.println("❌ خطایی در ذخیره فایل رخ داد: " + e.getMessage());
        }
    }
}