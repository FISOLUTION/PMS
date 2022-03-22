package fis.pms.domain.caseEnum;

//기록물구분(1.신기록물, 2.구기록물)
public enum C_newold {
    NEW("1"),
    OLD("2");

    private String newold;

    C_newold(String newold) {
        this.newold = newold;
    }

    public String getNewold() {
        return newold;
    }
}