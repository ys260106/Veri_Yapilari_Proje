package kutuphane;

public class Uye{
    private int uyeNo;
    private String adSoyad;
    private int acikOduncSayisi;

    public Uye sonraki;
    public Uye onceki;

    public Uye(int uyeNo, String adSoyad){
        this.uyeNo = uyeNo;
        this.adSoyad = adSoyad;
        this.acikOduncSayisi = 0;
        this.sonraki = null;
        this.onceki = null;
    }
 
    public int getUyeNo(){
        return uyeNo;
    }

    public String getAdSoyad(){
        return adSoyad;
    }

    public int getAcikOduncSayisi(){
        return acikOduncSayisi;
    }

    public void oduncArtir(){
        acikOduncSayisi++;
    }

    public void oduncAzalt(){
        if(acikOduncSayisi > 0){
            acikOduncSayisi--;
        }
    }

    public boolean silinebilirMi(){
        return acikOduncSayisi == 0;
    }

    @Override 
    public String toString(){
        return "Uye No: " + uyeNo + " | Ad Soyad: " + adSoyad + " | Acik Odunc: " + acikOduncSayisi;
    }













}