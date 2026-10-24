import java.time.LocalDate;

public class Maintest {
    public static void main(String[] args) {
    OduncService servis=new OduncService();

    servis.oduncVer(1, 200,1, LocalDate.now().plusDays(14) );
    servis.beklemeTalebiOlustur(2, 200, 1);
    servis.iadeAl(1, 200);
    servis.beklemeTalebiSil(1);
        
}
}