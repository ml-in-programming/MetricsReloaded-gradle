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

public class NumExecutableStatementsCalculator extends NumSimpleElementCalculator {

    @Override
    protected PsiElementVisitor createVisitor() {
        return new Visitor();
    }

    private class Visitor extends NumSimpleElementCalculator.Visitor {

        @Override
        public void visitExpressionListStatement(PsiExpressionListStatement statement) {
            super.visitExpressionListStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitExpressionStatement(PsiExpressionStatement statement) {
            super.visitExpressionStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitDeclarationStatement(PsiDeclarationStatement statement) {
            super.visitDeclarationStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitAssertStatement(PsiAssertStatement statement) {
            super.visitAssertStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitReturnStatement(PsiReturnStatement statement) {
            super.visitReturnStatement(statement);
            elementsCounter++;
        }

        @Override
        public void visitThrowStatement(PsiThrowStatement statement) {
            super.visitThrowStatement(statement);
            elementsCounter++;
        }
    }
}
