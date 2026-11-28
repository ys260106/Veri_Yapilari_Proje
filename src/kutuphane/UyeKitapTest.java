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
//===== UYE TESTI =====
//KAYDEDILDI = Uye No: 101 | Ad Soyad: Ali Yilmaz | Acik Odunc: 0
//KAYDEDILDI = Uye No: 102 | Ad Soyad: Ayse Kaya | Acik Odunc: 0
//KAYDEDILDI = Uye No: 103 | Ad Soyad: Mehmet Demir | Acik Odunc: 0
//HATA: 101 numarali uye zaten kayitli!
//HATA: 101 numarali uyenin aktif odunc kaydi var, silinemez!
//SILINDI =Uye No:102
//-- Bastan sona:
//Uye No: 101 | Ad Soyad: Ali Yilmaz | Acik Odunc: 0
//Uye No: 103 | Ad Soyad: Mehmet Demir | Acik Odunc: 0
//-- Sondan basa:
//Uye No: 103 | Ad Soyad: Mehmet Demir | Acik Odunc: 0
//Uye No: 101 | Ad Soyad: Ali Yilmaz | Acik Odunc: 0
//Dizideki uye sayisi: 2

//===== KITAP TESTI =====
//HATA: 1 numarali kitap zaten kayitli!
//HATA: 500 numarali kopya zaten var!
//HATA: 9 numarali kitap bulunamadi, once kitabi ekleyin!
//Kitap No: 1 | Kurk Mantolu Madonna - Sabahattin Ali (1943) | Kopya: 2
//    Kopya No: 500 | Kitap No: 1 | Durum: RAFTA
//    Kopya No: 501 | Kitap No: 1 | Durum: RAFTA
//Kitap No: 2 | Tutunamayanlar - Oguz Atay (1972) | Kopya: 1
//    Kopya No: 600 | Kitap No: 2 | Durum: RAFTA

//===== SILME KURALLARI =====
//HATA: 500 numarali kopya su an ODUNCTE, silinemez!
//Oduncteki kopya silindi mi? false
//HATA: 1 numarali kitabin oduncte veya ayrilmis kopyasi var, silinemez!
//Oduncte kopyasi olan kitap silindi mi? false
//Raftaki kopya silindi mi? true
//Kitap 2 silindi mi? true
//-- Son durum:
//Kitap No: 1 | Kurk Mantolu Madonna - Sabahattin Ali (1943) | Kopya: 1
//    Kopya No: 501 | Kitap No: 1 | Durum: RAFTA çıktısı da tam olarak böyle olmaktadır.