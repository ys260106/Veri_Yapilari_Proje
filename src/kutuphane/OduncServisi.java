package kutuphane;

import java.time.LocalDate;

public class OduncServisi {

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
     System.out.println(talepId+" numaralı talep silinmiştir"); 
      }
}