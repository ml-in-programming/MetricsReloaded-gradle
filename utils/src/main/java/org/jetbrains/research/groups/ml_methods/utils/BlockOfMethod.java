package org.jetbrains.research.groups.ml_methods.utils;

import com.intellij.psi.PsiStatement;

import java.util.Arrays;

public class BlockOfMethod {
    private PsiStatement[] statements;

    public BlockOfMethod(PsiStatement[] statements) {
        this.statements = Arrays.copyOf(statements, statements.length);
    }

    public PsiStatement get(int index) {
        return statements[index];
    }

    public PsiStatement getFirstStatement() {
        return statements[0];
    }

    public PsiStatement getLastStatement() {
        return statements[statements.length - 1];
    }

    public int getStatementsCount() {
        return statements.length;
    }
}
