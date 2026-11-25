package kutuphane;
public class Kutuphane {
    private UyeListesi uyeler;
    private KitapListesi kitaplar;
    private OduncListesi oduncler;
    private OduncServisi oduncServisi;
    private SorguSistemi sorgu;
    private KutuphaneSiralama siralama;
    
    public Kutuphane(){
        this.uyeler=new UyeListesi();
        this.kitaplar=new KitapListesi();
        this.oduncler=new OduncListesi();
        
        this.oduncServisi = new OduncServisi(this.uyeler, this.kitaplar, this.oduncler);
        this.sorgu = new SorguSistemi(this.kitaplar, this.uyeler, this.oduncler);
        this.siralama = new KutuphaneSiralama();
        
    }
    
    public UyeListesi getUyeler(){
        return uyeler;
    }
    public KitapListesi getKitaplar(){
        return kitaplar;
    }
    public OduncListesi getOduncler(){
        return oduncler;
    }
    public OduncServisi getOduncServisi(){
        return oduncServisi;
    }

    public SorguSistemi getSorgu(){
        return sorgu;
    }

    public KutuphaneSiralama getSiralama(){
        return siralama;
    }
}
