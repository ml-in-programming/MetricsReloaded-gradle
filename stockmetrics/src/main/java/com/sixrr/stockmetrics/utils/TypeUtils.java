package com.sixrr.stockmetrics.utils;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.sixrr.metrics.utils.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class TypeUtils {

    public static Method tryGetGetTypeMethod(PsiElement element) {
        Method gettingTypeMethod = null;
        try {
            gettingTypeMethod = element.getClass().getMethod("getType", (Class<?>[]) null);
        } catch (NoSuchMethodException | SecurityException ignored) {}
        return gettingTypeMethod;
    }

    public static boolean tryAddTypeOfElementTo(Set<PsiType> typeSet, PsiElement element) {

        boolean isInMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class) != null;
        if (!isInMethod)
            return false;
        if (element instanceof PsiMethod)
            return false;

        Method gettingTypeMethod = tryGetGetTypeMethod(element);

        if (gettingTypeMethod != null) {
            try {
                typeSet.add((PsiType) gettingTypeMethod.invoke(element));
                return true;
            } catch (IllegalAccessException | InvocationTargetException ignored) {}

        }
        return false;
    }

    public static boolean addTypesFromMethodTo(Set<PsiType> typeSet, PsiMethod method) {
        typeSet.add(method.getReturnType());
        PsiParameter[] parameters = method.getParameterList().getParameters();
        for (PsiParameter parameter : parameters) {
            typeSet.add(parameter.getType());
        }
        return true;
    }
}
