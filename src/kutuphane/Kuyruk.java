package kutuphane;

/**
 * Bağlı liste tabanlı kuyruk veri yapısı (FIFO: ilk giren ilk çıkar).
 * Her kitap kopyası için ayrı bir bekleme kuyruğu oluşturulur.
 * Düğüm olarak BeklemeTalebi nesneleri doğrudan birbirine bağlanır.
 */
public class Kuyruk {

    private BeklemeTalebi bas; // Sıranın başındaki talep (ilk çıkacak olan)
    private BeklemeTalebi son; // Sıranın sonundaki talep (yeni talep buraya eklenir)
    private int boyut;         // Kuyruktaki talep sayısı

    public Kuyruk() {
        bas = null;            // Başlangıçta kuyruk boş
        son = null;
        boyut = 0;
    }

    // Talebi kuyruğun sonuna ekler: O(1)
    public void kuyrugaEkle(BeklemeTalebi talep) {
        talep.sonraki = null;    // Sona eklenen talebin arkasında eleman yok
        if (bosMu()) {           // Kuyruk boşsa
            bas = talep;         // Yeni talep hem baş
            son = talep;         // hem son olur
        } else {
            son.sonraki = talep; // Mevcut sonun arkasına bağlanır
            son = talep;         // Yeni son, eklenen talep olur
        }
        boyut++;                 // Eleman sayısı artırılır
    }

    // Kuyruğun başındaki talebi çıkarır ve döndürür: O(1)
    public BeklemeTalebi kuyruktanCikar() {
        if (bosMu()) {           // Kuyruk boşsa
            return null;         // Çıkarılacak eleman yok
        }
        BeklemeTalebi ilk = bas; // Baştaki talep saklanır
        bas = bas.sonraki;       // Baş bir sonraki talebe kaydırılır
        if (bas == null) {       // Kuyrukta eleman kalmadıysa
            son = null;          // Son da boşaltılır
        }
        ilk.sonraki = null;      // Çıkan talebin kuyrukla bağlantısı koparılır
        boyut--;                 // Eleman sayısı azaltılır
        return ilk;              // Çıkan talep döndürülür
    }

    // Baştaki talebi çıkarmadan döndürür: O(1)
    public BeklemeTalebi ilkiGoster() {
        return bas;
    }

    // Kuyruğun boş olup olmadığını döndürür
    public boolean bosMu() {
        return bas == null;
    }

    // Kuyruktaki eleman sayısını döndürür
    public int getBoyut() {
        return boyut;
    }

    // Üyenin kuyrukta olup olmadığını kontrol eder (aynı üye iki kez bekleyemez): O(n)
    public boolean kuyruktaUyeVarMi(int uyeNo) {
        BeklemeTalebi gecici = bas;           // Baştan başlanır
        while (gecici != null) {              // Kuyruğun sonuna kadar gezilir
            if (gecici.getUyeNo() == uyeNo) { // Üye bulunduysa
                return true;
            }
            gecici = gecici.sonraki;          // Sonraki talebe geçilir
        }
        return false;                         // Üye kuyrukta yok
    }

    // Kuyruktaki talepleri sırasıyla yazdırır: O(n)
    public void listele() {
        if (bosMu()) {
            System.out.println("Sırada bekleyen yok");
            return;
        }
        BeklemeTalebi gecici = bas;                   // Baştan başlanır
        int sira = 1;                                 // Sıra numarası
        while (gecici != null) {                      // Sona kadar gezilir
            System.out.println(sira + ". " + gecici); // Talep yazdırılır
            gecici = gecici.sonraki;                  // Sonraki talebe geçilir
            sira++;
        }
    }
}