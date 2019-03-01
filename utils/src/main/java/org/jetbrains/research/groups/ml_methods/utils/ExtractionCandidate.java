package org.jetbrains.research.groups.ml_methods.utils;

import com.intellij.psi.*;

public final class ExtractionCandidate {
    private BlockOfMethod block;
    private PsiMethod sourceMethod;
    private boolean inCandidate;
    private final String id;

    public ExtractionCandidate(PsiStatement[] statements, PsiMethod sourceMethod, int uniqueId) {
        this.block = new BlockOfMethod(statements);
        this.sourceMethod = sourceMethod;
        this.id = "cand_" + uniqueId;
    }

    public ExtractionCandidate(PsiStatement[] statements, PsiMethod sourceMethod) {
        this(statements, sourceMethod, 0);
    }

    public String getId() {
        return this.id;
    }

    public boolean isInCandidate() {
        return inCandidate;
    }

    public void setInCandidate(boolean value) {
        inCandidate = value;
    }

    public PsiStatement getStart() {
        return block.getFirstStatement();
    }

    public PsiStatement getEnd() {
        return block.getLastStatement();
    }

    public PsiMethod getSourceMethod() {
        return sourceMethod;
    }

    public BlockOfMethod getBlock() {
        return block;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();//"Candidate of ");
//        str.append(sourceMethod).append(":\n");

        int blockSize = block.getStatementsCount();
        for (int i = 0; i < blockSize; i++) {
            str.append(block.get(i).getText()).append("\n");
            if (i != blockSize - 1)
                str.append("\n");
        }
        return str.toString();
    }
}
