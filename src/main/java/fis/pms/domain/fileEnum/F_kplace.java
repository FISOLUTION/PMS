package fis.pms.domain.fileEnum;


//보존장소 (1-기록관, 2-전문관리기관)
public enum F_kplace {
    NONE(""),
    ARCHIVIST("1"),
    PROFESSION("2");

    private String kplace;

    F_kplace(String kplace) {
        this.kplace = kplace;
    }

    public String getKplace() {
        return kplace;
    }
}
