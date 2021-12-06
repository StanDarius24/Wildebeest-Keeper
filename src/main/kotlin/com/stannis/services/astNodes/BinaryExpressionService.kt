package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.BinaryExpression
import com.stannis.services.FunctionCallsService
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionCallExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression

class BinaryExpressionService {

    companion object{
        private lateinit var binaryExpression: BinaryExpressionService

        fun getInstance(): BinaryExpressionService {
            if(!Companion::binaryExpression.isInitialized) {
                binaryExpression = BinaryExpressionService()
            }
            return binaryExpression
        }
    }


    private fun handreOperands(node: ASTNode, statement: Statement) {
       ASTNodeService.getInstance()
           .solveASTNode(node, statement)
    }

    fun solveBinaryExpressionService(expression: CPPASTBinaryExpression, statement: Statement?) {
        val binaryExpr = BinaryExpression(null, null)
        if(expression.operand1 != null) {
            val anonimStatement = AnonimStatement( null)
            handreOperands(expression.operand1 as ASTNode, anonimStatement)
            binaryExpr.addLeftExpression(anonimStatement.statement!!)
        }
        if(expression.operand2 != null) {
            val anonimStatement = AnonimStatement( null)
            handreOperands(expression.operand2 as ASTNode, anonimStatement)
            binaryExpr.addRightExpression(anonimStatement.statement!!)
        }
        StatementMapper.addStatementToStatement(statement!!, binaryExpr)
    }

}