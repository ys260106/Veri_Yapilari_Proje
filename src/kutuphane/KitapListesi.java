package kutuphane;

public class KitapListesi {
   private Kitap bas;    //ilk kitap
   private Kitap son;   //son litap
   private int boyut; //kitap sayisini temsil ediyor.   


public KitapListesi(){
    this.bas = null;
    this.son = null;
    this.boyut = 0;
}
public Kitap getBas(){
    return bas;
}
public Kitap getSon(){
    return son;
}
public int getBoyut(){
    return boyut;
}

 // Kitap numarasina gore arar. Bulamazsa null doner.

public Kitap kitapBul(int kitapNo) {
    Kitap gecici = bas;
    while (gecici!=null) {
        if (gecici.getKitapNo() == kitapNo){
            return gecici;
        }
        gecici = gecici.sonraki;
    }
    return null;
}
//Yeni kitabı liste sonuna ekler ve aynı numaralı kitap varsa eklemez false döner returnde.
 public boolean kitapEkle(int kitapNo, String ad, String yazar, int yil) {
        if (kitapBul(kitapNo) != null) {
            System.out.println("HATA: " + kitapNo + " numarali kitap zaten kayitli!");
            return false;
        }
        Kitap yeni = new Kitap(kitapNo, ad, yazar, yil);
        if (bas == null) {          // liste bossa ilk kitap hem bas hem son
            bas = yeni;
            son = yeni;
        } else {                    // doluysa sona baglanir
            son.sonraki = yeni;
            yeni.onceki = son;
            son = yeni;
        }
        boyut++;
        return true;
    }

    
     // Kitabi kopyalariyla birlikte siler.
     // kopyalarin hepsi rafta degilse (oduncte/ayrilmis varsa) silinmez.
     
    public boolean kitapSil(int kitapNo) {
        Kitap silinecek = kitapBul(kitapNo);
        if (silinecek == null) {
            System.out.println("HATA: " + kitapNo + " numarali kitap bulunamadi!");
            return false;
        }
        if (!silinecek.tumKopyalarRaftaMi()) {
            System.out.println("HATA: " + kitapNo + " numarali kitabin oduncte veya ayrilmis kopyasi var, silinemez!");
            return false;
        }
        // UyeListesi.uyeSil ile ayni mantik: onceki ve sonrakini birbirine bagla
        if (silinecek.onceki != null) {
            silinecek.onceki.sonraki = silinecek.sonraki;
        } else {
            bas = silinecek.sonraki;          // bastaki siliniyor
        }
        if (silinecek.sonraki != null) {
            silinecek.sonraki.onceki = silinecek.onceki;
        } else {
            son = silinecek.onceki;           // sondaki siliniyor
        }
        silinecek.sonraki = null;
        silinecek.onceki = null;
        boyut--;
        return true;
    }

    
     //Kitaba yeni kopya ekler. Kopya RAFTA baslar.
     //kopyaNo BÜTÜN SİSTEMDE TEKTİR.
     
    public boolean kopyaEkle(int kitapNo, int kopyaNo) {
        Kitap kitap = kitapBul(kitapNo);
        if (kitap == null) {
            System.out.println("HATA: " + kitapNo + " numarali kitap bulunamadi, once kitabi ekleyin!");
            return false;
        }
        if (kopyaBul(kopyaNo) != null) {      // baska kitapta bile olsa ayni numara olamaz
            System.out.println("HATA: " + kopyaNo + " numarali kopya zaten var!");
            return false;
        }
        kitap.kopyaEkle(new KitapKopyasi(kopyaNo, kitapNo));
        return true;
    }


     //Kopyayi butun kitaplarda arar. Bulamazsa null doner.
     // kopyaNo tek oldugu icin kitapNo gerekmez (OduncServisi bunu kullanir).
     
    public KitapKopyasi kopyaBul(int kopyaNo) {
        Kitap gecici = bas;
        while (gecici != null) {
            KitapKopyasi k = gecici.kopyaBul(kopyaNo);   // bu kitabin kopyalarina bak
            if (k != null) {
                return k;
            }
            gecici = gecici.sonraki;
        }
        return null;
    }

  
     // Kopyayi siler.
      // Kural: kopya rafta degilse silinmez.
     
    public boolean kopyaSil(int kopyaNo) {
        KitapKopyasi kopya = kopyaBul(kopyaNo);
        if (kopya == null) {
            System.out.println("HATA: " + kopyaNo + " numarali kopya bulunamadi!");
            return false;
        }
        if (!kopya.raftaMi()) {
            System.out.println("HATA: " + kopyaNo + " numarali kopya su an " + kopya.getDurum() + ", silinemez!");
            return false;
        }
        Kitap kitap = kitapBul(kopya.getKitapNo());   // kopyanin ait oldugu kitap
        return kitap.kopyaCikar(kopyaNo);
    }

    
      //Kitaplari bir diziye kopyalar (siralama modulu kullanir).
      //Dizide yer degistirmek asil listeyi bozmaz.
    
    public Kitap[] diziyeCevir() {
        Kitap[] dizi = new Kitap[boyut];
        Kitap gecici = bas;
        int i = 0;
        while (gecici != null) {
            dizi[i] = gecici;
            i++;
            gecici = gecici.sonraki;
        }
        return dizi;
    }

    //Tum kitaplari ve kopyalarini ekrana yazdirir. 
    public void listele() {
        if (bas == null) {
            System.out.println("Kayitli Kitap Yok!");
            return;
        }
        Kitap gecici = bas;
        while (gecici != null) {
            System.out.println(gecici);
            KitapKopyasi k = gecici.getIlkKopya();
            while (k != null) {
                System.out.println("    " + k);   // kopyalari iceriden yaz
                k = k.sonraki;
            }
            gecici = gecici.sonraki;
        }
    }




















































































































}
