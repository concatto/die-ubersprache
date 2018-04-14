package api;public interface Constants extends ScannerConstants, ParserConstants
{
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_INT_LIT = 2;
    int t_FLOAT_LIT = 3;
    int t_BIN_LIT = 4;
    int t_HEX_LIT = 5;
    int t_STRING_LIT = 6;
    int t_RB_OPEN = 7;
    int t_RB_CLOSE = 8;
    int t_SB_OPEN = 9;
    int t_SB_CLOSE = 10;
    int t_CB_OPEN = 11;
    int t_CB_CLOSE = 12;
    int t_PLUS = 13;
    int t_MINUS = 14;
    int t_END = 15;
    int t_GT = 16;
    int t_LT = 17;
    int t_GTE = 18;
    int t_LTE = 19;
    int t_EQ = 20;
    int t_NEQ = 21;
    int t_ATTRIB = 22;
    int t_M_COMMENT = 23;
    int t_COMMENT = 24;
    int t_SHIFT_RIGHT = 25;
    int t_SHIFT_LEFT = 26;
    int t_LOGIC_AND = 27;
    int t_LOGIC_OR = 28;
    int t_LOGIC_NOT = 29;
    int t_LOGIC_XOR = 30;
    int t_AND = 31;
    int t_OR = 32;
    int t_NOT = 33;
    int t_VOID = 34;
    int t_INT = 35;
    int t_FLOAT = 36;
    int t_STRING = 37;
    int t_BOOL = 38;
    int t_FUNC = 39;
    int t_RETURN = 40;
    int t_RETURNS = 41;
    int t_IF = 42;
    int t_FALSE = 43;
    int t_ELSE = 44;
    int t_WHILE = 45;
    int t_FOR = 46;
    int t_DO = 47;
    int t_ID = 48;

}
