
$s='f("stop", Conditions.class, 0, 11, 2);
    f("warning", Warning.class, 0, 111, 3);

    f("gettext", Text.class, 0, 11, 2);
    f("ngettext", Text.class, 0, 11, 4);
    f("bindtextdomain", Text.class, 0, 11, 2);
    f(".addCondHands", Conditions.class, 0, 111, 5);
    f(".resetCondHands", /*resetCondHands*/ null, 0, 111, 1);
    f(".signalCondition", Conditions.class, 0, 11, 3);
    f(".dfltStop", Conditions.class, 0, 11, 2);
    f(".dfltWarn", /*dfltWarn*/ null, 0, 11, 2);
    f(".addRestart", Conditions.class, 0, 11, 1);
    f(".getRestart", /*getRestart*/ null, 0, 11, 1);
    f(".invokeRestart", /*invokeRestart*/ null, 0, 11, 2);
    f(".addTryHandlers", /*addTryHandlers*/ null, 0, 111, 0);

    f("geterrmessage", Conditions.class, 0, 11, 0);
    f("seterrmessage", Conditions.class, 0, 111, 1);
    f("printDeferredWarnings", Warning.class, 0, 111, 0);
    f("interruptsSuspended", /*interruptsSuspended*/ null, 0, 11, -1);

    addInternal("restart", new RestartFunction());
    add(new ClosureFunction());

    f("as.function.default", Types.class, 0, 11, 2, PP_FUNCTION, PREC_FN, 0);

    add(new AssignLeftFunction());
    add(new AssignFunction());
    
    add(new ReassignLeftFunction());
    add(new BeginFunction());
    add(new ParenFunction());

    f(".subset", Subsetting.class, 1, 1, -1);
    f(".subset2", Subsetting.class, 2, 1, -1);
    f("[",Subsetting.class, 1, 0, -1, PP_SUBSET, PREC_SUBSET, 0);
    f("[[", Subsetting.class, 2, 0, -1, PP_SUBSET, PREC_SUBSET, 0);
    f("$", Subsetting.class, 3, 0, 2, PP_DOLLAR, PREC_DOLLAR, 0);
    f("@", Subsetting.class, 0, 0, 2, PP_DOLLAR, PREC_DOLLAR, 0);
    f("[<-", Subsetting.class, 0, 0, 3, PP_SUBASS, PREC_LEFT, 1);
    f("[[<-", Subsetting.class, 1, 0, 3, PP_SUBASS, PREC_LEFT, 1);
    f("$<-", Subsetting.class, 1, 0, 3, PP_SUBASS, PREC_LEFT, 1);

    add(new SwitchFunction());

    f("browser", /*browser*/ null, 0, 101, 3);
    f("debug", /*debug*/ null, 0, 111, 3);
    f("undebug", /*debug*/ null, 1, 111, 1);
    f("isdebugged", /*debug*/ null, 2, 11, 1);
    f("debugonce", /*debug*/ null, 3, 111, 3);
    f(".primTrace", /*trace*/ null, 0, 101, 1);
    f(".primUntrace", /*trace*/ null, 1, 101, 1);

    add(new InternalFunction());
    add(new OnExitFunction());

    addInternal("Recall", new RecallFunction());
    f("delayedAssign", Evaluation.class, 0, 111, 4);
    f("makeLazy", Serialization.class, 0, 111, 5);
    f(".Primitive", Evaluation.class, 0, 1, 1);
    f("identical",  Types.class, 0, 11, 5);


/* Binary Operators */
/* these are group generic and so need to eval args */
    f("+",  Ops.class, 0, /* PLUSOP, */ 1, 2, PP_BINARY, PREC_SUM, 0);
    f("-", Ops.class,  0, /* MINUSOP, */ 1, 2, PP_BINARY, PREC_SUM, 0);
    f("*", Ops.class,  0,/*TIMESOP ,*/ 1, 2, PP_BINARY, PREC_PROD, 0);
    f("/", Ops.class,  0,/*DIVOP,*/ 1, 2, PP_BINARY2, PREC_PROD, 0);
    f("^", Ops.class,  0, /*POWOP,*/ 1, 2, PP_BINARY2, PREC_POWER, 1);
//    add(new OpsFunction("+"));
//    add(new OpsFunction("-"));
//    add(new OpsFunction("*"));
//    add(new OpsFunction("/"));
//    add(new OpsFunction("^"));

    f("%%", Ops.class, 0 /* MODOP */, 1, 2, PP_BINARY2, PREC_PERCENT, 0);
    f("%/%", Ops.class, 0 /* IDIVOP */, 1, 2, PP_BINARY2, PREC_PERCENT, 0);
    f("%*%", Matrices.class, 0, 1, 2, PP_BINARY, PREC_PERCENT, 0);
    f("crossprod", Matrices.class, 1, 11, 2);
    f("tcrossprod", Matrices.class, 2, 11, 2);


/* these are group generic and so need to eval args */
    f("==", Ops.class, EQOP, 1, 2, PP_BINARY, PREC_COMPARE, 0);
    f("!=", Ops.class, NEOP, 1, 2, PP_BINARY, PREC_COMPARE, 0);
    f("<", Ops.class, LTOP, 1, 2, PP_BINARY, PREC_COMPARE, 0);
    f("<=", Ops.class, LEOP, 1, 2, PP_BINARY, PREC_COMPARE, 0);
    f(">=", Ops.class, GEOP, 1, 2, PP_BINARY, PREC_COMPARE, 0);
    f(">", Ops.class, GTOP, 1, 2, PP_BINARY, PREC_COMPARE, 0);
    f("&", Ops.class,  1, 1, 2, PP_BINARY, PREC_AND, 0);
    f("|", Ops.class, 2, 1, 2, PP_BINARY, PREC_OR, 0);
    f("!", Ops.class, 3, 1, 1, PP_UNARY, PREC_NOT, 0);
//    add(new OpsFunction("=="));
//    add(new OpsFunction("!="));
//    add(new OpsFunction("<"));
//    add(new OpsFunction("<="));
//    add(new OpsFunction(">"));
//    add(new OpsFunction(">="));
//    add(new OpsFunction("&"));
//    add(new OpsFunction("|"));
//    add(new OpsFunction("!"));

    f("&&", Comparison.class, "and", 1, 0, 2, PP_BINARY, PREC_AND, 0);
    f("||", Comparison.class, "or", 2, 0, 2, PP_BINARY, PREC_OR, 0);
    f(":", Sequences.class, "colon", 0, 1, 2, PP_BINARY2, PREC_COLON, 0);

    add(new TildeFunction());

/* Logic Related Functions */
/* these are group generic and so need to eval args */
    f("all", Summary.class, 1, 1, -1);
    f("any", Summary.class, 2, 1, -1);


/* Vectors, Matrices and Arrays */

