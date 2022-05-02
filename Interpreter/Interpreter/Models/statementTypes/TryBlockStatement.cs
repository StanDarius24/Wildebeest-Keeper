﻿using System.Collections.Generic;

namespace Interpreter.Models.statementTypes{
    public class TryBlockStatement : IStatement
    {
        public IList<IStatement> catchHandlers = new List<IStatement>();
        
        public IStatement tryBlock { set; get; }
    }
}