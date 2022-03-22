package fis.pms.domain.caseEnum;

// 전자기록물 여부 (1.전자기록물, 2.비전자기록물)
public enum C_edoc {
    ELEC("1"),
    PAPER("2");

    private String edoc;

    C_edoc(String edoc) {
        this.edoc = edoc;
    }

    public String getEdoc() {
        return edoc;
    }
}
