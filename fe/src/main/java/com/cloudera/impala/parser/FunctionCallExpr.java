// Copyright (c) 2011 Cloudera, Inc. All rights reserved.

package com.cloudera.impala.parser;

import java.util.List;

class FunctionCallExpr extends Expr {
  private final String functionName;

  public FunctionCallExpr(String functionName, List<Expr> params) {
    super();
    this.functionName = functionName;
    children.addAll(params);
  }

  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    return ((FunctionCallExpr) obj).functionName.equals(functionName);
  }

  @Override
  public void analyze(Analyzer analyzer) throws Analyzer.Exception {
    throw new Analyzer.Exception("CAST not supported");
  }
}
