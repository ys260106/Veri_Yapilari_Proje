import java.util.List;

public class KutupHaneSiralama {
    private int metinKarsilastir(String s1, String s2){
        if(s1==null && s2==null){
            return 0;
        }
        if(s1==null){
            return -1;
        }
        if(s2==null){
            return 1;
        }
        int minUzunluk = s1.length()<s2.length() ? s1.length():s2.length();
        for(int i=0; i<minUzunluk; i++){
            char c1=s1.charAt(i);
            char c2=s2.charAt(i);
            if(c1!=c2){
                return c1-c2;
            }
        }
        return s1.length()-s2.length();
    }
    // isme göre sıralama
    public void basligagöreKabarcikSirala(List<Kitap> kitaplar){
        if(kitaplar==null){
            return;
        }
        int n = kitaplar.size(); // listenin icindeki toplam kitap sayisi
        //Basliga göre
        for(int i=0; i<n-1; i++){
            for(int j=0; j<n - i - 1; j++){
                String b1 = kitaplar.get(j).getBaslik();
                String b2= kitaplar.get(j+1).getBaslik();
                if(metinKarsilastir(b1,b2)>0){
                    Kitap temp=kitaplar.get(j);
                    kitaplar.set(j,kitaplar.get(j+1));
                    kitaplar.set(j+1,temp);
                }
            }
        }
    }

    // Yayın yılına göre küçükten büyüğe sıralama
    public void yayinYilinaGöreSecmeliSirala(List<Kitap> kitaplar){
        if(kitaplar==null){
            return;
        }
        int n=kitaplar.size();
        for(int i=0; i<n - 1; i++){
            int minIndex=i;
            for(int j=i+1; j<n ; j++){
                if(kitaplar.get(j).getYayinYili()< kitaplar.get(minIndex).getYayinYili()){
                    minIndex=j;
                }
            }
            if (minIndex !=i){
                Kitap temp=kitaplar.get(i);
                kitaplar.set(i,kitaplar.get(minIndex));
                kitaplar.set(minIndex,temp);
            }
        }
    }
    // Alfabetik sıralama
    public void basligaGöreEklemeliSirala(List<Kitap> kitaplar){
        if(kitaplar==null){
            return;
        }
        int n=kitaplar.size();
        
        for(int i=1 i<n; i++);{
            Kitap anahtar = kitaplar.get(i);
            int j=i-1;
            
            while(j>=0 && metinKarsilastir(kitaplar.get(j).getBaslik(),anahtar.getBaslik())>0){
                kitaplar.set(j+1,kitaplar.get(j));
                j--;
            }
            kitaplar.set(j+1,anahtar);
        }
    }
}