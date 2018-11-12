package org.jetbrains.research.groups.ml_methods.utils;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiStatement;

public final class ExtractionCandidate {
    private PsiStatement start;
    private PsiStatement end;
    private PsiMethod sourceMethod;

    public ExtractionCandidate(PsiStatement start, PsiStatement end, PsiMethod sourceMethod) {
        this.start = start;
        this.end = end;
        this.sourceMethod = sourceMethod;
    }

    public PsiStatement getStart() {
        return start;
    }

    public PsiStatement getEnd() {
        return end;
    }

    public PsiMethod getSourceMethod() {
        return sourceMethod;
    }

    @Override
    public String toString() {

        String str = "Candidate, textOffset: ";
        str += start.getTextOffset() + ".." + end.getTextOffset() + ", ";
        str += "text: \n" + start.getText() + "\n..\n" + end.getText();
        return str;
    }
}
