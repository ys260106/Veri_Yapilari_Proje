package kutuphane;

/**
 * Butun uyeleri cift yonlu bagli liste ile RAM'de yonetir.
 * Hazir koleksiyon (ArrayList, LinkedList vb.) kullanilmaz.
 * Diger moduller uyelere bu sinif uzerinden ulasir.
 */

public class UyeListesi {
    private Uye bas;      // listedeki ilk uye
    private Uye son;      // listedeki son uye (sona eklemeyi hizlandirir)
    private int boyut;    // toplam uye sayisi

    public UyeListesi(){
        this.bas = null;
        this.son = null;
        this.boyut = 0;
    }

    public Uye getBas(){
        return bas;
    }
    public Uye getSon(){
        return son;
    }
    public int getBoyut(){
        return boyut;
    }

 /**
     * Uye numarasina gore uyeyi bulur.
     * Odunc verme ve arama islemlerinden once uyenin var olup olmadigini kontrol etmek icin kullanilir.
     * Bulamazsa null doner.
     */

    public Uye uyeBul(int uyeNo){
        Uye gecici = bas;
        while(gecici != null){
            if(gecici.getUyeNo() == uyeNo)
                return gecici;
            gecici = gecici.sonraki;
            }
        return null;
    }

 /**
     * Yeni uyeyi sisteme kaydeder, listenin sonuna ekler.
     * Ayni numarali uye varsa kaydetmez ve false doner.
     */

public boolean uyeKaydet(int uyeNo , String adSoyad){
    if (uyeBul(uyeNo) != null){
        System.out.println("HATA: " + uyeNo + " numarali uye zaten kayitli!");
    return false;    
    }
Uye yeni = new Uye(uyeNo, adSoyad);
if (bas ==null){         // liste bossa ilk uye hem bas hem son olur
    bas = yeni;
    son = yeni;
}else {                  // doluysa sona baglanir
    son.sonraki = yeni;  
    yeni.onceki = son;
    son = yeni;
}
boyut++;
System.out.println("KAYDEDILDI = " + yeni);
return true;
}

/**
     * Uyeyi sistemden siler.
     * Kural: uyenin aktif odunc kitabi varsa silinmez.
     * Silerken dugumun onceki/sonraki baglantilari birbirine baglanir,
     * sonra dugumun kendi baglantilari temizlenir.
     */

public boolean uyeSil(int uyeNo) {
Uye silinecek = uyeBul(uyeNo);
if (silinecek ==null){
    System.out.println("HATA: " + uyeNo + " numarali uye bulunamadi!");
    return false;
}
if (!silinecek.silinebilirMi()){
System.out.println("HATA: " + uyeNo + " numarali uyenin aktif odunc kaydi var, silinemez!");
return false;
}
if (silinecek.onceki !=null){
    silinecek.onceki.sonraki = silinecek.sonraki;
}else{
    bas = silinecek.sonraki;  // bastaki uye siliniyor
}
if (silinecek.sonraki != null){
    silinecek.sonraki.onceki = silinecek.onceki;
} else {
    son = silinecek.onceki;  // sondaki uye siliniyor
}
silinecek.sonraki = null;   // bellek sizintisini onlemek icin
silinecek.onceki = null;    // referanslar temizlenir
boyut--;
System.out.println("SILINDI =Uye No:" + uyeNo);
return true;
}
/**
     * yeni ekledim : Uyeleri bir diziye kopyalar.
     * Siralama ve sorgu modulleri bu diziyi kullanir.
     * Dizide yer degistirmek asil listeyi bozmaz.
     */
public Uye[] diziyeCevir() {
    Uye[] dizi = new Uye[boyut];   // uye sayisi kadar yer ac
    Uye gecici = bas;              // bastan basla
    int i = 0;
    while (gecici != null) {       // listenin sonuna kadar git
        dizi[i] = gecici;          // uyeyi diziye koy
        i++;
        gecici = gecici.sonraki;   // bir sonraki uyeye gec
    }
    return dizi;
}
/** Tum uyeleri bastan sona ekrana yazdirir. */
public void listele(){
    if (bas == null){
        System.out.println("Kayitli Uye Yok!");
        return;
    }

Uye gecici = bas;
while (gecici != null){
        System.out.println(gecici);
        gecici = gecici.sonraki;
    }
}
/** Sondan basa yazdirir. Listenin cift yonlu calistigini gosterir. */
public void tersListele(){
    Uye gecici = son;
    while (gecici != null){
        System.out.println(gecici);
        gecici = gecici.onceki;
    }
}
}

