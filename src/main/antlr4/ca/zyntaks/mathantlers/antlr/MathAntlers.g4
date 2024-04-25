grammar MathAntlers;
prog: expr (NEWLINE expr)* EOF ;
expr:   expr '_' expr     # subscript // Subscript
    |   'sum' '(' expr ',' expr ')' # summation
    |   ('floor' | 'flr') '(' expr ')'    # floor
    |   'ceil' '(' expr ')' # ceil
    |   '{' expr '}'                # invisibleBrackets
    |   '[' expr ']'                # squareBrackets
    |   '(' expr ')'                # parentheses
    |   '{{' expr '}}'              # braces
    |   expr expr                   # pointFreeMult
    |   expr '^' expr               # exponent
    |   expr MULDIV expr         # mulDiv
    |   expr PLUSMINUS expr         # addSub
    |   expr ',' expr         # list
    |   expr '==' expr        # alignedEquality
    |   expr '=' expr         # equality
    |   '-' expr                # negate
    |   STRING                      # string
    |   NUMBER                      # num
    |   VARIABLE                    # var
    |   '...'                       # ellipses
    |   'therefore'                 # therefore
    |   'el' # elementOf
    |   '|' # pipe
    ;
NEWLINE: [\n]+;
STRING: '"' ~["]+ '"';
SPACE:    [\r\n\t ]+ -> skip;
NUMBER:     [0-9]+ | [0-9]*[.][0-9]+ ;
VARIABLE:   [a-zA-Z] ;
PLUSMINUS: '+' | '-';
MULDIV: '*' | '/';
