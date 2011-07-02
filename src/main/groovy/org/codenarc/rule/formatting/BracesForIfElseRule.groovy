/*
 * Copyright 2011 the original author or authors.
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
package org.codenarc.rule.formatting

import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import org.codehaus.groovy.ast.stmt.IfStatement

/**
 * Checks the location of the opening brace ({) for if statements. By default, requires them on the same line, but the
 * sameLine property can be set to false to override this.
 *
 * @author Hamlet D'Arcy
 * @author <a href="mailto:geli.crick@osoco.es">Geli Crick</a>
 * @version $Revision: 24 $ - $Date: 2009-01-31 13:47:09 +0100 (Sat, 31 Jan 2009) $
 */
class BracesForIfElseRule extends AbstractAstVisitorRule {
    String name = 'BracesForIfElse'
    int priority = 2
    Class astVisitorClass = BracesForIfElseAstVisitor
    boolean sameLine = true
}

class BracesForIfElseAstVisitor extends AbstractAstVisitor {

    @Override
    // TODO: also check else blocks
    void visitIfElse(IfStatement node) {
        if (isFirstVisit(node)) {
            if (rule.sameLine) {
                if(!lastSourceLine(node.booleanExpression)?.contains('{')) {
                    addViolation(node, 'Braces should start on the same line')
                }
            } else {
                if(lastSourceLine(node.booleanExpression)?.contains('{')) {
                    addViolation(node, 'Braces should start on a new line')
                }
            }
        }
        super.visitIfElse(node)
    }
}
