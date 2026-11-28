package kutuphane;

  //Bir kitabin fiziksel kopyasi (rafta duran tek bir nusha).
  //kopyaNo butun sistemde tektir.
  //Kitap icindeki kopya listesinin dugumudur: 'sonraki' ayni kitabin bir sonraki kopyasi.
 // durum ve ayrilanUyeNo alanlarini SADECE OduncServisi degistirir.
 
public class KitapKopyasi {

    // Kopyanin 3 durumu. Herkes bu sabitleri kullanir.
    public static final String RAFTA = "RAFTA";       // kutuphanede, alinabilir
    public static final String ODUNCTE = "ODUNCTE";   // bir uyede
    public static final String AYRILDI = "AYRILDI";   // iade edildi, siradaki uyeye ayrildi

    private int kopyaNo;        // kopyanin numarasi (sistemde tek)
    private int kitapNo;        // hangi kitaba ait
    private String durum;       // RAFTA / ODUNCTE / AYRILDI
    private int ayrilanUyeNo;   // AYRILDI ise kime ayrildi, degilse -1

    public KitapKopyasi sonraki;   // ayni kitabin bir sonraki kopyasi

    // Yeni kopya rafta ve kimseye ayrilmamis olarak baslar
    public KitapKopyasi(int kopyaNo, int kitapNo) {
        this.kopyaNo = kopyaNo;
        this.kitapNo = kitapNo;
        this.durum = RAFTA;
        this.ayrilanUyeNo = -1;
        this.sonraki = null;
    }

    public int getKopyaNo() {
        return kopyaNo;
    }

    public int getKitapNo() {
        return kitapNo;
    }

    public String getDurum() {
        return durum;
    }

    
      //Durumu degistirir.
      //Gelen metin (dosyadan okunmus olabilir) hangi sabite esitse alana o sabit yazilir.
      //Gecersiz metin gelirse durum degismez.
     
    public void setDurum(String d) {
        if (ayniMetinMi(d, RAFTA)) {
            durum = RAFTA;
        } else if (ayniMetinMi(d, ODUNCTE)) {
            durum = ODUNCTE;
        } else if (ayniMetinMi(d, AYRILDI)) {
            durum = AYRILDI;
        } else {
            System.out.println("HATA: Gecersiz kopya durumu: " + d);
        }
    }

    public int getAyrilanUyeNo() {
        return ayrilanUyeNo;
    }

    public void setAyrilanUyeNo(int uyeNo) {
        this.ayrilanUyeNo = uyeNo;
    }

    // Kolaylik metotlari: durum hep sabitlerden biri oldugu icin == ile karsilastirmak yeterli
    public boolean raftaMi() {
        return durum == RAFTA;
    }

    public boolean oduncteMi() {
        return durum == ODUNCTE;
    }

    public boolean ayrildiMi() {
        return durum == AYRILDI;
    }

    // Iki metni harf harf karsilastirir (hazir equals yerine)
    private static boolean ayniMetinMi(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        if (a.length() != b.length()) {      // uzunluk farkliysa esit degil
            return false;
        }
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {   // ilk farkli harfte dur
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String metin = "Kopya No: " + kopyaNo + " | Kitap No: " + kitapNo + " | Durum: " + durum;
        if (ayrildiMi()) {
            metin = metin + " (Uye " + ayrilanUyeNo + " icin)";
        }
        return metin;
    }
}