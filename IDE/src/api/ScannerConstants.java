package api;

import importador.Importador;

public interface ScannerConstants
{
    int[] SCANNER_TABLE_INDEXES = 
    {
        0,
        87,
        87,
        88,
        281,
        281,
        281,
        281,
        281,
        281,
        291,
        293,
        306,
        317,
        317,
        319,
        320,
        322,
        385,
        385,
        385,
        385,
        448,
        512,
        575,
        638,
        701,
        764,
        827,
        890,
        953,
        1016,
        1079,
        1143,
        1206,
        1206,
        1206,
        1206,
        1206,
        1206,
        1206,
        1216,
        1409,
        1600,
        1602,
        1618,
        1618,
        1618,
        1618,
        1618,
        1618,
        1681,
        1744,
        1807,
        1808,
        1871,
        1934,
        1997,
        2060,
        2123,
        2186,
        2249,
        2312,
        2375,
        2376,
        2439,
        2502,
        2695,
        2697,
        2713,
        2776,
        2839,
        2902,
        2903,
        2966,
        3029,
        3092,
        3155,
        3218,
        3281,
        3344,
        3407,
        3470,
        3471,
        3534,
        3598,
        3598,
        3661,
        3724,
        3787,
        3850,
        3850,
        3913,
        3976,
        4039,
        4102,
        4165,
        4228,
        4229,
        4292,
        4293,
        4356,
        4419,
        4482,
        4545,
        4608,
        4671,
        4734,
        4797,
        4798,
        4861,
        4862,
        4925,
        4988,
        5051,
        5114,
        5115,
        5178,
        5179,
        5242,
        5305,
        5306,
        5369,
        5370,
        5433,
        5496,
        5496,
        5559,
        5560,
        5623,
        5686,
        5687,
        5750,
        5751,
        5814,
        5815,
        5878,
        5879,
        5879
    };

    int[][] SCANNER_TABLE =  new Importador().getTabela();

    int[] TOKEN_STATE = { -1,   0,  -1,  -1,  27,   7,   8,  13,  14,  -1,  -1,   2,   2,  15,  17,  -1,  16,  48,   9,  10,  30,  48,  48,  48,  48,  48,  48,  48,  48,  48,  48,  48,  48,  48,  11,  28,  12,  29,  21,   6,   3,  -1,  24,  -1,  -1,  26,  19,  20,  18,  25,  48,  48,  48,  -1,  48,  48,  48,  48,  48,  48,  48,  48,  48,  -1,  48,  48,  -1,   4,   5,  48,  48,  48,  -1,  48,  22,  48,  48,  48,  48,  48,  47,  31,  -1,  48,  48,  23,  48,  48,  48,  48,  46,  48,  34,  48,  32,  48,  48,  -1,  48,  -1,  48,  42,  48,  48,  35,  33,  48,  44,  -1,  48,  -1,  48,  43,  48,  36,  -1,  48,  -1,  48,  48,  -1,  48,  41,  48,  39,  45,  48,  -1,  38,  48,  -1,  48,  -1,  48,  -1,  37,  -1,  40 };

    String[] SCANNER_ERROR =
    {
        "Caractere n�o esperado",
        "",
        "Erro identificando NEQ",
        "Erro identificando STRING_LIT",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando FLOAT_LIT",
        "Erro identificando M_COMMENT ou COMMENT",
        "",
        "",
        "",
        "",
        "Erro identificando EQ",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando M_COMMENT",
        "",
        "Erro identificando BIN_LIT",
        "Erro identificando HEX_LIT",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando FOR",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando WHILE",
        "",
        "",
        "Erro identificando M_COMMENT",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando FOR",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando WHILE",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando WHILE",
        "",
        "Erro identificando RETURN ou RETURNS",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando WHILE",
        "",
        "Erro identificando RETURN ou RETURNS",
        "",
        "",
        "",
        "",
        "Erro identificando WHILE",
        "",
        "Erro identificando RETURN ou RETURNS",
        "",
        "",
        "Erro identificando WHILE",
        "",
        "",
        "",
        "",
        "",
        "",
        "Erro identificando RETURN",
        "",
        "",
        "Erro identificando RETURN",
        "",
        "Erro identificando RETURN",
        "",
        "Erro identificando RETURN",
        "",
        "Erro identificando RETURN",
        ""
    };

}