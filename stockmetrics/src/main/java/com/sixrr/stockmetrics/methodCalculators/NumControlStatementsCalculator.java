/*
 * Copyright 2005, Sixth and Red River Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.sixrr.stockmetrics.methodCalculators;

import com.intellij.psi.*;
import com.sixrr.metrics.utils.MethodUtils;

public class NumControlStatementsCalculator extends NumSimpleElementCalculator {

    @Override
    protected PsiElementVisitor createVisitor() {
        return new Visitor();
    }

    private class Visitor extends NumSimpleElementCalculator.Visitor {

        @Override
        public void visitIfStatement(PsiIfStatement statement) {
            super.visitIfStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitDoWhileStatement(PsiDoWhileStatement statement) {
            super.visitDoWhileStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitContinueStatement(PsiContinueStatement statement) {
            super.visitContinueStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitBreakStatement(PsiBreakStatement statement) {
            super.visitBreakStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitForStatement(PsiForStatement statement) {
            super.visitForStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitForeachStatement(PsiForeachStatement statement) {
            super.visitForeachStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitSwitchLabelStatement(PsiSwitchLabelStatement statement) {
            super.visitSwitchLabelStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitSwitchStatement(PsiSwitchStatement statement) {
            super.visitSwitchStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitSynchronizedStatement(PsiSynchronizedStatement statement) {
            super.visitSynchronizedStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitTryStatement(PsiTryStatement statement) {
            super.visitTryStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitWhileStatement(PsiWhileStatement statement) {
            super.visitWhileStatement(statement);
            elementsCounter++;
        }
    }
}
