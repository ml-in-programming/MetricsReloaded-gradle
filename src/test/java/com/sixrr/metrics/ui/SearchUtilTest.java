/*
 * Copyright 2005-2016 Sixth and Red River Software, Bas Leijdekkers
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

package com.sixrr.metrics.ui;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.sixrr.metrics.ui.SearchUtil.tokenizeFilter;
import static org.junit.Assert.assertEquals;

/**
 * @author Bas Leijdekkers
 */
public class SearchUtilTest {

    @Test
    public void testTokenizeFilter() {
        final List<String> tokens = tokenizeFilter("one 'two three' four");
        assertEquals(Arrays.asList("one", "two three", "four"), tokens);
    }
}