package kutuphane;

/**
 * Kutuphane uyesini temsil eder.
 * Ayni zamanda UyeListesi icindeki cift yonlu bagli listenin dugumudur:
 * 'sonraki' bir sonraki uyeyi, 'onceki' bir onceki uyeyi gosterir.
 */

public class Uye {
    private int uyeNo; // Uyeyi benzersiz tanimlayan numara
    private String adSoyad; // Uyenin adi ve soyadi
    private int acikOduncSayisi; // Uyenin elindeki iade edilmemis kitap sayisi

    // Liste baglantilari (listeyi gezen siniflar erisebilsin diye public)
    public Uye sonraki;
    public Uye onceki;

    /** Yeni uye olusturur. Baslangicta odunc kitabi yoktur, baglantilar bostur. */
    public Uye(int uyeNo, String adSoyad) {
        this.uyeNo = uyeNo;
        this.adSoyad = adSoyad;
        this.acikOduncSayisi = 0;
        this.sonraki = null;
        this.onceki = null;
    }

    public int getUyeNo() {
        return uyeNo;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public int getAcikOduncSayisi() {
        return acikOduncSayisi;
    }

    /** Uyeye kitap odunc verildiginde cagrilir (odunc/iade modulu kullanir). */
    public void oduncArtir() {
        acikOduncSayisi++;
    }

    /** Uye kitabi iade ettiginde cagrilir (odunc/iade modulu kullanir). */
    public void oduncAzalt() {
        if (acikOduncSayisi > 0) {
            acikOduncSayisi--;
        }
    }

    /** Uye silinebilir mi? Elinde kitap yoksa true doner. */
    public boolean silinebilirMi() {
        return acikOduncSayisi == 0;
    }

    @Override
    public String toString() {
        return "Uye No: " + uyeNo + " | Ad Soyad: " + adSoyad + " | Acik Odunc: " + acikOduncSayisi;
    }
}