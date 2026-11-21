package kutuphane;

import java.time.LocalDate; // Gün-ay-yıl olarak tarih tutmamızı sağlar

/**
 * Bir ödünç işlemini temsil eder.
 * Hangi üye, hangi kitabın hangi kopyasını aldı ve ne zaman iade etmesi gerekiyor bilgisini tutar.
 */
public class Odunc {
    private int oduncId;            // Ödünç işleminin benzersiz numarası
    private int kopyaNo;            // Ödünç verilen kopyanın kayıt numarası
    private int uyeNo;              // Kitabı alan üye
    private int kitapNo;            // Kopyanın ait olduğu kitap
    private LocalDate beklenenIade; // Kitabın en geç iade edilmesi gereken tarih
    private boolean iadeEdildi;     // Kitap geri geldi mi?

    public Odunc(int oduncId, int kopyaNo, int uyeNo, int kitapNo, LocalDate beklenenIade) {
        this.oduncId = oduncId;
        this.kopyaNo = kopyaNo;
        this.uyeNo = uyeNo;
        this.kitapNo = kitapNo;
        this.beklenenIade = beklenenIade;
        this.iadeEdildi = false; // Yeni ödünç alınan kitap henüz iade edilmemiştir
    }

    public int getOduncId() {
        return oduncId;
    }

    public int getKopyaNo() {
        return kopyaNo;
    }

    public int getUyeNo() {
        return uyeNo;
    }

    public int getKitapNo() {
        return kitapNo;
    }

    public LocalDate getBeklenenIade() {
        return beklenenIade;
    }

    public boolean isIadeEdildi() {
        return iadeEdildi;
    }

    /** Kitap geri geldiğinde çağrılır. */
    public void iadeEt() {
        this.iadeEdildi = true;
    }

    @Override
    public String toString() {
        return "Ödünç No: " + oduncId + " | Üye: " + uyeNo + " | Kitap: " + kitapNo
                + " | Kopya: " + kopyaNo + " | Son Tarih: " + beklenenIade
                + " | Durum: " + (iadeEdildi ? "İade edildi" : "Üyede");
    }
}
