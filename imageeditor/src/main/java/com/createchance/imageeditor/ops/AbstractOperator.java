package com.createchance.imageeditor.ops;

/**
 * ${DESC}
 *
 * @author gaochao1-iri
 * @date 2018/10/28
 */
public abstract class AbstractOperator {

    public static final int OP_FILTER = 0;

    protected final String mName;

    protected final int mType;

    public AbstractOperator(String name, int type) {
        mName = name;
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public abstract boolean checkRational();

    public abstract void exec();

    @Override
    public String toString() {
        return "AbstractOperator{" +
                "mName='" + mName + '\'' +
                '}';
    }
}