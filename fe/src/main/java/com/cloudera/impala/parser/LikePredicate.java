// Copyright (c) 2011 Cloudera, Inc. All rights reserved.

package com.cloudera.impala.parser;

import com.cloudera.impala.catalog.PrimitiveType;
import com.google.common.base.Preconditions;

class LikePredicate extends Predicate {
  enum Operator {
    LIKE,
    RLIKE,
    REGEXP;

    @Override
    public String toString() {
      switch (this) {
        case LIKE: return "LIKE";
        case RLIKE: return "RLIKE";
        case REGEXP: return "REGEXP";
      }
      return "";
    }
  }
  private final Operator op;

  public LikePredicate(Operator op, Expr e1, Expr e2) {
    super();
    this.op = op;
    Preconditions.checkNotNull(e1);
    children.add(e1);
    Preconditions.checkNotNull(e2);
    children.add(e2);
  }

  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    return ((LikePredicate) obj).op == op;
  }

  @Override
  public void analyze(Analyzer analyzer) throws Analyzer.Exception {
    super.analyze(analyzer);
    if (getChild(0).getType() != PrimitiveType.STRING) {
      throw new Analyzer.Exception(
          "left operand of " + op.toString() + " must be of type STRING: " + this.toSql());
    }
    if (getChild(1).getType() != PrimitiveType.STRING) {
      throw new Analyzer.Exception(
          "right operand of " + op.toString() + " must be of type STRING: " + this.toSql());
    }
  }
}
