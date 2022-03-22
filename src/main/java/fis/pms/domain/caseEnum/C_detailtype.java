package fis.pms.domain.caseEnum;

//건 세부유형 2 (11.졸업대장, 21.생활기록부, 22.인사카드)
public enum C_detailtype {
    GRADUATE("11"),
    RECORD("21"),
    CARD("22");

    private String detailtype;

    C_detailtype(String detailtype) {
        this.detailtype = detailtype;
    }

    public String getDetailtype() {
        return detailtype;
    }
}
