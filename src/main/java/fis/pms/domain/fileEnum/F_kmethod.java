package fis.pms.domain.fileEnum;


//보존방법
// (1-원본과 보존매체를 함께 보존하는 방법, 2-원본은 폐기하고 보존매체만 보존하는 방법, 3- 원본을 그대로 보존하는 방법)
public enum F_kmethod {
    NONE(""),
    ALL("1"),
    MEDIA("2"),
    ORIGINAL("3");

    private String kmethod;

    F_kmethod(String kmethod) {
        this.kmethod = kmethod;
    }

    public String getKmethod() {
        return kmethod;
    }
}
