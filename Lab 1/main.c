//
//  main.c
//  Lab 1
//
//  Created by Lucas Wiener on 9/6/12.
//  Copyright (c) 2012 skola. All rights reserved.
//

#include <stdio.h>

int main(int argc, const char * argv[])
{   
    if (argc != 2) {
        printf("Please specify the word to search for as an argument.");
        return 0;
    }
    
    const char *word = argv[1];
    
    printf("Word to search for: %s", word);
    
    return 0;
}

