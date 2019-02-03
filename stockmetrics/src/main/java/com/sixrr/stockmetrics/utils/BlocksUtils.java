package com.sixrr.stockmetrics.utils;

import com.intellij.psi.*;
import org.jetbrains.research.groups.ml_methods.utils.BlockOfMethod;

import java.util.*;

public class BlocksUtils {
    private BlocksUtils() {}

    public static <T extends PsiElement> Set<T> getElementsOfBlock(
            BlockOfMethod block, Class<T> aClassElement) {
        Set<T> result = new HashSet<>();

        for (int i = 0; i < block.getStatementsCount(); i++) {
            block.get(i).accept(
                    new JavaRecursiveElementVisitor() {
                        @Override
                        public void visitElement(PsiElement element) {
                            super.visitElement(element);
                            if (aClassElement.isAssignableFrom(element.getClass())) {
                                result.add(aClassElement.cast(element));
                            }
                        }
                    }
            );
        }
        return result;
    }

    private static int ourCount = 0;
    public static <T extends PsiElement> int getCountOfElementFromBlock(BlockOfMethod block, T element) {
        ourCount = 0;

        for (int i = 0; i < block.getStatementsCount(); i++) {
            block.get(i).accept(new JavaRecursiveElementVisitor() {
                @Override
                public void visitReferenceElement(PsiJavaCodeReferenceElement reference) {
                    super.visitReferenceElement(reference);
                    if (reference.isReferenceTo(element)) {
                        ourCount++;
                    }
                }
            });
        }
        return ourCount;
    }

    public static <T extends PsiElement> double getFreqOfElementFromBlock(BlockOfMethod block, T element) {
        int count = getCountOfElementFromBlock(block, element);
        return (double)count / block.getStatementsCount();
    }

    public static BlockOfMethod getBlockFromMethod(PsiMethod method) {
        return new BlockOfMethod(method.getBody().getStatements());
    }
}
