package dto;

public class MusicDto {
    private  int id;
    private  String name;
    private  MusicianDto musicianDto;
    private  String performance;

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

    public MusicianDto getMusicianDto() {
        return musicianDto;
    }

    public void setMusicianDto(MusicianDto musicianDto) {
        this.musicianDto = musicianDto;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }
}
