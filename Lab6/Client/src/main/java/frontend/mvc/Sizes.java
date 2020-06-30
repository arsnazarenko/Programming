package frontend.mvc;

public enum Sizes {
    S(2),
    M(3),
    L(4);

    private Integer size;

    Sizes(int size) {
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

    public Integer getSizeValue() {
        return size;
    }
}
