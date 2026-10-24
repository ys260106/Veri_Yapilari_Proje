import java.util.List;

public class KutupHaneSiralama {
    
    
    public void sortByTitle(List<Kitap> kitaplar){
        int n = kitaplar.size();
        for(int i=0; i<n-1; i++){
            for(int j=0; j<n - i - 1; j++){
                if(kitaplar.get(j).getBaslik().compareTo(kitaplar.get(j + 1).getBaslik()) > 0){
                    
                    Kitap temp=kitaplar.get(j);
                    kitaplar.set(j,kitaplar.get(j + 1));
                    kitaplar.set(j + 1,temp);
                }
            }
        }
    }


    public void sortByPublicationYear(List<Kitap> kitaplar){
        int n=kitaplar.size();
        for(int i=0; i<n - 1; i++){
            for(int j=0; j<n - i - 1; j++){
                if(kitaplar.get(j).getYayinYili()> kitaplar.get(j+1).getYayinYili()){
                    Kitap temp= kitaplar.get(j);
                    kitaplar.set(j,kitaplar.get(j + 1));
                    kitaplar.set(j + 1,temp);
                }
            }
        }
    }
}