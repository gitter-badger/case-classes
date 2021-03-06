package com.github.alexmojaki.caseclasses;

import java.util.Objects;

class EqualsBuilder extends DualResultBuilder {

    private boolean equal = true;

    static boolean equals(CaseClass o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (!(o2 instanceof CaseClass)) {
            return false;
        }
        if (o1 == null) {
            return false;
        }
        CaseClass caseClass2 = (CaseClass) o2;
        if (!classesMatch(o1, caseClass2)) {
            return false;
        }
        EqualsBuilder builder = new EqualsBuilder();
        builder.buildResult(o1, caseClass2);
        return builder.equal;
    }

    static boolean classesMatch(CaseClass o1, CaseClass o2) {
        if (o1 instanceof FlexiblyEqual && o2 instanceof FlexiblyEqual) {
            if (!(((FlexiblyEqual) o1).equalsBaseClass().isInstance(o2) &&
                    ((FlexiblyEqual) o2).equalsBaseClass().isInstance(o1))) {
                return false;
            }
        } else if (o1.getClass() != o2.getClass()) {
            return false;
        }
        return true;
    }

    @Override
    protected void add(String name1, Object value1, String name2, Object value2) {
        equal = equal && name1.equals(name2) && Objects.equals(value1, value2);
    }

    @Override
    protected void extraFirstValue(String name, Object value) {
        equal = false;
    }

    @Override
    protected void extraSecondValue(String name, Object value) {
        equal = false;
    }

}
