grammar MathAntlers;
prog: NEWLINE* expr (NEWLINE* expr)* NEWLINE* EOF ;
expr:   expr '_' expr         # subscript
    |   'cases' '{' NEWLINE? (expr ',' expr ';' NEWLINE?)* NEWLINE? '}' # cases
    |   'sum' '(' expr ',' expr ')'     # summation
    |   ('floor' | 'flr') '(' expr ')'  # floor
    |   'ceil' '(' expr ')'   # ceil
    |   '{' expr '}'          # invisibleBrackets
    |   '[' expr ']'          # squareBrackets
    |   '(' expr ')'          # parentheses
    |   '{{' expr '}}'        # braces
    |   expr expr             # pointFreeMult
    |   expr '^' expr         # exponent
    |   expr MULDIV expr      # mulDiv
    |   expr PLUSMINUS expr   # addSub
    |   expr '==' expr        # alignedEquality
    |   expr '!=' expr        # alignedInequality
    |   expr '=>' expr        # alignedGreaterThan
    |   expr '=<' expr        # alignedLessThan
    |   expr '=>=' expr       # alignedGreaterEqual
    |   expr '=<=' expr       # alignedLessEqual
    |   expr '=' expr         # equality
    |   expr '!=' expr        # inequality
    |   expr '>' expr         # greaterThan
    |   expr '<' expr         # lessThan
    |   expr '>=' expr        # greaterEqual
    |   expr '<=' expr        # lessEqual
    |   '-' expr              # negate
    |   expr ',' expr         # list
    |   STRING                # string
    |   NUMBER                # num
    |   VARIABLE              # var
    |   '...'                 # ellipses
    |   'therefore'           # therefore
    |   'el'                  # elementOf
    |   '|'                   # pipe
    ;
NEWLINE: [\n]+;
STRING: '"' ~["]+ '"';
SPACE:    [\t ]+ -> skip;
NUMBER:     [0-9]+ | [0-9]*[.][0-9]+ ;
VARIABLE:   [a-zA-Z] ;
PLUSMINUS: '+' | '-';
MULDIV: '*' | '/';
