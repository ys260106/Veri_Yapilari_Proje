package kutuphane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
           SwingUtilities.invokeLater(()->{
        Kutuphane kutuphane = new Kutuphane();
        DosyaIslemleri dosya = new DosyaIslemleri();
        dosya.yukle(kutuphane);
        
        AnaPencere pencere =new AnaPencere(kutuphane);
        pencere.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                dosya.kaydet(kutuphane);
            }
        });
        pencere.setVisible(true);
    });
    }
    }

