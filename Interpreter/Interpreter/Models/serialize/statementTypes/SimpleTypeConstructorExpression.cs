﻿using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{
    public class SimpleTypeConstructorExpression : IStatement
    {
        public string castType { set; get; }

        public IList<IStatement> parameters = new List<IStatement>();
    }
}