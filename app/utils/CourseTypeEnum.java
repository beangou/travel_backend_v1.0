package utils;

/**
 * Created by beangou on 16/7/5.
 */
public enum CourseTypeEnum {

    // course类型 1: 书名 2: 章名 3: 节名 4: 小节名
    BOOK_NAME(1, "书名"),
    CHAPTER_NAME(2, "章名"),
    KNOBBLE_NAME(3, "节名"),
    SUB_KNOBBLE_NAME(4, "子节名");

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    private int type;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setType(int type) {
        this.type = type;
    }

    private String desc;

    private CourseTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
