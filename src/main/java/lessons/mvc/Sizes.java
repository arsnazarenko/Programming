package lessons.mvc;

import java.awt.*;

public enum Sizes {

    S(new Dimension(8, 20)),
    M(new Dimension(16, 40)),
    L(new Dimension(24, 60));

    private Dimension size;
    Sizes(Dimension size) {
        this.size = size;
    }

    public static Sizes calcSize(Integer employeesCount) {
        if (employeesCount <= 50) {
            return S;
        } else if (employeesCount <= 500) {
            return M;
        } else {
            return L;
        }
    }

    public Dimension getSizeValue() {
        return size;
    }
}
