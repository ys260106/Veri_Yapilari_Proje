import java.time.LocalDate;//Gün ay yıl olarak tutmamızı sağlayan kütüphane
//odunc verme aşamasındaki aşamaların gerçekleştigi yer.
public class odunc {
    int oduncIdno;
    private int oduncId;
    private int kopyaNo;
    private int uyeNo;
    private int kitapNo;
    private LocalDate bekleneneIade;
public odunc(int oduncId, int kopyaNo, int uyeNo,int kitapNo, LocalDate bekleneneIade, oduncDurumu durumu) {
        this.oduncId = oduncIdno++;
        this.kopyaNo = kopyaNo;
        this.uyeNo = uyeNo;
        this.kitapNo=kitapNo;
        this.bekleneneIade = bekleneneIade;
        }
}