/* printname  c-entry   offset  eval  arity pp-kind      precedence rightassoc * ---------  -------   ------  ----  ----- -------      ---------- ----------*/
    f("vector", Types.class, 0, 11, 2);
    f("complex", ComplexGroup.class, 0, 11, 3);
    f("matrix", Matrices.class, 0, 11, -1);
    f("length", Types.class, 0, 1, 1);
    f("length<-", Types.class, 0, 1, 2, PP_FUNCALL, PREC_LEFT, 1);
    f("row", Matrices.class, 1, 11, 1);
    f("col", Matrices.class, 2, 11, 1);
    f("c", Combine.class,  0, 1, -1);
    f("unlist", Combine.class, 0, 11, 3);
    f("cbind", Combine.class, 1, 10, -1);
    f("rbind", Combine.class, 2, 10, -1);
    f("drop", Types.class, 0, 11, 1);
    f("oldClass", Attributes.class, 0, 1, 1);
    f("oldClass<-", Attributes.class, 0, 1, 2, PP_FUNCALL, PREC_LEFT, 1);
    f("class", Attributes.class, "getClass", 0, 1, 1);
    f(".cache_class", Methods.class, 1, 1, 2, PP_FUNCALL, PREC_FN, 0);
    f("class<-", Attributes.class, "setClass", 0, 1, 2);
    f("unclass", Attributes.class, 0, 1, 1);
    f("names", Attributes.class,  "getNames", 0, 1, 1);
    f("names<-", Attributes.class, "setNames", 0, 1, 2, PP_FUNCALL, PREC_LEFT, 1);
    f("dimnames", Attributes.class, 0, 1, 1);
    f("dimnames<-", Attributes.class, 0, 1, 2, PP_FUNCALL, PREC_LEFT, 1);
    f("all.names", AllNamesVisitor.class, 0, 11, 4);
    f("dim", Attributes.class, 0, 1, 1);
    f("dim<-", Attributes.class, 0, 1, 2, PP_FUNCALL, PREC_LEFT, 1);
    f("attributes", Attributes.class, 0, 1, 1);
    f("attributes<-", Attributes.class, null, 0, 1, 1, PP_FUNCALL, PREC_LEFT, 1);
    f("attr", Attributes.class, 0, 1, -1);
    f("attr<-", Attributes.class, 0, 1, 3, PP_FUNCALL, PREC_LEFT, 1);
    f("comment", Attributes.class, 0, 11, 1);
    f("comment<-", Attributes.class, 0, 11, 2, PP_FUNCALL, PREC_LEFT, 1);
    f("levels<-", Attributes.class, 0, 1, 2, PP_FUNCALL, PREC_LEFT, 1);
    f("get", Types.class, 1, 11, 4);
    f("mget", /*mget*/ null, 1, 11, 5);
    f("exists", Types.class, 0, 11, 4);
    f("assign", Evaluation.class, 0, 111, 4);
    f("remove", Evaluation.class, 0, 111, 3);
    f("duplicated", Duplicates.class, 0, 11, 3);
    f("unique", Duplicates.class, 1, 11, 3);
    f("anyDuplicated", Duplicates.class, 2, 11, 3);
    f("which.min", Sort.class, 0, 11, 1);
    f("which", Match.class, 0, 11, 1);
    f("pmin", Summary.class, 0, 11, -1);
    f("pmax", Summary.class, 1, 11, -1);
    f("which.max", Sort.class, 1, 11, 1);
    f("match", Match.class, 0, 11, 4);
    f("pmatch", Match.class, 0, 11, 4);
    f("charmatch", Match.class, 0, 11, 3);
    f("match.call", Match.class, 0, 11, 3);
    f("complete.cases", CompleteCases.class, 0, 11, 1);

    f("attach", Types.class, 0, 111, 3);
    f("detach", Types.class, 0, 111, 1);
    f("search", Types.class, 0, 11, 0);


/* Mathematical Functions */
/* these are group generic and so need to eval args */
/* Note that the number of arguments for the primitives in the Math group  only applies to the default method. */
    f("round", MathExt.class, 10001, 0, -1);
    f("signif", MathExt.class, 10004, 0, -1);
    f("atan",Math.class, 10002, 1, 1);
    f("log", MathExt.class, 10003, 0, -1);
    f("log10", Math.class, 10, 1, 1);
    f("log2", MathExt.class, 2, 1, 1);
    f("abs", MathExt.class, 6, 1, 1);
    f("floor", Math.class, 1, 1, 1);
    f("ceiling", Math.class, "ceil", 2, 1, 1);
    f("sqrt", Math.class, 3, 1, 1);
    f("sign", MathExt.class, 4, 1, 1);
    f("trunc", MathExt.class, 5, 1, -1);
    
    f("exp", Math.class, 10, 1, 1);
    f("expm1", MathExt.class, 11, 1, 1);
    f("log1p", MathExt.class, 12, 1, 1);

    f("cos", Math.class, 20, 1, 1);
    f("sin", Math.class, 21, 1, 1);
    f("tan", Math.class, 22, 1, 1);
    f("acos", Math.class, 23, 1, 1);
    f("asin", Math.class, 24, 1, 1);

    f("cosh", Math.class, 30, 1, 1);
    f("sinh", Math.class, 31, 1, 1);
    f("tanh", Math.class, 32, 1, 1);
    f("acosh", MathExt.class, 33, 1, 1);
    f("asinh", MathExt.class, 34, 1, 1);
    f("atanh", MathExt.class, 35, 1, 1);

    f("lgamma", org.apache.commons.math.special.Gamma.class, "logGamma", 40, 1, 1);
    f("gamma", MathExt.class, 41, 1, 1);

    f("digamma", org.apache.commons.math.special.Gamma.class, 42, 1, 1);
    f("trigamma",org.apache.commons.math.special.Gamma.class, 43, 1, 1);
/* see "psigamma" below !*/

/* Mathematical Functions of Two Numeric (+ 1-2 int) Variables */

    f("atan2", MathExt.class, 0, 11, 2);

    f("lbeta", MathExt.class, 2, 11, 2);
    f("beta", MathExt.class, 3, 11, 2);
    f("lchoose", MathExt.class, 4, 11, 2);
    f("choose", MathExt.class, 5, 11, 2);

    f("dchisq", Distributions.class, 6, 11, 2 + 1);
    f("pchisq", Distributions.class, 7, 11, 2 + 2);
    f("qchisq", Distributions.class, 8, 11, 2 + 2);

    f("dexp", Distributions.class, 9, 11, 2 + 1);
    f("pexp", Distributions.class, 10, 11, 2 + 2);
    f("qexp", Distributions.class, 11, 11, 2 + 2);

    f("dgeom", Distributions.class, 12, 11, 2 + 1);
    f("pgeom", Distributions.class, 13, 11, 2 + 2);
    f("qgeom", Distributions.class, 14, 11, 2 + 2);

    f("dpois", Distributions.class, 15, 11, 2 + 1);
    f("ppois", Distributions.class, 16, 11, 2 + 2);
    f("qpois", Distributions.class, 17, 11, 2 + 2);

    f("dt", Distributions.class, 18, 11, 2 + 1);
    f("pt", Distributions.class, 19, 11, 2 + 2);
    f("qt", Distributions.class, 20, 11, 2 + 2);

    f("dsignrank", Distributions.class, 21, 11, 2 + 1);
    f("psignrank", Distributions.class, 22, 11, 2 + 2);
    f("qsignrank", Distributions.class, 23, 11, 2 + 2);

    f("besselJ", /*math2*/ null, 24, 11, 2);
    f("besselY", /*math2*/ null, 25, 11, 2);

    f("psigamma", PsiGamma.class, 26, 11, 2);


/* Mathematical Functions of a Complex Argument */
/* these are group generic and so need to eval args */

    f("Re", ComplexGroup.class, 1, 1, 1);
    f("Im", ComplexGroup.class, 2, 1, 1);
    f("Mod", ComplexGroup.class, 3, 1, 1);
    f("Arg", ComplexGroup.class, 4, 1, 1);
    f("Conj", ComplexGroup.class, 5, 1, 1);


/* Mathematical Functions of Three Numeric (+ 1-2 int) Variables */

    f("dbeta", Distributionss.class, 1, 11, 3 + 1);
    f("pbeta", Distributions.class, 2, 11, 3 + 2);
    f("qbeta", Distributions.class, 3, 11, 3 + 2);

    f("dbinom", Distributions.class, 4, 11, 3 + 1);
    f("pbinom", Distributions.class, 5, 11, 3 + 2);
    f("qbinom", Distributions.class, 6, 11, 3 + 2);

    f("dcauchy", Distributions.class, 7, 11, 3 + 1);
    f("pcauchy", Distributions.class, 8, 11, 3 + 2);
    f("qcauchy", Distributions.class, 9, 11, 3 + 2);

    f("df", Distributions.class, 10, 11, 3 + 1);
    f("pf", Distributions.class, 11, 11, 3 + 2);
    f("qf", Distributions.class, 12, 11, 3 + 2);

    f("dgamma", Distributions.class, 13, 11, 3 + 1);
    f("pgamma", Distributions.class, 14, 11, 3 + 2);
    f("qgamma", Distributions.class, 15, 11, 3 + 2);

    f("dlnorm", Distributions.class, 16, 11, 3 + 1);
    f("plnorm", Distributions.class, 17, 11, 3 + 2);
    f("qlnorm", Distributions.class, 18, 11, 3 + 2);

    f("dlogis", Distributions.class, 19, 11, 3 + 1);
    f("plogis", Distributions.class, 20, 11, 3 + 2);
    f("qlogis", Distributions.class, 21, 11, 3 + 2);

    f("dnbinom", Distributions.class, 22, 11, 3 + 1);
    f("pnbinom", Distributions.class, 23, 11, 3 + 2);
    f("qnbinom", Distributions.class, 24, 11, 3 + 2);

    f("dnorm", Distributions.class, 25, 11, 3 + 1);
    f("pnorm", Distributions.class, 26, 11, 3 + 2);
    f("qnorm", Distributions.class, 27, 11, 3 + 2);

    f("dunif", Distributions.class, 28, 11, 3 + 1);
    f("punif", Distributions.class, 29, 11, 3 + 2);
    f("qunif", Distributions.class, 30, 11, 3 + 2);

    f("dweibull", Distributions.class, 31, 11, 3 + 1);
    f("pweibull", Distributions.class, 32, 11, 3 + 2);
    f("qweibull", Distributions.class, 33, 11, 3 + 2);

    f("dnchisq", Distributions.class, 34, 11, 3 + 1);
    f("pnchisq", Distributions.class, 35, 11, 3 + 2);
    f("qnchisq", Distributions.class, 36, 11, 3 + 2);

    f("dnt", Distributions.class, 37, 11, 3 + 1);
    f("pnt", Distributions.class , 38, 11, 3 + 2);
    f("qnt", Distributions.class, 39, 11, 3 + 2);

    f("dwilcox", Distributions.class, 40, 11, 3 + 1);
    f("pwilcox", Distributions.class, 41, 11, 3 + 2);
    f("qwilcox", Distributions.class, 42, 11, 3 + 2);

    f("besselI", /*math3*/ null, 43, 11, 3);
    f("besselK", /*math3*/ null, 44, 11, 3);

    f("dnbinom_mu", Distributions.class, 45, 11, 3 + 1);
    f("pnbinom_mu", Distributions.class, 46, 11, 3 + 2);
    f("qnbinom_mu", Distributions.class, 47, 11, 3 + 2);


