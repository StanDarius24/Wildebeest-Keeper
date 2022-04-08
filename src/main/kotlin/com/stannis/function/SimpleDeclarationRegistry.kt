package com.stannis.function

import com.stannis.dataModel.Statement
import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.statementTypes.*
import com.stannis.services.astNodes.CompositeTypeSpecifierService
import com.stannis.services.astNodes.FunctionDefinitionService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition

object SimpleDeclarationRegistry {

    var globalDeclaration: ArrayList<DeclarationWithParent>? = null
    var internDeclaration: ArrayList<DeclarationWithParent>? = null

    fun addToList(data: SimpleDeclaration, parent: ASTNode?) {
        if (data.declarators != null && data.declarators!![0] is Declarator) {
            val declarationWithParent = DeclarationWithParent(data, null)
            if (parent != null) {
                val anonimStatement = AnonimStatement(null)
                if (parent is CPPASTFunctionDefinition) {
                    solveFunctionDefinition(parent, anonimStatement)
                    declarationWithParent.parent = anonimStatement.statement as FunctionDefinition
                    if (globalDeclaration == null) {
                        globalDeclaration = ArrayList()
                    }
                    if (internDeclaration == null) {
                        internDeclaration = ArrayList()
                    }
                    if (declarationWithParent.parent == null) {
                        globalDeclaration!!.add(declarationWithParent)
                    } else {
                        internDeclaration!!.add(declarationWithParent)
                    }
                }
//                else if (parent is CPPASTCompositeTypeSpecifier) {
//                    solveClassDefinition(parent, anonimStatement)
//                    declarationWithParent.parent =
//                        anonimStatement.statement as CompositeTypeSpecifier
//                }

            }
        } else if (data.declSpecifier is EnumerationSpecifier) {
            if (globalDeclaration == null) {
                globalDeclaration = ArrayList()
            }
            globalDeclaration!!.add(DeclarationWithParent(data, null))
        }
    }

    fun clearList() {
        globalDeclaration = null
        internDeclaration = null
    }

    private fun solveFunctionDefinition(funcDef: CPPASTFunctionDefinition, statement: Statement?) {
        val functionDefinition = FunctionDefinitionService.setFunction(funcDef)
        StatementMapper.addStatementToStatement(statement!!, functionDefinition)
    }

    private fun solveClassDefinition(
        classDef: CPPASTCompositeTypeSpecifier,
        statement: Statement?
    ) {
        val composite = CompositeTypeSpecifierService.setClassDefinitions(classDef)
        StatementMapper.addStatementToStatement(statement!!, composite)
    }

    fun removeDeclarationBecouseClassDetected(list: ArrayList<Statement>?) {
        list!!.iterator().forEachRemaining { element ->
            run {
                var cacheData: DeclarationWithParent? = null
                if (internDeclaration != null) {
                    internDeclaration!!.iterator().forEachRemaining { internDecl ->
                        run {
                            if (cacheData == null && internDecl.declaration == element) {
                                cacheData = internDecl
                            }
                        }
                    }
                    if(cacheData != null) {
                        internDeclaration!!.remove(cacheData)
                    }
                }
//                if (globalDeclaration != null && cacheData == null) {
//                    globalDeclaration!!.iterator().forEachRemaining { globalDecl ->
//                        run {
//                            if (globalDecl.declaration.equals(element)) {
//                                cacheData = globalDecl
//                            }
//                        }
//                    }
//                    if(cacheData != null) {
//                        globalDeclaration!!.remove(cacheData)
//                    }
//                }
            }
        }
    }

    fun removeGlobalClassDetected(node: CompositeTypeSpecifier) {
        println()
    }
}
