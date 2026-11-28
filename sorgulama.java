import java.util.HashMap; //kullanıcagım sorgulama veri yapılarından biri (key-value) mantıgı ıle calısır 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class Kitap {
    String kayitNumarasi;
    String ad;
    String yazar;
    boolen oduncVerildimi;

    public Kitap(String kayitNumarasi, String ad, String yazar){

        this.kayitNumarasi = kayitNumarasi;
        this.ad = ad;
        this.yazar = yazar;
        this.oduncVerildimi = false;
    }
    


    @Override //bu ksıım kulladıgımız kutuphaneden gelıor normalde hafıza adresını yazdırıyor ancak biz bu methodu ezerek anlamlı bır cıktı saglattırıyoruz
    public String toString(){
        return "Kayıt NO:" + kayitnumarasi + " | " + ad + " - " + yazar;   
    }
}

public class SorguSistemi{

    //private kullanıyrzu cunku bu sorgu sistemi listesine dışardan bırılerının ulaşmasını istemıyoruz
    //burada bır harita  tanımlıyoruz aslında normalde aşagıda nesneyı olsutururken sol tarfta < > içinde
    //sol taraf yani anahtar (key) kısmını solda ise değer(value) kısmınıbelrtmemiz lazım ama biz burada
    //bir etiketleme yapıp şimdiden örenek: yazaraGoreIndex etıketının sol tarafı net sekılde strıng sol tarafı ise bir list kitap nesneısne aittir diyoruz
    //bu sekilde aşagı tarata bırdaha yazmak sorunda degılız
    private Map<String, List<Kitap>> yazaraGoreIndex;
    private Map<String, Kitap> kitapAdinaGoreIndex;

    

    public SorguSistemi(){
        kitapAdinaGoreIndex = new HashMap<>();
        yazaraGoreIndex = new HashMap<>();
    }

    //devamıdna sisteme kitap ve yazarlar ıcın lıstler acılıp kitap ve ayzara göre ayrı ayrı sorgulama eklenıcektır

}