/* Mathematical Functions of Four Numeric (+ 1-2 int) Variables */

    f("dhyper", Distributions.class, 1, 11, 4 + 1);
    f("phyper", Distributions.class, 2, 11, 4 + 2);
    f("qhyper", Distributions.class, 3, 11, 4 + 2);

    f("dnbeta", Beta.class, 4, 11, 4 + 1);
    f("pnbeta", Beta.class, 5, 11, 4 + 2);
    f("qnbeta", Beta.class, 6, 11, 4 + 2);

    f("dnf", Distributions.class , 7, 11, 4 + 1);
    f("pnf", Distributions.class, 8, 11, 4 + 2);
    f("qnf", Distributions.class, 9, 11, 4 + 2);

    /* Where is this primitive? (dtukey) I could\'nt find it in C source */
    f("dtukey", /*math4*/ null, 10, 11, 4 + 1);
    f("ptukey", Distributions.class, 11, 11, 4 + 2);
    f("qtukey", Distributions.class, 12, 11, 4 + 2);

/* Random Numbers */

    f("rchisq", RNG.class, 0, 11, 2);
    f("rexp", RNG.class, 1, 11, 2);
    f("rgeom", RNG.class, 2, 11, 2);
    f("rpois", RNG.class, 3, 11, 2);
    f("rt", RNG.class, 4, 11, 2);
    f("rsignrank", RNG.class, 5, 11, 2);

    f("rbeta", RNG.class, 0, 11, 3);
    f("rbinom", RNG.class, 1, 11, 3);
    f("rcauchy",RNG.class, 2, 11, 3);
    f("rf", RNG.class, 3, 11, 3);
    f("rgamma", RNG.class, 4, 11, 3);
    f("rlnorm", RNG.class, 5, 11, 3);
    f("rlogis", RNG.class, 6, 11, 3);
    f("rnbinom",RNG.class, 7, 11, 3);
    f("rnbinom_mu", RNG.class , 13, 11, 3);
    f("rnchisq", RNG.class, 12, 11, 3);
    f("rnorm", RNG.class, 8, 11, 3);
    f("runif", RNG.class, 9, 11, 3);
    f("rweibull", RNG.class, 10, 11, 3);
    f("rwilcox", RNG.class, 11, 11, 3);

    f("rhyper", RNG.class, 0, 11, 4);

    f("rmultinom", RNG.class, 0, 11, 3);
    f("sample", Sampling.class, 0, 11, 4);

    f("RNGkind", RNG.class, 0, 11, 2);
    f("set.seed", RNG.class, 0, 11, 3);

/* Data Summaries */
/* sum, min, max, prod, range are group generic and so need to eval args */
    f("sum", Summary.class, 0, 1, -1);
    f("mean", Summary.class, 1, 11, 1);
    f("min", Summary.class, 2, 1, -1);
    f("max", Summary.class, 3, 1, -1);
    f("prod", Summary.class, 4, 1, -1);
    f("range", Summary.class, 0, 1, -1);
    f("cov", Covariance.class, 0, 11, 4);
    f("cor", Covariance.class, 1, 11, 4);

/* Note that the number of arguments in this group only applies   to the default method */
    f("cumsum", Summary.class, 1, 1, 1);
    f("cumprod", Summary.class, 2, 1, 1);
    f("cummax", Summary.class, 3, 1, 1);
    f("cummin", Summary.class, 4, 1, 1);

/* Type coercion */

    f("as.character", Types.class, "asCharacter", 0, 1, -1);
    f("as.integer", Types.class, "asInteger", 1, 1, -1);
    f("as.double", Types.class, "asDouble",  2, 1, -1);
    f("as.complex", Types.class, 3, 1, -1);
    f("as.logical", Types.class, "asLogical", 4, 1, -1);
    f("as.raw", Types.class, 5, 1, 1);
    f("as.vector", Types.class, 0, 11, 2);
    f("paste", Text.class, 0, 11, 3);
    f("file.path", Text.class, 0, 11, 2);
    f("format", Text.class, 0, 11, 8);
    f("format.info", /*formatinfo*/ null, 0, 11, 3);
    f("cat", Cat.class, 0, 111, 6);
    add(new CallFunction());
    f("do.call", Evaluation.class, 0, 211, 3);
    f("as.call", Types.class, 0, 1, 1);
    f("type.convert", Scan.class, 1, 11, 4);
    f("as.environment", Types.class, "asEnvironment", 0, 1, 1);
    f("storage.mode<-", Types.class, 0, 1, 2);


/* String Manipulation */

    f("nchar", Text.class, 1, 11, 3);
    f("nzchar", Text.class, 1, 1, 1);
    f("substr", Text.class, 1, 11, 3);
    f("substr<-", Text.class, 1, 11, 4);
    f("strsplit", Text.class, 1, 11, 6);
    f("abbreviate", /*abbrev*/ null, 1, 11, 3);
    f("make.names", Text.class, 0, 11, 2);
    f("grep", Text.class, 0, 11, 9);
    f("grepl", Text.class, 1, 11, 9);
    f("sub", Text.class, 0, 11, 8);
    f("gsub", Text.class, 1, 11, 8);
    f("regexpr", Text.class, 1, 11, 7);
    f("gregexpr", /*gregexpr*/ null, 1, 11, 7);
    f("agrep", Text.class, 1, 11, 9);
    f("tolower", Text.class, 0, 11, 1);
    f("toupper", Text.class, 1, 11, 1);
    f("chartr", Text.class, 1, 11, 3);
    f("sprintf", Text.class, 1, 11, -1);
    f("make.unique", Text.class, 0, 11, 2);
    f("charToRaw", Types.class, 1, 11, 1);
    f("rawToChar", Types.class, 1, 11, 2);
    f("rawShift", Types.class , 1, 11, 2);
    f("intToBits", Types.class, 1, 11, 1);
    f("rawToBits", Types.class , 1, 11, 1);
    f("packBits", /*packBits*/ null, 1, 11, 2);
    f("utf8ToInt", Text.class, 1, 11, 1);
    f("intToUtf8", Text.class, 1, 11, 2);
    f("encodeString",Text.class, 1, 11, 5);
    f("iconv", Text.class, 0, 11, 5);
    f("strtrim", Text.class, 0, 11, 2);
    f("strtoi", Text.class,  0, 11, 2);
    
