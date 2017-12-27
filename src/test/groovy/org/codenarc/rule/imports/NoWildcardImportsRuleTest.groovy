/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.imports

import org.codenarc.rule.Rule
import org.junit.Test
import org.codenarc.rule.AbstractRuleTestCase

/**
 * Tests for NoWildcardImportsRule
 *
 * @author Kyle Boon
 * @author Chris Mair
 */
class NoWildcardImportsRuleTest extends AbstractRuleTestCase {

    @Test
    void testRuleProperties() {
        assert rule.priority == 3
        assert rule.name == 'NoWildcardImports'
        assert rule.ignoreStaticImports == false
    }

    @Test
    void testNoViolations() {
        final SOURCE = '''
            import com.google

            public class Foo {}
        '''
        assertNoViolations(SOURCE)
    }

    @Test
    void testWildcardImports_Violations() {
        final SOURCE = '''
            import static Math.*
            import static org.codenarc.report.AbstractHtmlReportWriter.*

            import com.google.*
            import org.codenarc.rule.*

            public class Foo {}
        '''
        assertViolations(SOURCE,
            [lineNumber:2, sourceLineText:'import static Math.*'],
            [lineNumber:3, sourceLineText:'import static org.codenarc.report.AbstractHtmlReportWriter.*'],
            [lineNumber:5, sourceLineText:'import com.google.*'],
            [lineNumber:6, sourceLineText:'import org.codenarc.rule.*'])
    }

    @Test
    void testStaticWildcardImports_ignoreStaticImports_NoViolations() {
        final SOURCE = '''
            import static Math.*
            import static org.codenarc.report.AbstractHtmlReportWriter.*

            public class Foo {}
        '''
        rule.ignoreStaticImports = true
        assertNoViolations(SOURCE)
    }

    @Override
    protected Rule createRule() {
        new NoWildcardImportsRule()
    }
}
