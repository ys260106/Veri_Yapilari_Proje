package kutuphane;


  //Kutuphanedeki bir kitap (eser).
  //KitapListesi'nin dugumudur (Uye ile ayni desen): 'sonraki' ve 'onceki'.
  //Kitabin kopyalari kendi icinde tek yonlu liste olarak tutulur:
 // ilkKopya -> sonraki -> sonraki -> null
 
public class Kitap {
    private int kitapNo;              // kitabin numarasi (tek)
    private String ad;                // kitabin adi
    private String yazar;             // yazari
    private int yayinYili;            // yayin yili
    private KitapKopyasi ilkKopya;    // kopya listesinin basi
    private int kopyaSayisi;          // kac kopyasi var

    // Liste baglantilari (Uye'deki gibi public)
    public Kitap sonraki;
    public Kitap onceki;

    // Yeni kitabin kopyasi yoktur, baglantilari bostur
    public Kitap(int kitapNo, String ad, String yazar, int yayinYili) {
        this.kitapNo = kitapNo;
        this.ad = ad;
        this.yazar = yazar;
        this.yayinYili = yayinYili;
        this.ilkKopya = null;
        this.kopyaSayisi = 0;
        this.sonraki = null;
        this.onceki = null;
    }

    public int getKitapNo() {
        return kitapNo;
    }

    public String getAd() {
        return ad;
    }

    public String getYazar() {
        return yazar;
    }

    public int getYayinYili() {
        return yayinYili;
    }

    public KitapKopyasi getIlkKopya() {
        return ilkKopya;
    }

    public int getKopyaSayisi() {
        return kopyaSayisi;
    }

    // Kopyayi kopya listesinin sonuna ekler. 
    public void kopyaEkle(KitapKopyasi k) {
        k.sonraki = null;
        if (ilkKopya == null) {              // hic kopya yoksa ilk kopya bu olur
            ilkKopya = k;
        } else {                             // varsa en sona kadar gidip baglanir
            KitapKopyasi gecici = ilkKopya;
            while (gecici.sonraki != null) {
                gecici = gecici.sonraki;
            }
            gecici.sonraki = k;
        }
        kopyaSayisi++;
    }

    // Bu kitabin kopyalarinda arar. Bulamazsa null doner.
    public KitapKopyasi kopyaBul(int kopyaNo) {
        KitapKopyasi gecici = ilkKopya;
        while (gecici != null) {
            if (gecici.getKopyaNo() == kopyaNo) {
                return gecici;
            }
            gecici = gecici.sonraki;
        }
        return null;
    }

    
     // Kopyayi listeden cikarir (KitapListesi.kopyaSil kullanir).
     // Tek yonlu listede silmek icin bir onceki dugumu de takip ederiz.
     
    public boolean kopyaCikar(int kopyaNo) {
        KitapKopyasi onceki = null;
        KitapKopyasi gecici = ilkKopya;
        while (gecici != null) {
            if (gecici.getKopyaNo() == kopyaNo) {
                if (onceki == null) {
                    ilkKopya = gecici.sonraki;          // bastaki kopya siliniyor
                } else {
                    onceki.sonraki = gecici.sonraki;    // aradaki/sondaki kopya atlanir
                }
                gecici.sonraki = null;
                kopyaSayisi--;
                return true;
            }
            onceki = gecici;
            gecici = gecici.sonraki;
        }
        return false;
    }

    // Butun kopyalar rafta mi? Kitap silinmeden once bakilir. 
    public boolean tumKopyalarRaftaMi() {
        KitapKopyasi gecici = ilkKopya;
        while (gecici != null) {
            if (!gecici.raftaMi()) {
                return false;
            }
            gecici = gecici.sonraki;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kitap No: " + kitapNo + " | " + ad + " - " + yazar + " (" + yayinYili + ")"
                + " | Kopya: " + kopyaSayisi;
    }
}