/* Type Checking (typically implemented in ./coerce.c ) */

    f("is.null", Types.class,  0 /*NILSXP*/, 1, 1);
    f("is.logical", Types.class ,0 /*LGLSXP*/, 1, 1);
    f("is.integer", Types.class, 0 /*INTSXP*/, 1, 1);
    f("is.real", Types.class,  0/*REALSXP */, 1, 1);
    f("is.double", Types.class, 0 /*REALSXP*/, 1, 1);
    f("is.complex", Types.class, 0/*CPLXSXP*/, 1, 1);
    f("is.character", Types.class, 0 /*STRSXP*/, 1, 1);
    f("is.symbol", Types.class,  0 /*SYMSXP*/, 1, 1);
    f("is.environment", Types.class, "isEnvironment", 0/* ENVSXP */, 1, 1);
    f("is.list", Types.class,"isList", 0/* VECSXP */, 1, 1);
    f("is.pairlist", Types.class, "isPairList", 0 /*LISTSXP */, 1, 1);
    f("is.expression", Types.class, "isExpression",  0 /* EXPRSXP*/, 1, 1);
    f("is.raw", Types.class ,0 /* RAWSXP */, 1, 1);

    f("is.object", Types.class, 50, 1, 1);

    f("is.numeric", Types.class,  100, 1, 1);
    f("is.matrix", Types.class, 101, 1, 1);
    f("is.array", Types.class, 102, 1, 1);

    f("is.atomic", Types.class, 200, 1, 1);
    f("is.recursive", Types.class, 201, 1, 1);

    f("is.call",  Types.class, 300, 1, 1);
    f("is.language", Types.class, 301, 1, 1);
    f("is.function", Types.class, 302, 1, 1);

    f("is.single", Types.class, 999, 1, 1);

    f("is.vector", Types.class, 0, 11, 2);
    f("is.na", Types.class,  0, 1, 1);
    f("is.nan", Types.class,  0, 1, 1);
    f("is.finite", Types.class, 0, 1, 1);
    f("is.infinite", Types.class, 0, 1, 1);


/* Miscellaneous */

    f("proc.time", System.class, 0, 1, 0);
    f("gc.time", /*gctime*/ null, 0, 1, -1);
    f("Version", System.class, 0, 11, 0);
    f("machine", System.class, 0, 11, 0);
    f("commandArgs", System.class, 0, 11, 0);
    f("unzip", Files.class, 0, 111, 6);
    f("system", System.class, 0, 211, 5);
    f("parse", Evaluation.class, 0, 11, 6);
    f("parse_Rd", /*parseRd*/ null, 0, 11, 7);
    f("save", Serialization.class, 0, 111, 6);
    f("saveToConn", Serialization.class, 0, 111, 6);
    f("load", /*load*/ null, 0, 111, 2);
    f("loadFromConn2", Serialization.class, 0, 111, 2);
    f("serializeToConn", Serialization.class, 0, 111, 5);
    f("unserializeFromConn", Serialization.class, 0, 111, 2);
    f("deparse", Deparse.class, 0, 11, 5);
    f("deparseRd", /*deparseRd*/ null, 0, 11, 2);
    f("dput", /*dput*/ null, 0, 111, 3);
    f("dump", /*dump*/ null, 0, 111, 5);
    add(new SubstituteFunction());
    add(new QuoteFunction());// f("quote", Evaluation.class, 0, 0, 1);
    f("quit", Session.class, 0, 111, 3);
    f("interactive", Session.class, 0, 0, 0);
    f("readline", /*readln*/ null, 0, 11, 1);
    f("menu", Session.class, 0, 11, 1);
    f("print.default", Print.class, 0, 111, 9);
    f("print.function", Print.class, 0, 111, 3);
    f("prmatrix", /*prmatrix*/ null, 0, 111, 6);
    f("invisible", Types.class, 0, 101, 1);
    f("gc", System.class, 0, 11, 2);
    f("gcinfo", /*gcinfo*/ null, 0, 11, 1);
    f("gctorture", /*gctorture*/ null, 0, 11, 1);
    f("memory.profile", /*memoryprofile*/ null, 0, 11, 0);
    add(new RepFunction());
    f("rep.int", Sequences.class, 0, 11, 2);
    f("seq.int", Sequences.class, 0, 0, -1);
    f("seq_len", Sequences.class, 0, 1, 1);
    f("seq_along", Sequences.class, "seqAlong", 0, 1, 1);
    f("list", Types.class, "list", 1, 1, -1);
    f("split",  Split.class, 0, 11, 2);
    f("is.loaded", /*isloaded*/ null, 0, 11, -1, PP_FOREIGN, PREC_FN, 0);
    f(".C", Native.class, 0, 1, -1, PP_FOREIGN, PREC_FN, 0);
    f(".Fortran", Native.class, 1, 1, -1, PP_FOREIGN, PREC_FN, 0);
    f(".External", /*External*/ null, 0, 1, -1, PP_FOREIGN, PREC_FN, 0);
    f(".Call", Native.class, 0, 1, -1, PP_FOREIGN, PREC_FN, 0);
    f(".External.graphics", /*Externalgr*/ null, 0, 1, -1, PP_FOREIGN, PREC_FN, 0);
    f(".Call.graphics", /*dotcallgr*/ null, 0, 1, -1, PP_FOREIGN, PREC_FN, 0);
    f("recordGraphics", /*recordGraphics*/ null, 0, 211, 3, PP_FOREIGN, PREC_FN, 0);
    f("dyn.load", Native.class,  0, 111, 4);
    f("dyn.unload", System.class, 0, 111, 1);
    f("ls", Types.class, 1, 11, 2);
    f("typeof", Types.class, 1, 11, 1);
    f("eval", Evaluation.class, 0, 211, 3);
    f("eval.with.vis",Evaluation.class, 1, 211, 3);
    f("withVisible", /*withVisible*/ null, 1, 10, 1);
    add(new ExpressionFunction());
    f("sys.parent", Contexts.class, 1, 11, -1);
    f("sys.call", Contexts.class, 2, 11, -1);
    f("sys.frame", Contexts.class, 3, 11, -1);
    f("sys.nframe", Contexts.class, 4, 11, -1);
    f("sys.calls", Contexts.class, 5, 11, -1);
    f("sys.frames", Contexts.class, 6, 11, -1);
    f("sys.on.exit", Contexts.class, 7, 11, -1);
    f("sys.parents", Contexts.class, 8, 11, -1);
    f("sys.function", Contexts.class, 9, 11, -1);
    f("browserText", /*sysbrowser*/ null, 1, 11, 1);
    f("browserCondition", /*sysbrowser*/ null, 2, 11, 1);
    f("browserSetDebug", /*sysbrowser*/ null, 3, 111, 1);
    f("parent.frame", Contexts.class, "parentFrame", 0, 11, -1);
    f("sort", Sort.class, 1, 11, 2);
    f("xtfrm", Sort.class, 0, 1, 1);
    f("is.unsorted", Sort.class, 0, 11, 2);
    f("psort", Sort.class, null, 0, 11, 2);
    f("qsort", Sort.class, 0, 11, 2);
    f("radixsort", /*radixsort*/ null, 0, 11, 3);
    f("order", Sort.class, 0, 11, -1);
    f("rank", /*rank*/ null, 0, 11, 2);
    f("missing", Evaluation.class, "missing", 1, 0, 1);
    f("nargs", Evaluation.class, 1, 0, 0);
    f("scan", Scan.class, 0, 11, 18);
    f("count.fields", /*countfields*/ null, 0, 11, 6);
    f("readTableHead", Scan.class, 0, 11, 6);
    f("t.default", Matrices.class, 0, 11, 1);
    f("aperm", Matrices.class, 0, 11, 3);
    f("builtins", /*builtins*/ null, 0, 11, 1);
    f("edit", /*edit*/ null, 0, 11, 4);
    f("dataentry", /*dataentry*/ null, 0, 11, 2);
    f("dataviewer", /*dataviewer*/ null, 0, 111, 2);
    f("args", /*args*/ null, 0, 11, 1);
    f("formals", Types.class, 0, 11, 1);
    f("body", Types.class, 0, 11, 1);
    f("bodyCode", /*bodyCode*/ null, 0, 11, 1);
    f("emptyenv", Types.class, 0, 1, 0);
    f("baseenv", Types.class, 0, 1, 0);
    f("globalenv", Types.class, 0, 1, 0);
    f("environment", Types.class, 0, 11, 1);
    f("environment<-", Types.class, 0, 1, 2, PP_FUNCALL, PREC_LEFT, 1);
    f("environmentName", Types.class, 0, 11, 1);
    f("env2list", Types.class, 0, 11, 2);
    f("reg.finalizer", /*regFinaliz*/ null, 0, 11, 3);
    f("options", Types.class, 0, 211, 1);
    f("sink", Connections.class, 0, 111, 4);
    f("sink.number", /*sinknumber*/ null, 0, 11, 1);
    f("lib.fixup", Types.class, 0, 111, 2);
    f("pos.to.env", /*pos2env*/ null, 0, 1, 1);
    f("eapply", /*eapply*/ null, 0, 10, 4);
    f("lapply", Evaluation.class, 1, 10, 2);
    f("vapply", Evaluation.class, 1, 10, 4);
    f("rapply", /*rapply*/ null, 0, 11, 5);
    f("islistfactor",  Types.class, 0, 11, 2);
    f("colSums", Matrices.class, 0, 11, 4);
    f("colMeans", Matrices.class, 1, 11, 4);
    f("rowSums", Matrices.class, 2, 11, 4);
    f("rowMeans", Matrices.class, 3, 11, 4);
    f("Rprof", /*Rprof*/ null, 0, 11, 4);
    f("Rprofmem", /*Rprofmem*/ null, 0, 11, 3);
    f("tracemem", /*memtrace*/ null, 0, 1, 1);
    f("retracemem", /*memretrace*/ null, 0, 1, -1);
    f("untracemem", /*memuntrace*/ null, 0, 101, 1);
    f("object.size", /*objectsize*/ null, 0, 11, 1);
    f("inspect", /*inspect*/ null, 0, 111, 1);
    f("mem.limits", /*memlimits*/ null, 0, 11, 2);
    f("merge", /*merge*/ null, 0, 11, 4);
    f("capabilities", System.class, 0, 11, 0);
    f("capabilitiesX11", /*capabilitiesX11*/ null, 0, 11, 0);
    f("new.env", Types.class, 0, 11, 3);
    f("parent.env", Types.class, 0, 11, 1);
    f("parent.env<-", Types.class, 0, 11, 2, PP_FUNCALL, PREC_LEFT, 1);
    f("visibleflag", /*visibleflag*/ null, 0, 1, 0);
    f("l10n_info", /*l10n_info*/ null, 0, 11, 0);
    f("Cstack_info", /*Cstack_info*/ null, 0, 11, 0);
    f("startHTTPD", /*startHTTPD*/ null, 0, 11, 2);
    f("stopHTTPD", /*stopHTTPD*/ null, 0, 11, 0);

