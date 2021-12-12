package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.ConstructorInitializer
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConstructorInitializer

class ConstructorInitializerService {
    companion object{
        private lateinit var constructorInitializerService: ConstructorInitializerService

        fun getInstance(): ConstructorInitializerService{
            if(!::constructorInitializerService.isInitialized) {
                constructorInitializerService = ConstructorInitializerService()
            }
            return constructorInitializerService
        }
    }

    fun solveConstructorInitializer(constructorInitializer: CPPASTConstructorInitializer, statement: Statement?) {
        val constrInit = ConstructorInitializer(null)
        constructorInitializer.arguments
            .iterator().forEachRemaining { arg ->
                run {
                    ASTNodeService.getInstance()
                        .solveASTNode(arg as ASTNode, constrInit)
                }
            }
        StatementMapper.addStatementToStatement(statement!!, constrInit)
    }
}