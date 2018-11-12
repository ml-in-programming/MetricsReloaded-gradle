package org.jetbrains.research.groups.ml_methods.utils

import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiStatement

class ExtractionCandidate(val start: PsiStatement, val end: PsiStatement, val sourceMethod: PsiMethod) {
    override fun toString(): String {
        return "Candidate, textOffset: ${start.textOffset}..${end.textOffset}, text: \n" +
                "${start.text}\n..\n${end.text}\n"
    }
}
