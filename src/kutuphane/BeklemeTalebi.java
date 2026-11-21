package kutuphane;

/**
 * Ödünçte olan bir kopya için üyenin sıraya girme isteğidir.
 * Kuyruktaki her düğüm bir BeklemeTalebi taşır.
 */
public class BeklemeTalebi {
    // static: tüm talepler bu tek sayacı paylaşır, böylece her talebe farklı numara verilir
    private static int sayac = 1;

    private int talepId; // Talebin benzersiz numarası
    private int uyeNo;   // Sırada bekleyen üye
    private int kitapNo; // Beklenen kitap
    private int kopyaNo; // Beklenen kopyanın kayıt numarası
    public BeklemeTalebi sonraki; // Kuyrukta bir sonraki talep (yoksa null)

    public BeklemeTalebi(int uyeNo, int kitapNo, int kopyaNo) {
        this.talepId = sayac++; // Önce mevcut sayacı ata, sonra bir artır
        this.uyeNo = uyeNo;
        this.kitapNo = kitapNo;
        this.kopyaNo = kopyaNo;
        this.sonraki = null; // Yeni talep henüz bir kuyruğa bağlı değil
    }

    public int getTalepId() {
        return talepId;
    }

    public int getUyeNo() {
        return uyeNo;
    }

    public int getKitapNo() {
        return kitapNo;
    }

    public int getKopyaNo() {
        return kopyaNo;
    }

    @Override
    public String toString() {
        return "Talep No: " + talepId + " | Üye: " + uyeNo + " | Kitap: " + kitapNo + " | Kopya: " + kopyaNo;
    }
}
