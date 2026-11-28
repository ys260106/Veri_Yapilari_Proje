package kutuphane;

/**
 * Kayit ve silme modulunun konsol testi.
 * Calistirma: java -cp out kutuphane.UyeKitapTest
 */
public class UyeKitapTest {

    public static void main(String[] args) {

        // ---------- UYELER ----------
        System.out.println("===== UYE TESTI =====");
        UyeListesi uyeler = new UyeListesi();
        uyeler.uyeKaydet(101, "Ali Yilmaz");
        uyeler.uyeKaydet(102, "Ayse Kaya");
        uyeler.uyeKaydet(103, "Mehmet Demir");
        uyeler.uyeKaydet(101, "Tekrar");               // HATA vermeli: ayni numara

        uyeler.uyeBul(101).oduncArtir();               // 101'in elinde kitap var
        uyeler.uyeSil(101);                            // HATA vermeli: elinde kitap var
        uyeler.uyeBul(101).oduncAzalt();               // kitabi iade etti

        uyeler.uyeSil(102);                            // ortadaki uye silinir
        System.out.println("-- Bastan sona:");
        uyeler.listele();                              // 101, 103
        System.out.println("-- Sondan basa:");
        uyeler.tersListele();                          // 103, 101

        Uye[] uyeDizisi = uyeler.diziyeCevir();
        System.out.println("Dizideki uye sayisi: " + uyeDizisi.length);   // 2

        // ---------- KITAPLAR VE KOPYALAR ----------
        System.out.println();
        System.out.println("===== KITAP TESTI =====");
        KitapListesi kitaplar = new KitapListesi();
        kitaplar.kitapEkle(1, "Kurk Mantolu Madonna", "Sabahattin Ali", 1943);
        kitaplar.kitapEkle(2, "Tutunamayanlar", "Oguz Atay", 1972);
        kitaplar.kitapEkle(1, "Baska Kitap", "Biri", 2000);   // HATA vermeli: ayni numara

        kitaplar.kopyaEkle(1, 500);
        kitaplar.kopyaEkle(1, 501);
        kitaplar.kopyaEkle(2, 600);
        kitaplar.kopyaEkle(2, 500);                    // HATA vermeli: 500 zaten var
        kitaplar.kopyaEkle(9, 700);                    // HATA vermeli: 9 numarali kitap yok
        kitaplar.listele();

        // ---------- SILME KURALLARI ----------
        System.out.println();
        System.out.println("===== SILME KURALLARI =====");
        KitapKopyasi kopya = kitaplar.kopyaBul(500);
        kopya.setDurum(KitapKopyasi.ODUNCTE);          // OduncServisi'nin yapacagini taklit
        System.out.println("Oduncteki kopya silindi mi? " + kitaplar.kopyaSil(500));   // false
        System.out.println("Oduncte kopyasi olan kitap silindi mi? " + kitaplar.kitapSil(1)); // false

        kopya.setDurum(KitapKopyasi.RAFTA);            // kopya rafa dondu
        System.out.println("Raftaki kopya silindi mi? " + kitaplar.kopyaSil(500));    // true
        System.out.println("Kitap 2 silindi mi? " + kitaplar.kitapSil(2));            // true

        System.out.println("-- Son durum:");
        kitaplar.listele();                            // sadece kitap 1 ve kopya 501
    }
}