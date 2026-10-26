import java.util.List;

public class KutupHaneSiralama {
    
    // isme göre sıralama
    public void sortByTitle(List<Kitap> kitaplar){
        int n = kitaplar.size(); // listenin icindeki toplam kitap sayisi
        for(int i=0; i<n-1; i++){
            for(int j=0; j<n - i - 1; j++){
                if(kitaplar.get(j).getBaslik().compareTo(kitaplar.get(j + 1).getBaslik()) > 0){
                    // comperto da iki metni alfabetik olarak karşılaştırır
                    Kitap temp=kitaplar.get(j);
                    kitaplar.set(j,kitaplar.get(j + 1));
                    kitaplar.set(j + 1,temp);
                }
            }
        }
    }

    // yayın yılına göre sıralama
    public void sortByPublicationYear(List<Kitap> kitaplar){
        int n=kitaplar.size();
        for(int i=0; i<n - 1; i++){
            for(int j=0; j<n - i - 1; j++){
                if(kitaplar.get(j).getYayinYili()> kitaplar.get(j+1).getYayinYili()){
                    Kitap temp= kitaplar.get(j);
                    kitaplar.set(j,kitaplar.get(j + 1));
                    kitaplar.set(j + 1,temp);
                    // temp değişkeni kullanılarak iki kitap listedeki yerleriyle takas edilir
                }
            }
        }
    }
}