/* Functions To Interact with the Operating System */

    f("file.show", /*fileshow*/ null, 0, 111, 5);
    f("file.edit", /*fileedit*/ null, 0, 111, 3);
    f("file.create", Files.class, 0, 11, 2);
    f("file.remove", /*fileremove*/ null, 0, 11, 1);
    f("file.rename", /*filerename*/ null, 0, 11, 2);
    f("file.append", Files.class, 0, 11, 2);
    f("codeFiles.append", /*fileappend*/ null, 1, 11, 2);
    f("file.symlink", /*filesymlink*/ null, 0, 11, 2);
    f("file.copy", Files.class, 0, 11, 4);
    f("list.files", Files.class, 0, 11, 6);
    f("file.exists", Files.class, 0, 11, 1);
    f("file.choose", /*filechoose*/ null, 0, 11, 1);
    f("file.info", Files.class, 0, 11, 1);
    f("file.access", Files.class, 0, 11, 2);
    f("dir.create", Files.class, 0, 11, 4);
    f("tempfile", Files.class, 0, 11, 2);
    f("tempdir", Files.class, 0, 11, 0);
    f("R.home", System.class, "getRHome", 0, 11, 0);
    f("date", System.class, 0, 11, 0);
    f("index.search", /*indexsearch*/ null, 0, 11, 5);
    f("Sys.getenv", System.class, 0, 11, 2);
    f("Sys.setenv", System.class, 0, 111, 2);
    f("Sys.unsetenv", System.class, 0, 111, 1);
    f("getwd", Files.class, 0, 11, 0);
    f("setwd", Files.class, 0, 111, 1);
    f("basename", Files.class, 0, 11, 1);
    f("dirname", Files.class, 0, 11, 1);
    f("dirchmod", System.class, 0, 111, 1);
    f("Sys.chmod", System.class, 0, 111, 2);
    f("Sys.umask", System.class, 0, 111, 1);
    f("Sys.readlink", /*readlink*/ null, 0, 11, 1);
    f("Sys.info", System.class, 0, 11, 0);
    f("Sys.sleep", System.class, 0, 11, 1);
    f("Sys.getlocale", System.class, 0, 11, 1);
    f("Sys.setlocale", System.class, 0, 11, 2);
    f("Sys.localeconv", /*localeconv*/ null, 0, 11, 0);
    f("path.expand", Files.class, "pathExpand", 0, 11, 1);
    f("Sys.getpid",System.class, 0, 11, 0);
    f("normalizePath", Files.class, 0, 11, 1);
    f("Sys.glob", Files.class, "glob", 0, 11, 2);
    f("unlink", Files.class, 0, 111, 2);

/* Complex Valued Functions */
    f("fft", FFT.class, 0, 11, 2);
    f("mvfft", /*mvfft*/ null, 0, 11, 2);
    f("nextn", /*nextn*/ null, 0, 11, 2);
    f("polyroot", /*polyroot*/ null, 0, 11, 1);

/* Device Drivers */


/* Graphics */

    f("dev.control", /*devcontrol*/ null, 0, 111, 1);
    f("dev.displaylist", /*devcontrol*/ null, 1, 111, 0);
    f("dev.copy", /*devcopy*/ null, 0, 111, 1);
    f("dev.cur", /*devcur*/ null, 0, 111, 0);
    f("dev.next", /*devnext*/ null, 0, 111, 1);
    f("dev.off", /*devoff*/ null, 0, 111, 1);
    f("dev.prev", /*devprev*/ null, 0, 111, 1);
    f("dev.set", /*devset*/ null, 0, 111, 1);
    f("rgb", RgbHsv.class, 0, 11, 6);
    f("rgb256", RgbHsv.class, 1, 11, 5);
    f("rgb2hsv", RgbHsv.class, 0, 11, 1);
    f("hsv", RgbHsv.class, 0, 11, 5);
    f("hcl", /*hcl*/ null, 0, 11, 5);
    f("gray", RgbHsv.class, 0, 11, 1);
    f("colors", /*colors*/ null, 0, 11, 0);
    f("col2rgb", RgbHsv.class, 0, 11, 1);
    f("palette", /*palette*/ null, 0, 11, 1);
    f("plot.new", Plot.class, 0, 111, 0);
    f("plot.window", Plot.class, 0, 111, 3);
    f("axis", Plot.class, 0, 111, 13);
    f("plot.xy", /*plot_xy*/ null, 0, 111, 7);
    f("text", /*text*/ null, 0, 111, -1);
    f("mtext", /*mtext*/ null, 0, 111, 5);
    f("title", Plot.class, 0, 111, 4);
    f("abline", /*abline*/ null, 0, 111, 6);
    f("box", Plot.class, 0, 111, 3);
    f("rect", Plot.class, 0, 111, 6);
    f("polygon", Plot.class, 0, 111, 5);
    f("xspline", Plot.class, 0, 111, -1);
    f("par", Par.class, 0, 11, 1);
    f("segments", /*segments*/ null, 0, 111, -1);
    f("arrows", /*arrows*/ null, 0, 111, -1);
    f("layout", /*layout*/ null, 0, 111, 10);
    f("locator", /*locator*/ null, 0, 11, 2);
    f("identify", /*identify*/ null, 0, 211, 8);
    f("strheight", /*strheight*/ null, 0, 11, -1);
    f("strwidth", /*strwidth*/ null, 0, 11, -1);
    f("contour", /*contour*/ null, 0, 11, 12);
    f("contourLines", /*contourLines*/ null, 0, 11, 5);
    f("image", /*image*/ null, 0, 111, 4);
    f("dend", /*dend*/ null, 0, 111, 6);
    f("dend.window", /*dendwindow*/ null, 0, 111, 5);
    f("erase", /*erase*/ null, 0, 111, 1);
    f("persp", /*persp*/ null, 0, 111, 4);
    f("filledcontour", /*filledcontour*/ null, 0, 111, 5);
    f("getSnapshot", /*getSnapshot*/ null, 0, 111, 0);
    f("playSnapshot", /*playSnapshot*/ null, 0, 111, 1);
    f("symbols", /*symbols*/ null, 0, 111, -1);
    f("getGraphicsEvent", /*getGraphicsEvent*/ null, 0, 11, 5);
    f("devAskNewPage", /*devAskNewPage*/ null, 0, 211, 1);
    f("dev.size", /*devsize*/ null, 0, 11, 0);
    f("clip", /*clip*/ null, 0, 111, 4);
    f("grconvertX", Graphics.class, 0, 11, 3);
    f("grconvertY", Graphics.class, 1, 11, 3);

