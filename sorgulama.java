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


