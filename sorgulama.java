
class Kitap {
    String kayitNumarasi;
    String ad;
    String yazar;
    boolean oduncVerildimi;

    public Kitap(String kayitNumarasi, String ad, String yazar){

        this.kayitNumarasi = kayitNumarasi;
        this.ad = ad;
        this.yazar = yazar;
        this.oduncVerildimi = false;
    }

    public String getAd() {
        return ad;
    }

    public String getYazar(){
        return yazar;
    }
    


    @Override //bu ksıım kulladıgımız kutuphaneden gelıor normalde hafıza adresını yazdırıyor ancak biz bu methodu ezerek anlamlı bır cıktı saglattırıyoruz
    public String toString(){
        return "Kayıt NO:" + kayitNumarasi + " | " + ad + " - " + yazar;   
    }
}

class HashDugumu {
    String anahtar;
    Kitap deger;
    HashDugumu sonraki;

    public HashDugumu (String anahtar, Kitap deger){
        this.anahtar = anahtar;
        this.deger = deger;
        this.sonraki = null;
    }
}


// 16 tane kutu olusturucagım her 16 kutunun ıcınde bir lınkedlist olucak
// ve gırılen kıtabın adı bir unicode koduna donusturulup bu 16 kutudan bırsıne gecılecek
// ve orda arama yapılacak bu sayede en kötü ihtimalde mesela 10 bin kıtap degıl 625 kıtap arma yapmış olucaz
class KendiHashMap {
    
    private HashDugumu kutular[];
    int boyut;

    public KendiHashMap(){
        kutular = new HashDugumu[16];
        boyut = 0;
    }

    //burdada (anahtar, deger) kısmını yazıyoruz anahtarı uniceodeyapıp o lıstey bulup sonra o lıstede gezınıcez
public void put(String anahtar, Kitap deger){
    
    int index = Math.abs(anahtar.hashCode()) % kutular.length;
    HashDugumu mevcut = kutular[index];

    //listeyi gezıyoruz aynı anahtar varmı diye yoksa yeni düğüm olsuutyoruz
    while(mevcut != null){
        if(mevcut.anahtar.equals(anahtar)){
            mevcut.deger = deger;
            return;
            }
        mevcut = mevcut.sonraki;
        }
    //yoksa yeni duüğüm olusturuyoruz
    //burdakı mantık su her kutular[index] aslıdan bir bas demek aynı lınkedlısttekı bas lar gibi
    //aynı lınked lısttekı gıbı yeni dugumu n sonrakını basa baglıyoruz bas degsıkenının yeni dugum yapıyoruz
    HashDugumu yeni = new HashDugumu(anahtar, deger);
    yeni.sonraki = kutular[index];
    kutular[index] = yeni;
    boyut++;

    } 
   

}