/* Objects */
    f("inherits", Attributes.class, 0, 11, 3);
    f("UseMethod", S3.class, 0, 200, -1);
    f("NextMethod", S3.class, 0, 210, -1);
    f("standardGeneric", Methods.class, 0, 201, -1);

/* Modelling Functionality */

    f("nlm", Optimizations.class, 0, 11, 11);
    f("fmin", Optimizations.class, 0, 11, 4);
    f("zeroin", /*zeroin*/ null, 0, 11, 5);
    f("zeroin2", Roots.class, 0, 11, 7);
    f("optim", Optimizations.class, 0, 11, 7);
    f("optimhess", /*optimhess*/ null, 0, 11, 4);
    f("terms.formula", Models.class, 0, 11, 5);
    f("update.formula", /*updateform*/ null, 0, 11, 2);
    f("model.frame", Models.class, 0, 11, 8);
    f("model.matrix", Models.class, 0, 11, 2);

    f("D", /*D*/ null, 0, 11, 2);
    f("deriv.default", /*deriv*/ null, 0, 11, 5);

/* History manipulation */
    f("loadhistory", /*loadhistory*/ null, 0, 11, 1);
    f("savehistory", /*savehistory*/ null, 0, 11, 1);
    f("addhistory", /*addhistory*/ null, 0, 11, 1);

/* date-time manipulations */
    f("Sys.time", Time.class, 0, 11, 0);
    f("as.POSIXct", Time.class, 0, 11, 2);
    f("as.POSIXlt", Time.class, 0, 11, 2);
    f("format.POSIXlt", Time.class, 0, 11, 3);
    f("strptime", Time.class,  0, 11, 3);
    f("Date2POSIXlt", Time.class, 0, 11, 1);
    f("POSIXlt2Date", Time.class, 0, 11, 1);


/* Connections */
    f("stdin", Connections.class, 0, 11, 0);
    f("stdout", Connections.class, 0, 11, 0);
    f("stderr", Connections.class, 0, 11, 0);
    f("readLines",Connections.class, 0, 11, 5);
    f("writeLines", Connections.class, 0, 11, 4);
    f("readBin", /*readbin*/ null, 0, 11, 6);
    f("writeBin", /*writebin*/ null, 0, 211, 5);
    f("readChar", Connections.class, 0, 11, 3);
    f("writeChar", /*writechar*/ null, 0, 211, 5);
    f("open", Connections.class, 0, 11, 3);
    f("isOpen", Connections.class, 0, 11, 2);
    f("isIncomplete", /*isincomplete*/ null, 0, 11, 1);
    f("isSeekable", /*isseekable*/ null, 0, 11, 1);
    f("close", Connections.class, 0, 11, 2);
    f("flush", /*flush*/ null, 0, 11, 1);
    f("file", Connections.class, 1, 11, 4);
    f("url", Connections.class, 0, 11, 4);
    f("pipe", /*pipe*/ null, 0, 11, 3);
    f("fifo", /*fifo*/ null, 0, 11, 4);
    f("gzfile", Connections.class, 0, 11, 4);
    f("bzfile", /*gzfile*/ null, 1, 11, 4);
    f("xzfile", /*gzfile*/ null, 2, 11, 4);
    f("unz", /*unz*/ null, 0, 11, 3);
    f("seek", /*seek*/ null, 0, 11, 4);
    f("truncate", /*truncate*/ null, 0, 11, 1);
    f("pushBack", Connections.class, 0, 11, 3);
    f("clearPushBack", Connections.class, 0, 11, 1);
    f("pushBackLength", Connections.class, 0, 11, 1);
    f("rawConnection", /*rawconnection*/ null, 0, 11, 3);
    f("rawConnectionValue", /*rawconvalue*/ null, 0, 11, 1);
    f("textConnection",  Connections.class, 0, 11, 5);
    f("textConnectionValue", /*textconvalue*/ null, 0, 11, 1);
    f("socketConnection", Connections.class, 0, 11, 6);
    f("sockSelect", /*sockselect*/ null, 0, 11, 3);
    f("getConnection", /*getconnection*/ null, 0, 11, 1);
    f("getAllConnections", /*getallconnections*/ null, 0, 11, 0);
    f("summary.connection", Connections.class, 0, 11, 1);
    f("download", /*download*/ null, 0, 11, 5);
    f("nsl", /*nsl*/ null, 0, 11, 1);
    f("gzcon", /*gzcon*/ null, 0, 11, 3);
    f("memCompress", /*memCompress*/ null, 0, 11, 2);
    f("memDecompress", /*memDecompress*/ null, 0, 11, 2);

    f("readDCF", DebianControlFiles.class, 0, 11, 2);

    f("getNumRtoCConverters", /*getNumRtoCConverters*/ null, 0, 11, 0);
    f("getRtoCConverterDescriptions", /*getRtoCConverterDescriptions*/ null, 0, 11, 0);
    f("getRtoCConverterStatus", /*getRtoCConverterStatus*/ null, 0, 11, 0);
    f("setToCConverterActiveStatus", /*setToCConverterActiveStatus*/ null, 0, 11, 2);
    f("removeToCConverterActiveStatus", /*setToCConverterActiveStatus*/ null, 1, 11, 1);

    f("lockEnvironment", Types.class, 0, 111, 2);
    f("environmentIsLocked", Types.class, 0, 11, 1);
    f("lockBinding", Types.class, 0, 111, 2);
    f("unlockBinding", Types.class, 1, 111, 2);
    f("bindingIsLocked", Types.class, 0, 11, 2);
    f("makeActiveBinding", /*mkActiveBnd*/ null, 0, 111, 3);
    f("bindingIsActive", /*bndIsActive*/ null, 0, 11, 2);
/* looks like mkUnbound is unused in base R */
    f("mkUnbound", /*mkUnbound*/ null, 0, 111, 1);
    f("isNamespace", Namespaces.class, 0, 0, 1);
  // hiding:  f("registerNamespace", Namespaces.class, 0, 11, 2);
   // hiding: f("unregisterNamespace", Namespaces.class, 0, 11, 1);
    f("getNamespace", Namespaces.class, 0, 0, 1);
    f("getRegisteredNamespace",Namespaces.class,  0, 11,  1);
    f("loadedNamespaces", Namespaces.class, 0,0,0);
    
    //hiding: f("getNamespaceRegistry", Namespaces.class, 0, 11, 0);
   // hiding f("importIntoEnv", Namespaces.class, 0, 11, 4);
    f("env.profile", /*envprofile*/ null, 0, 211, 1);
    f(":::", Namespaces.class, 0, 0, -1);
    f("::", Namespaces.class, 0, 0, -1);
    f("getDataset", Namespaces.class, 0, 11, 1);
    
    f("write.table", /*writetable*/ null, 0, 111, 11);
    f("Encoding", Types.class, 0, 11, 1);
    f("setEncoding", Types.class, 0, 11, 2);
  // REMOVED: f("lazyLoadDBfetch", Serialization.class, 0, 1, 4);
    f("setTimeLimit", /*setTimeLimit*/ null, 0, 111, 3);
    f("setSessionTimeLimit", /*setSessionTimeLimit*/ null, 0, 111, 2);
    f("icuSetCollate", /*ICUset*/ null, 0, 111, -1, PP_FUNCALL, PREC_FN, 0) ;
    
    // jvm specific
    f("import", Jvmi.class, 0, 0, -1);
    f("jload", Jvmi.class, 0, 0, -1);
    f("library", Packages.class, 0,0,-1);
    f("require", Packages.class, 0,0,-1);
';




@s = split("\n",$s);

