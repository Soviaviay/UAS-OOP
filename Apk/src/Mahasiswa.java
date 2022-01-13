public class Mahasiswa {
    private int id ;
    private String nama = null;
    private String nim = null ;
    private String jurusan = null ;
    private String fakultas = null ;

    public Mahasiswa(int inputId, String inputNama, String inputNim, String inputJurusan, String inputFakultas){
        this.id = inputId;
        this.nama = inputNama;
        this.nim = inputNim;
        this.jurusan = inputJurusan;
        this.fakultas = inputFakultas;
        

    }

    public int getId() {
        return id;
    }
    public String getNama(){
        return nama;
    }
    public String getNim(){
        return nim;
    }
    public String getJurusan(){
        return jurusan;
    }
    public String getFakultas(){
        return fakultas;
    }

}