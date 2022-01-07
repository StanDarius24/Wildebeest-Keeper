package com.stannis.declSpecifier

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.If
import com.stannis.services.CoreParserClass
import com.stannis.services.MethodService
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object IfStatementService {

    fun solveIfStatement(data: CPPASTIfStatement,  statement: Statement?) {
        println("ifStatement")
        val ifT = If(null, null, null, null, null)
        StatementMapper.addStatementToStatement(statement!!, ifT)
        if (data.conditionExpression != null) {
            ASTNodeService.solveASTNode(
                    data.conditionExpression as ASTNode,
                    ifT
                )
        }
        val ifBlock = MethodService.createMethod()
        ifT.addIfBlock(ifBlock)
        val elseBlock = MethodService.createMethod()
        ifT.addElseBlock(elseBlock)
        if (data.thenClause != null)
            CoreParserClass.seeCPASTCompoundStatement(data.thenClause, ifBlock)
        if (data.elseClause != null)
            CoreParserClass.seeCPASTCompoundStatement(data.elseClause, elseBlock)
    }
}