$map{'AllNamesVisitor.class'}='';
$map{'Attributes.class'}='';
$map{'Beta.class'}='org.renjin.stats.internals.distributions.Beta';
$map{'Cat.class'}='';
$map{'Combine.class'}='';
$map{'CompleteCases.class'}='';
$map{'ComplexGroup.class'}='';
$map{'Conditions.class'}='';
$map{'Connections.class'}='';
$map{'Contexts.class'}='';
$map{'Covariance.class'}='';
$map{'DebianControlFiles.class'}='';
$map{'Deparse.class'}='';
$map{'Distributions.class'} = 'org.renjin.Distributions';;
$map{'Duplicates.class'}='';
$map{'Evaluation.class'}='';
$map{'FFT.class'}='';
$map{'Files.class'}='';
$map{'Graphics.class'}='';
$map{'Jvmi.class'}='';
$map{'Match.class'}='';
$map{'Math.class'}='java.lang.Math';
$map{'MathExt.class'}='org.renjin.MathExt';
$map{'Matrices.class'}='';
$map{'Methods.class'}='';
$map{'Models.class'}='';
$map{'Namespaces.class'}='';
$map{'Native.class'}='';
$map{'Optimizations.class'}='';
$map{'Packages.class'}='';
$map{'Par.class'}='';
$map{'Plot.class'}='';
$map{'Print.class'}='';
$map{'PsiGamma.class'}='';
$map{'RNG.class'}='org.renjin.stats.internals.distributions.RNG';
$map{'RgbHsv.class'}='';
$map{'Roots.class'}='';
$map{'S3.class'}='';
$map{'Sampling.class'}='org.renjin.stats.internals.distributions.Sampling';
$map{'Scan.class'}='';
$map{'Sequences.class'}='';
$map{'Serialization.class'}='';
$map{'Session.class'}='';
$map{'Sort.class'}='';
$map{'Split.class'}='';
$map{'Subsetting.class'}='';
$map{'Summary.class'}='';
$map{'System.class'}='';
$map{'Text.class'}='';
$map{'Time.class'}='';
$map{'Types.class'}='';
$map{'Warning.class'}='';
$map{'org.apache.commons.math.special.Gamma.class'}='org.apache.commons.math.special.Gamma';



$args{'bd0'} = "{x, np}, {x, np}      ";
$args{'beta'} = "{a, b}, {a, b}      ";
$args{'choose'} = "{n, k}, {n, k}      ";
$args{'dbeta'} = "{x, a, b, give_log}, {x, a, b}   ";
$args{'dbinom'} = "{x, n, p, give_log}, {x, n, p}   ";
$args{'dcauchy'} = "{x, location, scale, give_log}, {x, location, scale}   ";
$args{'dchisq'} = "{x, df, give_log}, {x, df}     ";
$args{'dexp'} = "{x, scale, give_log}, {x, scale}     ";
$args{'df'} = "{x, m, n, give_log}, {x, m, n}   ";
$args{'dgamma'} = "{x, shape, scale, give_log}, {x, shape, scale}   ";
$args{'dgeom'} = "{x, p, give_log}, {x, p}     ";
$args{'dhyper'} = "{x, r, b, n, give_log}, {x, r, b, n} ";
$args{'dlnorm'} = "{x, meanlog, sdlog, give_log}, {x, meanlog, sdlog}   ";
$args{'dlogis'} = "{x, location, scale, give_log}, {x, location, scale}   ";
$args{'dnbeta'} = "{x, a, b, ncp, give_log}, {x, a, b, ncp} ";
$args{'dnbinom'} = "{x, size, prob, give_log}, {x, size, prob}   ";
$args{'dnchisq'} = "{x, df, ncp, give_log}, {x, df, ncp}   ";
$args{'dnf'} = "{x, df1, df2, ncp, give_log}, {x, df1, df2, ncp} ";
$args{'dnorm'} = "{x, mu, sigma, give_log}, {x, mu, sigma}   ";
$args{'dnt'} = "{x, df, ncp, give_log}, {x, df, ncp}   ";
$args{'dpois'} = "{x, lambda, give_log}, {x, lambda}     ";
$args{'dt'} = "{x, n, give_log}, {x, n}     ";
$args{'dunif'} = "{x, a, b, give_log}, {x, a, b}   ";
$args{'dweibull'} = "{x, shape, scale, give_log}, {x, shape, scale}   ";
$args{'expm1'} = "{x}, {x}        ";
$args{'fmax2'} = "{x, y}, {x, y}      ";
$args{'fmin2'} = "{x, y}, {x, y}      ";
$args{'fprec'} = "{x, digits}, {x, digits}      ";
$args{'fround'} = "{x, digits}, {x, digits}      ";
$args{'fsign'} = "{x, y}, {x, y}      ";
$args{'ftrunc'} = "{x}, {x}        ";
$args{'gamma'} = "{x}, {x}        ";
$args{'imax2'} = "{x, y}, {x, y}      ";
$args{'imin2'} = "{x, y}, {x, y}      ";
$args{'lbeta'} = "{a, b}, null       ";
$args{'lgammacor'} = "{x}, {x}        ";
$args{'lgamma'} = "{x}, {x}        ";
$args{'pbeta'} = "{x, pin, qin, lower_tail, log_p}, {x, pin, qin}  ";
$args{'pbinom'} = "{x, n, p, lower_tail, log_p}, {x, n, p}  ";
$args{'pcauchy'} = "{x, location, scale, lower_tail, log_p}, {x, location, scale}  ";
$args{'pchisq'} = "{x, df, lower_tail, log_p}, {x, df}    ";
$args{'pexp'} = "{x, scale, lower_tail, log_p}, {x, scale}    ";
$args{'pf'} = "{x, df1, df2, lower_tail, log_p}, {x, df1, df2}  ";
$args{'pgamma'} = "{x, alph, scale, lower_tail, log_p}, {x, alph, scale}  ";
$args{'pgeom'} = "{x, p, lower_tail, log_p}, {x, p}    ";
$args{'phyper'} = "{x, NR, NB, n, lower_tail, log_p}, {x, NR, NB, n}";
$args{'plnorm'} = "{x, meanlog, sdlog, lower_tail, log_p}, {x, meanlog, sdlog}  ";
$args{'plogis'} = "{x, location, scale, lower_tail, log_p}, {x, location, scale}  ";
$args{'pnbeta'} = "{x, a, b, ncp, lower_tail, log_p}, {x, a, b, ncp}";
$args{'pnbinom'} = "{x, size, prob, lower_tail, log_p}, {x, size, prob}  ";
$args{'pnchisq'} = "{x, df, ncp, lower_tail, log_p}, {x, df, ncp}  ";
$args{'pnf'} = "{x, df1, df2, ncp, lower_tail, log_p}, {x, df1, df2, ncp}";
$args{'pnorm'} = "{x, mu, sigma, lower_tail, log_p}, {x, mu, sigma}  ";
$args{'pnt'} = "{t, df, ncp, lower_tail, give_log}, {t, df, ncp}  ";
$args{'ppois'} = "{x, lambda, lower_tail, log_p}, {x, lambda}    ";
$args{'pt'} = "{x, n, lower_tail, /*log_p*/give_log}, {x, n, /*log_p*/give_log}   ";
$args{'ptukey'} = "{q, rr, cc, df, lower_tail, log_p}, {q, rr, cc, df}";
$args{'punif'} = "{x, a, b, lower_tail, log_p}, {x, a, b}  ";
$args{'pweibull'} = "{x, shape, scale, lower_tail, log_p}, {x, shape, scale}  ";
$args{'qbeta'} = "{p, shape1, shape2, lower_tail, log_p}, {p, shape1, shape2}  ";
$args{'qbinom'} = "{p, n, pr, lower_tail, log_p}, {p, n, pr}  ";
$args{'qcauchy'} = "{p, location, scale, lower_tail, log_p}, {p, location, scale}  ";
$args{'qchisq'} = "{p, df, lower_tail, log_p}, {p, df}    ";
$args{'qexp'} = "{p, scale, lower_tail, log_p}, {p, scale}    ";
$args{'qf'} = "{p, df1, df2, lower_tail, log_p}, {p, df1, df2}  ";
$args{'qgamma'} = "{p, alpha, beta, lower_tail, log_p}, {p, alpha, beta}  ";
$args{'qgeom'} = "{p, prob, lower_tail, log_p}, {p, prob}    ";
$args{'qhyper'} = "{p, NR, NB, n, lower_tail, log_p}, {p, NR, NB, n}";
$args{'qlnorm'} = "{p, meanlog, sdlog, lower_tail, log_p}, {p, meanlog, sdlog}  ";
$args{'qlogis'} = "{p, location, scale, lower_tail, log_p}, {p, location, scale}  ";
$args{'qnbeta'} = "{p, a, b, ncp, lower_tail, log_p}, {p, a, b, ncp}";
$args{'qnbinom'} = "{p, size, prob, lower_tail, log_p}, {p, size, prob}  ";
$args{'qnchisq'} = "{p, df, ncp, lower_tail, log_p}, {p, df, ncp}  ";
$args{'qnf'} = "{p, df1, df2, ncp, lower_tail, log_p}, {p, df1, df2, ncp}";
$args{'qnorm'} = "{p, mu, sigma, lower_tail, log_p}, {p, mu, sigma}  ";
$args{'qnt'} = "{p, df, ncp, lower_tail, log_p}, {p, df, ncp}  ";
$args{'qpois'} = "{p, lambda, lower_tail, log_p}, {p, lambda}    ";
$args{'qt'} = "{p, df, lower_tail, give_log}, {p, df}    ";
$args{'qtukey'} = "{p, rr, cc, df, lower_tail, log_p}, {p, rr, cc, df}";
$args{'qunif'} = "{p, a, b, lower_tail, log_p}, {p, a, b}  ";
$args{'qweibull'} = "{p, shape, scale, lower_tail, log_p}, {p, shape, scale}  ";
$args{'rbeta'} = "{aa, bb}, {aa, bb}      ";
$args{'rbinom'} = "{nin, pp}, {nin, pp}      ";
$args{'rcauchy'} = "{location, scale}, {location, scale}      ";
$args{'rchisq'} = "{df}, {df}        ";
$args{'rexp'} = "{scale}, {scale}        ";
$args{'rf'} = "{n1, n2}, {n1, n2}      ";
$args{'rgamma'} = "{a, scale}, {a, scale}      ";
$args{'rgeom'} = "{p}, {p}        ";
$args{'rhyper'} = "{nn1in, nn2in, kkin}, {nn1in, nn2in, kkin}    ";
$args{'rlnorm'} = "{meanlog, sdlog}, {meanlog, sdlog}      ";
$args{'rlogis'} = "{location, scale}, {location, scale}      ";
$args{'rmultinom'} = "{n, []prob, K, []rN}, {n, []prob, K, []rN}  ";
$args{'rnbinom'} = "{size, prob}, {size, prob}      ";
$args{'rnchisq'} = "{df, lambda}, {df, lambda}      ";
$args{'rnorm'} = "{mu, sigma}, {mu, sigma}      ";
$args{'rpois'} = "{mu}, {mu}        ";
$args{'rt'} = "{df}, {df}        ";
$args{'runif'} = "{a, b}, {a, b}      ";
$args{'rweibull'} = "{shape, scale}, {shape, scale}      ";
$args{'sign'} = "{x}, {x}        ";
$args{'stirlerr'} = "{n}, {n}        ";
$args{'wilcox'} = "{x, m, n, lower_tail, log_p}, {x, m, n}  ";

