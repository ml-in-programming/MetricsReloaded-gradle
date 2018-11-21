package org.jetbrains.research.groups.ml_methods.utils;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.impl.source.tree.java.PsiCodeBlockImpl;
import com.intellij.util.text.TextRangeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ExtractionCandidate {
    private PsiStatement[] statements;
    private PsiMethod sourceMethod;
    private boolean inCandidate;
    private PsiCodeBlock codeBlock;

    public ExtractionCandidate(PsiStatement[] statements, PsiMethod sourceMethod) {
        this.statements = statements;
        this.sourceMethod = sourceMethod;

        StringBuilder text = new StringBuilder();
        for (PsiStatement statement: statements) {
            text.append(statement.getText());
        }
        this.codeBlock = new PsiCodeBlockImpl(text.toString());
    }

    public boolean isInCandidate() {
        return inCandidate;
    }

    public void setInCandidate(boolean value) {
        inCandidate = value;
    }

    public PsiStatement getStart() {
        return statements[0];
    }

    public PsiStatement getEnd() {
        return statements[statements.length - 1];
    }

    public PsiMethod getSourceMethod() {
        return sourceMethod;
    }

    public PsiCodeBlock getCodeBlock() {
        return codeBlock;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder("Candidate of ");
        str.append(sourceMethod).append(":\n");

        for (PsiStatement statement: statements) {
            str.append(statement.getText()).append("\n");
        }
        return str.toString();
    }
}
