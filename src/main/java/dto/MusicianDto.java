package dto;

public class MusicianDto {
    private  int id;
    private  String name;
    private  EnsembleDto ensembleDto;
    private  String genre;
    private  String instrument;
    private  String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnsembleDto getEnsembleDto() {
        return ensembleDto;
    }

    public void setEnsembleDto(EnsembleDto ensembleDto) {
        this.ensembleDto = ensembleDto;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