$args{'round'} = "{x} {x}";
$args{'signif'} = "{x} {x}";
$args{'atan'} = "{x} {x}";
$args{'log'} = "{x} {x}";
$args{'log10'} = "{x} {x}";
$args{'log2'} = "{x} {x}";
$args{'abs'} = "{x} {x}";
$args{'floor'} = "{x} {x}";
$args{'ceiling'} = "{x} {x}";
$args{'ceil'} = "{x} {x}";
$args{'sqrt'} = "{x} {x}";
$args{'trunc'} = "{x} {x}";
$args{'exp'} = "{x} {x}";
$args{'log1p'} = "{x} {x}";
$args{'cos'} = "{x} {x}";
$args{'sin'} = "{x} {x}";
$args{'tan'} = "{x} {x}";
$args{'acos'} = "{x} {x}";
$args{'asin'} = "{x} {x}";
$args{'cosh'} = "{x} {x}";
$args{'sinh'} = "{x} {x}";
$args{'tanh'} = "{x} {x}";
$args{'acosh'} = "{x} {x}";
$args{'asinh'} = "{x} {x}";
$args{'atanh'} = "{x} {x}";
$args{'logGamma'} = "{x} {x}";
$args{'digamma'} = "{x} {x}";
$args{'trigamma'} = "{x} {x}";



$k = 0;
foreach $s (@s) {
	#remove comments
	$s =~ s/\/\*.*\*\///;
	$s =~ s/\/\/.*//;
	if (($s !~ /^\s*$/) && ($s =~ /class/)) {
		process($s);
	$k++;
	}
}
sub process {
	my $line = shift;
if ($line =~ /f\((.*),(.*),(.*),(.*),(.*),(.*)\)/) {
	$l1 = "f($1,$2,$4,$5,$6)";
	$l2 = "f($3,$2,$4,$5,$6)";
	process($l1);
	process($l2);
} elsif ($line =~ /f\((.*),(.*),(.*),(.*),(.*)\)/) {
	$cmd = $1;
	$class = $2;
	$offset = $3;
	$eval = $4;
	$arity = $5;
	$cmd =~ s/"//g;
	$class = trim($class);	
	$class = $map{$class};
	if ($class ne '') {
#		print "$k $line\n";
		generateCmd($cmd, $class, $arity);
	}
} else {
	print "MISMATCH $line\n";
}
}

sub generateCmd {
	open(FOUT,">$cmd.bsh") or die "cannot open file $cmd.bsh for writing";
print "$cmd $args{$cmd} $arity\n";
	if ($arity =~/(.*)\+(.*)/) {
		$args = $1;
		$opts = $2;
		$args=trim($args);
		$opts=trim($opts);
		$a = '';
		for ($i = 0;$i < $args; $i++) {
			$a .= "arg$i, ";
		}
		$a =~ s/, $//;
		if ($args{$cmd} ne '') {
			$args{$cmd} =~ /.*}.*{([^}]*)}/;
			$a = $1;
		}
		if ($opts == 0) {
			print FOUT "\n$cmd($a) {";
			printBody("$class.$cmd($a);");
			print FOUT "}\n";
		} elsif ($opts == 1) {
			print FOUT "\n$cmd($a) {";
			printBody("$class.$cmd($a, false);");
			print FOUT "}\n\n";
			print FOUT "$cmd($a, log) {\n";
			printBody("$class.$cmd($a, log);");
			print FOUT "}\n\n";
		} elsif ($opts == 2) {
			print FOUT "$cmd($a) {";
			printBody("$class.$cmd($a, false, false);");
			print FOUT "}\n\n";
			print FOUT "$cmd($a, log) {";
			printBody("$class.$cmd($a, false, log);");
			print FOUT "}\n\n";
			print FOUT "$cmd($a, lowerTail, log) {";
			printBody("$class.$cmd($a, lowerTail, log);");
			print FOUT "}\n\n";
		}
		
	} elsif ($arity =~/-1/) {
		$a = 'arg';
		if ($args{$cmd} ne '') {
			$args{$cmd} =~ /.*}.*{([^}]*)}/;
			$a = $1;
		}
		print FOUT "$cmd($a) {";
		printBody("$class.$cmd($a);");
		print FOUT "}\n";

	} else {
		$args = trim($arity);
		$a = '';
		for ($i = 0;$i < $args; $i++) {
			$a .= "arg$i, ";
		}
		$a =~ s/, $//;
		if ($args{$cmd} ne '') {
			$args{$cmd} =~ /.*}.*{([^}]*)}/;
			$a = $1;
		}
		print FOUT "$cmd($a) {";
		printBody("$class.$cmd($a);");
		print FOUT "}\n";
	}
	close FOUT;
}


sub printBody {
	$body = shift;
	$arg0 = $a;
	if ($arg0 =~ /^x/) {
	$body =~ s/\(x/(i/;
	$body =~ s/;//;
		print FOUT '
	if (x instanceof List || x.getClass().isArray()) {
		List<Double> result = new ArrayList<Double>();
		for (i : x) {
			result.add('.$body.');
		}
		return result;
	} else {
		return '.$body.'
	}
';} elsif ($arg0 =~ /^p/) {
	$body =~ s/\(p/(i/;
	$body =~ s/;//;
		print FOUT '
	if (p instanceof List || p.getClass().isArray()) {
		List<Double> result = new ArrayList<Double>();
		for (i : p) {
			result.add('.$body.');
		}
		return result;
	} else {
		return '.$body.'
	}
';
	} else {
		print FOUT "return $body\n";
	}
}


# Perl trim function to remove whitespace from the start and end of the string
sub trim($)
{
	my $string = shift;
	$string =~ s/^\s+//;
	$string =~ s/\s+$//;
	return $string;
}

