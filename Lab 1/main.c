//
//  main.c
//  Lab 1
//
//  Created by Lucas Wiener on 9/6/12.
//  Copyright (c) 2012 skola. All rights reserved.
//

#include <stdio.h>
#include "file.h"

int main(int argc, const char * argv[])
{
    if (argc == 2) {
        const char *word = argv[1];
        printf("Word to search for: %s", word);
    }
    else if (argc == 3) {
        FILE *file = openfile("./resources/words.txt", "r");
        
        closefile(file);
        
        wordpair p = readPairByLine(1);
        
        printf("%i, %s", p.index, p.text);
        
    }
    else {
        printf("Please specify the word to search for as an argument or specify -c <filename of text> to build files.");
        
    }
    
    return 0;
}

