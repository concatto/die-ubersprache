package api;public interface ParserConstants
{
    int FIRST_SEMANTIC_ACTION = 51;

    int SHIFT  = 0;
    int REDUCE = 1;
    int ACTION = 2;
    int ACCEPT = 3;
    int GO_TO  = 4;
    int ERROR  = 5;

    int[][][] PARSER_TABLE =
    {
        { {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {SHIFT , 3}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {GO_TO , 1}, {GO_TO , 2}, {ERROR , 0} },
        { {ACCEPT, 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0} },
        { {REDUCE, 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0} },
        { {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {SHIFT , 4}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0} },
        { {REDUCE, 1}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0}, {ERROR , 0} }
    };

    int[][] PRODUCTIONS =
    {
        { 49, 1 },
        { 50, 2 }
    };

    String[] PARSER_ERROR =
    {
        "Erro estado 0",
        "Erro estado 1",
        "Erro estado 2",
        "Erro estado 3",
        "Erro estado 4"
    };
}
