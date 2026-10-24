package src;

public class Uye {
     public int uyeNo;
     public String adSoyad;
     public int acikOduncSayisi;
     public Uye sonraki;
     public Uye onceki;

public Uye(int uyeNo, String adSoyad){
    this.uyeNo = uyeNo;
    this.adSoyad = adSoyad;
    this.acikOduncSayisi = 0;
    this.sonraki = null;
    this.onceki = null;
}
private static Uye bas = null;

public static Uye uyeBul(int uyeNo){
    Uye gecici = bas;
    while(gecici != null){
        if(gecici.uyeNo == uyeNo){
            return gecici;
        }
        gecici = gecici.sonraki;
    }
    return null;
}


public static void uyeKaydet(int uyeNo, String adSoyad){
    if(uyeBul(uyeNo) !=null ){
        System.out.println ("HATA: " + uyeNo + " numarali uye zaten sistemde kayitli!");
        return;
}
Uye yeniUye = new Uye(uyeNo, adSoyad);
if(bas == null){
    bas = yeniUye;
}else{
    Uye gecici = bas;
    while(gecici.sonraki != null){
        gecici = gecici.sonraki;
    }
    gecici.sonraki = yeniUye;

    yeniUye.onceki = gecici;
}

System.out.println("KAYDEDILDI = Uye No: " + uyeNo + " | Ad Soyad: " + adSoyad);
}

public static void main(String[] args) {
    uyeKaydet(1, "Ali Kara");
    uyeKaydet(2, "Leyla Divane");
    uyeKaydet(1, "Ayni Numarali deneme");
}
}