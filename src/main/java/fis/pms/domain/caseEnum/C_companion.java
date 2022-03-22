package fis.pms.domain.caseEnum;

//반려여부 1 (0.해당없음, 1.반려)
public enum C_companion {
    NONE("0"),
    COMPANION("1");

    private String companion;

    C_companion(String companion) {
        this.companion = companion;
    }

    public String getCompanion() {
        return companion;
    }

}
