//burası kütüphanede bulunmayan kitaplara bekleme talebi olusturarak sıraya girmesini saglar
public class beklemeTalebi {
    int talepId;
    int kopyaNo;
    int uyeNo;

    public beklemeTalebi(int talepId,int kopyaNo,int uyeNo){
    this.talepId=talepId++;
    this.kopyaNo=kopyaNo;
    this.uyeNo=uyeNo;
    }

}
