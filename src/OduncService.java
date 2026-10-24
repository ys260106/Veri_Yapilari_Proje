import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class OduncService {
    // Masadaki kayıtlar ve listeler
    private ArrayList<odunc> oduncListesi;//Sınırsız bir odunc sırası elde edilir
    private HashMap<Integer, Queue<beklemeTalebi>> beklemeKuyruklari;//spesifik olarak istedigimizde islem yapmamızi sağlar
    private HashMap<Integer, Integer> ayirma;//kitapı bekleyen kişiye ayırmayı saglar


    public OduncService() {
    }

    // kitap odunc verme kontrolu
    public void oduncVer(int uyeNo,int kitapNo,int kopyaNo, LocalDate beklenenIadeTarihi) {
      System.out.println(uyeNo+" numaralı kişi "+kitapNo+" numaralı kitabın "+kopyaNo+" kopyasını ödünç almıştır");
    }

    // kitap iade alma ve sıradakine ayırma
    public void iadeAl(int uyeNo,int kitapNo) {
      System.out.println(uyeNo+" numaralı kişi "+kitapNo+" numaralı kitabın iadesini yapmıştır");
    }

    // kitap almak icin sıraya girme
    public void beklemeTalebiOlustur(int uyeNo,int kitapNo,int kopyaNo) {
     System.out.println(uyeNo+" numaralı kişi "+kitapNo+" numaralı kitabın "+kopyaNo+" kopyasını almak için bekleme talebi oluşturmuştur");
    }

    // sıradan vazgeçme veyaa silme
    public void beklemeTalebiSil(int talepId) {
     System.out.println(talepId+"numaralı talep silinmiştir"); 
    }
}