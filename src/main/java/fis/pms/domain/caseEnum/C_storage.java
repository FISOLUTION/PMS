package fis.pms.domain.caseEnum;

//저장매체 2 (01.CD-R, 02.DAT, 03.DVD, 04.플로피디스크, 05.하드디스크, 06.JAZ 드라이브, 07.마이크로필름, 08.종이, 09.비디오테이프, 10.ZIP 드라이브, 99.기타)
public enum C_storage {
    CD("01"),
    DAT("02"),
    DVD("03"),
    FLOPPY("04"),
    HARD("05"),
    JAZ("06"),
    FILM("07"),
    PAPER("08"),
    VIDEO("09"),
    ZIP("10"),
    ETC("99");

    private String storage;

    C_storage(String storage) {
        this.storage = storage;
    }

    public String getStorage() {
        return storage;
    }
}
