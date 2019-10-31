package cn.gateon.library.jpa.enums;

/**
 * <p>
 * like查询%号位置
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
public enum Position {
    LEFT(-1), RIGHT(1), BOTH(0);

    private int mark;

    public int getMark() {
        return mark;
    }

    Position(int mark) {
        this.mark = mark;
    }
}
