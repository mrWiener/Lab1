//
//  file.c
//  Lab 1
//
//  Created by Lucas Wiener on 9/10/12.
//  Copyright (c) 2012 skola. All rights reserved.
//

#include <stdio.h>
#include "file.h"

FILE* openfile(const char *filename, const char *mode) {
    return fopen(filename, mode);
}

int closefile(FILE *file) {
    return fclose(file);
}

char* readline() {
    return NULL;
}

wordpair readPairByLine(int line) {
    
    wordpair p;
    p.text = "hej";
    p.index = 3;
    
    return p;
}